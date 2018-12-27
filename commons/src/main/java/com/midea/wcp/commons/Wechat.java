package com.midea.wcp.commons;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class Wechat {
    /*public static JsonObject getResponse(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new JsonParser().parse(response.body()).getAsJsonObject();
    }

    public static JsonObject getResponse(String url, String host, Integer port) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .proxy(ProxySelector.of(new InetSocketAddress(InetAddress.getByName(host), port)))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return new JsonParser().parse(response.body()).getAsJsonObject();
    }*/

    public static JsonObject getResponse(String url, String host, Integer port) {
        //设置代理IP、端口、协议（请分别替换）
        HttpHost proxy = new HttpHost(host, port, "http");

        //把代理设置到请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        //实例化CloseableHttpClient对象
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

        //访问目标地址
        HttpGet httpGet = new HttpGet(url);

        //请求返回
        CloseableHttpResponse httpResp = null;
        try {
            httpResp = httpclient.execute(httpGet);

            HttpEntity entity = httpResp.getEntity();
            if (entity == null) {
                return null;
            }

            String httpStr = EntityUtils.toString(entity, "utf-8");
            return new JsonParser().parse(httpStr).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(httpResp);
        }


    }

    public static String getAccessTokenFromWcp(String appKey) {

        RequestConfig defaultRequestConfig = RequestConfig.custom().build();

        //实例化CloseableHttpClient对象
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        String url = "http://weixincs.midea.com/wcp-api/mp/tools/getToken?appKey=" + appKey;

        //访问目标地址
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse httpResp = null;

        try {
            //请求返回
            httpResp = httpclient.execute(httpGet);

            HttpEntity entity = httpResp.getEntity();
            if (entity == null) {
                return null;
            }
            JsonObject jsonObject = new JsonParser().parse(EntityUtils.toString(entity, "utf-8")).getAsJsonObject();
            return jsonObject.get("value").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close(httpResp);
        }
    }

    private static void close(CloseableHttpResponse httpResp) {
        try {
            if (httpResp != null)
                httpResp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
