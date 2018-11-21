package com.midea.wcp.tokenbutler;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wcp.http")
public class HttpClientProperties {

    private Proxy proxy = new Proxy();

    @Data
    public static class Proxy {
        private String host;
        private Integer port;
    }
}
