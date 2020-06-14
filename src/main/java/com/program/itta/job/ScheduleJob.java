package com.program.itta.job;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.domain.entity.Timer;
import com.program.itta.service.NewsService;
import com.program.itta.service.ScheduleService;
import com.program.itta.service.TimerService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @program: itta
 * @description: 定时日程
 * @author: Mr.Huang
 * @create: 2020-05-20 11:28
 **/
@Component
public class ScheduleJob extends QuartzJobBean {

    @Autowired
    private TimerService timerService;

    @Autowired
    private ScheduleService scheduleService;

    @Resource
    private JwtConfig jwtConfig;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取数据库中的时间列表
        List<Timer> timerList = timerService.selectAll();
        // 进行时间的判断，并添加消息
        judgeTime(timerList);
    }

    private void judgeTime(List<Timer> timerList) {
        for (Timer timer : timerList) {
            Boolean insertFlag = null;
            Schedule schedule = scheduleService.selectByPrimaryKey(timer.getScheduleId());
            Calendar startCalendar = assignmentCalendar(schedule.getStartTime());
            Calendar endCalendar = assignmentCalendar(schedule.getEndTime());
            Calendar nowCalendar = assignmentCalendar(new Date());
            if (timer.getWeek() == 0) {
                Boolean judgeYear = judgeYear(timer, startCalendar, nowCalendar);
                Boolean judgeMonth = judgeMonth(timer, nowCalendar);
                Boolean judgeDay = judgeDay(timer, nowCalendar);
                insertFlag = judgeDay && judgeMonth && judgeYear;
            } else {
                Boolean judgeWeek = judgeWeek(timer, nowCalendar);
                insertFlag = judgeWeek;
            }
            if (insertFlag) {
                Schedule newSchedule = getNewSchedule(schedule, startCalendar, endCalendar, nowCalendar);
                scheduleService.addSchedule(newSchedule);
                jwtConfig.removeThread();
            }
        }
    }

    private Schedule getNewSchedule(Schedule schedule, Calendar startCalendar, Calendar endCalendar, Calendar nowCalendar) {
        int day = nowCalendar.get(Calendar.DAY_OF_MONTH);
        startCalendar.set(Calendar.DAY_OF_MONTH, day);
        endCalendar.set(Calendar.DAY_OF_MONTH, day);
        Date startTime = startCalendar.getTime();
        Date endTime = endCalendar.getTime();
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        return schedule;
    }

    private Boolean judgeYear(Timer timer, Calendar startCalendar, Calendar nowCalendar) {
        if (0 == timer.getYear()) {
            return true;
        }
        int startYear = startCalendar.get(Calendar.YEAR);
        int nowYear = nowCalendar.get(Calendar.YEAR);
        if (startYear - nowYear == timer.getYear()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean judgeMonth(Timer timer, Calendar nowCalendar) {
        if (0 == timer.getMonth()) {
            return true;
        }
        int nowMonth = nowCalendar.get(Calendar.MONTH);
        if (nowMonth + 1 == timer.getMonth()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean judgeDay(Timer timer, Calendar nowCalendar) {
        if (0 == timer.getDay()) {
            return true;
        }
        int nowDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
        if (nowDay == timer.getDay()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean judgeWeek(Timer timer, Calendar nowCalendar) {
        if (0 == timer.getWeek()) {
            return true;
        }
        //一周第一天是否为星期天
        boolean isFirstSunday = (nowCalendar.getFirstDayOfWeek() == Calendar.SUNDAY);
        int nowWeek = nowCalendar.get(Calendar.DAY_OF_WEEK);
        if (isFirstSunday) {
            nowWeek = nowWeek - 1;
            if (nowWeek == 0) {
                nowWeek = 7;
            }
        }
        if (nowWeek == timer.getWeek()) {
            return true;
        } else {
            return false;
        }
    }

    private Calendar assignmentCalendar(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar;
    }
}
