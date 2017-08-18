#include <string.h>
#include <jni.h>
//#include <JNIHelp.h>
#include <android/bitmap.h>
#include "jvm.h"
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define MAXCHAR 1280
//#define MAXCHAR 256

#define DEBUG
#ifdef DEBUG

//#include <wtf/text/CString.h>
#include <android/log.h>
#undef XLOG
#define XLOG(...) __android_log_print(ANDROID_LOG_INFO, __FILE__, __VA_ARGS__)

#else

#undef XLOG
#define XLOG(...)

#endif // DEBUG

//--------------------------------------------
typedef unsigned short gxj_pixel_type;
void* mpixels = NULL;
static int mwidth = 640;
static int mheight = 530;

JavaVM *g_vm = NULL;
static void *iptv_fn = NULL;
jclass jvmProvider = NULL;
jclass jvmplayerProvider = NULL;
jmethodID updateView = NULL;
jmethodID ParsingPlayer = NULL;

jmethodID construction_id = NULL;
jobject mjvmplayerProvider = NULL;

jstring
Java_com_System_IptvGame_jvm_runGames
(JNIEnv* env, jobject thiz){
    const char *argv[] = {"/data/data/com.NON.iptvgame/foundation/bin/cvm", 
        "-Xmx32m", "-Duser.timezone=GMT+8:00", "-Dmicroedition.location.version=1.0",
        "-Dmicroedition.profiles=MIDP-2.1", "-Dsun.midp.library.name=midp", 
        "-Dsun.midp.home.path=/data/data/com.NON.iptvgame/foundation/midp/midp_linux_fb_gcc", 
        "-Dcom.sun.midp.mainClass.name=com.sun.midp.main.CdcMIDletSuiteLoader",
        "sun.misc.MIDPLauncher", "-jadpath",
        "/data/data/com.NON.iptvgame/foundation/microemu-demo.jad",
        "-suitepath", "/data/data/com.NON.iptvgame/foundation/microemu-demo.jar",
        "-1", "org.microemu.midp.examples.simpledemo.SimpleDemoMIDlet"
    };

    const char *argvs[]= {"/data/data/com.NON.iptvgame/foundation/bin/cvm", 
        "-Xmx32m", "-Duser.timezone=GMT+8:00", "-Dmicroedition.location.version=1.0",
        "-Dmicroedition.profiles=MIDP-2.1", "-Dsun.midp.library.name=midp",
        "-Dsun.midp.home.path=/data/data/com.NON.iptvgame/foundation/midp/midp_linux_fb_gcc",
        "-Dcom.sun.midp.mainClass.name=com.sun.midp.main.CdcMIDletSuiteLoader",
        "sun.misc.MIDPLauncher", "-jadpath",
        "/data/data/com.NON.iptvgame/foundation/Game_PK.jad",
        "-suitepath", "/data/data/com.NON.iptvgame/foundation/Game_PK.jar",
        "-1", "engine.Startup"
    };

    jni_main(15, argvs);
    return (*env)->NewStringUTF(env, "哈哈完成自动化编译 !");
}

typedef struct jvmProp
{
	char key[56];
	char value[MAXCHAR];;
} JVMPROP;

static JVMPROP prop_table[100] ;
int propNum;

void Java_com_System_IptvGame_jvm_setProp
  (JNIEnv* env, jobject thiz, jstring key, jstring value, jint index)
{
	XLOG("line(%d), func(%s) index=%d value length=%d", __LINE__, __func__, index, strlen((*env)->GetStringUTFChars(env, value, 0)));
	char tmpKey[56]="";
	char tmpValue[MAXCHAR]="";
	 

	strcpy(tmpKey, (*env)->GetStringUTFChars(env, key, 0));
    //if (!strcmp("returnURL", (*env)->GetStringUTFChars(env, key, 0))) 
    //    strcpy(tmpValue, "www.baidu.com");
    //else
        strcpy(tmpValue, (*env)->GetStringUTFChars(env, value, 0));
	
    propNum=index+1;	
	strcpy(prop_table[index].key, tmpKey);
	strcpy(prop_table[index].value, tmpValue);
    
	XLOG("tmpKey = %s",  tmpKey);
	XLOG("tmpValue = %s",  tmpValue);

	//(*env)->ReleaseStringUTFChars(env, key, tmpKey);
	//(*env)->ReleaseStringUTFChars(env, value, tmpValue);
	XLOG("line(%d), func(%s)", __LINE__, __func__);
}

