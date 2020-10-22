package com.fo.Controller;

import com.fo.dao.UserMapper;
import com.fo.pojo.User;
import com.fo.service.UserService;
import com.fo.utils.ImageCut;
import com.fo.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @ProjectName: vedio_myfirst
 * @Author: HW
 * @Time: 2020/10/19 20:09
 * @Description:
 */

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping("loginUser")
    public String login(User user, HttpServletRequest request) {
        List<User> userList = userService.login(user);
        for (User user1 : userList) {
            if (user1 != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userAccount", user.getEmail());
                return "success";
            } else {
                return "failed";
            }
        }
        return "failed";
    }

    /**
     * 退出登录
     */
    @RequestMapping("loginOut")
    @ResponseBody
    public String loginOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("userAccount");
        return "success";
    }

    /**
     * 退出登录
     */
    @RequestMapping("loginOut2")
    public String loginOut2(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("userAccount");
        return "redirect:/index.jsp";
    }

    /**
     * 根据email判断用户是否存在
     */
    @RequestMapping("validateEmail")
    @ResponseBody
    public String validateEmail(String email) {
        User user = userService.selectUserByEmail(email);
        if (null == user) {
            return "success";
        }
        return "hasUser";
    }

    /**
     * 注册
     */
    @RequestMapping("insertUser")
    @ResponseBody
    public String insertUser(User user, HttpServletRequest request) {
        user.setCreatetime(new Date());
        userService.insertUser(user);
        HttpSession session = request.getSession();
        session.setAttribute("userAccount", user.getEmail());
        return "success";
    }

    /**
     * 个人中心
     */
    @RequestMapping("showMyProfile")
    public String showMyProfile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userAccount = (String) session.getAttribute("userAccount");
        User user = userService.selectUserByEmail(userAccount);
        session.setAttribute("user", user);
        return "/WEB-INF/jsp/before/my_profile.jsp";
    }

    /**
     * 忘记密码
     */
    @RequestMapping("forgetPassword")
    public String forgetPassword() {
        return "/WEB-INF/jsp/before/forget_password.jsp";
    }

    /**
     * 发送邮件
     */
    @RequestMapping("sendEmail")
    public String sendEmail(String email, HttpServletRequest request) {
        if ("success".equals(validateEmail(email))) {
            return "hasNoUser";
        }
        String code = MailUtils.getValidateCode(6);
        boolean sendMail = MailUtils.sendMail(email, "随机验证码是:" + code, "无需回复");
        if (sendMail) {
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
            session.setAttribute("code", code);
            return "success";
        }
        return "sendMail";
    }

    /**
     * 判断邮箱验证码
     */
    @RequestMapping("validateEmailCode")
    public String validateEmailCode(String email, String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionEmail = (String) session.getAttribute("email");
        String sessionCode = (String) session.getAttribute("code");
        if (sessionEmail.equals(email) && sessionCode.equals(code)) {
            return "/WEB-INF/jsp/before/reset_password.jsp";
        }
        return "redirect:/user/forgetPassword";
    }

    /**
     * 更改密码
     */
    @RequestMapping("resetPassword")
    public String resetPassword(String email, String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("email");
        session.removeAttribute("code");
        User user = userService.selectUserByEmail(email);
        if (null != user) {
            user.setPassword(password);
            userService.updateUser(user);
            session.setAttribute("userAccount", user.getEmail());
        }
        return "redirect:/index.jsp";
    }

    /**
     * 更改个人资料
     */
    @RequestMapping("changeProfile")
    public String changeProfile() {
        return "/WEB-INF/jsp/before/change_profile.jsp";
    }

    /**
     * 修改用户资料
     */
    @RequestMapping("updateUser")
    public String updateUser(String nickname, String sex, String birthday, String address, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        user.setNickname(nickname);
        user.setSex(sex);
        user.setBirthday(birthday);
        user.setAddress(address);
        userService.updateUser(user);
        return "redirect:/user/showMyProfile";
    }

    /**
     * 密码安全
     */
    @RequestMapping("passwordSafe")
    public String passwordSafe() {
        return "/WEB-INF/jsp/before/password_safe.jsp";
    }

    /**
     * 验证密码
     */
    @RequestMapping("validatePassword")
    @ResponseBody
    public String validatePassword(String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (password.equals(user.getPassword())) {
            return "success";
        }
        return "failed";
    }

    /**
     * 更改密码
     */
    @RequestMapping("updatePassword")
    public String updatePassword(String newPassword, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        user.setPassword(newPassword);
        userService.updateUser(user);
        return "redirect:/user/showMyProfile";
    }

    /**
     * 更改头像
     */
    @RequestMapping("changeAvatar")
    public String changeAvatar() {
        return "/WEB-INF/jsp/before/change_avatar.jsp";
    }

    /**
     * 上传图片
     */
    @RequestMapping("upLoadImage")
    public String upLoadImage(@RequestParam("image_file") MultipartFile imageFile, String x1, String x2, String y1, String y2, HttpServletRequest request) throws IOException {
        String path = "E:\\360Downloads\\Software\\apache-tomcat-9.0.34\\webapps\\vedio\\";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String filename = imageFile.getOriginalFilename();
        filename = filename.substring(filename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + filename;
        imageFile.transferTo(new File(path, filename));

        int x1Int = (int) Double.parseDouble(x1);
        int x2Int = (int) Double.parseDouble(x2);
        int y1Int = (int) Double.parseDouble(y1);
        int y2Int = (int) Double.parseDouble(y2);
        new ImageCut().cutImage(path + "/" + filename, x1Int, y1Int, x2Int - x1Int, y2Int - y1Int);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        user.setImgurl(filename);
        userService.updateUser(user);

        return "redirect:/user/showMyProfile";
    }
}
