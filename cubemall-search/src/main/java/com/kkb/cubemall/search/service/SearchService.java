package com.kkb.cubemall.search.service;

import org.springframework.stereotype.Service;

import java.util.Map;

public interface SearchService {

    Map<String, Object> search(Map<String, String> paramMap);
}
