package com.NON.iptvgame;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class watchdogservice extends Service
{

	private Boolean gamealived = true;

	@Override
	public IBinder onBind(Intent arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{

			// Log.d("NON", "BroadcastReceiver com.NON.gamealives");
			gamealived = true;
		}
	};

	@Override
	public void onCreate()
	{
		Log.d("NON", "watchdogservice onCreate");
		super.onCreate();
		IntentFilter IF = new IntentFilter();
		IF.addAction("com.NON.gamealives");
		registerReceiver(mReceiver, IF);
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					Log.d("NON", "check gamealived:" + gamealived);
					if (gamealived)
					{
						gamealived = false;
						try
						{
							Thread.sleep(700);
						}
						catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						Intent intent = new Intent();
						intent.setAction("com.NON.gameover");
						sendBroadcast(intent);
						System.exit(0);
					}
				}

			}
		}).start();

	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		Log.d("NON", "onStart");
		super.onStart(intent, startId);

	}

}
