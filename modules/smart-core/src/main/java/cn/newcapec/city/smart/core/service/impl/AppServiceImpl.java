package cn.newcapec.city.smart.core.service.impl;

import cn.newcapec.city.smart.api.IAppService;
import cn.newcapec.city.smart.core.dao.IAppVersionDao;
import cn.newcapec.city.smart.core.entity.AppVersionEntity;
import cn.newcapec.city.smart.req.AppVerReq;
import cn.newcapec.city.smart.resp.AppVerResp;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * APP业务处理
 * Created by es on 2018/12/26.
 */
@Service
@Component
public class AppServiceImpl implements IAppService {

    @Autowired
    private IAppVersionDao appVersionDao;

    @Override
    public long nowTime() {
        return nowDate().getTime();
    }

    @Override
    public Date nowDate() {
        return new Date();
    }

    //获得最新的应用版本
    @Override
    public AppVerResp newVersion(AppVerReq req) {
        //获得应用版本
        AppVersionEntity entity = appVersionDao.findByAppTypeAndAppNameAndChannelCode(req.getAppType(), req.getAppName(), req.getChannelCode());
        if(entity == null){
            return null;
        }
        AppVerResp resp = entity.toBean(AppVerResp.class);
        return resp;
    }
}
