package com.midea.wcp.user.service.impl;

import com.midea.wcp.commons.message.OpenIdWrapper;
/*import com.midea.wcp.user.jpa.repository.SyncOpenIdDao;*/
import com.midea.wcp.user.mybatis.mapper.CleanUserMapper;
import com.midea.wcp.user.mybatis.model.MpUser;
import com.midea.wcp.user.service.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class DatabasePersistence implements Persistence {

    private final CleanUserMapper cleanUserMapper;

    @Autowired
    public DatabasePersistence(CleanUserMapper cleanUserMapper) {
        this.cleanUserMapper = cleanUserMapper;
    }

    @Override
    public void saveDetail(String appId, List<MpUser> mpUserList) {
        String tableNamePrefix = "mp_user_";
        String table = tableNamePrefix + appId;
        int size = 200;
        int idCount = mpUserList.size();
        int times = (idCount / size) + 1;

        for (int i = 1; i <= times; i++) {
            if (i != times) {
                List<MpUser> result = getContinuityElement((i - 1) * size, i * size - 1, mpUserList);
                cleanUserMapper.batchSaveMpUser(result, table);
            } else {
                List<MpUser> result = getContinuityElement((i - 1) * size, idCount - 1, mpUserList);
                cleanUserMapper.batchSaveMpUser(result, table);
            }
        }
        log.info("本次完成保存mp user:{}条", idCount);
    }

    private <T> List<T> getContinuityElement(int start, int end, List<T> target) {
        List<T> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(target.get(i));
        }
        return result;
    }

    @Override
    public void saveOpenid(OpenIdWrapper openIDWrapper) {
        String tableNamePrefix = "sync_open_id_";
        String table = tableNamePrefix + openIDWrapper.getAppId();
        List<String> openIds = openIDWrapper.getOpenIds();
        int size = 1000;
        int idCount = openIds.size();
        int times = (idCount / size) + 1;

        for (int i = 1; i <= times; i++) {
            if (i != times) {
                //不是最后一次
                List<String> result = getContinuityElement((i - 1) * size, i * size - 1, openIds);
                cleanUserMapper.batchSaveOpenId(result, table);
            } else {
                //最后一次
                List<String> result = getContinuityElement((i - 1) * size, idCount - 1, openIds);
                cleanUserMapper.batchSaveOpenId(result, table);
            }
        }
        log.info("本次完成保存openId:{}条", idCount);
    }
}
