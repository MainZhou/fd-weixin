package com.boyoi.base.web.controller;

import com.boyoi.base.domain.Ztree;
import com.boyoi.base.service.ZtreeService;
import com.boyoi.core.utils.ComparatorZtree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequestMapping(value = {"/zTree"})
public class ZtreeController {

    @Autowired
    private ZtreeService service;

    /**
     * 加载树形
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/loadZtree", method = RequestMethod.GET)
    @ResponseBody
    public List<Ztree> loadZtree(@RequestParam("model") String model) {
        if ("area".equals(model) || "productInfo".equals(model) || "productInfoRadio".equals(model) || "productFlowCount".equals(model) || "productStockCount".equals(model)) {

            List<Ztree> list = service.findAll(model);
            List<Ztree> allList = new ArrayList<>();
            if ("productInfoRadio".equals(model)) {
                // 产品单选。不能选择所有的父级元素
                allList = findParentList(list, allList, true);
            } else {
                allList = findParentList(list, allList);
            }
            allList.addAll(list);
            Map<String, Ztree> map = new HashMap<>();
            //list去重
            for (Ztree tree : allList) {
                map.put(tree.getId(), tree);
            }
            allList.clear();
            for (String key : map.keySet()) {
                allList.add(map.get(key));
            }
            //排序
            ComparatorZtree comparator = new ComparatorZtree();
            Collections.sort(allList, comparator);
            // 如是产品Radio.所有父级都不加Radio
            return allList;
        } else {
            return service.findAll(model);
        }
    }

    private List<Ztree> findParentList(List<Ztree> list, List<Ztree> allList, boolean nocheck) {
        if (list != null && list.size() != 0) {
            List<String> ids = checkId(list);
            if (ids.size() == 0) {
                return allList;
            } else {
                List<Ztree> parentList = service.findByIds(ids);

                // 如是非check项
                if (nocheck) {
                    for (Ztree ztree : parentList) {
                        ztree.setNocheck(true);
                    }
                }

                allList.addAll(parentList);
                findParentList(parentList, allList, nocheck);
            }
        }
        return allList;
    }

    private List<Ztree> findParentList(List<Ztree> list, List<Ztree> allList) {
        if (list != null && list.size() != 0) {
            List<String> ids = checkId(list);
            if (ids.size() == 0) {
                return allList;
            } else {
                List<Ztree> parentList = service.findByIds(ids);
                allList.addAll(parentList);
                findParentList(parentList, allList);
            }
        }
        return allList;
    }

    private List<String> checkId(List<Ztree> list) {
        List<String> idsList = new ArrayList<>();
        for (Ztree z : list) {
            if (!"0".equals(z.getPid())) {
                idsList.add(z.getPid());
            }
        }
        return idsList;
    }

    /**
     * 添加树
     */
    @RequestMapping(value = "/addZtree", method = RequestMethod.GET)
    @ResponseBody
    public void addZtree(@RequestParam("model") String model, @RequestParam(value = "id") String id, @RequestParam(value = "name") String name, @RequestParam(value = "pid") String pid) {
        id = UUID.randomUUID().toString();
        Ztree ztree = new Ztree();
        ztree.setId(id);
        ztree.setPid(pid);
        ztree.setName(encod(name, "UTF-8"));
        service.addZtree(ztree, model);
    }

    /**
     * 修改树
     */
    @RequestMapping(value = "/updateZtree", method = RequestMethod.GET)
    @ResponseBody
    public void updateZtree(@RequestParam("model") String model, @RequestParam(value = "id") String id, @RequestParam(value = "name") String name) {
        Ztree ztree = new Ztree();
        ztree.setId(id);
        ztree.setName(encod(name, "UTF-8"));
        service.updateZtree(ztree, model);
    }

    /*
     *删除树
     */
    @RequestMapping(value = "/deleteZtree", method = RequestMethod.GET)
    @ResponseBody
    public void deleteZtree(@RequestParam("model") String model, @RequestParam List<String> array) {
        service.deleteZtree(array, model);
    }

    /*
     * 添加/修改前验证
     */
    @RequestMapping(value = "checkName", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkName(Model model, @RequestParam("name") String name, @RequestParam("model") String modelName) {
        return service.checkNode(encod(name, "UTF-8"), modelName);
    }

    private String encod(String str, String charsetName) {
        try {
            return new String(str.getBytes("ISO-8859-1"), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
