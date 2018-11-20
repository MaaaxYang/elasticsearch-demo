package com.atom.elasticsearch.esdemo.services;

import com.atom.elasticsearch.esdemo.dao.MockRepository;
import com.atom.elasticsearch.esdemo.entity.MockEntity;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

/**
 * @program: es-demo
 * @description:
 * @author: Maxxx.Yg
 * @create: 2018-11-08 10:20
 **/
@Service
public class StandardSearch {

    @Autowired
    private MockRepository repository;

    @Autowired
    private ElasticsearchTemplate template;


    public void test(){
        MockEntity mockEntity = repository.findById(1L).orElse(null);
        MockEntity mockEntity1 = repository.save(mockEntity);
        repository.deleteById(1L);


        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withPageable(Pageable.unpaged())
                .withSort(SortBuilders.fieldSort("createTime"))
                .build()
                ;
        repository.search(searchQuery);
    }
}
