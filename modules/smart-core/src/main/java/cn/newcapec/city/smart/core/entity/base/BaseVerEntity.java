package cn.newcapec.city.smart.core.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 数据库实体类带版本号
 * Created by es on 2018/3/16.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseVerEntity extends BaseEntity {
    @Version
    @Column(name = "ver")
    protected Integer ver;
}
