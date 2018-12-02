package cn.newtol.weixin.utils;

import cn.newtol.weixin.Enum.ResultEnum;
import cn.newtol.weixin.domain.WeiXinUserInfo;
import cn.newtol.weixin.domain.HttpClientResult;
import cn.newtol.weixin.domain.dto.WeiXinVerify;
import cn.newtol.weixin.exceptions.TestException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 公众号：Newtol
 * @Description:    微信后台工具类
 * @Date: Created in 17:46 2018/11/24
 */
@Component
@Service
public class WeiXinUtil {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinUtil.class);
    @Resource
    private  RedisUtil redisUtil;

    /**
    * @Author: 公众号：Newtol
    * @Description: 获取access_token
    * @Date: Created in 19:00
    * @param:
    */
    public synchronized String getAccessToken(String appId,String appSecret,String key) throws Exception {
        String accessToken = getAccessTokenFromDatabase(appId,key);
        if (accessToken == null || "".equals(accessToken)) {
            accessToken = getAccessTokenFromWeiXin(appId,appSecret,key);
        }
        return accessToken;
    }

    /**
     * @Author: 公众号：Newtol
     * @Description: 从Redis获取AccessToken
     * @Date: Created in 22:40
     * @param:
     */
    private  String getAccessTokenFromDatabase(String appId,String key) throws SQLException, ClassNotFoundException {
        String accessToken = redisUtil.getString(key+appId);
        return accessToken;
    }

    /**
     * @Author: 公众号：Newtol
     * @Description: 从微信服务器重新获取AccessToken并存入Redis
     * @Date: Created in 22:39
     * @param:
     */
    private  String getAccessTokenFromWeiXin(String appId,String appSecret,String key) throws Exception,TestException {
        String accessToken = null;

        /*准备发送GET请求*/
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("grant_type","client_credential");
        hashMap.put("appid",appId);
        hashMap.put("secret",appSecret);

        /*获取返回结果*/
        HttpClientResult httpClientResult = HttpClientUtil.doGet(url,hashMap);
        JSONObject jsonObject = JSONObject.parseObject(httpClientResult.getContent());
        accessToken = jsonObject.getString("access_token");

        /*获取失败就抛出异常*/
        if (accessToken == null || "".equals(accessToken)){
            logger.info("请求AccessToken错误："+"===appid："+appId+"===appSecret："+appSecret);
            throw new TestException(ResultEnum.ERROR_WEIXINBASEINFO);
        }

        /*因为Access_Token过期时间为7200所以将Redis中的AccessToken过期时间提前50s过期,将key设置为配置文件中的key+appId的形式*/
        redisUtil.setString(key+appId,accessToken,7150, TimeUnit.SECONDS);
        return accessToken;
    }
    /**
    * @Author: 公众号：Newtol
    * @Description: 接入微信服务器
    * @Date: Created in 18:22
    * @param:  weiXinVerify
    */
    public String joinWeiXin(WeiXinVerify weiXinVerify,String token) {
        String list[] = {token,weiXinVerify.getTimestamp(),weiXinVerify.getNonce()};
        //字典排序
        Arrays.sort(list);
        //拼接字符串
        StringBuilder builder = new StringBuilder();
        for (String str : list) {
            builder.append(str);
        }
        //sha1加密
        String hashcode = EncryptUtil.sha1(builder.toString());
        //不区分大小写差异情况下比较是否相同
        if (hashcode.equalsIgnoreCase(weiXinVerify.getSignature())) {
            //响应输出
            return weiXinVerify.getEchostr();
        }
        logger.error("微信接入失败");
        return null;
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 获取微信用户信息
    * @Date: Created in 19:19
    * @param:
    */

    public WeiXinUserInfo getUserInfo(String access_token, String openId) throws Exception {
        String url  = "https://api.weixin.qq.com/cgi-bin/user/info";
        Map<String,String> map = new HashMap<>();
        map.put("access_token",access_token);
        map.put("openid",openId);
        map.put("lang","zh_CN");
        HttpClientResult httpClientResult = HttpClientUtil.doGet(url,map);
        System.out.println(httpClientResult.getContent());
        WeiXinUserInfo weiXinUserInfo = JSONObject.parseObject(httpClientResult.getContent(), WeiXinUserInfo.class);
        if (weiXinUserInfo ==null || "".equals(weiXinUserInfo)){
            logger.info("获取用户信息失败："+httpClientResult.getContent()+"==="+"openid"+openId+"==="+"access_token"+access_token);
        }
        return weiXinUserInfo;
    }
}
