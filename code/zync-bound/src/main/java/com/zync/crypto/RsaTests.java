package com.zync.crypto;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description RSA非对称加密算法
 * @date 2020/11/25 13:52
 */
public class RsaTests {

    /**
     * RSA私钥，Base64
     */
    private static final String PRIVATE_KEY_BASE64 = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQChAarnklaa8/MBf87V/9PHPC+EPWJrGl0r13MYt6KRpd+JOxZkhhvR1fxak5MXg5S4Cg2nZdYuMMFmRXF7XECA8BTCKb4Dxi6rDCkVPBkW6qbNCEqwkj9y0DiMOXHV1KM13qhG8+jhjXV/S2sevCBgmqpdd5umHF+AZbTW7YC6pwMPxebjFN0ObiliDKFWKa7Mi+5KG6DOuhU51oAtmOX3pzx9z2MigDkV0I4w/Sxstp+3JeVwdUDNOcOAJMqBgmxXR5dE6Dd/9SGu4uU9X5KI0iR4PGv8wST+uBwVwhQ0pykZWzk/aaVyAOnYOu39kzWAb6oKdYjTUXGgYWP0e0/5AgMBAAECggEBAJahGcQ2i2HCcwlnChn5kqcl7op4Z1guPRakJWDthGFg3uXjasq3ENXarZIL2KQ6DOu1NBr1UlC6emV0+HthzlMSyUjxhny/b6/E27arGAnxlAXkCQRyfnuqxAykPZTAr8TZP3PKA4jpMBDcfSYu+NeIO3TgTrU0fECCZpJhdnqoAX8JnrNcfSMwxcI6Yi/pslCCHpCrK498O7LOxX69Qm87pICx88Eq08wn7WbAGzkkqI98wWgUoiswxK/z+2cT2VCdyHxKb4J4djJdguTwvDdQprE/NZWBNUcRMaygenu6TJZPTnuWP7saOm90QqHNAy5ZoNIZZc2G+OtSo+pZ0HUCgYEA4N6hBRuledgMXtg96T9+IqzWLbgJbvJ4zGCM3Iw4qkCy389cfGAISRE2c5Aqc9MDNyes3TmbfK1b/RRCzECz81feJ1FBIPsDilOEJa9es5DHcPoej3jIdEVdczTQ/lYvw2mtRvQQy5icTf1E5D9c7XPLgp3PkqzsrGVtLsHSMM8CgYEAt0u7XFpzYZgANeOuH/94/v96okaIXxWeyzOOPPDkLAN0Ojm46kMgu4JvHfP+MLKWCVV1utjAdngHKUB6T4qxCQBze48MmlEv1XLPuxB6k3iSDk9ZfD8zdFGhIrbwB1NZv0KyQZif5YFRx7fYC+n2WF59iTTy68divDXdt/oB1LcCgYEAkAHC+LR1yovzkCHHtEI3ExnAp9K+J9EcTzZdvzgFafK5Gt9/CR+u7qwZRPTEWxx9aKr950bnLncO/AonpQqis2pIGMHOhcJ6x/EoJcIjUbecIdvsClF9fgiWCht+5AJp4CkjENdokhoPr1pM7ict6dtIL0PFFNetUdI6k8EITL0CgYBoodeTcaK5zE4adRz58Rj935L+vWqtdKcP8h85FH37zgD0F+1B9Q3jXCLYI2cwH1HchCdPgNnlBsF3v/HSJCbJL7O8jb0w/jVjEEYaz95Y/pPJjWxUq7NQrCRir7E3TVJHBujA30IvffHUI1M2Adl5On9I6qvdAhWPwOQPWKzg4wKBgQDXz3jClPWKCmiIvIQYB4SVjuyrdVA9D9yVcyyh3D8M2hwD/YLhp6p0VikIYgDnvifcD+goWQ1Cv9bFW7JAjkPmDY6oPugJLkya33WCjMALL5IDoP2piefGnapXR+0AVo/VXhYsuZ5Y9Sfg126gJr40dH7H9l36dcfTGqSfWnM6jQ==";
    /**
     * RSA公钥，Base64
     */
    private static final String PUBLIC_KEY_BASE64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoQGq55JWmvPzAX/O1f/TxzwvhD1iaxpdK9dzGLeikaXfiTsWZIYb0dX8WpOTF4OUuAoNp2XWLjDBZkVxe1xAgPAUwim+A8YuqwwpFTwZFuqmzQhKsJI/ctA4jDlx1dSjNd6oRvPo4Y11f0trHrwgYJqqXXebphxfgGW01u2AuqcDD8Xm4xTdDm4pYgyhVimuzIvuShugzroVOdaALZjl96c8fc9jIoA5FdCOMP0sbLaftyXlcHVAzTnDgCTKgYJsV0eXROg3f/UhruLlPV+SiNIkeDxr/MEk/rgcFcIUNKcpGVs5P2mlcgDp2Drt/ZM1gG+qCnWI01FxoGFj9HtP+QIDAQAB";

    private static final RSA PRIVATE_RSA = SecureUtil.rsa(PRIVATE_KEY_BASE64, null);

    private static final RSA PUBLIC_RSA = SecureUtil.rsa(null, PUBLIC_KEY_BASE64);

    public static void main(String[] args) {
        pubEnPriDe("admin");

        priEnPubDe("root");
    }

    /**
     * 公钥加密，私钥解密
     * @param data
     */
    private static void pubEnPriDe(String data) {
        String base64 = PUBLIC_RSA.encryptBase64(data, KeyType.PublicKey);
        System.out.println("BASE64: " + base64);

        String hex = PUBLIC_RSA.encryptHex(data, KeyType.PublicKey);
        System.out.println("HEX: " + hex);

        String bcd = PUBLIC_RSA.encryptBcd(data, KeyType.PublicKey);
        System.out.println("BCD: " + bcd);


        String decryptBase64 = PRIVATE_RSA.decryptStr(base64, KeyType.PrivateKey);
        System.out.println("DECRYPT BASE64: " + decryptBase64);

        String decryptHex = PRIVATE_RSA.decryptStr(hex, KeyType.PrivateKey);
        System.out.println("DECRYPT HEX: " + decryptHex);

        String decryptBcd = PRIVATE_RSA.decryptStrFromBcd(bcd, KeyType.PrivateKey);
        System.out.println("DECRYPT BCD: " + decryptBcd);
    }

    /**
     * 私钥加密，公钥解密
     * @param data
     */
    private static void priEnPubDe(String data) {
        String base64 = PRIVATE_RSA.encryptBase64(data, KeyType.PrivateKey);
        System.out.println(base64);

        String decryptStr = PUBLIC_RSA.decryptStr(base64, KeyType.PublicKey);
        System.out.println(decryptStr);
    }
}
