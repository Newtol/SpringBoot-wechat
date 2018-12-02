package cn.newtol.weixin.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;


import javax.validation.constraints.NotEmpty;


/**
 * @Author: 公众号：Newtol
 * @Description: 微信公众号的基本信息：appId和appSecret
 * @Date: Created in 13:03 2018/11/12
 */
@Data
@Component
public class WeiXinConfigInfo {
    /** 公众号的appId*/
    @NotEmpty(message = "appId不能为空" )
    public String appId;

    /** 公众号的appSecret*/
    @NotEmpty(message = "appSecret不能为空")
    public String appSecret;
}
