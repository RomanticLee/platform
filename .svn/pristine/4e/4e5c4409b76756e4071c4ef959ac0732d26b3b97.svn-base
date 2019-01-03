package cn.newcapec.city.smart.gateway.config;

import cn.newcapec.city.smart.gateway.authorization.interceptor.AuthorizationInterceptor;
import cn.newcapec.city.smart.gateway.authorization.resolvers.CurrentUserMethodArgumentResolver;
import cn.newcapec.city.smart.gateway.spring.interceptor.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * 配置类，增加自定义拦截器和解析器
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    private static final String SWAGGER_SCAN_BASE_PACKAGE = "cn.newcapec.city.smart.gateway.controller";
    private static final String VERSION = "1.0.0";
    private static final String TITLE = "广西农信智慧城市APP平台接口";
    private static final String DESCRIPTION = "本接口文档描述广西农信智慧城市的APP调用二维码平台的接口。";

    @Value("${application.swagger.enable}")
    private boolean enableSwagger; //是否启用swapper
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;
    @Autowired
    private RequestHolder requestHolder;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
        registry.addInterceptor(requestHolder);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //静态资源访问
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        //swagger访问
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/docApi/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/docApi/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/docApi/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/docApi/swagger-resources", "/swagger-resources");
    }

    //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .apiInfo(apiInfo())
                .select()
                //控制暴露出去的路径下的实例
                //如果某个接口不想暴露,可以使用以下注解
                //@ApiIgnore 这样,该接口就不会暴露在 swagger2 的页面下
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title(TITLE)
                //创建人
                .contact(new Contact("WEIXING", "http://www.newcapec.cn", "weixing@newcapec.net"))
                //版本号
                .version(VERSION)
                //描述
                .description(DESCRIPTION)
                .build();
    }

}
