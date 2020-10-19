package com.fo.service.Impl;

import com.fo.dao.SpeakerMapper;
import com.fo.pojo.Speaker;
import com.fo.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakerServiceImpl implements SpeakerService {
    @Autowired
    private SpeakerMapper speakerMapper;


    @Override
    public List<Speaker> findAll() {
        return speakerMapper.selectByExample(null);
    }
}
