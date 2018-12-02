package cn.newtol.weixin.service;

import cn.newtol.weixin.domain.Result;
import cn.newtol.weixin.domain.WeiXinSendAutoMessage;
import cn.newtol.weixin.domain.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 19:21 2018/11/10
 */
@Service
public interface WeiXinBaseService {

    /**
    * @Author: 公众号：Newtol
    * @Description: 接入微信服务器
    * @Date: Created in 19:38
    * @param:
    */
    String joinWeiXin(WeiXinVerify weiXinVerify);

    /**
    * @Author: 公众号：Newtol
    * @Description: 获取access_token
    * @Date: Created in 19:38
    * @param:
    */
    Result getAccessToken(WeiXinConfigInfo weiXinConfigInfo) throws Exception;

    /**
    * @Author: 公众号：Newtol
    * @Description: 设置微信公众号的后台
    * @Date: Created in 19:49
    * @param:
    */
    Result setWeiXinMenu(WeiXinConfigInfo weiXinConfigInfo) throws Exception;

    /**
    * @Author: 公众号：Newtol
    * @Description: 微信获取网页授权
    * @Date: Created in 22:15
    * @param:
    */
    void weiXinWebAuthorize(WeiXinWebAuthorize weiXinWebAuthorize, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
    * @Author: 公众号：Newtol
    * @Description: 网页授权时，获取code时，使用的state
    * @Date: Created in 13:16
    * @param:
    */
    Result setRedirect(RedirectUrlWeiXinConfig weiXinRedirectUrl) throws NoSuchAlgorithmException;

    /**
    * @Author: 公众号：Newtol
    * @Description: 跳转到微信网页授权链接
    * @Date: Created in 19:51
    * @param:
    */
    void redirectToWeiXin(HttpServletResponse response,String state);

    /**
    * @Author: 公众号：Newtol
    * @Description: 微信自动回复关键词
    * @Date: Created in 22:44
    * @param:
    */
    String sendAutoMessage(WeiXinReceiveMessage weiXinReceiveMessage) throws JsonProcessingException;

    /**
    * @Author: 公众号：Newtol
    * @Description: 设置自动回复内容
    * @Date: Created in 23:58
    * @param:
    */
    Result setAutoMessage(WeiXinSendAutoMessage weiXinSendAutoMessage);
}
