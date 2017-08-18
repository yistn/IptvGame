package com.System.IptvGame;


import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;

import com.NON.iptvgame.GameActivity;
import com.NON.iptvgame.R;

public class Utilities
{
//  public static void asyncExec(ConsoleActivity paramConsoleActivity, String paramString)
//  {
//    if (paramConsoleActivity == null)
//      throw new IllegalArgumentException();
//    if ((paramString == null) || (paramString.equals("")))
//      throw new IllegalArgumentException();
//    new Thread(new Runnable(paramConsoleActivity, paramString)
//    {
//      public void run()
//      {
//        Utilities.runCommand(this.val$activity, this.val$command, true);
//        ConsoleActivity.appendOutput("#\n");
//        ConsoleActivity.outputConsole();
//      }
//    }).start();
//  }
//
//  public static void asyncRootExec(ConsoleActivity paramConsoleActivity, String paramString)
//  {
//    new Thread(new Runnable(paramConsoleActivity, paramString)
//    {
//      public void run()
//      {
//        Utilities.runRootCommand(this.val$activity, this.val$command, true);
//      }
//    }).start();
//  }
//
//  public static void downloadFile(ConsoleActivity paramConsoleActivity, String paramString1, String paramString2)
//  {
//    ConsoleActivity.appendOutput("\nDownloading " + paramString1 + " ...\n");
//    ConsoleActivity.outputConsole();
//    try
//    {
//      downloadFile(paramString1, paramString2);
//      return;
//    }
//    catch (IOException localIOException)
//    {
//      ConsoleActivity.appendOutput("\nFailed to get " + paramString1 + "!\n");
//      ConsoleActivity.outputConsole();
//    }
//  }
//
//  public static void downloadFile(String paramString1, String paramString2)
//    throws IOException
//  {
//    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
//    HttpEntity localHttpEntity = localDefaultHttpClient.execute(new HttpGet(paramString1)).getEntity();
//    if (localHttpEntity != null)
//    {
//      InputStream localInputStream = localHttpEntity.getContent();
//      byte[] arrayOfByte = new byte[65536];
//      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramString2));
//      int i;
//      do
//      {
//        i = localInputStream.read(arrayOfByte);
//        if (i <= 0)
//          continue;
//        localBufferedOutputStream.write(arrayOfByte, 0, i);
//      }
//      while (i > 0);
//      localBufferedOutputStream.flush();
//      localBufferedOutputStream.close();
//      localInputStream.close();
//      localDefaultHttpClient.getConnectionManager().shutdown();
//    }
//  }

//  public static void dumpLog()
//  {
//    monitorenter;
//    try
//    {
//      File localFile1 = new File(Environment.getExternalStorageDirectory() + "/phoneme.log");
//      if (localFile1.exists())
//      {
//        File localFile2 = new File(Environment.getExternalStorageDirectory() + "/phoneme.log.0");
//        if (localFile2.exists())
//          localFile2.delete();
//        localFile1.renameTo(localFile2);
//      }
//      localFile1.createNewFile();
//      String str = "logcat -d -v time -f " + localFile1.getAbsolutePath() + " -r 64 PhoneME:V stdout:V stderr:V System.err:V System.out:V *:S";
//      Runtime.getRuntime().exec(str);
//      return;
//    }
//    catch (IOException localIOException)
//    {
//      while (true)
//        localIOException.printStackTrace();
//    }
//    finally
//    {
//      monitorexit;
//    }
//    throw localObject;
//  }

//  public static boolean isRunAsRootSupported()
//  {
//    try
//    {
//      java.lang.Process localProcess = Runtime.getRuntime().exec("su");
//      DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
//      localDataOutputStream.writeBytes("ls\n");
//      localDataOutputStream.writeBytes("exit\n");
//      localDataOutputStream.flush();
//      localProcess.waitFor();
//      int i = localProcess.exitValue();
//      int j = 0;
//      if (i == 0)
//        j = 1;
//      localDataOutputStream.close();
//      localProcess.destroy();
//      return j;
//    }
//    catch (Exception localException)
//    {
//    }
//    throw new RuntimeException(localException.getMessage());
//  }

//  public static void killVM()
//  {
//    Log.d("PhoneME", "Utilities.killVM");
//    GameActivity localGameActivity = GameActivity.getInstance();
//    if (localGameActivity != null)
//      localGameActivity.cleanUp();
//    if (PhoneMEActivity.getInstance() != null);
//    android.os.Process.killProcess(android.os.Process.myPid());
//  }

//  public static String loadArgs(ConsoleActivity paramConsoleActivity, String paramString)
//  {
//    if (paramConsoleActivity == null)
//      throw new IllegalArgumentException();
//    if ((paramString == null) || (paramString.equals("")))
//      throw new IllegalArgumentException();
//    BufferedReader localBufferedReader;
//    try
//    {
//      localBufferedReader = new BufferedReader(new FileReader(paramString));
//      if (!localBufferedReader.ready())
//        throw new IOException();
//    }
//    catch (IOException localIOException)
//    {
//    }
//    while (true)
//    {
//      return "";
//      while (true)
//      {
//        String str1 = localBufferedReader.readLine();
//        if (str1 == null)
//          break;
//        String str2 = str1.trim();
//        if ((!str2.startsWith("-cp ")) && (!str2.startsWith("-jar ")))
//          continue;
//        localBufferedReader.close();
//        return str2;
//      }
//      localBufferedReader.close();
//      5 local5 = new Runnable(paramConsoleActivity)
//      {
//        public void run()
//        {
//          Toast.makeText(this.val$activity, "Unable to parse ARGS file!", 1).show();
//        }
//      };
//      paramConsoleActivity.getHandler().post(local5);
//    }
//  }


  
  
