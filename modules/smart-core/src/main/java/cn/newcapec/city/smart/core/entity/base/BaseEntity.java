package cn.newcapec.city.smart.core.entity.base;

import cn.newcapec.city.smart.common.model.BaseBin;
import cn.newcapec.city.smart.common.model.IEntityModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 数据库实体类基类
 * Created by es on 2018/3/13.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity extends BaseBin implements IEntityModel {
    @Id
    @Column(name = "id", nullable = false, length = 32)
    //生成器名称，uuid生成类
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDHexGenerator")
    //指定生成器名称
    @GeneratedValue(generator = "uuid2")
    protected String id;
}