void Java_com_System_IptvGame_jvm_runGame
  (JNIEnv* env, jobject thiz, jstring jadUrl, jstring jarUrl, jstring className){
      char **jvmArgs = NULL;
      int i = 0;
      if (!jvmArgs) {
          jvmArgs = (char**)malloc(sizeof(char*) * 100);
          for(i = 0; i < 100; i++)
              jvmArgs[i] = (char*)malloc(sizeof(char) * MAXCHAR);
      }
      const char **argv = jvmArgs;
      
      XLOG("line(%d), func(%s)", __LINE__, __func__);
      int num = 0;
      strcpy(jvmArgs[num++], "/data/data/com.NON.iptvgame/foundation/bin/cvm");
      strcpy(jvmArgs[num++], "-Xss2m");
      strcpy(jvmArgs[num++], "-Xmx32m");
      strcpy(jvmArgs[num++], "-Xms8m");
      strcpy(jvmArgs[num++], "-Duser.timezone=GMT+8:00");
      strcpy(jvmArgs[num++], "-Duser.dir=/data/data/com.NON.iptvgame/foundation/midp");
      strcpy(jvmArgs[num++], "-Dsun.boot.class.path=");
      strcat(jvmArgs[num - 1], (*env)->GetStringUTFChars(env, jarUrl, 0));
      strcpy(jvmArgs[num++], "-Dcom.sun.midp.mainClass.name=com.sun.midp.main.CdcMIDletSuiteLoader");
      strcpy(jvmArgs[num++], "sun.misc.MIDPLauncher");
      strcpy(jvmArgs[num++], "-1");
      //strcpy(jvmArgs[num++], "internal");
      strcpy(jvmArgs[num++], (*env)->GetStringUTFChars(env, className, 0));
      strcpy(jvmArgs[num++], "-suitepath");
      strcpy(jvmArgs[num++], (*env)->GetStringUTFChars(env, jarUrl, 0));
      //strcat(jvmArgs[4], (*env)->GetStringUTFChars(env, jarUrl, 0));

      //sprintf(jvmArgs[num++], "user.dir=%s", "/data/data/com.NON.iptvgame/foundation");
      XLOG("line(%d), func(%s)", __LINE__, __func__);

      {
          FILE * fp;
          char * line = NULL;
          char buf[256] = {0}; /*缓冲区*/
          size_t len = 0;
          ssize_t read;
          fp = fopen((*env)->GetStringUTFChars(env, jadUrl, 0), "r");
          if (fp == NULL)
              exit(EXIT_FAILURE);
#if 0          
          while(fgets(buf,256,fp) != NULL)
          {
              len = strlen(buf);
              XLOG("line %d", len);
              if (buf[len-1] == '\n')
                  buf[len-1] = '\0'; /*去掉换行符*/
              if ((len - 2) == 0)
                  continue;
              XLOG("buf = %s %d\n",buf,len);
              XLOG("%s %d",buf,len - 2);
              char delims[] = ":";
              char *result = NULL;
              char *result2 = NULL;
              result = strtok(buf, delims);
              XLOG("result is \"%s\"", result);
              for (i=0; i<len-strlen(result); i++) {
                  if (*(buf + strlen(result) + 1 + i) != " " || *(buf + strlen(result) + 1 + i) != '\0') {
                      result2 = buf + strlen(result) + 1 + i;
                      break;  
                  }        
                  //XLOG("%c", *(buf + strlen(result) + 1 + i));
              }                   
              XLOG("result2 is \"%s\"", result2);
              if (result2 && result2[0] == ' ')
                  sprintf(jvmArgs[num++], "%s%s", result, result2);
              else
                  sprintf(jvmArgs[num++], "%s %s", result, result2);
          }
#else 
          while(fgets(buf,2048,fp) != NULL)
          {
              len = strlen(buf);
              //if (buf[len-1] == '\n')
              //    buf[len-1] = '\0'; /*去掉换行符*/
              XLOG("line %d\n", len);
              char delims[] = ":";
              char *result = NULL;
              char *result2 = NULL;
              char *p = NULL;
              result = strtok(buf, delims);
              p = result + 1;
              XLOG("result is \"%s\"\n", result);
              while(*(p++)>=' ');
              XLOG("result p = %s\n", p);
              char *dst = p;
              while(*(++dst)>' ');
                *dst = '\0';
              if (*p == ' ')
                  p++;
              //yistn add cf start
              int isEmpty = 0;
              int ncf = 0;
              char *pval = NULL;
              for(ncf=0; ncf < propNum; ncf++)
              {
                  if (strcmp(prop_table[ncf].key, result) == 0) {
                      isEmpty = 1;
                      //XLOG("result prop_table.key = %s\n", prop_table[ncf].key);
                      break;
                  }
                  if (strcmp(result, "resURL") == 0 && strcmp(prop_table[ncf].key, "resUrl") == 0) {
                      //sprintf(jvmArgs[num++], "%s %s", result, prop_table[ncf].value);
                      pval = prop_table[ncf].value;
                  }
                  //XLOG("(*-*) prop_table.key = %s\n", prop_table[ncf].key);
              }
              //yistn add cf end
              if (isEmpty == 0) {
                  //if (pval)
                  //    sprintf(jvmArgs[num++], "%s %s", result, pval);
                  //else
                  sprintf(jvmArgs[num++], "%s %s", result, pval?pval:p);
              }
          }
#endif          
          if (fp)
              fclose(fp);
      }
      /***********************************/
      sprintf(jvmArgs[num++], "%s %s", "isNeedNet", "true");
      sprintf(jvmArgs[num++], "%s %s", "isNeedCheckJad", "true");
      sprintf(jvmArgs[num++], "%s %s", "isNeedPrint", "true");
      //sprintf(jvmArgs[num++], "%s %s", "isPaintFullScreen", "true");
      //sprintf(jvmArgs[num++], "%s %s", "coinTokenName", "true");
      sprintf(jvmArgs[num++], "%s %s", "isEnableRecharge", "true");
      //sprintf(jvmArgs[num++], "%s %s", "isPointRecharge", "true");
      sprintf(jvmArgs[num++], "%s %s", "isAndroid", "true");
      //sprintf(jvmArgs[num++], "%s %s", "isShowFrame", "true");
      sprintf(jvmArgs[num++], "%s %s", "isTokenVersions", "true");
      sprintf(jvmArgs[num++], "%s %s", "isTwiceBuyConfirm", "true");
      sprintf(jvmArgs[num++], "%s %s", "isKDPopOnOK", "true");
      sprintf(jvmArgs[num++], "%s %s", "isJumpToRet", "true");
      /***********************************/


      XLOG("line(%d), func(%s)", __LINE__, __func__);
      for (i=0; i< propNum; i++)
      {
          strcpy(jvmArgs[num+i], prop_table[i].key);
          strcat(jvmArgs[num+i]," ");
          strcat(jvmArgs[num+i],prop_table[i].value);
      }

      XLOG("line(%d), func(%s)", __LINE__, __func__);
      for (i=0; i < num + propNum; i++)
      {
          XLOG("argv[%d] = %s", i, jvmArgs[i]);
          argv[i] = jvmArgs[i];
      }
     
      XLOG("line(%d), func(%s)", __LINE__, __func__);
      jni_initApplicationExecDir(argv[0]);
      jni_main(num+propNum, argv);
      XLOG("line(%d), func(%s)", __LINE__, __func__);
      //(*env)->ReleaseStringUTFChars(env, jadUrl, jvmArgs[num - 5]);
      //(*env)->ReleaseStringUTFChars(env, jarUrl, jvmArgs[num - 3]);
      //(*env)->ReleaseStringUTFChars(env, className, jvmArgs[num - 1]);
      
      for(i=0;i<100;i++)
          free((void *)jvmArgs[i]);

      free((void *)jvmArgs);
      jvmArgs = NULL;

      XLOG("line(%d), func(%s)", __LINE__, __func__);
}
void Java_com_System_IptvGame_jvm_runGameAuto
  (JNIEnv* env, jobject thiz, jstring jadUrl, jstring jarUrl, jstring appDir, jstring className){
      char **jvmArgs = NULL;
      int i = 0;
      if (!jvmArgs) {
          jvmArgs = (char**)malloc(sizeof(char*) * 100);
          for(i = 0; i < 100; i++)
              jvmArgs[i] = (char*)malloc(sizeof(char) * MAXCHAR);
      }
      const char **argv = jvmArgs;
      
      XLOG("line(%d), func(%s)", __LINE__, __func__);
      int num = 0;
      strcpy(jvmArgs[num++], (*env)->GetStringUTFChars(env, appDir, 0));
      strcat(jvmArgs[num -1], "/foundation/bin/cvm");
      strcpy(jvmArgs[num++], "-Xss2m");
      strcpy(jvmArgs[num++], "-Xmx32m");
      strcpy(jvmArgs[num++], "-Xms8m");
      strcpy(jvmArgs[num++], "-Duser.timezone=GMT+8:00");
      strcpy(jvmArgs[num++], "-Duser.dir=");
      strcat(jvmArgs[num - 1], (*env)->GetStringUTFChars(env, appDir, 0));
      strcat(jvmArgs[num - 1], "/foundation/midp");
      strcpy(jvmArgs[num++], "-Dsun.boot.class.path=");
      strcat(jvmArgs[num - 1], (*env)->GetStringUTFChars(env, jarUrl, 0));
      strcpy(jvmArgs[num++], "-Dcom.sun.midp.mainClass.name=com.sun.midp.main.CdcMIDletSuiteLoader");
      strcpy(jvmArgs[num++], "sun.misc.MIDPLauncher");
      strcpy(jvmArgs[num++], "-1");
      strcpy(jvmArgs[num++], (*env)->GetStringUTFChars(env, className, 0));
      strcpy(jvmArgs[num++], "-jadpath");
      strcpy(jvmArgs[num++], (*env)->GetStringUTFChars(env, jadUrl, 0));
      strcpy(jvmArgs[num++], "-suitepath");
      strcpy(jvmArgs[num++], (*env)->GetStringUTFChars(env, jarUrl, 0));

      XLOG("line(%d), func(%s)", __LINE__, __func__);
      for (i=0; i< propNum; i++)
      {
          strcpy(jvmArgs[num+i], prop_table[i].key);
          strcat(jvmArgs[num+i]," ");
          strcat(jvmArgs[num+i],prop_table[i].value);
          XLOG("line(%d), func(%s)", __LINE__, __func__);
          if (!strcmp(prop_table[i].key, "ret_url"))
          {
              XLOG("line(%d), func(%s)", __LINE__, __func__);
              num++;
              strcpy(jvmArgs[num+i], "returnUrl");
              strcat(jvmArgs[num+i]," ");
              strcat(jvmArgs[num+i],prop_table[i].value);
          }
      }

      XLOG("line(%d), func(%s)", __LINE__, __func__);
      for (i=0; i < num + propNum; i++)
      {
          XLOG("argv[%d] = %s", i, jvmArgs[i]);
          argv[i] = jvmArgs[i];
      }
     
      XLOG("line(%d), func(%s)", __LINE__, __func__);
      jni_initApplicationExecDir(argv[0]);
      jni_main(num+propNum, argv);
      XLOG("line(%d), func(%s)", __LINE__, __func__);
      //(*env)->ReleaseStringUTFChars(env, jadUrl, jvmArgs[num - 5]);
      //(*env)->ReleaseStringUTFChars(env, jarUrl, jvmArgs[num - 3]);
      //(*env)->ReleaseStringUTFChars(env, className, jvmArgs[num - 1]);
      
      for(i=0;i<100;i++)
          free((void *)jvmArgs[i]);

      free((void *)jvmArgs);
      jvmArgs = NULL;

      XLOG("line(%d), func(%s)", __LINE__, __func__);
}

