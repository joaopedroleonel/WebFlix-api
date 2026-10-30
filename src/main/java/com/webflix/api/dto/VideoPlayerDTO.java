package com.webflix.api.dto;

import java.util.List;

import com.webflix.api.entity.Video;

public record VideoPlayerDTO(Integer videoID, String title, String description, String videoUrl, List<GenreDTO> genres) {

	public static VideoPlayerDTO from(Video video) {
		return new VideoPlayerDTO(video.getVideoID(), video.getTitle(), video.getDescription(), video.getVideoUrl(),
				GenreDTO.fromAll(video.getGenres()));
	}

}
