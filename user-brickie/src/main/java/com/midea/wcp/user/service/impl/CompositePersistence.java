package com.midea.wcp.user.service.impl;

import com.midea.wcp.commons.model.User;
import com.midea.wcp.user.jpa.model.SyncDetail;
import com.midea.wcp.user.service.DetailHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompositePersistence implements DetailHandler {

    private final DatabasePersistence databasePersistence;
    private final ElasticsearchPersistence elasticsearchPersistence;

    @Autowired
    public CompositePersistence(DatabasePersistence databasePersistence, ElasticsearchPersistence elasticsearchPersistence) {
        this.databasePersistence = databasePersistence;
        this.elasticsearchPersistence = elasticsearchPersistence;
    }

    @Override
    public void process(String appId, List<User> userList) {
        //添加其他业务字段
        List<SyncDetail> syncDetailList = new ArrayList<>();
        for (Object user : userList) {
            SyncDetail syncDetail = new SyncDetail();
            BeanUtils.copyProperties(user, syncDetail);
            syncDetailList.add(syncDetail);
        }
        databasePersistence.saveDetail(appId, syncDetailList);
        elasticsearchPersistence.saveDetail(appId, syncDetailList);
    }
}
