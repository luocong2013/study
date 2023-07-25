## CMake入门教程

CMake基础 CMake概述 CMake基础语法 引入说明：命令执行 详细说明 制作动态库或静态库 CMake日志 操作变量 追加 使用`set`拼接 字符串移除 其他功能 宏定义 CMake的嵌套 添加子目录 CMake流程控制 分支判断  循环

### CMake概述

CMake是一个跨平台的、开源的构建工具，用于自动化构建过程的设置和管理。它的主要作用是简化在各种平台上创建、构建和部署软件的过程。

CMake通过一个名为CMakeLists.txt的配置文件来描述构建过程，这个文件使用CMake的领域特定语言（DSL）编写。CMakeLists.txt文件中定义了各种目标和规则，用于指定源代码文件、头文件、库文件、编译器选项、链接器选项等。

当执行CMake时，它会根据CMakeLists.txt文件生成适用于特定平台的构建文件，如Makefile、Visual Studio项目文件、Xcode项目文件等。这些构建文件可以用于构建软件，并可以集成到各种集成开发环境（IDE）中。

CMake的另一个重要特性是支持跨平台构建。通过使用CMake，开发者可以在不同的操作系统（如Windows、Linux和macOS）上使用相同的构建脚本进行构建和部署。这使得在多个平台上开发和测试软件变得非常容易。

此外，CMake还支持测试和调试功能。它可以生成测试框架，如CTest和CDash，用于自动化测试和监视构建过程中的代码质量。这使得开发者可以在早期阶段发现和修复错误，从而提高软件质量和开发效率。

### CMake基础语法

#### 引入

```cmake
# 这是一个CMakeLists.txt文件
#[[这里是块注释
注释
注释
]]
# 指定cmake的最低版本
cmake_minimum_required(VERSION 3.15.0)

#指定项目名称
project(test)

# set(SRC add.cpp div.cpp mult.cpp main.cpp sub.cpp)
# aux_source_directory(${PROJECT_SOURCE_DIR}/src SRC)
file(GLOB SRC ${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp)
set(EXECUTABLE_OUTPUT_PATH ./simple/aa)
set(CMAKE_CXX_STANDARD 11)
# 指定头文件路径
include_directories(${PROJECT_SOURCE_DIR}/include)

# add_executable(app add.cpp div.cpp mult.cpp main.cpp sub.cpp)
add_executable(app ${SRC})
```

##### 说明：

- 单行注释以`#`开头，多行注释采用`#[[]]`
- `cmake_minimum_required`用于指定使用 CMake 的最低版本号。
- `project` 指定项目名称
- `set` 用于定义变量
- 宏`CMAKE_CXX_STANDARD`用于设定C++的标准
- 宏`EXECUTABLE_OUTPUT_PATH` 指定输出可执行程序的路径
- 宏`PROJECT_SOURCE_DIR`工程文件的根目录
- 宏`CMAKE_CURRENT_SOURCE_DIR`表示当前访问的 CMakeLists.txt 文件所在的路径
- `aux_source_directory`和`set`进行搜索文件
- `include_directories` 指定头文件路径
- `add_executable`生成一个可执行程序，语法为`add_executable(可执行程序名 源文件名称)`

##### 命令执行

```cmake
cmake CMakeList.txt所在的文件路径
# 执行成功后，会在对应的路径下生成makefile
# 之后执行make命令，即可对项目进行构建
make
```

#### 详细说明

1、`project`相关参数

用于定义工程名称，并可指定工程的版本、工程描述、web主页地址、支持的语言（默认情况支持所有语言）可忽略

语法格式：

```cmake
project(<PROJECT-NAME> [<language-name>...])
project(<PROJECT-NAME>
      [VERSION <major>[.<minor>[.<patch>[.<tweak>]]]]
      [DESCRIPTION <project-description-string>]
      [HOMEPAGE_URL <url-string>]
      [LANGUAGES <language-name>...])
```

2、定义变量

语法格式：

```cmake
# [] 中的参数为可选项，可省略
SET(VAR [VALUE] [CACHE TYPE DOCSTRING [FORCE]])  # set(变量名 变量值)
# 取变量值
${变量名}
# e.g:
set(SRC add.cpp div.cpp mult.cpp main.cpp sub.cpp)
add_executable(app ${SRC})
```

3、搜索文件

使用`aux_source_directory`命令或者`file`命令

语法格式：

使用`aux_source_directory` 命令查找某个路径下的所有源文件

```cmake
aux_source_directory(< dir > < variable >)
# dir：要搜索的目录
# variable：将从dir目录下搜索到的源文件列表存储到该变量中
```