  public static void playAlert(int p1) {
	  final int type = p1;
      new Thread() {
          
          public void run() {
        	  MediaPlayer localMediaPlayer = null;
              if(type == 0x1) {
            	  localMediaPlayer = MediaPlayer.create(GameActivity.getInstance(), R.raw.info);
              } else if(type == 0x2) {
            	  localMediaPlayer = MediaPlayer.create(GameActivity.getInstance(), R.raw.warn);
              } else if(type == 0x3) {
            	  localMediaPlayer = MediaPlayer.create(GameActivity.getInstance(), R.raw.err);
              } else if(type == 0x4) {
            	  localMediaPlayer = MediaPlayer.create(GameActivity.getInstance(), R.raw.alrm);
              } else if(type == 0x5) {
            	  localMediaPlayer = MediaPlayer.create(GameActivity.getInstance(), R.raw.cfm);
              } else {
            	  localMediaPlayer = MediaPlayer.create(GameActivity.getInstance(), R.raw.info);
              }
              localMediaPlayer.start();
              try {
                  sleep((long)(localMediaPlayer.getDuration() + 0x64));
                  localMediaPlayer.release();
                  return;
              } catch(InterruptedException localInterruptedException7) {
                  localInterruptedException7.printStackTrace();
              }
          }
      }.start();
  }
  

