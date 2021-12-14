package com.kkb.cubemall.sdes;

import com.kkb.cubemall.search.CubemallSearchApplication;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Author: sublun
 * @Date: 2021/4/25 18:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CubemallSearchApplication.class)
public class RestClientTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testRestClient() throws IOException {
        restHighLevelClient.indices().create(new CreateIndexRequest("test"), RequestOptions.DEFAULT);
    }
}
