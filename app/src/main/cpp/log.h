#ifndef ANDROID_OPENSLES_DEMO_LOG_H
#define ANDROID_OPENSLES_DEMO_LOG_H

#include <android/log.h>

#define LOG_OPEN 1

#if(LOG_OPEN==1)
#define LOG(...) __android_log_print(ANDROID_LOG_DEBUG,"zeuslivepush",__VA_ARGS__)
#else
#define LOG(...) NULL
#endif

#endif //ANDROID_OPENSLES_DEMO_LOG_H