`file`语法格式：

```cmake
file(GLOB/GLOB_RECURSE 变量名 要搜索的文件路径和文件类型)
# GLOB: 将指定目录下搜索到的满足条件的所有文件名生成一个列表，并将其存储到变量中。
# GLOB_RECURSE：递归搜索指定目录，将搜索到的满足条件的文件名生成一个列表，并将其存储到变量中。
# e.g:
file(GLOB SRC ${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp)
```

4、头文件包含

将源文件对应的头文件路径指定出来，保证在编译过程中编译器能够找到这些头文件，顺利通过编译。

语法格式：

```cmake
include_directories(headpath)
```

#### 制作动态库或静态库

将源代码编译生成一些静态库或动态库提供给第三方使用。

1、制作静态库

语法格式

```cmake
add_library(库名称 STATIC 源文件1 [源文件2] ...)
```

在Linux中，静态库名字分为三部分：`lib`+`库名字`+`.a`，`add_library`只要指定出库的名字就可以，另外两部分在生成该文件时会自动填充。

举例说明

```cmake
# 指定cmake的最低版本
cmake_minimum_required(VERSION 3.15.0)

#指定项目名称
project(testlib)

set(CMAKE_CXX_STANDARD 11)
file(GLOB SRC ${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp)
include_directories(${PROJECT_SOURCE_DIR}/include)
set(LIBRARY_OUTPUT_PATH /home/test/learn_cmake/simple_lib/lib)
# 生成静态库文件libcalc.a
# add_library(calc SHARED ${SRC})
add_library(calc STATIC ${SRC})
```

2、制作动态库

语法格式：

```cmake
add_library(库名称 SHARED 源文件1 [源文件2] ...)
```

在Linux中，动态库名字分为三部分：`lib`+`库名字`+`.so`，同样只指定出库名字就可以了，另外两部分在生成该文件时会自动填充。

只需将上述代码中的`STATIC`修改为`SHARED`即可

3、指定库的输出路径

```cmake
# 设置动态库生成路径,动态库具备可执行权限
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/lib)
# 使用LIBRARY_OUTPUT_PATH，设置静态库路径，同样适用于动态库
set(LIBRARY_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/lib)
```

3、库文件包含

链接静态库的语法格式：

```cmake
link_libraries(<static lib> [<static lib>...])
# 参数1：指定出要链接的静态库的名字
# 可以是全名 libxxx.a
# 也可以是掐头（lib）去尾（.a）之后的名字 xxx
# 参数2-N：要链接的其它静态库的名字
对于非系统提供库，需要指定库路径
link_directories(<lib path>)
```

链接动态库语法格式：

```cmake
target_link_libraries(
  <target>
  <PRIVATE|PUBLIC|INTERFACE> <item>...
  [<PRIVATE|PUBLIC|INTERFACE> <item>...]...)

参数说明:
# target：指定要加载动态库的文件的名字 可以是源文件、动态库文件、可执行文件
# PRIVATE|PUBLIC|INTERFACE：动态库的访问权限，默认为PUBLIC
# 如果各个动态库之间没有依赖关系，无需做任何设置，三者没有没有区别，一般无需指定，使用默认的 PUBLIC 即可。
# 动态库的链接具有传递性，如果动态库 A 链接了动态库B、C，动态库D链接了动态库A，此时动态库D相当于也链接了动态库B、C，并可以使用动态库B、C中定义的方法。
```

关于PRIVATE|PUBLIC|INTERFACE权限的说明：

- PUBLIC：在public后面的库会被Link到前面的target中，并且里面的符号也会被导出，提供给第三方使用。
- PRIVATE：在private后面的库仅被link到前面的target中，并且终结掉，第三方不能感知你调了什么库。
- INTERFACE：在interface后面引入的库不会被链接到前面的target中，只会导出符号。

动态库与静态库的主要区别：

- 静态库会在生成可执行程序的链接阶段被打包到可执行程序中，可执行程序启动，静态库就被加载到内存。
- 动态库在生成可执行程序的链接阶段**不会**被打包到可执行程序中，当可执行程序被启动并且调用了动态库中的函数的时候，动态库才会被加载到内存。

在`cmake`中指定要链接的动态库的时候，应该将命令写到生成的`可执行文件之后`。

e.g:

```cmake
# 指定要链接的动态库的路径
link_directories(${PROJECT_SOURCE_DIR}/lib)
# 添加并生成一个可执行程序
add_executable(app ${SRC_LIST})
# 指定要链接的动态库
target_link_libraries(app pthread calc)
```

