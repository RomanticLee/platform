package cn.newcapec.city.smart.gateway.model;

import cn.newcapec.city.smart.common.model.BaseBin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 接口响应的数据对象
 * Created by es on 2018/3/13.
 */
@Getter
@Setter
@ApiModel(value = "通用返回对象", description = "通用返回对象")
public class Msg extends BaseBin {

    @ApiModelProperty("系统状态码")
    private String code;
    @ApiModelProperty("状态描述")
    private String msg;
    @ApiModelProperty("业务数据")
    private Object data;

}
