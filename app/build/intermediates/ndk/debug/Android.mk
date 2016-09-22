LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := app
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libaes4pad.so \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libDecodeWlt.so \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libJctrl_gpio.so \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libJNIEMV.so \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libJNISerial.so \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libN20Epp.so \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libNETLH_E.so \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libserialport.so \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libserial_port.so \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libTctrl_gpio.so \
	C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni\armeabi\libwltdecode.so \

LOCAL_C_INCLUDES += C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\main\jni
LOCAL_C_INCLUDES += C:\Users\MAN\AndroidStudioProjects\POS_Fun_V4.7\app\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
