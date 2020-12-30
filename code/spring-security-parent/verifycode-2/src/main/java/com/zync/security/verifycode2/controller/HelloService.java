package com.zync.security.verifycode2.controller;

import com.zync.security.verifycode2.config.MyWebAuthenticationDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author luocong
 */
@Service
public class HelloService {

    public void hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        System.out.println(details);
    }
}
