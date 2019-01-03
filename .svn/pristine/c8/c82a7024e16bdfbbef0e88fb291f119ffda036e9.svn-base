package cn.newcapec.city.smart.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 获取应用版本的参数
 * Created by es on 2018/12/26.
 */
@Setter
@Getter
public class AppVerReq extends BaseReq {
    //应用类型
    @NotBlank(message = "应用类型不能为空")
    private String appType;
    //应用名称
    @NotBlank(message = "应用名称不能为空")
    private String appName;
    //渠道号
    @NotBlank(message = "渠道号不能为空")
    private String channelCode;
}
