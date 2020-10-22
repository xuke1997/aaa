package com.fo.service.Impl;

import com.fo.dao.UserMapper;
import com.fo.pojo.User;
import com.fo.pojo.UserExample;
import com.fo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: vedio_myfirst
 * @Author: HW
 * @Time: 2020/10/19 21:15
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> login(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(user.getEmail());
        criteria.andPasswordEqualTo(user.getPassword());
        List<User> userList = userMapper.selectByExample(userExample);
        return userList;
    }

    @Override
    public User findOne(String id) {
       int i = Integer.parseInt(id);
       return userMapper.selectByPrimaryKey(i);
    }

    @Override
    public User selectUserByEmail(String email) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> userList = userMapper.selectByExample(userExample);
        User user = userList.get(0);
        return user;
    }

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKey(user);
    }
}
