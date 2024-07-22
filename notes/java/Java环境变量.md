# Java环境变量



## 一、System.getenv()

> 获取环境变量，系统里所有程序都有效，Spring中对应 systemEnvironment



## 二、System.getProperties()

> 获取java系统属性，仅在java平台中有效，使用 -D 指定变量，Spring中对应 systemProperties



## 三、测试

```java
		public static void main(String[] args) {
        System.out.println("==============System.getenv()==============");
        // 获取环境变量，系统里所有程序都有效，Spring中对应 systemEnvironment
        Map<String, String> envMap = System.getenv();
        envMap.forEach((k, v) -> System.out.println("key: " + k + ", val: " + v));
        System.out.println("==============System.getProperties()==============");
        // 获取java系统属性，仅在java平台中有效，使用 -D 指定变量，Spring中对应 systemProperties
        Properties properties = System.getProperties();
        for (String key : properties.stringPropertyNames()) {
            System.out.println("key: " + key + ", val: " + properties.getProperty(key));
        }
    }
```

