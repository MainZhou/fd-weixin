package com.boyoi.core.creator;

/**
 * 生成不同文件的接口.
 * 如需生成不同的文件实现此接口
 * @author Chenj
 */
public interface Creator {

    /**
     * 生成文件
     * @return 生成后的文件绝对路径
     */
    String creatorFile();


}
