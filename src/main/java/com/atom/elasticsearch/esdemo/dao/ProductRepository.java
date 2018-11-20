package com.atom.elasticsearch.esdemo.dao;

import com.atom.elasticsearch.esdemo.entity.ProductOrderEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @program: es-demo
 * @description:
 * @author: Maxxx.Yg
 * @create: 2018-11-08 14:56
 **/
public interface ProductRepository extends ElasticsearchRepository<ProductOrderEntity,Long> {
}
