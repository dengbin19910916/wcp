package com.midea.wcp.user.service;

import com.midea.wcp.commons.message.OpenIdWrapper;
import com.midea.wcp.user.jpa.model.SyncDetail;
import com.midea.wcp.user.mybatis.model.MpUser;

import java.util.List;

public interface Persistence {
    void saveDetail(String appId, List<MpUser> mpUserList);
    void saveOpenid(OpenIdWrapper openIDWrapper);
}
