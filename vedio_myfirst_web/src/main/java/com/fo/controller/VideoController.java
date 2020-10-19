package com.fo.controller;

import com.fo.pojo.Video;
import com.fo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RequestMapping("VideoController")
@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping("findAll")
    public String findAll() {
        List<Video> all = videoService.findAll();

        return "/WEB-INF/jsp/before/index.jsp";
    }
}
