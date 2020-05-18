package com.program.itta.common.util.fineGrainedPermissions;

import com.program.itta.domain.entity.Item;
import org.springframework.stereotype.Component;

/**
 * @program: itta
 * @description: 项目负责人权限
 * @author: Mr.Huang
 * @create: 2020-04-24 16:00
 **/
@Component
public class ItemPermissionsUtil {

    public Boolean DeletePermissions(Integer userId, Item item) {
        if (item.getUserId().equals(userId)) {
            return true;
        }
        return false;
    }
}
