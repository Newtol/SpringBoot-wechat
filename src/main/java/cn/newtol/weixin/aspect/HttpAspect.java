//package cn.newtol.weixin.aspect;
//
////import org.aspectj.lang.JoinPoint;
////import org.aspectj.lang.annotation.Aspect;
////import org.aspectj.lang.annotation.Before;
////import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.net.URL;
//
///**
// * @Author: 公众号：Newtol
// * @Description:
// * @Date: Created in 15:35 2018/11/10
// */
//@Aspect
//@Component
//public class HttpAspect {
//    @Pointcut("execution(public * cn.newtol.weixin.controller.*.*(..))")
//    public void log() {
//
//    }
//
//    @Before("log()")
//    public void forward(JoinPoint joinPoint) throws IOException {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        HttpServletResponse response = attributes.getResponse();
//        System.out.println("11");
////        HttpSession session = request.getSession();
////        if (session.getAttribute("loginname") == null) {
////            response.sendRedirect("www.baidu.com");
////
////        }
//        StringBuffer url = request.getRequestURL();
//        System.out.println(url);
//        response.setContentType("text/html;charset=UTF-8");
//        // 要重定向的新位置
//        String site = new String("http://www.baidu.com");
//        response.setStatus(response.SC_MOVED_TEMPORARILY);
//        response.setHeader("Location", site);
//    }
//}
