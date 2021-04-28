package com.johnwu.service;

import java.util.Optional;

public interface CacheService {

	void saveTinyUrl(String url, String tinyUrl);

	Optional<String> findTinyUrl(String url);

	Optional<String> findUrl(String tinyUrl);

	void saveUrl(String string, String tinyUrl);

}
