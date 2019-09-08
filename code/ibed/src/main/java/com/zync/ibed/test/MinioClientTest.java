package com.zync.ibed.test;

import io.minio.MinioClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.http.MediaType;

import java.util.UUID;

/**
 * MinioClientTest
 *
 * @author luocong
 * @version 3.0.0
 * @description Minio客户端
 * @date 2019年07月01日 20:22
 */
public class MinioClientTest {


    public static void main(String[] args) throws Exception {
        String endpoint = "http://192.168.0.111:9000";
        String accessKey = "dev";
        String secretKey = "123456789";
        MinioClient minioClient = new MinioClient(endpoint, accessKey, secretKey);
        if (!minioClient.bucketExists("ebdp")) {
            minioClient.makeBucket("ebdp");
            System.out.println("创建桶ebdp");
        }

        //ZipFile zipFile = new ZipFile("D:/aa.zip");
        //ZipEntry entry = zipFile.getEntry("WS/0d409ff9d5f54d6586d78f7d642a09bc.pdf");
        //InputStream inputStream = zipFile.getInputStream(entry);

        String fileName = "C:\\Users\\luoc\\Desktop\\ibed2-develop.zip";
        String objectName = getTodayObjectName("44444444", fileName);
        minioClient.putObject("ebdp", "6666/aa.zip", fileName, null, null, null, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        System.out.println(objectName);

        //InputStream inputStream = minioClient.getObject("sacw", "2019/07/06/44444444/12f53ab2b48647199981da9294aa1826.zip");
        //FileUtils.copyInputStreamToFile(inputStream, new File("D:/aa.zip"));
    }

    public static String getTodayObjectName(String bhAj, String fileName) {
        return StringUtils.join(getToday(), "/",
                bhAj, "/",
                getUuid(), ".", FilenameUtils.getExtension(fileName));
    }

    public static String getToday() {
        return FastDateFormat.getInstance("yyyy/MM/dd").format(System.currentTimeMillis());
    }

    private static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
