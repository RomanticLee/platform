/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smart.admin.modular.system.service;

import com.smart.admin.core.common.node.MenuNode;
import com.smart.admin.core.common.node.ZTreeNode;
import com.smart.admin.modular.system.model.Menu;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 菜单服务
 *
 * @author fengshuonan
 * @date 2017-05-05 22:19
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 删除菜单
     *
     * @author stylefeng
     * @Date 2017/5/5 22:20
     */
    void delMenu(String menuId);

    /**
     * 删除菜单包含所有子菜单
     *
     * @author stylefeng
     * @Date 2017/6/13 22:02
     */
    void delMenuContainSubMenus(String menuId);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectMenus(String condition, String level);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<String> getMenuIdsByRoleId(String roleId);

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<String> menuIds);

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     * @date 2017年2月19日 下午4:10:59
     */
    int deleteRelationByMenu(String menuId);

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
    List<String> getResUrlsByRoleId(String roleId);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     * @date 2017年2月19日 下午10:35:40
     */
    List<MenuNode> getMenusByRoleIds(List<String> roleIds);
}