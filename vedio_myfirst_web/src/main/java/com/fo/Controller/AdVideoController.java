package com.fo.Controller;

import com.fo.pojo.Speaker;
import com.fo.pojo.Video;
import com.fo.service.SpeakerService;
import com.fo.service.VideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("video")
@Controller
public class AdVideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private SpeakerService speakerService;

    @RequestMapping("findAll")
    public String findAll() {
        List<Video> all = videoService.findAll();

        return "/WEB-INF/jsp/behind/login.jsp";
    }

    @RequestMapping("list")
    public ModelAndView videoList() {
        ModelAndView modelAndView = new ModelAndView();

        PageHelper.startPage(1,6);
        List<Video> all = videoService.findAll();
        PageInfo<Video> Page = new PageInfo<>(all);
        List<Speaker> all1 = speakerService.findAll();


        modelAndView.addObject("speakerList",all1);
        modelAndView.addObject("page",Page);
        modelAndView.setViewName("/WEB-INF/jsp/behind/videoList.jsp");
        return modelAndView;
    }



}
