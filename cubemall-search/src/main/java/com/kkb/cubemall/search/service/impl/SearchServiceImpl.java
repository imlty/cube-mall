package com.kkb.cubemall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.kkb.cubemall.search.model.Page;
import com.kkb.cubemall.search.model.SpuInfo;
import com.kkb.cubemall.search.service.SearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 *
 */
//@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Override
    public Map<String, Object> search(Map<String, String> paramMap) {

        //创建查询条件
        NativeSearchQuery query = buildQuery(paramMap);

        //执行查询
        SearchHits<SpuInfo> searchHits = esTemplate.search(query, SpuInfo.class);
        //解析查询结果
        Map<String, Object> resultMap = parseResponse(searchHits);
        //一共查询到多少条数据
        long totalHits = searchHits.getTotalHits();
        resultMap.put("totalRows", totalHits);
        String pageNumStr = paramMap.get("pageNum");
        int pageNum = 1;
        if (pageNumStr != null && !"".equals(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        //当前页数
        resultMap.put("pageNum", pageNum);
        //总页数
        int totalPages = (int) Math.ceil(totalHits / Page.pageSize);
        resultMap.put("totalPages", totalPages);

        return resultMap;

    }

    private NativeSearchQuery buildQuery(Map<String, String> paramMap) {
        /**
         * 0. 创建下面查询所需要的一些查询对象
         */
        //创建组合查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //创建顶级查询封装对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //判断如果查询条件为空, 直接返回null
        if (paramMap == null || paramMap.size() == 0) {
            return null;
        }

        /**
         * 1. 根据关键字设置查询条件
         * must 是对关键字切分词查询
         * operator.AND 作用是切分词后, 多个切分出来的关键字的关系是 and, 如果不加这个默认是or
         */
        String keywords = paramMap.get("keywords");
        if (!StringUtils.isEmpty(keywords)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("name", keywords).operator(Operator.AND));
        }

        /**
         * 2. 设置品牌过滤条件
         * termQuery是精确查询, 查询的内容不进行切分词
         * filter是过滤查询, 是在根据关键字查询出来的结果集基础上, 进行进一步过滤
         */
        String brand = paramMap.get("brand");
        if (!StringUtils.isEmpty(brand)) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("brandName", brand));
        }

        /**
         * 3. 设置分类过滤条件
         */
        String category = paramMap.get("category");
        if (!StringUtils.isEmpty(category)) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("categoryName", category));
        }

        /**
         * 4. 设置规格过滤查询条件
         */
        //获取参数map中所有key的集合
        Set<String> keySet = paramMap.keySet();
        if (keySet != null) {
            for (String key : keySet) {
                //从所有参数的key中找到规格的key
                if (key.startsWith("spec_")) {
                    //将spec_颜色替换成, specMap.颜色.keyword
                    //规格默认是text类型, 是具有切分词功能的类型, 这里精确查询必须强制
                    //转换成keyword类型, 不进行切分词, 所以后面拼接.keyword是临时强制类型转换
                    String specMapKey = key.replace("spec_", "specMap.") + ".keyword";
                    //获取规格的值
                    String specValue = paramMap.get(key);
                    //判断规格值中如果有空格
                    if (specValue.contains(" ")) {
                        //将规格值中的空格转换成加号, 因为接受参数的时候, springMvc将, 规格中参数的加号
                        //默认替换成了空格, 这里给转换回来
                        specValue = specValue.replaceAll(" ", "+");
                    }
                    boolQueryBuilder.filter(QueryBuilders.termQuery(specMapKey, specValue));
                }
            }
        }

        /**
         * 5. 根据价格区间范围查询
         */
        String priceStr = paramMap.get("price");
        if (!StringUtils.isEmpty(priceStr)) {
            //根据价格字符串进行切割, 切割出最小值和最大值
            String[] splitPrice = priceStr.split("-");
            //有最大值和最小值
            if (splitPrice.length == 2) {
                //大于等于最小值
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(splitPrice[0]));
                //小于等于最大值
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lte(splitPrice[1]));
            }
            //判断如果参数只有最小值
            if (splitPrice.length == 1) {
                //大于等于最小值
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(splitPrice[0]));
            }
        }

        //将组合查询条件放入顶级查询对象中
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        /**
         * 6. 根据价格排序
         */
        //获取排序字段
        String sortField = paramMap.get("sortField");
        //获取排序规则
        String sortRule = paramMap.get("sortRule");

        if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortRule)) {
            //根据排序规则判断是升序
            if ("ASC".equals(sortRule)) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.ASC));
            }
            //根据排序规则判断是降序
            if ("DESC".equals(sortRule)) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.DESC));
            }
        }

        /**
         * 7. 设置分页
         */
        //获取当前页字符串
        String pageNumStr = paramMap.get("pageNum");
        //设置默认当前页为第一页
        Integer pageNum = Page.pageNum;
        //判断页面传入的当前页参数如果不为空
        if (!StringUtils.isEmpty(pageNumStr)) {
            //当前页数等于页面传入的当前页参数
            pageNum = Integer.parseInt(pageNumStr);
        }

        //第一个参数: 当前页,es中第0页就是我们实际的第一页,  第二个参数: 每页显示多少条数据
        nativeSearchQueryBuilder.withPageable(PageRequest.of(pageNum - 1, Page.pageSize));


        /**
         * 8. 根据品牌分组查询, 识别出关键字的所有品牌, 作为过滤查询条件使用(聚合)
         */
        //设置分组名称
        String brandGroup = "brandGroup";
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(brandGroup).field("brandName"));

        /**
         * 9. 根据规格分组查询, 识别出关键字的所有规格, 作为过滤查询条件使用(聚合)
         */
        //设置分组名称
        String specGroup = "specGroup";
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(specGroup).field("spec.keyword"));

        /**
         * 10. 设置高亮显示的字段, 根据商品名称高亮显示
         */
        HighlightBuilder.Field highlightField = new HighlightBuilder.Field("name").preTags("<span style='color:red'>").postTags("</span>");
        nativeSearchQueryBuilder.withHighlightFields(highlightField);

        //返回结果
        return nativeSearchQueryBuilder.build();
    }


    /**
     * 解析查询结果
     * @param searchHits
     * @return
     */
    private Map<String, Object> parseResponse(SearchHits<SpuInfo> searchHits) {
        Map<String, Aggregation> agMap = searchHits.getAggregations().asMap();
        /**
         * 12. 获取根据品牌分组查询的结果(获取聚合结果)
         */
        List<String> brandNameList = new ArrayList<>();
        //获取分组查询结果, 结果强转成StringTerms对象, 为了更方便获取结果数据
        ParsedStringTerms aggregation = (ParsedStringTerms)agMap.get("brandGroup");
        //从结果中, 获取桶对象
        List<? extends Terms.Bucket> buckets = aggregation.getBuckets();
        //判断桶的集合不为空
        if (buckets != null) {
            for (Terms.Bucket bucket : buckets) {
                //从桶中获取去重后的品牌名称
                String brandName = bucket.getKeyAsString();
                brandNameList.add(brandName);
            }
        }


        /**
         * 13. 获取根据规格分组查询的结果(获取聚合结果)
         */

        List<String> specList = new ArrayList<>();
        //获取分组查询结果, 结果强转成StringTerms对象, 为了更方便获取结果数据
        ParsedStringTerms specAgg = (ParsedStringTerms)agMap.get("specGroup");
        //从结果中, 获取桶对象
        List<? extends Terms.Bucket> specAggBuckets = specAgg.getBuckets();
        //判断桶的集合不为空
        if (specAggBuckets != null) {
            for (Terms.Bucket bucket : specAggBuckets) {
                //从桶中获取去重后的品牌名称
                String spec = bucket.getKeyAsString();
                specList.add(spec);
            }
        }

        /**
         * 14. 封装所有查询出的数据返回
         */
        List<SpuInfo> content = new ArrayList<>();
        for (SearchHit<SpuInfo> hit : searchHits.getSearchHits()) {
            //将json转换成对象
            SpuInfo SpuInfo = hit.getContent();

            //获取商品名称带高亮的字符
            List<String> name = hit.getHighlightField("name");
            if (name != null && name.size() > 0) {
                //将找到的带高亮的商品名称, 放到SpuInfo对象中
                SpuInfo.setSpuName(name.get(0));
            }
            content.add(SpuInfo);
        }
        Map<String, Object> resultMap = new HashMap<>();
        //查询到的结果集数据
        resultMap.put("rows", content);
        //根据品牌聚合查询结果集
        resultMap.put("brandList", brandNameList);
        //根据规格聚合查询结果集
        resultMap.put("specList", specDistinct(specList));
        return resultMap;
    }

    /**
     * 对规格聚合查询结果去除重复数据
     *
     * 原始数据格式:
     *  [
     *         "{'颜色': '蓝色', '版本': '6GB+128GB'}",
     *         "{'颜色': '黑色', '版本': '6GB+128GB'}",
     *         "{'颜色': '黑色', '版本': '4GB+64GB'}",
     *         "{'颜色': '蓝色', '版本': '4GB+64GB'}",
     *         "{'颜色': '金色', '版本': '4GB+64GB'}",
     *         "{'颜色': '粉色', '版本': '6GB+128GB'}"
     *     ]
     *
     * 需要返回的数据结果:
     * {
     *      "{'颜色': ['蓝色', '黑色', '金色', '粉色']}",
     *      "{'版本': ['6GB+128GB', '4GB+64GB', '6GB+128GB']}"
     * }
     *
     *
     * @param specJsonStrList
     * @return
     */
    private Map<String, Set<String>> specDistinct(List<String> specJsonStrList) {
        if (specJsonStrList == null) {
            return null;
        }

        Map<String, Set<String>> reslutMap = new HashMap<>();

        //遍历, 每个specJsonStr对象中的数据例如: "{'颜色': '蓝色', '版本': '6GB+128GB'}
        for (String specJsonStr : specJsonStrList) {
            Map<String, String> specMap = JSON.parseObject(specJsonStr, Map.class);

            Set<Map.Entry<String, String>> entries = specMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                //key的值就是例如: 颜色,   版本....
                String key = entry.getKey();
                //value的值就是例如: 蓝色,   '6GB+128GB' .....
                String value = entry.getValue();

                //从返回的map中根据key叫做, 颜色, 版本这样的key获取value值的集合
                Set<String> valueSet = reslutMap.get(key);
                if (valueSet == null) {
                    //返回的map中没有颜色或者版本数据, 需要新建对象
                    valueSet = new HashSet<>();
                    //在新建的set对象中放入, 例如: 蓝色,黑色等值, 或者放入: 6G+128g这样的值
                    valueSet.add(value);
                    //将值的set集合, 和key放入resultMap返回对象中
                    reslutMap.put(key, valueSet);
                } else {
                    //返回的map中有颜色或者版本对象数据, 需要在原有数据上追加当前的数据进去
                    valueSet.add(value);
                    //将最新的值放回到reslutMap中, 覆盖之前里面的数据
                    reslutMap.put(key, valueSet);
                }
            }
        }

        return reslutMap;
    }
}
