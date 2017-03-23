package com.boyoi.base.service.impl;

import com.boyoi.base.dao.LogOptDao;
import com.boyoi.base.domain.LogOpt;
import com.boyoi.base.service.LogOptService;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.service.impl.BaseCrudServiceImpl;
import com.boyoi.core.utils.GetI18N;
import com.boyoi.core.utils.StringUtil;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 操作日志 service层实现类
 *
 * @author Chenj
 */
@Service
public class LogOptServiceImpl extends BaseCrudServiceImpl<LogOptDao> implements LogOptService {

    @Override
    public List<LogOpt> findOptLogByGuid(String guid) {
        return dao.findOptLogByGuid(guid);
    }

    @Override
    public List<LogOpt> findOptLogByOptGuid(String optGuid) {
        return dao.findOptLogByOptGuid(optGuid);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected <T extends BaseDomain> void findByEasyGridRequestAfter(EasyGridRequest easyGridRequest, List<T> resultList) {
        EasyUiPage page = (EasyUiPage) resultList;
        List<LogOpt> rows = page.getRows();
        HttpServletRequest request;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch (NullPointerException n){
            // 未登录。ignore
            return;
        }

        // 解析中文
        for (LogOpt logOpt : rows){
            String optObject = logOpt.getOptObject();
            String s = StringUtil.tableName2Prefix(optObject);
            logOpt.setOptObject(GetI18N.get(request, s + ".func.name"));
        }
    }
}