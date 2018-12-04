package com.midea.wcp.user.service.impl;

import com.midea.wcp.commons.message.OpenIdWrapper;
import com.midea.wcp.user.jpa.model.SyncDetail;
import com.midea.wcp.user.jpa.model.SyncOpenId;
import com.midea.wcp.user.jpa.repository.SyncDetailDao;
import com.midea.wcp.user.jpa.repository.SyncOpenIdDao;
import com.midea.wcp.user.jpa.repository.TestSyncDao;
import com.midea.wcp.user.service.Persistence;
import com.midea.wcp.user.service.SyncUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class DatabasePersistence implements Persistence, SyncUser {

    private ThreadPoolExecutor saveOpenIdExecutor = new ThreadPoolExecutor(2, 3, 20,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    private ThreadPoolExecutor saveDetailExecutor = new ThreadPoolExecutor(2, 3, 20,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    private final TestSyncDao testSyncDao;
    private final SyncOpenIdDao syncOpenIdDao;
    private final SyncDetailDao syncDetailDao;

    @Autowired
    public DatabasePersistence(TestSyncDao testSyncDao, SyncOpenIdDao syncOpenIdDao,
                               SyncDetailDao syncDetailDao) {
        this.testSyncDao = testSyncDao;
        this.syncOpenIdDao = syncOpenIdDao;
        this.syncDetailDao = syncDetailDao;
    }

    @Override
    public void saveDetail(String appId, List<SyncDetail> syncDetailList) {
        int size = 300;
        int idCount = syncDetailList.size();
        int times = (idCount / size) + 1;

        for (int i = 1; i <= times; i++) {
            if (i != times) {
                List<SyncDetail> result = getContinuityDetail((i - 1) * size, i * size - 1, syncDetailList);
                final int temp = i;
                saveDetailExecutor.execute(
                        () -> {
                            syncDetailDao.saveAll(result);
                            log.info("完成同步detail:{}->{}", (temp - 1) * size + 1, temp * size);
                        }
                );
            } else {
                List<SyncDetail> result = getContinuityDetail((i - 1) * size, idCount - 1, syncDetailList);
                final int temp = i;
                saveDetailExecutor.execute(
                        () -> {
                            syncDetailDao.saveAll(result);
                            log.info("完成同步detail:{}->{}", (temp - 1) * size + 1, idCount);
                        }
                );
            }
        }
    }

    private List<SyncDetail> getContinuityDetail(int start, int end, List<SyncDetail> target) {
        List<SyncDetail> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(target.get(i));
        }
        return result;
    }


    @Override
    public void saveOpenid(OpenIdWrapper openIDWrapper) {
        List<String> openIds = openIDWrapper.getOpenIds();
        int size = 200;
        int idCount = openIds.size();
        int times = (idCount / size) + 1;

        for (int i = 1; i <= times; i++) {
            if (i != times) {
                //不是最后一次
                List<SyncOpenId> result = getContinuityOpenId((i - 1) * size, i * size - 1, openIds);
                final int temp = i;
                saveOpenIdExecutor.execute(
                        () -> {
                            syncOpenIdDao.saveAll(result);
                            log.info("完成同步openId:{}->{}", (temp - 1) * size + 1, temp * size);
                        }
                );
            } else {
                //最后一次
                List<SyncOpenId> result = getContinuityOpenId((i - 1) * size, idCount - 1, openIds);
                final int temp = i;
                saveOpenIdExecutor.execute(
                        () -> {
                            syncOpenIdDao.saveAll(result);
                            log.info("完成同步openId:{}->{}", (temp - 1) * size + 1, idCount);
                        });
            }
        }
    }

    private List<SyncOpenId> getContinuityOpenId(int start, int end, List<String> target) {
        List<SyncOpenId> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            SyncOpenId temp = new SyncOpenId();
            temp.setOpenId(target.get(i));
            result.add(temp);
        }
        return result;
    }

}
