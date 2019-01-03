package cn.newcapec.city.smart.validation.authorization;

import cn.newcapec.city.smart.validation.validator.BankCardnoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 检查银行卡号是否合法
 * Created by es on 2018/7/4.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BankCardnoValidator.class)
@Documented
public @interface BankCardno {

    String message() default "{cn.newcapec.constraints.bankcardno}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
