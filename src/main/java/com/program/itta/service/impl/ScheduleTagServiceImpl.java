package com.program.itta.service.impl;

import java.awt.Desktop.Action;

import com.program.itta.domain.entity.Schedule;
import com.program.itta.domain.entity.ScheduleTag;
import com.program.itta.domain.entity.Tag;
import com.program.itta.domain.entity.TaskTag;
import com.program.itta.mapper.ScheduleTagMapper;
import com.program.itta.mapper.TagMapper;
import com.program.itta.service.ScheduleService;
import com.program.itta.service.ScheduleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: itta
 * @description: 日程标签实现类
 * @author: Mr.Huang
 * @create: 2020-05-25 16:58
 **/
@Service
public class ScheduleTagServiceImpl implements ScheduleTagService {

    @Autowired
    private ScheduleTagMapper scheduleTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Boolean addScheduleTag(Integer scheduleId, String content) {
        Tag tag = tagMapper.selectByContent(content);
        ScheduleTag scheduleTag = ScheduleTag.builder()
                .scheduleId(scheduleId)
                .tagId(tag.getId())
                .build();
        Boolean judgeScheduleTag = judgeScheduleTag(scheduleTag);
        if (judgeScheduleTag) {
            return true;
        }
        int insert = scheduleTagMapper.insert(scheduleTag);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> selectByScheduleId(Integer scheduleId) {
        List<ScheduleTag> scheduleTags = scheduleTagMapper.selectByScheduleId(scheduleId);
        List<Integer> tagIds = scheduleTags.stream()
                .map(ScheduleTag -> ScheduleTag.getTagId())
                .collect(Collectors.toList());
        if (!tagIds.isEmpty()) {
            return tagIds;
        }
        return null;
    }

    @Override
    public Boolean deleteScheduleTag(Schedule schedule) {
        List<ScheduleTag> scheduleTagList = scheduleTagMapper.selectByScheduleId(schedule.getId());
        for (ScheduleTag scheduleTag : scheduleTagList) {
            int delete = scheduleTagMapper.deleteByPrimaryKey(scheduleTag.getId());
            if (delete == 0){
                return false;
            }
        }
        return true;
    }

    private Boolean judgeScheduleTag(ScheduleTag scheduleTag) {
        ScheduleTag selectByScheduleTag = scheduleTagMapper.selectByScheduleTag(scheduleTag);
        return selectByScheduleTag != null;
    }
}
