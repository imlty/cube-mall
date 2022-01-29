package com.kkb.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Author: sublun
 * @Date: 2021/4/22 10:57
 */
public class SearchIndexTest {
    //will be deprecated in Elasticsearch 7.0 and removed in Elasticsearch 8.0.
    //TransportClient transportClient;
    public RestHighLevelClient client = null;
    @Before
    public void init() {
        client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("192.168.68.129", 9200, "http"),
                new HttpHost("192.168.68.130", 9200, "http"),
                new HttpHost("192.168.68.131", 9200, "http")
        ));
    }

    @Test
    public void testClient() throws IOException {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //searchSourceBuilder.aggregation(AggregationBuilders.terms("top_10_states").field("state").size(10));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("blog1");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
    }

    @Test
    public void searchIndex() throws Exception {
        SearchRequest request = new SearchRequest();
        request.indices("test")
                .source(new SearchSourceBuilder()
                        .query(QueryBuilders.matchAllQuery())
                        .from(0)
                        .size(10)
                );
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        SearchHits searchHits = response.getHits();
        System.out.println("总记录数：" + searchHits.getTotalHits().value);
        SearchHit[] hits = searchHits.getHits();
        Stream.of(hits).forEach(h-> System.out.println(h));
    }

    @Test
    public void searchIndexWithHightlight() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("test")
                .source(new SearchSourceBuilder()
                        .query(QueryBuilders.multiMatchQuery("最喜爱的电影","title", "content"))
                        .postFilter(QueryBuilders.termQuery("title", "开心"))
                        //.postFilter(QueryBuilders.termQuery("title", "中国"))
                        .highlighter(new HighlightBuilder()
                                .field("title")
                                .field("content")
                                .preTags("<em>")
                                .postTags("</em>")
                        )
                );
        System.out.println(request);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        printResponse(response);
    }

    private void printResponse(SearchResponse response) {
        SearchHits hits = response.getHits();
        System.out.println("总记录数：" + hits.getTotalHits().value);
        SearchHit[] searchHits = hits.getHits();
        Stream.of(searchHits)
                .forEach(e->{
                    Map<String, HighlightField> highlightFields = e.getHighlightFields();
                    String source = e.getSourceAsString();
                    System.out.println(source);
                    System.out.println(highlightFields);

                });
    }

}
