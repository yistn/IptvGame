package com.NON.iptvgame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParseJAD
{
	
	public static String getpackagename(String url)
	{
		String str2 = "";
		InputStream in = null;
		InputStreamReader ir = null;
		BufferedReader bufferedReader = null;
		File jad = new File(url);
		if(jad.exists())
		{
			
			try
			{
				in = new FileInputStream(jad);
				ir = new InputStreamReader(in);
				bufferedReader = new BufferedReader(ir);
				while ((str2 = bufferedReader.readLine()) != null)
				{
					if(str2.contains("MIDlet-1"))
					{
						break;
					}
				}
				
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (bufferedReader != null)
					{
						bufferedReader.close();
					}
					
					if(ir != null)
					{
						ir.close();
					}
					
					if(in != null)
					{
						in.close();
					}
					
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
		}
		if(str2 != null)
		{
			String[] array = str2.split(",");
			if(array.length > 1)
			{
				//lrlong add ignore space
				for(int i = 0; i < array.length; i ++)
				{
					array[i] = array[i].trim();
				}
				return array[array.length - 1];
			}
		}
		
		
		return null;
	}
}
