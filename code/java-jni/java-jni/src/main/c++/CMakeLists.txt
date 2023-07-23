CMAKE_MINIMUM_REQUIRED(VERSION 3.26)
PROJECT(LINUXHIDUSB CXX C)

SET(SRC_LIST src/LinuxHidUsb.cpp)

INCLUDE_DIRECTORIES(include)
INCLUDE_DIRECTORIES(/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/include)
INCLUDE_DIRECTORIES(/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/include/darwin)

ADD_LIBRARY(LinuxHidUsb SHARED ${SRC_LIST})
