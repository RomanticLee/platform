package com.smart.admin.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.smart.admin.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.smart.admin.modular.system.model.AppUser;
import com.smart.admin.modular.system.service.IAppUserService;

/**
 * appUser控制器
 *
 * @author fengshuonan
 * @Date 2018-11-15 16:18:49
 */
@Controller
@RequestMapping("/appUser")
public class AppUserManagerController extends BaseController {

    private String PREFIX = "/system/appUser/";

    @Autowired
    private IAppUserService appUserService;

    /**
     * 跳转到appUser首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "appUser.html";
    }

    /**
     * 跳转到添加appUser
     */
    @RequestMapping("/appUser_add")
    public String appUserAdd() {
        return PREFIX + "appUser_add.html";
    }

    /**
     * 跳转到修改appUser
     */
    @RequestMapping("/appUser_update/{appUserId}")
    public String appUserUpdate(@PathVariable Integer appUserId, Model model) {
        AppUser appUser = appUserService.selectById(appUserId);
        model.addAttribute("item",appUser);
        LogObjectHolder.me().set(appUser);
        return PREFIX + "appUser_edit.html";
    }

    /**
     * 获取appUser列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<AppUser> selectList = appUserService.selectList(null);
        for (AppUser appUser : selectList) {
			String state = appUser.getState();
			if("0".equals(state)){
				appUser.setState("已登录");
			} else if ("1".equals(state)) {
				appUser.setState("未登录");
			}
		}
        return selectList;
    }

    /**
     * 新增appUser
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AppUser appUser) {
        appUserService.insert(appUser);
        return SUCCESS_TIP;
    }

    /**
     * 删除appUser
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer appUserId) {
        appUserService.deleteById(appUserId);
        return SUCCESS_TIP;
    }

    /**
     * 修改appUser
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AppUser appUser) {
        appUserService.updateById(appUser);
        return SUCCESS_TIP;
    }

    /**
     * appUser详情
     */
    @RequestMapping(value = "/detail/{appUserId}")
    @ResponseBody
    public Object detail(@PathVariable("appUserId") Integer appUserId) {
        return appUserService.selectById(appUserId);
    }
}
