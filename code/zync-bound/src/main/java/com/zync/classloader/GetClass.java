package com.zync.classloader;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author luocong
 * @description
 * @date 2020/5/25 16:23
 */
public class GetClass {

    public static void main(String[] args) {
        try {
            byte[] classData = FileUtils.readFileToByteArray(new File("F:\\ideaProjects\\com\\classloader\\ClassLoaderTest.class"));
            String base64String = Base64.encodeBase64String(classData);
            System.out.println(base64String);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
