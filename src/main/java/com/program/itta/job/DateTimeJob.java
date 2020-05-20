package com.program.itta.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @program: itta
 * @description: QuartzTest
 * @author: Mr.Huang
 * @create: 2020-05-20 11:28
 **/
@Component
public class DateTimeJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取JobDetail中关联的数据
        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
        System.out.println("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" + msg);
        // 获取数据库中的时间列表
        // 进行时间的判断，并添加消息
        // 时间的判断——年月日；每个星期几
    }
}
