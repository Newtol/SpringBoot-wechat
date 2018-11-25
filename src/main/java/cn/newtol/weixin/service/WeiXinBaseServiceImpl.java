package cn.newtol.weixin.service;

import cn.newtol.weixin.Enum.ResultEnum;
import cn.newtol.weixin.domain.WeiXinUserInfo;
import cn.newtol.weixin.domain.HttpClientResult;
import cn.newtol.weixin.domain.Result;
import cn.newtol.weixin.domain.dto.*;
import cn.newtol.weixin.repository.BaseUserInfoRepository;
import cn.newtol.weixin.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    /* 从配置文件中获取redis中AccessToken存入的key */
    @Value("${weiXin.accessTokenKey}")
    private String accessTokenKey;

    /* 微信服务器配置的token */
    @Value("${weiXin.token}")
    private String token;

    /* RedirectUrl链接的key*/
    @Value("${weiXin.redirectUrlKey}")
    private String redirectUrlKey;

    /* 跳转到本地的网页授权页面*/
    @Value("${weiXin.getWebAuthorize}")
    private String getWebAuthorizeUrl;

    /* 微信公众号菜单*/
    @Resource
    Menu menu;

    @Override
    public String joinWeiXin(WeiXinVerify weiXinVerify) {
        return  weiXinUtil.joinWeiXin(weiXinVerify,token);
    }

    @Override
    public Result getAccessToken(WeiXinBaseInfo weiXinBaseInfo) throws Exception {
        String access_token = weiXinUtil.getAccessToken(weiXinBaseInfo.getAppId(),weiXinBaseInfo.getAppSecret(), accessTokenKey);
        Map<String,String> map = new HashMap<>(1);
        map.put("access_token",access_token);
        return ResultUtil.success(map);
    }

    /**
     * @param weiXinBaseInfo
     * @Author: 公众号：Newtol
     * @Description: 设置微信公众号的菜单
     * @Date: Created in 19:49
     * @param:
     */
    @Override
    public Result setWeiXinMenu(WeiXinBaseInfo weiXinBaseInfo) throws Exception {
        String access_token = weiXinUtil.getAccessToken(weiXinBaseInfo.getAppId(),weiXinBaseInfo.getAppSecret(), accessTokenKey);
        if (access_token == null ||"".equals(access_token)){
            logger.error("请求菜单时access_token为空："+weiXinBaseInfo);
            return ResultUtil.error(ResultEnum.ERROR_WEIXINBASEINFO);
        }
        /* 封装url，参数，Json数据*/
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
        Map<String,String> map = new HashMap<>(1);
        map.put("access_token",access_token);
        String jsonData = JSON.toJSONString(menu);
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
        WeiXinRedirectUrl weiXinRedirectUrl = JSONObject.parseObject(stateInfo,WeiXinRedirectUrl.class);
        String redirectUrl = weiXinRedirectUrl.getRedirectUrl();

        /* 获取openId等信息 */
        WeiXinUserInfoOpenId weiXinUserInfoOpenId = getUserOpenId(weiXinRedirectUrl.getAppId(),weiXinRedirectUrl.getAppSecret(),weiXinWebAuthorize.getCode());

        /* 获取公众号的access_token信息 */
        String accessToken = weiXinUtil.getAccessToken(weiXinRedirectUrl.getAppId(),weiXinRedirectUrl.getAppSecret(), accessTokenKey);

        /* 获取用户信息 */
        WeiXinUserInfo weiXinUserInfo = weiXinUtil.getUserInfo(accessToken, weiXinUserInfoOpenId.getOpenId());

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
    * @Description: 网页授权时获取用户的openId
    * @Date: Created in 15:56
    * @param: 
    */
    public WeiXinUserInfoOpenId getUserOpenId(String appId, String appSecret, String code) throws Exception {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        /* 封装请求的参数*/
        Map<String,String>  map = new HashMap<>(4);
        map.put("appid",appId);
        map.put("secret",appSecret);
        map.put("code",code);
        map.put("grant_type","authorization_code");
        /* 获取请求结果 */
        HttpClientResult httpClientResult = HttpClientUtil.doGet(url,map);
        WeiXinUserInfoOpenId weiXinUserInfoOpenId = JSONObject.parseObject(httpClientResult.getContent(), WeiXinUserInfoOpenId.class);
        return weiXinUserInfoOpenId;
    }

    /**
     * @param weiXinRedirectUrl
     * @Author: 公众号：Newtol
     * @Description: 网页授权时，设置跳转的链接，即为返回获取code时，使用的state
     * @Date: Created in 13:16
     * @param:
     */
    @Override
    public Result setRedirect(WeiXinRedirectUrl weiXinRedirectUrl) throws NoSuchAlgorithmException {
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
        WeiXinRedirectUrl weiXinRedirectUrl = JSONObject.parseObject(stateInfo,WeiXinRedirectUrl.class);
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weiXinRedirectUrl.getAppId()+"&redirect_uri="+getWebAuthorizeUrl+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
        httpServletUtil.redirectToUrl(response,url);
    }

}
