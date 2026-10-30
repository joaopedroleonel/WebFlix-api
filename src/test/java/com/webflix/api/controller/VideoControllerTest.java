package com.webflix.api.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.webflix.api.dto.GenreDTO;
import com.webflix.api.dto.VideoListDTO;
import com.webflix.api.dto.VideoPlayerDTO;
import com.webflix.api.service.VideoService;

@WebMvcTest(VideoController.class)
class VideoControllerTest {

	private static final List<GenreDTO> GENRES = List.of(new GenreDTO(1, "Ação"));

	private static final VideoListDTO LIST_ITEM = new VideoListDTO(1, "Filme", "Descrição", "img/filme.jpg", GENRES);

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private VideoService videoService;

	@Test
	void videoListReturnsOriginalJsonKeys() throws Exception {
		when(videoService.listVideos()).thenReturn(List.of(LIST_ITEM));

		mockMvc.perform(get("/api/Video/videoList"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].videoID").value(1))
				.andExpect(jsonPath("$[0].title").value("Filme"))
				.andExpect(jsonPath("$[0].description").value("Descrição"))
				.andExpect(jsonPath("$[0].imageUrl").value("img/filme.jpg"))
				.andExpect(jsonPath("$[0].videoUrl").doesNotExist())
				.andExpect(jsonPath("$[0].genres[0].genreID").value(1))
				.andExpect(jsonPath("$[0].genres[0].name").value("Ação"));
	}

	@Test
	void videoListReturnsNotFoundWhenEmpty() throws Exception {
		when(videoService.listVideos()).thenReturn(List.of());

		mockMvc.perform(get("/api/Video/videoList")).andExpect(status().isNotFound());
	}

	@Test
	void videoListRouteIsCaseInsensitive() throws Exception {
		when(videoService.listVideos()).thenReturn(List.of(LIST_ITEM));

		mockMvc.perform(get("/api/video/videolist")).andExpect(status().isOk());
	}

	@Test
	void videoPlayerReturnsArrayWithSingleItem() throws Exception {
		when(videoService.findPlayerById(1))
				.thenReturn(List.of(new VideoPlayerDTO(1, "Filme", "Descrição", "video/filme.mp4", GENRES)));

		mockMvc.perform(get("/api/Video/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].videoUrl").value("video/filme.mp4"))
				.andExpect(jsonPath("$[0].imageUrl").doesNotExist());
	}

	@Test
	void videoPlayerReturnsNotFoundWhenMissing() throws Exception {
		when(videoService.findPlayerById(99)).thenReturn(List.of());

		mockMvc.perform(get("/api/Video/99")).andExpect(status().isNotFound());
	}

	@Test
	void videoPlayerRejectsNonNumericId() throws Exception {
		mockMvc.perform(get("/api/Video/abc")).andExpect(status().isNotFound());

		verify(videoService, never()).findPlayerById(anyInt());
	}

	@Test
	void byGenreFiltersByQueryParameter() throws Exception {
		when(videoService.listByGenre(1)).thenReturn(List.of(LIST_ITEM));

		mockMvc.perform(get("/api/Video/genre").param("genreId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].videoID").value(1));
	}

	@Test
	void byGenreWithoutParameterUsesZeroLikeDotNet() throws Exception {
		when(videoService.listByGenre(0)).thenReturn(List.of());

		mockMvc.perform(get("/api/Video/genre")).andExpect(status().isNotFound());

		verify(videoService).listByGenre(0);
	}

	@Test
	void byGenreRejectsNonNumericParameter() throws Exception {
		mockMvc.perform(get("/api/Video/genre").param("genreId", "abc")).andExpect(status().isBadRequest());
	}

}
