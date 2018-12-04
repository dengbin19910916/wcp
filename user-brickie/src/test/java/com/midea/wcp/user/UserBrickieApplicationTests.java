package com.midea.wcp.user;

import com.midea.wcp.user.mybatis.mapper.CleanUserMapper;
import com.midea.wcp.user.service.CleanUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBrickieApplicationTests {

    @Autowired
    private CleanUser cleanUser;

    @Autowired
    private CleanUserMapper cleanUserMapper;


    @Test
    public void contextLoads() {
    }

    @Test
    public void test1() {
//        cleanUser.clean();
    }

    @Test
    public void test2() {
        String table = "sync_open_id_xxx";
        List<Integer> ids = new ArrayList<>();
        ids.add(3396);

        String id = "id";
        Integer newValue = 999999;
        System.out.println(cleanUserMapper.batchUpdateIntegerById(table, ids, id, newValue));
    }
    @Test
    public void test3(){
        String table = "sync_open_id_xxx";
        List<Integer> ids = new ArrayList<>();
        ids.add(99999);

        String id = "openid";
        String newValue = "xswl";
        System.out.println(cleanUserMapper.batchUpdateStringById(table, ids, id, newValue));
    }

}
