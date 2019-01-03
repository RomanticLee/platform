package cn.newcapec.city.smart.validation.authorization;

import cn.newcapec.city.smart.validation.validator.NickNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 检查昵称是否合法
 * Created by es on 2018/7/4.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NickNameValidator.class)
@Documented
public @interface NickName {

    String message() default "{cn.newcapec.constraints.nickName}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
