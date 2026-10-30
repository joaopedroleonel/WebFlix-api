package com.webflix.api.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webflix.api.dto.GenreDTO;
import com.webflix.api.repository.GenreRepository;

@Service
@Transactional(readOnly = true)
public class GenreService {

	private final GenreRepository genreRepository;

	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	public List<GenreDTO> findAll() {
		return genreRepository.findAll(Sort.by("genreID")).stream().map(GenreDTO::from).toList();
	}

}
