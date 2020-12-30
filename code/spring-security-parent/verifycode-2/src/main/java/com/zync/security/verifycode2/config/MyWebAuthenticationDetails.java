package com.zync.security.verifycode2.config;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luocong
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final long serialVersionUID = -8109680870555141241L;

    private boolean isPassed;

    public MyWebAuthenticationDetails(HttpServletRequest req) {
        super(req);
        String code = req.getParameter("code");
        String verifyCode = (String) req.getSession().getAttribute("verify_code");
        if (code != null && code.equals(verifyCode)) {
            isPassed = true;
        }
    }

    public boolean isPassed() {
        return isPassed;
    }
}
