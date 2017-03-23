package com.boyoi.base.domain;

/**
 * ZTree实体
 */
public class Ztree {
    // id
	private String id;
    // 名称
	private String name;
    // 父id
	private String pid;
    // 是否打开
	private boolean open=true;
    // 是否让此数据被Check
    private boolean nocheck=false;
    //是否禁止勾选
    private boolean doCheck = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public boolean isDoCheck() {
        return doCheck;
    }

    public void setDoCheck(boolean doCheck) {
        this.doCheck = doCheck;
    }
}
