package cn.newcapec.city.smart.validation.authorization;

import cn.newcapec.city.smart.validation.validator.NumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 检查输入是否是数字
 * Created by es on 2018/7/4.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NumberValidator.class)
@Documented
public @interface Number {

    String message() default "{cn.newcapec.constraints.number}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //数字最小长度限制(只在length不验证时有效)
    int min() default 0;

    //数字最大长度限制(只在length不验证是有效)
    int max() default 2147483647;

    //数字的长度，默认0，不限制；
    // 如果大于0，则不验证min和max
    int length() default 0;

    //是否允许0开头，默认true，允许0开头
    boolean beginZero() default true;

}
