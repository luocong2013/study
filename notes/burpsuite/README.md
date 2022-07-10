# BurpSuite资料



## 一、下载安装

下载地址：[https://portswigger.net/Burp/Releases](https://portswigger.net/Burp/Releases)
注册机下载地址：[https://github.com/h3110w0r1d-y/BurpLoaderKeygen/releases](https://github.com/h3110w0r1d-y/BurpLoaderKeygen/releases)



## 二、破解

```shell
$JAVA_17_HOME/bin/java -jar BurpLoaderKeygen.jar
```



## 三、启动

### 3.1 方式一（MacOS）

以下命令保存为文件，名字为：run.sh

```shell
#!/bin/bash

/Library/Java/JavaVirtualMachines/jdk-17.0.3.1.jdk/Contents/Home/bin/java --add-opens=java.desktop/javax.swing=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED -javaagent:BurpLoaderKeygen.jar -noverify -jar burpsuite_pro_v2022.3.9.jar
```



### 3.2 方式二-无窗口（Windows）

以下命令保存为文件，名字为：cmb.vbs

```shell
DIM objShell
DIM command
set objShell=wscript.createObject("wscript.shell")
command="java --add-opens=java.desktop/javax.swing=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED -javaagent:BurpLoaderKeygen.jar -noverify -jar burpsuite_pro_v2022.3.9.jar"
iReturn=objShell.Run(command, 0, TRUE)
```



### 3.3 方式三-带图标（Windows）

以下命令保存为文件，名字为：bp.cs

```c#
using System;
using System.Diagnostics;
using System.IO;
// windows 转换 bp.cs 为 Bp.exe
// C:\Windows\Microsoft.NET\Framework64\v4.0.30319\csc.exe /target:winexe /out:C:\Users\luoc\desktop\Bp.exe /win32icon:C:\Users\luoc\Pictures\IconGroup1001.ico  C:\Users\luoc\Desktop\bp.cs
namespace burpsuite_pro_v2021._7
{
    static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]
        static void Main()
        {
            string startbp_seconde = "java -javaagent:BurpLoaderKeygen.jar -noverify -jar burpsuite_pro_v2021.7.jar";
            if (File.Exists("config.cfg"))
            {
                Exec(startbp_seconde);
            }
            else
            {
                string first = "java -jar BurpLoaderKeygen.jar";
                Exec(first);
            }
        }

        static void Exec(string cmd)
        {
            Process p = new Process();
            //设置要启动的应用程序
            p.StartInfo.FileName = "cmd.exe";
            //是否使用操作系统shell启动
            p.StartInfo.UseShellExecute = false;
            // 接受来自调用程序的输入信息
            p.StartInfo.RedirectStandardInput = true;
            //输出信息
            p.StartInfo.RedirectStandardOutput = true;
            // 输出错误
            p.StartInfo.RedirectStandardError = true;
            //不显示程序窗口
            p.StartInfo.CreateNoWindow = true;
            //启动程序
            p.Start();
            //向cmd窗口发送输入信息
            p.StandardInput.WriteLine(cmd+ "&exit");
            p.StandardInput.AutoFlush = true;
            //获取输出信息
            string strOuput = p.StandardOutput.ReadToEnd();
            //等待程序执行完退出进程
            //p.WaitForExit();
            p.Close();
        }
    }
}
```



#### 3.3.1 windows 转换 bp.cs 为 Bp.exe

```
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\csc.exe /target:winexe /out:C:\Users\luoc\desktop\Bp.exe /win32icon:C:\Users\luoc\Pictures\IconGroup1001.ico  C:\Users\luoc\Desktop\bp.cs


注释: 
/out - exe程序输出位置
/win32icon - exe程序图标
```

