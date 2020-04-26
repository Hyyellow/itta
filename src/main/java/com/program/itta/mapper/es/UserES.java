package com.program.itta.mapper.es;

import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserES extends ElasticsearchRepository<User,Integer> {
}
