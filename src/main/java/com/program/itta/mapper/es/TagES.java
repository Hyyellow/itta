package com.program.itta.mapper.es;

import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.Tag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TagES extends ElasticsearchRepository<Tag,Integer> {
}
