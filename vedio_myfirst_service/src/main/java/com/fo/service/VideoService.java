package com.fo.service;

import com.fo.pojo.Video;

import java.util.List;

public interface VideoService {
    List<Video> findAll();
    Video findOne();
}
