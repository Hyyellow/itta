package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.common.valid.Delete;
import com.program.itta.common.valid.Update;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.domain.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: itta
 * @description: DTO Schedule类
 * @author: Mr.Huang
 * @create: 2020-04-25 15:28
 **/
@Data
@Builder
@ApiModel(value = "ScheduleDTO", description = "日程DTO类")
public class ScheduleDTO implements Serializable {
    @ApiModelProperty(value = "日程id", example = "1")
    @NotNull(groups = {Update.class, Delete.class}, message = "日程id不可为空")
    private Integer id;

    @ApiModelProperty(value = "用户id", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "日程名称", example = "学习操作系统", required = true)
    @NotNull(message = "日程名称不可为空")
    private String name;

    @ApiModelProperty(value = "地点", example = "图书馆", required = true)
    @NotNull(message = "地点不可为空")
    private String place;

    @ApiModelProperty(value = "紧急程度", example = "1", notes = "3：非常紧急；2：紧急；1：一般", required = true)
    @NotNull(message = "紧急程度不可为空")
    private Integer priority;

    @ApiModelProperty(value = "完成标志", example = "0")
    private Boolean whetherFinish;

    @ApiModelProperty(value = "开始时间", example = "2020-04-25 15:28")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", example = "2020-04-25 17:28")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    @ApiModelProperty(value = "完成时间", example = "2020-04-25 16:28")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date completionTime;

    public Schedule convertToSchedule() {
        ScheduleDTOConvert scheduleDTOConvert = new ScheduleDTOConvert();
        Schedule convert = scheduleDTOConvert.doForward(this);
        return convert;
    }

    public ScheduleDTO convertFor(Schedule schedule) {
        ScheduleDTOConvert scheduleDTOConvert = new ScheduleDTOConvert();
        ScheduleDTO convert = scheduleDTOConvert.doBackward(schedule);
        return convert;
    }

    private static class ScheduleDTOConvert extends BaseDTOConvert<ScheduleDTO, Schedule> {
        @Override
        protected Schedule doForward(ScheduleDTO scheduleDTO) {
            Schedule schedule = new Schedule();
            BeanUtils.copyProperties(scheduleDTO, schedule);
            return schedule;
        }

        @Override
        protected ScheduleDTO doBackward(Schedule schedule) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDTO);
            return scheduleDTO;
        }

        @Override
        public Schedule apply(ScheduleDTO scheduleDTO) {
            return null;
        }
    }

    @Tolerate
    public ScheduleDTO() {
    }
}
