package cn.newcapec.city.smart.validation.authorization;

import cn.newcapec.city.smart.validation.validator.CertnoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 检查身份证号是否合法
 * Created by es on 2018/7/4.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CertnoValidator.class)
@Documented
public @interface Certno {

    String message() default "{cn.newcapec.constraints.certno}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
