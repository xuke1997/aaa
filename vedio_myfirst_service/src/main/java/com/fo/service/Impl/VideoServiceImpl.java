package com.fo.service.Impl;

import com.fo.dao.VideoMapper;
import com.fo.pojo.Video;
import com.fo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll() {
        return videoMapper.selectByExample(null);
    }

    @Override
    public Video findOne() {
        return null;
    }
}
