package com.webflix.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.webflix.api.dto.GenreDTO;
import com.webflix.api.entity.Genre;
import com.webflix.api.repository.GenreRepository;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

	@Mock
	private GenreRepository genreRepository;

	@InjectMocks
	private GenreService genreService;

	@Test
	void findAllMapsGenresToDto() {
		Genre action = new Genre("Ação");
		action.setGenreID(1);
		when(genreRepository.findAll(Sort.by("genreID"))).thenReturn(List.of(action));

		assertThat(genreService.findAll()).containsExactly(new GenreDTO(1, "Ação"));
	}

	@Test
	void findAllReturnsEmptyListWhenThereAreNoGenres() {
		when(genreRepository.findAll(Sort.by("genreID"))).thenReturn(List.of());

		assertThat(genreService.findAll()).isEmpty();
	}

}
