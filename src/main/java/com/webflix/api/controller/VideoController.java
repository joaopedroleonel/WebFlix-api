package com.webflix.api.controller;

import static com.webflix.api.controller.ResponseLists.okOrNotFound;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webflix.api.service.VideoService;

@RestController
@RequestMapping("/api/Video")
public class VideoController {

	private final VideoService videoService;

	public VideoController(VideoService videoService) {
		this.videoService = videoService;
	}

	@GetMapping("/videoList")
	public ResponseEntity<?> getVideosList() {
		return okOrNotFound(videoService.listVideos());
	}

	@GetMapping("/{id:-?\\d+}")
	public ResponseEntity<?> getVideoPlayer(@PathVariable int id) {
		return okOrNotFound(videoService.findPlayerById(id));
	}

	@GetMapping("/genre")
	public ResponseEntity<?> getVideosListByGenre(@RequestParam(defaultValue = "0") int genreId) {
		return okOrNotFound(videoService.listByGenre(genreId));
	}

}
