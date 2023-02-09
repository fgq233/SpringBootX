package com.hantomjs.demo.util;


import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * Html 转为图片、pdf
 */
public class HtmlCoverUtil {

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
        System.out.println(sb.toString());
        rt.exec(sb.toString());
    }

}
