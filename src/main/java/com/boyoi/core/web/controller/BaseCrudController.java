package com.boyoi.core.web.controller;

import com.boyoi.base.domain.User;
import com.boyoi.base.enums.LoginFlag;
import com.boyoi.base.service.LogOptService;
import com.boyoi.core.common.RemoteValidatorResult;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.service.BaseCrudService;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Controller层的基类,继承此类.自动获得增删除改查的基本功能
 * @param <T> 实体
 * @param <S> service
 * @author Chenj
 */
public abstract class BaseCrudController<T extends BaseDomain,S extends BaseCrudService> {

	private Class<T> entityClass;

    protected Logger LOG;

    //把传来的继续baseservice的service实例化
	@Autowired
	protected S service;

    @Autowired
    private LogOptService logOptService;
	
	//自动注入request,继承本类的子类就可以直接使用
	@Autowired
	protected HttpServletRequest request;

    //自动注入session,继承本类的子类就可以直接使用
    @Autowired
    protected HttpSession session;
	
	/**
	 * 创建路径名称，必须为实体类，类名的全小写
	 * 如 user
	 */
	protected String url;

    /**
     * 初始化返回json信息
     */
    protected  ResponseResult responseResult = new ResponseResult();

    @SuppressWarnings("unchecked")
	public BaseCrudController(){
		/**
		 * ParameterizedType就是范型,
		 * getActualTypeArguments()[0]获得调用者的泛型中的值，
		 */
		ParameterizedType classt = (ParameterizedType) this.getClass().getGenericSuperclass();	//返回调用者的泛型中的Class对象
		entityClass = (Class<T>) classt.getActualTypeArguments()[0];
        LOG = LoggerFactory.getLogger(entityClass);
		url = entityClass.getSimpleName();
	}

	/**
	 * 添加的界面
	 * @return 视图
	 */
	@RequestMapping(value = "add",method=RequestMethod.GET)
	public String addUI(T t,Model model){
        addUiInit(t, model);
		return url+"/add";
	}

    /**
     * 添加页面需要初始化的值
     * @param t 实体
     * @param model 模型
     */
    protected abstract void addUiInit(T t, Model model);


    /**
     * 添加的实体
     * @param t 实体
     * @param result 检验返回的结果
     * @param other 其它字段
     * @return 视图
     */
	@RequestMapping(value = "add",method=RequestMethod.POST)
    @ResponseBody
	public ResponseResult add(@Validated T t,BindingResult result,Model model,String... other){

		//判断是否有错,有错返回添加页面
		if(result.hasErrors()){
            addUiInit(t, model);
            execErrors(result);
            LOG.debug("添加数据失败! 没通过服务端校验!");
            return responseResult;
        }

		try {
            addFunBefore(t, other);
            service.add(t);
            addFunAfter(t, other);
        }catch (Exception e){
            e.printStackTrace();
            responseResult.setStatus(false);
            responseResult.setMsg("添加失败!");
            //转到显示所有的页面
            return responseResult;
        }

        responseResult.setStatus(true);
        responseResult.setMsg("添加成功!");

        //转到显示所有的页面
        return responseResult;
	}

    /**
	 * 添加一个实体之前要做的
	 */
	public void addFunBefore(T t, String... other){

	}

	/**
	 * 添加一个实体之后要做的
	 */
	public void addFunAfter(T t, String... other){
	}

    /**
     * 更新实体的界面
     * @param guid 实体的id
     * @return 视图
     */
	@RequestMapping(value="update", method = RequestMethod.GET)
	public String updateUI(String guid, Model model){
        T t = service.findByGuid(guid);
        // 存放在model, 用于展示页面
        model.addAttribute("update", t);
        // 存放在session中, 用于保存操作日志
        session.setAttribute(guid, t);
        updateUiInit(model);
		return url + "/update";
	}

    /**
     * 请求更新实体之前
     * 要初始化的数据
     * @param model model
     */
    protected abstract void updateUiInit(Model model);

    /**
     * 更新实体的功能
     * @param t 实体
     * @param result 错误结果
     * @param other 其它字段
     * @return 视图
     */
    @RequestMapping(value="update",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@Validated T t, BindingResult result, Model model, String... other){

        //有错,返回更新界面
        if (result.hasErrors()){
            updateUiInit(model);
            execErrors(result);
            return responseResult;
        }

        try {
            updateBefore(t, other);
            service.update(t);
            updateAfter(t, other);
        }catch (Exception e){
            e.printStackTrace();
            responseResult.setStatus(false);
            responseResult.setMsg("更新失败!");
            return responseResult;
        }

        responseResult.setStatus(true);
        responseResult.setMsg("更新成功!");
        //返回json数据
        return responseResult;
    }

