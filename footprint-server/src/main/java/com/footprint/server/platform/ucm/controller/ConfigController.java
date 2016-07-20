package com.footprint.server.platform.ucm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.footprint.common.WebResult;
import com.footprint.common.utils.Assert;
import com.footprint.server.platform.ucm.model.ConfigInfo;
import com.footprint.server.platform.ucm.service.ConfigService;

@Controller
@RequestMapping("/ucm")
public class ConfigController {
	
	@Autowired
	private ConfigService configService;
	
    @RequestMapping(value = "/getNodeList")
    @ResponseBody
    public WebResult getNodeList(String path) {
    	if(path == null) {
    		path = "";
    	}
    	List<String> names = configService.getChildren(path);
        WebResult result = WebResult.newResult();
        result.put("list", names);
        return result;
    }
    
    @RequestMapping("getConfig")
    @ResponseBody
    public WebResult getConfig(String path) {
    	Assert.notEmpty(path, "Node path not empty");
    	ConfigInfo info = configService.getConfig(path);
        WebResult result = WebResult.newResult();
        result.put("info", info);
        return result;
    }
    
    @RequestMapping("writeConfig")
    @ResponseBody
    public WebResult writeConfig(String path, String value) {
    	Assert.notEmpty(path, "Node path not empty");
    	configService.writeConfig(path, value);
        return WebResult.newResult();
    }
    
    @RequestMapping("deleteConfig")
    @ResponseBody
    public WebResult deleteConfig(String path) {
    	Assert.notEmpty(path, "Node path not empty");
    	ConfigInfo info = configService.getConfig(path);
    	if(info != null && info.getNumChildren()>0) {
    		return WebResult.newErrorResult("删除父节点之前，请先删除子节点");
    	}
    	configService.deleteConfig(path);
        return WebResult.newResult();
    }
}
