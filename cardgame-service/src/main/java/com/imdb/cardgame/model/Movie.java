package com.imdb.cardgame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Movie {

	private Long id;
	private String title;
	private String ranking;
	
	public Double getRanking() {
		return Double.valueOf(ranking);
	}
}
