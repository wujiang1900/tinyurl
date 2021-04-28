package com.johnwu.service;

import com.johnwu.exception.UrlNotFoundException;

public interface TinyUrlService {

	String shortenUrl(String url);

	String retrieveUrl(String tinyUrl) throws UrlNotFoundException;

}
