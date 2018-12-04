package com.midea.wcp.user.service;

import com.midea.wcp.user.jpa.model.SyncDetail;

import java.util.List;

public interface Persistence {
    void saveDetail(String appId, List<SyncDetail> userList);
}
