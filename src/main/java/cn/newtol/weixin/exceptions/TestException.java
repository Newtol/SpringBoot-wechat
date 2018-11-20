package cn.newtol.weixin.exceptions;

import cn.newtol.weixin.Enum.ResultEnum;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 13:35 2018/11/10
 */

public class TestException extends RuntimeException {
    private Integer error_code;

    public TestException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.error_code = resultEnum.getError_code();
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }
}
