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
    /** 二级菜单 */
    private BaseButton[] sub_button;
    /** 二级菜单的名字 */
    private String name;
}
