# ftppool-spring-boot-starter
ftp connect pool for spring boot starter

## 1. How to use

First you have to use spring boot project.

### 1）Add dependencies

1. maven dependencies

```xml
<dependencies>
  <dependency>
    <groupId>com.zync.boot</groupId>
    <artifactId>ftppool-spring-boot-starter</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

2. gradle dependencies

```gradle
dependencies {
  compile 'com.zync.boot:ftppool-spring-boot-starter:1.0.0'
}
```

### 2）Add the following configuration to the `application.yml` configuration file

```yaml
spring:
  ftp:
    enabled: true
    host: 127.0.0.1
    port: 21
    username: admin
    password: admin
```

### 3）Use `FtpClientService`

`FtpClientExt` obtained by `FtpClientService` extends `org.apache.commons.net.ftp.FTPClient`, you can use `FtpClientExt` like `org.apache.commons.net.ftp.FTPClient`

```java
@Slf4j
@RestController
public class FtpClientController {
    
    @Autowired
    private FtpClientService ftpClientService;

    @GetMapping("/listFtp")
    public List<String> listFtp() {
        try {
            FtpClientExt ftpClient = ftpClientService.getFtpClient();
            try {
                FTPFile[] ftpFiles = ftpClient.listFiles();
                return Arrays.stream(ftpFiles).map(FTPFile::getName).collect(Collectors.toList());
            } catch (IOException e) {
                log.error("Get File Exception", e);
            } finally {
                // return the connection to the connection pool
                ftpClientService.releaseFtpClient(ftpClient);
            }
        } catch (Exception e) {
            log.error("Get FtpClient Exception", e);
        }
        return Collections.emptyList();
    }
}
```
