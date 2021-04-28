package com.johnwu.service.impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.johnwu.domain.TinyUrl;
import com.johnwu.exception.UrlNotFoundException;
import com.johnwu.repo.TinyUrlRepo;
import com.johnwu.service.CacheService;
import com.johnwu.service.TinyUrlGeneratorService;
import com.johnwu.service.TinyUrlService;

@Service
public class TinyUrlServiceImpl implements TinyUrlService {
	private static final long CACHE_SERVICE_TIME_OUT_MS = 40;
	
	private CacheService cache;
	private TinyUrlRepo repo;
	private TinyUrlGeneratorService generator;
	
	public TinyUrlServiceImpl(TinyUrlRepo repo, TinyUrlGeneratorService generator, CacheService cache) {
		this.repo = repo;
		this.generator = generator;
		this.cache = cache;
	}
	
	@Transactional
	public String shortenUrl(String url) {
		Future<Optional<String>> tinyUrlFuture = cache.findTinyUrl(url);
		String tinyUrl = getFromCache(tinyUrlFuture);
		if(tinyUrl != null)
			return tinyUrl;
		
		TinyUrl tiny = repo.findByUrl(url);
		if(tiny != null) // url already shortened before
			return tiny.getTinyUrl();

		do {
			tinyUrl = generator.generateTinyUrl(url);
			tiny = repo.findByTinyUrl(tinyUrl);
		} while(tiny != null); // make sure that newly generated tiny url not already associated with another url in db
		
		repo.save(new TinyUrl(url, tinyUrl));
		cache.saveTinyUrl(url, tinyUrl);
		return tinyUrl;
	}

	@Override
	public String retrieveUrl(String tinyUrl) throws UrlNotFoundException {
		Future<Optional<String>> tinyUrlFuture = cache.findUrl(tinyUrl);
		String url = getFromCache(tinyUrlFuture);
		if(url != null)
			return url;
		
		TinyUrl tiny = repo.findByTinyUrl(tinyUrl);
		if(tiny != null && tiny.getUrl() != null) {
			cache.saveUrl(tiny.getUrl(), tinyUrl);
			return tiny.getUrl();
		}
		
		throw new UrlNotFoundException("No url found for: "+tinyUrl);
	}
	
	private String getFromCache(Future<Optional<String>> tinyUrlFuture) {
		Optional<String> tinyUrlOpt;
		try {
			tinyUrlOpt = tinyUrlFuture.get(CACHE_SERVICE_TIME_OUT_MS, TimeUnit.MILLISECONDS);
			if(tinyUrlOpt.isPresent())
				return tinyUrlOpt.get();
			else
				return null;
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			return null; // in case anything wrong with cache service, we go to db;
		} 
	}
}
