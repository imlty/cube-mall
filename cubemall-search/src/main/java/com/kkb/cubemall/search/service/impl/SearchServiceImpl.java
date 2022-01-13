package com.kkb.cubemall.search.service.impl;

import com.kkb.cubemall.search.model.SpuInfo;
import com.kkb.cubemall.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchRestTemplate template;

    @Override
    public Map<String, Object> search(Map<String, String> paramMap) {
        // 封装查询条件
        NativeSearchQuery query = buildQuery(paramMap);
        // 执行查询
        SearchHits<SpuInfo> searchHits = template.search(query, SpuInfo.class);

        // 取返回结果
        Map<String, Object> result = parseResponse(searchHits);

        // 页面计算
        long totalHits = searchHits.getTotalHits();
        String pageNumStr = paramMap.get("pageNum");
        int pageNum = StringUtils.isNotBlank(pageNumStr) ? Integer.parseInt(pageNumStr) : 1;
        result.put("pageNum", pageNum);
        // 计算总页数
        int totalPages = (int) Math.ceil(totalHits / 60);
        result.put("totalPages", totalPages);
        return result;
    }

    private NativeSearchQuery buildQuery(Map<String, String> paramMap) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        // keywords
        String spuName = StringUtils.isBlank(paramMap.get("keywords")) ? "" : paramMap.get("keywords");
        builder.withQuery(QueryBuilders.matchQuery("spuName", spuName));

        // 品牌过滤
        String brand = paramMap.get("brand");
        if (StringUtils.isNotBlank(brand)) {
            builder.withQuery(QueryBuilders.termQuery("brandName", brand));
        }

        // 分类过滤
        String category = paramMap.get("category");
        if (StringUtils.isNotBlank(category)) {
            builder.withQuery(QueryBuilders.termQuery("categoryName", category));
        }

        // 价格区间过滤
        String price = paramMap.get("price");
        if (StringUtils.isNotBlank(price)) {
            String[] split = price.split("-");
            builder.withFilter(QueryBuilders.rangeQuery(price).gte(split[0]).gte(split[1]));
        }

        // 排序
        String sortRule = paramMap.get("sortRule");
        String sortField = paramMap.get("sortField");
        if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortRule)) {
            if ("ASC".equals(sortRule)) {
                builder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.ASC));
            } else {
                builder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.DESC));
            }
        }

        // 分页

        String pageNumStr = paramMap.get("pageNum");
        int pageNum = 1;
        if (StringUtils.isNotBlank(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        builder.withPageable(PageRequest.of(pageNum - 1, 60));


        // 高亮
        builder.withHighlightBuilder(new HighlightBuilder()
                .field("spuName")
                .preTags("<em><Style=\"color:red\">")
                .postTags("<em/>"));

        // 聚合条件：品牌、分类
        builder.addAggregation(AggregationBuilders.terms("brandGroup").field("brandName"));
        builder.addAggregation(AggregationBuilders.terms("categoryGroup").field("categoryName"));


        return builder.build();
    }

    private Map<String, Object> parseResponse(SearchHits<SpuInfo> searchHits) {
        Map<String, Object> resultMap = new HashMap<>();

        // 总记录数
        long totalHits = searchHits.getTotalHits();
        resultMap.put("totalRows", totalHits);

        // 结果
        List<SpuInfo> spuInfos = searchHits.getSearchHits().stream().map(e -> {
            SpuInfo spuInfo = e.getContent();
            List<String> highlightField = e.getHighlightField("spuName");
            if (highlightField.size() > 0) {
                spuInfo.setSpuName(highlightField.get(0));
            }
            return spuInfo;
        }).collect(Collectors.toList());

        resultMap.put("rows", spuInfos);

        // 聚合结果

        Aggregations aggregations = searchHits.getAggregations();
        ParsedStringTerms brandGroup = aggregations.get("brandGroup");
        List<String> brandGroups = brandGroup.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getKeyAsString).collect(Collectors.toList());
        resultMap.put("brandList", brandGroups);
        ParsedStringTerms categoryGroup = aggregations.get("categoryGroup");
        List<String> categoryGroups = categoryGroup.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getKeyAsString).collect(Collectors.toList());
        resultMap.put("categoryList", categoryGroups);

        return resultMap;
    }
}
