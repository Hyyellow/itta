package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.domain.entity.Timer;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@Builder
public class TimerDTO {
    private Integer id;

    private Integer scheduleId;

    private String year;

    private String month;

    private String week;

    private String day;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public TimerDTO() {
    }

    public Timer convertToTask() {
        TimerBaseDTOConvert timerBaseDTOConvert = new TimerBaseDTOConvert();
        Timer convert = timerBaseDTOConvert.doForward(this);
        return convert;
    }

    public TimerDTO convertFor(Timer timer) {
        TimerBaseDTOConvert timerBaseDTOConvert = new TimerBaseDTOConvert();
        TimerDTO convert = timerBaseDTOConvert.doBackward(timer);
        return convert;
    }

    private static class TimerBaseDTOConvert extends BaseDTOConvert<TimerDTO, Timer> {

        @Override
        protected Timer doForward(TimerDTO timerDTO) {
            Timer timer = new Timer();
            BeanUtils.copyProperties(timerDTO, timer);
            return timer;
        }

        @Override
        protected TimerDTO doBackward(Timer timer) {
            TimerDTO timerDTO = new TimerDTO();
            BeanUtils.copyProperties(timer, timerDTO);
            return timerDTO;
        }

        @Override
        public Timer apply(TimerDTO timerDTO) {
            return null;
        }
    }
}