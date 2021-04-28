package com.johnwu.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.johnwu.domain.TinyUrl;
import com.johnwu.repo.TinyUrlRepo;
import com.johnwu.service.CacheService;
import com.johnwu.service.TinyUrlGeneratorService;

@RunWith(MockitoJUnitRunner.class)
public class TinyUrlServiceImplTest {
	@Mock
	private TinyUrlRepo repo;
	@Mock
	private TinyUrlGeneratorService generator;
	@Mock
	private CacheService cache;
	
	@InjectMocks
	TinyUrlServiceImpl service = new TinyUrlServiceImpl(repo, generator, cache);
	
	@Test
	public void shortenUrl_not_in_cache_db() {
		String url = "http://google.com/search";
		String tinyUrl = "tiny";
		
		when(generator.generateTinyUrl(url)).thenReturn(tinyUrl);
		
		assertEquals(tinyUrl, service.shortenUrl(url));
		verify(repo, times(1)).save(new TinyUrl(url, tinyUrl));
	}
	
	@Test
	public void shortenUrl_in_cache() {
		String url = "http://google.com/search";
		String tinyUrl = "tiny";
		
		when(cache.find(url)).thenReturn(Optional.of(tinyUrl));
		
		assertEquals(tinyUrl, service.shortenUrl(url));
		verify(repo, never()).findByUrl(null);
		verify(generator, never()).generateTinyUrl(url);
		verify(repo, never()).save(null);
	}
	
	@Test
	public void shortenUrl_in_db_not_cache() {
		String url = "http://google.com/search";
		String tinyUrl = "tiny";
		
		when(repo.findByUrl(url)).thenReturn(new TinyUrl(url, tinyUrl));

		assertEquals(tinyUrl, service.shortenUrl(url));
		verify(generator, never()).generateTinyUrl(url);
		verify(repo, never()).save(null);
	}

	
}
