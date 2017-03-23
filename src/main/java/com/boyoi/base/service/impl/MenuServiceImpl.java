package com.boyoi.base.service.impl;

import com.boyoi.base.dao.MenuDao;
import com.boyoi.base.domain.Menu;
import com.boyoi.base.service.MenuService;
import com.boyoi.core.service.impl.BaseRedisServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单 service层实现类
 *
 * @author Chenj
 */
@Service
public class MenuServiceImpl extends BaseRedisServiceImpl<MenuDao> implements MenuService {

    @Override
    public List<Menu> findLevel1Menu() {
        return dao.findLevel1Menu();
    }

    @Override
    public List<Menu> findChildMenuByParentMenuId(String parentMenuId) {
        return dao.findChildMenuByParentMenuId(parentMenuId);
    }
}