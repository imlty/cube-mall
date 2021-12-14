package com.kkb.cubemall.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: sublun
 * @Date: 2021/4/25 16:09
 * 索引管理
 */
public class IndexManager {

    private RestHighLevelClient client;

    @Before
    public void init() {
        //创建一个client对象
        client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("127.0.0.1", 9201),
                new HttpHost("127.0.0.1", 9202),
                new HttpHost("127.0.0.1", 9204)
        ));
    }

    @Test
    public void createIndex() throws Exception {

        //获得索引管理对象
        IndicesClient indicesClient = client.indices();
        //两个参数
        //1.创建索引请求对象
        //参数：创建的索引库的名称
        CreateIndexRequest request = new CreateIndexRequest("hello");
        //2.请求选项，使用默认值。配置请求头，主要用于认证。
        CreateIndexResponse response = indicesClient.create(request, RequestOptions.DEFAULT);
        //显示结果
        System.out.println(response);
    }

    @Test
    public void createIndex2() throws Exception {
        CreateIndexRequest request = new CreateIndexRequest("hello1")
                .settings(Settings.builder()
                        .put("number_of_shards", 5)
                        .put("number_of_replicas", 1)
                        .build()
                );
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    public void createIndex3() throws Exception {
        /*{
            "properties":{
            "id":{
                "type":"long"
            },
            "title":{
                "type":"text",
                        "analyzer":"ik_smart",
                        "store":true
            },
            "content":{
                "type":"text",
                        "analyzer":"ik_smart",
                        "store":true
            }
        }
        }*/
        XContentBuilder mappings = XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("id")
                            .field("type","long")
                        .endObject()
                        .startObject("title")
                            .field("type","text")
                            .field("analyzer","ik_smart")
                            .field("store", true)
                        .endObject()
                        .startObject("content")
                            .field("type","text")
                            .field("analyzer","ik_smart")
                            .field("store", true)
                        .endObject()
                    .endObject()
                .endObject();


        CreateIndexRequest request = new CreateIndexRequest("hello2")
                .settings(Settings.builder()
                        .put("number_of_shards", 5)
                        .put("number_of_replicas", 1)
                        .build()
                )
                .mapping(mappings);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    public void deleteIndex() throws Exception {
        client.indices().delete(new DeleteIndexRequest("hello"), RequestOptions.DEFAULT);
    }

    @Test
    public void putMappings() throws Exception {
        String mappings = "{\n" +
                "\t\t\t\"properties\":{\n" +
                "\t\t\t\t\"id\":{\n" +
                "\t\t\t\t\t\"type\":\"long\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"title\":{\n" +
                "\t\t\t\t\t\"type\":\"text\",\n" +
                "\t\t\t\t\t\"analyzer\":\"ik_smart\",\n" +
                "\t\t\t\t\t\"store\":true\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"content\":{\n" +
                "\t\t\t\t\t\"type\":\"text\",\n" +
                "\t\t\t\t\t\"analyzer\":\"ik_smart\",\n" +
                "\t\t\t\t\t\"store\":true\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}";
        PutMappingRequest request = new PutMappingRequest("hello1")
                .source(mappings, XContentType.JSON);
        client.indices().putMapping(request, RequestOptions.DEFAULT);
    }
}
