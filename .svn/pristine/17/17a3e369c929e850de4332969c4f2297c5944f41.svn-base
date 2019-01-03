package cn.newcapec.city.smart.gateway.authorization.model;

import cn.newcapec.city.smart.common.model.BaseBin;
import lombok.Getter;
import lombok.Setter;

/**
 * jwt对象
 * Created by es on 2018/7/6.
 */
@Getter
@Setter
public class JWTModel extends BaseBin {

    //用户的授权token
    private String token;
    //随机生成的uuid，防止重放攻击和重复提交
    private String uuid;
    //jwt生成时间，时间戳，单位毫秒
    private long time;
    //生成jwt的设备ID
    private String devieid;
    //jwt的签名
    private String sign;

}
