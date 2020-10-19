package com.fo.service;

import com.fo.pojo.Admin;

import java.util.List;

public interface AdminService {
    Admin checkAdmin(String username, String password);
    List<Admin> findAll();
}
