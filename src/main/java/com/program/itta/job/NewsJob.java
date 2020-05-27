package com.program.itta.job;

import java.io.DataOutputStream;

import com.program.itta.domain.dto.ScheduleDTO;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.service.NewsService;
import com.program.itta.service.ScheduleService;
import com.program.itta.service.TaskService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: itta
 * @description: 定时消息
 * @author: Mr.Huang
 * @create: 2020-05-26 16:19
 **/
@Component
public class NewsJob extends QuartzJobBean {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private NewsService newsService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<ScheduleDTO> scheduleList = scheduleService.selectAll();
        for (ScheduleDTO schedule : scheduleList) {
            Boolean judgeTime = judgeTime(schedule);
            if (judgeTime){
                newsService.addScheduleNews(schedule);
            }
        }
    }

    private Boolean judgeTime(ScheduleDTO schedule) {
        Calendar calendar = assignmentCalendar(new Date());
        Calendar startCalendar = assignmentCalendar(schedule.getStartTime());
        int year = calendar.get(Calendar.YEAR);
        int startYear = startCalendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int startMonth = startCalendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        if ((year == startYear) && (month == startMonth) && (day == startDay)) {
            return true;
        }
        return false;
    }

    private Calendar assignmentCalendar(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar;
    }
}
