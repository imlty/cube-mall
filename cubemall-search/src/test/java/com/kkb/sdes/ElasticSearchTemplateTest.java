package com.kkb.sdes;

import com.kkb.cubemall.search.CubemallSearchApplication;
import com.kkb.cubemall.search.model.Blog;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;


/**
 * @Author: sublun
 * @Date: 2021/4/22 18:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CubemallSearchApplication.class)
public class ElasticSearchTemplateTest {
    @Autowired
    private ElasticsearchRestTemplate template;

    @Test
    public void createIndex() {
        //template.indexOps(Blog.class).create();
        template.indexOps(IndexCoordinates.of("hello_1")).create();
    }

    @Test
    public void putMappiing() {
        Document mapping = template.indexOps(Blog.class).createMapping();
        template.indexOps(IndexCoordinates.of("hello_1")).putMapping(mapping);
    }

    @Test
    public void testQuery() {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("title", "电影"))
                .withHighlightBuilder(new HighlightBuilder().field("title").preTags("<em>").postTags("</em>"))
                .withFilter(QueryBuilders.termQuery("title", "湖南"))
                .addAggregation(AggregationBuilders.count("mobile_count").field("mobile"))
                .build();
        SearchHits<Blog> searchHits = template.search(query, Blog.class);
        long totalHits = searchHits.getTotalHits();
        System.out.println(totalHits);
        List<SearchHit<Blog>> searchHits1 = searchHits.getSearchHits();
        searchHits1.forEach(e->{
            Blog content = e.getContent();
            System.out.println(content);
            Map<String, List<String>> highlightFields = e.getHighlightFields();
            List<String> title = highlightFields.get("title");
            System.out.println(title);
        });
        Map<String, Aggregation> stringAggregationMap = searchHits.getAggregations().asMap();
        System.out.println(stringAggregationMap);
        Aggregation aggregation = stringAggregationMap.get("mobile_count");
        ParsedValueCount valueCount = searchHits.getAggregations().get("mobile_count");
        System.out.println(aggregation == valueCount);
        System.out.println(aggregation.getName());
        System.out.println(valueCount.getValue());
    }

}
