package com.boyoi.core.creator.impl;

import com.boyoi.core.creator.CreatorHelper;
import com.boyoi.core.domain.ColumnRequest;

import javax.servlet.ServletRequest;
import java.io.File;

/**
 * 国际化资源生成器
 * 生成中文 .properties文件
 * @author Chenj
 */
public class I18N_zh_cn_Creator extends CreatorHelper {
    /**
     * 构造函数.初始化
     *
     * @param columnRequest  页面传来的请求
     * @param servletRequest HttpServletRequest
     */
    public I18N_zh_cn_Creator(ColumnRequest columnRequest, ServletRequest servletRequest) {
        super(columnRequest, servletRequest);
    }

    @Override
    protected String setGenFileName() {
        return resourcePath + File.separator +
                "i18n" + File.separator +
                columnRequest.getPrefix() + ".properties";    }

    @Override
    protected String setTplUrl() {
        return basePath + "/creator/tpl/i18n_zh_cn";
    }
}
