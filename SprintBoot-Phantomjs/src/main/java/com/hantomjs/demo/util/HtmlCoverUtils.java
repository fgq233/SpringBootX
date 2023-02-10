package com.hantomjs.demo.util;


import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Html 转为图片、pdf
 */
public class HtmlCoverUtils {

    /**
     * 网页转换
     *
     * @param path   phantomjs.exe 和 执行的 js 文件路径
     * @param params 参数(网页地址、生成文件存放地址、Cookie......)，每个参数需要以空格分开
     */
    public static void conver(String path, List<String> params) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        sb.append(path).append(ConverType.EXE)
                .append(" ")
                .append(path).append(ConverType.JS)
                .append(" ");
        // 参数，也就是 htmlCover.js 中的 system.args
        if (!CollectionUtils.isEmpty(params)) {
            sb.append(String.join(" ", params));
        }
        // 调用 phantomjs.exe 程序，使用对应 js 文件转换网页
        Runtime rt = Runtime.getRuntime();
        // ★★★★★★★★★  转换命令，可以直接在命令行运行 ★★★★★★★★★
        System.out.println(sb);
        rt.exec(sb.toString());
    }

    /**
     * 添加 Cookie(一般用于认证,在自己的系统才会用到)
     */
    public static void addCookies(List<String> params, Cookie[] cookies) {
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

    /**
     * 获取桌面路径(服务器本机)
     */
    public static String getDesktopDir() {
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        return desktopDir.getAbsolutePath();
    }

}
