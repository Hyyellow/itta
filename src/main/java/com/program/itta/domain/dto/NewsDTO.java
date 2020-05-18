package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.domain.entity.News;
import com.program.itta.domain.entity.Tag;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@Builder
public class NewsDTO {
    private Integer id;

    private Integer senderId;

    private Integer recipientId;

    private Boolean isUser;

    private String content;

    private Date createTime;

    private Date updateTime;

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