package com.johnwu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.johnwu.service.CacheService;

public class CacheServiceImplTest {

	private CacheService cache;
	
	@Before
	public void setup() {
		cache = new CacheServiceImpl();
	}
	@After
	public void teardown() {
		cache = null;
	}
	
	@Test
	public void retrieve_tinyUrl_success() {
		String url = "http://google.com/search";
		String tinyUrl = "tiny";
		
		cache.saveTinyUrl(url, tinyUrl);
		assertEquals(tinyUrl, cache.findTinyUrl(url).get());
	}	

	@Test
	public void retrieve_tinyUrl_not_found() {
		assertFalse(cache.findTinyUrl("http://google.com/search").isPresent());
	}
	
	@Test
	public void retrieve_url_success() {
		String url = "http://google.com/search";
		String tinyUrl = "tiny";
		
		cache.saveUrl(url, tinyUrl);
		assertEquals(url, cache.findUrl(tinyUrl).get());
	}	

	@Test
	public void retrieve_url_not_found() {
		assertFalse(cache.findUrl("sdsd2").isPresent());
	}
}
