package com.johnwu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
	public void retrieve_tinyUrl_success() throws InterruptedException, ExecutionException {
		String url = "http://google.com/search";
		String tinyUrl = "tiny";
		
		cache.saveTinyUrl(url, tinyUrl);
		assertEquals(tinyUrl, cache.findTinyUrl(url).get().get());
	}	

	@Test
	public void retrieve_tinyUrl_not_found() throws InterruptedException, ExecutionException {
		Future<Optional<String>> tinyUrlFuture = cache.findTinyUrl("http://google.com/search");
		assertFalse(tinyUrlFuture.get().isPresent());
	}
	
	@Test
	public void retrieve_url_success() throws InterruptedException, ExecutionException {
		String url = "http://google.com/search";
		String tinyUrl = "tiny";
		
		cache.saveUrl(url, tinyUrl);
		assertEquals(url, cache.findUrl(tinyUrl).get().get());
	}	

	@Test
	public void retrieve_url_not_found() throws InterruptedException, ExecutionException {
		Future<Optional<String>> tinyUrlFuture = cache.findUrl("sdsd2");
		assertFalse(tinyUrlFuture.get().isPresent());
	}
}
