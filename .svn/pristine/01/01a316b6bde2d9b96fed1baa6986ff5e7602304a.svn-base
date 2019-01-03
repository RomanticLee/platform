package cn.newcapec.city.smart.gateway.controller.base;

import cn.newcapec.city.smart.common.utils.CollectionUtils;
import cn.newcapec.city.smart.common.utils.security.MD5Util;
import cn.newcapec.city.smart.gateway.exception.SysException;
import cn.newcapec.city.smart.gateway.spring.interceptor.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 控制器基类
 * Created by es on 2018/3/13.
 */
@Slf4j
public abstract class BaseController {

    public String getParams(String name) {
        HttpServletRequest request = getRequest();
        return getParams(request, name);
    }

    public String getParams(ServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        Object obj = request.getAttribute(name);
        if (obj == null) {
            obj = request.getParameter(name);
            if (obj == null) {
                return null;
            }
        }
        String result = "";
        try {
            result = new String(obj.toString().getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            log.error("[BaseController.getParams]参数编码转换异常，request.name=" + name
                    + ", remoteAddr=" + request.getRemoteAddr() + ", error=" + e.getMessage());
        }
        return result;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
