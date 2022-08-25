package com.imdb.gamecard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.imdb.cardgame.model.Movie;
import com.imdb.cardgame.model.Result;
import com.imdb.cardgame.model.UserChoice;
import com.imdb.cardgame.model.UserResult;

public class CardGameApplicationControllerTest extends AbstractTest {

	@Override
	@BeforeEach
	protected void setUp() {
		super.setUp();
	}

	@Test
	public void getMovieList() throws Exception {
		String uri = "/cardgame/all-movies";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		Movie[] movieList = super.mapFromJson(content, Movie[].class);
		assertTrue(movieList.length > 0);
	}

	@Test
	public void selectDifferentMovies() throws Exception {
		String uri = "/cardgame/movies-play";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		Movie[] movieList = super.mapFromJson(content, Movie[].class);
		assertTrue(movieList[0].getId() != movieList[1].getId() );
	}

	@Test
	public void getAllResults() throws Exception {
		String uri = "/cardgame/all-results";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		Result[] results = super.mapFromJson(content, Result[].class);
		assertTrue(results.length > 0);
	}
	
	@Test
	public void getAllUsersRanking() throws Exception {
		String uri = "/cardgame/users-ranking";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		UserResult[] usersRanking = super.mapFromJson(content, UserResult[].class);
		assertTrue(usersRanking.length > 0);
	}
	
	@Test
	@WithMockUser(username = "cinema", password = "123", roles = "USER")
	public void saveChoice() throws Exception {
		String uri = "/cardgame/save-user-choice";
		UserChoice choice = new UserChoice();
		choice.setIdMovie1(50L);
		choice.setIdMovie2(100L);
		choice.setIdMovieSelected(50L);
		String inputJson = super.mapToJson(choice);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders
				.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertNotNull(content);
	}
}
