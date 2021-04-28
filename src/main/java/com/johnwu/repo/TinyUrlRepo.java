package com.johnwu.repo;

import org.springframework.data.repository.CrudRepository;

import com.johnwu.domain.TinyUrl;

public interface TinyUrlRepo extends CrudRepository<TinyUrl, Long> {

	TinyUrl findByTinyUrl(String tinyUrl);
	TinyUrl findByUrl(String url);
}
