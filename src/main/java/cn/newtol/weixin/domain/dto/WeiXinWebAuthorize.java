package cn.newtol.weixin.domain.dto;

import lombok.Data;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;


/**
 * @Author: 公众号：Newtol
 * @Description:    获取微信用户信息需要的参数
 * @Date: Created in 21:35 2018/11/12
 */
@Component
@Data
public class WeiXinWebAuthorize extends WeiXinBaseInfo {
    private String redirectUrl;
}
