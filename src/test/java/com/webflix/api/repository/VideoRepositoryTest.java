package com.webflix.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import com.webflix.api.entity.Genre;
import com.webflix.api.entity.Video;

@DataJpaTest
class VideoRepositoryTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private VideoRepository videoRepository;

	private Genre action;
	private Genre comedy;
	private Video movie;
	private Video sitcom;

	@BeforeEach
	void setUp() {
		action = em.persist(new Genre("Ação"));
		comedy = em.persist(new Genre("Comédia"));

		movie = new Video("Filme", "Ação e comédia", "img/filme.jpg", "video/filme.mp4");
		movie.addGenre(action);
		movie.addGenre(comedy);
		em.persist(movie);

		sitcom = new Video("Sitcom", null, "img/sitcom.jpg", "video/sitcom.mp4");
		sitcom.addGenre(comedy);
		em.persist(sitcom);

		em.persist(new Video("Sem gênero", null, "img/x.jpg", "video/x.mp4"));

		em.flush();
		em.clear();
	}

	@Test
	void findAllWithGenresReturnsEveryVideoWithItsGenres() {
		List<Video> videos = videoRepository.findAllWithGenres();

		assertThat(videos).extracting(Video::getTitle).containsExactly("Filme", "Sitcom", "Sem gênero");
		assertThat(videos.get(0).getGenres()).extracting(Genre::getName).containsExactlyInAnyOrder("Ação", "Comédia");
		assertThat(videos.get(2).getGenres()).isEmpty();
	}

	@Test
	void findByIdWithGenresReturnsTheVideo() {
		Video found = videoRepository.findByIdWithGenres(movie.getVideoID()).orElseThrow();

		assertThat(found.getTitle()).isEqualTo("Filme");
		assertThat(found.getGenres()).hasSize(2);
	}

	@Test
	void findByIdWithGenresReturnsEmptyWhenMissing() {
		assertThat(videoRepository.findByIdWithGenres(9999)).isEmpty();
	}

	@Test
	void findAllByGenreIdKeepsAllGenresOfEachVideo() {
		List<Video> videos = videoRepository.findAllByGenreId(action.getGenreID());

		assertThat(videos).extracting(Video::getTitle).containsExactly("Filme");
		assertThat(videos.get(0).getGenres()).extracting(Genre::getName).containsExactlyInAnyOrder("Ação", "Comédia");
	}

	@Test
	void findAllByGenreIdReturnsEveryVideoOfTheGenre() {
		assertThat(videoRepository.findAllByGenreId(comedy.getGenreID())).extracting(Video::getTitle)
				.containsExactly("Filme", "Sitcom");
	}

	@Test
	void findAllByGenreIdReturnsEmptyForUnknownGenre() {
		assertThat(videoRepository.findAllByGenreId(0)).isEmpty();
	}

}
