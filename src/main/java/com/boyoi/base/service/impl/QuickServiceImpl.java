package com.boyoi.base.service.impl;

import com.boyoi.base.dao.QuickDao;
import com.boyoi.base.service.QuickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快捷方式 service层
 * @author Chenj
 */
@Service
public class QuickServiceImpl implements QuickService {

    @Autowired
    private QuickDao quickDao;

    @Override
    public Map<String, String> getQuickNum(String userId) {
        List<Map<String, String>> quickNum = quickDao.getQuickNum(userId);
        Map<String, String> returnMap = new HashMap<>();
        for (Map<String, String> map : quickNum){
            returnMap.put((map.get("quickType")), String.valueOf(map.get("quickNum")));
        }

        return returnMap;
    }
}
