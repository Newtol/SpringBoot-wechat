package cn.newtol.weixin.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 13:13 2018/11/24
 */
@Data
@Component
public class WeiXinRedirectUrl extends WeiXinBaseInfo{

    /** 获取网页授权时，需要跳转的链接 */
    @NotEmpty(message = "跳转链接不能为空")
    private String redirectUrl;
}