void Java_com_System_IptvGame_jvm_initSurfaceBitmap(JNIEnv* env, jobject  obj, jint width, jint height, jint s, jobject bitmap)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    AndroidBitmapInfo  info;
    void*              pixels;
    int ret;
    if ((ret = AndroidBitmap_getInfo(env, bitmap, &info)) < 0) {
        XLOG("AndroidBitmap_getInfo() failed ! error=%d", ret);
        return;
    }
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    ret = AndroidBitmap_lockPixels(env, bitmap, &pixels);
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    mpixels = pixels;
    int iwidth=(int)info.width;
    int iheight=(int)info.height;
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    AndroidBitmap_unlockPixels(env, bitmap);
    XLOG("line(%d), func(%s) iwidth=%d, iheight=%d", __LINE__, __func__, iwidth, iheight);

    //yistn add 
    jni_initSurfaceBitmap(iwidth, iheight, pixels, sizeof(gxj_pixel_type) * iwidth * iheight, flushfuncCallBack);
    jni_initInvoke(invokefuncCallBack);
    jni_initExit(exitfuncCallBack);
    XLOG("line(%d), func(%s)", __LINE__, __func__);
}

//------------------------------------------------

int registerClass()
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    JNIEnv *env = NULL;
    if ((*g_vm)->GetEnv(g_vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        XLOG("--------------JNI_OnLoad() error!!");
        return -1;
    }

    XLOG("line(%d), func(%s)", __LINE__, __func__);
    if(jvmProvider == NULL) {
        XLOG("line(%d), func(%s)", __LINE__, __func__);
        jvmProvider = (*env)->FindClass(env, "com/System/IptvGame/jvm");
        if(jvmProvider == NULL){
            XLOG("line(%d), func(%s)", __LINE__, __func__);
            return -1;
        }
    }
    
    if(jvmplayerProvider == NULL) {
        XLOG("line(%d), func(%s)", __LINE__, __func__);
        //jvmplayerProvider = (*env)->FindClass(env, "com/System/IptvGame/JVMPlayer");
        jvmplayerProvider = (*env)->FindClass(env, "com/System/IptvGame/MediaPlayerProxy");
        if(jvmplayerProvider == NULL) {
            XLOG("line(%d), func(%s)", __LINE__, __func__);
            return -1;
        }
    }

    if (updateView == NULL) {
        XLOG("line(%d), func(%s)", __LINE__, __func__);
        updateView = (*env)->GetStaticMethodID(env, jvmProvider, "updateView","()V");
        if (updateView == NULL) {
            XLOG("line(%d), func(%s)", __LINE__, __func__);
            (*env)->DeleteLocalRef(env, jvmProvider);
            return -1;
        }
    }

    if (ParsingPlayer == NULL) {
        XLOG("line(%d), func(%s)", __LINE__, __func__);
        ParsingPlayer = (*env)->GetMethodID(env, jvmplayerProvider, "ParsingPlayer","([Ljava/lang/String;)[Ljava/lang/String;");
        //ParsingPlayer = (*env)->GetMethodID(env, jvmProvider, "ParsingPlayer","([Ljava/lang/String;)[Ljava/lang/String;");
        if (ParsingPlayer == NULL) {
            XLOG("line(%d), func(%s)", __LINE__, __func__);
            (*env)->DeleteLocalRef(env, jvmplayerProvider);
            return -1;
        }
    }

    if (mjvmplayerProvider == NULL) {
        XLOG("line(%d), func(%s)", __LINE__, __func__);
         jmethodID
             construction_id = (*env)->GetMethodID(env,
                     jvmplayerProvider,"<init>", "()V");
        mjvmplayerProvider = (*env)->NewObject(env, jvmplayerProvider, construction_id);
        if (mjvmplayerProvider == NULL) {
            XLOG("line(%d), func(%s)", __LINE__, __func__);
            (*env)->DeleteLocalRef(env, mjvmplayerProvider);
            return -1;
        }
        mjvmplayerProvider = (*env)->NewGlobalRef (env, mjvmplayerProvider);
    }

    return JNI_VERSION_1_4;
}

