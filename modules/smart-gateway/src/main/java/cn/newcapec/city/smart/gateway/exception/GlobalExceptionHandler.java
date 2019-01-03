package cn.newcapec.city.smart.gateway.exception;

import cn.newcapec.city.smart.gateway.model.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 * Created by es on 2018/3/13.
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 所有异常报错
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public void allExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        //打印错误日志
        Msg msg = new Msg();
        //系统异常
        IRet.Code code;
        if (exception instanceof SysException) {
            //系统异常
            code = ((SysException) exception).getCode();
            code.setMessage(exception.getMessage());
        } else if (exception instanceof IllegalArgumentException) {
            //参数解析异常
            code = IRet.Code.ILLEGAL_ARGUMENT;
            log.error(exception.getMessage(), exception);
        } else if (exception instanceof BindException) {
            //数据绑定异常
            code = IRet.Code.BINDERROR;
            log.error(exception.getMessage(), exception);
        } else if (exception instanceof MethodArgumentNotValidException) {
            //方法参数无效
            code = IRet.Code.METHOD_ARGUMENT_NOTVALID;
            log.error(exception.getMessage(), exception);
        } else if (exception instanceof ServletException) {
            //servlet异常
            code = IRet.Code.FAIL;
            log.error(exception.getMessage(), exception);
        } else if (exception instanceof NoHandlerFoundException) {
            //访问的接口不存在
            code = IRet.Code.NOT_FOUND;
            code.setMessage("接口[" + request.getRequestURI() + "]不存在");
            log.error(exception.getMessage(), exception);
        } else {
            //其他异常
            code = IRet.Code.UNKONE;
            log.error(exception.getMessage(), exception);
        }
        msg.setCode(code.getCode());
        msg.setMsg(code.getMessage());
        try {
            response.getWriter().write(msg.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
