package com.zync.ibed.minio;

import com.zync.ibed.exception.MinioRuntimeException;
import com.zync.ibed.utils.StringUtil;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Minio服务抽象类
 * @date 2020/1/4 15:41
 */
@Slf4j
public abstract class AbstractMinioService {
    /**
     * Minio客户端
     */
    protected MinioClient minioClient;

    /**
     * 获取桶名
     * @return
     */
    protected abstract String getBucket();

    /**
     * 检查 bucket 是否存在，如果不存在则创建一个.
     */
    protected void checkAndInitBucket() {
        try {
            // 如果该桶不存在，就创建一个.
            if (!this.minioClient.bucketExists(getBucket())) {
                log.warn("【{}】的桶不存在，将会创建一个。", getBucket());
                this.minioClient.makeBucket(getBucket());
                log.info("【{}】的桶已经创建成功，目前请手动设置该桶为只读。", getBucket());
            }
        } catch (Exception e) {
            throw new MinioRuntimeException(StringUtil.format("检查或初始化Minio中名为【{}】的桶是否存在出错!", getBucket()), e);
        }
    }

    /**
     * 上传文件到 Minio 中.
     * @return
     */
    public void putObject(String objectName, String fileName) throws IOException, XmlPullParserException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, InternalException,
            NoResponseException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, InvalidResponseException {
        this.minioClient.putObject(getBucket(), objectName, fileName, null, null, null, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    /**
     * 上传文件到 Minio 中.
     * @return
     */
    public void putObject(String objectName, InputStream stream) throws IOException, XmlPullParserException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, InternalException,
            NoResponseException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, InvalidResponseException {
        this.minioClient.putObject(getBucket(), objectName, stream, null, null, null, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    /**
     * 获取文件输入流
     * @return
     */
    public InputStream getObject(String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException,
            InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException,
            XmlPullParserException, ErrorResponseException, InvalidResponseException {
        return this.minioClient.getObject(getBucket(), objectName);
    }

    /**
     * 获取minio objectName
     * @param prefix
     * @return
     */
    public List<String> getObjectName(String prefix) throws XmlPullParserException, InsufficientDataException, NoSuchAlgorithmException,
            IOException, NoResponseException, InvalidKeyException, InternalException, InvalidBucketNameException, ErrorResponseException {
        Iterable<Result<Item>> listObjects = this.minioClient.listObjects(getBucket(), prefix);
        List<String> objectNames = new ArrayList<>();
        for (Result<Item> listObject : listObjects) {
            objectNames.add(listObject.get().objectName());
        }
        return objectNames;
    }

    /**
     * 获取minio objectUrl
     * @param prefix
     * @return
     */
    public List<String> getObjectUrl(String prefix) throws XmlPullParserException, InsufficientDataException, NoSuchAlgorithmException,
            IOException, NoResponseException, InvalidKeyException, InternalException, InvalidBucketNameException, ErrorResponseException, InvalidResponseException {
        Iterable<Result<Item>> listObjects = this.minioClient.listObjects(getBucket(), prefix);
        List<String> objectUrls = new ArrayList<>();
        for (Result<Item> listObject : listObjects) {
            objectUrls.add(this.minioClient.getObjectUrl(getBucket(), listObject.get().objectName()));
        }
        return objectUrls;
    }

    /**
     * 列出minio文件
     * @return
     */
    public List<Item> listObjects(String prefix) throws XmlPullParserException, InsufficientDataException, NoSuchAlgorithmException,
            IOException, NoResponseException, InvalidKeyException, InternalException, InvalidBucketNameException, ErrorResponseException {
        Iterable<Result<Item>> listObjects = this.minioClient.listObjects(getBucket(), prefix);
        List<Item> items = new ArrayList<>();
        for (Result<Item> listObject : listObjects) {
            items.add(listObject.get());
        }
        return items;
    }
}
