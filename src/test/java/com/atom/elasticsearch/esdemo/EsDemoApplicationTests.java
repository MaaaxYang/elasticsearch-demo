package com.atom.elasticsearch.esdemo;

import com.atom.elasticsearch.esdemo.entity.ProductOrderEntity;
import com.google.gson.Gson;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsDemoApplicationTests {

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void letsGo() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("productType",20000))
                .withPageable(PageRequest.of(0,20))  // page = 0 代表第一页
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)) // 排序基础使用
                .build();
        List<ProductOrderEntity> list = template.queryForList(searchQuery,ProductOrderEntity.class);
        Gson gson = new Gson();

        int count = 1;
        for (ProductOrderEntity entity:list){
            System.out.println((count++) +" - "+gson.toJson(entity));
        }
    }

    @Test
    public void letsGo2() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery()
                                .filter(QueryBuilders.termQuery("productType",10000))
                                .must(QueryBuilders.matchQuery("remark","黄瓜"))
                )
                .withPageable(PageRequest.of(0,20))  // page = 0 代表第一页
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)) // 排序基础使用
                .build();
        List<ProductOrderEntity> list = template.queryForList(searchQuery,ProductOrderEntity.class);
        Gson gson = new Gson();

        int count = 1;
        for (ProductOrderEntity entity:list){
            System.out.println((count++) +" - "+gson.toJson(entity));
        }
    }

    @Test
    public void letsGo3() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery()
                                .filter(QueryBuilders.termQuery("productType",10000))
                                .filter(QueryBuilders.rangeQuery("id").gte(10010).lt(10020)) // 范围查询
                                .filter(QueryBuilders.matchQuery("orderNo","290206123796019134"))  // 由于分词原因 orderNo 不能模糊查询
                )
                .withPageable(PageRequest.of(0,20))  // page = 0 代表第一页
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)) // 排序基础使用
                .build();
        List<ProductOrderEntity> list = template.queryForList(searchQuery,ProductOrderEntity.class);
        Gson gson = new Gson();

        int count = 1;
        for (ProductOrderEntity entity:list){
            System.out.println((count++) +" - "+gson.toJson(entity));
        }
    }

    @Test
    public void letsGo4() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.matchQuery("remark","乐事薯片 黄瓜 可乐 肥宅")
                                //.operator(Operator.AND) // 必须上面的关键词都匹配
                                .minimumShouldMatch("50%") // 只要匹配其中2个 就符合条件
                )
                .withPageable(PageRequest.of(0,20))  // page = 0 代表第一页
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)) // 排序基础使用
                .build();
        List<ProductOrderEntity> list = template.queryForList(searchQuery,ProductOrderEntity.class);
        Gson gson = new Gson();

        int count = 1;
        for (ProductOrderEntity entity:list){
            System.out.println((count++) +" - "+gson.toJson(entity));
        }
    }

    @Test
    public void letsGo5() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(
                       QueryBuilders.boolQuery()                                            // 组合查询
                        .must(QueryBuilders.matchQuery("remark","薯片"))          // 必须包括关键词 薯片
                        .mustNot(QueryBuilders.matchQuery("remark","泡菜"))        // 必须不包括 泡菜
                        .should(QueryBuilders.matchQuery("remark","乐"))          // 尽可能包括 可乐
                               .should(QueryBuilders.matchQuery("remark","黄瓜"))
                               .should(QueryBuilders.matchQuery("remark","水"))
                        .minimumShouldMatch(1)                                                                  // should 至少匹配一项，也可以用百分比形式表示
                )
                .withPageable(PageRequest.of(0,20))  // page = 0 代表第一页
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)) // 排序基础使用
                .build();
        List<ProductOrderEntity> list = template.queryForList(searchQuery,ProductOrderEntity.class);
        Gson gson = new Gson();

        int count = 1;
        for (ProductOrderEntity entity:list){
            System.out.println((count++) +" - "+gson.toJson(entity));
        }
    }


    @Test
    public void letsGo6() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery()                                            // 组合查询
                                .must(QueryBuilders.matchQuery("remark","薯片"))          // 必须包括关键词 薯片
                                .mustNot(QueryBuilders.matchQuery("remark","泡菜"))        // 必须不包括 泡菜
                                .should(QueryBuilders.matchQuery("remark","黄瓜"))    // boost 提升权重 默认boost是1
                                .should(QueryBuilders.matchQuery("remark","水").boost(200))
                                .should(QueryBuilders.matchQuery("remark","可乐").boost(100))         // 尽可能包括 可乐
                                .minimumShouldMatch(1)                                                                  // should 至少匹配一项，也可以用百分比形式表示
                )
                .withPageable(PageRequest.of(0,20))  // page = 0 代表第一页
                //.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)) // 排序基础使用
                .build();
        Page<ProductOrderEntity> page = template.queryForPage(searchQuery,ProductOrderEntity.class);
        Gson gson = new Gson();
        int count = 1;
        for (ProductOrderEntity entity:page.getContent()){
            System.out.println((count++) +" - "+gson.toJson(entity));
        }
    }

    @Test
    public void letsGo7() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(
                    QueryBuilders.disMaxQuery()                                             // dis_max 分离最大化查询，将任何与任一查询匹配的文档作为结果返回，但只将最佳匹配的评分作为查询的评分结果返回
                        .add(QueryBuilders.matchQuery("productName","乐"))
                        .add(QueryBuilders.matchQuery("remark","水"))
                        .tieBreaker(0.3f)       // tie_breaker 可以是 0 到 1 之间的浮点数，其中 0 代表使用 dis_max 最佳匹配语句的普通逻辑， 1 表示所有匹配语句同等重要。最佳的精确值需要根据数据与查询调试得出，但是合理值应该与零接近（处于 0.1 - 0.4 之间），这样就不会颠覆 dis_max 最佳匹配性质的根本。
                )
                .withPageable(PageRequest.of(0,20))  // page = 0 代表第一页
                //.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)) // 排序基础使用
                .build();
        Page<ProductOrderEntity> page = template.queryForPage(searchQuery,ProductOrderEntity.class);
        Gson gson = new Gson();
        int count = 1;
        for (ProductOrderEntity entity:page.getContent()){
            System.out.println((count++) +" - "+gson.toJson(entity));
        }
    }


    @Test
    public void letsGo8() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.multiMatchQuery("乐 薯片 水","productName","remark")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                        .tieBreaker(0.3f)
                        .minimumShouldMatch("30%")
                )
                .withPageable(PageRequest.of(0,20))  // page = 0 代表第一页
                //.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)) // 排序基础使用
                .build();
        Page<ProductOrderEntity> page = template.queryForPage(searchQuery,ProductOrderEntity.class);
        Gson gson = new Gson();
        int count = 1;
        for (ProductOrderEntity entity:page.getContent()){
            System.out.println((count++) +" - "+gson.toJson(entity));
        }
    }

    @Test
    public void letsGo9() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.multiMatchQuery("乐 薯片 水","product_*","prodcutName^2","remark")  // 模糊查询 多个字段 productName ,productType 字段都会查询 , ^ 用于指定字段提权
                                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                                .tieBreaker(0.3f)
                                .minimumShouldMatch("30%")
                )
                .withPageable(PageRequest.of(0,20))  // page = 0 代表第一页
                //.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)) // 排序基础使用
                .build();
        Page<ProductOrderEntity> page = template.queryForPage(searchQuery,ProductOrderEntity.class);
        Gson gson = new Gson();
        int count = 1;
        for (ProductOrderEntity entity:page.getContent()){
            System.out.println((count++) +" - "+gson.toJson(entity));
        }
    }

    @Test
    public void letsGo10() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.matchPhraseQuery("remark","乐 水")   // 短语匹配
                )
                .withPageable(PageRequest.of(0,20))  // page = 0 代表第一页
                //.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)) // 排序基础使用
                .build();
        Page<ProductOrderEntity> page = template.queryForPage(searchQuery,ProductOrderEntity.class);
        Gson gson = new Gson();
        int count = 1;
        for (ProductOrderEntity entity:page.getContent()){
            System.out.println((count++) +" - "+gson.toJson(entity));
        }
    }


    @Test
    public void letsGo11() {

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withIndexName("indexName")
                .withId("id")
                .withObject(new Object())
                .build();
    }
}
