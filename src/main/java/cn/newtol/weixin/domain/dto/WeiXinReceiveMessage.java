package cn.newtol.weixin.domain.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 22:44 2018/11/25
 */
@Data
@JacksonXmlRootElement(localName = "xml")
public class WeiXinReceiveMessage extends BaseWeiXinMessage{
    /**
     * 文本消息内容
     */
    @JacksonXmlProperty(localName = "Content")
    private String content;
    /**
     * 消息ID
     */
    @JacksonXmlProperty(localName = "MsgId")
    private Long msgId;
    /**
     * 图片链接
     */
    @JacksonXmlProperty(localName = "PicUrl")
    private String picUrl;
    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    @JacksonXmlProperty(localName ="MediaId" )
    private String mediaId;
    /**
     * 语音格式
     */
    @JacksonXmlProperty(localName = "Format")
    private String format;
    /**
     * 语音识别结果
     */
    @JacksonXmlProperty(localName = "Recognition")
    private String recognition;
    /**
     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    @JacksonXmlProperty(localName = "ThumbMediaId")
    private String thumbMediaId;
    /**
     * 地理位置维度
     */
    @JacksonXmlProperty(localName = "Location_X")
    private String location_X;
    /**
     * 地理位置经度
     */
    @JacksonXmlProperty(localName = "Location_Y")
    private String location_Y;
    /**
     * 地图缩放大小
     */
    @JacksonXmlProperty(localName = "Scale")
    private String scale;
    /**
     * 地理位置信息
     */
    @JacksonXmlProperty(localName = "Label")
    private String label;
    /**
     * 消息标题
     */
    @JacksonXmlProperty(localName = "Title")
    private String title;

    /**
     * 消息描述
     */
    @JacksonXmlProperty(localName = "Description")
    private String description;

    /**
     * 消息链接
     */
    @JacksonXmlProperty(localName = "Url")
    private String url;
}
