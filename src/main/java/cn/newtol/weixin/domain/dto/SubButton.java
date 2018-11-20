package cn.newtol.weixin.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: 公众号：Newtol
 * @Description: 二级菜单
 * @Date: Created in 0:17 2018/11/11
 */
@Data
@Component
public class SubButton {
    private BaseButton[] sub_button;
    private String name;
}
