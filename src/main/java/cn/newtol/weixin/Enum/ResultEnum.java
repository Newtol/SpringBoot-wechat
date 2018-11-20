package cn.newtol.weixin.Enum;

import lombok.Data;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 13:46 2018/11/10
 */

public enum ResultEnum {
    UNKONW_ERROR(-1,"未知错误："),
    SUCCESS(0,"success"),
    LACK_REDIRECTURL(1,"缺少redirect_url"),
    LACK_APPID(2,"缺少appId或者appSecret");

    /* 错误码 */
    private Integer error_code;
    /* 错误提示信息 */
    private String message;

    ResultEnum(Integer error_code, String message) {
        this.error_code = error_code;
        this.message = message;
    }

    public Integer getError_code() {
        return error_code;
    }


    public String getMessage() {
        return message;
    }

}
