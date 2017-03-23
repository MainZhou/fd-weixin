package com.boyoi.base.service;
 
import com.boyoi.base.domain.Menu;
import com.boyoi.core.service.BaseCrudService;

import java.util.List;

/**
 * 菜单 service层
 *
 * @author Chenj
 */
public interface MenuService extends BaseCrudService {

    /**
     * 查找一级菜单
     * @return 一级菜单集合
     */
    List<Menu> findLevel1Menu();

    /**
     * 通过父菜单查找下面的所有子菜单
     * @param parentMenuId 父菜单ID
     * @return 父菜单下的所有的子菜单
     */
    List<Menu> findChildMenuByParentMenuId(String parentMenuId);

}