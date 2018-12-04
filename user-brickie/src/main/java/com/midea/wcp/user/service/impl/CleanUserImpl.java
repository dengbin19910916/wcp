package com.midea.wcp.user.service.impl;

import com.midea.wcp.user.jpa.model.SyncDetail;
import com.midea.wcp.user.mybatis.mapper.CleanUserMapper;
import com.midea.wcp.user.mybatis.model.Data2Handle;
import com.midea.wcp.user.service.CleanUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CleanUserImpl implements CleanUser {

    private final CleanUserMapper cleanUserMapper;

    @Autowired
    public CleanUserImpl(CleanUserMapper cleanUserMapper) {
        this.cleanUserMapper = cleanUserMapper;
    }

    @Override
    public void clean(String appId) {

        String tableNamePrefix = "mp_user_";
        String openidTablePrefix = "sync_open_id_";


        String originTable = tableNamePrefix + appId;
        String compareTable = openidTablePrefix + appId;

        cleanDuplicateOpenId(originTable);

        setSubZero2One(originTable, compareTable);

        List<Data2Handle> result3 = cleanUserMapper.selectSubOne2Zero("mp_user_wxd19c5a897bbaaaae", "sync_open_id_xxx");

        List<Data2Handle> result2 = cleanUserMapper.selectOpenid2Add("mp_user_wxd19c5a897bbaaaae", "sync_open_id_xxx");
    }

    /**
     * 删除清除重复的openid信息，只保留最新的那一个，不处理订阅状态
     */
    private void cleanDuplicateOpenId(String table) {
        List<Data2Handle> duplicateOpenidList = cleanUserMapper.selectDuplicateOpenid(table);
        if (duplicateOpenidList == null || duplicateOpenidList.size() == 0) {
            return;
        }

        Map<String, List<Integer>> compareMap = new HashMap<>();
        for (Data2Handle data2Handle : duplicateOpenidList) {
            if (compareMap.containsKey(data2Handle.getOpenid())) {
                compareMap.get(data2Handle.getOpenid()).add(data2Handle.getId());
            } else {
                List<Integer> temp = new ArrayList<>();
                temp.add(data2Handle.getId());
                compareMap.put(data2Handle.getOpenid(), temp);
            }
        }

        List<Integer> id2Delete = new ArrayList<>();
        for (String openid : compareMap.keySet()) {
            List<Integer> ids = compareMap.get(openid);
            Collections.sort(ids);
            ids.remove(ids.size() - 1);
            id2Delete.addAll(ids);
        }

        updateDbInTurn(table, id2Delete, "duplicate", 1);
    }


    private void updateDbInTurn(String table, List<Integer> id2Delete, String column, Integer newValue) {
        int size = 800;
        int idCount = id2Delete.size();
        int times = (idCount / size) + 1;

        for (int i = 1; i <= times; i++) {
            if (i != times) {
                List<Integer> result = getContinuityDetail((i - 1) * size, i * size - 1, id2Delete);
                cleanUserMapper.batchUpdateIntegerById(table, result, column, newValue);
            } else {
                List<Integer> result = getContinuityDetail((i - 1) * size, idCount - 1, id2Delete);
                cleanUserMapper.batchUpdateIntegerById(table, result, column, newValue);
            }
        }
    }

    private List<Integer> getContinuityDetail(int start, int end, List<Integer> target) {
        List<Integer> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(target.get(i));
        }
        return result;
    }

    private void setSubZero2One(String originTable, String compareTable) {
        List<Integer> result = cleanUserMapper.selectSubZero2One(originTable, compareTable);
    }

}
