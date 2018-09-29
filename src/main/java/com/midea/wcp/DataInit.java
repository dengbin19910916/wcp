package com.midea.wcp;

import com.midea.wcp.data.AccountRepository;
import com.midea.wcp.data.ManagerRepository;
import com.midea.wcp.data.RoleRepository;
import com.midea.wcp.domain.Account;
import com.midea.wcp.domain.Manager;
import com.midea.wcp.domain.Role;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInit implements InitializingBean {

    private final ManagerRepository managerRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    public DataInit(ManagerRepository managerRepository,
                    RoleRepository roleRepository,
                    AccountRepository accountRepository) {
        this.managerRepository = managerRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] managerNames = new String[]{"张三", "李四", "王五", "赵六"};
        String[] roleNames = new String[]{"集团超级管理员", "事业部超级管理员", "服务号超级管理员", "普通管理员"};
        String[] accountNames = new String[]{"美的云服务", "美的米管家", "美云智数-美信云", "美云智数-智造云"};

        List<Manager> managers = new ArrayList<>();
        for (int i = 1; i <= managerNames.length; i++) {
            Manager manager = new Manager();
            manager.setId(i);
            manager.setName(managerNames[i - 1]);
            manager.setName(managerNames[i - 1]);
            managers.add(manager);
        }

        List<Role> roles = new ArrayList<>();
        for (int i = 1; i <= roleNames.length; i++) {
            Role role = new Role();
            role.setId(i);
            role.setName(roleNames[i - 1]);
            roles.add(role);
        }

        List<Account> accounts = new ArrayList<>();
        for (int i = 1; i <= accountNames.length; i++) {
            Account account = new Account();
            account.setId(i);
            account.setName(accountNames[i - 1]);
            accounts.add(account);
        }

        for (Manager manager : managers) {
            manager.setRoles(roles);
        }

        for (Role role : roles) {
            role.setAccounts(accounts);
//            role.setManagers(managers);
        }

        for (Account account : accounts) {
//            account.setRoles(roles);
        }

        accountRepository.saveAll(accounts);
        roleRepository.saveAll(roles);
        managerRepository.saveAll(managers);
    }
}
