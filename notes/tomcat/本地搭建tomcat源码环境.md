## 本地搭建tomcat源码环境



### 一、拉取tomcat源码

```shell
git clone https://github.com/apache/tomcat.git

git checkout 8.5.x
```



### 二、新建pom.xml文件，放入tomcat目录下

pom.xml文件内容

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
http://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>
  <groupId>org.apache.tomcat</groupId>
  <artifactId>apache-tomcat-8.5.79-src</artifactId>
  <name>Tomcat8.5</name>
  <version>8.5</version>

  <build>
    <!--指定源⽬录-->
    <finalName>Tomcat8.5</finalName>
    <sourceDirectory>java</sourceDirectory>
    <resources>
      <resource>
        <directory>java</directory>
      </resource>
    </resources>
    <plugins>
      <!--引⼊编译插件-->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.1</version>
        <configuration>
          <encoding>UTF-8</encoding>
          <source>8</source>
          <target>8</target>
        </configuration>
      </plugin>
    </plugins>
  </build>

  <!--tomcat 依赖的基础包-->
  <dependencies>
    <dependency>
      <groupId>org.easymock</groupId>
      <artifactId>easymock</artifactId>
      <version>3.4</version>
    </dependency>
    <dependency>
      <groupId>org.apache.ant</groupId>
      <artifactId>ant</artifactId>
      <version>1.7.0</version>
    </dependency>
    <dependency>
      <groupId>wsdl4j</groupId>
      <artifactId>wsdl4j</artifactId>
      <version>1.6.2</version>
    </dependency>
    <dependency>
      <groupId>javax.xml</groupId>
      <artifactId>jaxrpc-api</artifactId>
      <version>1.1</version>
    </dependency>
    <dependency>
      <groupId>org.eclipse.jdt.core.compiler</groupId>
      <artifactId>ecj</artifactId>
      <version>4.5.1</version>
    </dependency>
    <dependency>
      <groupId>javax.xml.soap</groupId>
      <artifactId>javax.xml.soap-api</artifactId>
      <version>1.4.0</version>
    </dependency>
  </dependencies>
</project>
```



### 三、初始化jsp解析引擎-jasper

```java
找到 org.apache.catalina.startup.ContextConfig#configureStart 方法

在 webConfig(); 方法下面添加如下代码

// 初始化jsp解析引擎-jasper
context.addServletContainerInitializer(new JasperInitializer(), null);
```



### 四、启动

```
找到 org.apache.catalina.startup.Bootstrap 类

直接运行启动tomcat

浏览器中输入: localhost:8080 观察是否启动成功
```



### 五、将war包放入webapps下



