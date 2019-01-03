package cn.newcapec.city.smart.gateway;

import cn.newcapec.city.smart.gateway.config.appuser.AppUserConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableDubbo
@SpringBootApplication(scanBasePackages = "cn.newcapec.city.smart")
@EnableConfigurationProperties({
        AppUserConfig.class, AppUserConfig.Template.class, AppUserConfig.Token.class
})
public class SmartGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartGatewayApplication.class, args);
    }

}

