package com.boyoi.base.web.controller;

import com.boyoi.base.domain.LogLogin;
import com.boyoi.base.service.LogLoginService;
import com.boyoi.core.utils.poi.*;
import com.boyoi.core.web.controller.BaseCrudController;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 登录日志 controller层
 *
 * @author Chenj
 */
@Controller
@RequestMapping(value = {"loglogin", "logLogin" })
public class LogLoginController extends BaseCrudController<LogLogin,LogLoginService> {

    @Override
    protected void addSearchCondition(EasyGridRequest easyGridRequest) {
        if (null == easyGridRequest.getOrder() || "".equals(easyGridRequest.getOrder()))
            easyGridRequest.setOrder("desc");
        if (null == easyGridRequest.getSort() || "".equals(easyGridRequest.getSort()))
            easyGridRequest.setSort("tableAlias.loginDate");
        super.addSearchCondition(easyGridRequest);
    }

    /**
    * 进入添加对象前,需要初始化的一些数据
    * @param loglogin 登录日志实体对象
    * @param model 模型
    */
    @Override
    protected void addUiInit(LogLogin loglogin, Model model) {

    }

    /**
    * 进入编辑对象前,需要初始化的一些数据
    * @param model 模型
    */
    @Override
    protected void updateUiInit(Model model) {

    }

    @RequestMapping(value = "excelExport")
    public void excelExport(ExcelExportRequest exportRequest, HttpServletResponse response){
        // 基础信息
        ExcelBaseData excelBaseData = new ExcelBaseData("登录日志",
                                                        new String[]{"登录日志(所有)"},
                                                        new String[]{"登录日志"});

        // 合并区域
        List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
        cellRangeAddressList.add(new CellRangeAddress(0,0,0,5));

        // 标题
        List<ExcelTitle> excelTitles = new ArrayList<>();
        excelTitles.add(new ExcelTitle(15, "realName", "姓名"));
        excelTitles.add(new ExcelTitle(15, "loginName", "登录名"));
        excelTitles.add(new ExcelTitle(25, "loginDate", "登录时间"));
        excelTitles.add(new ExcelTitle(25, "browser", "浏览器"));
        excelTitles.add(new ExcelTitle(25, "ip", "IP"));
        excelTitles.add(new ExcelTitle(25, "logoutDate", "退出时间"));

        // 从数据库中取得数据, 并把数据封装到sheet对应的数据集中
        List<ExcelQueryData> excelQueryDataList = new ArrayList<>();
        List<Map<String, Object>> queryData = service.findForExcelExport(exportRequest);
        // sheet名称必须等于 ExcelBaseData 中的sheetName
        ExcelQueryData excelQueryData = new ExcelQueryData("登录日志", queryData);
        excelQueryDataList.add(excelQueryData);

        // 创建帮助类对象
        POIHelper poiHelper = new POIHelper(excelBaseData, cellRangeAddressList, excelTitles, excelQueryDataList);

        // 生成 workbook
        Workbook make = poiHelper.make();

        // 打给浏览器
        POIUtil.excel2Browser(excelBaseData, make, response);

    }

}