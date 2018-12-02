package cn.newtol.weixin.controller;

import cn.newtol.weixin.domain.Result;
import cn.newtol.weixin.domain.WeiXinSendAutoMessage;
import cn.newtol.weixin.domain.dto.*;
import cn.newtol.weixin.service.WeiXinBaseService;
import cn.newtol.weixin.utils.ResultUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Controller;
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
@Controller
public class WeiXinBaseController {

    @Resource
    private WeiXinBaseService weiXinBaseService;


    /**
     * @Author: 公众号：Newtol
     * @Description: 接入微信服务器
     * @Date: Created in 20:47
     * @param: WeiXinBase 微信服务器传来的四个参数：signature,timestamp,nonce,echostr
     */

    @GetMapping(value = "/")
    @ResponseBody
    public String joinWeiXin(@Valid WeiXinVerify weiXinVerify){
        return weiXinBaseService.joinWeiXin(weiXinVerify);
    }
    /**
    * @Author: 公众号：Newtol
    * @Description: 接收微信服务器消息
    * @Date: Created in 22:41
    * @param:
    */

    @PostMapping(value = "/",consumes = "text/xml; charset=utf-8", produces =  "text/xml; charset=utf-8")
    @ResponseBody
    public String getWeiXinMessage(@RequestBody WeiXinReceiveMessage weiXinReceiveMessage) throws JsonProcessingException {
        System.out.println(weiXinReceiveMessage.toString());

        return weiXinBaseService.sendAutoMessage(weiXinReceiveMessage);
    }
    /**
     * @Author: 公众号：Newtol
     * @Description: AccessToken获取
     * @Date: Created in 21:13
     * @param:
     */

    @GetMapping(value = "/accessToken")
    @ResponseBody
    public Result getAccessToken(@Valid WeiXinConfigInfo weiXinConfigInfo) throws Exception {
        return  weiXinBaseService.getAccessToken(weiXinConfigInfo);
    }

    /**
     * @Author: 公众号：Newtol
     * @Description: 微信公众号菜单的设置
     * @Date: Created in 19:58
     * @param:
     */
    @GetMapping(value = "/menu")
    @ResponseBody
    public Result setMenu(@Valid WeiXinConfigInfo weiXinConfigInfo) throws Exception {
        return weiXinBaseService.setWeiXinMenu(weiXinConfigInfo);
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 获取网页授权
    * @Date: Created in 20:58
    * @param:
    */
    @GetMapping(value = "/getWebAuthorize")
    @ResponseBody
    public void getWeiXinWebAuthorize(@Valid WeiXinWebAuthorize weiXinWebAuthorize, HttpServletRequest request, HttpServletResponse response) throws Exception {
        weiXinBaseService.weiXinWebAuthorize(weiXinWebAuthorize,request,response);
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 获取跳转微信网页授权获取链接时需要使用的state
    * @Date: Created in 13:07
    * @param:
    */
    @GetMapping(value = "/setRedirectUrl")
    @ResponseBody
    public Result setRedirectUrl(@Valid RedirectUrlWeiXinConfig weiXinRedirectUrl) throws NoSuchAlgorithmException {
        return weiXinBaseService.setRedirect(weiXinRedirectUrl);
    }

    /**
    * @Author: 公众号：Newtol
    * @Description: 跳转到微信获取网页授权的链接地址
    * @Date: Created in 19:21
    * @param:
    */
    @GetMapping(value = "/redirectUrl")
    @ResponseBody
    public void redirectToWeiXin(@RequestParam String state, HttpServletResponse response){
        weiXinBaseService.redirectToWeiXin(response,state);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = { "text/xml" }, produces = { "application/xml" })
    @ResponseBody
    public Result setSession(@RequestBody WeiXinSendAutoMessage weiXinSendAutoMessage) throws JsonProcessingException {
        System.out.println(weiXinSendAutoMessage.toString());
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        //字段为null，自动忽略，不再序列化
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String xml = xmlMapper.writeValueAsString(weiXinSendAutoMessage);
        System.out.println(xml);
        return ResultUtil.success();
    }


    /**
    * @Author: 公众号：Newtol
    * @Description: 设置自动回复内容
    * @Date: Created in 23:57
    * @param:
    */
    @PostMapping(value = "/setAutoMessage")
    @ResponseBody
    public  Result setAutoMessage(@RequestBody WeiXinSendAutoMessage weiXinSendAutoMessage){
        return ResultUtil.success(weiXinBaseService.setAutoMessage(weiXinSendAutoMessage));
    }
}