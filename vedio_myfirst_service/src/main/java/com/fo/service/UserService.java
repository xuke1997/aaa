package com.fo.service;

import com.fo.pojo.User;

import java.util.List;

/**
 * @ProjectName: vedio_myfirst
 * @Author: HW
 * @Time: 2020/10/19 21:15
 * @Description:
 */
public interface UserService {
    public List<User> login(User user);
    public User findOne(String id);
    public User selectUserByEmail(String email);
    void insertUser(User user);
    void updateUser(User user);
}
