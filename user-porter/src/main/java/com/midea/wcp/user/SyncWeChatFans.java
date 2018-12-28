package com.midea.wcp.user;

import com.midea.wcp.user.service.CleanUser;
import com.midea.wcp.user.service.PullOpenId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制粉丝同步流程的类
 */
@RestController
public class SyncWeChatFans {
    private final PullOpenId pullOpenId;
    private final CleanUser cleanUser;

    @Autowired
    public SyncWeChatFans(PullOpenId pullOpenId, CleanUser cleanUser) {
        this.pullOpenId = pullOpenId;
        this.cleanUser = cleanUser;
    }

    @GetMapping("/sync")
    public void gkd(String appId, String appSecret, String host, Integer port, String appKey) {
        String tableNamePrefix = "mp_user_";
        String openidTablePrefix = "sync_open_id_";
        String originTable = tableNamePrefix + appId;
        String compareTable = openidTablePrefix + appId;

        try {
            //清除之前拉取的openid表
            cleanUser.cleanOpenIdTable(compareTable);

            //subscribe为null的置零
            cleanUser.setSubNull2Zero(originTable);

            //从微信拉取open id ，保存到数据库中
            int totalOpenId = pullOpenId.pullOpenId(appId, appSecret, host, port, appKey);
            if (totalOpenId == 0) {
                return;
            }

            //20s扫描一次，是否已经完成拉取openid并保存到数据库的工作
            while (!completeSaveOpenId2Db(totalOpenId, compareTable)) {
                Thread.sleep(20000L);
            }

            //原粉丝数据表中重复的open id删除
            cleanUser.cleanDuplicateOpenId(originTable);

            //从前取消关注的粉丝再次关注
            cleanUser.setSubZero2One(originTable, compareTable);

            //粉丝取消关注
            cleanUser.setSubOne2Zero(originTable, compareTable);

            //新增订阅的粉丝获取详情
            cleanUser.pullNullData(originTable, compareTable, appId, appSecret, host, port,appKey);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //todo 完善判断完成同步的机制
    private boolean completeSaveOpenId2Db(int totalOpenId, String table) {
        int saveNum = cleanUser.countOpenId(table);
        return saveNum == totalOpenId;
    }
}
