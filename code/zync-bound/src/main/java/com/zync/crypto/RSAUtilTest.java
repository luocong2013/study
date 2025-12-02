package com.zync.crypto;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author luocong
 * @version 1.0
 * @since 2025/11/3 15:20
 **/
public class RSAUtilTest {

    public static void main(String[] args) throws Exception {
        // encrypt();
        // encryptPrivate();
        // decrypt();
        decryptPublic();
        System.out.println(DigestUtils.md5Hex("zjx123456"));
    }

    private static void encrypt() throws Exception {
        String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA50UZCUUKpWvYogSw6wqgfJwwrOIXgQ9yO9XsLDYj6ZtU+P3etWrjSyPn5uGdUEP9l+CWmgZgqQZLO8w34lLRP7dAk9i7G6UTNJNW3rvjN7V7cqNdLKzRKiRcIu6DENAKEwV7yf98h/wKQdXX7+RYMcKWE+JJi2Wb79CWJlFxzjmfi7LYC4LnZn3towjGT0NTbDWUl8rA0xaGMX57/ywU/NVcwfaGosPazr9zsjwg9YvqxTlNoSO2YgPyw0n3lrJG5reWXJbBrag7cdW2t3e3Sr4qEfg9n3pUQR0fVpq3R2AQ1Pnk8B3LQFvy47zPXA4l79uS7xusW/vNgUwI1L4qGQIDAQAB";

        String plainText = DigestUtils.md5Hex("zjx123456");
        System.out.println("plainText: " + plainText);

        String cipherText = RSAUtil.encrypt(pubKey, plainText);

        System.out.println("加密数据: " + cipherText);
    }

    private static void encryptPrivate() throws Exception {
        String priKey = "";

        String plainText = "";

        String cipherText = RSAUtil.encryptPrivate(priKey, plainText);
        System.out.println(cipherText);
    }

