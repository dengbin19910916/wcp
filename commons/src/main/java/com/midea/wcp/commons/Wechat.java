package com.midea.wcp.commons;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpRequest.BodyPublishers.ofString;

public class Wechat {

    public static JsonObject getResponse(String uri, String host, Integer port) throws IOException, InterruptedException {
        HttpClient client = getClient(host, port);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        return getResult(client, request);
    }

    public static JsonObject postResponse(String uri, String body, String host, Integer port) throws IOException, InterruptedException {
        HttpClient client = getClient(host, port);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(ofString(body))
                .uri(URI.create(uri))
                .build();

        return getResult(client, request);
    }

    private static JsonObject getResult(HttpClient client, HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject result = new JsonParser().parse(response.body()).getAsJsonObject();
        if (!result.get("errcode").isJsonNull() && result.get("errcode").getAsInt() == 40001) {
            throw WechatExceptionFactory.getWechatException(result);
        }

        return result;
    }

    private static HttpClient getClient(String host, Integer port) throws UnknownHostException {
        return host == null ? HttpClient.newHttpClient() :
                HttpClient.newBuilder()
                        .proxy(ProxySelector.of(new InetSocketAddress(InetAddress.getByName(host), port)))
                        .build();
    }
}
