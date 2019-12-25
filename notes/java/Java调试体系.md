> ### Java调试体系
>

#### 一、添加JVM参数

1. 对于JDK1.3.x或者更早的版本

   > -Xnoagent -Djava.compiler=NONE -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005

2. 对于JDK1.4.x版本

   > -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005

3. 对于JDK1.5 - JDK1.8版本

   > -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005

4. 对于JDK9或者更新的版本

   > -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005



> 备注：
>
> - 使用-X:debug方式，主要是在交互模式下适应，它在性能上会弱于使用-agentlib方式
> - transport: 有两种形式，分别是dt_socket和dt_shmem，需要跨机器，只能使用dt_socket
> - address: 端口号，这里采用的是tcp协议
> - suspend: 如果是y，则需要连上debugger后，线程才开始运行；否则，程序启动的时候直接运行，不会挂起



#### 二、JDB远程调试

1. 建立连接

   > jdb -connect com.sun.jdi.SocketAttach:hostname=172.25.16.4,port=5005
   >
   > --linux环境
   >
   > jdb -attach <ip>:<port>

2. 设置断点

   > stop at com.example.demo.controller.BookController:57

3. 清除对应断点

   > clear com.example.demo.controller.BookController:57

4. 清除所有断点

   > clear

5. 列出当前的变量，包括本地变量，参数变量等

   > locals

6. 打印输出变量x

   > print x

7. 下一步，代码向下执行

   > next

8. 代码执行放过，继续执行，会停留在下一个断点处

   > cont

9. 退出

   > exit