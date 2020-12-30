package com.zync.security.stricthttpfirewall;

import org.springframework.web.bind.annotation.*;

/**
 * @author luocong
 */
@RestController
public class HelloController {

    @RequestMapping(value = "/hello")
    public void hello(String param) {
        System.out.println("param = " + param);
    }

    @GetMapping("/hi/{id}")
    public void hi(@PathVariable Integer id, @MatrixVariable String name) {
        System.out.println("id = " + id);
        System.out.println("name = " + name);
    }
}
