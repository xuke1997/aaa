package com.fo.service;
import com.fo.pojo.Speaker;
import java.util.List;

public interface SpeakerService {
     List<Speaker> findAll();
     Speaker findSpeaker(Integer id);
     void updateSpeaker(Speaker speaker);
     void addSpeaker(Speaker speaker);
     void deleteSpeaker(String id);
}
