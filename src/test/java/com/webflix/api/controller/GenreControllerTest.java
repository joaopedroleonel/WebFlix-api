package com.webflix.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.webflix.api.dto.GenreDTO;
import com.webflix.api.service.GenreService;

@WebMvcTest(GenreController.class)
class GenreControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private GenreService genreService;

	@Test
	void returnsGenresWithOriginalJsonKeys() throws Exception {
		when(genreService.findAll()).thenReturn(List.of(new GenreDTO(1, "Ação")));

		mockMvc.perform(get("/api/Genre"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].genreID").value(1))
				.andExpect(jsonPath("$[0].name").value("Ação"));
	}

	@Test
	void returnsNotFoundWhenThereAreNoGenres() throws Exception {
		when(genreService.findAll()).thenReturn(List.of());

		mockMvc.perform(get("/api/Genre"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404));
	}

	@Test
	void routeIsCaseInsensitive() throws Exception {
		when(genreService.findAll()).thenReturn(List.of(new GenreDTO(1, "Ação")));

		mockMvc.perform(get("/api/genre")).andExpect(status().isOk());
	}

	@Test
	void allowsAnyOrigin() throws Exception {
		when(genreService.findAll()).thenReturn(List.of(new GenreDTO(1, "Ação")));

		mockMvc.perform(get("/api/Genre").header("Origin", "http://localhost:4200"))
				.andExpect(status().isOk())
				.andExpect(header().string("Access-Control-Allow-Origin", "*"));
	}

}
