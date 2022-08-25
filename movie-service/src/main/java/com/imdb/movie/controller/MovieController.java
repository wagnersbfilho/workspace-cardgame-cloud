package com.imdb.movie.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imdb.movie.exception.MovieNotFoundException;
import com.imdb.movie.model.Movie;
import com.imdb.movie.model.ParameterizedMovie;
import com.imdb.movie.repository.MovieRepository;

@RestController
@RequestMapping({ "/movie" })
public class MovieController {

	@Autowired
	private MovieRepository movieRepo;

	/**
	 * @return
	 */
	@GetMapping
	public ParameterizedMovie findAll() {
		List<Movie> movies = movieRepo.findAll();
		ParameterizedMovie paramMovie = new ParameterizedMovie();
		paramMovie.setMovies(movies);
		return paramMovie;
	}

	/**
	 * @param id
	 * @return
	 * @throws MovieNotFoundException
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable(value="id") Long id) 
		throws MovieNotFoundException {
		
		Movie movie = movieRepo.findById(id)
				.orElseThrow(()-> new MovieNotFoundException("Movie not found: " + id));
		return ResponseEntity.ok().body(movie);
	}
	
	/**
	 * @return
	 */
	@GetMapping("/two-different-movies")
	public ParameterizedMovie findTwoDifferenteMovies() {

		List<Movie> allMovies = movieRepo.findAll();
		Collections.shuffle(allMovies);
		List<Movie> selectedMovies = allMovies.subList(0, 2);
		
		ParameterizedMovie paramMovie = new ParameterizedMovie();
		paramMovie.setMovies(selectedMovies);
		return paramMovie;
	}
	
}