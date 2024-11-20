package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import com.example.demo.data.A;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AUtil {

    private static final String LICENCE1 = "D82E1726-7D49-4FEA-9392-DA2EB651B695";
    private static final String LICENCE2 = "77D8A44D-3E93-4A5A-8036-E8A505DEDD64";
    // 获取证书
    private static final String GET_LICENCE = "http://www.mairui.club/getlicence.html";
    // 分时
    private static final String URL_LS = "http://api.mairui.club/zs/sssj/";
    private static final String URL_LS2 = "http://api.mairui.club/hszb/fsjy/000066/dn/";
    private static final String URL_LS3 = "http://api.mairui.club/zs/fsjy/000066/dn/";


    public static void main(String[] args) throws IOException {
        HttpClient client = new DefaultHttpClient();
        List<String> queryList = Arrays.asList("000066", "600536", "000977");
        List<A> result = new ArrayList<>();
        for (String code : queryList) {
            HttpGet get = new HttpGet(URL_LS + code + "/" + LICENCE1);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                HttpEntity httpEntity = response.getEntity();
                String str = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                A a = JSON.parseObject(str, A.class);
                a.setCode(code);
                result.add(a);
            } else {
                System.out.println(code + "请求失败");
            }
        }
        result.forEach(System.out::println);
    }


}
