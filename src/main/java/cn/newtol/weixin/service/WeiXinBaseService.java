package cn.newtol.weixin.service;

import cn.newtol.weixin.domain.BaseUserInfo;
import cn.newtol.weixin.domain.HttpClientResult;
import cn.newtol.weixin.domain.dto.WeiXinBaseInfo;
import cn.newtol.weixin.domain.dto.WeiXinWebAuthorize;
import cn.newtol.weixin.domain.dto.WeiXinVerify;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Map;

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
    Map<String,String> getAccessToken(WeiXinBaseInfo weiXinBaseInfo) throws Exception;
    /**
    * @Author: 公众号：Newtol
    * @Description: 设置微信公众号的后台
    * @Date: Created in 19:49
    * @param:
    */
    HttpClientResult setWeiXinMenu(WeiXinBaseInfo weiXinBaseInfo) throws Exception;

    /**
    * @Author: 公众号：Newtol
    * @Description: 微信获取网页授权，返回参数为用户基本信息
    * @Date: Created in 22:15
    * @param:
    */
//    BaseUserInfo getWebAuthorize(WeiXinWebAuthorize weiXinWebAuthorize);

}
