package com.boyoi.base.domain;

import com.boyoi.core.domain.BaseDomain;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单 实体对象
 *
 * @author Chenj
 */
public class Menu extends BaseDomain {
	private static final long serialVersionUID = -7014954409806653679L;

	/**
     * 菜单名称
     */
    @NotBlank(message = "{Menu.validator.menuName.required}")@Size(max=16, message = "{Menu.validator.menuName.max}")
    private String menuName;
    
    /**
     * 上级菜单
     */
    private Menu parentMenu;
    /**
     * 同级排序号
     */
    @Max(value=99, message = "{Menu.validator.sortNum.max}")
    private Integer sortNum;
    
    /**
     * 菜单提示
     */
    @Size(max=100, message = "{Menu.validator.hint.max}")
    private String hint;
    
    /**
     * 图标
     */
    private Icon icon;
    
    /**
     * 入口地址
     */
    @Size(max=512, message = "{Menu.validator.entryUrl.max}")
    private String entryUrl;

    /**
     * 父菜单对应子菜单集合
     */
    private List<Menu> subMenus = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        Menu menu = null;

        if (obj instanceof  Menu)
            menu = (Menu)obj;

        if (null != menu && null != menu.getGuid())
            if (menu.getGuid().equals(this.getGuid()))
                return true;

        return false;
    }


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getEntryUrl() {
        return entryUrl;
    }

    public void setEntryUrl(String entryUrl) {
        this.entryUrl = entryUrl;
    }

    public List<Menu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }
}