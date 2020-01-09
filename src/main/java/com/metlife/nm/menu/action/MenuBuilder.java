package com.metlife.nm.menu.action;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.metlife.nm.domain.UserVO;

public class MenuBuilder {
	
	private static final Logger log = Logger.getLogger(MenuBuilder.class);

	private UserVO user;
	
	public void buildMenuForUser(UserVO user) {
		this.user = user;
		 
		JSONObject messages = new JSONObject();
		JSONArray array = new JSONArray();
		array = parseJson(COMPLETE_MENU);
		filtraMenu(array);
		if(log.isDebugEnabled()){
			//log.debug(array.toString(2));
		}
		messages.put("ResultSet", array);
		messages.put("success", true);
		user.setJsonMenu(messages.toString());
	}
	
	private final JSONArray parseJson(String jsonString) throws RuntimeException {
	   
		JSONArray jsonArray = new JSONArray();
		try {
			if (jsonString != null) {
				jsonArray = JSONArray.fromObject(jsonString);
			}
			return jsonArray;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	private void filtraMenu(JSONArray array) {
		for(int i=0; i<array.size();i++){
			JSONObject jsonObject = array.getJSONObject(i);
			if(jsonObject.containsKey("submenu")){
				filtraMenu(jsonObject.getJSONObject("submenu").getJSONArray("itemdata"));
				//checamos si quedo sin hijos al final
				if(jsonObject.getJSONObject("submenu").getJSONArray("itemdata").size()==0){
					array.remove(i);
					i--;
				}
			}
			else {
				if(!user.hasIdMenu(jsonObject.getString("id"))){
					array.remove(i);
					i--;
				}
			}
		}
		
	}

    private static String COMPLETE_MENU = menu();

	private static String menu(){
	    URL url = MenuBuilder.class.getResource("menu.js");
	    File file = new File(url.getPath());

//	    File file = new File("menu.js");
	    int ch;
	    StringBuffer strContent = new StringBuffer("");
	    FileInputStream fin = null;
	    try {
	      fin = new FileInputStream(file);
	      while ((ch = fin.read()) != -1)
	        strContent.append((char) ch);
	      fin.close();
	    } catch (Exception e) {
	      log.error(e.toString());
	    }  
	    return strContent.toString();
	}
}
