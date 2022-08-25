package com.imdb.cardgame.model;

import lombok.Data;

@Data
public class UserChoice {

	private Long idMovie1;
	private Long idMovie2;
	private Long idMovieSelected;
	
	private Double point;
	private String username;

}
