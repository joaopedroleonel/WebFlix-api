package com.webflix.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.webflix.api.dto.GenreDTO;
import com.webflix.api.dto.VideoListDTO;
import com.webflix.api.dto.VideoPlayerDTO;
import com.webflix.api.entity.Genre;
import com.webflix.api.entity.Video;
import com.webflix.api.repository.VideoRepository;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

	@Mock
	private VideoRepository videoRepository;

	@InjectMocks
	private VideoService videoService;

	private static Genre genre(int id, String name) {
		Genre genre = new Genre(name);
		genre.setGenreID(id);
		return genre;
	}

	private static Video video() {
		Video video = new Video("Filme", "Descrição", "img/filme.jpg", "video/filme.mp4");
		video.setVideoID(1);
		video.addGenre(genre(3, "Terror"));
		video.addGenre(genre(1, "Ação"));
		return video;
	}

	@Test
	void listVideosMapsToListDtoWithGenresSortedById() {
		when(videoRepository.findAllWithGenres()).thenReturn(List.of(video()));

		assertThat(videoService.listVideos()).containsExactly(new VideoListDTO(1, "Filme", "Descrição",
				"img/filme.jpg", List.of(new GenreDTO(1, "Ação"), new GenreDTO(3, "Terror"))));
	}

	@Test
	void findPlayerByIdReturnsSingleItemList() {
		when(videoRepository.findByIdWithGenres(1)).thenReturn(Optional.of(video()));

		List<VideoPlayerDTO> result = videoService.findPlayerById(1);

		assertThat(result).hasSize(1);
		assertThat(result.get(0).videoUrl()).isEqualTo("video/filme.mp4");
		assertThat(result.get(0).genres()).extracting(GenreDTO::genreID).containsExactly(1, 3);
	}

	@Test
	void findPlayerByIdReturnsEmptyListWhenMissing() {
		when(videoRepository.findByIdWithGenres(42)).thenReturn(Optional.empty());

		assertThat(videoService.findPlayerById(42)).isEmpty();
	}

	@Test
	void listByGenreDelegatesToRepository() {
		when(videoRepository.findAllByGenreId(1)).thenReturn(List.of(video()));

		assertThat(videoService.listByGenre(1)).extracting(VideoListDTO::title).containsExactly("Filme");
	}

}
