package com.example.demo.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class A {

    private static final String LICENCE = "D82E1726-7D49-4FEA-9392-DA2EB651B695";
    // 获取证书
    private static final String GET_LICENCE = "http://www.mairui.club/getlicence.html";
    // 历史成交
    private static final String URL_LS = "http://api.mairui.club/hsmy/lscjt/000066/" + LICENCE;


    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(URL_LS);
        CloseableHttpResponse response = client.execute(get);
        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
        System.out.println(s);
    }


}
