package com.zync.jwt.jsonwebtoken;

/**
 * 单元测试
 *
 * @author luocong
 * @version v1.0
 * @date 2024/4/9 18:08
 */
public class Client {

    private static final String JWT_SALT_LOCAL = "16085#4$46-aab4-*^4c99-b03c-17dc43ae1678";

    private static final String JWT_SALT_TEST_DEV = "16085#4$46-aab4-*^4126-9eb2-9970ee3641bd";

    private static final String JWT_SALT_PRE = "16085#4$46-aab4-*^4ffb-8a04-e6d205d40d90";

    private static final String JWT_SALT_PRD = "16085#4$46-aab4-*^417f-a555-7d2106c3539e";

    public static void main(String[] args) {

        String token = JwtUtil.create("username", "COMMON", 5, JWT_SALT_LOCAL);
        System.out.println(token);

        //String token = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJYWUNSTSIsInN1YiI6InlhbmdqaWEiLCJhdWQiOiJDT01NT04iLCJleHAiOjE3MTI2NjIxNTIsImlhdCI6MTcxMjY2MTg1Mn0.LF6TRcmu8YsSpTfXNWqkjr8oNSfm0Ms3Pl70A9DfVLbVwC5DcSUfMEQ4AUDwcctgtxi5pdCvV7O74HDLn09iog";

        System.out.println(JwtUtil.getUsername(token, "COMMON", JWT_SALT_LOCAL));
    }
}
