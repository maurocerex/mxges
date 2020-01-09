package com.metlife.nm.domain;

import java.util.Map;

public class SecurityMap {
	private Map<String, String> actionsMap;
	
	public String getMenuId(String actionName){
		return actionsMap.get(actionName);
	}

	public void setActionsMap(Map<String, String> actionsMap) {
		this.actionsMap = actionsMap;
	}

	public Map<String, String> getActionsMap() {
		return actionsMap;
	}
	
}
