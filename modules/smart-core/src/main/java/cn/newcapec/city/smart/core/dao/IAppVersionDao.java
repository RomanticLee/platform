package cn.newcapec.city.smart.core.dao;

import cn.newcapec.city.smart.core.dao.base.BaseDao;
import cn.newcapec.city.smart.core.entity.AppVersionEntity;
import org.springframework.stereotype.Repository;

/**
 * APP版本控制数据操作接口
 * Created by es on 2018/10/11.
 */
@Repository
public interface IAppVersionDao extends BaseDao<AppVersionEntity, String> {

    /**
     * 获得最新的应用版本信息
     *
     * @param appType     应用类型
     * @param appName     应用名称
     * @param channelCode 渠道号
     * @return
     */
    AppVersionEntity findByAppTypeAndAppNameAndChannelCode(String appType, String appName, String channelCode);

}
