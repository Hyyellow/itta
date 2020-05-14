package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.domain.entity.Tag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.util.Date;

/**
 * @program: itta
 * @description: DTO Schedule类
 * @author: Mr.Huang
 * @create: 2020-04-25 15:28
 **/
@Data
@Builder
public class ScheduleDTO {
    @ApiModelProperty(value = "日程id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "日程名称", example = "学习操作系统")
    private String name;

    @ApiModelProperty(value = "地点", example = "图书馆")
    private String place;

    @ApiModelProperty(value = "开始时间", example = "2020-04-25 15:28")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", example = "2020-04-25 17:28")
    private Date endTime;

    @ApiModelProperty(value = "完成时间", example = "2020-04-25 16:28")
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
