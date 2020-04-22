package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.domain.entity.Task;
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
public class TaskDTO {
    private Integer id;

    private String name;

    private Integer itemId;

    private Integer leaderId;

    private Integer securityLevel;

    private Integer status;

    private Date startTime;

    private Date endTime;

    private Date completionTime;

    private Integer priority;

    private Integer label;

    private String description;

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
