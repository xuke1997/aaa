package com.fo.controller;

import com.fo.pojo.Video;
import com.fo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("VideoController")
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping("findAll")
    public List<Video> findAll() {
        List<Video> all = videoService.findAll();

        return all;
    }
}
