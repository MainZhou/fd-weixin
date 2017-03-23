package com.boyoi.icon;

import com.boyoi.base.dao.IconDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量添加图标
 * @author Chenj
 */
public class BatchDel {
    ApplicationContext context=null;
    String icon ;
    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(new String[]{"classpath*:/spring/spring_daos.xml"});
    }

    @Test
    public void add(){
        IconDao dao = context.getBean(IconDao.class);

        List<Serializable> guids = new ArrayList<>();
        guids.add("eef4fa10-ce15-4c8f-be27-95fade1efd76");
        guids.add("c955373d-35f6-4c62-b01f-fde196a084ff");
        int i = dao.delBatch(guids);
        System.out.println("成功删除:" + i + "个!");
    }
}
