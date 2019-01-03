package com.smart.admin.modular.system.service;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.baomidou.mybatisplus.service.IService;

import com.smart.admin.modular.api.util.JsonResult;
import com.smart.admin.modular.system.model.AppUser;
import com.smart.admin.modular.system.model.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sesame
 * @since 2018-11-15
 */
public interface IAppUserService extends IService<AppUser> {

	//用户登录
	JsonResult login(String mobile, String password);
	//修改密码
	JsonResult changePassword(String token, String oldPass, String newPass);
	//发送短信验证码
	JsonResult getMessageCode(String mobile, String suffix);
	//忘记密码--发送短信验证码
	JsonResult getForgetPasswordCode(String mobile, String name);
	//更换手机号--发送短信验证码
	JsonResult getResetMobileCode(String token, String password, String mobile);
	//忘记密码
	JsonResult forgetPossword(String mobile, String name, String code, String password);
	//更换手机号
	JsonResult resetMobile(String token, String password, String mobile, String code);

}
