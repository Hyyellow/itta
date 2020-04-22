package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.domain.entity.Item;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.beans.BeanUtils;

/**
 * @program: itta
 * @description: DTO item类
 * @author: Mr.Huang
 * @create: 2020-04-05 22:26
 **/
@Data
@Builder
public class ItemDTO {
    private Integer id;

    private String name;

    private String serialNumber;

    private String taskPrefix;

    private String color;

    private String actionScope;

    private String groupName;

    private String description;

    private Integer leaderId;

    private String markId;

    @Tolerate
    public ItemDTO() {
    }

    public Item convertToItem() {
        ItemBaseDTOConvert userDTOConvert = new ItemBaseDTOConvert();
        Item convert = userDTOConvert.doForward(this);
        return convert;
    }

    public ItemDTO convertFor(Item item) {
        ItemBaseDTOConvert userDTOConvert = new ItemBaseDTOConvert();
        ItemDTO convert = userDTOConvert.doBackward(item);
        return convert;
    }

    private static class ItemBaseDTOConvert extends BaseDTOConvert<ItemDTO, Item> {
        @Override
        protected Item doForward(ItemDTO itemDTO) {
            Item item = new Item();
            BeanUtils.copyProperties(itemDTO, item);
            return item;
        }

        @Override
        protected ItemDTO doBackward(Item item) {
            ItemDTO itemDTO = new ItemDTO();
            BeanUtils.copyProperties(item, itemDTO);
            return itemDTO;
        }

        @Override
        public Item apply(ItemDTO itemDTO) {
            return null;
        }
    }
}
