package com.NON.iptvgame;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class JAROrJADInfo
{
	public String name;
	public String url;
	public String saveurl;
	public String packagename;
	public int downloadid;
	public Boolean downloadfinished = false;
	private String sdcardsavepath;

	JAROrJADInfo()
	{
		sdcardsavepath = Environment.getExternalStorageDirectory().getPath() + "/jzJAVAGame";
		//sdcardsavepath = "/data/data/com.NON.iptvgame/foundation";
		File sdcardsavedir = new File(sdcardsavepath);
		if (!sdcardsavedir.exists())
		{
			sdcardsavedir.mkdir();
		}
	}

	public static void clear()
	{
		String str = Environment.getExternalStorageDirectory().getPath() + "/jzJAVAGame";
		//String str = "/data/data/com.NON.iptvgame/foundation";
		File gamefile = new File(str);
		if (gamefile.exists())
		{
			if(gamefile.isDirectory())
			{
				File[] childFiles = gamefile.listFiles();
				if(childFiles.length > 20)
				{
					delete(gamefile);
				}
			}
			
		}
	}

	public static void delete(File file)
	{
		if (file.isFile())
		{
			file.delete();
			return;
		}

		if (file.isDirectory())
		{
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0)
			{
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++)
			{
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

	public String tostring()
	{
		return "name:" + name + ";url:" + url + ";saveurl:" + saveurl + ";packagename:" + packagename + ";downloadid" + downloadid + ";sdcardsavepath:" + sdcardsavepath;
	}

	public void GetName()
	{
		if (url != null)
		{
			String[] temparray = url.split("/");
			if (temparray.length > 1)
			{
				name = temparray[temparray.length - 1];
			}
		}
	}

	public void GetSaveURL()
	{
		if (name != null) saveurl = sdcardsavepath + "/" + name;
	}

	public Boolean Isexists()
	{
		if (saveurl != null)
		{
			File file = new File(saveurl);
			if (file.exists())
				downloadfinished = true;
			else
				downloadfinished = false;
		}
		return downloadfinished;
	}

	public String GetPackagename()
	{
		if (saveurl != null) packagename = ParseJAD.getpackagename(saveurl);
		return packagename;
	}

}
