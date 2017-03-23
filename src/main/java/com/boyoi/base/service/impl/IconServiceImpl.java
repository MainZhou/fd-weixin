package com.boyoi.base.service.impl;

import com.boyoi.base.dao.IconDao;
import com.boyoi.base.service.IconService;
import com.boyoi.core.service.impl.BaseRedisServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 图标 service层实现类
 *
 * @author Chenj
 */
@Service
public class IconServiceImpl extends BaseRedisServiceImpl<IconDao> implements IconService {

}