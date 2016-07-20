package com.footprint.server.monitor.zookeeper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.footprint.common.WebResult;
import com.footprint.common.utils.Assert;
import com.footprint.server.monitor.zookeeper.entity.ZkCluster;
import com.footprint.server.monitor.zookeeper.model.ZkNode;
import com.footprint.server.monitor.zookeeper.service.ZkManageService;

@Controller
@RequestMapping("/zk")
public class ZkController {
	
	@Autowired
	private ZkManageService zkManageService;
	
    @RequestMapping("clusterList")
    @ResponseBody
    public WebResult getZkClusterList() {
    	List<ZkCluster> list = zkManageService.getAllCluster();
    	WebResult result = WebResult.newResult();
        result.put("list", list);
        return result;
    }
    
    @RequestMapping(value = "/getChildren")
    @ResponseBody
    public WebResult getChildren(Long clusterId, String path) {
    	if(path == null) {
    		path = "";
    	}
    	List<String> names = zkManageService.getChildren(clusterId, path);
        WebResult result = WebResult.newResult();
        result.put("list", names);
        return result;
    }
    
    @RequestMapping("getNode")
    @ResponseBody
    public WebResult getNode(Long clusterId, String path) {
    	Assert.notEmpty(path, "Node path not empty");
    	ZkNode info = zkManageService.getNode(clusterId, path);
        WebResult result = WebResult.newResult();
        result.put("info", info);
        return result;
    }
    
    @RequestMapping("createNode")
    @ResponseBody
    public WebResult createNode(Long clusterId, String path, String value) {
    	Assert.notEmpty(path, "Node path not empty");
    	zkManageService.createNode(clusterId, path, value);
        return WebResult.newResult();
    }
    
    @RequestMapping("writeData")
    @ResponseBody
    public WebResult writeData(Long clusterId, String path, String value) {
    	Assert.notEmpty(path, "Node path not empty");
    	zkManageService.writeData(clusterId, path, value);
        return WebResult.newResult();
    }
    
    @RequestMapping("deleteNode")
    @ResponseBody
    public WebResult deleteNode(Long clusterId, String path) {
    	Assert.notEmpty(path, "Node path not empty");
    	ZkNode info = zkManageService.getNode(clusterId, path);
    	if(info != null && info.getNumChildren()>0) {
    		return WebResult.newErrorResult("删除父节点之前，请先删除子节点");
    	}
    	zkManageService.deleteNode(clusterId, path);
        return WebResult.newResult();
    }
}