void unRegisterClass()
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    JNIEnv *env = NULL;
    if ((*g_vm)->GetEnv(g_vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        XLOG("--------------JNI_OnLoad() error!!");
        return;
    }
    if(jvmProvider != NULL) {
        (*env)->DeleteLocalRef(env, jvmProvider);
    }
    
    if(jvmplayerProvider != NULL) {
        (*env)->DeleteLocalRef(env, jvmplayerProvider);
    }
    
    if (updateView != NULL) {
        XLOG("line(%d), func(%s)", __LINE__, __func__);
        (*env)->DeleteLocalRef(env, updateView);
    }

    if (ParsingPlayer != NULL) {
        XLOG("line(%d), func(%s)", __LINE__, __func__);
        (*env)->DeleteLocalRef(env, ParsingPlayer);
    }

    if (mjvmplayerProvider != NULL) {
        XLOG("line(%d), func(%s)", __LINE__, __func__);
        (*env)->DeleteLocalRef(env, mjvmplayerProvider);
    }
    XLOG("line(%d), func(%s)", __LINE__, __func__);
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    XLOG("--------------JNI_OnLoad() START!!");
    g_vm = vm;
    jni_Onload();
    XLOG("--------------JNI_OnLoad() END!!");
    return registerClass();
}

JNIEXPORT jint JNICALL JNI_Unload(JavaVM* vm, void* reserved)
{
    XLOG("--------------JNI_OnUnLoad() START!!");
    jni_Unonload();
    unRegisterClass();
    XLOG("--------------JNI_OnUnLoad() END!!");
}


