package com.midea.wcp.site.web;

import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.AccessTokenBook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final AccessTokenBook accessTokenBook;

    public TokenController(AccessTokenBook accessTokenBook) {
        this.accessTokenBook = accessTokenBook;
    }

    @GetMapping
    public AccessToken getAccessToken() {
        return accessTokenBook.get();
    }
}
