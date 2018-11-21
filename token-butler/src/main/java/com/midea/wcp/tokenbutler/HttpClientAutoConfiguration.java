package com.midea.wcp.tokenbutler;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.UnknownHostException;
import java.net.http.HttpClient;

@Configuration
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {

    private final HttpClientProperties properties;

    public HttpClientAutoConfiguration(HttpClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public HttpClient httpClient() throws UnknownHostException {
        HttpClientProperties.Proxy proxy = properties.getProxy();
        if (StringUtils.isEmpty(proxy.getHost())) {
            return HttpClient.newHttpClient();
        }
        return HttpClient.newBuilder()
                .proxy(ProxySelector.of(new InetSocketAddress(InetAddress.getByName(proxy.getHost()), proxy.getPort())))
                .build();
    }

}
