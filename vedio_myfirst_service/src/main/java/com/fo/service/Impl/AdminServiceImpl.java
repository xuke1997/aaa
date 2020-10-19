package com.fo.service.Impl;

import com.fo.dao.AdminMapper;
import com.fo.pojo.Admin;
import com.fo.pojo.AdminExample;
import com.fo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;


    @Override
    public Admin checkAdmin(String username, String password) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
       criteria.andUsernameEqualTo(username);
       criteria.andPasswordEqualTo(password);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (admins.size() == 0) {
            return null;
        } else {
            return admins.get(0);
        }



    }
}
