package com.program.itta.controller;

import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.ScheduleDTO;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: itta
 * @description: API Schedule类
 * @author: Mr.Huang
 * @create: 2020-04-25 15:23
 **/
@RequestMapping("/schedule")
@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/selectSchedule")
    public HttpResult selectSchedule() {
        List<Schedule> scheduleList = scheduleService.selectByUserId();
        if (scheduleList!=null && !scheduleList.isEmpty()){
            return HttpResult.success(scheduleList);
        }else{
            return HttpResult.success("该用户无添加日程");
        }
    }

    @PostMapping("/addSchedule")
    public HttpResult addSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTO.convertToSchedule();
        Boolean addSchedule = scheduleService.addSchedule(schedule);
        if(!addSchedule){
            throw new RuntimeException("日程添加失败");
        }
        return HttpResult.success();
    }

    @PutMapping("/updateSchedule")
    public HttpResult updateSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleDTO.convertToSchedule();
        Boolean updateSchedule = scheduleService.updateSchedule(schedule);
        if (!updateSchedule){
            throw new RuntimeException("日程更新失败");
        }
        return HttpResult.success();
    }

    @DeleteMapping("/deleteSchedule")
    public HttpResult deleteSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleDTO.convertToSchedule();
        Boolean deleteSchedule = scheduleService.deleteSchedule(schedule);
        if (!deleteSchedule){
            throw new RuntimeException("日程删除失败");
        }
        return HttpResult.success();
    }
}
