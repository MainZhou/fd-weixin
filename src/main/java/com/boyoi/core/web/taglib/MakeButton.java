package com.boyoi.core.web.taglib;

import com.boyoi.core.utils.GetI18N;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * 生成添加、编辑、详情、删除按钮
 * @author Chenj
 */
public class MakeButton extends SimpleTagSupport{
    // 添加
    private boolean addBtn    = true;
    // 编辑
    private boolean editBtn   = true;
    // 详情
    private boolean detailBtn = true;
    // 删除
    private boolean delBtn    = true;
    // 导出
    private boolean exportBtn = false;
    // 返回
    private boolean goBackBtn = false;

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        String html = "";
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (addBtn){
            html += "<li><a href=\"javascript:\" class=\"add-tag\" title=\"" + GetI18N.get(request, "global.add") + "\"><span class=\"fa fa-fw fa-plus\"></span></a></li>";
        }
        if (editBtn){
            html += "<li><a href=\"javascript:\" class=\"edit-tag\" title=\""+ GetI18N.get(request, "global.update") +"\"><span class=\"glyphicon glyphicon-pencil\"></span></a></li>";
        }
        if (detailBtn){
            html += "<li><a href=\"javascript:\" class=\"detail-tag\" title=\"" + GetI18N.get(request, "global.detail") + "\"><span class=\"glyphicon glyphicon-list-alt\"></span></a></li>";
        }
        if (delBtn){
            html += "<li><a href=\"javascript:\" class=\"del-tag\" title=\"" + GetI18N.get(request, "global.del") + "\"><span class=\"glyphicon glyphicon-trash\"></span></a></li>";
        }
        if (exportBtn){
            html += "<li><a href=\"javascript:\" class=\"export-tag\" title=\"" + GetI18N.get(request, "global.export") + "\"><span class=\"fa fa-fw fa-share-square-o\"></span></a></li>";
        }
        if (goBackBtn){
            html += "<li><a href=\"javascript:\" class=\"goBack-tag\" title=\"" + GetI18N.get(request, "global.back") + "\"><span class=\"fa fa-fw fa-mail-reply\" style='color:green;'></span></a></li>";
        }
//        if (addBtn){
//            html += "<li><a href=\"javascript:\" class=\"add-tag\"><span class=\"glyphicon glyphicon-plus\"></span></a></li>";
//        }
//        if (editBtn){
//            html += "<li><a href=\"javascript:\" class=\"edit-tag\"><span class=\"glyphicon glyphicon-wrench\"></span></a></li>";
//        }
//        if (detailBtn){
//            html += "<li><a href=\"javascript:\" class=\"detail-tag\"><span class=\"glyphicon glyphicon-list-alt\"></span></a></li>";
//        }
//        if (delBtn){
//            html += "<li><a href=\"javascript:\" class=\"del-tag\"><span class=\"glyphicon glyphicon-trash\"></span></a></li>";
//        }
        pageContext.getOut().write(html);

    }

    public boolean isAddBtn() {
        return addBtn;
    }

    public void setAddBtn(boolean addBtn) {
        this.addBtn = addBtn;
    }

    public boolean isEditBtn() {
        return editBtn;
    }

    public void setEditBtn(boolean editBtn) {
        this.editBtn = editBtn;
    }

    public boolean isDetailBtn() {
        return detailBtn;
    }

    public void setDetailBtn(boolean detailBtn) {
        this.detailBtn = detailBtn;
    }

    public boolean isDelBtn() {
        return delBtn;
    }

    public void setDelBtn(boolean delBtn) {
        this.delBtn = delBtn;
    }

    public boolean isExportBtn() {
        return exportBtn;
    }

    public void setExportBtn(boolean exportBtn) {
        this.exportBtn = exportBtn;
    }

    public boolean isGoBackBtn() {
        return goBackBtn;
    }

    public void setGoBackBtn(boolean goBackBtn) {
        this.goBackBtn = goBackBtn;
    }
}
