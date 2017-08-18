package com.NON.iptvgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.System.IptvGame.GameParam;
import com.System.IptvGame.GameSurface;
import com.System.IptvGame.jvm;
import com.System.IptvGame.utils;

import com.NON.iptvgame.JZDownload.IDownloadFinish;
import com.NON.iptvgame.R.id;
import com.NON.iptvgame.R;
//import com.NON.downloadutil.IDownloadFinish;
//import com.NON.downloadutil.JZDownload;
//import com.tencent.bugly.crashreport.CrashReport;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.NON.iptvgame.IListenJVMMsg;
import android.os.SystemClock;

//import com.System.IptvGame.GameSurface;
//import com.System.IptvGame.jvm;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameActivity extends Activity implements IDownloadFinish,
		IListenJVMMsg
{
	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	public static final String TAG = "NON";
	public static GameSurface itvGame = null;
	public static int _gameWidth = 0;
	public static int _gameHeight = 0;
	GameParam _gameparam;
	private static GameActivity instance;
	private Timer mTimer;
	private TimerTask mTimerTask;
	public static Handler handler;
	private boolean GameIsRun = false;
	private JZDownload downloadTask;
	private static final int LOADSUCCESS = 10000;
	private static final int DOWNLOADFAILED = 12344;
	private static final int DOWNLOADFINISHED = 12345;
	private static final int GAMEALIVE = 12346;
	private JAROrJADInfo JARInfo = null;
	private JAROrJADInfo JADInfo = null;
	public Boolean copyfinish = false;
	public Boolean windowfocused = false;

	Handler mhandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			// Log.d("GameActivity.java", "handleMessage msg.what="+msg.what );
			switch ( msg.what )
			{

				case 0:
					if (itvGame != null) itvGame.updateView();
				break;
				// case 1:
				// mTimerTask.cancel();
				// Log.i("itvGame", "recv msg 1");
				// // jvm.Close();
				// GameActivity.itvGame.Close();
				// sendEmptyMessageDelayed(0x3, 0x64);
				// break;
				// case 2:
				// sendBroadcast((Intent) msg.obj);
				// sendEmptyMessageDelayed(0x1, 0xa);
				// break;
				// case 3:
				// Log.i("itvGame", "recv msg 3");
				// finish();
				// android.os.Process.killProcess(android.os.Process.myPid());
				// break;
				case LOADSUCCESS:
				{
					Log.d("NON", "LOADSUCCESS copyfinish:" + copyfinish);
					if (copyfinish&&windowfocused)
					{
						itvGame = new GameSurface(GameActivity.this,_gameWidth,_gameHeight, GetPackageName());
						Log.d("NON", "setGameHeightAndWidth->" + _gameHeight + "," + _gameWidth);
						//itvGame.setGameHeightAndWidth(_gameHeight, _gameWidth);
						setContentView(GameActivity.itvGame);
						new Thread(new Runnable()
						{
							@Override
							public void run()
							{
								// jvm.runGame();
								//setContentView(GameActivity.itvGame);
								int i = 0;
								Iterator iterator = _gameparam.ParamList.iterator();
								while(iterator.hasNext())
								{
									Map<String, String> map = (Map<String, String>)iterator.next();
									String key = map.get("key");
									String value = map.get("value");
									Log.d("NON", "setProp("+key + "," + value + ","+ i +")");
									jvm.setProp(key, value, i);
									i++;
								}
								
								
								
//								jvm.runGame("/data/data/com.NON.iptvgame/foundation/MediaPlayer.jad",
//									"/data/data/com.NON.iptvgame/foundation/MediaPlayer.jar",
//									"MediaPlayer");
//								jvm.runGame("/data/data/com.NON.iptvgame/foundation/Game_PK.jad",
//										"/data/data/com.NON.iptvgame/foundation/Game_PK.jar",
//										"engine.Startup");

                        String mPackageName = getApplicationContext().getFilesDir().getParent();
                        Log.i(TAG, "mPackageName=" + mPackageName);
                        //jvm.runGame(JADInfo.saveurl, JARInfo.saveurl,
                        //		JADInfo.packagename);
                        jvm.runGameAuto(JADInfo.saveurl, JARInfo.saveurl,
                                mPackageName, JADInfo.packagename);
							}
						}).start();
					}
					else
					{
						sendEmptyMessageDelayed(LOADSUCCESS, 500);
					}

				}
				break;
				// case 8:
				// {
				// if (mTimer != null)
				// {
				// mTimerTask.cancel();
				// mTimer.cancel();
				// mTimer = null;
				// mTimerTask = null;
				// }
				// itvGame.updateView();
				// }
				// break;
				case DOWNLOADFAILED:
				{
					Log.i("itvGame", "download failed!");
					Toast.makeText(getApplicationContext(), R.string.download_fail,
							Toast.LENGTH_SHORT).show();
				}
				break;
				case DOWNLOADFINISHED:
				{

					JADInfo.GetPackagename();
					Log.i("itvGame", "download success!");
					Toast.makeText(getApplicationContext(), R.string.download_success,
							Toast.LENGTH_SHORT).show();

					handler.sendEmptyMessage(LOADSUCCESS);
				}
				break;
				case GAMEALIVE:
				{
					Intent intent = new Intent();  
					intent.setAction("com.NON.gamealives");  
					sendBroadcast(intent);
					sendEmptyMessageDelayed(GAMEALIVE, 500);
				}
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);	
		instance = this;
		startService(new Intent("com.NON.iptvgame.watchdogservice"));
		setContentView(R.layout.activity_game);
		JAROrJADInfo.clear();
		//CrashReport.initCrashReport(this, "注册时申请的APPID", false);
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				BuildCVMEnv();

			}
		}).start();

		jvm.SetIListenJVMMsg(GameActivity.this);
		handler = mhandler;
		mhandler.sendEmptyMessageDelayed(GAMEALIVE, 500);

		
		
		Intent intent = getIntent();
		String gameparamstr = intent.getStringExtra("GameStartParam");
		_gameparam = new GameParam();
		_gameparam.parseParam(gameparamstr);
		//_gameparam.test();
		
		Log.d("NON", "get prarm from iptv:"+gameparamstr);
		
		
		Log.d("NON", "parse from param:"+_gameparam.toString());
		
		try {
			_gameHeight = Integer.parseInt(_gameparam.height);
			_gameWidth = Integer.parseInt(_gameparam.width);
		} catch (Exception e) {
			_gameHeight = 0;
			_gameWidth = 0;
		}
		Log.d("NON", "gameWidth:" + _gameWidth + ",gameHeight:" + _gameHeight);
		
		//JADInfo.url = intent.getStringExtra("jadurl");
		JADInfo = new JAROrJADInfo();
		JADInfo.url = _gameparam.jad;
		Log.d(TAG, "jadurl is " + JADInfo.url);
		if (JADInfo.url != null)
		{
			JADInfo.GetName();
			JADInfo.GetSaveURL();

			Log.d(TAG, "JADInfo.Isexists() is " + JADInfo.Isexists());
			Log.d(TAG, "JADInfo.saveurl is " + JADInfo.saveurl);
			if (!JADInfo.Isexists())
			{
				downloadTask = new JZDownload(GameActivity.this, JADInfo.url,
						JADInfo.saveurl, 10 * 1000);
				downloadTask.setOnDownloadFinish(this);
				JADInfo.downloadid = downloadTask.getDownloadId();
				Log.d(TAG, JADInfo.tostring());
				downloadTask.startDownload();
			}
		}

		//JARInfo.url = getIntent().getStringExtra("jarurl");
		JARInfo = new JAROrJADInfo();
		JARInfo.url = _gameparam.jar;
		Log.d(TAG, "jarurl is "+ JARInfo.url);
		if (JARInfo.url != null)
		{
			JARInfo.GetName();
			JARInfo.GetSaveURL();
			
			Log.d(TAG, "JARInfo.Isexists() is "+ JARInfo.Isexists());
			Log.d(TAG, "JARInfo.saveurl is "+ JARInfo.saveurl);
			if (!JARInfo.Isexists())
			{
				downloadTask = new JZDownload(GameActivity.this, JARInfo.url,
						JARInfo.saveurl, 10 * 1000);
				downloadTask.setOnDownloadFinish(this);
				JARInfo.downloadid = downloadTask.getDownloadId();
				Log.d(TAG, JARInfo.tostring());
				downloadTask.startDownload();
			}
		}

		if (JADInfo.downloadfinished && JARInfo.downloadfinished)
		{
			Log.d(TAG, "jad and jar is exists");
			handler.sendEmptyMessage(DOWNLOADFINISHED);
		}
