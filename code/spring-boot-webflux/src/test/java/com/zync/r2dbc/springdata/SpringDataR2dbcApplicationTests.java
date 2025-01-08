package com.zync.r2dbc.springdata;

import com.zync.r2dbc.springdata.repositories.UserRepositories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * @author luocong
 * @version v2.5.0
 * @since 2025/1/8 16:46
 */
@SpringBootTest
public class SpringDataR2dbcApplicationTests {

    @Autowired
    private UserRepositories userRepositories;


    @Test
    void findByIdInAndNameLike() throws IOException {
        userRepositories.findByIdInAndNameLike(List.of(1806238322551586817L, 1806238322572558338L, 1806238322572558339L, 1806247969794691073L), "张%")
                .subscribe(v -> System.out.println("v = " + v));
        System.in.read();
    }

    @Test
    void findCustomAll() throws IOException {
        userRepositories.findCustomAll()
                .subscribe(v -> System.out.println("v = " + v));
        System.in.read();
    }

}
