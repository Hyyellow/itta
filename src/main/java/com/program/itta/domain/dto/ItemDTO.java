package com.program.itta.domain.dto;

import com.program.itta.common.convert.DTOConvert;
import com.program.itta.domain.entity.Item;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * @program: itta
 * @description: DTO item类
 * @author: Mr.Huang
 * @create: 2020-04-05 22:26
 **/
@Data
public class ItemDTO {
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String serialNumber;

    @NotBlank
    private String taskPrefix;

    private String color;
    @NotBlank
    private String actionScope;

    private String groupName;

    private String description;

    @NotBlank
    private String markId;

    private Integer userId;

    public Item convertToItem(){
        ItemDTO.ItemDTOConvert userDTOConvert = new ItemDTO.ItemDTOConvert();
        Item convert = userDTOConvert.doForward(this);
        return convert;
    }

    public ItemDTO convertFor(Item item){
        ItemDTO.ItemDTOConvert userDTOConvert = new ItemDTO.ItemDTOConvert();
        ItemDTO convert = userDTOConvert.doBackward(item);
        return convert;
    }

    private static class ItemDTOConvert extends DTOConvert<ItemDTO, Item> {
        @Override
        protected Item doForward(ItemDTO itemDTO) {
            Item item = new Item();
            BeanUtils.copyProperties(itemDTO,item);
            return item;
        }

        @Override
        protected ItemDTO doBackward(Item item) {
            ItemDTO itemDTO = new ItemDTO();
            BeanUtils.copyProperties(item,itemDTO);
            return itemDTO;
        }

        @Override
        public Item apply(ItemDTO itemDTO) {
            return null;
        }
    }
}
