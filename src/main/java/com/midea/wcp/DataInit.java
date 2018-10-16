package com.midea.wcp;

import com.midea.wcp.site.data.*;
import com.midea.wcp.site.model.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInit implements InitializingBean {

    private final ManagerRepository managerRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Autowired
    public DataInit(ManagerRepository managerRepository,
                    RoleRepository roleRepository,
                    AccountRepository accountRepository,
                    UserRepository userRepository,
                    TagRepository tagRepository) {
        this.managerRepository = managerRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void afterPropertiesSet() {
        String[] managerNames = new String[]{
                "张三", "李四", "王五", "赵六",
                "张三三", "李四四", "王五五", "赵六六",
                "张三四", "李四五", "王五六", "赵六七",
        };
        String[] uids = new String[] {
            "zhangsan", "lisi", "wangwu", "zhaoliu",
            "zhangss", "liss", "wangww", "zhaoll",
            "zhangss1", "lisw", "wangwl", "zhaolq"
        };
        String[] roleNames = new String[]{"集团超级管理员", "事业部超级管理员", "服务号超级管理员", "普通管理员"};
        String[] accountNames = new String[]{"美的云服务", "美的米管家", "美云智数-美信云", "美云智数-智造云"};
        String[] userNames = new String[]{"用户1", "用户2", "用户3", "用户4"};
        String[] tagNames = new String[]{"标签1", "标签2", "标签3", "标签4"};

        List<Assistant> assistants = new ArrayList<>();
        for (int i = 1; i <= managerNames.length; i++) {
            Assistant assistant = new Assistant();
            assistant.setId(i);
            assistant.setUid(uids[i - 1]);
            assistant.setName(managerNames[i - 1]);
            assistant.setGender(GenderType.values()[i % 2]);
            assistants.add(assistant);
        }

        List<Role> roles = new ArrayList<>();
        for (int i = 1; i <= roleNames.length; i++) {
            Role role = new Role();
            role.setId(i);
            role.setName(roleNames[i - 1]);
            role.setType(Role.Type.values()[i % 2]);
            roles.add(role);
        }

        List<Account> accounts = new ArrayList<>();
        for (int i = 1; i <= accountNames.length; i++) {
            Account account = new Account();
            account.setId(i);
            account.setName(accountNames[i - 1]);
            account.setType(Account.Type.values()[i % 2]);
            accounts.add(account);
        }

        List<User> users = new ArrayList<>();
        for (int i = 1; i <= userNames.length; i++) {
            User user = new User();
            user.setId(i);
            user.setName(userNames[i - 1]);
            user.setGender(GenderType.values()[i % 2]);
            users.add(user);
        }

        List<User.Tag> tags = new ArrayList<>();
        for (int i = 1; i <= tagNames.length; i++) {
            User.Tag tag = new User.Tag();
            tag.setId(i);
            tag.setName(tagNames[i - 1]);
            tags.add(tag);
        }

        // 保存数据
        for (Assistant assistant : assistants) {
            assistant.setRole(roles.get(new Random().nextInt(4)));
        }

        for (Role role : roles) {
            role.setAccounts(accounts);
        }

        for (Account account : accounts) {
            account.setUsers(users);
        }

        for (User user : users) {
            user.setTags(tags);
        }

        tagRepository.saveAll(tags);
        userRepository.saveAll(users);
        accountRepository.saveAll(accounts);
        roleRepository.saveAll(roles);
        managerRepository.saveAll(assistants);
    }
}
