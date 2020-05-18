package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.domain.entity.Item;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "ItemDTO", description = "项目DTO类")
public class ItemDTO {
    @ApiModelProperty(value = "项目id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "项目名称", example = "学习小程序")
    private String name;

    @ApiModelProperty(value = "私密性", example = "1", notes = "0为私密——不可查看，1为公开——可查找到")
    private Integer actionScope;

    @ApiModelProperty(value = "创建人id", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "标志id", example = "1syufgf2dfg4sddtvxfgf45")
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
