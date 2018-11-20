package com.atom.elasticsearch.esdemo.dao;

import com.atom.elasticsearch.esdemo.entity.MockEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @program: es-demo
 * @description:
 * @author: Maxxx.Yg
 * @create: 2018-11-01 14:08
 **/
@Component
public interface MockRepository extends ElasticsearchRepository<MockEntity,Long> {

    MockEntity queryMockEntityByUserName(String userName);

}
