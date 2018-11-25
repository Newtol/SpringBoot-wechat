package cn.newtol.weixin.repository;

import cn.newtol.weixin.domain.WeiXinUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 16:31 2018/11/24
 */
public interface BaseUserInfoRepository extends JpaRepository<WeiXinUserInfo,Integer> {
    /**
    * @Author: 公众号：Newtol
    * @Description:  通过openId查找用户
    * @Date: Created in 20:56
    * @param:
    */
    WeiXinUserInfo findBaseUserInfoByOpenId(String openId);
}
