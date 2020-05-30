package com.program.itta.controller;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.schedule.ScheduleAddFailException;
import com.program.itta.common.exception.schedule.ScheduleDelFailException;
import com.program.itta.common.exception.schedule.ScheduleUpdateFailException;
import com.program.itta.common.exception.timer.TimerAddFailException;
import com.program.itta.common.exception.timer.TimerDelFailException;
import com.program.itta.common.exception.timer.TimerUpdateFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.ScheduleDTO;
import com.program.itta.domain.dto.TimerDTO;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.domain.entity.Timer;
import com.program.itta.service.ScheduleService;
import com.program.itta.service.ScheduleTagService;
import com.program.itta.service.TimerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: itta
 * @description: 日程API
 * @author: Mr.Huang
 * @create: 2020-04-25 15:23
 **/
@Api(tags = "日程接口")
@RequestMapping("/schedule")
@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TimerService timerService;

    @Autowired
    private ScheduleTagService scheduleTagService;

    @Resource
    private JwtConfig jwtConfig;

    @ApiOperation(value = "查找日程", notes = "(查看该用户的所有日程安排)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该用户无添加日程")})
    @GetMapping("/selectSchedule")
    public HttpResult selectSchedule() {
        List<ScheduleDTO> scheduleList = scheduleService.selectByUserId();
        jwtConfig.removeThread();
        if (scheduleList != null && !scheduleList.isEmpty()) {
            return HttpResult.success(scheduleList);
        } else {
            return HttpResult.success("该用户无添加日程");
        }
    }

    @ApiOperation(value = "查找未完成日程", notes = "(查看该用户的今日未完成日程安排)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该用户无添加日程")})
    @GetMapping("/selectNotFinishSchedule")
    public HttpResult selectNotFinishSchedule() {
        List<ScheduleDTO> scheduleList = scheduleService.selectNotFinishSchedule();
        jwtConfig.removeThread();
        if (scheduleList != null && !scheduleList.isEmpty()) {
            return HttpResult.success(scheduleList);
        } else {
            return HttpResult.success("该用户无未完成日程");
        }
    }

    @ApiOperation(value = "查找完成日程", notes = "(查看该用户的今日已完成日程安排)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该用户无完成日程")})
    @GetMapping("/selectFinishSchedule")
    public HttpResult selectFinishSchedule() {
        List<ScheduleDTO> scheduleList = scheduleService.selectFinishSchedule();
        jwtConfig.removeThread();
        if (scheduleList != null && !scheduleList.isEmpty()) {
            return HttpResult.success(scheduleList);
        } else {
            return HttpResult.success("该用户无已完成日程");
        }
    }

    @ApiOperation(value = "添加日程", notes = "(添加该用户的日程安排)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 40003, message = "日程添加失败")})
    @PostMapping("/addSchedule")
    public HttpResult addSchedule(@ApiParam(name = "日程DTO类", value = "传入Json格式", required = true)
                                  @RequestBody @Valid ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTO.convertToSchedule();
        Boolean addSchedule = scheduleService.addSchedule(schedule);
        jwtConfig.removeThread();
        if (!addSchedule) {
            throw new ScheduleAddFailException("日程添加失败");
        }
        return HttpResult.success();
    }


    @ApiOperation(value = "编辑日程", notes = "(编辑此日程安排)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 40005, message = "日程更新失败")})
    @PutMapping("/updateSchedule")
    public HttpResult updateSchedule(@ApiParam(name = "日程DTO类", value = "传入Json格式", required = true)
                                     @RequestBody @Valid ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTO.convertToSchedule();
        Boolean updateSchedule = scheduleService.updateSchedule(schedule);
        jwtConfig.removeThread();
        if (!updateSchedule) {
            throw new ScheduleUpdateFailException("日程更新失败");
        }
        return HttpResult.success();
    }

    @ApiOperation(value = "改变日程是否完成状态", notes = "(编辑此日程安排)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 40005, message = "日程更新失败")})
    @PutMapping("/setScheduleStatus")
    public HttpResult setScheduleStatus(@ApiParam(name = "日程DTO类", value = "传入Json格式", required = true)
                                        @RequestBody @Valid ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTO.convertToSchedule();
        Boolean updateSchedule = scheduleService.setScheduleStatus(schedule);
        if (!updateSchedule) {
            throw new ScheduleUpdateFailException("日程更新失败");
        }
        return HttpResult.success();
    }

    @ApiOperation(value = "删除日程", notes = "(删除此日程安排)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 40004, message = "日程删除失败")})
    @DeleteMapping("/deleteSchedule")
    public HttpResult deleteSchedule(@ApiParam(name = "日程DTO类", value = "传入Json格式", required = true)
                                     @RequestBody @Valid ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTO.convertToSchedule();
        Boolean deleteSchedule = scheduleService.deleteSchedule(schedule);
        Boolean deleteScheduleTag = scheduleTagService.deleteAllScheduleTag(schedule);
        if (!(deleteSchedule && deleteScheduleTag)) {
            throw new ScheduleDelFailException("日程删除失败");
        }
        return HttpResult.success();
    }

    @ApiOperation(value = "添加重复", notes = "(添加该日程的重复消息提示)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 70001, message = "定时器添加失败")})
    @PostMapping("/addTimer")
    public HttpResult addTimer(@ApiParam(name = "定时器DTO类", value = "传入Json格式", required = true)
                               @RequestBody @Valid TimerDTO timerDTO) {
        Timer timer = timerDTO.convertToTimer();
        Boolean addTimer = timerService.addTimer(timer);
        if (!addTimer) {
            throw new TimerAddFailException("重复提醒定时器添加失败");
        }
        return HttpResult.success();
    }

    @ApiOperation(value = "添加工作日重复", notes = "(添加该日程的工作日重复消息提示)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 70001, message = "定时器添加失败")})
    @PostMapping("/addWorkDayTimer")
    public HttpResult addWorkDayTimer() {
        Boolean addTimer = timerService.addWorkDayTimer();
        if (!addTimer) {
            throw new TimerAddFailException("重复提醒定时器添加失败");
        }
        return HttpResult.success();
    }

    @ApiOperation(value = "编辑重复", notes = "(编辑该日程的重复消息提示)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 70002, message = "定时器更新失败")})
    @PutMapping("/updateTimer")
    public HttpResult updateTimer(@ApiParam(name = "定时器DTO类", value = "传入Json格式", required = true)
                                  @RequestBody @Valid TimerDTO timerDTO) {
        Timer timer = timerDTO.convertToTimer();
        Boolean updateTimer = timerService.updateTimer(timer);
        if (!updateTimer) {
            throw new TimerUpdateFailException("定时器更新失败");
        }
        return HttpResult.success();
    }

    @ApiOperation(value = "删除重复", notes = "(删除该日程的重复消息提示)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 70003, message = "定时器删除失败")})
    @DeleteMapping("/deleteTimer")
    public HttpResult deleteTimer(@ApiParam(name = "定时器DTO类", value = "传入Json格式", required = true)
                                  @RequestBody @Valid TimerDTO timerDTO) {
        Timer timer = timerDTO.convertToTimer();
        Boolean deleteTimer = timerService.deleteTimer(timer.getScheduleId());
        if (!deleteTimer) {
            throw new TimerDelFailException("定时器删除失败");
        }
        return HttpResult.success();
    }
}
