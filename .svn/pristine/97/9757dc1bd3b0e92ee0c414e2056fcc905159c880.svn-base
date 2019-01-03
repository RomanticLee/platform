package cn.newcapec.city.smart.validation.validator;

import cn.newcapec.city.smart.common.utils.ValidationUtils;
import cn.newcapec.city.smart.validation.authorization.NickName;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 检查昵称是否合法的验证器
 * Created by es on 2018/7/5.
 */
public class NickNameValidator implements ConstraintValidator<NickName, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return ValidationUtils.checkNickname(s);
    }
}