void jni_Onload()
{
    iptv_fn = dlopen("/system/lib/libcvm.so",RTLD_NOW);
    //iptv_fn = dlopen("./libcvm.so",RTLD_NOW);
    if(iptv_fn == NULL)
    {
        printf("dlopen error!\n");
        XLOG("dlopen error!");
        XLOG("[%s]", dlerror());
        return;
    }

    printf("dlopen ok!");
    XLOG("dlopen ok!");
    return;
}

void jni_Unonload()
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    if (iptv_fn != NULL)
        dlclose(iptv_fn);
}

int jni_main(int argc, const char *argv[])
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    int i = 0;

    if (iptv_fn) {
        XLOG("line(%d), func(%s)", __LINE__, __func__);
        jvm_main = dlsym(iptv_fn, "main");
        XLOG("line(%d), func(%s)", __LINE__, __func__);
        (*jvm_main)(argc, argv);
        XLOG("line(%d), func(%s)", __LINE__, __func__);
    }
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    
    return 0;
}

void jni_initSurfaceBitmap(int w, int h, void *ptr, int s, flushfunc *f)
{
    XLOG("line(%d), func(%s), width(%d), height(%d), s(%d), f(%p)", __LINE__, __func__, w, h, s, f);
    mwidth = w; 
    mheight = h;
    if (iptv_fn) {
        jvm_initSurfaceBitmap = dlsym(iptv_fn, "initSurfaceBitmap");
        (*jvm_initSurfaceBitmap)(w, h, ptr, s, f);
    }
}

