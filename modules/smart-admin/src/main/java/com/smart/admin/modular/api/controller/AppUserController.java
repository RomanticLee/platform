package com.smart.admin.modular.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smart.admin.modular.api.util.JsonResult;
import com.smart.admin.modular.api.util.RedisUtil;
import com.smart.admin.modular.system.model.AppUser;
import com.smart.admin.modular.system.service.IAppUserService;
import com.smart.admin.modular.system.service.IDeptService;
import com.smart.admin.modular.system.service.IUserService;
import cn.stylefeng.roses.core.util.MD5Util;

@Validated
@RestController
@RequestMapping("app/appUser")
public class AppUserController {

	private Logger log = LoggerFactory.getLogger(AppUserController.class);

	@Autowired
	private RedisUtil redis;
	@Autowired
	private IAppUserService appUserService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDeptService deptService;
	
	/**
	 * 用户登录接口
	 * @param mobile  电话号
	 * @param password  密码
	 * @return 返回token
	 * (不拦截token)
	 */
	@PostMapping("login")
	public JsonResult login(
			@NotBlank(message="账号不能为空") String mobile,
			@NotBlank(message="密码不能为空") String password){
		//执行登录业务
		return appUserService.login(mobile,password);
	}
	
	/**
	 * 用户退出登录
	 * @param token
	 * @return
	 */
	@PostMapping("logout")
	public JsonResult logout(String token){
		redis.del(token);
		return new JsonResult();
	}
	
	/**
	 * 修改密码
	 * @param token
	 * @param oldPass 旧密码
	 * @param newPass 新密码
	 * @param checkPass 校验
	 * @return
	 */
	@PostMapping("changePassword")
	public JsonResult changePassword(
			@RequestParam String token,
			@NotBlank(message="原密码不能为空") String oldPass,
			@NotBlank(message="新密码不能为空") String newPass,
			@NotBlank(message="校验密码不能为空") String checkPass){

		//校验参数是否合法
		if(!newPass.equals(checkPass)){
			return new JsonResult(JsonResult.FALSE,"密码不能为空且两次输入密码不一致");
		}
		//执行修改密码业务，处理结果
		return appUserService.changePassword(token,oldPass,newPass);
	}
	
	/**
	 * 注册---获得短信验证码
	 * @param mobile 手机号
	 * (不拦截token)
	 * */
	@PostMapping("getRegisterCode")
	public JsonResult getRegisterCode(
			@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
		    @NotBlank(message = "手机号码不能为空") String mobile){
		
		return appUserService.getMessageCode(mobile,"register");
	}
	
	/**
	 * 忘记密码--获得短信验证码
	 * @param mobile 手机号
	 * @param name 姓名
	 * @return
	 */
	@PostMapping("getForgetPasswordCode")
	public JsonResult getForgetPasswordCode(
			@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
			@NotBlank(message = "手机号码不能为空") String mobile,
			@NotBlank(message = "姓名不能为空") String name){
		
		return appUserService.getForgetPasswordCode(mobile,name);
	}
	
	/**
	 * 忘记密码
	 * @param mobile 手机号
	 * @param name 姓名
	 * @param code 验证码
	 * @param password 新密码
	 * @return
	 */
	@PostMapping("forgetPossword")
	public JsonResult forgetPossword(
			@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
			@NotBlank(message = "手机号码不能为空") String mobile,
			@NotBlank(message = "姓名不能为空") String name,
			@NotBlank(message = "验证码不能为空") String code,
			@NotBlank(message = "密码不能为空") String password){
		
		return appUserService.forgetPossword(mobile,name,code,password);
	}
	
	
	/**
	 * 更换手机号--获得短信验证码
	 * @param mobile 手机号
	 * @param password 密码
	 * @param token
	 * @return
	 */
	@PostMapping("getResetMobileCode")
	public JsonResult getResetMobileCode(
			String token,
			@NotBlank(message = "密码不能为空") String password,
			@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
			@NotBlank(message = "手机号码不能为空") String mobile){
		
		return appUserService.getResetMobileCode(token,password,mobile);
	}
	
	/**
	 * 更换手机号
	 * @param token
	 * @param password 密码
	 * @param mobile 手机号
	 * @param code 验证码
	 * @return
	 */
	@PostMapping("resetMobile")
	public JsonResult ResetMobile(
			String token,
			@NotBlank(message = "密码不能为空") String password,
			@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
			@NotBlank(message = "手机号码不能为空") String mobile,
			@NotBlank(message = "验证码不能为空") String code){
		
		return appUserService.resetMobile(token,password,mobile,code);
	}
	
	/**
	 * 注册页面----获得区域id，simplename
	 * @param  //pid为0获得所有省份信息，传入省份id获得市的信息，传入市id获得区信息
	 * @return
	 */
	@PostMapping("getRegion/{pid}")
	public JsonResult getRegion(
			@PathVariable("pid") String pid){
		List<Map<String,Object>> list = deptService.getRegion(pid);
		return new JsonResult(list);
	}
	
	/**
	 * 注册页面--根据区域id管理员
	 * @param //regionName 区域名称
	 * @return
	 */
	@PostMapping("getAdmin/{deptId}")
	public JsonResult getAdminByRegion(
			@PathVariable("deptId") String deptId){
		
		List<Map<String,Object>> list = userService.getAdminByRegion(deptId);
		return new JsonResult(list);
	}
	
	/**
	 * 注册
	 * @param mobile 电话号
	 * @param name 姓名
	 * @param password 密码
	 * @param company 公司地址
	 * @param administratorId 区域管理员id
	 * @param salesman 业务员姓名
	 * @param code 验证码
	 * @return
	 */
	@PostMapping("register")
	public JsonResult register(
			@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
			@NotBlank(message = "手机号码不能为空") String mobile,
			@Length(min=1,max=20,message="姓名不能为空，且长度最大为20") String name,
			@NotBlank(message = "密码不能为空") String password,
			@Length(max=255,message="公司地址太长")String company,
			@NotBlank(message = "区域管理员不能为空") Integer administratorId,
			@Length(min=1,max=20,message="业务员姓名不能为空，且长度最大为20")String salesman,
			@NotBlank(message = "验证码不能为空") String code){
		
		AppUser appUser = new AppUser(name, MD5Util.encrypt(password), mobile, company, administratorId, salesman);	
		
		return null;
	}
	
	
	@PostMapping("test")
	public void ma(
			@RequestParam(defaultValue="0")double a,
			@RequestParam(defaultValue="0")double b){
		System.out.println(a);
		System.out.println(b);
		System.out.println(a+b);
	}
	
	
	
	
	
}
