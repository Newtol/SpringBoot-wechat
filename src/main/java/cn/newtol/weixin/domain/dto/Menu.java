package cn.newtol.weixin.domain.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: 公众号：Newtol
 * @Description:  菜单
 * @Date: Created in 0:17 2018/11/11
 */
@Data
@Component
@ConfigurationProperties(prefix= "menu")
public class Menu {
    private SubButton[] button;
}
