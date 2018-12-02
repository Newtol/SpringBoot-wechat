package cn.newtol.weixin.domain.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 11:01 2018/11/29
 */

@Data
@Component
public  abstract class BaseWeiXinMessage {
    /**
     * 接收者Id
     */
    @JacksonXmlProperty(localName = "ToUserName")
    private String toUserName;
    /**
     * 发送者Id
     */
    @NotEmpty(message = "发送者不能为空")
    @JacksonXmlProperty(localName = "FromUserName")
    private String fromUserName;
    /**
     * 消息创建时间
     */
    @JacksonXmlProperty(localName = "CreateTime")
    private Long createTime;
    /**
     * 消息类型
     * 文本为：text; 图片为：image; 语音为：voice;
     *
     */
    @NotEmpty(message = "文章类型不能为空")
    @JacksonXmlProperty(localName = "MsgType")
    private String msgType;
}
