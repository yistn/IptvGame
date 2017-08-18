package com.System.IptvGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class utils {

	public static final String style = "position:absolute; left:0px; top:0px; width:640px; height:526px; z-index:1";
	public static final String gameparam = "<key=\"-Xkeypass\" value=\"true\" //>  \n  <key=\"bgcolor\" value=\"#000000\" //>";

	
	public static List<Map<String, String>> formatParam(String param)
	{
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		String NoParseString = param;
		boolean parseflag = true;
		while(parseflag)
		{
			Map<String, String> ParamMap = new HashMap<String, String>();
			int index = NoParseString.indexOf("key=\"");
			if(index == -1)
			{
				parseflag = false;
				continue;
			}
			int start = index + "key=\"".length();
			int end = NoParseString.indexOf("\"",start);
			String key = NoParseString.substring(start, end);
			
			NoParseString = NoParseString.substring(end+1);
			
			index = NoParseString.indexOf("value=\"");
			if(index == -1)
			{
				parseflag = false;
				continue;
			}
			start = index + "value=\"".length();
			end = NoParseString.indexOf("\"",start);
			String value = NoParseString.substring(start, end);
			
			NoParseString = NoParseString.substring(end+1);
			
			ParamMap.put("key", key);
			ParamMap.put("value", value);
			list.add(ParamMap);

		}


		return list;
	}
	
	
//	public static GameParam parsegamestyle(String style) {
//		GameParam gamestyle = new GameParam();
//
//		try {
//
//			int leftindex = style.indexOf("left");
//			String str = style.substring(leftindex);
//			int leftvalueindex = str.indexOf("px");
//			gamestyle.left = Integer.parseInt(str.substring(5, leftvalueindex)
//					.trim());
//
//			int topindex = style.indexOf("top");
//			str = style.substring(topindex);
//			int topvalueindex = str.indexOf("px");
//			gamestyle.top = Integer.parseInt(str.substring(4, topvalueindex)
//					.trim());
//
//			int widthindex = style.indexOf("width");
//			str = style.substring(widthindex);
//			int widthvalueindex = str.indexOf("px");
//			gamestyle.width = Integer.parseInt(str
//					.substring(6, widthvalueindex).trim());
//
//			int heightindex = style.indexOf("height");
//			str = style.substring(heightindex);
//			int heightvalueindex = str.indexOf("px");
//			gamestyle.height = Integer.parseInt(str.substring(7,
//					heightvalueindex).trim());
//
//		} catch (Exception e) {
//			return null;
//		}
//
//		return gamestyle;
//
//	}

}
