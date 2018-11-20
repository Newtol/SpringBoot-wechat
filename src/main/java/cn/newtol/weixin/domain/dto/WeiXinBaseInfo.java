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
public class WeiXinBaseInfo{
    @NotEmpty(message = "密码不能为空" )
    public String appId;
    @NotEmpty(message = "密码不能为空")
    public String appSecret;
}
