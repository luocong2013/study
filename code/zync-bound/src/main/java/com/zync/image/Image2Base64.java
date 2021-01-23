package com.zync.image;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author luocong
 * @version 3.0.9
 * @description 图片转Base64
 * @date 2021/1/23 9:46
 */
public class Image2Base64 {

    public static void main(String[] args) throws Exception {
        try (InputStream inputStream = new FileInputStream("d:/image_1.jpg");
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(ImageIO.read(inputStream), "png", outputStream);
            byte[] data = outputStream.toByteArray();
            System.out.println("data:image/png;base64," + Base64.getEncoder().encodeToString(data));
        }

    }

}
