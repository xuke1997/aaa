package com.fo.service;

import com.fo.pojo.QueryVo;
import com.fo.pojo.Video;

import java.util.List;

public interface VideoService {
    List<Video> findAll();
    Video findOne(Integer id);
    void addVideo(Video video);
    void updateVideo(Video video);
    void deleteVideo(String id);
    List<Video> queryVideo(QueryVo queryVo);
}
