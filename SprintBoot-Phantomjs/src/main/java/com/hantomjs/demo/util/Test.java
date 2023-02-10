package com.hantomjs.demo.util;

/**
 * 测试 html 转 pdf、网页
 */
public class Test {

    // phantomjs.exe 文件路径
    public static final String DESKTOP_PATH = "C:\\Users\\Administrator\\Desktop\\";

    // phantomjs.exe 文件路径
    public static final String EXE_PATH = DESKTOP_PATH + "phantomjs.exe";

    // 调用的 JS 文件路径
    public static final String JS_PATH = DESKTOP_PATH + "htmlCover.js";


    public static void main(String[] args) throws Exception {
        test("xxx.pdf", "pdf", "#wrapper");
        test("xxx.jpg", "img", ".wrapper_new");
    }

    private static void test(String fileName, String fileType, String domSelector) throws Exception {
        String argParams = "https://www.baidu.com" + " " + (DESKTOP_PATH + fileName) + " " + fileType + " " + domSelector;
        // ★★★★★★★★★  转换命令，可以直接在命令行运行 ★★★★★★★★★
        String command = EXE_PATH + " " + JS_PATH + " " + argParams;
        System.out.println(command);

        Runtime rt = Runtime.getRuntime();
        rt.exec(command);
    }

}
