package com.johnwu.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TinyUrlGeneratorServiceImplTest {

	@Test
	public void generateTinyUrl_asExcpected() {
		String tinyUrl = new TinyUrlGeneratorServiceImpl().generateTinyUrl("http://add.com");
		assertEquals(TinyUrlGeneratorServiceImpl.LENGTH, tinyUrl.length());
	}
}
