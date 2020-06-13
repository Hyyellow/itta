package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.common.valid.Delete;
import com.program.itta.common.valid.Update;
import com.program.itta.domain.entity.Timer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@ApiModel(value = "TimerDTO", description = "定时器DTO类")
public class TimerDTO implements Serializable {
    @ApiModelProperty(value = "定时器id", example = "1")
    @NotNull(groups = {Update.class, Delete.class}, message = "定时器id不可为空")
    private Integer id;

    @ApiModelProperty(value = "日程id", example = "1", required = true)
    @NotNull(message = "日程id不可为空")
    private Integer scheduleId;

    @ApiModelProperty(value = "年，其中0为不设置", example = "1", required = true)
    @NotNull(message = "年不可为空")
    private String year;

    @ApiModelProperty(value = "月，其中0为不设置", example = "1", required = true)
    @NotNull(message = "月不可为空")
    private String month;

    @ApiModelProperty(value = "星期，其中0为不设置", example = "1", required = true)
    @NotNull(message = "星期不可为空")
    private String week;

    @ApiModelProperty(value = "日，其中0为不设置", example = "1", required = true)
    @NotNull(message = "日不可为空")
    private String day;

    @Tolerate
    public TimerDTO() {
    }

    public Timer convertToTimer() {
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