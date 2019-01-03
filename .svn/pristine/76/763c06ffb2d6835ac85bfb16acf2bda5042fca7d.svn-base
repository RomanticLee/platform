package cn.newcapec.city.smart.validation.validator;

import cn.newcapec.city.smart.common.utils.ValidationUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cn.newcapec.city.smart.validation.authorization.Number;

/**
 * 检查是否是数字的验证器
 * Created by es on 2018/7/5.
 */
public class NumberValidator implements ConstraintValidator<Number, String> {

    private int length, min, max;
    //是否允许0开头
    private boolean beginZero;

    @Override
    public void initialize(Number constraintAnnotation) {
        this.length = constraintAnnotation.length();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.beginZero = constraintAnnotation.beginZero();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        //先判断是否是全数字
        boolean isNumeric = ValidationUtils.checkNumber(s);
        if (!isNumeric) {
            return false;
        }
        if (!beginZero) { //不允许0开头
            //判断是否是以0开头
            if (s.startsWith("0")) {
                return false;
            }
        }
        //判断长度是否满足要求
        int sLen = s.length();
        if (this.length > 0) {
            if (sLen != this.length) {
                return false;
            }
        } else {
            if (sLen < this.min || sLen > this.max) {
                return false;
            }
        }
        return true;
    }
}
