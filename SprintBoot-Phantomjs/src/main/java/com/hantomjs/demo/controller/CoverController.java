package com.hantomjs.demo.controller;

import com.hantomjs.demo.util.HtmlCoverUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 页面转换
 */
@Controller
public class CoverController {

    /**
     * 转换界面
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 转换
     *
     * @param htmlUrl     要转换的网页地址
     * @param fileType    转换成什么类型的文件
     * @param domSelector 是否根据页面元素裁剪，如 "#id值"、 ".class值"
     */
    @RequestMapping("/doCover")
    @ResponseBody
    public String pdf(HttpServletRequest request, String htmlUrl, String fileType, String domSelector, String cookies, String domain) {
        List<String> params = new ArrayList<>();
        if (StringUtils.isEmpty(htmlUrl)) {
            return "网页地址为空";
        } else {
            params.add(htmlUrl);
        }
        // 保存文件全路径，示例：C:\\Users\\Administrator\\Desktop\\xxx.pdf"
        String savePath = HtmlCoverUtils.getDesktopDir() + File.separator + UUID.randomUUID() + "." + fileType;
        params.add(savePath);
        params.add(fileType);
        if (!StringUtils.isEmpty(cookies) && !StringUtils.isEmpty(domain)) {
            params.add(cookies);
            params.add(domain);
        }
        if (!StringUtils.isEmpty(domSelector)) {
            params.add("#" + domSelector);
        }
        // HtmlCoverUtils.addCookies(params, request.getCookies());
        String staticPath = Objects.requireNonNull(CoverController.class.getResource("/static/")).getPath();
        try {
            HtmlCoverUtils.conver(staticPath, params);
            return savePath;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


}
