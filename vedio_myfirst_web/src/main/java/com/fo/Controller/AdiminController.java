package com.fo.Controller;

import com.fo.pojo.Admin;
import com.fo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("admin")
@Controller
public class AdiminController {

    @Autowired
   private AdminService adminService;

    @ResponseBody
    @RequestMapping("login")
    public String adminLogin(Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        System.out.println("password = " + password);
        Admin admin1 = adminService.checkAdmin(username, password);
        if (admin1==null) {
            return "123";
        } else {
            return "success";
        }

    }


}
