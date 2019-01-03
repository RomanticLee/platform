package cn.newcapec.city.smart.core.service.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 服务业务基类
 * Created by es on 2018/3/13.
 */
@Slf4j
public abstract class BaseService {

    @Value("${application.user.pageSize}")
    protected int pageSize; //查询数据每页的大小

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
            log.error("[BaseService.getParams]参数编码转换异常，request.name=" + name
                    + ", remoteAddr=" + request.getRemoteAddr() + ", error=" + e.getMessage());
        }
        return result;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
