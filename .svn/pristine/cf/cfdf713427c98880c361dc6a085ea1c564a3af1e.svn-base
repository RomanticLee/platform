package cn.newcapec.city.smart.gateway.controller;

import cn.newcapec.city.smart.api.IAppService;
import cn.newcapec.city.smart.gateway.authorization.annotation.AppAuthorization;
import cn.newcapec.city.smart.gateway.controller.base.SmartBaseController;
import cn.newcapec.city.smart.req.AppVerReq;
import cn.newcapec.city.smart.resp.AppVerResp;
import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * APP控制器
 * Created by es on 2018/12/26.
 */
@RestController
@RequestMapping(value = "app", produces = "application/json; charset=utf-8")
@Api(description = "APP相关接口")
public class AppController extends SmartBaseController {

    @Reference
    private IAppService appService;

    /**
     * 获得当前时间(时间戳，单位毫秒)
     *
     * @return
     * @throws RuntimeException
     */
    @ApiOperation("获得当前时间")
    @ApiResponses({
            @ApiResponse(code = 0, message = "当前时间，时间戳，单位毫秒", response = Long.class)
    })
    @RequestMapping(value = "nowTime", method = {RequestMethod.GET, RequestMethod.POST})
    public Long nowTime() {
        long nowTime = appService.nowTime();
        return nowTime;
    }

    //获得应用版本号
//    @AppAuthorization
    @RequestMapping(value = "version", method = {RequestMethod.GET, RequestMethod.POST})
    public AppVerResp version(@Validated AppVerReq appVer, BindingResult result) {
        this.bindingError(result);
        AppVerResp resp = appService.newVersion(appVer);
        if (resp == null) {
            return null;
        } else {
            return resp;
        }
    }

}
