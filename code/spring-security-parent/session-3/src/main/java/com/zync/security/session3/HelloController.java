package com.zync.security.session3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author luocong
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public void hello(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        String name = (String) session.getAttribute("name");
        if (name == null || "".equals(name)) {
            session.setAttribute("name", "javaboy");
            System.out.println("set session");
        }
        System.out.println("name = " + name);
        System.out.println(session.getId());
    }
}
