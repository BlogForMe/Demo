//
// Created by john on 2020-02-11.
//

#include<jni.h>
#include <stdlib.h>
#include <stdio.h>

int test() {
    return 123;
}


jstring Java_com_netease_ndk_MainActivity_sayHello(JNIEnv *env, jobject jobject) {
    char *text = "sayHello";
    return (*env)->NewStringUTF(env, text);
}




/*
#include<stdio.h>
#include<stdlib.h>
#include<jni.h>

jstring Java_com_example_ndkdemo_JNI_sayHello(JNIEnv* env,jobject jobj){

    char* text= "I am from c";
    return (*env)->NewStringUTF(env,text);*/
