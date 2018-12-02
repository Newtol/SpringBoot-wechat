package cn.newtol.weixin.domain;

import lombok.Data;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 13:13 2018/11/10
 */

@Data
public class Result<T> {
    /**
     * 错误码
     */
    private Integer errorCode;

    /**
     *  错误提示信息
     */
    private String message;

    /**
     * 数据块
     */
    private T data;
}
