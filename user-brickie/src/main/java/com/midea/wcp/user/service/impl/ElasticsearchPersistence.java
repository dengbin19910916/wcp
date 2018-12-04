package com.midea.wcp.user.service.impl;

import com.midea.wcp.user.jpa.model.SyncDetail;
import com.midea.wcp.user.service.Persistence;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticsearchPersistence implements Persistence {

    @Override
    public void saveDetail(String appId, List<SyncDetail> userList) {
    }
}
