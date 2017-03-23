package com.boyoi.base.service;

import com.boyoi.base.domain.LogOpt;
import com.boyoi.core.service.BaseCrudService;

import java.util.List;

/**
 * 操作日志 service层
 *
 * @author Chenj
 */
public interface LogOptService extends BaseCrudService {

    /**
     * 通过GUID查找对应的所有日志
     * @param guid GUID
     * @return 对应的日志
     */
    List<LogOpt> findOptLogByGuid(String guid);

    /**
     * 通过操作的GUID查找对应的所有日志
     * @param optGuid 操作的GUID
     * @return 对应的日志
     */
    List<LogOpt> findOptLogByOptGuid(String optGuid);

}