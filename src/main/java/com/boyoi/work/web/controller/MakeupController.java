package com.boyoi.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.utils.DateTimeUtil;
import com.boyoi.core.utils.HttpUtils;
import com.boyoi.work.domain.Makeup;
import com.boyoi.work.domain.MakeupInfo;
import com.boyoi.work.domain.MakeupPhoto;
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
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 消费者查询
 *
 * @author zhouJL
 */
@Controller
@RequestMapping(value = "makeup")
public class MakeupController {

    protected Logger LOG;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, int index, int checkIndex) {
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);
        if (userInfo == null) {
            return "redirect:/ wxLogin";
        }
        return "makeup/makeup";
    }

    @RequestMapping(value = "batch", method = RequestMethod.GET)
    public String batchUI(Model model) {
        return "makeup/batchMakeup";
    }

    @RequestMapping(value = "batchPhoto", method = RequestMethod.GET)
    public String batchPhotoUI() {
        return "makeup/batchPhoto";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult listData(HttpServletRequest request, int index, int checkIndex) {
        ResponseResult responseResult = new ResponseResult();
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);
        Map<String, Object> resultMap;
        Map<String, String> dataMap = new HashMap<>();
        Date now = new Date();
        if (userInfo != null) {
            dataMap.put("wholesalerId", (String) ((JSONObject) userInfo.get("userInfo")).get("wholesalerId"));
            if (index == 0) {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(now, Calendar.DATE, -30)));
            } else if (index == 1) {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(new Date(), Calendar.DATE, -60)));
            } else {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(new Date(), Calendar.DATE, -90)));
            }
            dataMap.put("endTime", DateTimeUtil.yyyyMMddhhmmss(now));
            if (checkIndex == 0) {
                dataMap.put("makeupType", "U");
            } else if (checkIndex == 2) {
                dataMap.put("makeupType", "N");
            } else if (checkIndex == 3) {
                dataMap.put("makeupType", "P");
            }
        }
        dataMap.put("pageNum","1");
        dataMap.put("pageSize","100");

        //  接口地址：http://192.168.1.20:8080/fd-web/saleRecords/fd_ws_dealRecordPaper
        String entrRecordUrl = WxUtils.baseUrl + "/purchaseRecords/fd_ws_makeupRecordsNew";
        String jsonStr = "jsonStr=" + JSON.toJSONString(dataMap);
        JSONObject jsonObject = HttpUtils.postWithToken(entrRecordUrl, jsonStr);
        if (null != jsonObject) {
            responseResult.setMsg("查询成功");
            responseResult.setStatus(true);
            resultMap = JSON.parseObject(JSON.toJSONString(jsonObject), HashMap.class);
            responseResult.setResultMap(resultMap);
        } else {
            // 查询失败
            responseResult.setStatus(false);
            responseResult.setMsg("没有数据");
        }
        return responseResult;
    }

    @RequestMapping(value = "addUI", method = RequestMethod.GET)
    public String addUI(HttpServletRequest request) {
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);
        if (userInfo == null) {
            return "redirect:/wxLogin";
        }
        return "makeup/makeupAdd";
    }

    @RequestMapping(value = "makeupAdd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult makeupAdd(@RequestBody MakeupInfo makeupInfo, int apply, HttpServletRequest request) {
        ResponseResult responseResult = new ResponseResult();//返回数据对象
        Map<String, Object> resultMap;//返回数据集合
        Makeup makeup = new Makeup();//主记录
        DecimalFormat df = new DecimalFormat("######0.00");//数字转换
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);//当前登录用户信息
        if (userInfo != null) {
            String stockTime = DateTimeUtil.yyyyMMddhhmmss(new Date());//获取当前时间并做为建档时间与进货时间
            String[] ids = makeupInfo.getSourcePlaceId().split(",");
            makeupInfo.setSourcePlaceId(ids[ids.length - 1]);//重新剪切产地Id

            //设置主记录参数
            String wholeSalerId = (String) ((JSONObject) userInfo.get("userInfo")).get("wholesalerId");
            String wmId = (String) ((JSONObject) userInfo.get("userInfo")).get("deptId");
            makeup.setWholesalerId(wholeSalerId);
            makeup.setWmId(wmId);
            makeup.setStockTime(stockTime);
            makeup.setCreateTime(stockTime);

            if (makeupInfo.getPrice() != null) {
                makeup.setTotalPrice(df.format(
                        Double.parseDouble(makeupInfo.getWeight()) * Double.parseDouble(makeupInfo.getPrice())
                ));
            } else {
                makeup.setTotalPrice("0.00");
            }
            makeup.setTotalWeight(makeupInfo.getWeight());

            //设置进货明细参数
            makeupInfo.setStockTime(stockTime);
            makeupInfo.setCreateTime(stockTime);
            String accessToken = WxUtils.getAccessToken2();
            List<File> list = new ArrayList<>();
            //遍历图片数据，并从微信服务器上把图片取下来并重新设置路径等
            for(MakeupPhoto photo : makeupInfo.getPhotoData()){
                File file = WxUtils.downloadMedia(accessToken, photo.getPath());
                list.add(file);
                photo.setPath(photo.getPath()+".jpg");
                photo.setDetectTaskId("");
            }
            //当前需要上传的图片
            File[] files = new File[list.size()];
            for (int i = 0; i < list.size(); i++) {
                files[i] = list.get(i);
            }
            //是否检测
            if (apply == 0) {
               MakeupPhoto makeupPhoto =new MakeupPhoto();
                makeupPhoto.setPath("");
                makeupPhoto.setDocumentType("J");
                makeupPhoto.setDetectTaskId("");
                makeupInfo.getPhotoData().add(makeupPhoto);
            }
            List<MakeupInfo> data = new ArrayList<>();
            data.add(makeupInfo);
            makeup.setData(data);
            String entrRecordUrl = WxUtils.baseUrl + "/purchaseRecords/fd_ws_makeUpNew";
            Map<String, String> para = new HashMap<>();
            para.put("jsonStr", JSON.toJSONString(makeup));
            JSONObject jsonObject = HttpUtils.postWithToken(entrRecordUrl, para, files);
            if (null != jsonObject) {
                responseResult.setStatus(true);
                resultMap = JSON.parseObject(JSON.toJSONString(jsonObject), HashMap.class);
                responseResult.setResultMap(resultMap);
            } else {
                // 查询失败
                responseResult.setStatus(false);
                responseResult.setMsg("添加失败");
            }
        }
        return responseResult;
    }

    @RequestMapping(value = "infoUI", method = RequestMethod.GET)
    public String infoUI(HttpServletRequest request) {
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);
        if (userInfo == null) {
            return "redirect:/wxLogin";
        }
        return "makeup/makeupInfo";
    }

    @RequestMapping(value = "info", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult infoData(HttpServletRequest request, int index, int checkIndex, int dataIndex) {
        ResponseResult responseResult = new ResponseResult();
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);
        Map<String, Object> resultMap;
        Map<String, String> dataMap = new HashMap<>();
        Date now = new Date();
        if (userInfo != null) {
            dataMap.put("wholesalerId", (String) ((JSONObject) userInfo.get("userInfo")).get("wholesalerId"));
            if (index == 0) {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(now, Calendar.DATE, -30)));
            } else if (index == 1) {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(new Date(), Calendar.DATE, -60)));
            } else {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(new Date(), Calendar.DATE, -90)));
            }
            dataMap.put("endTime", DateTimeUtil.yyyyMMddhhmmss(now));
            if (checkIndex == 0) {
                dataMap.put("makeupType", "U");
            } else if (checkIndex == 1) {
                dataMap.put("makeupType", "O");
            } else if (checkIndex == 2) {
                dataMap.put("makeupType", "N");
            } else if (checkIndex == 3) {
                dataMap.put("makeupType", "P");
            }
        }

        //  接口地址：http://192.168.1.20:8080/fd-web/saleRecords/fd_ws_dealRecordPaper
        String entrRecordUrl = WxUtils.baseUrl + "/purchaseRecords/fd_ws_makeupRecords";
        String jsonStr = "jsonStr=" + JSON.toJSONString(dataMap);
        JSONObject jsonObject = HttpUtils.postWithToken(entrRecordUrl, jsonStr);
        if (null != jsonObject) {
            responseResult.setMsg("查询成功");
            responseResult.setStatus(true);
            resultMap = JSON.parseObject(JSON.toJSONString(jsonObject), HashMap.class);
            resultMap = ((List<Map<String, Object>>) resultMap.get("listData")).get(dataIndex);
            responseResult.setResultMap(resultMap);
        } else {
            // 查询失败
            responseResult.setStatus(false);
            responseResult.setMsg("没有数据");
        }
        return responseResult;
    }

    @RequestMapping(value = "updateUI", method = RequestMethod.GET)
    public String updateUI(HttpServletRequest request, Model model) {
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);

        if (userInfo == null) {
            return "redirect:/wxLogin";
        }
        Map<String, String> dataMap2 = new HashMap<>();
        dataMap2.put("keyWords", "");
        //  接口地址：http://192.168.1.20:8080/fd-web/saleRecords/fd_ws_dealRecordPaper
        String entrRecordUrl2 = WxUtils.baseUrl + "/types/fd_type_search";
        String jsonStr2 = "jsonStr=" + JSON.toJSONString(dataMap2);
        JSONObject jsonObject2 = HttpUtils.postWithToken(entrRecordUrl2, jsonStr2);
        if (null != jsonObject2) {
            model.addAttribute("types", JSON.parseObject(JSON.toJSONString(jsonObject2.get("typeInfo")), ArrayList.class));
        }
        return "makeup/makeupUpdate";

    }

    @RequestMapping(value = "updateInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateInfo(HttpServletRequest request, int index, int checkIndex, int dataIndex) {
        ResponseResult responseResult = new ResponseResult();
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);
        Map<String, Object> resultMap;
        Map<String, String> dataMap = new HashMap<>();
        Date now = new Date();
        if (userInfo != null) {
            dataMap.put("wholesalerId", (String) ((JSONObject) userInfo.get("userInfo")).get("wholesalerId"));
            if (index == 0) {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(now, Calendar.DATE, -30)));
            } else if (index == 1) {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(new Date(), Calendar.DATE, -60)));
            } else {
                dataMap.put("startTime", DateTimeUtil.yyyyMMddhhmmss(DateTimeUtil.timeAdd(new Date(), Calendar.DATE, -90)));
            }
            dataMap.put("endTime", DateTimeUtil.yyyyMMddhhmmss(now));
            if (checkIndex == 0) {
                dataMap.put("makeupType", "U");
            }else if (checkIndex == 2) {
                dataMap.put("makeupType", "N");
            } else if (checkIndex == 3) {
                dataMap.put("makeupType", "P");
            }
            //  接口地址：http://192.168.1.20:8080/fd-web/saleRecords/fd_ws_dealRecordPaper
            String entrRecordUrl = WxUtils.baseUrl + "/purchaseRecords/fd_ws_makeupRecordsNew";
            String jsonStr = "jsonStr=" + JSON.toJSONString(dataMap);
            JSONObject jsonObject = HttpUtils.postWithToken(entrRecordUrl, jsonStr);
            if (null != jsonObject) {
                resultMap = JSON.parseObject(JSON.toJSONString(jsonObject), HashMap.class);
                resultMap = JSON.parseObject(JSON.toJSONString(((List<Map<String, Object>>) resultMap.get("data")).get(dataIndex)), HashMap.class);
                responseResult.setResultMap(resultMap);
                responseResult.setStatus(true);
            }
        }
        return responseResult;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@RequestBody MakeupInfo makeupInfo, int apply, HttpServletRequest request) {
        ResponseResult responseResult = new ResponseResult();
        Map<String, Object> resultMap;
        JSONObject userInfo = WxUserInfoSaveUtil.get(request);
        if (userInfo != null) {
            String stockTime = DateTimeUtil.yyyyMMddhhmmss(new Date());
            String[] ids = makeupInfo.getSourcePlaceId().split(",");
            makeupInfo.setSourcePlaceId(ids[ids.length - 1]);

            String wholeSalerId = (String) ((JSONObject) userInfo.get("userInfo")).get("wholesalerId");
            Makeup makeup = new Makeup();
            makeup.setWholesalerId(wholeSalerId);

            makeupInfo.setStockTime(stockTime);

            String accessToken = WxUtils.getAccessToken2();
            List<File> list = new ArrayList<>();
            for(MakeupPhoto photo : makeupInfo.getPhotoData()){
                if(!"".equals(photo.getPath()) || !"".equals(photo.getGuid())){//如果是第一次上传图片
                    File file = WxUtils.downloadMedia(accessToken, photo.getPath());
                    list.add(file);
                    photo.setPath(photo.getPath()+".jpg");
                    photo.setDetectTaskId("");
                }
            }
            File[] files = new File[list.size()];
            for (int i = 0; i < list.size(); i++) {
                files[i] = list.get(i);
            }
            if (apply == 0) {
                MakeupPhoto makeupPhoto = new MakeupPhoto();
                makeupPhoto.setDocumentType("J");
                makeupPhoto.setPath("");
                makeupInfo.getPhotoData().add(makeupPhoto);
            }
            List<MakeupInfo> listInfo = new ArrayList<>();
            listInfo.add(makeupInfo);
            makeup.setData(listInfo);
            String entrRecordUrl = WxUtils.baseUrl + "/purchaseRecords/fd_ws_makeupUpdateNew";
            Map<String, String> para = new HashMap<>();
            para.put("jsonStr", JSON.toJSONString(makeup));
            JSONObject jsonObject = HttpUtils.postWithToken(entrRecordUrl, para, files);
            if (null != jsonObject) {
                responseResult.setStatus(true);
                resultMap = JSON.parseObject(JSON.toJSONString(jsonObject), HashMap.class);
                responseResult.setResultMap(resultMap);
            } else {
                // 查询失败
                responseResult.setStatus(false);
                responseResult.setMsg("添加失败");
            }
        }
        return responseResult;
    }


    @RequestMapping(value = "findType", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult findType() {
        ResponseResult responseResult = new ResponseResult();
        Map<String, String> dataMap = new HashMap<>();
        Map<String, Object> resultMap;
        dataMap.put("keyWords", "");
        //  接口地址：http://192.168.1.20:8080/fd-web/saleRecords/fd_ws_dealRecordPaper
        String entrRecordUrl = WxUtils.baseUrl + "/types/fd_type_search";
        String jsonStr = "jsonStr=" + JSON.toJSONString(dataMap);
        JSONObject jsonObject = HttpUtils.postWithToken(entrRecordUrl, jsonStr);
        if (null != jsonObject) {
            resultMap = JSON.parseObject(JSON.toJSONString(jsonObject), HashMap.class);
            String code = (String) JSON.parseObject(resultMap.get("status").toString(), HashMap.class).get("code");
            if (code != null && "1".equals(code)) {
                responseResult.setMsg("查询成功");
                responseResult.setStatus(true);
                responseResult.setResultMap(JSON.parseObject(resultMap.get("data").toString(), HashMap.class));
            } else {
                // 查询失败
                responseResult.setStatus(false);
                responseResult.setMsg("没有数据");
            }
        } else {
            // 查询失败
            responseResult.setStatus(false);
            responseResult.setMsg("没有数据");
        }
        return responseResult;
    }

    @RequestMapping(value = "detectInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult detectInfo(String detectTaskId) {
        ResponseResult responseResult = new ResponseResult();
        Map<String, String> dataMap = new HashMap<>();
        Map<String, Object> resultMap;
        dataMap.put("detectTaskId", detectTaskId);
        String entrRecordUrl = WxUtils.baseUrl + "/detectTask/fd_query_hisCheckTypes";
        String jsonStr = "jsonStr=" + JSON.toJSONString(dataMap);
        JSONObject jsonObject = HttpUtils.postWithToken(entrRecordUrl, jsonStr);
        if (null != jsonObject) {
            responseResult.setMsg("查询成功");
            responseResult.setStatus(true);
            resultMap = JSON.parseObject(JSON.toJSONString(jsonObject), HashMap.class);
            responseResult.setResultMap(resultMap);
        } else {
            // 查询失败
            responseResult.setStatus(false);
            responseResult.setMsg("没有数据");
        }
        return responseResult;
    }
}
