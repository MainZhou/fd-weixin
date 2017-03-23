package com.boyoi.work.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 随机生成短信验证码
 * @author Chenj
 */
public class GenSmsCode {

    private static DecimalFormat decimalFormat = new DecimalFormat("0000");

    /**
     * 随机生成四位数
     * @return 四位数
     */
    public static String random4Number(){
        Random random = new Random();
        return decimalFormat.format(random.nextInt(9999));
    }
}
