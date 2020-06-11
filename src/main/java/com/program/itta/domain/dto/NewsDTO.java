package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.common.valid.Delete;
import com.program.itta.common.valid.Update;
import com.program.itta.domain.entity.News;
import com.program.itta.domain.entity.Tag;
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
@ApiModel(value = "NewsDTO", description = "消息DTO类")
public class NewsDTO implements Serializable {
    @ApiModelProperty(value = "消息id", example = "1")
    @NotNull(groups = {Update.class, Delete.class}, message = "消息id不可为空")
    private Integer id;

    @ApiModelProperty(value = "发送方id", example = "1", required = true)
    @NotNull(message = "发送方id不可为空")
    private Integer senderId;

    @ApiModelProperty(value = "被发送方id", example = "1", required = true)
    @NotNull(message = "被发送方id不可为空")
    private Integer recipientId;

    @ApiModelProperty(value = "用户标志", example = "1")
    private Boolean whetherUser;

    @ApiModelProperty(value = "消息内容", example = "todo")
    private String content;

    public News convertToNews() {
        NewsBaseDTOConvert newsBaseDTOConvert = new NewsBaseDTOConvert();
        News convert = newsBaseDTOConvert.doForward(this);
        return convert;
    }

    public NewsDTO convertFor(News news) {
        NewsBaseDTOConvert newsBaseDTOConvert = new NewsBaseDTOConvert();
        NewsDTO convert = newsBaseDTOConvert.doBackward(news);
        return convert;
    }

    private static class NewsBaseDTOConvert extends BaseDTOConvert<NewsDTO, News> {

        @Override
        protected News doForward(NewsDTO newsDTO) {
            News news = new News();
            BeanUtils.copyProperties(newsDTO, news);
            return news;
        }

        @Override
        protected NewsDTO doBackward(News news) {
            NewsDTO newsDTO = new NewsDTO();
            BeanUtils.copyProperties(news, newsDTO);
            return newsDTO;
        }

        @Override
        public News apply(NewsDTO newsDTO) {
            return null;
        }
    }

    @Tolerate
    public NewsDTO() {
    }


}