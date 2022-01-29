package com.kkb.cubemall.search.service;

import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.search.model.Blog;
import com.kkb.cubemall.search.repostory.BlogRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: sublun
 * @Date: 2021/4/22 18:32
 */
@Service
public class BlogManageService {
    @Autowired
    private BlogRepostory blogRepostory;
    @Autowired
    private ElasticsearchRestTemplate template;

    public R crateIndex() {
        //template.indexOps(Blog.class).create();
        template.indexOps(IndexCoordinates.of("blog_3")).create();
        return R.ok();
    }

    public R putMapping() {
        Document mapping = template.indexOps(Blog.class).createMapping();
        System.out.println(mapping);
        template.indexOps(Blog.class).putMapping(mapping);
        return R.ok();
    }
    public R deleteIndex(String index) {
        template.indexOps(IndexCoordinates.of(index)).delete();
        return R.ok();
    }

    public R addBlog(Blog blog) {
        blogRepostory.save(blog);
        return R.ok();
    }
    public R updateBlog(Blog blog) {
        blogRepostory.save(blog);
        return R.ok();
    }

    public R deleteBlog(long id) {
        blogRepostory.deleteById(id);
        return R.ok();
    }

    public R bulkBlog(List<Blog> blogs) {
        List<IndexQuery> queryList = blogs.stream()
                .map(b -> {
                    IndexQuery indexQuery = new IndexQuery();
                    indexQuery.setId(b.getId() + "");
                    //文档内容使用setObject或者使用setSource
                    indexQuery.setObject(b);
                    //都可以
                    //indexQuery.setSource(JSONObject.toJSONString(b));
                    return indexQuery;
                })
                .collect(Collectors.toList());
        List<String> results = template.bulkIndex(queryList, IndexCoordinates.of("blog_1"));
        return R.ok(results);
    }

    public Blog getDocumentById(String id) {
        Blog blog = template.get(id, Blog.class, IndexCoordinates.of("test"));
        return blog;
    }



}
