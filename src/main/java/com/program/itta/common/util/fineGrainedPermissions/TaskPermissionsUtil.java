package com.program.itta.common.util.fineGrainedPermissions;

import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.Task;
import com.program.itta.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Access;

/**
 * @program: itta
 * @description: 项目负责人权限
 * @author: Mr.Huang
 * @create: 2020-04-24 16:00
 **/
@Component
public class TaskPermissionsUtil {

    @Autowired
    private ItemService itemService;

    public Boolean AddPermissions(Integer userId, Task task) {
        Item item = itemService.selectById(task.getItemId());
        if (userId.equals(item.getUserId())) {
            return true;
        }
        return false;
    }

    public Boolean UpdatePermissions(Integer userId,Task task){
        if (userId.equals(task.getUserId())){
            return true;
        }
        return false;
    }
}
