package cn.newtol.weixin.utils;

import cn.newtol.weixin.Enum.ResultEnum;
import cn.newtol.weixin.domain.Result;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 13:14 2018/11/10
 */
public class ResultUtil {
    public static Result success(Object object){
        Result result = new Result();
        result.setError_code(ResultEnum.SUCCESS.getError_code());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    public static Result error(Integer code ,String msg){
        Result result = new Result();
        result.setError_code(code);
        result.setMessage(msg);
        return result;
    }

    public static  Result error(ResultEnum resultEnum){
        Result result = new Result();
        result.setError_code(resultEnum.getError_code());
        result.setMessage(resultEnum.getMessage());
        return result;
    }
}
