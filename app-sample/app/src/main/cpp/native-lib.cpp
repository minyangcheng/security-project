#include <jni.h>
#include <string.h>
#include "logUtil.h"
#include "common.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_min_ndk_sample_MainActivity_login(JNIEnv *env, jobject obj, jstring userName,
                                           jstring password) {

    char *_userName = (char *) env->GetStringUTFChars(userName, NULL);
    char *_password = (char *) env->GetStringUTFChars(password, NULL);

    LOGD("userName=%s , password=%s", _userName, _password);

    int result = loginCheck(_userName, _password);

    env->ReleaseStringUTFChars(userName, _userName);
    env->ReleaseStringUTFChars(password, _password);

    char *success = "success";
    char *fail = "fail";
    return result ? env->NewStringUTF(success) : env->NewStringUTF(fail);
}

extern "C" JNIEXPORT jint JNICALL
Java_com_min_ndk_sample_MainActivity_add(JNIEnv *env, jobject obj, jint a,
                                         jint b) {
    int v1 = a;
    int v2 = b;
    return v1 + v2;
}