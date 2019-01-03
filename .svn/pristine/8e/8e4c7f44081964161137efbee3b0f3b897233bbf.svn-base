package cn.newcapec.city.smart.gateway.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 用于预处理请求
 * Created by es on 2018/3/13.
 */
@Slf4j
@ControllerAdvice
public class DataRequestBodyAdvice implements RequestBodyAdvice {

    private static final String CHARSET_UTF8 = "UTF-8";

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    //可以对请求进行拦截，可以在这做权限验证、token校验等等
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        try {
            return new MyHttpInputMessage(httpInputMessage);
        } catch (Exception e) {
            log.debug("请求数据解析失败, " + e.getMessage(), e);
            return httpInputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    @Override
    public Object handleEmptyBody(@Nullable Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    class MyHttpInputMessage implements HttpInputMessage {

        private HttpHeaders headers;
        private InputStream body;

        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body = inputMessage.getBody();
            String params = IOUtils.toString(this.body, CHARSET_UTF8);
            log.debug("请求参数: " + params);
            this.body = IOUtils.toInputStream(params, CHARSET_UTF8);
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

}