#### CMake日志

用于显示消息，指示Cmake过程。

语法格式：

```cmake
message([STATUS|WARNING|AUTHOR_WARNING|FATAL_ERROR|SEND_ERROR] "message to display" ...)
# (无) ：重要消息
# STATUS ：非重要消息
# WARNING：CMake 警告, 会继续执行
# AUTHOR_WARNING：CMake 警告 (dev), 会继续执行
# SEND_ERROR：CMake 错误, 继续执行，但是会跳过生成的步骤
# FATAL_ERROR：CMake 错误, 终止所有处理过程
```

#### 操作变量

##### 1、追加

项目中的源文件并不一定都在同一个目录中，但这些源文件最终却需要一起进行编译来生成最终的可执行文件或者库文件，通过`file`命令对各个目录下的源文件进行搜索，之后进行字符串拼接。

###### 使用`set`拼接

语法格式：

```cmake
set(变量名1 ${变量名1} ${变量名2} ...)
```

使用`list`拼接

```cmake
list(APPEND <list> [<element> ...])
e.g:
list(APPEND SRC_1 ${SRC_1} ${SRC_2} ${TEMP})
# 使用set命令可以创建一个list。一个在list内部是一个由分号;分割的一组字符串
```

##### 2、字符串移除

语法格式：

```cmake
list(REMOVE_ITEM <list> <value> [<value> ...])
# 移除 main.cpp
list(REMOVE_ITEM SRC_1 ${PROJECT_SOURCE_DIR}/main.cpp)
```

注意：

通过 file 命令搜索源文件的时候得到的是文件的绝对路径

在list中每个文件对应的路径都是一个item，并且都是绝对路径，

在移除的时候也要将该文件的绝对路径指定出来才可以，否则移除操作不会成功。

##### 3、list的其他功能

1、获取 list 的长度。

```cmake
CMAKE
list(LENGTH <list> <output variable>)

LENGTH：子命令LENGTH用于读取列表长度
<list>：当前操作的列表
<output variable>：新创建的变量，用于存储列表的长度。
```

2、读取列表中指定索引的的元素，可以指定多个索引

```cmake
list(GET <list> <element index> [<element index> ...] <output variable>)
```

3、将列表中的元素用连接符（字符串）连接起来组成一个字符串

```cmake
list (JOIN <list> <glue> <output variable>)
# <glue>：指定的连接符（字符串）
```

4、查找列表是否存在指定的元素，若果未找到，返回-1

```cmake
list(FIND <list> <value> <output variable>)
```

5、将元素追加到列表中

```cmake
list (APPEND <list> [<element> ...])
```

6、在list中指定的位置插入若干元素

```cmake
list(INSERT <list> <element_index> <element> [<element> ...])
```

7、将元素插入到列表的0索引位置

```cmake
list (PREPEND <list> [<element> ...])
```

8、将列表中最后元素移除

```cmake
list (POP_BACK <list> [<out-var>...])
```

9、将列表中第一个元素移除

```cmake
list (POP_FRONT <list> [<out-var>...])
```

10、将指定的元素从列表中移除

```cmake
list (REMOVE_ITEM <list> <value> [<value> ...])
```

11、将指定索引的元素从列表中移除

```cmake
list (REMOVE_AT <list> <index> [<index> ...])
```

12、移除列表中的重复元素

```cmake
list (REMOVE_DUPLICATES <list>)
```

13、列表翻转

```cmake
list(REVERSE <list>)
```

14、列表排序

```cmake
list (SORT <list> [COMPARE <compare>] [CASE <case>] [ORDER <order>])
# COMPARE：指定排序方法。有如下几种值可选：
# STRING:按照字母顺序进行排序，为默认的排序方法
# FILE_BASENAME：如果是一系列路径名，会使用basename进行排序
# NATURAL：使用自然数顺序排序
# CASE：指明是否大小写敏感。有如下几种值可选：
# SENSITIVE: 按照大小写敏感的方式进行排序，为默认值
# INSENSITIVE：按照大小写不敏感方式进行排序
# ORDER：指明排序的顺序。有如下几种值可选：
# ASCENDING:按照升序排列，为默认值
# DESCENDING：按照降序排列
```

参考链接：https://subingwen.cn/cmake/CMake-primer/

#### 宏定义

用于控制某些代码是否生效

```cmake
add_definitions(-D宏名称)
```



#### CMake的嵌套

嵌套的 CMake 是一个树状结构，最顶层的 CMakeLists.txt 是根节点，其次都是子节点。

