# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

### libjnicvm.so build
include $(CLEAR_VARS)

LOCAL_MODULE    := jnicvm
LOCAL_SRC_FILES := jvm.c

LOCAL_LDLIBS := \
	-ljnigraphics \
	-lm \
	-llog

#LOCAL_SHARED_LIBRARIES := \
	libcutils

#LOCAL_LDLIBS += lcvm

LOCAL_LDFLAGS:=\
			-L./lib

LOCAL_CFLAGS:= \
	        -I./ 


include $(BUILD_SHARED_LIBRARY)

#########################################################
### cvm exe build
#include $(CLEAR_VARS)
 
#LOCAL_MODULE_TAGS := eng
 
#LOCAL_MODULE:=  cvm 
#
#LOCAL_SRC_FILES:= \
#    cvm.c
# 
#LOCAL_C_INCLUDES += \
#		    $(LOCAL_PATH) 
# 
#LOCAL_LDLIBS := \
#	-ljnicvm \
#	-llog
# 
##LOCAL_SHARED_LIBRARIES := \
#    libutils
# 
##LOCAL_CFLAGS := -DRIL_SHLIB
#
#LOCAL_LDFLAGS:=\
#			-L../libs/armeabi
#
#LOCAL_CFLAGS:= \
#	        -I./
# 
#include $(BUILD_EXECUTABLE) 
