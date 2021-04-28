package com.johnwu.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.johnwu.service.TinyUrlGeneratorService;

@Service
public class TinyUrlGeneratorServiceImpl implements TinyUrlGeneratorService {

	
	protected static final int LENGTH = 5;
	private static final boolean HAS_NUMBERS = true;
	private static final boolean USE_LETTERS = true;

	@Override
	public String generateTinyUrl(String url) {
		
		return RandomStringUtils.random(LENGTH, USE_LETTERS, HAS_NUMBERS);
	}

}
