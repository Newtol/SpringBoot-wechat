package cn.newtol.weixin.controller;

import cn.newtol.weixin.domain.Result;
import cn.newtol.weixin.domain.dto.WeiXinBaseInfo;
import cn.newtol.weixin.domain.dto.WeiXinRedirectUrl;
import cn.newtol.weixin.domain.dto.WeiXinWebAuthorize;
import cn.newtol.weixin.domain.dto.WeiXinVerify;
import cn.newtol.weixin.service.WeiXinBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;


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
    public String joinWeiXin(@Valid WeiXinVerify weiXinVerify){
        return weiXinBaseService.joinWeiXin(weiXinVerify);
    }

    /**
     * @Author: 公众号：Newtol
     * @Description: AccessToken获取
     * @Date: Created in 21:13
     * @param:
     */

    @GetMapping("/accessToken")
    public Result getAccessToken(@Valid WeiXinBaseInfo weiXinBaseInfo) throws Exception {
        return  weiXinBaseService.getAccessToken(weiXinBaseInfo);
    }

    /**
     * @Author: 公众号：Newtol
     * @Description: 微信公众号菜单的设置
     * @Date: Created in 19:58
     * @param:
     */
    @GetMapping("/menu")
    public Result setMenu(@Valid WeiXinBaseInfo weiXinBaseInfo) throws Exception {
        return weiXinBaseService.setWeiXinMenu(weiXinBaseInfo);
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 获取网页授权
    * @Date: Created in 20:58
    * @param:
    */
    @GetMapping("/getWebAuthorize")
    public void WeiXinWebAuthorize(@Valid WeiXinWebAuthorize weiXinWebAuthorize, HttpServletRequest request, HttpServletResponse response) throws Exception {
        weiXinBaseService.weiXinWebAuthorize(weiXinWebAuthorize,request,response);
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 获取跳转微信网页授权获取链接时需要使用的state
    * @Date: Created in 13:07
    * @param:
    */
    @GetMapping("/setRedirectUrl")
    public Result setRedirectUrl(@Valid WeiXinRedirectUrl weiXinRedirectUrl) throws NoSuchAlgorithmException {
        return weiXinBaseService.setRedirect(weiXinRedirectUrl);
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 跳转到微信获取网页授权的链接地址
    * @Date: Created in 19:21
    * @param:
    */
    @GetMapping("/redirectUrl")
    public void redirectToWeiXin(@RequestParam String state, HttpServletResponse response){
        weiXinBaseService.redirectToWeiXin(response,state);
    }

//
//    @GetMapping("/setSession")
//    public Result setSession(@RequestParam String name, HttpServletRequest request){
//        request.getSession().setAttribute("User", name);
//        System.out.println("用户信息："+request.getSession().getAttribute("User"));
//        return ResultUtil.success(name);
//    }
}