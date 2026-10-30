package com.webflix.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webflix.api.dto.VideoListDTO;
import com.webflix.api.dto.VideoPlayerDTO;
import com.webflix.api.repository.VideoRepository;

@Service
@Transactional(readOnly = true)
public class VideoService {

	private final VideoRepository videoRepository;

	public VideoService(VideoRepository videoRepository) {
		this.videoRepository = videoRepository;
	}

	public List<VideoListDTO> listVideos() {
		return videoRepository.findAllWithGenres().stream().map(VideoListDTO::from).toList();
	}

	public List<VideoPlayerDTO> findPlayerById(int id) {
		return videoRepository.findByIdWithGenres(id).map(VideoPlayerDTO::from).stream().toList();
	}

	public List<VideoListDTO> listByGenre(int genreId) {
		return videoRepository.findAllByGenreId(genreId).stream().map(VideoListDTO::from).toList();
	}

}
