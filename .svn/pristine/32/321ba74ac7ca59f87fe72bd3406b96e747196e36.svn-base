package com.smart.admin.modular.system.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.smart.admin.modular.api.util.JsonResult;
import com.smart.admin.modular.api.util.RandNumUtil;
import com.smart.admin.modular.api.util.RedisUtil;
import com.smart.admin.modular.api.util.SmsSendResult;
import com.smart.admin.modular.api.util.TokenUtil;
import com.smart.admin.modular.system.dao.AppUserMapper;
import com.smart.admin.modular.system.model.AppUser;
import com.smart.admin.modular.system.model.User;
import com.smart.admin.modular.system.service.IAppUserService;
import cn.stylefeng.roses.core.util.MD5Util;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sesame
 * @since 2018-11-15
 */
@Service
@Transactional
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

	private Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);
	
	@Autowired
	private RedisUtil redis;
	@Autowired
	private AppUserMapper appUserMapper;
	
	//登录有效时间
	@Value("${AppUser.redis.loginTime}")
	private long loginTime;
	//验证码间隔时间
	@Value("${AppUser.redis.intervalTime}")
	private long intervalTime;
	//验证码有效时间
	@Value("${AppUser.redis.effectiveTime}")
	private long effectiveTime;
	
	
	//用户登录
	@Override
	public JsonResult login(String mobile, String password) {
		//账号密码判断
		List<AppUser> users = appUserMapper.selectByMobile(mobile);
		if(users == null || users.size() <= 0){
			return new JsonResult(JsonResult.FALSE, "账号或密码错误");
		}
		AppUser user = users.get(0);
		if(user == null || !user.getPassword().equals(MD5Util.encrypt(password))){
			return new JsonResult(JsonResult.FALSE, "账号或密码错误");
		}
		//判断账号是否过期
		if(user.getExpiryTime().getTime() < new Date().getTime()){
			return new JsonResult(JsonResult.FALSE, "账号过期");
		}
		//登录成功，修改登录状态，记录已经登录
		if(!"0".equals(user.getState())){
			user.setState("0");
			user.updateById();
		}
		//生成token放入redis
		String token = TokenUtil.getToken(user.getId().toString());
		redis.deleteBySuffix("!"+user.getId());
		redis.set(token,user,loginTime);		
		log.info("用户"+user.getName()+"登录成功");
		return new JsonResult(token);
	}

	
	/**
	 * 修改密码
	 */
	@Override
	public JsonResult changePassword(String token, String oldPass, String newPass) {
		AppUser user = (AppUser)redis.get(token);
		if(MD5Util.encrypt(oldPass).equals(user.getPassword())){
			//密码输入正确，修改密码
			user.setPassword(MD5Util.encrypt(newPass));
			user.updateById();
			redis.set(token,user,loginTime);
			return new JsonResult();
		}else{
			//密码输入错误
			return new JsonResult(JsonResult.FALSE, "原密码输入错误");
		}
	}


	/**
	 * 发送短信验证码
	 * @param mobile 电话号
	 * @param suffix 存储验证码redis中key的后缀
	 */
	@Override
	public JsonResult getMessageCode(String mobile, String suffix) {
		//判断该手机号是否在一分钟内发送过短信
		boolean hasKey = redis.hasKey(mobile+"_"+suffix+"_time");
		if(hasKey){
			return new JsonResult(JsonResult.FALSE, "操作太频繁，请您稍后再试");
		}
		//发送短信验证码
		String code = RandNumUtil.getRandNum(6);
		//SmsSendResult result = SmsApiUtil.SmsCodeSend(mobile,code);
		//成功
		//if(result.isResult()){
		if(true){
			redis.set(mobile+"_"+suffix+"_time", "0", intervalTime);
			redis.set(mobile+"_"+suffix+"_code", code, effectiveTime);
			log.info("向手机号"+mobile+"发送短信验证码"+code);
			return new JsonResult(JsonResult.SUCCESS, "短信发送成功");
		//失败
		}else{
			log.error("向手机号"+mobile+"发送短信验证码失败");
			return new JsonResult(JsonResult.FALSE, "短信发送失败");
		}
	}

	/**
	 * 忘记密码--获得短信验证码
	 */
	@Override
	public JsonResult getForgetPasswordCode(String mobile, String name) {
		//判断手机号和姓名是否正确
		List<AppUser> users = appUserMapper.selectByMobileAndName(mobile,name);
		if(users == null || users.size() <= 0){
			return new JsonResult(JsonResult.FALSE, "姓名或手机号错误");
		}
		//正确发送短信验证码
		return this.getMessageCode(mobile, "forgetPassword");
	}

	/**
	 * 更换手机号--获得短信验证码
	 */
	@Override
	public JsonResult getResetMobileCode(String token, String password, String mobile) {
		//判断密码是否正确,token存在用户就存在
		AppUser user = (AppUser)redis.get(token);
		if(!MD5Util.encrypt(password).equals(user.getPassword())){
			return new JsonResult(JsonResult.FALSE, "密码输入错误");
		}
		//判断已更改手机号是否存在
		List<AppUser> users = appUserMapper.selectByMobile(mobile);
		if(users.size() > 0){
			return new JsonResult(JsonResult.FALSE, "此手机号已被注册");
		}
		//发送短信验证码
		return this.getMessageCode(mobile, "resetMobile");
	}

	/**
	 * 忘记密码
	 */
	@Override
	public JsonResult forgetPossword(String mobile, String name, String code, String password) {
		//判断手机号和姓名是否正确
		List<AppUser> users = appUserMapper.selectByMobileAndName(mobile,name);
		if(users == null || users.size() <= 0){
			return new JsonResult(JsonResult.FALSE, "姓名或手机号错误");
		}
		//判断验证码是否正确
		String redisCode = (String) redis.get(mobile+"_forgetPassword_code");
		if(redisCode == null || !code.equals(redisCode)){
			return new JsonResult(JsonResult.FALSE, "验证码错误");
		}
		//修改密码
		AppUser user = users.get(0);
		user.setPassword(MD5Util.encrypt(password));
		user.updateById();
		return new JsonResult();
	}


	/**
	 * 更换手机号
	 */
	@Override
	public JsonResult resetMobile(String token, String password, String mobile, String code) {
		//判断密码是否正确,token存在用户就存在
		AppUser user = (AppUser)redis.get(token);
		if(!MD5Util.encrypt(password).equals(user.getPassword())){
			return new JsonResult(JsonResult.FALSE, "密码输入错误");
		}
		//判断已更改手机号是否存在
		List<AppUser> users = appUserMapper.selectByMobile(mobile);
		if(users.size() > 0){
			return new JsonResult(JsonResult.FALSE, "此手机号已被注册");
		}
		//判断验证码是否正确
		String redisCode = (String) redis.get(mobile+"_resetMobile_code");
		if(redisCode == null || !code.equals(redisCode)){
			return new JsonResult(JsonResult.FALSE, "验证码错误");
		}
		//修改手机号
		user.setMobile(mobile);
		user.updateById();
		redis.set(token,user,loginTime);
		return new JsonResult();
	}




}
