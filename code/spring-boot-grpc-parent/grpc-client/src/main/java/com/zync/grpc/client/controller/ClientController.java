package com.zync.grpc.client.controller;

import com.zync.grpc.client.service.HelloworldClientService;
import com.zync.grpc.client.service.PlaybookClientService;
import com.zync.grpc.client.service.StudentClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端Controller
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/20 13:10
 */
@RestController
public class ClientController {

    private final HelloworldClientService helloworldClientService;
    private final PlaybookClientService playbookClientService;
    private final StudentClientService studentClientService;

    public ClientController(HelloworldClientService helloworldClientService, PlaybookClientService playbookClientService, StudentClientService studentClientService) {
        this.helloworldClientService = helloworldClientService;
        this.playbookClientService = playbookClientService;
        this.studentClientService = studentClientService;
    }

    @GetMapping("/helloWorld")
    public String helloWorld(@RequestParam(value = "name", defaultValue = "jack") String name) {
        return helloworldClientService.sendMessage(name);
    }

    @GetMapping("/playbook")
    public String playbook() {
        playbookClientService.executePlaybook();
        return "ok!";
    }

    @GetMapping("/realName")
    public String realName(@RequestParam("name") String name) {
        return studentClientService.getRealName(name);
    }

    @GetMapping("/realByAge")
    public String realByAge(@RequestParam("age") String age) {
        studentClientService.getRealByAge(age);
        return "Success!";
    }

    @GetMapping("/studentByAge")
    public String studentByAge(@RequestParam("age") String age) {
        studentClientService.getStudentByAge(age);
        return "Success!";
    }
}
