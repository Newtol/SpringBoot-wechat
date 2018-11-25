package cn.newtol.weixin.exceptions;

import cn.newtol.weixin.Enum.ResultEnum;
import lombok.Data;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 13:35 2018/11/10
 */
@Data
public class TestException extends RuntimeException {
    private Integer errorCode;

    public TestException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.errorCode = resultEnum.getErrorCode();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
