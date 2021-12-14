package com.kkb.cubemall.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * @Author: sublun
 * @Date: 2021/4/25 17:41
 */
public class IndexSearchTest {
    private RestHighLevelClient client;

    @Before
    public void init() {
        //创建一个client对象
        client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("127.0.0.1", 9200)
        ));
    }

    @Test
    public void searchIndex1() throws Exception {
        SearchRequest request = new SearchRequest("hello1")
                .source(new SearchSourceBuilder()
                    .query(QueryBuilders.matchAllQuery())
                );
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //从response对象中取结果
        SearchHits hits = response.getHits();
        long total = hits.getTotalHits().value;
        System.out.println("总记录数：" + total);
        SearchHit[] searchHits = hits.getHits();
        Stream.of(searchHits).forEach(e-> System.out.println(e.getSourceAsString()));
    }

    @Test
    public void termQuery() throws Exception {
        SearchRequest request = new SearchRequest("blog_1")
                .source(new SearchSourceBuilder()
                        .query(QueryBuilders.termQuery("title","品牌"))
                );

        printResult(request);
    }

    private void printResult(SearchRequest request) throws Exception {
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //从response对象中取结果
        SearchHits hits = response.getHits();
        long total = hits.getTotalHits().value;
        System.out.println("总记录数：" + total);
        SearchHit[] searchHits = hits.getHits();
        Stream.of(searchHits).forEach(e-> {
            System.out.println(e.getSourceAsString());
            System.out.println(e.getHighlightFields());
        });
    }

    @Test
    public void searchPage() throws Exception {
        SearchRequest request = new SearchRequest("blog_1")
                .source(new SearchSourceBuilder()
                        //查询条件
                        .query(QueryBuilders.matchQuery("title", "开心"))
                        //过滤
//                        .postFilter(QueryBuilders.termQuery("title","未来"))
                        //.postFilter(QueryBuilders.termQuery("content","围城"))
                        //高亮显示
                        .highlighter(new HighlightBuilder()
                                .field("title")
                                .field("content")
                                .preTags("<em>")
                                .postTags("</em>")
                        )
                        //分页条件
                        .from(0)
                        .size(30)

                );
        //执行查询
        printResult(request);
    }
}
