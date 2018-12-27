package com.midea.wcp.user;

import com.midea.wcp.user.mybatis.mapper.CleanUserMapper;
import com.midea.wcp.user.mybatis.mapper.TestMapper;
import com.midea.wcp.user.mybatis.model.MybatisTest;
import com.midea.wcp.user.service.PullOpenId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserPorterControllerApplicationTests {

    @Autowired
    PullOpenId pullOpenId;
    @Autowired
    TestMapper testMapper;
    @Autowired
    private CleanUserMapper cleanUserMapper;

    @Test
    public void test1() {
//        pullOpenId.pullOpenId("wxd19c5a897bbaaaae", "6dfa31490e52b36c6f4043e286a0846b", "weixincs.midea.com", 8090);
    }

    @Test
    public void test2() {
        MybatisTest mybatisTest = testMapper.selectTest(51);
        System.out.println(mybatisTest);
    }

    @Test
    public void test3() {
        String table = "sync_open_id_xxx";
        List<Integer> ids = new ArrayList<>();
        ids.add(3396);

        String id = "id";
        Integer newValue = 999999;
        System.out.println(cleanUserMapper.batchUpdateIntegerById(table, ids, id, newValue));
    }

    @Test
    public void test4() {
        String table = "sync_open_id_xxx";
        List<Integer> ids = new ArrayList<>();
        ids.add(99999);

        String id = "openid";
        String newValue = "xswl";
        System.out.println(cleanUserMapper.batchUpdateStringById(table, ids, id, newValue));
    }

    @Test
    public void test5() {
        List<Integer> result = cleanUserMapper.selectSubZero2One("mp_user_wxd19c5a897bbaaaae", "sync_open_id_xxx");
        System.out.println(result);
    }

    @Test
    public void test6() {
        cleanUserMapper.deleteAll("aaaa");
    }

    @Test
    public void test7() {
        System.out.println(cleanUserMapper.queryOpenIdNum("sync_open_id_xxx"));
    }
}
