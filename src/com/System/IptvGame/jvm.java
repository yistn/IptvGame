package com.System.IptvGame;



import java.io.File;


import com.NON.iptvgame.GameActivity;
import com.NON.iptvgame.IListenJVMMsg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class jvm {
	
	
	public static final int RUN_GAME_FAIL = 2;
	public static final int RUN_GAME_START = 0;
	public static final int RUN_GAME_SUCCESS = 1;
	public static int RunGameState = 1;
	public static IListenJVMMsg listenjvmmsg;

    private static void DispatchForRequest(int nType, String strUrl) {
        Intent intent = new Intent();
        System.out.println(nType + ", strUrl:" + strUrl);
        intent.setAction("ACTION_DATA_TO_IPTV");
        intent.putExtra("type", 0xb);
        intent.putExtra("GameType", nType);
        intent.putExtra("url", strUrl);
        Message msg = new Message();
        msg.what = 0x2;
        msg.obj = intent;
        GameActivity.handler.sendMessage(msg);
    }
    
    private static void SetRunGameState(int state) {
        if(0x1 == state) {
        	GameActivity.handler.sendEmptyMessageDelayed(0x5, 0x3e8);
            return;
        }
        if(0x2 == state) {
        	GameActivity.handler.sendEmptyMessageDelayed(0x6, 0x3e8);
            return;
        }
        GameActivity.itvGame.updateView();
    }
	
	public static void SetIListenJVMMsg(IListenJVMMsg amsg)
	{
		listenjvmmsg = amsg;
	}
	
	public static void SendMessage(int what)
	{
		//Log.d("jvm.java", "SendMessage");
		Message msg = new Message();
		msg.what = what;
		if(listenjvmmsg != null) {
			//Log.d("jvm.java", "listenjvmmsg.sendmessage");
			listenjvmmsg.sendmessage(msg);
		}
	}
	
	public static void updateView()
	{
		//Log.d("jvm.java", "updateView");
		SendMessage(0);	
	}
	public String[] ParsingPlayer(String[] paramArray)
	{
		Log.d("jvm.java", "ParsingPlayer");
		return new String[] {"hahaha", "yayaya"};
	}
	public static void ParsingPlayer()
	{
		Log.d("jvm.java", "ParsingPlayer");
		//return new String[] {"hahaha", "yayaya"};
	}
	
    static{
    	System.loadLibrary("jnicvm");
    }

    public static native void runGame(String jadUrl, String jarurl, String packageName); 
    public static native void runGameAuto(String jadUrl, String jarUrl, String appDir, String className); 
    public static native void setProp(String key, String value,int index); 
    public static native String runGame(); 
    public static native void initSurfaceBitmap(int width, int height, int s, Bitmap bitmap); 
}
