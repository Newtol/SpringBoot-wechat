package cn.newtol.weixin.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 14:42 2018/11/24
 */
@Data
@Component
public class WeiXinUserInfoOpenId {

    /** snsapi_userinfo为scope时，获取用户信息使用的access_token */
    private String accessToken;
    /** 用户的openId */
    private String openId;
    /** 用来刷新用户access_token使用 */
    private String refreshToken;
}
