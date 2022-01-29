package com.kkb.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author: sublun
 * @Date: 2021/4/22 10:57
 */
public class IndexManagerTest {
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
    public void createIndex() throws IOException {
        client.indices().create(new CreateIndexRequest("test"), RequestOptions.DEFAULT);
    }

    @Test
    public void createIndexWithSetting() throws Exception {

        CreateIndexRequest request = new CreateIndexRequest("test1")
                .settings(
                        Settings.builder()
                                .put("number_of_shards", 5)
                                .put("number_of_replicas", 1)
                                .build()
                );
                //.mapping("_doc", builder);
        boolean acknowledged = client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
        System.out.println(acknowledged);
    }

    @Test
    public void putMapping() throws Exception {
        String mappingStr = "{\n" +
                "\t\"properties\":{\n" +
                "\t\t\"id\":{\n" +
                "\t\t\t\"type\":\"long\"\n" +
                "\t\t},\n" +
                "\t\t\"title\":{\n" +
                "\t\t\t\"type\":\"text\",\n" +
                "\t\t\t\"analyzer\":\"ik_max_word\",\n" +
                "\t\t\t\"store\":true\n" +
                "\t\t},\n" +
                "\t\t\"content\":{\n" +
                "\t\t\t\"type\":\"text\",\n" +
                "\t\t\t\"analyzer\":\"ik_max_word\",\n" +
                "\t\t\t\"store\":true\n" +
                "\t\t},\n" +
                "\t\t\"comment\":{\n" +
                "\t\t\t\"type\":\"text\",\n" +
                "\t\t\t\"analyzer\":\"ik_max_word\",\n" +
                "\t\t\t\"store\":true\n" +
                "\t\t},\n" +
                "\t\t\"mobile\":{\n" +
                "\t\t\t\"type\":\"keyword\",\n" +
                "\t\t\t\"store\":true\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        System.out.println(mappingStr);

        PutMappingRequest request = new PutMappingRequest("test")
                .source(mappingStr, XContentType.JSON);
        boolean acknowledged = client.indices().putMapping(request, RequestOptions.DEFAULT).isAcknowledged();
        System.out.println(acknowledged);
    }

    @Test
    public void createIndexWithMapping() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("id")
                            .field("type", "long")
                        .endObject()
                        .startObject("title")
                            .field("type", "text")
                            .field("store", true)
                            .field("analyzer", "ik_max_word")
                        .endObject()
                        .startObject("content")
                            .field("type", "text")
                            .field("store", true)
                            .field("analyzer", "ik_max_word")
                        .endObject()
                        .startObject("comment")
                            .field("type", "text")
                            .field("store", true)
                            .field("analyzer", "ik_max_word")
                        .endObject()
                        .startObject("mobile")
                            .field("type", "keyword")
                            .field("store", true)
                        .endObject()
                    .endObject()
                .endObject();
        /*String mappingStr = "{\n" +
                "\t\"properties\":{\n" +
                "\t\t\"id\":{\n" +
                "\t\t\t\"type\":\"long\"\n" +
                "\t\t},\n" +
                "\t\t\"title\":{\n" +
                "\t\t\t\"type\":\"text\",\n" +
                "\t\t\t\"analyzer\":\"ik_max_word\",\n" +
                "\t\t\t\"store\":true\n" +
                "\t\t},\n" +
                "\t\t\"content\":{\n" +
                "\t\t\t\"type\":\"text\",\n" +
                "\t\t\t\"analyzer\":\"ik_max_word\",\n" +
                "\t\t\t\"store\":true\n" +
                "\t\t},\n" +
                "\t\t\"comment\":{\n" +
                "\t\t\t\"type\":\"text\",\n" +
                "\t\t\t\"analyzer\":\"ik_max_word\",\n" +
                "\t\t\t\"store\":true\n" +
                "\t\t},\n" +
                "\t\t\"mobile\":{\n" +
                "\t\t\t\"type\":\"keyword\",\n" +
                "\t\t\t\"store\":true\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";*/
        String json = XContentHelper.convertToJson(BytesReference.bytes(builder), true, true, builder.contentType());
        System.out.println(json);

        CreateIndexRequest request = new CreateIndexRequest("test3")
                .settings(
                        Settings.builder()
                                .put("number_of_shards", 5)
                                .put("number_of_replicas", 1)
                                .build()
                )
                //.mapping(mappingStr, XContentType.JSON);
                .mapping(builder);
        boolean acknowledged = client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
        System.out.println(acknowledged);
    }

    @Test
    public void deleteIndex() throws Exception {
        client.indices().delete(new DeleteIndexRequest("test3"), RequestOptions.DEFAULT);
    }
}
