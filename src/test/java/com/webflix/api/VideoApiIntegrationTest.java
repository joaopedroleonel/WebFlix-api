package com.webflix.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.webflix.api.entity.Genre;
import com.webflix.api.entity.Video;
import com.webflix.api.repository.GenreRepository;
import com.webflix.api.repository.VideoRepository;

@SpringBootTest
@AutoConfigureMockMvc
class VideoApiIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private VideoRepository videoRepository;

	private Genre drama;
	private Video video;

	@BeforeEach
	void setUp() {
		drama = genreRepository.save(new Genre("Drama"));
		Genre action = genreRepository.save(new Genre("Ação"));
		video = new Video("Filme", "Descrição", "img/filme.jpg", "video/filme.mp4");
		video.addGenre(drama);
		video.addGenre(action);
		video = videoRepository.save(video);
	}

	@AfterEach
	void tearDown() {
		videoRepository.deleteAll();
		genreRepository.deleteAll();
	}

	@Test
	void listsGenres() throws Exception {
		mockMvc.perform(get("/api/Genre"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].name").value("Drama"));
	}

	@Test
	void listsVideosWithGenres() throws Exception {
		mockMvc.perform(get("/api/Video/videoList"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].videoID").value(video.getVideoID()))
				.andExpect(jsonPath("$[0].genres.length()").value(2));
	}

	@Test
	void returnsVideoPlayerAsArray() throws Exception {
		mockMvc.perform(get("/api/Video/" + video.getVideoID()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].videoUrl").value("video/filme.mp4"));
	}

	@Test
	void filtersByGenreKeepingAllGenres() throws Exception {
		mockMvc.perform(get("/api/Video/genre").param("genreId", drama.getGenreID().toString()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].genres.length()").value(2));
	}

	@Test
	void returnsNotFoundForUnknownVideo() throws Exception {
		mockMvc.perform(get("/api/Video/999999")).andExpect(status().isNotFound());
	}

}
