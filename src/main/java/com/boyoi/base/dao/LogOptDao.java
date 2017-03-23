package com.boyoi.base.dao;
 
import com.boyoi.base.domain.LogOpt;
import com.boyoi.core.dao.BaseCrudDao;

import java.util.List;

/**
 * 操作日志 dao层
 *
 * @author Chenj
 */
public interface LogOptDao extends BaseCrudDao {
    /**
     * 通过操作的GUID查找对应的所有日志
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