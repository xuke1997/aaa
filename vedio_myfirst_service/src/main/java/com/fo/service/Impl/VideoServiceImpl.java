package com.fo.service.Impl;

import com.fo.dao.VideoMapper;
import com.fo.pojo.QueryVo;
import com.fo.pojo.Video;
import com.fo.pojo.VideoExample;
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
    public Video findOne(Integer id) {
        return videoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addVideo(Video video) {
        videoMapper.insert(video) ;
    }

    @Override
    public void updateVideo(Video video) {
        videoMapper.updateByPrimaryKey(video);
    }

    @Override
    public void deleteVideo(String id) {
        int i = Integer.parseInt(id);
        videoMapper.deleteByPrimaryKey(i);
    }

    @Override
    public List<Video> queryVideo(QueryVo queryVo) {
        VideoExample videoExample = new VideoExample();
        VideoExample.Criteria criteria = videoExample.createCriteria();
        if (null != queryVo.getTitle() && queryVo.getTitle() !="") {
            criteria.andTitleLike("%"+queryVo.getTitle() +"%");
        }
        if (null != queryVo.getCourseId() && queryVo.getCourseId() !="") {
            int courseId = Integer.parseInt(queryVo.getCourseId());
            criteria.andCourseIdEqualTo(courseId);
        }
        if (null != queryVo.getSpeakerId() && queryVo.getSpeakerId() !="") {
            int speakerId = Integer.parseInt(queryVo.getSpeakerId());
            criteria.andSpeakerIdEqualTo(speakerId);
        }
        List<Video> videos = videoMapper.selectByExample(videoExample);

        return videos;
    }
}
