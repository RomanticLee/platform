package com.smart.admin.modular.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.smart.admin.modular.api.util.RedisUtil;

@Component
public class LoginAppInterceptor implements HandlerInterceptor{
	
	private final static Logger log = LoggerFactory.getLogger(LoginAppInterceptor.class);

	@Autowired
	private RedisUtil redis;
	@Value("${AppUser.redis.loginTime}")
	private long loginTime;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 处理器实际执行之前
		String token = request.getParameter("token");
		log.info("拦截token："+token);
		if(token!=null && redis.get(token)!=null && token.length()>32){
			log.info("token有效被放行");
			redis.expire(token, loginTime);
			return true;
		}else {
			response.setCharacterEncoding("UTF-8");
			response.getWriter()
					.write("{\"state\": \"1\",\"message\": \"用户没有登录\",\"data\": \"\"}");
			response.getWriter().flush();
			response.getWriter().close();
			log.info("token无效被拦截");
			return false;
		}
	}
	
}