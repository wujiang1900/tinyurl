package com.johnwu.service.impl;

import org.springframework.stereotype.Service;

import com.johnwu.domain.TinyUrl;
import com.johnwu.repo.TinyUrlRepo;
import com.johnwu.service.TinyUrlGeneratorService;
import com.johnwu.service.TinyUrlService;

@Service
public class TinyUrlServiceImpl implements TinyUrlService {
	private TinyUrlRepo repo;
	private TinyUrlGeneratorService generator;
	
	public TinyUrlServiceImpl(TinyUrlRepo repo, TinyUrlGeneratorService generator) {
		this.repo = repo;
		this.generator = generator;
	}
	
	public String shortenUrl(String url) {
		TinyUrl tiny = repo.findByUrl(url);
		if(tiny != null) // url already shortened before
			return tiny.getTinyUrl();

		String tinyUrl = null;
		do {
			tinyUrl = generator.generateTinyUrl(url);
			tiny = repo.findByTinyUrl(tinyUrl);
		} while(tiny != null); // make sure that newly generated tiny url not already associated with another url in db
		
		repo.save(new TinyUrl(url, tinyUrl));
		return tinyUrl;
	}
}
