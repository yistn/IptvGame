/**
 * 
 */
/**
 * @author yistn
 *
 */
package com.System.IptvGame;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.NON.iptvgame.GameActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.text.method.MetaKeyKeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
	private static final int WIDTH_DEFAULT = 560;
	private Bitmap iptvBmp;
	private SurfaceHolder mSurfaceHolder;
	private BufferedOutputStream kbdStream;
	private BufferedOutputStream mouseStream;

	public static int defaultWidth = 1920;
	public static int defaultHeight = 1080;

	public static int displayWidth = 1280;
	public static int displayHeight = 720;

	public static int _gameWidth = 1280;
	public static int _gameHeight = 720;

	public GameSurface(Context context,int w,int h, String packagename) {
		super(context);

		DisplayMetrics metrics = new DisplayMetrics(); // 获取分辨率大小
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

		Log.d("NON", "metrics.widthPixels:" + metrics.widthPixels);
		Log.d("NON", "metrics.heightPixels:" + metrics.heightPixels);
		Log.d("NON", "w:" + w);
		Log.d("NON", "h:" + h);

		if (h != 0)
			_gameHeight = h;
		if (w != 0)
			_gameWidth = w;
		
		
		int width =_gameWidth;// metrics.widthPixels-640;//w;//metrics.widthPixels;
		//int height =WIDTH_DEFAULT;// metrics.heightPixels-560;//-100;//h;//metrics.heightPixels;
		int height =_gameHeight;// metrics.heightPixels-560;//-100;//h;//metrics.heightPixels;
		
		Log.d("NON", "width:"+width+ "    height:"+height);
		
		displayWidth = metrics.widthPixels;
		displayHeight = metrics.heightPixels;
		mSurfaceHolder = getHolder();
		mSurfaceHolder.setFixedSize(width, height);
		//mSurfaceHolder.setFixedSize(displayWidth, displayHeight);
		iptvBmp = Bitmap.createBitmap(width, height, Config.RGB_565);// 设置Bitmap的大小和颜色格式
		jvm.initSurfaceBitmap(width, height, width * height, iptvBmp);

		
		try {
			//File filemos = new File("/data/data/com.NON.iptvgame/mouse_fd");
			File filemos = new File( packagename + "/mouse_fd");
			if (filemos.exists()) {
				filemos.delete();
			}
			mouseStream = new BufferedOutputStream(
					new FileOutputStream(filemos));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("mouse_fd", "sssssss--------no");
			e.printStackTrace();
		}

		try {
			//File filekbd = new File("/data/data/com.NON.iptvgame/keyboard_fd");
			File filekbd = new File(packagename + "/keyboard_fd");
			if (filekbd.exists()) {
				filekbd.delete();
			}

			kbdStream = new BufferedOutputStream(new FileOutputStream(filekbd));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("keyboard_fd", "sssssss--------no");
			e.printStackTrace();
		}

	}

	public void setGameHeightAndWidth(int h, int w) {
		if (h != 0)
			_gameHeight = h;
		if (w != 0)
			_gameWidth = w;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 删除文件夹所有内容
	 * 
	 */
	public void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			//
		}
	}

	public void updateView() {
		// Log.d("GameSurface.java", "updateView msg");
		if (mSurfaceHolder.getSurface().isValid()) {
			// Log.d("GameSurface.java", "getSurface().isValid");
			Canvas localCanvas = mSurfaceHolder.lockCanvas();
			//localCanvas.drawBitmap(iptvBmp, 0.0f, 0.0f, null);
			drawImage(localCanvas, iptvBmp);
			mSurfaceHolder.unlockCanvasAndPost(localCanvas);
		}
	}

	// GameView.drawImage(canvas, mBitDestTop, miDTX, mBitQQ.getHeight(),
	// mBitDestTop.getWidth(), mBitDestTop.getHeight()/2, 0, 0);
	public static void drawImage(Canvas canvas, Bitmap blt) {
		Rect src = new Rect();
		Rect dst = new Rect();
		// src 这个是表示绘画图片的大小
		src.left = 0; // 0,0
		src.top = 0;
		src.right = _gameWidth;
		src.bottom = _gameHeight;
		// 下面的 dst 是表示 绘画这个图片的位置
		dst.left = 0;
		dst.top = 0;
		//dst.right = _gameWidth;
        dst.right = displayWidth;
		//dst.bottom = WIDTH_DEFAULT;
		//dst.bottom = _gameHeight;
		dst.bottom = displayHeight;
		//canvas.drawBitmap(blt, src, dst, null);
		canvas.drawBitmap(blt, 0, 0, null);

		src = null;
		dst = null;
	}

	public boolean KeyDown(int paramInt, KeyEvent paramKeyEvent) {
		char i = mapChar(paramInt, paramKeyEvent);
		if (i == 0)
			i = paramKeyEvent.getDisplayLabel();
		if (i <= 0)
			return false;

		Log.d("GameSurface.java", "keyboardEvent=" + i);
		keyboardEvent(i);
		// PrintStream localPrintStream = System.out;
		// String str = "[KeyDown] key:" + i;
		// localPrintStream.println(str);
		// this.kbdStream.write(i);
		// this.kbdStream.flush();
		return true;

	}

	public boolean KeyUp(int paramInt, KeyEvent paramKeyEvent) {
		char i = mapChar(paramInt, paramKeyEvent);
		if (i == 0)
			i = paramKeyEvent.getDisplayLabel();
		if (i <= 0)
			return false;

		i = (char) (i + 128);
		keyboardEvent(i);

		return true;

		// char i = paramKeyEvent.getDisplayLabel();
		// int j = i + 128;
		// PrintStream localPrintStream = System.out;
		// String str = "[KeyUp] key:" + j;
		// localPrintStream.println(str);
		// this.kbdStream.write(j);
		// this.kbdStream.flush();
		// return true;

	}

	public static final byte[] intToByteArray(int p1) {

		// return new byte[] {(byte)p1, (byte)(byte)p1, (byte)(byte)(byte)p1,
		// (byte)(byte)(byte)(byte)p1};
		byte[] array = new byte[4];
		array[0] = ((byte) p1);
		array[1] = ((byte) (p1 >>> 8));
		array[2] = ((byte) (p1 >>> 16));
		array[3] = ((byte) (p1 >>> 24));
		return array;
	}

	public static final int byteArrayToInt(byte[] p1) {
		return ((((p1[0x3] << 0x18) + ((p1[0x2] & 0xff) << 0x10)) + ((p1[0x1] & 0xff) << 0x8)) + (p1[0x0] & 0xff));
	}

	public void mouseEvent(int paramInt1, int paramInt2, int paramInt3) {
		// if ((enableSIP) && (paramInt1 > (-50 + fbView.getWidth()) / 2) &&
		// (paramInt1 < (50 + fbView.getWidth()) / 2) && (paramInt2 > -40 +
		// fbView.getHeight()))
		// {
		// showSoftInput();
		// return;
		// }
		int p1 = paramInt1 * (_gameWidth );
		int p2 = paramInt2 * (_gameHeight);
		int p3 = paramInt3;
		Log.d("GameSurface.java", "------------------mouse--------------");
		try {
			if (mouseStream != null) {
				Log.d("GameSurface.java",
						"------------------mouse--sss------------");
				mouseStream.write(intToByteArray(paramInt1));
				mouseStream.write(intToByteArray(paramInt2));
				mouseStream.write(intToByteArray(paramInt3));
				mouseStream.flush();
			}
			Log.d("PhoneME",
					"FrameBufferActivity.mouseEvent: mouse output stream is no null");
			return;
		} catch (Exception localException) {
			Log.d("PhoneME",
					"FrameBufferActivity.mouseEvent: Exception in mouseEvent(): "
							+ localException.getMessage());
			localException.printStackTrace();
		}
	}

	public void Close() {
		try {
			kbdStream.write(16);
			kbdStream.flush();
			return;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}

	public synchronized void keyboardEvent(char p1) {
		try {
			if (kbdStream != null) {
				//kbdStream.write(p1);
				kbdStream.write((p1 / 0x100));
				kbdStream.write(0x80);
				//kbdStream.write(0x0);
				kbdStream.write(p1);
				kbdStream.write((p1 / 0x100));
				kbdStream.flush();
				// return;
			}
			Log.d("PhoneME",
					"FrameBufferActivity.keyboardEvent: Keyboard output stream is no null");
			return;
		} catch (Exception localException1) {
			Log.d("PhoneME",
					"FrameBufferActivity.keyboardEvent: Exception in keyboardEvent(): "
							+ localException1.getMessage());
			localException1.printStackTrace();
		}
	}

	public char mapChar(int paramInt, KeyEvent paramKeyEvent) {

		Log.d("PhoneME", "------------------------key s");
		Log.d("PhoneME", "mapClar: paramInt=" + paramInt); 
        Log.d("PhoneMe", "keyEvent: " + paramKeyEvent + ", Unicode: " + paramKeyEvent.getUnicodeChar());
		Log.d("PhoneME", "------------------------key e");
		switch (paramInt) {
		case 0:
		default:
			return '\000';
		case 1:
			return '\005';
		case 2:
			return '\006';
		case 17:
			return '*';
		case 18:
			return '#';
		case 19:
			return '\001';
		case 20:
			return '\002';
		case 21:
			return '\003';
		case 22:
			return '\004';
		case 23:
			return '\r';
		case 26:
			return '\021';
		case 28:
			return '\033';
		case 66:
			return '\r';
        case 4:
		case 67:
			return '\006';
		}

	}

}
