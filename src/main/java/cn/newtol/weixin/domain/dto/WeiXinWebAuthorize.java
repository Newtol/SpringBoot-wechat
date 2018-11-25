package cn.newtol.weixin.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: 公众号：Newtol
 * @Description:    获取网页授权时，需要的code和state
 * @Date: Created in 21:13 2018/11/22
 */
@Component
@Data
public class WeiXinWebAuthorize {
    @NotEmpty(message = "code不能为空")
    private String code;
    @NotEmpty(message = "state不能为空")
    private String state;
}
