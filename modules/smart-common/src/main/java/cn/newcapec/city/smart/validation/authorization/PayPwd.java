package cn.newcapec.city.smart.validation.authorization;

import cn.newcapec.city.smart.validation.validator.PayPwdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 检查支付密码是否合法
 * Created by es on 2018/7/4.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PayPwdValidator.class)
@Documented
public @interface PayPwd {

    String message() default "{cn.newcapec.constraints.paypwd}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
