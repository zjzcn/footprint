package com.footprint.server.platform.ucm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.footprint.client.ucm.ConfigNode;
import com.footprint.client.ucm.UcmClient;
import com.footprint.server.platform.ucm.model.ConfigInfo;
import com.footprint.server.platform.ucm.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Override
	public ConfigInfo getConfig(String path) {
    	UcmClient client = UcmClient.getInstance();
    	ConfigNode cNode = client.getConfigNode(path);
    	if(cNode == null) return null;
    	ConfigInfo info = new ConfigInfo();
    	info.setPath(cNode.getPath());
    	info.setValue(cNode.getValue());
    	info.setCreateTime(cNode.getCreateTime());
    	info.setModifyTime(cNode.getModifyTime());
    	info.setNumChildren(cNode.getNumChildren());
    	return info;
	}
	
	@Override
	public void writeConfig(String path, String value) {
    	UcmClient client = UcmClient.getInstance();
    	client.writeConfig(path, value);
	}
	
	@Override
	public void deleteConfig(String path) {
    	UcmClient client = UcmClient.getInstance();
    	client.deleteConfig(path);
	}
	
	@Override
	public List<String> getChildren(String parentPath) {
    	UcmClient client = UcmClient.getInstance();
    	return client.getChildren(parentPath);
	}
}
