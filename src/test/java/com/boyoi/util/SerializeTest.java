package com.boyoi.util;

import com.boyoi.base.domain.User;
import com.boyoi.core.utils.FstUtil;
import com.boyoi.core.utils.SerializeUtil;

import org.junit.Test;
import java.util.Date;

/**
 * @author Chenj
 */
public class SerializeTest {
    @SuppressWarnings("unused")
	@Test
    public void test(){
        System.out.println(new Date());
        User user = new User();
        user.setLoginName("chenjie");
        user.setPassword("test");
        user.setAddr("11111111111111111111111111111111111111231333333333333333333333333333333333");

        for (int i =0 ;i< 1000000; i++){
            byte[] serialize = SerializeUtil.serialize(user);
            User newUser = SerializeUtil.unserialize(serialize);
        }

        System.out.println(new Date());

    }

    @SuppressWarnings("unused")
	@Test
    public void testWithFst(){
        System.out.println(new Date());
        User user = new User();
        user.setLoginName("chenjie");
        user.setPassword("test");
        user.setAddr("11111111111111111111111111111111111111231333333333333333333333333333333333");

        for (int i =0 ;i< 1000000; i++){
            byte[] serialize = FstUtil.serialize(user);
            User newUser = FstUtil.unserialize(serialize);
        }

        System.out.println(new Date());

    }
    

    
}

