package cn.newtol.weixin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 11:05 2018/11/13
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600)
public class SessionConfig {


}