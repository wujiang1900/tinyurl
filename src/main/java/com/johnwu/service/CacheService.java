package com.johnwu.service;

import java.util.Optional;

public interface CacheService {

	Optional<String> find(String url);

	void save(String url, String tinyUrl);

}
