package com.johnwu.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.johnwu.domain.TinyUrl;
import com.johnwu.repo.TinyUrlRepo;
import com.johnwu.service.TinyUrlGeneratorService;

@RunWith(MockitoJUnitRunner.class)
public class TinyUrlServiceImplTest {
	@Mock
	private TinyUrlRepo repo;
	@Mock
	private TinyUrlGeneratorService generator;
	
	@InjectMocks
	TinyUrlServiceImpl service = new TinyUrlServiceImpl(repo, generator);
	
	@Test
	public void shortenUrl_not_in_db() {
		String url = "http://google.com/search";
		String tinyUrl = "tiny";
		
		when(generator.generateTinyUrl(url)).thenReturn(tinyUrl);
		
		assertEquals(tinyUrl, service.shortenUrl(url));
		verify(repo, times(1)).save(new TinyUrl(url, tinyUrl));
	}
	
	@Test
	public void shortenUrl_in_db() {
		String url = "http://google.com/search";
		String tinyUrl = "tiny";
		
		when(repo.findByUrl(url)).thenReturn(new TinyUrl(url, tinyUrl));

		assertEquals(tinyUrl, service.shortenUrl(url));
		verify(generator, never()).generateTinyUrl(url);
		verify(repo, never()).save(null);
	}

}