- 根节点 CMakeLists.txt 中的变量全局有效
- 父节点 CMakeLists.txt 中的变量可以在子节点中使用
- 子节点 CMakeLists.txt 中的变量只能在当前节点中使用

##### 添加子目录

语法格式：

```cmake
add_subdirectory(source_dir [binary_dir] [EXCLUDE_FROM_ALL])
参数说明：
# source_dir：指定CMakeLists.txt 源文件和代码文件的位置
# binary_dir：指定输出文件的路径，一般不需要指定，可忽略。
# EXCLUDE_FROM_ALL：在子路径下的目标默认不会被包含到父路径的 ALL 目标里，并且也会被排除在 IDE 工程文件之外。用户必须显式构建在子路径下的目标。
```

根目录CMakeList.txt示例：

```cmake
#指定最低的cmake版本
cmake_minimum_required(VERSION 3.15)
# 项目名
project(test)

#定义变量
#静态库的生成路径
set(LIBPATH ${PROJECT_SOURCE_DIR}/lib)
#可执行文件路径
set(ECECPATH ${PROJECT_SOURCE_DIR}/bin)
#头文件路径
set(HEADPATH ${PROJECT_SOURCE_DIR}/include)
# 库文件名字
set(CALCLIB calc)
set(SORTLIB sort)

# 可执行程序名字
set(APPNAME1 app1)
set(APPNAME2 app2)

# 给当前节点添加子目录 其他CMakeLists.txt对应的目录
add_subdirectory(calc)
add_subdirectory(sort)
add_subdirectory(test1)
add_subdirectory(test2)
```

#### CMake流程控制

##### 分支判断

if 语法格式：

```cmake
if(<condition>)
<commands>
elseif(<condition>) # 可选, 可以重复
<commands>
else()              # 可选
<commands>
endif()
```

条件表达式：

```cmake
True: 1, ON, YES, TRUE, Y, 非零值，非空字符串
False: 0, OFF, NO, FALSE, N, IGNORE, NOTFOUND，空字符串
```

逻辑判断：

```cmake
# NOT
if(NOT <condition>)
# AND
if(<cond1> AND <cond2>)
# OR
if(<cond1> OR <cond2>)
```

比较

```cmake
# 数值比较
if(<variable|string> LESS <variable|string>)  # 小于
if(<variable|string> GREATER <variable|string>)  # 大于
if(<variable|string> EQUAL <variable|string>)  # 等于
if(<variable|string> LESS_EQUAL <variable|string>)  # 小于等于
if(<variable|string> GREATER_EQUAL <variable|string>)  # 大于等于

# 字符串比较
if(<variable|string> STRLESS <variable|string>)   # 小于
if(<variable|string> STRGREATER <variable|string>)  # 大于
if(<variable|string> STREQUAL <variable|string>)  # 等于
if(<variable|string> STRLESS_EQUAL <variable|string>)  # 小于等于
if(<variable|string> STRGREATER_EQUAL <variable|string>)  # 大于等于
```

文件操作

```cmake
# 判断文件或者目录是否存在
if(EXISTS path-to-file-or-directory)
# 判断是不是目录
if(IS_DIRECTORY path)
# 判断是不是软连接
if(IS_SYMLINK file-name)
# 判断是不是绝对路径
if(IS_ABSOLUTE path)
```

其它

```cmake
# 判断某个元素是否在列表中
if(<variable|string> IN_LIST <variable>)
# 比较两个路径是否相等
if(<variable|string> PATH_EQUAL <variable|string>)
```

##### 循环

`foreach`循环

语法格式：

```cmake
foreach(<loop_var> <items>)
  <commands>
endforeach()

e.g:
# 对items中的数据进行遍历，然后通过 loop_var 将遍历到的当前的值取出
foreach(<loop_var> RANGE <stop>)

foreach(item RANGE 10)
   message(STATUS "value: ${item}" )
endforeach()
# 遍历一个整数区间指定起始值，中止值，步长，闭区间
foreach(<loop_var> RANGE <start> <stop> [<step>])

# 对复杂数据遍历，对列表进行遍历
foreach(<loop_var> IN [LISTS [<lists>]] [ITEMS [<items>]])

# 遍历多个列表
foreach(<loop_var>... IN ZIP_LISTS <lists>)
e.g:
foreach(key value IN ZIP_LISTS KEY VALUE)
   message(STATUS "result: key = ${key}, value=${value}" )
endforeach()
```

`while`循环

语法格式

```cmake
while(<condition>)
  <commands>
endwhile()
```



