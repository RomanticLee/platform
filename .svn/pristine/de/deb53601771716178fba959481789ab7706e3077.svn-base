package cn.newcapec.city.smart.validation.validator;

import cn.newcapec.city.smart.validation.authorization.CheckCase;
import cn.newcapec.city.smart.validation.model.CaseMode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 检查大小写的验证器
 * Created by es on 2018/7/4.
 */
public class CheckCaseValidator implements ConstraintValidator<CheckCase, String> {

    private CaseMode caseMode;

    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        if (caseMode == CaseMode.UPPER) {
            return object.equals(object.toUpperCase());
        } else {
            return object.equals(object.toLowerCase());
        }
    }
}
