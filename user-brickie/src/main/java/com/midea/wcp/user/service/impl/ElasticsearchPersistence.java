package com.midea.wcp.user.service.impl;

import com.midea.wcp.commons.message.OpenIdWrapper;
import com.midea.wcp.user.mybatis.model.MpUser;
import com.midea.wcp.user.service.Persistence;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticsearchPersistence implements Persistence {

    @Override
    public void saveDetail(String appId, List<MpUser> mpUserList) {
    }

    @Override
    public void saveOpenid(OpenIdWrapper openIDWrapper) {

    }
}
