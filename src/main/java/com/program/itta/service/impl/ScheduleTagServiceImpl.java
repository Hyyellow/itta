package com.program.itta.service.impl;

import java.awt.Desktop.Action;

import java.awt.Desktop.Action;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.domain.entity.*;
import com.program.itta.mapper.ScheduleMapper;
import com.program.itta.mapper.ScheduleTagMapper;
import com.program.itta.mapper.TagMapper;
import com.program.itta.service.ScheduleService;
import com.program.itta.service.ScheduleTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTagServiceImpl.class);

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private ScheduleTagMapper scheduleTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Resource
    private JwtConfig jwtConfig;

    @Override
    public Boolean addScheduleTag(Integer scheduleId, String content) {
        Tag tag = tagMapper.selectByContent(content);
        ScheduleTag scheduleTag = ScheduleTag.builder()
                .scheduleId(scheduleId)
                .tagId(tag.getId())
                .build();
        logger.info("日程" + scheduleId + "增加标签" + tag);
        return addJudge(scheduleTag);
    }

    @Override
    public Boolean addScheduleTag(Schedule schedule, String content) {
        Tag tag = tagMapper.selectByContent(content);
        Integer userId = jwtConfig.getUserId();
        List<Schedule> scheduleList = scheduleMapper.selectByUserId(userId);
        Schedule schedule1 = selectScheduleByList(scheduleList, schedule);
        ScheduleTag scheduleTag = ScheduleTag.builder()
                .scheduleId(schedule1.getId())
                .tagId(tag.getId())
                .build();
        logger.info("日程" + schedule.getId() + "增加标签" + tag);
        return addJudge(scheduleTag);
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
    public Boolean deleteAllScheduleTag(Schedule schedule) {
        List<ScheduleTag> scheduleTagList = scheduleTagMapper.selectByScheduleId(schedule.getId());
        for (ScheduleTag scheduleTag : scheduleTagList) {
            int delete = scheduleTagMapper.deleteByPrimaryKey(scheduleTag.getId());
            if (delete == 0) {
                return false;
            }
        }
        logger.info("删除日程" + schedule.getId() + "的所有标签");
        return true;
    }

    @Override
    public Boolean deleteScheduleTag(ScheduleTag scheduleTag) {
        ScheduleTag tag = scheduleTagMapper.selectByScheduleTag(scheduleTag);
        int delete = scheduleTagMapper.deleteByPrimaryKey(tag.getId());
        if (delete != 0) {
            logger.info("删除日程" + tag.getScheduleId() + "的标签" + tag);
            return true;
        }
        return false;
    }


    private Boolean judgeScheduleTag(ScheduleTag scheduleTag) {
        ScheduleTag selectByScheduleTag = scheduleTagMapper.selectByScheduleTag(scheduleTag);
        return selectByScheduleTag != null;
    }

    private Schedule selectScheduleByList(List<Schedule> scheduleList, Schedule schedule) {
        for (Schedule schedule1 : scheduleList) {
            if (schedule1.getName().equals(schedule.getName())) {
                return schedule1;
            }
        }
        return null;
    }

    private Boolean addJudge(ScheduleTag scheduleTag) {
        Boolean judgeScheduleTag = judgeScheduleTag(scheduleTag);
        if (judgeScheduleTag) {
            return true;
        }
        int insert = scheduleTagMapper.insert(scheduleTag);
        return insert != 0;
    }
}
