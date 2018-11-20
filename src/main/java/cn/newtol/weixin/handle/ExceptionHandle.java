package cn.newtol.weixin.handle;

import cn.newtol.weixin.Enum.ResultEnum;
import cn.newtol.weixin.domain.Result;
import cn.newtol.weixin.exceptions.TestException;
import cn.newtol.weixin.service.WeiXinBaseServiceImp;
import cn.newtol.weixin.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 13:55 2018/11/10
 */

@ControllerAdvice
public class ExceptionHandle {


    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        if(e instanceof TestException){
            TestException testException = (TestException) e;
            return ResultUtil.error(testException.getError_code(),testException.getMessage());
        }
        else {
            logger.error("系统错误："+e);
            return ResultUtil.error(ResultEnum.UNKONW_ERROR.getError_code(),ResultEnum.UNKONW_ERROR.getMessage()+e.getMessage());
        }

    }


}