    private static void decrypt() throws Exception {
        String priKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDnRRkJRQqla9iiBLDrCqB8nDCs4heBD3I71ewsNiPpm1T4/d61auNLI+fm4Z1QQ/2X4JaaBmCpBks7zDfiUtE/t0CT2LsbpRM0k1beu+M3tXtyo10srNEqJFwi7oMQ0AoTBXvJ/3yH/ApB1dfv5FgxwpYT4kmLZZvv0JYmUXHOOZ+LstgLgudmfe2jCMZPQ1NsNZSXysDTFoYxfnv/LBT81VzB9oaiw9rOv3OyPCD1i+rFOU2hI7ZiA/LDSfeWskbmt5ZclsGtqDtx1ba3d7dKvioR+D2felRBHR9WmrdHYBDU+eTwHctAW/LjvM9cDiXv25LvG6xb+82BTAjUvioZAgMBAAECggEAMw1CyJQQFJIpASLt9wSihr9n01IZhCjnu6Oufdf5t4DNkiVge0W8JQMpFwNo2aYYneU6+Yc84TL9epbLI0/K1zjZrYljTGf1RF7eB08MimiqdpTvZYREq/vYxvFFx3lVL/u+5slaMR6HGR9eipsqiNl+jacfOwFUGe0+p/jEmZQdW1DM8qJEEgEJ6V5QY9+zseZgFNEkZ/KEFPLbDF3obUN020v3odhOrB1iAmQsusJEsm0ub3kaHU40m3aPIl2GAAqTLnWC9y2rTzvj0q+z8fIed8J0K2SWpLJYw73R2KoKL3ek6aFL8Yw5xv/ITrt+L6A0UU9VSZJwolftfjWybQKBgQD4yqohx+3wfCiq/NdGjAcoimSlqBpaUg+flZOCQzIAcDX90Zytair+AS0MRWhOC5rPamOjJhLdkYM4lK210QWzHiN92r/rSBxe7PiCm3D6h+BIOOgaVE2rGQUfN54t+gBbDoYc9tlKTn3IJhfovbXf8tqiMt0GpRObJK5ee+RL2wKBgQDt+HiYeZEhPPN+mBqR7rqS3hlO3CGzTPflAhw0TWGRH0jR/7/GibtXfuonTQH9OY29JlETj/jpjBGjBkwWAz4dqAtJzlwYKI59SKkf9jy7kGp3F+ssX8a3d5l80bxKnc061gJz6l1EZMv4ZK3ifIPApqki9q5ms+LLxHXjklueGwKBgAfathZM7okaElqbktxKTWuNOvIz5WkWkCG0oiyJnImBAWNa+7xJzsOoG9j4U/uqyB9mN3EOIahXELJ3lyhXSfvGps4xWr+9zg99IbdV2vI92enNyh4SrUUKjCvZIKhI7TvysG83p9J9ueDYuwaA/r3sN9TJEEtGaOEL/mlDNgjjAoGBAKna4mkizF5HHR3M/5oOJgnSkYniQgOy+nXxbaQ/X1awV8/clkvl31xycDuhEbxnKi5KfTBTYT2CBsywH/rOUbXlTjleHAn6WMo/5Me1gF/4kqVy4x+HNVWa/mlDYvx3aheGzbVjITNCJvyjECJK8de+nZchMBaKa2U6U1MeElrlAoGBAOb6sPmd2A3u3JwCotahNRiipH1DJF7erOUZ/xT9ZwydZK/vzlkyBhT6jdp7ogofYAMLLXindJi/yiBynK/DfvZo3qF860+apRpG/7bDceEvTyyKyDFX2nPdoTDzXg14pT9Ew1f0SMTAy6Bt7MlzYYxYv0IdTfJJ+g3+22f65jzf";

        String cipherText = "LthavplUQOQKVlmnCUnl4CEpsgl3ZJj4JUwv+MjI7Y5p1QqeTZhJA2i66DRJ1C+/jcpkI0jUJDR1vG8dUBrOvNWV3ygxFxvrRkSd3hgRa12EedohjmjELj9w7+5UJ1885T3ld0+o26M73TWyHG0eEaeI2xyHdBcZgmQPz8QinO2RX9n30GPoAnpZ7l64z+Pjf1UpPSB4VZPVSCklahlK21HkLYclEGrNcFENd7aYcBV5AEqXwX4jwcisTG8t5bmbvYxQP+NxgeHODmZPZ3SbVLxhYVn3tEEEp8Fu/JOIiecznN8m1itj5mS8wB613UgsWBKKg9uGol3lP0VFcSkXRA==";

        String rawPassword = RSAUtil.decrypt(priKey, cipherText);
        System.out.println(rawPassword);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches(rawPassword, "$2a$10$RA5bsnXBB4XpZnutfXF9ROFQLcxY.p4yJhELOXWlB4//9QdNcV9qm"));
    }

    private static void decryptPublic() throws Exception {
        String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArAqVAMvqlNQiQ/Jhz+sJoiNW+R1TV4WNTFSxPGVdY7W1gp3Pegckjhlow8ulXHwsafzJrMJccNG8PKy6eVVvJ6mmfywWt1mUKMMQPcfp9NQp96NUEIyYy5hluLDjdXhgf0T7wv+B7GZkkLVexx5ZGfsRWIs1fIZzsLe3YWYAa3GVqPDoeR5q0nprht9p0mgB5r0mFgl3Hca7qD5Yu3NHTCG13ZxMOIYnu6Y94GCSFLZ0Z3v8la5jsCkt1LMt2Ywchwkdk+S3dDWUSRZUBxgJVs4seO73C2L5ojyuqqznyoM/GfnWx49nBxdc90RaB7j31PHLB/l3rOjb9MpSQJGC5QIDAQAB";

        String cipherText = "LmtDf4FlVeU3hlr77ItyNrzP1wbQ6zhNfAuj7mJ8ZGXBNEUb6P2v//FTXS9K1mcchLJLuq255tgCBjgVkwcQ5sB7FwafEdDg3eBJvEoRZnkc8Boqsou1ktDjHs27ylWo2/U4Ppe3ylbs+mYKsxDzmTEg00QvsisO7Kh7u2lHBaThuDZteyDy524Sb5QmLRpBid77sB5rM+Qrl7vtIQWHF3nBkEPUV6IKlxrVGHW2Vm+TfOXNRLZAMv572Dl7GAeLSVBSbyp5izQrrTBP9wtbrUzJylKGzV40g/kUsn+cwSD+m5X+ApAXd/8QgZhygXkco1VvusoIMwAjGv1CqGfz0w==";

        String plainText = RSAUtil.decryptPublic(pubKey, cipherText);
        System.out.println(plainText);
    }


}
