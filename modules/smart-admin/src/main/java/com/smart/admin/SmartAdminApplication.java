
package com.smart.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.stylefeng.roses.core.config.WebAutoConfiguration;

@SpringBootApplication(exclude = WebAutoConfiguration.class)
public class SmartAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAdminApplication.class, args);
    }

}

