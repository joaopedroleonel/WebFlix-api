package com.webflix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webflix.api.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

}
