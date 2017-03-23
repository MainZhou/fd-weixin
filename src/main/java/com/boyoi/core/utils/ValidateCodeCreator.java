package com.boyoi.core.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 随机验证码生成
 * @author Chenj
 */
public class ValidateCodeCreator {
    /**
     * 随机生成的验证码
     */
    private String validateCode;

    private static final String randomSrc = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";

    /**
     * 验证码的位数
     */
    private static final int validateCodeLen = 4;

    /**
     * 内存图象的 width
     */
    private static final int bufferedImageWidth = 98;

    /**
     * 内存图象的 height
     */
    private static final int bufferedImageHeight = 30;

    /**
     * 产生验证码与验证码图象
     * @return 验证码图象
     */
    public BufferedImage creatImageAndCode() {
        /**
         * 初始化验证码
         */
        validateCode = "";

        /**
         * 在内存中创建图象
         */
        BufferedImage validateImage = new BufferedImage(bufferedImageWidth,
                bufferedImageHeight,
                BufferedImage.TYPE_INT_RGB);

        /**
         * 获取图形上下文
         */
        Graphics myGraphic = validateImage.getGraphics();

        /**
         * 生成随机类
         */
        Random random = new Random();

        /**
         * 设定背景色
         */
        myGraphic.setColor(new Color(255, 255, 255));

        /**
         *填充边框
         */
        myGraphic.fillRect(0, 0, bufferedImageWidth, bufferedImageHeight);

        /**
         * 设定字体
         */
        myGraphic.setFont(new Font("Times New Roman", Font.ITALIC, 22));

        for (int i = 0; i < 19; i++) {
            myGraphic.setColor(getRandColor(170, 220));
            int x = random.nextInt(bufferedImageWidth);
            int y = random.nextInt(bufferedImageHeight);
            int xl = random.nextInt(bufferedImageWidth);
            int yl = random.nextInt(bufferedImageHeight);
            myGraphic.drawLine(x, y, x + xl, y + yl);
        }

        /**
         * 取随机产生的认证码
         */
        for (int i = 0; i < validateCodeLen; i++) {
            int index = random.nextInt(randomSrc.length());
            String rand = String.valueOf(randomSrc.charAt(index));
            validateCode += rand;
            /**
             * 将认证码显示到图象中
             */
            myGraphic.setColor(getRandColor(0, 190));
            myGraphic.drawString(rand, 23 * i + 12, 23);
        }

        /**
         * 图象生效
         */
        myGraphic.dispose();

        return validateImage;
    }

    /**
     * 给定范围获得随机颜色
     * @param fc 大于此数
     * @param bc 小于的此数
     * @return 随机颜色
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public String getValidateCode() {
        return validateCode;
    }
}
