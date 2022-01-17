package com.kkb.cubemall.search.service.impl;

import com.kkb.cubemall.search.model.SpuInfo;
import com.kkb.cubemall.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl2  implements SearchService {

    @Autowired
    private ElasticsearchRestTemplate template;

    @Override
    public Map<String, Object> search(Map<String, String> paramMap) {
        //创建查询条件
        NativeSearchQuery query = buildQuery(paramMap);
        //执行查询
        SearchHits<SpuInfo> searchHits = template.search(query, SpuInfo.class);
        //解析结果
        Map<String, Object> resultMap = parseResponse(searchHits);
        //取总记录数
        long totalHits = searchHits.getTotalHits();
        resultMap.put("totalRows", totalHits);
        //取当前页面码
        String pageNumStr = paramMap.get("pageNum");
        int pageNum = StringUtils.isNotBlank(pageNumStr)?Integer.parseInt(pageNumStr):1;
        resultMap.put("pageNum", pageNum);
        int totalPages = (int) Math.ceil(totalHits / 60);
        resultMap.put("totalPages", totalPages);

        return resultMap;
    }

    private NativeSearchQuery buildQuery(Map<String, String> paramMap) {
        //创建一个构造器
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //取查询条件
        String keywords = paramMap.get("keywords");
        boolQueryBuilder.must(QueryBuilders.matchQuery("spuName", keywords));
        //品牌过滤
        String brand = paramMap.get("brand");
        if (StringUtils.isNotBlank(brand)) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("brandName", brand));
        }
        //分类过滤
        String category = paramMap.get("category");
        if (StringUtils.isNotBlank(category)) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("categoryName", category));
        }
        //价格区间过滤
        String price = paramMap.get("price");
        if (StringUtils.isNotBlank(price)) {
            String[] split = price.split("-");
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(split[0]).lte(split[1]));
        }
        builder.withQuery(boolQueryBuilder);
        //价格排序
        String sortField = paramMap.get("sortField");
        //排序规则
        String sortRule = paramMap.get("sortRule");
        if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortRule)) {
            if ("ASC".equals(sortRule)) {
                builder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.ASC));
            } else {
                builder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.DESC));
            }
        }
        //分页设置
        String pageNumStr = paramMap.get("pageNum");
        Integer pageNum = 1;
        if (StringUtils.isNotBlank(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        builder.withPageable(PageRequest.of(pageNum - 1, 60));
        //品牌聚合
        builder.addAggregation(AggregationBuilders.terms("brandGroup").field("brandName"));
        //分类聚合
        builder.addAggregation(AggregationBuilders.terms("categoryGroup").field("categoryName"));
        //高亮显示
        builder.withHighlightBuilder(new HighlightBuilder().field("spuName").preTags("<em style=\"color:red\">").postTags("</em>"));

        return builder.build();


    }

    private Map<String, Object> parseResponse(SearchHits<SpuInfo> searchHits) {
        Map<String, Object> resultMap = new HashMap<>();
        //取品牌聚合结果
        Aggregations aggregations = searchHits.getAggregations();
        //取品牌列表
        ParsedStringTerms brandTerms = aggregations.get("brandGroup");
        List<String> brandList = brandTerms.getBuckets().stream()
                .map(b -> b.getKeyAsString())
                .collect(Collectors.toList());
        resultMap.put("brandList", brandList);
        //取分类列表
        ParsedStringTerms categroyTerms = aggregations.get("categoryGroup");
        List<String> categoryList = categroyTerms.getBuckets().stream()
                .map(b -> b.getKeyAsString())
                .collect(Collectors.toList());
        resultMap.put("categoryList", categoryList);
        //取商品列表
        List<SearchHit<SpuInfo>> docList = searchHits.getSearchHits();
        List<SpuInfo> rows = docList.stream()
                .map(doc -> {
                    SpuInfo spuInfo = doc.getContent();
                    List<String> highlightField = doc.getHighlightField("spuName");
                    if (highlightField.size() > 0) {
                        spuInfo.setSpuName(highlightField.get(0));
                    }
                    return spuInfo;
                })
                .collect(Collectors.toList());
        resultMap.put("rows",rows);
        return resultMap;
    }
}
