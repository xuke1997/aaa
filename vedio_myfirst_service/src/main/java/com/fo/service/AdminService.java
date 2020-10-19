package com.fo.service;

import com.fo.pojo.Admin;

public interface AdminService {
    Admin checkAdmin(String username, String password);
}
