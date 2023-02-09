package com.hantomjs.demo.controller;

import com.hantomjs.demo.util.HtmlCoverUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author fgq
 * @date 2023年02月09日
 */
@Controller
public class TestController {

    /**
     * 网页转pdf   localhost:8080/pdf
     */
    @RequestMapping("/pdf")
    @ResponseBody
    public void pdf(HttpServletRequest request) throws IOException {
        List<String> params = new ArrayList<>();
        params.add("https://www.baidu.com");
        params.add("C:\\Users\\Administrator\\Desktop\\xxx.pdf");
        params.add("pdf");
        params.add("#wrapper");
        addCookies(params, request.getCookies());
        String staticPath = Objects.requireNonNull(TestController.class.getResource("/static/")).getPath();
        HtmlCoverUtil.conver(staticPath, params);
    }


    /**
     * 网页转img   localhost:8080/img
     */
    @RequestMapping("/img")
    @ResponseBody
    public void img(HttpServletRequest request) throws IOException {
        List<String> params = new ArrayList<>();
        params.add("https://www.baidu.com");
        params.add("C:\\Users\\Administrator\\Desktop\\xxx.jpg");
        params.add("img");
        params.add(".wrapper_new");
        addCookies(params, request.getCookies());
        String staticPath = Objects.requireNonNull(TestController.class.getResource("/static/")).getPath();
        HtmlCoverUtil.conver(staticPath, params);
    }

    private static void addCookies(List<String> params, Cookie[] cookies) {
        StringBuilder cookiesStr = new StringBuilder();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                cookiesStr.append(c.getName()).append("=").append(c.getValue()).append(";");
            }
            cookiesStr.deleteCharAt(cookiesStr.length() - 1);
        } else {
            cookiesStr.append("null");
        }
        params.add(cookiesStr.toString());
    }

}
