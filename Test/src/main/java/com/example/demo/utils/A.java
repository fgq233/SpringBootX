package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import com.example.demo.data.A8;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class A {

    private static final String LICENCE1 = "D82E1726-7D49-4FEA-9392-DA2EB651B695";
    private static final String LICENCE2 = "77D8A44D-3E93-4A5A-8036-E8A505DEDD64";
    // 获取证书
    private static final String GET_LICENCE = "http://www.mairui.club/getlicence.html";
    // 分时
    private static final String URL_LS1 = "http://api.mairui.club/zs/sssj/000066/";
    private static final String URL_LS2 = "http://api.mairui.club/hszb/fsjy/000066/dn/";
    private static final String URL_LS3 = "http://api.mairui.club/zs/fsjy/000066/dn/";


    public static void main(String[] args) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(URL_LS1 + LICENCE1);
        HttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            HttpEntity httpEntity = response.getEntity();
            String str = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            A8 a8 = JSON.parseObject(str, A8.class);
            System.out.println(a8);
        } else {
            System.out.println("请求失败");
        }

    }


}
