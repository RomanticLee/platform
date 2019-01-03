package cn.newcapec.city.smart.api;

import cn.newcapec.city.smart.req.AppVerReq;
import cn.newcapec.city.smart.resp.AppVerResp;

import java.util.Date;

/**
 * APP业务处理接口
 * Created by es on 2018/3/28.
 */
public interface IAppService {

    /**
     * 获得当前系统时间(时间戳，单位毫秒)
     *
     * @return
     */
    long nowTime();

    /**
     * 获得当前系统时间
     *
     * @return
     */
    Date nowDate();

    /**
     * 获得最新的应用版本
     *
     * @param req 请求参数
     * @return
     */
    AppVerResp newVersion(AppVerReq req);

}
