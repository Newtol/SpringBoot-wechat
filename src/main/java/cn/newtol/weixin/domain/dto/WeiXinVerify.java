package cn.newtol.weixin.domain.dto;

import lombok.Data;


import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;


/**
 * @Author: 公众号：Newtol
 * @Description:    接入微信服务器时，微信服务器传过来的参数
 * @Date: Created in 19:08 2018/11/10
 */
@Data
@Component
public class WeiXinVerify {
    @NotEmpty(message = "微信接入缺少参数")
    private String signature;
    @NotEmpty(message = "微信接入缺少参数")
    private String timestamp;
    @NotEmpty(message = "微信接入缺少参数")
    private String nonce;
    @NotEmpty(message = "微信接入缺少参数")
    private String echostr;
}
