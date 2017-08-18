#/bin/sh

PWD=`pwd`

export PATH=$PATH:$HOME/work/Android/android-ndk-r9d

rm ../libs/armeabi -rf

ndk-build -B

#CVMDIR=$PWD/../assets/cvm
#if [ -z "$CVMDIR" ]
#then
#	echo "no cvm yes"
#	mkdir $PWD/../assets/cvm -p
#fi
#mv $PWD/../libs/armeabi/kvm $PWD/../assets/cvm/
#cp $HOME/work/Workspace/phoneme_android/cdc/build/linux-arm-generic/foundation/bin/libcvm.so ../libs/armeabi -rf
#cp $HOME/work/Workspace/phoneme_advanced_mr2/cdc/build/linux-arm-generic/foundation/bin/libcvm.so ../libs/armeabi -rf
#cp $HOME/work/Workspace/phoneme_advanced_mr2/cdc/build/linux-arm-generic/foundation_dbg/bin/libcvm_g.so ../libs/armeabi/libcvm.so -rf
