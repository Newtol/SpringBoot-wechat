package cn.newtol.weixin.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: 公众号：Newtol
 * @Description: 基础Button
 * @Date: Created in 0:18 2018/11/11
 */
@Data
@Component
public class BaseButton {
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


