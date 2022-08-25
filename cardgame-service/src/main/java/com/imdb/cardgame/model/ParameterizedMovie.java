package com.imdb.cardgame.model;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ParameterizedMovie {

	private List<Movie> movies;
}
