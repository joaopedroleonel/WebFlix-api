package com.webflix.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webflix.api.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Integer> {

	@Query("select distinct v from Video v left join fetch v.genres order by v.videoID")
	List<Video> findAllWithGenres();

	@Query("select v from Video v left join fetch v.genres where v.videoID = :id")
	Optional<Video> findByIdWithGenres(@Param("id") int id);

	@Query("""
			select distinct v from Video v left join fetch v.genres
			where v.videoID in (select v2.videoID from Video v2 join v2.genres g where g.genreID = :genreId)
			order by v.videoID""")
	List<Video> findAllByGenreId(@Param("genreId") int genreId);

}
