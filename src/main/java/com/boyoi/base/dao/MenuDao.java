package com.boyoi.base.dao;
 
import com.boyoi.base.domain.Menu;
import com.boyoi.core.dao.BaseCrudDao;

import java.util.List;

/**
 * 菜单 dao层
 *
 * @author Chenj
 */
public interface MenuDao extends BaseCrudDao {
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