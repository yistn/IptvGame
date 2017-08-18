package com.System.IptvGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class GameParam {
	
//	<key="-Xkeypass" value="true" />
//	<key="bgcolor" value="#000000" />
//	<key="jad" value="http://192.168.180.240/DreamKarting.jad" />
//	<key="jar" value="http://192.168.180.240/DreamKarting.jar" />
//	<key="J2MEVersion" value=" MIDP 2.0,CLDC 1.1" />
//	<key="ServerURL" value="http://172.0.10.16/Platform3CPHttpService/HttpService/" />
//	<key="Account" value="e4b78353c1a65efc" />
//	<key="ADAccount" value="i5201907751@itv" />
//	<key="GameID" value="117007" />
//	<key="UserToken" value="776fbe6f7704283e362c734064f4a850" />
//	<key="IsAutoTopUp" value="false" />
//	<key="width" value="640" />
//	<key="height" value="530" />
	public static final String gameparam = "<key=\"-Xkeypass\" value=\"true\" //>  \n  <key=\"bgcolor\" value=\"#000000\" //>";

	public static final String key_jad = "jad";
	public static final String key_jar = "jar";
	public static final String key_width = "width";
	public static final String key_height = "height";	
	
	public List<Map<String, String>> ParamList = null;;
	
	public String jad;	
	public String jar;
	public String height;
	public String width;

	public void parseParam(String param)
	{
		if(param == null)
			return ;
		if(ParamList == null)
			ParamList = new ArrayList<Map<String,String>>();
		else
			ParamList.clear();
		
		String NoParseString = param;

		while(true)
		{
			Map<String, String> ParamMap = new HashMap<String, String>();
			int index = NoParseString.indexOf("key=\"");
			if(index == -1)
			{
				break;
			}
			int start = index + "key=\"".length();
			int end = NoParseString.indexOf("\"",start);
			String key = NoParseString.substring(start, end);
			
			NoParseString = NoParseString.substring(end+1);
			
			index = NoParseString.indexOf("value=\"");
			if(index == -1)
			{
				break;
			}
			start = index + "value=\"".length();
			end = NoParseString.indexOf("\"",start);
			String value = NoParseString.substring(start, end);
			
			NoParseString = NoParseString.substring(end+1);
			
			initField(key,value);
			ParamMap.put("key", key);
			ParamMap.put("value", value);
			ParamList.add(ParamMap);

		}

	}
	
	public void initField(String key,String value)
	{
		if(key_jad.equals(key))
		{
			jad = value;
		}
		else if(key_jar.equals(key))
		{
			jar = value;
		}
		else if(key_width.equals(key))
		{
			width = value;
		}
		else if(key_height.equals(key))
		{
			height = value;
		}
		
		
	}
	
	
	
	public String toString()
	{
		String str = "\n\n\n\nparseParam:\n";
		Iterator iterator = ParamList.iterator();

		while(iterator.hasNext())
		{
			Map<String, String> map = (Map<String, String>)iterator.next();
			String key = map.get("key");
			String value = map.get("value");
			str += "("+key + ","+value+")\n";
		}
		
		str+="\n\n\n\n";
		
		return str;
	}
	
	public void test()
	{
		parseParam(gameparam);
		Log.d("NON", toString());
	}
	
}
