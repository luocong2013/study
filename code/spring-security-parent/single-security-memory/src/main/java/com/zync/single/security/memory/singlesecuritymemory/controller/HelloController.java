package com.zync.single.security.memory.singlesecuritymemory.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试接口
 * @date 2020/8/23 16:11
 */
@RestController
public class HelloController {

    @Autowired
    private Producer producer;

    /**
     * 获取验证码接口
     */
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletResponse resp, HttpSession session) throws IOException {
        String text = producer.createText();
        session.setAttribute("verify_code", text);
        BufferedImage image = producer.createImage(text);
        resp.setContentType("image/jpeg");
        try (ServletOutputStream out = resp.getOutputStream()) {
            ImageIO.write(image, "jpg", out);
        }
    }

    @GetMapping("/admin/hello")
    public String admin() {
        return "hello admin";
    }

    @GetMapping("/user/hello")
    public String user() {
        return "hello user";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
