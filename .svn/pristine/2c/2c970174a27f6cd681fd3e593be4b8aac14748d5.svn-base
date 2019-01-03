package cn.newcapec.city.smart.gateway.authorization.model;

import cn.newcapec.city.smart.common.model.BaseModel;
import cn.newcapec.city.smart.model.AppUserModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 */
@Getter
@Setter
@RequiredArgsConstructor
public class TokenModel extends BaseModel {

    //随机生成的uuid
    @NonNull
    private String token;
    //token包含的用户信息
    AppUserModel appUser;
    //token的生成时间，时间戳，单位毫秒
    private long ctdt;

}
