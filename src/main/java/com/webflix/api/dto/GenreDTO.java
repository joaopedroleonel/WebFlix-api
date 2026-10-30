package com.webflix.api.dto;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.webflix.api.entity.Genre;

public record GenreDTO(Integer genreID, String name) {

	public static GenreDTO from(Genre genre) {
		return new GenreDTO(genre.getGenreID(), genre.getName());
	}

	public static List<GenreDTO> fromAll(Collection<Genre> genres) {
		return genres.stream()
				.sorted(Comparator.comparing(Genre::getGenreID, Comparator.nullsLast(Comparator.naturalOrder())))
				.map(GenreDTO::from)
				.toList();
	}

}
