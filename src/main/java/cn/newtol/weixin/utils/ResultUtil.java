package cn.newtol.weixin.utils;

import cn.newtol.weixin.Enum.ResultEnum;
import cn.newtol.weixin.domain.Result;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 13:14 2018/11/10
 */
public class ResultUtil {
    /**
    * @Author: 公众号：Newtol
    * @Description: 请求成功，返回带参数结果
    * @Date: Created in 21:16
    * @param:
    */
    public static Result success(Object object){
        Result result = new Result();
        result.setErrorCode(ResultEnum.SUCCESS.getErrorCode());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }
    /**
    * @Author: 公众号：Newtol
    * @Description: 请求成功，返回不带参数结果
    * @Date: Created in 21:16
    * @param:
    */
    public static Result success(){
        return success(null);
    }

    /**
    * @Author: 公众号：Newtol
    * @Description:  访问错误，自定义返回参数
    * @Date: Created in 21:17
    * @param:
    */
    public static Result error(Integer code ,String msg){
        Result result = new Result();
        result.setErrorCode(code);
        result.setMessage(msg);
        return result;
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 访问错误，使用预定义返回参数
    * @Date: Created in 21:17
    * @param:
    */
    public static  Result error(ResultEnum resultEnum){
        Result result = new Result();
        result.setErrorCode(resultEnum.getErrorCode());
        result.setMessage(resultEnum.getMessage());
        return result;
    }
}
