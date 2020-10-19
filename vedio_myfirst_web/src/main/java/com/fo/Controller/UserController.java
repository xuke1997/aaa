package com.fo.Controller;

import com.fo.dao.UserMapper;
import com.fo.pojo.User;
import com.fo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

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
    UserService userService;

    public UserService getUser() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @ResponseBody
    @RequestMapping("loginUser")
    public Object login(User user, HttpServletRequest request) {
        List<User> userList = userService.login(user);
        HashMap<Object, Object> map = new HashMap<>();
        for (User user1 : userList) {
            System.out.println(user1);
            if (user1 != null) {
                if (user.getPassword().equals(user.getPassword())) {
                    System.out.println(user.getEmail());
                    request.getSession().setAttribute("loginEmail", user.getEmail());
                    System.out.println(user.getPassword());
                    request.getSession().setAttribute("userPassword", user.getPassword());
                    map.put("success", true);
                } else {
                    map.put("msg", "密码错误");
                }
            } else {
                map.put("msg", "邮箱错误，登录失败");
            }
        }
        return map;

    }
}
