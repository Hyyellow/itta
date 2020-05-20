package com.program.itta.job;

import com.program.itta.domain.entity.Timer;
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
 * @description: QuartzTest
 * @author: Mr.Huang
 * @create: 2020-05-20 11:28
 **/
@Component
public class DateTimeJob extends QuartzJobBean {

    @Autowired
    private TimerService timerService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取JobDetail中关联的数据
        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
        System.out.println("current time :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" + msg);
        // 获取数据库中的时间列表
        List<Timer> timerList = timerService.selectAll();
        // 进行时间的判断，并添加消息

        // 时间的判断——年月日；每个星期几
    }

    private void judgeTime(List<Timer> timerList) {
        for (Timer timer : timerList) {
            Calendar createCalendar = assignmentCalendar(timer.getCreateTime());
            Calendar nowCalendar = assignmentCalendar(new Date());
            Boolean judgeYear = judgeYear(timer, createCalendar, nowCalendar);
            Boolean judgeMonth = judgeMonth(timer, createCalendar, nowCalendar);
            Boolean judgeDay = judgeDay(timer, createCalendar, nowCalendar);
        }
    }

    private Boolean judgeYear(Timer timer, Calendar createCalendar, Calendar nowCalendar) {
        if (0 == timer.getYear()) {
            return true;
        }
        int createYear = createCalendar.get(Calendar.YEAR);
        int nowYear = nowCalendar.get(Calendar.YEAR);
        if (createYear - nowYear == timer.getYear()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean judgeMonth(Timer timer, Calendar createCalendar, Calendar nowCalendar){
        if (0 == timer.getMonth()){
            return true;
        }
        int createMonth = createCalendar.get(Calendar.MONTH);
        int nowMonth = nowCalendar.get(Calendar.MONTH);
        if (createMonth - nowMonth == timer.getMonth()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean judgeDay(Timer timer, Calendar createCalendar, Calendar nowCalendar){
        if (0 == timer.getDay()){
            return true;
        }
        int createDay = createCalendar.get(Calendar.DAY_OF_MONTH);
        int nowDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
        if (createDay - nowDay == timer.getDay()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean judgeWeek(Timer timer, Calendar createCalendar, Calendar nowCalendar){
        if (0 == timer.getWeek()){
            return true;
        }
        int createWeek = createCalendar.get(Calendar.DAY_OF_WEEK);
        int nowWeek = nowCalendar.get(Calendar.DAY_OF_WEEK);
        if (createWeek - nowWeek == timer.getWeek()) {
            return true;
        } else {
            return false;
        }
    }

    private Calendar assignmentCalendar(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar;
    }
}
