package com.example.demo.utils;

import java.awt.*;
import java.net.URI;

public class BrowerUtil {

    public static void openBrowse(int num) {
        // 检查Desktop类是否支持打开浏览器
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                // 使用Desktop打开默认浏览器并导航到指定的URL
                Desktop.getDesktop().browse(new URI("http://09thz.com/forum-60-" + num + ".html"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop 不支持打开浏览器或操作不被支持。");
        }
    }

}
