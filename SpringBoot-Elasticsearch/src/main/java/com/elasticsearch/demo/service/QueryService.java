package com.elasticsearch.demo.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.elasticsearch.demo.domain.Hotel;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QueryService implements IQueryService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public Page<Hotel> searchPageList(String searchVal, String brand, String city, String starName, Integer sort, Integer highlight, Integer curPage, Integer pageSize) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 分页
        Pageable pageable = PageRequest.of(curPage - 1, pageSize);
        nativeSearchQueryBuilder.withPageable(pageable);
        // 过滤
        if (StringUtils.isNotBlank(brand) || StringUtils.isNotBlank(city) || StringUtils.isNotBlank(starName)) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (StringUtils.isNotBlank(brand)) {
                boolQueryBuilder.must(QueryBuilders.termQuery("brand", brand));
            }
            if (StringUtils.isNotBlank(city)) {
                boolQueryBuilder.must(QueryBuilders.termQuery("city", city));
            }
            if (StringUtils.isNotBlank(starName)) {
                boolQueryBuilder.must(QueryBuilders.termQuery("starName", starName));
            }
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }
        // 搜索
        if (StringUtils.isNotBlank(searchVal)) {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("name", searchVal),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("address", searchVal),
                    ScoreFunctionBuilders.weightFactorFunction(5)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("business", searchVal),
                    ScoreFunctionBuilders.weightFactorFunction(2)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);

            FunctionScoreQueryBuilder functionScoreQueryBuilder;
            if (highlight == 1) {
                MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchVal, "name", "address", "business");
                functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(multiMatchQueryBuilder, builders)
                        .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                        .setMinScore(2);
            } else {
                functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                        .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                        .setMinScore(2);
            }
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        } else {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        }
        // 高亮
        if (highlight == 1) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags("<font color='red'>");
            highlightBuilder.postTags("</font>");
            highlightBuilder.field("name").field("address").field("business");
            nativeSearchQueryBuilder.withHighlightBuilder(highlightBuilder);
        }
        //排序
        if (sort != null) {
            if (sort == 1) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
            } else if (sort == 2) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
            } else if (sort == 3) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("score").order(SortOrder.ASC));
            } else if (sort == 4) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC));
            } else if (sort == 5) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("cjsj").order(SortOrder.ASC));
            } else if (sort == 6) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("cjsj").order(SortOrder.DESC));
            }
        } else {
            //按算分排序
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        }
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        // 查询
        SearchHits<Hotel> searchHits = elasticsearchRestTemplate.search(searchQuery, Hotel.class);
        List<Hotel> content;
        if (highlight == 1) {
            // 高亮结果处理
            content = searchHits.stream().map(hit -> {
                Hotel hotel = hit.getContent();
                Map<String, List<String>> highlightFields = hit.getHighlightFields();
                highlightFields.forEach((k, v) -> {
                    String highlightVal = v.get(0);
                    if ("name".equals(k)) {
                        hotel.setName(highlightVal);
                    }
                    if ("address".equals(k)) {
                        hotel.setAddress(highlightVal);
                    }
                    if ("business".equals(k)) {
                        hotel.setBusiness(highlightVal);
                    }
                });
                return hotel;
            }).collect(Collectors.toList());
        } else {
            content = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        }
        return new PageImpl<>(content, pageable, searchHits.getTotalHits());
    }


}
