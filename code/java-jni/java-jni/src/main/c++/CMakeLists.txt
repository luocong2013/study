# 指定cmake的最低版本
CMAKE_MINIMUM_REQUIRED(VERSION 3.26)
# 指定项目名称
PROJECT(LINUXHIDUSB CXX C)

# 搜索文件
# SET(SRC src/LinuxHidUsb.cpp)

# 搜索文件，找某个路径下的所有源文件
AUX_SOURCE_DIRECTORY(${PROJECT_SOURCE_DIR}/src SRC)

# 工程的根目录
MESSAGE(STATUS ">>> PROJECT_SOURCE_DIR " ${PROJECT_SOURCE_DIR})
# 运行 cmake 命令的目录，通常是 ${PROJECT_SOURCE_DIR}/build
MESSAGE(STATUS ">>> PROJECT_BINARY_DIR " ${PROJECT_BINARY_DIR})
# 通过 project 命令定义的项目名称
MESSAGE(STATUS ">>> PROJECT_NAME " ${PROJECT_NAME})
# 当前处理的 CMakeLists.txt 所在的路径
MESSAGE(STATUS ">>> CMAKE_CURRENT_SOURCE_DIR " ${CMAKE_CURRENT_SOURCE_DIR})
# target 编译目录
MESSAGE(STATUS ">>> CMAKE_CURRENT_BINARY_DIR " ${CMAKE_CURRENT_BINARY_DIR})
# CMakeLists.txt 的完整路径
MESSAGE(STATUS ">>> CMAKE_CURRENT_LIST_DIR " ${CMAKE_CURRENT_LIST_DIR})
# 当前所在的行
MESSAGE(STATUS ">>> CMAKE_CURRENT_LIST_LINE " ${CMAKE_CURRENT_LIST_LINE})
MESSAGE(STATUS ">>> UNIX " ${UNIX})



# 指定头文件路径
INCLUDE_DIRECTORIES(${PROJECT_SOURCE_DIR}/include)
INCLUDE_DIRECTORIES(/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/include)
INCLUDE_DIRECTORIES(/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/include/darwin)

ADD_LIBRARY(LinuxHidUsb SHARED ${SRC})
