package cn.newtol.weixin.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: 公众号：Newtol
 * @Description:
 * @Date: Created in 1:16 2018/11/25
 */
@Data
@Component
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @CreatedDate
    @Column(updatable = false)
    public Date createTime ;
    @LastModifiedDate
    @Column
    public Date updateTime ;
}
