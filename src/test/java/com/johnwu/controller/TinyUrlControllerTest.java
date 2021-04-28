package com.johnwu.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import com.johnwu.domain.TinyUrl;
import com.johnwu.exception.UrlNotFoundException;
import com.johnwu.service.TinyUrlService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(TinyUrlController.class)
public class TinyUrlControllerTest {
	@Mock
	Model model;
	@MockBean
	TinyUrlService service;
	
    @InjectMocks
	TinyUrlController controller = new TinyUrlController(service);

    @Autowired
    MockMvc mockMvc;
    
    @BeforeEach
    public void setUp() {
        
    }

    @AfterEach
    void tearDown() {
        reset(service);
    }

    @DisplayName("Test Proper View name is returned for index page")
    @Test
    public void index() {
        assertEquals("index", controller.index(model), "Wrong View Returned");
    }
    
    @DisplayName("Test Proper mapping of root to index page")
    @Test
    public void root_mapping() throws Exception {
    	mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("url", hasProperty("url", nullValue())))
        .andExpect(model().attribute("url", hasProperty("tinyUrl", nullValue())));
    }
    
    @DisplayName("POST /shortenUrl should add shortened url to the model")
    @Test
    public void shortenUrl() throws Exception {
    	String tinyUrl="1ls23";
    	given(service.shortenUrl(null)).willReturn(tinyUrl);
    	
    	mockMvc.perform(post("/shortenUrl"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("url", hasProperty("tinyUrl", is(tinyUrl))));
    }

    @DisplayName("Test redirect success")
    @Test
    public void redirect() throws Exception {
    	String tinyUrl="1ls23";
		String url = "http://google.com/search";
    	given(service.retrieveUrl(tinyUrl)).willReturn(url);
    	
    	mockMvc.perform(get("/{tinyUrl}", tinyUrl))
        .andExpect(status().is3xxRedirection());
    }
    
}
