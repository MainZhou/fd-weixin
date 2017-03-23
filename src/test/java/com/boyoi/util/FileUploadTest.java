package com.boyoi.util;

import com.boyoi.core.utils.HttpUtils;
import org.junit.Test;

import java.io.File;

/**
 * @author Chenj
 */
public class FileUploadTest {
    @Test
    public void test(){
        File[] files = new File[2];
        files[0] = new File("d:\\1.txt");
        files[1] = new File("d:\\2.txt");
        HttpUtils.postWithToken("http://192.168.1.76/test",null, files);
    }
}
