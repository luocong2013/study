package com.zync.pools.alioss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import com.zync.pools.alioss.client.AliOssInfo;
import com.zync.pools.alioss.pool.AliOssClientPoolConfig;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * AliOssClientTest
 *
 * @author luocong
 * @version V1.0.0
 * @description 阿里OSS测试类
 * @date 2020年03月25日 17:00
 */
public class AliOssClientTest {

    public static void main(String[] args) throws Exception {
        AliOssClientFactory factory = new AliOssClientFactory();
        AliOssClientPoolConfig config = new AliOssClientPoolConfig();
        config.setMaxTotal(10);

        AliOssClientPoolManager manager = new AliOssClientPoolManager(factory, config);

        AliOssInfo ossInfo = AliOssInfo.builder()
                .endpoint("http://oss-cn-hangzhou.aliyuncs.com")
                .accessKeyId("<yourAccessKeyId>")
                .accessKeySecret("<yourAccessKeySecret>")
                .bucketName("<yourBucketName>")
                .build();
        OSS ossClient = manager.getOssClient(ossInfo);
        System.out.println(ossClient);
        //listObjects(ossInfo, ossClient);
        //getObjectStream(ossInfo, ossClient);
        //getObjectLocal(ossInfo, ossClient);
        //getObjectCheckpoint(ossInfo, ossClient);
        putObjectFile(ossInfo, ossClient);
        manager.releaseOssClient(ossInfo, ossClient);
        manager.destroy();

    }

    /**
     *
     * @param ossInfo
     * @param ossClient
     * @throws Exception
     */
    private static void putObjectFile(AliOssInfo ossInfo, OSS ossClient) throws Exception {
        String objectName = String.format("xycrm/dev/%s/%s", "2024_03-2024_07", "charge_code_apportion.txt");

        List<String> list = new ArrayList<>();
        list.add("9680d6df8e5c6294018f5aa7cb9d0035");
        list.add("9680d6df8e5c6294018f8e277d891625");
        File file = new File("/Users/luocong/Downloads/charge_code_apportion.txt");
        FileUtils.writeLines(file, StandardCharsets.UTF_8.name(), list);

        try (FileInputStream inputStream = FileUtils.openInputStream(file)) {
            PutObjectResult result = ossClient.putObject(ossInfo.getBucketName(), objectName, inputStream);
            System.out.println(result.getETag());
        } finally {
            FileUtils.deleteQuietly(file);
        }
    }

    /**
     * 断点续传下载
     * @param ossInfo
     * @param ossClient
     * @throws Exception
     */
    private static void getObjectCheckpoint(AliOssInfo ossInfo, OSS ossClient) throws Exception {
        String objectName = String.format("sdk_bill_export/prd/%s/%s", "2024-05", "wechat_detail.txt");
        DownloadFileRequest request = new DownloadFileRequest(ossInfo.getBucketName(), objectName);
        // 指定Object下载到本地文件的完整路径
        request.setDownloadFile("/Users/luocong/Downloads/wechat_detail.txt");
        // 设置分片大小，单位为字节，取值范围为100 KB~5 GB。默认值为100 KB
        request.setPartSize(1024 * 100);
        // 设置分片下载的并发数，默认值为1
        request.setTaskNum(10);
        // 开启断点续传下载，默认关闭
        request.setEnableCheckpoint(true);
        // 设置断点记录文件的完整路径，例如 /Users/luocong/Downloads/examplefile.txt.dcp
        // 只有当Object下载中断产生了断点记录文件后，如果需要继续下载该Object，才需要设置对应的断点记录文件。下载完成后，该文件会被删除。
        request.setCheckpointFile("/Users/luocong/Downloads/wechat_detail.txt.dcp");

        // 下载文件
        try {
            DownloadFileResult result = ossClient.downloadFile(request);
            ObjectMetadata metadata = result.getObjectMetadata();
            System.out.println(metadata.getETag());
            System.out.println(metadata.getLastModified());
            System.out.println(metadata.getUserMetadata().get("meta"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载到本地文件
     * @param ossInfo
     * @param ossClient
     * @throws Exception
     */
    private static void getObjectLocal(AliOssInfo ossInfo, OSS ossClient) throws Exception {
        String objectName = String.format("sdk_bill_export/prd/%s/%s", "2024-05", "ent_cascade_mcu_event.txt");
        ossClient.getObject(new GetObjectRequest(ossInfo.getBucketName(), objectName), new File("/Users/luocong/Downloads/ent_cascade_mcu_event.txt"));
    }

    /**
     * 流式下载
     * @param ossInfo
     * @param ossClient
     * @throws Exception
     */
    private static void getObjectStream(AliOssInfo ossInfo, OSS ossClient) throws Exception {
        String objectName = String.format("sdk_bill_export/prd/%s/%s", "2024-05", "record_charge.txt");
        // OSSObject 包含文件所在的存储空间名称、文件名称、文件元数据以及一个输入流
        OSSObject object = ossClient.getObject(ossInfo.getBucketName(), objectName);
        System.out.println("Object content:");
        System.out.println(object);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                System.out.println("\n" + line);
            }
        }
        // OSSObject 对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作
        object.close();
    }

    /**
     * 列举文件
     * @param ossInfo
     * @param ossClient
     * @throws Exception
     */
    private static void listObjects(AliOssInfo ossInfo, OSS ossClient) throws Exception {
        //ObjectListing objectListing = ossClient.listObjects(ossInfo.getBucketName());
        // 指定列举 10个文件（默认100个）
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(ossInfo.getBucketName()).withMaxKeys(10));
        List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : objectSummaries) {
            System.out.println(s.getKey());
        }
    }
}
