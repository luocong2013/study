package com.ccyw.springboot.own.thymeleaf.controller;

import com.ccyw.springboot.own.common.bean.SysUser;
import com.ccyw.springboot.own.common.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author luoc
 * @version V1.0.0
 * @description 控制器
 * @date 2018/8/19 15:41
 */
@Controller
@RequestMapping(value = "/thymeleaf")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping(value = "/index")
    public String index(ModelMap map) {
        return "index";
    }

    @GetMapping(value = "/login")
    public String toLogin() {
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password, HttpSession session) {
        System.out.println("登录名：" + username);
        System.out.println("密码是：" + password);
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        return "redirect:/thymeleaf/list";
    }

    @GetMapping("/list")
    public String listSysUser(Map<String, Object> map, HttpSession session) {
        System.out.println(session);
        List<SysUser> sysUsers = sysUserService.selectAll();
        map.put("users", sysUsers);
        return "list";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 清除session
        session.invalidate();
        // 重定向到登录页面
        return "redirect:login";
    }

}
