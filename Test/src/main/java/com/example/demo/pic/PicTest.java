package com.example.demo.pic;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PicTest {

    public static final String DIR = "C:\\Users\\fgq\\Desktop";
    public static int num = 1;

    public static void main(String[] args) {
        File source = new File(DIR);
        File[] fileArr = source.listFiles();

        assert fileArr != null;
        List<File> fileList = Arrays.asList(fileArr);
        Collections.sort(fileList, (o1, o2) -> {
            if (o1.getName().length() > o2.getName().length()) {
                return 1;
            } else if (o1.getName().length() < o2.getName().length()) {
                return -1;
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (File file : fileList) {
            if (!file.isDirectory()) {
                file.renameTo(new File(getNewFilePath(file.getName(), num)));
                num++;
            }
        }
    }

    private static String getNewFilePath(String oldName, int num) {
        return DIR + "\\" + getPreFix() + "_" + String.format("%03d", num) + oldName.substring(oldName.indexOf("."));
    }

    public static String getPreFix() {
        int idx = DIR.indexOf("(");
        return DIR.substring(idx - 2, idx);
    }

}
