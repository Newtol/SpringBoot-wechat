package cn.newtol.weixin.Enum;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 13:46 2018/11/10
 */

@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public enum ResultEnum {
    /**
     * 系统未知错误
     */
    UNKONW_ERROR(-1,"未知错误："),
     /**
      * 请求成功
      */
    SUCCESS(0,"success"),
    /**
     * 请求缺少参数
     */
    LACK_PARAMETER(1,"缺少参数"),
    /**
     * 微信公众号的appId或者appSecret错误
     */
    ERROR_WEIXINBASEINFO(2,"appId或者appSecret错误");

    /**
     * 错误码
     */
    private Integer errorCode;
    /**
     * 错误提示信息
     */
    private String message;

    ResultEnum(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }


    public String getMessage() {
        return message;
    }

}
