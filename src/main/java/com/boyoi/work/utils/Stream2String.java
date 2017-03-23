package com.boyoi.work.utils;

import java.io.*;

/**
 * 流转字符
 * @author Chenj
 */
public class Stream2String {


	public static String inputStream2String(InputStream is) throws IOException{
        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line;
        while ((line=br.readLine())!=null){
            sb.append(line);
        }

        return sb.toString();
	}
	
}
