package com.johnwu.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.johnwu.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService {

	private Map<String, String> cache = new HashMap<>();
	
	@Override
	public Optional<String> find(String url) {
		String value = cache.get(url);
		return value==null ? Optional.empty(): Optional.of(value);
	}

	@Override
	public void save(String url, String tinyUrl) {
		cache.put(url, tinyUrl);
	}

}
