#include <jni.h>
#include <string>
#include <sstream>
#include "Rect.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_io_fabianterhorst_layoutkit_sample_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::stringstream ss;
    Rect rect = Rect(1, 2, 3, 4);
    ss << "bla" << rect.origin.y;
    std::string hello = ss.str();
    return env->NewStringUTF(hello.c_str());
}