void jni_initApplicationExecDir(const char *appDir)
{
    XLOG("line(%d), func(%s), appDir(%s), ", __LINE__, __func__, appDir);
    if (iptv_fn) {
        jvm_initApplicationExecDir = dlsym(iptv_fn, "initApplicationExecDir");
        (*jvm_initApplicationExecDir)(appDir);
    }
}
void jni_initExit(exitfunc *e)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    if (iptv_fn) {
        jvm_initExit = dlsym(iptv_fn, "initExit");
        (*jvm_initExit)(e);
    }
    XLOG("line(%d), func(%s)", __LINE__, __func__);
}

void jni_initInvoke(invokefunc *i)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    if (iptv_fn) {
        jvm_initInvoke = dlsym(iptv_fn, "initInvoke");
        (*jvm_initInvoke)(i);
    }
    XLOG("line(%d), func(%s)", __LINE__, __func__);
}

void jni_resizeFrameBuffer(int width, int height, void *ptr)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    if (iptv_fn) {
        jvm_resizeFrameBuffer = dlsym(iptv_fn, "resizeFrameBuffer");
        (*jvm_resizeFrameBuffer)(width, height, ptr);
    }
    XLOG("line(%d), func(%s)", __LINE__, __func__);
}

void jni_finalizeFrameBuffer()
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    if (iptv_fn) {
        jvm_finalizeFrameBuffer = dlsym(iptv_fn, "finalizeFrameBuffer");
        (*jvm_finalizeFrameBuffer)();
    }
    XLOG("line(%d), func(%s)", __LINE__, __func__);
}

