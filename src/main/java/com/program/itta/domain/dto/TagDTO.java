package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.domain.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * @program: itta
 * @description: DTO tag类
 * @author: Mr. Boyle
 * @create: 2020-04-08 16:53
 **/
@Data
@Builder
@ApiModel(value = "TagDTO", description = "标签DTO类")
public class TagDTO {
    @ApiModelProperty(value = "标签id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "标签内容", example = "后台")
    private String content;

    public Tag convertToTag() {
        TagBaseDTOConvert userDTOConvert = new TagBaseDTOConvert();
        Tag convert = userDTOConvert.doForward(this);
        return convert;
    }

    public TagDTO convertFor(Tag tag) {
        TagBaseDTOConvert userDTOConvert = new TagBaseDTOConvert();
        TagDTO convert = userDTOConvert.doBackward(tag);
        return convert;
    }

    private static class TagBaseDTOConvert extends BaseDTOConvert<TagDTO, Tag> {

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

    @Tolerate
    public TagDTO() {
    }
}
