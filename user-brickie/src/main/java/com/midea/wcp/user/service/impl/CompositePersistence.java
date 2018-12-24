package com.midea.wcp.user.service.impl;

import com.midea.wcp.commons.model.User;
import com.midea.wcp.user.mybatis.model.MpUser;
import com.midea.wcp.user.service.DetailHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
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

    /**
     * 添加其他业务字段
     */
    @Override
    public void process(String appId, List<User> userList) {
        List<MpUser> mpUserList = new ArrayList<>();
        for (User user : userList) {
            MpUser mpUser = new MpUser();
            BeanUtils.copyProperties(user, mpUser);
            if (user.getSex() == 1) {
                mpUser.setSex("男");
                mpUser.setSexId(1);
            } else if (user.getSex() == 2) {
                mpUser.setSex("女");
                mpUser.setSexId(2);
            } else {
                mpUser.setSex("未知");
                mpUser.setSexId(0);
            }
            mpUser.setSubscribe_time(new Date(user.getSubscribe_time()));
            mpUserList.add(mpUser);
        }
        databasePersistence.saveDetail(appId, mpUserList);
        elasticsearchPersistence.saveDetail(appId, mpUserList);
    }
}
