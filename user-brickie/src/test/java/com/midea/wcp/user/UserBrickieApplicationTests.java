package com.midea.wcp.user;

import com.midea.wcp.user.jpa.model.SyncDetail;
/*import com.midea.wcp.user.jpa.repository.SyncDetailDao;*/
import com.midea.wcp.user.mybatis.mapper.CleanUserMapper;
import com.midea.wcp.user.mybatis.model.MpUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBrickieApplicationTests {

    @Autowired
    private CleanUserMapper cleanUserMapper;

    /*@Autowired
    private SyncDetailDao syncDetailDao;*/


    @Test
    public void contextLoads() {
    }

    @Test
    public void test1() {
//        cleanUser.clean();
    }

    @Test
    public void test2(){
        List<SyncDetail> list = new ArrayList<>();

        SyncDetail detail1 = new SyncDetail();
        detail1.setAppId("xswl");
        detail1.setOpenId("xswl");
        detail1.setCreatedAt(new Date());
        detail1.setUpdatedAt(new Date());
        list.add(detail1);

        SyncDetail detail2 = new SyncDetail();
        detail1.setAppId("gkd");
        detail1.setOpenId("gkd");
        detail1.setCreatedAt(new Date());
        detail1.setUpdatedAt(new Date());
        list.add(detail2);
        /*syncDetailDao.saveAll(list);*/
    }

    @Test
    public void test3(){
        List<MpUser> list =new ArrayList<>();
        MpUser mpUser = new MpUser();
        mpUser.setAppId("xswl");
        mpUser.setOpenid("xswl");
        mpUser.setCancelSubscribeTime(new Date());
        mpUser.setCreatedAt(new Date());
        mpUser.setUpdatedAt(new Date());
        mpUser.setUnionid("xswl");
        mpUser.setEmail("xswl");
        mpUser.setSubscribe(1);
        mpUser.setGroupId(555555);
        mpUser.setContactStatus(55555);
        list.add(mpUser);
        list.add(mpUser);
        cleanUserMapper.batchSaveMpUser(list,"mp_user_wxd19c5a897bbaaaae");
    }

    @Test
    public void test4(){
        List<String> list = new ArrayList<>();
        list.add("gkd");
        list.add("kkp");
        cleanUserMapper.batchSaveOpenId(list,"sync_open_id_xxx");
    }
}
