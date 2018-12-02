package cn.newtol.weixin.service;

import cn.newtol.weixin.Enum.ResultEnum;
import cn.newtol.weixin.domain.WeiXinSendAutoMessage;
import cn.newtol.weixin.domain.WeiXinUserInfo;
import cn.newtol.weixin.domain.HttpClientResult;
import cn.newtol.weixin.domain.Result;
import cn.newtol.weixin.domain.dto.*;
import cn.newtol.weixin.repository.BaseUserInfoRepository;
import cn.newtol.weixin.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 19:23 2018/11/10
 */
@Service
@Component
public class WeiXinBaseServiceImpl implements WeiXinBaseService {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinBaseServiceImpl.class);

    @Resource
    private WeiXinUtil weiXinUtil;

    @Autowired
    BaseUserInfoRepository baseUserInfoRepository;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    HttpServletUtil httpServletUtil;

    @Autowired
    JacksonUtil jacksonUtil;

    /**
     *  从配置文件中获取redis中AccessToken存入的key
     */
    @Value("${weiXin.accessTokenKey}")
    private String accessTokenKey;

    /**
     * 微信服务器配置的token
     */
    @Value("${weiXin.token}")
    private String token;

    /**
     * RedirectUrl链接的key
     */
    @Value("${weiXin.redirectUrlKey}")
    private String redirectUrlKey;

    /**
     * 跳转到本地的网页授权页面
     */
    @Value("${weiXin.getWebAuthorize}")
    private String getWebAuthorizeUrl;

    /**
     *  微信公众号菜单
     */
    @Resource
    private WeiXinMenu weiXinMenu;

    @Override
    public String joinWeiXin(WeiXinVerify weiXinVerify) {
        return  weiXinUtil.joinWeiXin(weiXinVerify,token);
    }

    @Override
    public Result getAccessToken(WeiXinConfigInfo weiXinConfigInfo) throws Exception {
        String access_token = weiXinUtil.getAccessToken(weiXinConfigInfo.getAppId(), weiXinConfigInfo.getAppSecret(), accessTokenKey);
        Map<String,String> map = new HashMap<>(1);
        map.put("access_token",access_token);
        return ResultUtil.success(map);
    }

    /**
     * @param weiXinConfigInfo
     * @Author: 公众号：Newtol
     * @Description: 设置微信公众号的菜单
     * @Date: Created in 19:49
     */
    @Override
    public Result setWeiXinMenu(WeiXinConfigInfo weiXinConfigInfo) throws Exception {
        String access_token = weiXinUtil.getAccessToken(weiXinConfigInfo.getAppId(), weiXinConfigInfo.getAppSecret(), accessTokenKey);
        if (access_token == null ||"".equals(access_token)){
            logger.error("请求菜单时access_token为空："+ weiXinConfigInfo);
            return ResultUtil.error(ResultEnum.ERROR_WEIXINBASEINFO);
        }
        /* 封装url，参数，Json数据*/
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
        Map<String,String> map = new HashMap<>(1);
        map.put("access_token",access_token);
        String jsonData = JSON.toJSONString(weiXinMenu);
        HttpClientResult httpClientResult = HttpClientUtil.doPostForJson(url,map,jsonData);
       /* 获取返回结果*/
        Result result = ResultUtil.success(httpClientResult);
        logger.info("微信公众号菜单设置请求的JSON参数："+jsonData);
        return result;
    }

    /**
     * @param weiXinWebAuthorize
     * @Author: 公众号：Newtol
     * @Description: 获取网页授权时，用于获取用户openId
     * @Date: Created in 21:16
     * @param:
     */
    @Override
    public void weiXinWebAuthorize(WeiXinWebAuthorize weiXinWebAuthorize, HttpServletRequest request, HttpServletResponse response) throws Exception {
        /* 从redis中获取appId,appSecret,redirectUrl等信息*/
        String stateInfo = redisUtil.getHash(redirectUrlKey,weiXinWebAuthorize.getState());
        RedirectUrlWeiXinConfig weiXinRedirectUrl = JSONObject.parseObject(stateInfo, RedirectUrlWeiXinConfig.class);
        String redirectUrl = weiXinRedirectUrl.getRedirectUrl();

        /* 获取openId等信息 */
        WeiXinUserInfoOpenId weiXinUserInfoOpenId = getUserOpenId(weiXinRedirectUrl.getAppId(),weiXinRedirectUrl.getAppSecret(),weiXinWebAuthorize.getCode());

        /* 获取公众号的access_token信息 */
        String accessToken = weiXinUtil.getAccessToken(weiXinRedirectUrl.getAppId(),weiXinRedirectUrl.getAppSecret(), accessTokenKey);

        /* 获取用户信息 */
        WeiXinUserInfo weiXinUserInfo = weiXinUtil.getUserInfo(accessToken, weiXinUserInfoOpenId.getOpenId());

//        WeiXinUserInfo weiXinUserInfo = getWeiXinNoSubUserInfo(weiXinUserInfoOpenId);

        /* 看数据库是否存在，存在则更新，不存在就插入*/
        WeiXinUserInfo weiXinUserInfoFromDatabase = baseUserInfoRepository.findBaseUserInfoByOpenId(weiXinUserInfoOpenId.getOpenId());
        if (weiXinUserInfoFromDatabase != null || "".equals(weiXinUserInfoFromDatabase)){
            weiXinUserInfo.setId(weiXinUserInfoFromDatabase.getId());
        }

        /* 保存数据库*/
        baseUserInfoRepository.save(weiXinUserInfo);
        /* 保存Session */
        Map<String,Object> map = new HashMap<>(2);
        map.put("User",weiXinUserInfo);
        map.put("stateInfo",stateInfo);
        httpServletUtil.setSession(map,request);
        /* 重定向到业务页面*/
        httpServletUtil.redirectToUrl(response,redirectUrl);
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 网页授权时，用户授权获取用户信息
    * @Date: Created in 23:42
    * @param:
    */
    public WeiXinUserInfo getWeiXinNoSubUserInfo(WeiXinUserInfoOpenId weiXinUserInfoOpenId) throws Exception {
        String url = "https://api.weixin.qq.com/sns/userinfo";
        Map<String,String> map = new HashMap<>(3);
        map.put("access_token",weiXinUserInfoOpenId.getAccessToken());
        map.put("openid",weiXinUserInfoOpenId.getOpenId());
        map.put("lang","zh_CN");
        HttpClientResult httpClientResult = HttpClientUtil.doGet(url,map);
        System.out.println(httpClientResult.getContent());
        WeiXinUserInfo weiXinUserInfo = JSONObject.parseObject(httpClientResult.getContent(), WeiXinUserInfo.class);
        if (weiXinUserInfo ==null || "".equals(weiXinUserInfo)){
            logger.info("获取用户信息失败："+httpClientResult.getContent()+"==="+"openid"+weiXinUserInfoOpenId.getOpenId()+"==="+"access_token"+weiXinUserInfoOpenId.getAccessToken());
        }
        return weiXinUserInfo;
    }
    /**
    * @Author: 公众号：Newtol
    * @Description: 网页授权时获取用户的openId
    * @Date: Created in 15:56
    * @param: 
    */
    private WeiXinUserInfoOpenId getUserOpenId(String appId, String appSecret, String code) throws Exception {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        /* 封装请求的参数*/
        Map<String,String>  map = new HashMap<>(4);
        map.put("appid",appId);
        map.put("secret",appSecret);
        map.put("code",code);
        map.put("grant_type","authorization_code");
        /* 获取请求结果 */
        HttpClientResult httpClientResult = HttpClientUtil.doGet(url,map);
        return JSONObject.parseObject(httpClientResult.getContent(), WeiXinUserInfoOpenId.class);
    }

    /**
     * @param weiXinRedirectUrl
     * @Author: 公众号：Newtol
     * @Description: 网页授权时，设置跳转的链接，即为返回获取code时，使用的state
     * @Date: Created in 13:16
     * @param:
     */
    @Override
    public Result setRedirect(RedirectUrlWeiXinConfig weiXinRedirectUrl) throws NoSuchAlgorithmException {
        String state = EncryptUtil.md5(weiXinRedirectUrl.toString());
        String redirectUrl = JSONObject.toJSONString(weiXinRedirectUrl);
        redisUtil.setHash(redirectUrlKey,state,redirectUrl);
        return  ResultUtil.success(state);
    }

    /**
     * @param response
     * @param state
     * @Author: 公众号：Newtol
     * @Description: 跳转到微信网页授权链接
     * @Date: Created in 19:51
     * @param:
     */
    @Override
    public void redirectToWeiXin(HttpServletResponse response, String state) {
        String stateInfo = redisUtil.getHash(redirectUrlKey,state);
        RedirectUrlWeiXinConfig weiXinRedirectUrl = JSONObject.parseObject(stateInfo, RedirectUrlWeiXinConfig.class);
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weiXinRedirectUrl.getAppId()+"&redirect_uri="+getWebAuthorizeUrl+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
        httpServletUtil.redirectToUrl(response,url);
    }

    /**
     * @param weiXinReceiveMessage
     * @Author: 公众号：Newtol
     * @Description: 微信自动回复关键词
     * @Date: Created in 22:44
     * @param:
     */
    @Override
    public String sendAutoMessage(WeiXinReceiveMessage weiXinReceiveMessage) throws JsonProcessingException {
        /* 返回结果*/
        String result = null;
        /* 获得公众号的id*/
        String appId = weiXinReceiveMessage.getToUserName();
        /* 获得关键词*/
        String content = weiXinReceiveMessage.getContent();
        /* 如果查找到关键词就根据关键词回复，否则调用客服接口*/
        if (content == null ) {
            //调用客服接口
            return result;
        }
        /* 获得需要回复的内容，为空则调用客服接口*/
        String str = redisUtil.getHash(appId,content);
        if (str == null || "".equals(str)){
            //调用客服接口
            return result;
        }

        WeiXinSendAutoMessage weiXinSendAutoMessage = JSONObject.parseObject(str,WeiXinSendAutoMessage.class);
        /* 设置接收的用户openId*/
        String openId = weiXinReceiveMessage.getFromUserName();
        weiXinSendAutoMessage.setToUserName(openId);
        /* 设置时间*/
        Long createTime = System.currentTimeMillis()/1000;
        weiXinSendAutoMessage.setCreateTime(createTime);
        /* 将数据转换为xml格式*/
        result = jacksonUtil.getXmlFromBean(weiXinSendAutoMessage);
        System.out.println("re:"+result);
        return result;
    }

    /**
     * @param
     * @Author: 公众号：Newtol
     * @Description: 设置自动回复图片内容
     * @Date: Created in 23:58
     * @param:
     */
    @Override
    public Result setAutoMessage(WeiXinSendAutoMessage weiXinSendAutoMessage) {
        String appId = weiXinSendAutoMessage.getFromUserName();
        String content = JSONObject.toJSONString(weiXinSendAutoMessage);
        redisUtil.setHash(appId,weiXinSendAutoMessage.getKey(),content);
        return ResultUtil.success(weiXinSendAutoMessage);
    }

}
