#include <jni.h>
#include <string>
#include "lame.h"

#include <android/log.h>
#define  LOG_TAG    "testjni"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)


extern "C" JNIEXPORT jstring JNICALL
Java_com_jon_lame_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C+";
//    return env->NewStringUTF(hello.c_str());

    ALOG("输出了消息 %s.", "输出消息");
    ALOG("输出消息");
    return env->NewStringUTF(get_lame_version());
}
