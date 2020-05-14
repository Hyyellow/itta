package com.program.itta.controller;

import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.ScheduleDTO;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: itta
 * @description: 日程Api
 * @author: Mr.Huang
 * @create: 2020-04-25 15:23
 **/
@Api(tags = "日程接口")
@RequestMapping("/schedule")
@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "查找日程", notes = "(查看该用户的所有日程安排)")
    @GetMapping("/selectSchedule")
    public HttpResult selectSchedule() {
        List<Schedule> scheduleList = scheduleService.selectByUserId();
        if (scheduleList!=null && !scheduleList.isEmpty()){
            return HttpResult.success(scheduleList);
        }else{
            return HttpResult.success("该用户无添加日程");
        }
    }

    @ApiOperation(value = "添加日程", notes = "(添加该用户的日程安排)")
    @PostMapping("/addSchedule")
    public HttpResult addSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTO.convertToSchedule();
        Boolean addSchedule = scheduleService.addSchedule(schedule);
        if(!addSchedule){
            throw new RuntimeException("日程添加失败");
        }
        return HttpResult.success();
    }

    @ApiOperation(value = "编辑日程", notes = "(编辑此日程安排)")
    @PutMapping("/updateSchedule")
    public HttpResult updateSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleDTO.convertToSchedule();
        Boolean updateSchedule = scheduleService.updateSchedule(schedule);
        if (!updateSchedule){
            throw new RuntimeException("日程更新失败");
        }
        return HttpResult.success();
    }

    @ApiOperation(value = "删除日程", notes = "(删除此日程安排)")
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
