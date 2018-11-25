package cn.newtol.weixin.utils;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 19:26 2018/11/25
 */
@Service
public class HttpServletUtil {

    /**
    * @Author: 公众号：Newtol
    * @Description: 设置session
    * @Date: Created in 19:34
    * @param:
    */
    public void setSession(Map map, HttpServletRequest request){
        if (map != null) {
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                request.getSession().setAttribute(entry.getKey(), entry.getValue());
            }
        }
    }

    public void redirectToUrl(HttpServletResponse response,String url){
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", url);
    }
}
