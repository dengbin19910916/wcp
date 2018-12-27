package com.midea.wcp.tokenbutler;

import com.midea.wcp.api.AccessToken;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat/access-token")
public class TokenController {
/*
    private final SingleTokenButler tokenButler;

    public TokenController(SingleTokenButler tokenButler) {
        this.tokenButler = tokenButler;
    }

    @GetMapping
    public AccessTokenView getToken(@RequestParam String appId,
                                    @RequestParam String appSecret,
                                    @RequestParam(required = false) String host,
                                    @RequestParam(required = false) Integer port) {
        return new AccessTokenView(tokenButler.get(appId, appSecret, host, port));
    }

    @SuppressWarnings("unused")
    @AllArgsConstructor
    private static class AccessTokenView {
        @NonNull
        private AccessToken accessToken;

        public String getAccessToken() {
            return accessToken.value();
        }

        public int getExpiresIn() {
            return accessToken.getExpiresIn();
        }
    }*/
}
