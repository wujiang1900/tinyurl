package com.johnwu.service.impl;

import java.util.Optional;

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
		Optional<String> tinyUrlOpt = cache.findTinyUrl(url);
		if(tinyUrlOpt.isPresent())
			return tinyUrlOpt.get();
			
		TinyUrl tiny = repo.findByUrl(url);
		if(tiny != null) // url already shortened before
			return tiny.getTinyUrl();

		String tinyUrl;
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
		Optional<String> urlOpt = cache.findUrl(tinyUrl);
		if(urlOpt.isPresent())
			return urlOpt.get();
		
		TinyUrl url = repo.findByTinyUrl(tinyUrl);
		if(url != null && url.getUrl() != null) {
			cache.saveUrl(url.getUrl(), tinyUrl);
			return url.getUrl();
		}
		
		throw new UrlNotFoundException("No url found for: "+tinyUrl);
	}
}