//		Log.d(TAG, "jvm.SendMessage(LOADSUCCESS)");
//		jvm.SendMessage(LOADSUCCESS);
	}
	
	
	
	
	  @Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setContentView(R.layout.activity_game);
		

		String gameparamstr = intent.getStringExtra("GameStartParam");
		_gameparam = new GameParam();
		_gameparam.parseParam(gameparamstr);
		
		
		try {
			_gameHeight = Integer.parseInt(_gameparam.height);
			_gameWidth = Integer.parseInt(_gameparam.width);
		} catch (Exception e) {
			_gameHeight = 0;
			_gameWidth = 0;
		}
		
		
		//JADInfo.url = intent.getStringExtra("jadurl");
		JADInfo = new JAROrJADInfo();
		JADInfo.url = _gameparam.jad;
		Log.d(TAG, "jadurl is " + JADInfo.url);
		if (JADInfo.url != null)
		{
			JADInfo.GetName();
			JADInfo.GetSaveURL();

			Log.d(TAG, "JADInfo.Isexists() is " + JADInfo.Isexists());
			Log.d(TAG, "JADInfo.saveurl is " + JADInfo.saveurl);
			if (!JADInfo.Isexists())
			{
				downloadTask = new JZDownload(GameActivity.this, JADInfo.url,
						JADInfo.saveurl, 10 * 1000);
				downloadTask.setOnDownloadFinish(this);
				JADInfo.downloadid = downloadTask.getDownloadId();
				Log.d(TAG, JADInfo.tostring());
				downloadTask.startDownload();
			}
		}

		//JARInfo.url = getIntent().getStringExtra("jarurl");
		JARInfo = new JAROrJADInfo();
		JARInfo.url = _gameparam.jar;
		Log.d(TAG, "jarurl is "+ JARInfo.url);
		if (JARInfo.url != null)
		{
			JARInfo.GetName();
			JARInfo.GetSaveURL();
			
			Log.d(TAG, "JARInfo.Isexists() is "+ JARInfo.Isexists());
			Log.d(TAG, "JARInfo.saveurl is "+ JARInfo.saveurl);
			if (!JARInfo.Isexists())
			{
				downloadTask = new JZDownload(GameActivity.this, JARInfo.url,
						JARInfo.saveurl, 10 * 1000);
				downloadTask.setOnDownloadFinish(this);
				JARInfo.downloadid = downloadTask.getDownloadId();
				Log.d(TAG, JARInfo.tostring());
				downloadTask.startDownload();
			}
		}

		if (JADInfo.downloadfinished && JARInfo.downloadfinished)
		{
			Log.d(TAG, "jad and jar is exists");
			handler.sendEmptyMessage(DOWNLOADFINISHED);
		}
		
	}




	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		windowfocused = true;
	}




	public static GameActivity getInstance()
	  {
	    return instance;
	  }

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		Log.d("GameActivity.java", "------------------mouse--------------");
		int i = (int) event.getX();
		int j = (int) event.getY();
		int res = 0;
		switch ( event.getAction() )
		{
			default:
				Log.d("GameActivity.java", "------------------mouse default------------");
				//return false;
				break;
			case 0:
			case 2:
				//int k = (int) event.getX();
				//int m = (int) event.getY();
				//itvGame.mouseEvent(k, m, 1);
				//return true;
				Log.d("GameActivity.java", "------------------mouse 0 2------------");
				res = 1;
			break;
		}
		itvGame.mouseEvent(i, j, res);
		
		return true;
	}

    private static long clickTime = 0; //记录第一次点击的时间                                                                                       

    public /*static*/ void exit() {  
        if ((System.currentTimeMillis() - clickTime) > 500) {  
            //Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",  
            //        Toast.LENGTH_SHORT).show();  
            clickTime = System.currentTimeMillis();  
        } else {  
            Log.e("JVM", "exit application");  
            //System.exit(0);  
			new AlertDialog.Builder(this).setIcon(android.R.drawable.btn_star)
					.setTitle(getResources().getString(R.string.notice)).setMessage(getResources().getString(R.string.exit_notice))
					.setPositiveButton(getResources().getString(R.string.exit), new OnClickListener()
					{
						@Override
						public void onClick(DialogInterface arg0, int arg1)
						{
							System.exit(0);
						}
					}).setNegativeButton(getResources().getString(R.string.cancel), new OnClickListener()
					{
						@Override
						public void onClick(DialogInterface arg0, int arg1)
						{
							arg0.dismiss();
						}
					}).create().show();
        }    
    }   
    //yistn add end	
    public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_DEL || keyCode == KeyEvent.KEYCODE_AVR_POWER 
                    || keyCode == KeyEvent.KEYCODE_PROG_RED || keyCode == KeyEvent.KEYCODE_PROG_GREEN 
                    || keyCode == KeyEvent.KEYCODE_PROG_BLUE || keyCode == KeyEvent.KEYCODE_PROG_YELLOW)
        {
            System.exit(0);
        }
        
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
            exit();
			//new AlertDialog.Builder(this).setIcon(android.R.drawable.btn_star)
			//		.setTitle(getResources().getString(R.string.notice)).setMessage(getResources().getString(R.string.exit_notice))
			//		.setPositiveButton(getResources().getString(R.string.exit), new OnClickListener()
			//		{
			//			@Override
			//			public void onClick(DialogInterface arg0, int arg1)
			//			{
			//				System.exit(0);
			//			}
			//		}).setNegativeButton(getResources().getString(R.string.cancel), new OnClickListener()
			//		{
			//			@Override
			//			public void onClick(DialogInterface arg0, int arg1)
			//			{
			//				arg0.dismiss();
			//			}
			//		}).create().show();
		}
		
        if (itvGame != null) 
			itvGame.KeyDown(keyCode, event);
        

		return true;
	}

	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		if (itvGame != null) 
			itvGame.KeyUp(keyCode, event);

		return true;
	}

    public String GetPackageName()
    {
        String mPackageName = getApplicationContext().getFilesDir().getParent();
        Log.i(TAG, "mPackageName=" + mPackageName);
        return mPackageName;
    }

	protected int BuildCVMEnv(String paramString)
	{
		StringBuilder localStringBuilder1 = new StringBuilder(GetPackageName());
		String str1 = "/foundation";
		String path = localStringBuilder1.append(str1).toString();
		CopyAssets(paramString, path);
		return 0;
	}

	protected void BuildCVMEnv()
	{
		StringBuilder localStringBuilder1 = new StringBuilder(GetPackageName());
		String str2 = "/foundation";
		localStringBuilder1.append(str2);
		String str3 = localStringBuilder1.toString();
		if (new File(str3).exists())
		{
			copyfinish = true;
			return;
		}

		BuildCVMEnv("foundation");
		copyfinish = true;
	}

	private void CopyAssets(String assetDir, String dir)
	{
		String[] files;
		try
		{
			files = this.getResources().getAssets().list(assetDir);
		}
		catch (IOException e1)
		{
			return;
		}
		File mWorkingPath = new File(dir);
		// if this directory does not exists, make one.
		if (!mWorkingPath.exists())
		{
			if (!mWorkingPath.mkdirs())
			{
				Log.e("--CopyAssets--", "cannot create directory.");
			}
		}
		for (int i = 0; i < files.length; i++)
		{
			try
			{
				String fileName = files[i];
				String curpath = assetDir + "/" + fileName;
				String[] tempfiles;
				try
				{
					tempfiles = this.getResources().getAssets().list(curpath);
				}
				catch (IOException e1)
				{
					Log.d("NON", ".getResources().getAssets() err");
					e1.printStackTrace();
					return;
				}

				// we make sure file name not contains '.' to be a folder.
				// if (!fileName.contains("."))
				if (tempfiles.length != 0)
				{
					if (0 == assetDir.length())
					{
						CopyAssets(fileName, dir + fileName + "/");
					}
					else
					{
						CopyAssets(assetDir + "/" + fileName,
								dir + "/" + fileName + "/");
					}
					continue;
				}
				File outFile = new File(mWorkingPath, fileName);
				if (outFile.exists()) outFile.delete();
				InputStream in = null;
				if (0 != assetDir.length())
					in = getAssets().open(assetDir + "/" + fileName);
				else
					in = getAssets().open(fileName);
				OutputStream out = new FileOutputStream(outFile);
				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0)
				{
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
			catch (FileNotFoundException e)
			{
				Log.d("NON", "FileNotFoundException");
				e.printStackTrace();
			}
			catch (IOException e)
			{
				Log.d("NON", "IOException");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDownloadFinish(int id, int arg)
	{
		Log.d(TAG, "JARInfo.downloadid is:"+JARInfo.downloadid+",JADInfo.downloadid is:"+JADInfo.downloadid);
		Log.d(TAG, "onDownloadFinish id is:"+id+",arg is:"+arg);
		if ((id == JARInfo.downloadid) && (arg == JZDownload.DOWNLOAD_SUCCESS))
			JARInfo.downloadfinished = true;
		else if ((id == JADInfo.downloadid) && (arg == JZDownload.DOWNLOAD_SUCCESS))
			JADInfo.downloadfinished = true;
		else
		{
			handler.sendEmptyMessage(DOWNLOADFAILED);
			return;
		}

		if ((JARInfo.downloadfinished) && (JADInfo.downloadfinished))
		{
			handler.sendEmptyMessage(DOWNLOADFINISHED);
		}
	}

	@Override
	public void sendmessage(Message msg)
	{
		handler.sendMessage(msg);

	}

	@Override
    public void onStop()
    {
        //System.exit(0);
    }

}
