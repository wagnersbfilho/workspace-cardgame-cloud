package com.imdb.cardgame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.imdb.cardgame.model.Movie;
import com.imdb.cardgame.model.ParameterizedMovie;
import com.imdb.cardgame.model.ParameterizedResult;
import com.imdb.cardgame.model.Result;
import com.imdb.cardgame.model.UserChoice;
import com.imdb.cardgame.model.UserResult;
import com.imdb.cardgame.model.exception.MovieNotFoundException;

@RestController
@RequestMapping({ "/cardgame" })
public class CardGameController {

	@Autowired 
	private RestTemplate restTemplate;
	
	/**
	 * @param id
	 * @return
	 * @throws MovieNotFoundException
	 */
	@GetMapping("/movie/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable(value="id") Long id) {
		
		try {
			
			Movie movie = restTemplate.getForObject(
					"http://movie-service/movie/" + id, 
					Movie.class);
			return ResponseEntity.ok().body(movie);
		
		} catch (Exception exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Not Found");
		}
	}
	
	/**
	 * @return
	 */
	@GetMapping("/all-movies")
	public List<Movie> findAllMovies() {
		ParameterizedMovie movieParam = 
				restTemplate.getForObject("http://movie-service/movie",
				ParameterizedMovie.class);
		return movieParam.getMovies();
	}


	/**
	 * @return
	 */
	@GetMapping("/movies-play")
	public List<Movie> findDifferenteMoviesToPlay() {

		ParameterizedMovie movieParam = 
				restTemplate.getForObject("http://movie-service/movie/two-different-movies",
				ParameterizedMovie.class);
		return movieParam.getMovies();
	}
	
	/**
	 * @return
	 */
	@GetMapping("/all-results")
	public List<Result> findAllResults() {
		ParameterizedResult resultParam = 
				restTemplate.getForObject("http://result-service/result/all-results", 
				ParameterizedResult.class);
		return resultParam.getResults();
	}
	
	/**
	 * @return
	 */
	@GetMapping("/users-ranking")
	public List<UserResult> getusersRanking() {
		
		ParameterizedResult resultParam = 
				restTemplate.getForObject("http://result-service/result/users-ranking", 
				ParameterizedResult.class);
		return resultParam.getUserResults();
	}
	
	/**
	 * @param userChoice
	 * @return
	 * @throws MovieNotFoundException
	 */
	@PostMapping("/save-user-choice")
    public ResponseEntity<String> saveChoices(@RequestBody UserChoice userChoice) 
    		throws MovieNotFoundException{

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		String message = "";
		try {
			Movie movie1 = restTemplate.getForObject(
					"http://movie-service/movie/" + userChoice.getIdMovie1(), 
					Movie.class);
			Movie movie2 = restTemplate.getForObject(
					"http://movie-service/movie/" + userChoice.getIdMovie2(), 
					Movie.class);
			
			Movie movieBestAvaliation = movie1;
			if (movie2.getRanking() > movie1.getRanking()) {
				movieBestAvaliation = movie2;
			}
			
			Double point = 0d;
			if (movieBestAvaliation.getId().equals(userChoice.getIdMovieSelected()) ) {
				message = "Correct!";
				point = 1d;
			} else {
				message = "Wrong!";
			}
			
			userChoice.setUsername(currentPrincipalName);
			userChoice.setPoint(point);

			restTemplate.postForEntity(
					"http://result-service/result/save-user-choice", 
					userChoice, null);
			
		} catch (Exception exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Not Found");
		}
		
		return new ResponseEntity<>(currentPrincipalName.concat(", the answer is " + message), HttpStatus.OK);
    }
}