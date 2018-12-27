package com.midea.wcp.commons.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenIdWrapper implements Serializable {

    private static final long serialVersionUID = 8951273575700710121L;

    private List<String> openIds;

    private String appId;
    private String appSecret;
    private String host;
    private Integer port;
    private String appKey;
}
