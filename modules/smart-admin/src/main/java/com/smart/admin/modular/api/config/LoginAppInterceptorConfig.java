package com.smart.admin.modular.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.smart.admin.modular.api.interceptor.LoginAppInterceptor;
@Configuration
public class LoginAppInterceptorConfig implements WebMvcConfigurer{

	@Bean
	public LoginAppInterceptor getLoginAppInterceptor(){
		return new LoginAppInterceptor();
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		String [] addPathPatterns = {
				"/app/**"
		};
		String [] excludePathPatterns = {
				//登录
				"/app/appUser/login",           
				//注册发送短信验证码
				"/app/appUser/getRegisterCode",
				//忘记密码发送短信验证码
				"/app/appUser/getForgetPasswordCode",
				//忘记密码
				"/app/appUser/forgetPossword",
				//获得区域管理员
				"/app/appUser/getAdmin/**",
				//获得区域
				"/app/appUser/getRegion/**",
				//注册
				"/app/appUser/register",
				"/app/appUser/test"
		};
        registry.addInterceptor(getLoginAppInterceptor())
        	.addPathPatterns(addPathPatterns)
        	.excludePathPatterns(excludePathPatterns);
    }
}
