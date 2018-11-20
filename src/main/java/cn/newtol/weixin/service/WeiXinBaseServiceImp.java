package cn.newtol.weixin.service;

import cn.newtol.weixin.domain.HttpClientResult;
import cn.newtol.weixin.domain.dto.Menu;
import cn.newtol.weixin.domain.dto.WeiXinBaseInfo;
import cn.newtol.weixin.domain.dto.WeiXinVerify;
import cn.newtol.weixin.utils.AccessToken;
import cn.newtol.weixin.utils.EncryptUtil;
import cn.newtol.weixin.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 19:23 2018/11/10
 */
@Service
@Component
public class WeiXinBaseServiceImp implements WeiXinBaseService {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinBaseServiceImp.class);

    @Resource
    private AccessToken accessToken;

    /* 微信服务器配置的token */
    @Value("${weiXin.token}")
    private String token;
    /* 微信公众号菜单*/
    @Resource
    Menu menu;


    @Override
    public String joinWeiXin(WeiXinVerify weiXinVerify) {
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

    @Override
    public Map<String, String> getAccessToken(WeiXinBaseInfo weiXinBaseInfo) throws Exception {
        String access_token =  accessToken.getAccessToken(weiXinBaseInfo.getAppId(),weiXinBaseInfo.getAppSecret());
        Map<String,String> map = new HashMap<>();
        map.put("access_token",access_token);
        return map;
    }

    /**
     * @param weiXinBaseInfo
     * @Author: 公众号：Newtol
     * @Description: 设置微信公众号的菜单
     * @Date: Created in 19:49
     * @param:
     */

    @Override
    public HttpClientResult setWeiXinMenu(WeiXinBaseInfo weiXinBaseInfo) throws Exception {
        String access_token = accessToken.getAccessToken(weiXinBaseInfo.getAppId(),weiXinBaseInfo.getAppSecret());
        if (access_token == null ||"".equals(access_token)){
            logger.error("请求菜单时access_token为空："+weiXinBaseInfo);
        }

        /* 封装url，参数，Json数据*/
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
        Map<String,String> map = new HashMap<>();
        map.put("access_token",access_token);
        String jsonData = JSON.toJSONString(menu);
        HttpClientResult httpClientResult = HttpClientUtil.doPostForJson(url,map,jsonData);
        logger.info("微信公众号菜单设置请求的JSON参数："+jsonData);
        return httpClientResult;
    }

    /**
     * @param weiXinWebAuthorize
     * @Author: 公众号：Newtol
     * @Description: 微信获取网页授权，返回参数为用户基本信息
     * @Date: Created in 22:15
     * @param:
     */
//    @Override
//    public BaseUserInfo getWebAuthorize(WeiXinWebAuthorize weiXinWebAuthorize) {
//        String url =
//    }
}
