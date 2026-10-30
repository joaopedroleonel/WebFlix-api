package com.webflix.api.controller;

import static com.webflix.api.controller.ResponseLists.okOrNotFound;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webflix.api.service.GenreService;

@RestController
@RequestMapping("/api/Genre")
public class GenreController {

	private final GenreService genreService;

	public GenreController(GenreService genreService) {
		this.genreService = genreService;
	}

	@GetMapping
	public ResponseEntity<?> get() {
		return okOrNotFound(genreService.findAll());
	}

}
