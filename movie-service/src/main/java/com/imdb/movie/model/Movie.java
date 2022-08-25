package com.imdb.movie.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name="movie")
public class Movie {

	@Id
	private Long id;
	private String title;
	private String ranking;
//	
//	public Double getRanking() {
//		return Double.valueOf(ranking);
//	}
}
