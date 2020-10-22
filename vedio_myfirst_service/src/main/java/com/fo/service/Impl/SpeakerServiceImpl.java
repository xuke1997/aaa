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

    @Override
    public Speaker findSpeaker(Integer id) {
        return speakerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateSpeaker(Speaker speaker) {
        speakerMapper.updateByPrimaryKey(speaker);
    }

    @Override
    public void addSpeaker(Speaker speaker) {
        speakerMapper.insert(speaker);
    }

    @Override
    public void deleteSpeaker(String id) {
        int parseInt = Integer.parseInt(id);
        speakerMapper.deleteByPrimaryKey(parseInt);
    }
}
