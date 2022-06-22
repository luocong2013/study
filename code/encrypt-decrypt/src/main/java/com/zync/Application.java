package com.zync;

import com.zync.enums.TypeEnum;
import com.zync.utils.DigestUtil;
import org.apache.commons.io.FileUtils;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * 启动加解密类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/6/22 12:51
 */
public class Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("请问您是需要加密还是解密？%s: %s、%s: %s: ", TypeEnum.ENC.getType(), TypeEnum.ENC.getDesc(), TypeEnum.DEC.getType(), TypeEnum.DEC.getDesc());
        TypeEnum type = TypeEnum.obtainType(scanner.nextLine());
        System.out.printf("请输入%s文件的全路径: ", type.getDesc());
        String pathname = scanner.nextLine();
        System.out.printf("请输入%s密码: ", type.getDesc());
        String password = scanner.nextLine();

        switch (type) {
            case ENC:
                encrypt(password, pathname);
                break;
            case DEC:
                decrypt(password, pathname);
                break;
            default:
                throw new IllegalArgumentException("不支持的类型");
        }
    }

    /**
     * 解密文件
     * @param password 密码
     * @param pathname 文件全路径
     */
    private static void decrypt(String password, String pathname) {
        try {
            PooledPBEStringEncryptor encryptor = buildEncryptor(password);
            File file = new File(pathname);
            List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
            File decryptFile = new File(file.getParent(), file.getName() + ".decrypt");
            for (String line : lines) {
                String message = encryptor.decrypt(line);
                FileUtils.writeStringToFile(decryptFile, message, StandardCharsets.UTF_8, true);
                FileUtils.writeStringToFile(decryptFile, LF, StandardCharsets.UTF_8, true);
            }
            if (!FileUtils.deleteQuietly(file)) {
                System.err.println("删除文件失败！！！");
                return;
            }
            if (!decryptFile.renameTo(file)) {
                System.err.println("重命名文件失败");
                return;
            }
            System.out.println("文件解密成功！！！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密文件
     * @param password 密码
     * @param pathname 文件全路径
     */
    private static void encrypt(String password, String pathname) {
        try {
            PooledPBEStringEncryptor encryptor = buildEncryptor(password);
            File file = new File(pathname);
            List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
            File encryptFile = new File(file.getParent(), file.getName() + ".encrypt");
            for (String line : lines) {
                String encryptedMessage = encryptor.encrypt(line);
                FileUtils.writeStringToFile(encryptFile, encryptedMessage, StandardCharsets.UTF_8, true);
                FileUtils.writeStringToFile(encryptFile, LF, StandardCharsets.UTF_8, true);
            }
            if (!FileUtils.deleteQuietly(file)) {
                System.err.println("删除文件失败！！！");
                return;
            }
            if (!encryptFile.renameTo(file)) {
                System.err.println("重命名文件失败！！！");
                return;
            }
            System.out.println("文件加密成功！！！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建 encryptor
     * @param password
     * @return
     */
    private static PooledPBEStringEncryptor buildEncryptor(String password) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(DigestUtil.md5Hex(password));
        config.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        config.setKeyObtentionIterations("1278");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }

    private static final String LF = "\n";
}
