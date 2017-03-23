package com.boyoi.base.service.impl;

import com.boyoi.base.dao.LogLoginDao;
import com.boyoi.base.service.LogLoginService;
import com.boyoi.core.service.impl.BaseRedisServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 登录日志 service层实现类
 *
 * @author Chenj
 */
@Service
public class LogLoginServiceImpl extends BaseRedisServiceImpl<LogLoginDao> implements LogLoginService {

}