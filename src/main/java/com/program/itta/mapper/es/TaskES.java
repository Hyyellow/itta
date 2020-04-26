package com.program.itta.mapper.es;

import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.Task;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TaskES extends ElasticsearchRepository<Task,Integer> {
}
