cmake_minimum_required(VERSION 3.4.1)

# 指定头文件目录在哪里
include_directories(src/main/cpp/include)

# 指定生成的文件对应的链接库
link_directories(src/main/jniLibs/armeabi-v7a)

# 收集该目录下的源码文件到SRC_LIST变量中
aux_source_directory(src/main/cpp SRC_LIST)

# 根据源文件生成一个动态库
add_library(native-lib SHARED ${SRC_LIST})


find_library(
        log-lib
        log)

target_link_libraries(
        native-lib
        ${log-lib})