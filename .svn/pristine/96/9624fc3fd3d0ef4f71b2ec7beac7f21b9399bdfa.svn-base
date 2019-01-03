package cn.newcapec.city.smart.gateway.config.appuser;

import cn.newcapec.city.smart.common.model.BaseBin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * APP用户参数配置
 * Created by es on 2018/6/27.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "application.user")
public class AppUserConfig extends BaseBin {

    @Autowired
    private Token token;
    @Autowired
    private Template template;
    //余额不足提醒的阀值，单位：元
    private double balanceRemind;
    //用户查询列表每页的大小
    private int pageSize;

    //APP用户的余额
    @Getter
    @Setter
    @ConfigurationProperties(prefix = "application.user.template")
    public static class Template extends BaseBin {
        //申请退费提醒模板
        private String balance;
        //充值提醒模板
        private String recharge;
        //消费提醒模板
        private String consume;
    }

    //APP用户的token
    @Getter
    @Setter
    @ConfigurationProperties(prefix = "application.user.token")
    public static class Token extends BaseBin {
        //用户授权令牌的失效时间，单位小时
        private int failTime;
        //用户token的超时时间，单位秒 2*60；
        //token超过该时间认为无效，防止第三方截获token发起请求
        private int timeout;
        //url token的超时时间，单位秒 2*60*60
        //html页面的token授权时效较长(默认2个小时)，并且不验证重复
        private int urlTimeout;
    }

}
