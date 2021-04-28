package com.johnwu.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity (name="tinyurl")
@AllArgsConstructor
@NoArgsConstructor
public class TinyUrl {

	@Id
	@GeneratedValue
	private long id;
	
	@NonNull 
	private String url;

	private String tinyUrl;
	
	public TinyUrl(String url, String tinyUrl) {
		this.url = url;
		this.tinyUrl = tinyUrl;
	}
}
