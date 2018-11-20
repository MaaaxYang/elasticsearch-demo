package com.atom.elasticsearch.esdemo.controller;

import com.atom.elasticsearch.esdemo.IdWorker;
import com.atom.elasticsearch.esdemo.dao.MockRepository;
import com.atom.elasticsearch.esdemo.dao.ProductRepository;
import com.atom.elasticsearch.esdemo.entity.MockEntity;
import com.atom.elasticsearch.esdemo.entity.ProductOrderEntity;
import com.google.gson.Gson;
import org.apache.http.impl.client.HttpClientBuilder;
import org.elasticsearch.common.rounding.DateTimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: es-demo
 * @description:
 * @author: Maxxx.Yg
 * @create: 2018-11-01 14:10
 **/
@RestController
@RequestMapping("es")
public class MockController {

    @Autowired
    private MockRepository mockRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * 新增
     * @return
     */
    @RequestMapping("/add")
    public String add(){

        List<ProductOrderEntity> list = new ArrayList<>(400);

        IdWorker idWorker = new IdWorker(1);

        for(int i = 0;i<100;i++){
            ProductOrderEntity productOrderEntity = new ProductOrderEntity();
            productOrderEntity.setDeleted(false);
            productOrderEntity.setCreateTime(new Date());
            productOrderEntity.setProductName("乐事薯片");
            productOrderEntity.setProductType(10000);
            productOrderEntity.setOrderNo(String.valueOf(idWorker.nextId()));
            productOrderEntity.setId(10000L+i);
            productOrderEntity.setRemark("乐事薯片 泡菜口味");
            list.add(productOrderEntity);
        }

        for(int i = 100;i<200;i++){
            ProductOrderEntity productOrderEntity = new ProductOrderEntity();
            productOrderEntity.setDeleted(false);
            productOrderEntity.setCreateTime(new Date());
            productOrderEntity.setProductName("乐事薯片");
            productOrderEntity.setProductType(10000);
            productOrderEntity.setOrderNo(String.valueOf(idWorker.nextId()));
            productOrderEntity.setId(10000L+i);
            productOrderEntity.setRemark("乐事薯片 黄瓜口味");
            list.add(productOrderEntity);

        }

        for(int i = 200;i<300;i++){
            ProductOrderEntity productOrderEntity = new ProductOrderEntity();
            productOrderEntity.setDeleted(false);
            productOrderEntity.setCreateTime(new Date());
            productOrderEntity.setProductName("可口可乐");
            productOrderEntity.setProductType(20000);

            productOrderEntity.setOrderNo(String.valueOf(idWorker.nextId()));
            productOrderEntity.setId(10000L+i);
            productOrderEntity.setRemark("可口可乐 肥宅快乐水");
            list.add(productOrderEntity);

        }

        for(int i = 300;i<400;i++){
            ProductOrderEntity productOrderEntity = new ProductOrderEntity();
            productOrderEntity.setDeleted(false);
            productOrderEntity.setCreateTime(new Date());
            productOrderEntity.setProductName("肥宅快乐套装");
            productOrderEntity.setProductType(30000);

            productOrderEntity.setOrderNo(String.valueOf(idWorker.nextId()));
            productOrderEntity.setId(10000L+i);
            productOrderEntity.setRemark("肥宅快乐水（可乐）+ 薯片");
            list.add(productOrderEntity);

        }
        productRepository.saveAll(list);
        return "success";
    }


    /**
     * 删除
     * @return
     */
    @RequestMapping("delete")
    public String delete() {
        mockRepository.deleteAll();
        return "success";
    }

    /**
     * 局部更新
     * @return
     */
    @RequestMapping("update")
    public String update() {
        MockEntity mock = mockRepository.queryMockEntityByUserName("Mock Data");
        mock.setUserName("哈哈");
        mockRepository.save(mock);
        return "success";
    }
    /**
     * 查询
     * @return
     */
    @RequestMapping("query")
    public MockEntity query() {
        MockEntity mock = mockRepository.queryMockEntityByUserName("Mock Data");
        Gson gson = new Gson();
        System.err.println(gson.toJson(mock));
        return mock;

    }


    public void test() throws IOException {

        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
        ClientHttpRequest clientHttpRequest = factory.createRequest(null,null);
        ClientHttpResponse response = clientHttpRequest.execute();

    }

}
