package com.program.itta.domain.dto;

import com.program.itta.common.convert.DTOConvert;

import com.program.itta.domain.entity.Tag;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @program: itta
 * @description: DTO tag类
 * @author: Mr. Boyle
 * @create: 2020-04-08 16:53
 **/
@Data
public class TagDTO {
    private Integer id;

    @NotBlank
    private String name;

    private String description;

    private  Integer userId;

    public Tag convertToTag() {
        TagDTO.TagDTOConvert userDTOConvert = new TagDTOConvert();
        Tag convert = userDTOConvert.doForward(this);
        return convert;
    }

    public TagDTO convertFor(Tag tag) {
        TagDTO.TagDTOConvert userDTOConvert = new TagDTOConvert();
        TagDTO convert = userDTOConvert.doBackward(tag);
        return convert;
    }

    private static class TagDTOConvert extends DTOConvert<TagDTO, Tag> {

        @Override
        protected Tag doForward(TagDTO tagDTO) {
            Tag tag = new Tag();
            BeanUtils.copyProperties(tagDTO, tag);
            return tag;
        }

        @Override
        protected TagDTO doBackward(Tag tag) {
            TagDTO tagDTO = new TagDTO();
            BeanUtils.copyProperties(tag, tagDTO);
            return tagDTO;
        }

        @Override
        public Tag apply(TagDTO tagDTO) {
            return null;
        }
    }
}
