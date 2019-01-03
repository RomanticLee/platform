package cn.newcapec.city.smart.gateway.spring.interceptor;

import cn.newcapec.city.smart.gateway.utils.Constants;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器，判断此次请求的request参数
 * Created by es on 2018/7/10.
 */
@Component
public class RequestHolder extends HandlerInterceptorAdapter {

    private static final ThreadLocal<String> merchantCodeLocal = new ThreadLocal<String>();
    private static final ThreadLocal<String> deviceidLocal = new ThreadLocal<String>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //从reqeust中得到merchantcode
        String merchantcode = (String) request.getAttribute(Constants.MERCHANTCODE);
        if (!StringUtils.isEmpty(merchantcode)) {
            merchantCodeLocal.set(merchantcode);
        }
        //从request中获得deviceid
        String deviceid = (String) request.getAttribute(Constants.DEVICEID);
        if (!StringUtils.isEmpty(deviceid)) {
            deviceidLocal.set(deviceid);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        merchantCodeLocal.remove();
        deviceidLocal.remove();
    }

    /**
     * 获得商户编号
     *
     * @return
     */
    public static String merchantCode() {
        return merchantCodeLocal.get();
    }

    /**
     * 获得设备编号
     *
     * @return
     */
    public static String deviceId() {
        return deviceidLocal.get();
    }

}
