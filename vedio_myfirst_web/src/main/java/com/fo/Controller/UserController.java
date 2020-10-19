package com.fo.Controller;

import com.fo.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: vedio_myfirst
 * @Author: HW
 * @Time: 2020/10/19 20:09
 * @Description:
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;
    public UserMapper getUser() {
        return userMapper;
    }
}
