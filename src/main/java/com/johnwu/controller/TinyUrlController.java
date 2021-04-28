package com.johnwu.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.johnwu.domain.TinyUrl;
import com.johnwu.exception.UrlNotFoundException;
import com.johnwu.service.TinyUrlService;

@Controller
public class TinyUrlController {
	
	private TinyUrlService tinyUrlService;

	public TinyUrlController(TinyUrlService tinyUrlService) {
		this.tinyUrlService = tinyUrlService;
	}
	
	@RequestMapping("/")
    public String index(Model model) {
		model.addAttribute("url", new TinyUrl());
        return "index";
    }
	
	@PostMapping("/shortenUrl")
	public String shortenUrl(@ModelAttribute TinyUrl url, Model model) {
		url.setTinyUrl(tinyUrlService.shortenUrl(url.getUrl()));
		model.addAttribute("url", url);
		return "index";
	}
	
	@RequestMapping("/{tinyUrl}")
	void redirect(@PathVariable String tinyUrl, HttpServletResponse response) throws IOException, UrlNotFoundException {
		String url = tinyUrlService.retrieveUrl(tinyUrl);
	    response.sendRedirect(url);
	}
}
