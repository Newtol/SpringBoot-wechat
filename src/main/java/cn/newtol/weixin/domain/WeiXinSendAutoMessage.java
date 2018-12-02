package cn.newtol.weixin.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 19:26 2018/11/27
 */
@Data
@Component
@JacksonXmlRootElement(localName = "xml")
public class WeiXinSendAutoMessage {
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

    /**
     * 回复的关键词
     */
    @JacksonXmlText
    private String key;

    /**
     * 多媒体Id,回复图片、语音时使用
     */

    @Data
    public static class MediaId {
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName = "MediaId")
        private String mediaId;
        public MediaId(String mediaId) {
            this.mediaId = mediaId;
        }
        public MediaId() {
        }
    }

    /**
     * 自动回复图片消息时使用
     */
    @JacksonXmlProperty(localName = "Image")
    private MediaId image;

    /**
     * 回复语音消息时使用
     */
    @JacksonXmlProperty(localName = "Voice")
    private MediaId voice;

    /**
     * 回复文本消息
     */
    @JacksonXmlProperty(localName = "Content")
    private String Content;


    /**
     * 回复视频消息
     */
    @Data
    public static class VideoContent {
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName = "MediaId")
        private String mediaId;
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName = "Title")
        private String title;
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName = "Description")
        private String description;
    }
    @JacksonXmlProperty(localName = "Video")
    private VideoContent video;

    /**
     * 回复音乐消息
     */

    @Data
    public static class MusicContent{
        public MusicContent() {
        }

        /**
         * 音乐标题
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName = "Title")
        private String title;
        /**
         * 音乐描述
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName = "Description")
        private String description	;
        /**
         * 音乐链接
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName = "MusicUrl")
        private String musicUrl	;
        /**
         * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName = "HQMusicUrl")
        private String hqMusicUrl;
        /**
         * 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName ="ThumbMediaId" )
        private String thumbMediaId;
    }

    @JacksonXmlProperty(localName = "Music")
    private MusicContent music;


    /**
     * 回复图文消息
     */
    @Data
    public static class PicAndContent {
        public PicAndContent() {
        }

        /**
         * 图文消息个数；当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName ="ArticleCount" )
        private Integer articleCount;
        /**
         * 图文消息信息，注意，如果图文数超过限制，则将只发限制内的条数
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName ="Articles" )
        private String articles;
        /**
         * 图文消息标题
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName ="Title" )
        private String title;
        /**
         * 图文消息描述
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName ="Description" )
        private String description	;
        /**
         * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName ="PicUrl" )
        private String picUrl;
        /**
         * 图文跳转链接
         */
        @JacksonXmlText(value =false)
        @JacksonXmlProperty(localName ="Url" )
        private String url;
    }

    @JacksonXmlProperty(localName = "Articles")
    private String articles;

}
