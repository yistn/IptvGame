#!/bin/bash
#IptvGame is Object name ./ is Object path
export PATH=$PATH:$HOME/work/Android/android-sdk-linux/tools
cd jni
./build.sh
cd -
android update project --name IptvGame -t 4 -p ./
#ant release
ant debug
