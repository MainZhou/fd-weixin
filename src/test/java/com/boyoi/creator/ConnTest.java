package com.boyoi.creator;

import com.boyoi.core.dao.CreatorDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Chenj
 */
public class ConnTest {

    ApplicationContext context=null;
    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(new String[]{"classpath*:/spring/spring_services.xml", "classpath*:/spring/spring_daos.xml"});
    }

    @Test
    public void daoTest(){
        CreatorDao creatorDao = context.getBean(CreatorDao.class);
        creatorDao.getTables();
    }

    @Test
    public void getColumn(){
        CreatorDao creatorDao = context.getBean(CreatorDao.class);
        creatorDao.getColumnByTable("tb_success_case_info");
    }

}
