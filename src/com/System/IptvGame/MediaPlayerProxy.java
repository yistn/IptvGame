package com.System.IptvGame;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class MediaPlayerProxy extends AbstractProxy {
	public static final String KEY_MEDIAPLAYER = "mediaplayer";
	public static final String KEY_DATASOURCE = "datasource";
	public static final String KEY_LOOP = "loop";

	public void finish() {
		Iterator localIterator = this.objects.values().iterator();
		while (localIterator.hasNext())
			((MediaPlayer) localIterator.next()).stop();
	}

	public String[] ParsingPlayer(String[] paramArrayOfString) {
		for (String str : paramArrayOfString) {
			Log.d("MediaPlayerProxy.java", "ParsingPlayer,param is:" + str);
		}
		try {
			String str1 = paramArrayOfString[1];
			Log.d("PhoneME", "MediaPlayerProxy.process: method call: '" + str1
					+ "'");
			if (str1.equals("create")) {
				MediaPlayer localMediaPlayer1 = new MediaPlayer();
				// final String str2 = Integer.toString(localMediaPlayer1
				// .hashCode());

				Map<String, Object> map = new HashMap<String, Object>();
				map.put(KEY_MEDIAPLAYER, localMediaPlayer1);
				map.put(KEY_DATASOURCE, paramArrayOfString[2]);
				map.put(KEY_LOOP, Integer.toString(1));

				final String maphashcode = Integer.toString(map.hashCode());

				this.objects.put(maphashcode, map);

				localMediaPlayer1.setDataSource(paramArrayOfString[2]);

				localMediaPlayer1
						.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
							public void onCompletion(
									MediaPlayer paramMediaPlayer) {
								paramMediaPlayer.reset();
								paramMediaPlayer.release();
								MediaPlayerProxy.this.objects
										.remove(maphashcode);
							}
						});

				Log.d("MediaPlayerProxy.java", "create, maphashcode is "
						+ maphashcode + "  datasource is "
						+ paramArrayOfString[2]);

				return new String[] { maphashcode };
			}
			if (str1.equals("pause")) {
				String str11 = paramArrayOfString[2];

				Map<String, Object> map = (Map<String, Object>) this.objects
						.get(str11);

				MediaPlayer localMediaPlayer7 = (MediaPlayer) map
						.get(KEY_MEDIAPLAYER);

				if (localMediaPlayer7 != null)
					localMediaPlayer7.pause();
				// return new String[0];
				return new String[] { str11 };
			}
			if (str1.equals("prepare")) {
				String str10 = paramArrayOfString[2];

				Map<String, Object> map = (Map<String, Object>) this.objects
						.get(str10);

				MediaPlayer localMediaPlayer6 = (MediaPlayer) map
						.get(KEY_MEDIAPLAYER);
				if (localMediaPlayer6 != null)
					localMediaPlayer6.prepare();
				// return new String[0];
				return new String[] { str10 };
			}
			if (str1.equals("release")) {
				String str9 = paramArrayOfString[2];
				Map<String, Object> map = (Map<String, Object>) this.objects
						.get(str9);

				MediaPlayer localMediaPlayer5 = (MediaPlayer) map
						.get(KEY_MEDIAPLAYER);

				if (localMediaPlayer5 != null) {
					localMediaPlayer5.release();
					this.objects.remove(str9);
				}
				// return new String[0];
				return new String[] { str9 };
			}

			if (str1.equals("loop")) {
				final String str10 = paramArrayOfString[2];

				Map<String, Object> map = (Map<String, Object>) this.objects
						.get(str10);

				MediaPlayer localMediaPlayer = (MediaPlayer) map
						.get(KEY_MEDIAPLAYER);
				String datasource = (String) map.get(KEY_DATASOURCE);
				String loop = (String) map.get(KEY_LOOP);

				String loopCount = paramArrayOfString[3];
				map.put(KEY_LOOP, loop);

				if ("-1".equals(loopCount)) {
					localMediaPlayer.setLooping(true);
					localMediaPlayer.setOnCompletionListener(null);
				} else {

					localMediaPlayer
							.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
								public void onCompletion(
										MediaPlayer paramMediaPlayer) {

									Map<String, Object> map = (Map<String, Object>) MediaPlayerProxy.this.objects
											.get(str10);
									String datasource = (String) map
											.get(KEY_DATASOURCE);
									String loop = (String) map.get(KEY_LOOP);
									int loopcount = 1;
									try {
										loopcount = Integer.parseInt(loop);
									} catch (Exception e) {
										loopcount = 1;
									}

									Log.d("MediaPlayerProxy.java",
											"onCompletion, loopCount:"
													+ loopcount);

									loopcount--;
									map.put(KEY_LOOP, loopcount + "");

									if (loopcount == 0) {
										paramMediaPlayer.reset();
										paramMediaPlayer.release();
										MediaPlayerProxy.this.objects
												.remove(str10);

										Log.d("MediaPlayerProxy.java",
												"release");
									} else {
										if (paramMediaPlayer.isPlaying()) {
											paramMediaPlayer.stop();// 停止
										}

										try {
											paramMediaPlayer.reset();// 重置
											paramMediaPlayer
													.setDataSource(datasource);// 设置数据源
											paramMediaPlayer.prepare();// 准备
											paramMediaPlayer.start();// 开始播放
										} catch (Exception e) {
											e.printStackTrace();
											paramMediaPlayer.reset();
											paramMediaPlayer.release();
											MediaPlayerProxy.this.objects
													.remove(str10);
											Log.d("MediaPlayerProxy.java",
													"Exception ,release");
										}

										Log.d("MediaPlayerProxy.java",
												"restart");
									}

								}
							});

				}

				Log.d("MediaPlayerProxy.java", "ParsingPlayer, loopCount:"
						+ loopCount);
				// return new String[0];
				return new String[] { str10 };
			}

			if (str1.equals("reset")) {
				String str8 = paramArrayOfString[2];

				Map<String, Object> map = (Map<String, Object>) this.objects
						.get(str8);

				MediaPlayer localMediaPlayer4 = (MediaPlayer) map
						.get(KEY_MEDIAPLAYER);

				if (localMediaPlayer4 != null)
					localMediaPlayer4.reset();
				// return new String[0];
				return new String[] { str8 };
			}
			if (str1.equals("start")) {
				String str7 = paramArrayOfString[2];

				Map<String, Object> map = (Map<String, Object>) this.objects
						.get(str7);

				MediaPlayer localMediaPlayer3 = (MediaPlayer) map
						.get(KEY_MEDIAPLAYER);

				if (localMediaPlayer3 != null)
					localMediaPlayer3.start();
				// return new String[0];
				return new String[] { str7 };
			}
			if (str1.equals("stop")) {
				String str6 = paramArrayOfString[2];

				Map<String, Object> map = (Map<String, Object>) this.objects
						.get(str6);

				MediaPlayer localMediaPlayer2 = (MediaPlayer) map
						.get(KEY_MEDIAPLAYER);

				if (localMediaPlayer2 != null)
					localMediaPlayer2.stop();
				// return new String[0];
				return new String[] { str6 };
			}
			if (str1.equals("playTone")) {
				String str3 = paramArrayOfString[2];
				String str4 = paramArrayOfString[3];
				String str5 = paramArrayOfString[4];
				Utilities.playTone(Integer.parseInt(str3),
						Integer.parseInt(str4), Integer.parseInt(str5));
				// return new String[0];
				return new String[] { "999999" };
			}
			if (str1.equals("playAlert")) {
				Utilities.playAlert(Integer.parseInt(paramArrayOfString[2]));
				String[] arrayOfString = new String[0];
				// return arrayOfString;
				return new String[] { "999999" };
			}
		} catch (Exception localException) {
			Log.d("PhoneME",
					"MediaPlayerProxy.process: Exception in MediaPlayerProxy: "
							+ localException.getMessage());
			localException.printStackTrace();
		}
        return new String[] { "999999" };
	}
}

/*
 * Location:
 * D:\android\tools\dex2jar-0.0.7.11-SNAPSHOT\dex2jar-0.0.7.11-SNAPSHOT
 * \classes_dex2jar(1).jar Qualified Name:
 * be.preuveneers.phoneme.fpmidp.MediaPlayerProxy JD-Core Version: 0.6.0
 */