void jni_initDrawChars(drawChars *draw, getFontInfo *info, getCharsWidth *width)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    if (iptv_fn) {
        jvm_initDrawChars = dlsym(iptv_fn, "initDrawChars");
        (*jvm_initDrawChars)(draw, info, width);
    }
    XLOG("line(%d), func(%s)", __LINE__, __func__);
}
//----
//----CallBack
//------
void flushfuncCallBack(void *pixels, int width, int height)
{
    //XLOG("line(%d), func(%s), mpixels(%p), pixels(%p), width(%d), height(%d), mwidth(%d), mheight(%d)", __LINE__, __func__, mpixels, pixels, width, height, mwidth, mheight);
    int i = 0;
    if (!mpixels || !pixels) {
        XLOG("line(%d), func(%s), mpixels=%p, pixels=%p one NULL", __LINE__, __func__, mpixels, pixels);
        return;
    }

    if (mpixels != pixels) {
        //XLOG("line(%d), func(%s), mpixels(%p), pixels(%p), width(%d), height(%d), mwidth(%d), mheight(%d)", __LINE__, __func__, mpixels, pixels, width, height, mwidth, mheight);
        for (i=0; i < (mheight<=height?mheight:height); i++) {
            memcpy(mpixels + i*mwidth*sizeof(gxj_pixel_type), pixels + i*width*sizeof(gxj_pixel_type), sizeof(gxj_pixel_type) * (mwidth<=width?mwidth:width));
        }
    }
    //XLOG("line(%d), func(%s), %p", __LINE__, __func__, pixels);
    CallJniUpdate();
}

void exitfuncCallBack(int result)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    return;
}

int invokefuncCallBack(char **invocation, int count, char **result)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    int ret = 0;
    ret = CallJniParsingPlayer(invocation, count, result);
    XLOG(" line(%d), func(%s), ret(%d)", __LINE__, __func__, ret);
    return ret;
}

