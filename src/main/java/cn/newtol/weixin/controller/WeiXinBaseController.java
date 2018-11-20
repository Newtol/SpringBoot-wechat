package cn.newtol.weixin.controller;

import cn.newtol.weixin.Enum.ResultEnum;
import cn.newtol.weixin.domain.HttpClientResult;
import cn.newtol.weixin.domain.Result;
import cn.newtol.weixin.domain.dto.WeiXinBaseInfo;
import cn.newtol.weixin.domain.dto.WeiXinVerify;
import cn.newtol.weixin.service.WeiXinBaseService;
import cn.newtol.weixin.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;


/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 18:51 2018/11/10
 */
@RestController
public class WeiXinBaseController {

    private static  final Logger logger = LoggerFactory.getLogger(WeiXinBaseController.class);

    @Resource
    private WeiXinBaseService weiXinBaseService;


    /**
     * @Author: 公众号：Newtol
     * @Description: 接入微信服务器
     * @Date: Created in 20:47
     * @param: WeiXinBase 微信服务器传来的四个参数：signature,timestamp,nonce,echostr
     */

    @GetMapping("/")
    public String joinWeiXin(@Valid WeiXinVerify weiXinVerify, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            logger.error("微信接入缺少参数");
        }
        return weiXinBaseService.joinWeiXin(weiXinVerify);
    }

    /**
     * @Author: 公众号：Newtol
     * @Description: AccessToken获取
     * @Date: Created in 21:13
     * @param:
     */

    @GetMapping("/accessToken")
    public Result getAccessToken(@Valid WeiXinBaseInfo weiXinBaseInfo,BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResultUtil.error(ResultEnum.LACK_APPID);
        }
        Map<String,String> map = weiXinBaseService.getAccessToken(weiXinBaseInfo);
        return ResultUtil.success(map);
    }

    /**
     * @Author: 公众号：Newtol
     * @Description: 微信公众号菜单的设置
     * @Date: Created in 19:58
     * @param:
     */

    @GetMapping("/menu")
    @ResponseBody
    public Result setMenu(@Valid WeiXinBaseInfo weiXinBaseInfo,BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResultUtil.error(ResultEnum.LACK_APPID);
        }
        System.out.println(weiXinBaseInfo.toString());
        HttpClientResult httpClientResult = weiXinBaseService.setWeiXinMenu(weiXinBaseInfo);
        return ResultUtil.success(httpClientResult);
    }

//    @ResponseBody
//    @GetMapping(value = "/getSession")
//    public Object getSession (HttpServletRequest request){
//        Map<String, Object> map = new HashMap<>();
//        map.put("sessionId", request.getSession().getId());
//        map.put("message", request.getSession().getAttribute("message"));
//        return map;
//    }


//    @GetMapping("/userInfo")
//    public Result getUserInfo(@Valid WeiXinWebAuthorize weiXinWebAuthorize, BindingResult bindingResult){
//        if (bindingResult.hasErrors()){
//
//        }
//    }


}