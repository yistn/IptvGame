#ifndef IPTVGAMEJNI_JVM_H
#define IPTVGAMEJNI_JVM_H

#include<stdio.h>
#include<stdlib.h>
#include<dlfcn.h>

typedef void flushfunc(void *, int, int);
typedef void exitfunc(int);
typedef int invokefunc(char **, int, char **);

typedef int drawChars(int, const jshort *, void *, int, int, int, int, int, int, int, const jchar *, int);
typedef int getFontInfo(int, int, int, int *, int *, int *);
typedef int getCharsWidth(int, int, int, const jchar *, int);


/*
 *jvm point function
 */
void (*jvm_main)(int argc, const char **argv);

void (*jvm_initSurfaceBitmap)(int w, int h, void *ptr, int s, flushfunc *f);
void (*jvm_initApplicationExecDir)(const char *);
void (*jvm_initExit)(exitfunc *e);
void (*jvm_initInvoke)(invokefunc *i);

void (*jvm_resizeFrameBuffer)(int width, int height, void *ptr);
void (*jvm_finalizeFrameBuffer)();

void (*jvm_initDrawChars)(drawChars *draw, getFontInfo *info, getCharsWidth *width);
/*
 * jvm point function end
 */

/*
 * jni function
 */
int  jni_main(int argc, const char *argv[]);

void jni_initSurfaceBitmap(int w, int h, void *ptr, int s, flushfunc *f);
void jni_initExit(exitfunc *e);
void jni_initInvoke(invokefunc *i);

void jni_resizeFrameBuffer(int width, int height, void *ptr);
void jni_finalizeFrameBuffer();

void jni_initDrawChars(drawChars *draw, getFontInfo *info, getCharsWidth *width);
/*
 * jni function end
 */

/*
 * my function
 */
void jni_Onload();
void jni_Unonload();

void flushfuncCallBack(void *pixels, int width, int height);
void exitfuncCallBack(int result);
int invokefuncCallBack(char **invocation, int count, char **result);

//int drawCharsCallBack(int, const jshort *, void *, int, int, int, int, int, int, int, const jchar *, int);
int drawCharsCallBack(int pixel, const jshort *clip, void *dst, int dotted, int face, int style, int size, int x, int y, int anchor, const jchar *chararray, int n);
//int getFontInfoCallBack(int, int, int, int *, int *, int *);
int getFontInfoCallBack(int face, int style, int size, int *ascent, int *descent, int *leading);
//int getCharsWidthCallBack(int, int, int, const jchar *, int);
int getCharsWidthCallBack(int face, int style, int size, const jchar *charArray, int n);

void CallJniUpdate();

#endif //IPTVGAMEJNI_JVM_H