    /**
     * 添加一个实体之后要做的
     */
    public void updateBefore(T t, String... other){
    }
    /**
     * 添加一个实体之后要做的
     */
    public void updateAfter(T t, String... other){
    }

    /**
     * 获得实体json
     * @param guid guid
     * @return 视图
     */
    @RequestMapping(value="detail")
    public String detail(String guid, Model model){
        T t = service.findByGuid(guid);
        detailBefore(t);
        model.addAttribute("detail", t);
        return url + "/detail";
    }

    /**
     * 查看详细前 的操作
     * @param t 实体对象
     */
    protected void detailBefore(T t) {

    }

    /**
     * 删除实体（批量）
     * @param ids 待删除的id数组
     * @return 视图
     */
    @RequestMapping(value="del", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delByArray(String[] ids){

        int successNum;
        //成功的集合
        List<String> idsList = new ArrayList<>();
        //删除的ID
        for (String id : ids) {
            try {
                successNum = service.del(id);
                //是否删除成功
                if (successNum == 1) {
                    idsList.add(id);
                }
            } catch (DataIntegrityViolationException ignored) {

            }
        }
        //删除的记录为单条
        if (ids.length == 1) {
            if (idsList.size() == 0) {
                responseResult.setStatus(false);
                responseResult.setMsg("选中的记录已经与其它记录有关联，不能删除。");
            } else if (idsList.size() == 1) {
                responseResult.setStatus(true);
                responseResult.setMsg("删除成功。");
            }
        } else if (ids.length > 1) {//删除的记录为多条
            if (idsList.size() == ids.length) {
                responseResult.setStatus(true);
                responseResult.setMsg("删除成功。");
            } else if (idsList.size() == 0) {
                responseResult.setStatus(false);
                responseResult.setMsg("选中的记录已经与其它记录有关联，不能删除。");
            } else if (idsList.size() != ids.length && idsList.size() != 0) {
                responseResult.setStatus(false);
                responseResult.setMsg("已成功删除" + idsList.size() + "条记录，另外" + (ids.length - idsList.size()) + "条记录与其它记录有关联，不能删除。");
            }
        }

        return responseResult;

    }


    /**
     * 例出所有的实体
     * @return 实体集合 json格式
     */
	@RequestMapping(value = "listAll", method = RequestMethod.POST)
	@ResponseBody
	public Collection<T> listAll2JSON(){
        return service.findAll();
	}

    /**
     * 根据EasyGridRequest请求,例出实体
     * @return EasyUiPage.Page的json数据
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public EasyUiPage.Page listAllByEasyGridRequest(EasyGridRequest easyGridRequest){
        addSearchCondition(easyGridRequest);
        EasyUiPage all = (EasyUiPage)service.<T>findByEasyGridRequest(easyGridRequest);
        return all.get(0); //返回第一个。Page,里面封装了rows,total
    }

    /**
     * 自定义搜索条件,不包含前台传来的值
     * @param easyGridRequest easyGridRequest
     */
    protected void addSearchCondition(EasyGridRequest easyGridRequest) {
    }

    /**
     * 显示所有的信息。
     * @return 视图
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String listAll(Model model){
        beforeListAll(model);
        return url+"/list";
    }

    /**
     * 查找所有之前
     * @param model model
     */
    protected void beforeListAll(Model model){
    }

    /**
     * 校验实体中是否存在对应的数据
     * @param t 实体
     * @return json
     */
    @RequestMapping(value = "check")
    @ResponseBody
    public RemoteValidatorResult check(T t){
        RemoteValidatorResult validatorResult = new RemoteValidatorResult();
        if (service.findByDomain(t).size() > 0)
            validatorResult.setError("此项已经存在,请重新输入!");
        else
            validatorResult.setOk("√");

        return validatorResult;
    }

    /**
     * 处理包含有验证错误
     * @param result BindingResult
     */
    protected void execErrors(BindingResult result){
        List<ObjectError> allErrors = result.getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError objectError: allErrors){
            sb.append(objectError.getDefaultMessage());
        }
        responseResult.setStatus(false);
        responseResult.setMsg(sb.toString());
    }

    /**
     * 获得当前登录用户
     * @return  用户对象
     */
    protected User getUser(){
        return (User) session.getAttribute(LoginFlag.LOGIN_SUCCESS.name());
    }

}
