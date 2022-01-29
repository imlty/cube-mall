package com.kkb.cubemall.search.service;

import java.util.Map;

/**
 *
 */
public interface SearchService {

    /**
     * 搜索(全文检索)
     * @param paramMap
     * @return
     */
    public Map<String, Object> search(Map<String, String> paramMap);
}
