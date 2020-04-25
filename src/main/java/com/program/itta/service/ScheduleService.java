package com.program.itta.service;

import com.program.itta.domain.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    // 添加日程
    Boolean addSchedule(Schedule schedule);
    // 删除日程
    Boolean deleteSchedule(Schedule schedule);
    // 更新日程
    Boolean updateSchedule(Schedule schedule);
    // 查找用户下的所有日程
    List<Schedule> selectByUserId();
}
