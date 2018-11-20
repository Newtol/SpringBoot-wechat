package cn.newtol.weixin.utils;
import cn.newtol.weixin.domain.HttpClientResult;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 21:20 2018/11/10
 */
@Component
@Service
public class AccessToken {
    private static final Logger logger = LoggerFactory.getLogger(AccessToken.class);
    @Resource
    private  RedisUtil redisUtil;

    /* 从配置文件中获取redis中AccessToken存入的key */
    @Value("${weiXin.accessTokenKey}")
    private String key;

    public String getAccessToken(String appId, String appSecret) throws Exception {
        String accessToken = getAccessTokenFromDatabase(appId);
        if (accessToken == null || "".equals(accessToken)) {
            synchronized (AccessToken.class) {
                if (getAccessTokenFromDatabase(appId) == null ) {
                    accessToken = getAccessTokenFromWeiXin(appId,appSecret);
                }
            }
        }
        return accessToken;
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 从Redis获取AccessToken
    * @Date: Created in 22:40
    * @param:
    */
    private  String getAccessTokenFromDatabase(String appId) throws SQLException, ClassNotFoundException {
        String accessToken = redisUtil.getString(key+appId);
        return accessToken;
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 从微信服务器重新获取AccessToken并存入Redis
    * @Date: Created in 22:39
    * @param:
    */
    private  String getAccessTokenFromWeiXin(String appId,String appSecret) throws Exception {
        String accessToken = null;

        /*准备发送GET请求*/
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("grant_type","client_credential");
        hashMap.put("appid",appId);
        hashMap.put("secret",appSecret);
        HttpClientResult httpClientResult = HttpClientUtil.doGet(url,hashMap);

        JSONObject jsonObject = JSONObject.parseObject(httpClientResult.getContent());
        try {
            accessToken = (String) jsonObject.get("access_token");

            /*因为Access_Token过期时间为7200，所以将Redis中的AccessToken过期时间提前50s过期*/
            redisUtil.setString(key+appId,accessToken,7150, TimeUnit.SECONDS);
        }catch (Exception e){
            logger.error("AccessToken获取错误");
        }finally {
            return accessToken;
        }
    }
}
