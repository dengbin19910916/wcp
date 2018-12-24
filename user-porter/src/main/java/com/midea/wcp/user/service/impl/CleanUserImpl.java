package com.midea.wcp.user.service.impl;

import com.google.gson.JsonArray;
import com.midea.wcp.user.util.RabbitMQUtil;
import com.midea.wcp.user.mybatis.mapper.CleanUserMapper;
import com.midea.wcp.user.mybatis.model.Data2Handle;
import com.midea.wcp.user.service.CleanUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CleanUserImpl implements CleanUser {

    private final CleanUserMapper cleanUserMapper;

    private final RabbitMQUtil rabbitMQUtil;

    @Autowired
    public CleanUserImpl(CleanUserMapper cleanUserMapper, RabbitMQUtil rabbitMQUtil) {
        this.cleanUserMapper = cleanUserMapper;
        this.rabbitMQUtil = rabbitMQUtil;
    }

    @Override
    public void cleanOpenIdTable(String table) {
        cleanUserMapper.deleteAll(table);
    }

    @Override
    public void cleanDuplicateOpenId(String table) {
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

        deleteInTurn(table, id2Delete);

    }

    @Override
    public void setSubZero2One(String originTable, String compareTable) {
        List<Integer> result = cleanUserMapper.selectSubZero2One(originTable, compareTable);
        if (result == null || result.size() == 0) {
            return;
        }
        updateDbInTurn(originTable, result, "subscribe", 1);
    }

    @Override
    public void setSubOne2Zero(String originTable, String compareTable) {
        List<Integer> result = cleanUserMapper.selectSubOne2Zero(originTable, compareTable);
        if (result == null || result.size() == 0) {
            return;
        }
        updateDbInTurn(originTable, result, "subscribe", 0);
    }

    @Override
    public void pullNullData(String originTable, String compareTable, String appId, String appSecret, String host, Integer port) {
        List<String> result = cleanUserMapper.selectOpenid2Add(originTable, compareTable);
        if (result == null || result.size() == 0) {
            return;
        }
        //分批发送
        int size = 100;
        int idCount = result.size();
        int times = (idCount / size) + 1;

        for (int i = 1; i <= times; i++) {
            if (i != times) {
                List<String> temp = getContinuityElement((i - 1) * size, i * size - 1, result);
                JsonArray jsonArray = list2JsonArray(temp);
                rabbitMQUtil.sendToRabbitMQ(appId, appSecret, jsonArray, host, port, "getDetailExchange");
            } else {
                List<String> temp = getContinuityElement((i - 1) * size, idCount - 1, result);
                JsonArray jsonArray = list2JsonArray(temp);
                rabbitMQUtil.sendToRabbitMQ(appId, appSecret, jsonArray, host, port, "getDetailExchange");
            }
        }
    }


    private JsonArray list2JsonArray(List<String> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        JsonArray jsonArray = new JsonArray(list.size());
        for (String temp : list) {
            jsonArray.add(temp);
        }
        return jsonArray;
    }

    private void updateDbInTurn(String table, List<Integer> id2Handle, String column, Integer newValue) {
        int size = 800;
        int idCount = id2Handle.size();
        int times = (idCount / size) + 1;

        for (int i = 1; i <= times; i++) {
            if (i != times) {
                List<Integer> result = getContinuityElement((i - 1) * size, i * size - 1, id2Handle);
                cleanUserMapper.batchUpdateIntegerById(table, result, column, newValue);
            } else {
                List<Integer> result = getContinuityElement((i - 1) * size, idCount - 1, id2Handle);
                cleanUserMapper.batchUpdateIntegerById(table, result, column, newValue);
            }
        }
    }

    private void deleteInTurn(String table, List<Integer> id2Handle) {
        int size = 800;
        int idCount = id2Handle.size();
        int times = (idCount / size) + 1;

        for (int i = 1; i <= times; i++) {
            if (i != times) {
                List<Integer> result = getContinuityElement((i - 1) * size, i * size - 1, id2Handle);
                cleanUserMapper.batchDeleteById(table, result);
            } else {
                List<Integer> result = getContinuityElement((i - 1) * size, idCount - 1, id2Handle);
                cleanUserMapper.batchDeleteById(table, result);
            }
        }
    }

    private <T> List<T> getContinuityElement(int start, int end, List<T> target) {
        List<T> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(target.get(i));
        }
        return result;
    }

    @Override
    public Integer countOpenId(String table) {
        return cleanUserMapper.queryOpenIdNum(table);
    }

}