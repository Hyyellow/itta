package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.domain.entity.Task;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * @program: itta
 * @description: DTO task类
 * @author: Mr.Huang
 * @create: 2020-04-14 15:06
 **/
@Data
@Builder
@ApiModel(value = "TaskDTO", description = "任务DTO类")
public class TaskDTO {
    @ApiModelProperty(value = "任务id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "任务名称", example = "写操作系统作业")
    private String name;

    @ApiModelProperty(value = "项目id", example = "1")
    private Integer itemId;

    @ApiModelProperty(value = "创建人id", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "父任务id", example = "1")
    private Integer superId;

    @ApiModelProperty(value = "任务标志id", example = "1")
    private String markId;

    @ApiModelProperty(value = "任务状态", example = "1", notes = "0：未开始，1：进行中，2：完成")
    private Integer status;

    @ApiModelProperty(value = "开始时间", example = "2020-04-25 15:28")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", example = "2020-04-25 17:28")
    private Date endTime;

    @ApiModelProperty(value = "完成时间", example = "2020-04-25 16:28")
    private Date completionTime;

    @ApiModelProperty(value = "紧急程度", example = "1",notes = "3：非常紧急；2：紧急；1：一般")
    private Integer priority;

    @ApiModelProperty(value = "该任务下的成员列表", example = "{1,2,3}")
    private List<Integer> userIdList;


    @Tolerate
    public TaskDTO() {
    }

    public Task convertToTask() {
        TaskBaseDTOConvert taskDTOConvert = new TaskBaseDTOConvert();
        Task convert = taskDTOConvert.doForward(this);
        return convert;
    }

    public TaskDTO convertFor(Task task) {
        TaskBaseDTOConvert taskDTOConvert = new TaskBaseDTOConvert();
        TaskDTO convert = taskDTOConvert.doBackward(task);
        return convert;
    }

    private static class TaskBaseDTOConvert extends BaseDTOConvert<TaskDTO, Task> {

        @Override
        protected Task doForward(TaskDTO taskDTO) {
            Task task = new Task();
            BeanUtils.copyProperties(taskDTO, task);
            return task;
        }

        @Override
        protected TaskDTO doBackward(Task task) {
            TaskDTO taskDTO = new TaskDTO();
            BeanUtils.copyProperties(task, taskDTO);
            return taskDTO;
        }

        @Override
        public Task apply(TaskDTO taskDTO) {
            return null;
        }
    }
}
