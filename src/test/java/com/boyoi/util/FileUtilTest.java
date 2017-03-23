package com.boyoi.util;

import com.boyoi.core.utils.FileUtil;
import org.junit.Test;

/**
 * 文件工具测试
 * @author Chenj
 */
public class FileUtilTest {
    @Test
    public void test(){
        String fileSuffix = FileUtil.getFileSuffix("jljlj");
        System.out.println(fileSuffix);

    }

}
