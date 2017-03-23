package ${columnRequest.packageName}.service.impl;
<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
import com.boyoi.core.service.impl.BaseServiceImpl;
import com.boyoi.core.utils.WebUtils;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;
import ${columnRequest.packageName}.dao.${columnRequest.prefix}Dao;
import ${columnRequest.packageName}.domain.${columnRequest.prefix};
import ${columnRequest.packageName}.service.${columnRequest.prefix}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${columnRequest.funcName} service层实现类
 *
 * @author ${columnRequest.devPerson}
 */
@Service
public class ${columnRequest.prefix}ServiceImpl extends BaseServiceImpl<${columnRequest.prefix}Dao> implements ${columnRequest.prefix}Service {

    @Autowired
    private ${columnRequest.prefix}Dao dao;

    @Cacheable(value="${columnRequest.prefixFirstCharLow}", keyGenerator="gridRequestKeyGen", condition="#gridRequest.mapSize == 0")
    @Override
    public EasyUiPage.Page findByGridRequest(EasyGridRequest gridRequest) {
        EasyUiPage page = dao.findByGridRequest(gridRequest);
        return page.getPage();
    }

    @CacheEvict(value = "${columnRequest.prefixFirstCharLow}", allEntries = true)
    @Override
    public int add(${columnRequest.prefix} ${columnRequest.prefixFirstCharLow}) {
        ${columnRequest.prefixFirstCharLow}.setGuid(WebUtils.generateID());
        return dao.add(${columnRequest.prefixFirstCharLow});
    }

    @CacheEvict(value = "${columnRequest.prefixFirstCharLow}", allEntries = true)
    @Override
    public int addBatch(List<${columnRequest.prefix}> ${columnRequest.prefixFirstCharLow}s) {
        return dao.addBatch(${columnRequest.prefixFirstCharLow}s);
    }

    @Caching(evict = {@CacheEvict(value = "${columnRequest.prefixFirstCharLow}", allEntries = true),
                      @CacheEvict(value = "${columnRequest.prefixFirstCharLow}_guids", key = "#${columnRequest.prefixFirstCharLow}.guid")})
    @Override
    public int update(${columnRequest.prefix} ${columnRequest.prefixFirstCharLow}) {
        return dao.update(${columnRequest.prefixFirstCharLow});
    }

    @Caching(evict = {@CacheEvict(value = "${columnRequest.prefixFirstCharLow}", allEntries = true),
                      @CacheEvict(value = "${columnRequest.prefixFirstCharLow}_guids", key = "#${columnRequest.prefixFirstCharLow}.guid")})
    @Override
    public int updateNotEmpty(${columnRequest.prefix} ${columnRequest.prefixFirstCharLow}) {
        return dao.updateNotEmpty(${columnRequest.prefixFirstCharLow});
    }

    @Caching(evict = {@CacheEvict(value = "${columnRequest.prefixFirstCharLow}", allEntries = true),
                      @CacheEvict(value = "${columnRequest.prefixFirstCharLow}_guids", allEntries = true)})
    @Override
    public int delBatch(List<String> guids) {
        return dao.delBatch(guids);
    }

    @Cacheable(value = "${columnRequest.prefixFirstCharLow}_guids", key = "#guid")
    @Override
    public ${columnRequest.prefix} findByGuid(String guid) {
        return dao.findByGuid(guid);
    }
}