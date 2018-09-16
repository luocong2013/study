## <center>JVM性能调优</center>
### 一、JVM参数
1. 可以通过下面的参数打印出 Heap Dump 信息
 * -XX:HeapDumpPath
 * -XX:+PrintGCDetails
 * -XX:+PrintGCTimeStamps
 * -Xloggc:/usr/aaa/dump/heap_trace.txt

2. 通过下面的参数可以控制 OutOfMemoryError 时打印堆信息
 * -XX:+HeapDumpOnOutOfMemoryError


### 二、实例
* Java堆溢出

```java
package com.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @description 堆溢出测试
 * @VM args: -Xms20M -Xmx20M -verbose:gc -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=F:/JVM/dumpfile.hprof -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:F:/JVM/heap_trace.txt
 * @date 2018/9/10 22:13
 */
public class HeapOutOfMemory {

    public static void main(String[] args){
        List<TestCase> cases = new ArrayList<>();
        while (true) {
            cases.add(new TestCase());
        }
    }
}

package com.jvm;

/**
 * @author luoc
 * @version V1.0.0
 * @description 测试用例
 * @date 2018/9/10 22:20
 */
public class TestCase {
}

```

* Java栈溢出

```java
package com.jvm;

/**
 * @author luoc
 * @version V1.0.0
 * @description Java栈溢出
 * @VM args: -Xss128K
 * @date 2018/9/10 22:52
 */
public class StackOverFlow {

    private int i;

    public void plus() {
        i++;
        plus();
    }

    public static void main(String[] args){
        StackOverFlow stackOverFlow = new StackOverFlow();
        try {
            stackOverFlow.plus();
        } catch (Exception e) {
            System.out.println("Exception:stack length: " + stackOverFlow.i);
            e.printStackTrace();
        } catch (Error e) {
            System.out.println("Error:stack length: " + stackOverFlow.i);
            e.printStackTrace();
        }
    }
}

```

### 三、手动获取堆转储文件
1. 采用jamp获取，对于部署到服务器上的程序可以采用这种方式，获取堆转储文件后scp到本地，然后本地分析。获取命令为：
```txt
jmap -dump:format=b,file=<dumpfile.hprof> <pid>
```

2. 在eclipse中安装mat插件，运行程序，File --> new --> Other --> Heap Dump --> next ，选择对应的进程, Finish。这种方式似乎对远程服务器上的程序也可以

### 四、堆转储文件分析
1. jhat
 > 这是最原始的分析工具，它会读取堆转储文件，并运行一个小型的HTTP服务器，该服务器运行你通过一系列网易链接查看堆转储信息
 > 找一台带浏览器的机器访问它，http://ip:7000

2. jvisualvm
 > jvisualvm的监视（Monitor） 选项卡可以从一个运行中的程序获得堆转储文件，也可以打开之前生成堆转储文件。

3. mat
 > 打开map，File --> Open File... --> Leak Suspects
 > 之后会在转储文件同目录内生成一个 *_Suspects.zip文件
 > 解压该文件后可以通过浏览器打开分析结果