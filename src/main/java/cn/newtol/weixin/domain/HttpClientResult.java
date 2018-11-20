package cn.newtol.weixin.domain;

import lombok.Data;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 16:50 2018/11/10
 */

@Data
public class HttpClientResult{
    private static final long serialVersionUID = 2168152194164783950L;
    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;


    public HttpClientResult(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public HttpClientResult(int code) {
        this.code = code;
    }
}
