package com.program.itta.mapper.es;

import com.program.itta.domain.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemES extends ElasticsearchRepository<Item,Integer> {
}
