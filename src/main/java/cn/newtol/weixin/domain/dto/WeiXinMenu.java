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
public class WeiXinMenu {
    /** 菜单 */
    private SubButton[] button;


    @Data
    public static class SubButton {
        /** 二级菜单 */
        private Button[] sub_button;
        /** 二级菜单的名字 */
        private String name;
    }

    @Data
    public static class Button {
        /** 按钮的种类 */
        private String type;
        /** 按钮的名字 */
        private String name;
        /** 当类型为view类型时，跳转的链接*/
        private String url;
        /** 当类型为小程序时，小程序的appId*/
        private String appId;
        /** 当类型为小程序时使用*/
        private String pagePath;
        /** 当类型为click时，发送给后台的key*/
        private String key;

    }



}
