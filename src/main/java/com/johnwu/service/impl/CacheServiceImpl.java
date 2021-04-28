package com.johnwu.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.johnwu.service.CacheService;

/*******************************************
 * 
 * This implementation uses in-memory hashmap structure, which is subject to
 * 	 the available memory space in the system.
 * 
 * A more sophisticated implementation would be a distributed and scalable cache
 *   solution, e.g. redis cache. 
 */

@Service
public class CacheServiceImpl implements CacheService {

	private Map<String, String> tinyUrlCache = new HashMap<>();
	private Map<String, String> urlCache = new HashMap<>();
	
	@Override
	public Optional<String> findTinyUrl(String url) {
		String value = urlCache.get(url);
		return value==null ? Optional.empty(): Optional.of(value);
	}

	@Override
	public void saveTinyUrl(String url, String tinyUrl) {
		urlCache.put(url, tinyUrl);
	}

	@Override
	public Optional<String> findUrl(String tinyUrl) {
		String value = tinyUrlCache.get(tinyUrl);
		return value==null ? Optional.empty(): Optional.of(value);
	}

	@Override
	public void saveUrl(String url, String tinyUrl) {
		tinyUrlCache.put(tinyUrl, url);
	}

}
