package com.johnwu.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TinyUrlController.class)
public class TinyUrlControllerTest {
    @InjectMocks
	TinyUrlController controller;

    @Autowired
    MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        
    }

    @DisplayName("Test Proper View name is returned for index page")
    @Test
    void index() {
        assertEquals("index.html", controller.index(), "Wrong View Returned");
    }
    
    @DisplayName("Test Proper mapping of root to index page")
    @Test
    public void root_mapping() throws Exception {
    	mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index.html"))
        .andExpect(forwardedUrl("index.html"));
    }
}
