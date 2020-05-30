package com.program.itta.service;

import com.program.itta.domain.entity.Schedule;
import com.program.itta.domain.entity.ScheduleTag;
import com.program.itta.domain.entity.Task;

import java.util.List;

public interface ScheduleTagService {
    // 添加日程标签中间关系
    Boolean addScheduleTag(Integer scheduleId, String content);

    // 查找该日程的所有标签
    List<Integer> selectByScheduleId(Integer scheduleId);

    // 删除该日程的所有标签
    Boolean deleteAllScheduleTag(Schedule schedule);

    // 删除该日程标签
    Boolean deleteScheduleTag(ScheduleTag scheduleTag);
}
