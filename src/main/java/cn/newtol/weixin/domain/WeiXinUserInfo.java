package cn.newtol.weixin.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.io.Serializable;


/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 22:09 2018/11/12
 */
@Component
@Data
@Entity
@Table(name = "weixin_userinfo")
public class WeiXinUserInfo extends BaseDO implements Serializable {
    @Column(nullable = false,unique = true)
    private String openId;
    @Column(nullable = false)
    private String nickName;
    @Column
    private String city;
    @Column
    private Integer sex;
    @Column(nullable = false)
    private String headImgUrl;
    @Column
    private String province;

}
