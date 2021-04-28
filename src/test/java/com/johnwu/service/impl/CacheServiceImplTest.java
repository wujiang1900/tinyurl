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
	public void retrieve_success() {
		String url = "http://google.com/search";
		String tinyUrl = "tiny";
		
		cache.save(url, tinyUrl);
		assertEquals(tinyUrl, cache.find(url).get());
	}	

	@Test
	public void retrieve_not_found() {
		assertFalse(cache.find("http://google.com/search").isPresent());
	}
}
