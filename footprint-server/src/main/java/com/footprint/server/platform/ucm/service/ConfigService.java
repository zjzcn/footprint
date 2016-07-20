package com.footprint.server.platform.ucm.service;

import java.util.List;

import com.footprint.server.platform.ucm.model.ConfigInfo;

public interface ConfigService {

	ConfigInfo getConfig(String path);

	void writeConfig(String path, String value);

	void deleteConfig(String path);

	List<String> getChildren(String parentPath);
	
}
