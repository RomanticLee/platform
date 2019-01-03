package cn.newcapec.city.smart.core.entity.base;

import cn.newcapec.city.smart.common.model.BaseBin;
import cn.newcapec.city.smart.common.model.IEntityModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 没有自动主键ID的数据实体父类
 * Created by es on 2018/3/20.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseNoneAutoIdEntity extends BaseBin implements IEntityModel {
    @Id
    @Column(name = "id", nullable = false, length = 32)
    protected String id;
    @Version
    @Column(name = "ver")
    protected Integer ver;

}
