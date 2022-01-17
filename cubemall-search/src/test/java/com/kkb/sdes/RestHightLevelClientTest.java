package com.kkb.sdes;

import com.kkb.cubemall.search.CubemallSearchApplication;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Author: sublun
 * @Date: 2021/4/23 14:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CubemallSearchApplication.class)
public class RestHightLevelClientTest {
    @Autowired
    private RestHighLevelClient client;

    @Test
    public void restClientTest() throws IOException {
        client.indices().create(new CreateIndexRequest("hello"), RequestOptions.DEFAULT);
    }
}
