package cn.newcapec.city.smart.gateway.authorization.resolvers;

import cn.newcapec.city.smart.gateway.authorization.annotation.CurrentUser;
import cn.newcapec.city.smart.gateway.exception.IRet;
import cn.newcapec.city.smart.gateway.exception.SysException;
import cn.newcapec.city.smart.gateway.utils.Constants;
import cn.newcapec.city.smart.model.AppUserModel;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是AppUserDto并且有CurrentUser注解则支持
        if (parameter.getParameterType().isAssignableFrom(AppUserModel.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取出鉴权时存入的登录用户
        AppUserModel appUser = (AppUserModel) webRequest.getAttribute(Constants.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        if (appUser == null) {
            //认证失败，返回认证错误信息
            throw new SysException(IRet.Code.INVALIDITY_AUTHORIZATION);
        }
        return appUser;
    }
}
