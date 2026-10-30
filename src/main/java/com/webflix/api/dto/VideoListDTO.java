package com.webflix.api.dto;

import java.util.List;

import com.webflix.api.entity.Video;

public record VideoListDTO(Integer videoID, String title, String description, String imageUrl, List<GenreDTO> genres) {

	public static VideoListDTO from(Video video) {
		return new VideoListDTO(video.getVideoID(), video.getTitle(), video.getDescription(), video.getImageUrl(),
				GenreDTO.fromAll(video.getGenres()));
	}

}
