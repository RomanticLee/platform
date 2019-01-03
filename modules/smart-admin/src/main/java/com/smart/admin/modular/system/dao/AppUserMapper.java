package com.smart.admin.modular.system.dao;

import com.smart.admin.modular.system.model.AppUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sesame
 * @since 2018-11-15
 */
public interface AppUserMapper extends BaseMapper<AppUser> {

	List<AppUser> selectByMobile(String mobile);

	List<AppUser> selectByMobileAndName(@Param("mobile") String mobile, @Param("name") String name);


	

}
