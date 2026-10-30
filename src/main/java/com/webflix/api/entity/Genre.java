package com.webflix.api.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Genres")
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "genreID")
	private Integer genreID;

	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@ManyToMany(mappedBy = "genres")
	private Set<Video> videos = new HashSet<>();

	public Genre() {
	}

	public Genre(String name) {
		this.name = name;
	}

	public Integer getGenreID() {
		return genreID;
	}

	public void setGenreID(Integer genreID) {
		this.genreID = genreID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Video> getVideos() {
		return videos;
	}

	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}

}
