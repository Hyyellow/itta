package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.domain.entity.Tag;
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
    private Integer id;

    private Integer userId;

    private String name;

    private String place;

    private Date startTime;

    private Date endTime;

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