int drawCharsCallBack(int pixel, const jshort *clip, void *dst, int dotted, int face, int style, int size, int x, int y, int anchor, const jchar *chararray, int n)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    return 0;
}

int getFontInfoCallBack(int face, int style, int size, int *ascent, int *descent, int *leading)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    return 0;
}

int getCharsWidthCallBack(int face, int style, int size, const jchar *charArray, int n)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    return 0;
}

void CallJniUpdate()
{
    //XLOG("line(%d), func(%s)", __LINE__, __func__);
    int iSAttach = 0;
    JNIEnv *env = NULL;
    int GetEnvResult = (*g_vm)->GetEnv(g_vm, (void **) &env,
            JNI_VERSION_1_4);
    if (GetEnvResult != JNI_OK) {
        GetEnvResult = (*g_vm)->AttachCurrentThread(g_vm, &env, NULL);
        if (GetEnvResult != JNI_OK) {
            XLOG("[INFO] ChangeChannelCallBack error  GetEnvResult!!!!!!!!!!!");
            return;
        }
        else
            iSAttach = 1;
    }
    //XLOG("line(%d), func(%s)", __LINE__, __func__);
    (*env)->CallStaticVoidMethod(env, jvmProvider, updateView);
    //XLOG("line(%d), func(%s)", __LINE__, __func__);
    //(*g_vm)->DetachCurrentThread(g_vm);
    if (iSAttach)
        (*g_vm)->DetachCurrentThread(g_vm);
}

int CallJniParsingPlayer(char **invocation, int count, char **result)
{
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    int i = 0;
    int j = 0; 
    int iSAttach = 0;
    JNIEnv *env = NULL;

    int GetEnvResult = (*g_vm)->GetEnv(g_vm, (void **) &env,
            JNI_VERSION_1_4);
    if (GetEnvResult != JNI_OK) {
		XLOG("[INFO] AttachCurrentThread !!!!!!!!!!!");
        GetEnvResult = (*g_vm)->AttachCurrentThread(g_vm, &env, NULL);
        if (GetEnvResult != JNI_OK) {
            XLOG("[INFO]  error  GetEnvResult!!!!!!!!!!!");
            return;
        }
        else
            iSAttach = 1;
    }
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    jobjectArray labelArray = 0;
    jclass objClass = (*env)->FindClass(env, "java/lang/String");    
    labelArray = (*env)->NewObjectArray(env, count, objClass, 0);    

    for (j = 0; j < count; j++) {    
        XLOG("line(%d), func(%s), str(%s)", __LINE__, __func__, invocation[j]);
        jstring strTmp = (*env)->NewStringUTF(env, invocation[j]);    
        (*env)->SetObjectArrayElement(env, labelArray, j, strTmp);    
        (*env)->DeleteLocalRef(env, strTmp);
    }    

    XLOG("line(%d), func(%s)", __LINE__, __func__);
    jobjectArray array = (jobjectArray)
        (*env)->CallObjectMethod(env, mjvmplayerProvider, ParsingPlayer, labelArray);
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    
    int counts = (*env)->GetArrayLength(env, array);
    XLOG("line(%d), func(%s)", __LINE__, __func__);
    for (i = 0; i < counts; i++) {
        jstring jstr = (*env)->GetObjectArrayElement(env, array, i);
        const char *str = (*env)->GetStringUTFChars(env, jstr, 0);                                                                                    
        strcpy(result[i], str);                  
        XLOG("line(%d), func(%s), str(%s)", __LINE__, __func__, str);
        (*env)->ReleaseStringUTFChars(env, jstr, str);
    }        
    (*env)->DeleteLocalRef(env, array);
    (*env)->DeleteLocalRef(env, labelArray);
    if (iSAttach)
        (*g_vm)->DetachCurrentThread(g_vm);
    XLOG("line(%d), func(%s), counts = %d", __LINE__, __func__, counts);
    return counts;
}
