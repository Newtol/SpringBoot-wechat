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
    private String type;
    private String name;
    private String url;
    private String appId;
    private String pagePath;
    private String key;
}


