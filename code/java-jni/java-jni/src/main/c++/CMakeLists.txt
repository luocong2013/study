# 指定cmake的最低版本
CMAKE_MINIMUM_REQUIRED(VERSION 3.26)
# 指定项目名称
PROJECT(LINUXHIDUSB CXX C)

# 搜索文件
# SET(SRC src/LinuxHidUsb.cpp)

# 搜索文件，找某个路径下的所有源文件
AUX_SOURCE_DIRECTORY(${PROJECT_SOURCE_DIR}/src SRC)

# 指定头文件路径
INCLUDE_DIRECTORIES(${PROJECT_SOURCE_DIR}/include)
INCLUDE_DIRECTORIES(/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/include)
INCLUDE_DIRECTORIES(/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/include/darwin)

ADD_LIBRARY(LinuxHidUsb SHARED ${SRC})
