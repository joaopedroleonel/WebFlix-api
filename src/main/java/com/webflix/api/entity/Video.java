package com.webflix.api.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Videos")
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "videoID")
	private Integer videoID;

	@Column(name = "title", length = 80, nullable = false)
	private String title;

	@Column(name = "description", length = 250)
	private String description;

	@Column(name = "imageUrl", length = 250, nullable = false)
	private String imageUrl;

	@Column(name = "videoUrl", length = 250, nullable = false)
	private String videoUrl;

	@ManyToMany
	@JoinTable(name = "GenreVideo",
			joinColumns = @JoinColumn(name = "VideosvideoID"),
			inverseJoinColumns = @JoinColumn(name = "GenresgenreID"))
	private Set<Genre> genres = new HashSet<>();

	public Video() {
	}

	public Video(String title, String description, String imageUrl, String videoUrl) {
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
		this.videoUrl = videoUrl;
	}

	public void addGenre(Genre genre) {
		genres.add(genre);
		genre.getVideos().add(this);
	}

	public Integer getVideoID() {
		return videoID;
	}

	public void setVideoID(Integer videoID) {
		this.videoID = videoID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

}
