package com.https.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.cert.X509Certificate;

/**
 * @author fgq
 */
@RestController
public class TestController {

    @ResponseBody
    @RequestMapping("test")
    public String test(HttpServletRequest request) {
//        双向认证时获取客户端证书
//        X509Certificate[] certs = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
        return "success";
    }

}
