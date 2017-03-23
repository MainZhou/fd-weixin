package com.boyoi.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.utils.DateTimeUtil;
import com.boyoi.core.utils.HttpUtils;
import com.boyoi.work.domain.PaperPhoto;
import com.boyoi.work.domain.PaperSales;
import com.boyoi.work.utils.WxUserInfoSaveUtil;
import com.boyoi.work.utils.WxUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * 消费者查询
 *
 * @author zhouJL
 */
@Controller
@RequestMapping(value = "batchMakeup")
public class BatchMakeupController {


    protected Logger LOG;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model) {
        List<Map<String, String>> list = (List<Map<String, String>>) request.getSession().getAttribute("imgList2");
        model.addAttribute("lists", list);
        return "makeup/batchMakeup";
    }

    @RequestMapping(value = "photographUI", method = RequestMethod.GET)
    public String photograph(HttpServletRequest request) {
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);
        if (userInfo == null) {
            return "redirect:/wxLogin";
        }
        return "makeup/batchPhoto";
    }

    @RequestMapping(value = "saveImg", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult saveImg(String img, int index,String salesTime ,HttpSession session) {
        ResponseResult responseResult = new ResponseResult();
        salesTime=salesTime.replace("T"," ");
        salesTime=salesTime+":00";
        List<Map<String, String>> list = (List<Map<String, String>>) session.getAttribute("imgList2");
        if (list != null) {
            if (index != -1) {
                Map<String, String> map = new HashMap<>();
                map.put("img", img);
                map.put("time", salesTime);
                list.set(index, map);
                session.setAttribute("imgList2", list);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("img", img);
                map.put("time", salesTime);
                list.add(map);
                session.setAttribute("imgList2", list);
            }
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("img", img);
            map.put("time", salesTime);
            list = new ArrayList<>();
            list.add(map);
            session.setAttribute("imgList2", list);
        }
        responseResult.setStatus(true);
        return responseResult;
    }

    @RequestMapping(value = "addImgs", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult addImgs(@RequestBody List<PaperPhoto> imgs, HttpSession session, HttpServletRequest request) {
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);
        ResponseResult responseResult = new ResponseResult();
        String accessToken = WxUtils.getAccessToken2();
        File[] files = new File[imgs.size()];
        for (int i = 0; i < imgs.size(); i++) {
            File file = WxUtils.downloadMedia(accessToken, imgs.get(i).getPath());
            files[i] = file;
            imgs.get(i).setPath(file.getName());
        }
        PaperSales sales = new PaperSales();
        sales.setWholesalerId(((JSONObject) userInfo.get("userInfo")).get("wholesalerId").toString());
        sales.setStockTime(DateTimeUtil.yyyyMMddhhmmss(new Date()));
        sales.setData(imgs);
        String entrRecordUrl = WxUtils.baseUrl + "/purchaseRecords/fd_ws_makeUpListNew";
        Map<String, String> para = new HashMap<>();
        para.put("jsonStr", JSON.toJSONString(sales));
        System.out.println(JSON.toJSONString(sales));
        JSONObject jsonObject = HttpUtils.postWithToken(entrRecordUrl, para, files);
        System.out.println(JSONObject.toJSON(jsonObject));
        if (null != jsonObject) {
            responseResult.setMsg("保存成功");
            responseResult.setStatus(true);
            session.removeAttribute("imgList2");
        } else {
            // 查询失败
            responseResult.setStatus(false);
            responseResult.setMsg("没有数据");
        }
        return responseResult;
    }
}
