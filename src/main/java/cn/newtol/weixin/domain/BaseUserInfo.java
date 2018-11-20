package cn.newtol.weixin.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 22:09 2018/11/12
 */
@Component
@Data
public class BaseUserInfo {
    private String openId;
    private String nickName;
    private String city;
    private Integer sex;
}
