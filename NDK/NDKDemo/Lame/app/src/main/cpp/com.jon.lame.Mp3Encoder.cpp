//
// Created by john on 2020-02-12.
//

#include "com.jon.lame.Mp3Encoder.h"

#include <stdio.h>
#include "lame.h"
#include "Mp3_encode.h"


Mp3Encoder *encoder = NULL;

extern "C" JNIEXPORT jint JNICALL Java_com_jon_lame_Mp3Encoder_init
        (JNIEnv *env,
         jobject jobj,
         jstring pcmPathParam,
         jint audioChannelsParam,
         jint bitRateParam,
         jint sampleRateParam,
         jstring mp3PahtParam){
    const char* pcmPath = env->GetStringUTFChars(pcmPathParam,NULL);
    const char* mp3Path = env->GetStringUTFChars(mp3PahtParam,NULL);
    encoder = new Mp3Encoder();
    int ret = encoder->lint(pcmPath,
                            mp3Path,
                            sampleRateParam,
                            audioChannelsParam,
                            bitRateParam);
    env->ReleaseStringUTFChars(mp3PahtParam, mp3Path);
    env->ReleaseStringUTFChars(pcmPathParam, pcmPath);
    return ret;
}

extern "C" JNIEXPORT void JNICALL Java_com_jon_lame_Mp3Encoder_encode
        (JNIEnv *, jobject){
    encoder->Encode();
}

extern "C" JNIEXPORT void JNICALL Java_com_jon_lame_Mp3Encoder_destroy
        (JNIEnv *, jobject){
    encoder->Destory();
}