  public static void playTone(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    int j = paramInt2 * 8000 / 1000;
    double d = 8.176D * Math.exp(paramInt1 / 17.312340490667552D);
    byte[] arrayOfByte = new byte[j * 2];
    while (i < j)
    {
      int k = (short)(int)(32767.0D * Math.sin(6.283185307179586D * i / (8000.0D / d)));
      arrayOfByte[(i * 2)] = (byte)(k & 0xFF);
      arrayOfByte[(1 + i * 2)] = (byte)((k & 0xFF00) >>> 8);
      i++;
    }
    try
    {
      AudioTrack localAudioTrack = new AudioTrack(3, 8000, 2, 2, j, 0);
      localAudioTrack.write(arrayOfByte, 0, j);
      localAudioTrack.play();
      return;
    }
    catch (Exception localException)
    {
    }
  }

//  public static void runCommand(ConsoleActivity paramConsoleActivity, String paramString, boolean paramBoolean)
//  {
//    if (paramConsoleActivity == null)
//      throw new IllegalArgumentException();
//    if ((paramString == null) || (paramString.equals("")))
//      throw new IllegalArgumentException();
//    try
//    {
//      java.lang.Process localProcess = Runtime.getRuntime().exec(paramString);
//      BufferedReader localBufferedReader1 = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
//      BufferedReader localBufferedReader2 = new BufferedReader(new InputStreamReader(localProcess.getErrorStream()));
//      BufferedWriter localBufferedWriter = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
//      paramConsoleActivity.setInputStream(localBufferedWriter);
//      new Thread(new Runnable(localBufferedReader1, paramBoolean)
//      {
//        public void run()
//        {
//          char[] arrayOfChar = new char[80];
//          try
//          {
//            while (true)
//            {
//              int i = this.val$outputStream.read(arrayOfChar);
//              if (i == -1)
//                break;
//              String str = "";
//              for (int j = 0; j < i; j++)
//                str = str + arrayOfChar[j];
//              if (!this.val$showOutput)
//                continue;
//              ConsoleActivity.appendOutput(str);
//              ConsoleActivity.outputConsole();
//            }
//          }
//          catch (Exception localException)
//          {
//          }
//        }
//      }).start();
//      new Thread(new Runnable(localBufferedReader2, paramBoolean)
//      {
//        public void run()
//        {
//          char[] arrayOfChar = new char[80];
//          try
//          {
//            while (true)
//            {
//              int i = this.val$errorStream.read(arrayOfChar);
//              if (i == -1)
//                break;
//              String str = "";
//              for (int j = 0; j < i; j++)
//                str = str + arrayOfChar[j];
//              if (!this.val$showOutput)
//                continue;
//              ConsoleActivity.appendOutput(str);
//              ConsoleActivity.outputConsole();
//            }
//          }
//          catch (Exception localException)
//          {
//          }
//        }
//      }).start();
//      localProcess.waitFor();
//      Thread.sleep(1000L);
//      localBufferedReader1.close();
//      localBufferedWriter.close();
//      localBufferedReader2.close();
//      paramConsoleActivity.setInputStream(null);
//      localProcess.destroy();
//      return;
//    }
//    catch (Exception localException)
//    {
//    }
//    throw new RuntimeException(localException.getMessage());
//  }

//  public static void runRootCommand(ConsoleActivity paramConsoleActivity, String paramString, boolean paramBoolean)
//  {
//    try
//    {
//      java.lang.Process localProcess = Runtime.getRuntime().exec("su");
//      DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
//      localDataOutputStream.writeBytes(paramString + "\n");
//      localDataOutputStream.writeBytes("exit\n");
//      localDataOutputStream.flush();
//      BufferedReader localBufferedReader1 = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
//      BufferedReader localBufferedReader2 = new BufferedReader(new InputStreamReader(localProcess.getErrorStream()));
//      BufferedWriter localBufferedWriter = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
//      paramConsoleActivity.setInputStream(localBufferedWriter);
//      new Thread(new Runnable(localBufferedReader1, paramBoolean)
//      {
//        public void run()
//        {
//          char[] arrayOfChar = new char[80];
//          try
//          {
//            while (true)
//            {
//              int i = this.val$outputStream.read(arrayOfChar);
//              if (i == -1)
//                break;
//              String str = "";
//              for (int j = 0; j < i; j++)
//                str = str + arrayOfChar[j];
//              if (!this.val$showOutput)
//                continue;
//              ConsoleActivity.appendOutput(str);
//              ConsoleActivity.outputConsole();
//            }
//          }
//          catch (Exception localException)
//          {
//          }
//        }
//      }).start();
//      new Thread(new Runnable(localBufferedReader2, paramBoolean)
//      {
//        public void run()
//        {
//          char[] arrayOfChar = new char[80];
//          try
//          {
//            while (true)
//            {
//              int i = this.val$errorStream.read(arrayOfChar);
//              if (i == -1)
//                break;
//              String str = "";
//              for (int j = 0; j < i; j++)
//                str = str + arrayOfChar[j];
//              if (!this.val$showOutput)
//                continue;
//              ConsoleActivity.appendOutput(str);
//              ConsoleActivity.outputConsole();
//            }
//          }
//          catch (Exception localException)
//          {
//          }
//        }
//      }).start();
//      localProcess.waitFor();
//      Thread.sleep(1000L);
//      localBufferedReader1.close();
//      localBufferedWriter.close();
//      localBufferedReader2.close();
//      paramConsoleActivity.setInputStream(null);
//      localDataOutputStream.close();
//      localProcess.destroy();
//      return;
//    }
//    catch (Exception localException)
//    {
//    }
//    throw new RuntimeException(localException.getMessage());
//  }

//  public static boolean saveAsset(ConsoleActivity paramConsoleActivity, String paramString1, String paramString2)
//  {
//    byte[] arrayOfByte;
//    FileOutputStream localFileOutputStream;
//    InputStream localInputStream1;
//    try
//    {
//      arrayOfByte = new byte[8192];
//      localFileOutputStream = new FileOutputStream(paramString2);
//      localInputStream1 = paramConsoleActivity.getAssets().open(paramString1);
//      while (true)
//      {
//        int i = localInputStream1.read(arrayOfByte);
//        if (i <= 0)
//          break;
//        localFileOutputStream.write(arrayOfByte, 0, i);
//      }
//    }
//    catch (Exception localException)
//    {
//      Log.d("PhoneME", "Utilities.saveAsset: Saving asset '" + paramString1 + "' to '" + paramString2 + "' failed!");
//      return false;
//    }
//    localInputStream1.close();
//    int j = 1;
//    while (true)
//    {
//      if (j < 100);
//      try
//      {
//        InputStream localInputStream2 = paramConsoleActivity.getAssets().open(paramString1 + "." + j);
//        while (true)
//        {
//          int k = localInputStream2.read(arrayOfByte);
//          if (k <= 0)
//            break;
//          localFileOutputStream.write(arrayOfByte, 0, k);
//        }
//        localInputStream2.close();
//        j++;
//        continue;
//        label186: localFileOutputStream.close();
//        return true;
//      }
//      catch (IOException localIOException)
//      {
//        break label186;
//      }
//    }
//  }
//
//  public static boolean saveAssets(ConsoleActivity paramConsoleActivity)
//  {
//    String str1 = paramConsoleActivity.getApplicationInfo().dataDir;
//    StatFs localStatFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
//    long l = localStatFs.getBlockSize() * localStatFs.getFreeBlocks();
//    if (l < 10485760L)
//    {
//      Log.d("PhoneME", "Utilities.saveAssets: Not enough space in data directory (" + l / 1048576L + "MB left)! phoneME needs at least 10MB internal free space.");
//      ConsoleActivity.appendOutput("\nNot enough space in data directory (" + l / 1048576L + "MB left)! phoneME needs at least 10MB internal free space.\n");
//      ConsoleActivity.outputConsole();
//      2 local2 = new Runnable(paramConsoleActivity, l)
//      {
//        public void run()
//        {
//          Toast.makeText(this.val$activity, "Not enough space in data directory (" + this.val$freeSize / 1048576L + "MB left)! phoneME needs at least 10MB internal free space.", 1).show();
//        }
//      };
//      paramConsoleActivity.getHandler().post(local2);
//      return false;
//    }
//    if (!saveAsset(paramConsoleActivity, "assets.txt", str1 + "/assets.txt"))
//    {
//      Log.d("PhoneME", "Utilities.saveAssets: Failed to save 'assets.txt'!");
//      ConsoleActivity.appendOutput("Failed to save 'assets.txt'!\n");
//      ConsoleActivity.outputConsole();
//      3 local3 = new Runnable(paramConsoleActivity)
//      {
//        public void run()
//        {
//          Toast.makeText(this.val$activity, "Failed to save 'assets.txt'!", 1).show();
//        }
//      };
//      paramConsoleActivity.getHandler().post(local3);
//      return false;
//    }
//    HashSet localHashSet = new HashSet();
//    try
//    {
//      BufferedReader localBufferedReader = new BufferedReader(new FileReader(str1 + "/assets.txt"));
//      boolean bool1 = localBufferedReader.ready();
//      if (!bool1)
//      {
//        Exception localException2;
//        try
//        {
//          throw new IOException("Cannot read file assets.txt.");
//        }
//        catch (Exception localException4)
//        {
//          i = 0;
//          localException2 = localException4;
//        }
//        while (true)
//        {
//          Log.d("PhoneME", "Utilities.safeAssets: Error while saving assets: " + localException2.getMessage());
//          ConsoleActivity.appendOutput("Error while verifying assets: " + localException2.getMessage() + "\n");
//          ConsoleActivity.outputConsole();
//          while (true)
//          {
//            return i;
//            try
//            {
//              while (true)
//              {
//                boolean bool2 = saveAsset(paramConsoleActivity, str3, str1 + "/" + str3);
//                if (!bool2)
//                {
//                  Log.d("PhoneME", "Utilities.saveAssets: Failed to save asset '" + str3 + "'!");
//                  ConsoleActivity.appendOutput("Failed to save asset '" + str3 + "'!\n");
//                  ConsoleActivity.outputConsole();
//                  4 local4 = new Runnable(paramConsoleActivity, str3)
//                  {
//                    public void run()
//                    {
//                      Toast.makeText(this.val$activity, "Failed to save asset '" + this.val$filename + "'!", 1).show();
//                    }
//                  };
//                  paramConsoleActivity.getHandler().post(local4);
//                  i = 0;
//                }
//                String str2 = localBufferedReader.readLine();
//                if (str2 == null)
//                  break;
//                String str3 = str2.replaceAll("\r", "").replaceAll("\n", "").trim();
//                String[] arrayOfString = str3.split("/");
//                String str4 = "";
//                j = 0;
//                if (j >= -1 + arrayOfString.length)
//                  continue;
//                str4 = str4 + "/" + arrayOfString[j];
//                if (localHashSet.contains(str4))
//                  break label657;
//                new File(str1 + str4).mkdir();
//                localHashSet.add(str4);
//                break label657;
//              }
//              localBufferedReader.close();
//            }
//            catch (Exception localException3)
//            {
//            }
//          }
//        }
//      }
//    }
//    catch (Exception localException1)
//    {
//      while (true)
//      {
//        int j;
//        Object localObject = localException1;
//        int i = 1;
//        continue;
//        i = 1;
//        continue;
//        label657: j++;
//      }
//    }
//  }
//
//  public static void verifyAssets(ConsoleActivity paramConsoleActivity)
//  {
//    String str1 = paramConsoleActivity.getApplicationInfo().dataDir;
//    BufferedReader localBufferedReader;
//    try
//    {
//      localBufferedReader = new BufferedReader(new FileReader(str1 + "/assets.txt"));
//      if (!localBufferedReader.ready())
//        throw new IOException();
//    }
//    catch (Exception localException)
//    {
//      Log.d("PhoneME", "Utilities.verifyAsset: Error while verifying assets: " + localException.getMessage());
//      ConsoleActivity.appendOutput("Error while verifying assets: " + localException.getMessage() + "\n");
//      ConsoleActivity.outputConsole();
//      return;
//    }
//    while (true)
//    {
//      String str2 = localBufferedReader.readLine();
//      if (str2 == null)
//        break;
//      String str3 = str2.replaceAll("\r", "").replaceAll("\n", "").trim();
//      if (new File(str1 + "/" + str3).exists())
//        continue;
//      Log.d("PhoneME", "Utilities.verifyAsset: Failed to verify asset '" + str3 + "'!");
//      ConsoleActivity.appendOutput("Failed to verify asset '" + str3 + "'!\n");
//      ConsoleActivity.outputConsole();
//      1 local1 = new Runnable(paramConsoleActivity, str3)
//      {
//        public void run()
//        {
//          Toast.makeText(this.val$activity, "Failed to verify asset '" + this.val$filename + "'!", 1).show();
//        }
//      };
//      paramConsoleActivity.getHandler().post(local1);
//    }
//    localBufferedReader.close();
//  }
}

/* Location:           D:\android\tools\dex2jar-0.0.7.11-SNAPSHOT\dex2jar-0.0.7.11-SNAPSHOT\classes_dex2jar(1).jar
 * Qualified Name:     be.preuveneers.phoneme.fpmidp.Utilities
 * JD-Core Version:    0.6.0
 */
