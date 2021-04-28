package com.johnwu.service;

import java.util.Optional;
import java.util.concurrent.Future;

public interface CacheService {

	void saveTinyUrl(String url, String tinyUrl);

	Future<Optional<String>> findTinyUrl(String url);

	Future<Optional<String>> findUrl(String tinyUrl);

	void saveUrl(String string, String tinyUrl);

}
