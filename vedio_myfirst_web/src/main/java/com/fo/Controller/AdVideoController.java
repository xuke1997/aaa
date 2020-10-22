package com.fo.Controller;

import com.fo.pojo.*;
import com.fo.service.CourseService;
import com.fo.service.SpeakerService;
import com.fo.service.SubjectService;
import com.fo.service.VideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("video")
@Controller
public class AdVideoController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private SpeakerService speakerService;
    @Autowired
    private CourseService courseService;

    @RequestMapping("findAll")
    public String findAll() {
        List<Video> all = videoService.findAll();

        return "/WEB-INF/jsp/behind/login.jsp";
    }

    /**
     * 展示video数据到前端页面
     * @param page
     * @return
     */
    @RequestMapping("list")
    public ModelAndView videoList(String page , QueryVo queryVo2 , String count, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        QueryVo queryVo = new QueryVo();
        /**
         * 获取传入页面的video数量
         */


        if (!(queryVo2.getTitle() == queryVo2.getSpeakerId() && queryVo2.getSpeakerId() ==queryVo2.getCourseId())) {
            session.setAttribute("queryVo",queryVo2);
            queryVo=queryVo2;
        } else {
            if (session.getAttribute("queryVo")!=null) {
                QueryVo queryVo1 = (QueryVo) session.getAttribute("queryVo");
                queryVo=queryVo1;
            }else {
                queryVo=queryVo2;
            }
        }

        if ("1".equals(count)) {
            if (session.getAttribute("queryVo")!=null){
                session.removeAttribute("queryVo");
                queryVo=queryVo2;
            }
        }
        List<Video> videos = videoService.queryVideo(queryVo);
        int size = videos.size();
        //判断是否是初次转入此页面
        if (page==null) {
            page="1";
        }
        //对video进行分页
        int i = Integer.parseInt(page);
        PageHelper.startPage(i,8);
        List<Video> all = videoService.queryVideo(queryVo);
        PageInfo<Video> Page = new PageInfo<>(all);

        //获取speaker集合对象
        List<Speaker> all1 = speakerService.findAll();

        //获取course集合对象
        List<Course> allCourse = courseService.findAllCourse();

        //将所有获取参数传入前端
        modelAndView.addObject("size",size);
        modelAndView.addObject("courseList",allCourse);
        modelAndView.addObject("speakerList",all1);
        modelAndView.addObject("page",Page);
        modelAndView.setViewName("/WEB-INF/jsp/behind/videoList.jsp");
        return modelAndView;
    }

    /**
     * 从前端页面接受一个video对象
     * @return
     */
    @RequestMapping("addVideo")
    public ModelAndView addVideo() {
        ModelAndView modelAndView = new ModelAndView();

        //获取speaker集合对象
        List<Speaker> all1 = speakerService.findAll();

        //获取course集合对象
        List<Course> allCourse = courseService.findAllCourse();

        //进行向前端传参和转跳
        modelAndView.addObject("courseList",allCourse);
        modelAndView.addObject("speakerList",all1);
        modelAndView.setViewName("/WEB-INF/jsp/behind/addVideo.jsp");



        return modelAndView;
    }

    /**
     * 添加一个video对象到数据库
     * @param video
     * @return
     */
    @RequestMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(Video video) {
        ModelAndView modelAndView = new ModelAndView();

        //判断当前传入对象是否在数据库中存在
        Video one = videoService.findOne(video.getId());
        if (one == null) {
            videoService.addVideo(video);
        } else {
            videoService.updateVideo(video);
        }

        modelAndView.setViewName("redirect:/video/list");
        return modelAndView;
    }

    @RequestMapping("edit")
    public ModelAndView editVideo(String id) {
        ModelAndView modelAndView = new ModelAndView();
        int i = Integer.parseInt(id);
        Video one = videoService.findOne(i);

        //获取speaker集合对象
        List<Speaker> all1 = speakerService.findAll();

        //获取course集合对象
        List<Course> allCourse = courseService.findAllCourse();

        //进行向前端传参和转跳
        modelAndView.addObject("courseList",allCourse);
        modelAndView.addObject("speakerList",all1);
        modelAndView.addObject("video",one);
        modelAndView.setViewName("/WEB-INF/jsp/behind/addVideo.jsp");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("videoDel")
    public String videoDelVideo(String id) {

        videoService.deleteVideo(id);
        return "success";
    }

    @RequestMapping("delBatchVideos")
    public ModelAndView delBatchVideos(Integer[]  ids) {
        ModelAndView modelAndView = new ModelAndView();

        for (Integer id : ids) {
            String s = String.valueOf(id);
            videoService.deleteVideo(s);
        }

        modelAndView.setViewName("redirect:/video/list");

        return modelAndView;
    }

    @RequestMapping("showVideo")
    public ModelAndView showVideo(String videoId,String subjectName) {
        ModelAndView modelAndView = new ModelAndView();

        int parseInt = Integer.parseInt(videoId);
        Video one = videoService.findOne(parseInt);

        Integer speakerId = one.getSpeakerId();
        Speaker speaker = speakerService.findSpeaker(speakerId);

        List<Subject> allSubject = subjectService.findAllSubject();
        int subjectId = 0;
        for (Subject subject : allSubject) {
            if (subject.getSubjectName().equals(subjectName)) {
                subjectId = subject.getId();
            }
        }
        List<Course> courses = courseService.checkCourse(subjectId);

        Integer courseId = one.getCourseId();
        String s1 = String.valueOf(courseId);
        Course course = courseService.findCourse(s1);

        QueryVo queryVo = new QueryVo();
        String s = String.valueOf(courseId);
        queryVo.setCourseId(s);
        List<Video> videos = videoService.queryVideo(queryVo);
        course.setVideos(videos);

        modelAndView.addObject("course",course);
        modelAndView.addObject("speaker",speaker);
        modelAndView.addObject("subjectName",subjectName);
        modelAndView.addObject("video",one);
        modelAndView.setViewName("/WEB-INF/jsp/before/section.jsp");
        return modelAndView;
    }
}
