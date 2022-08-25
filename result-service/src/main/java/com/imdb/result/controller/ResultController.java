package com.imdb.result.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imdb.result.model.ParameterizedResult;
import com.imdb.result.model.Result;
import com.imdb.result.model.UserChoice;
import com.imdb.result.model.UserResult;
import com.imdb.result.repository.ResultRepository;

@RestController
@RequestMapping({ "/result" })
public class ResultController {

	@Autowired
	private ResultRepository resultRepo;

	
	/**
	 * @return
	 */
	@GetMapping("/all-results")
	public ParameterizedResult findAllResults() {
		List<Result> results = resultRepo.findAll();
		ParameterizedResult resultParam = new ParameterizedResult();
		resultParam.setResults(results);
		return resultParam;
	}
	
	/**
	 * @return
	 */
	@GetMapping("/users-ranking")
	public ParameterizedResult getusersRanking() {
		
		List<UserResult> listResults = new ArrayList<UserResult>();
		List<Result> results = resultRepo.findAll();
		
		results.stream().forEach(result -> {
			UserResult dto = new UserResult();
			Double correctPercentage = (result.getPoints() / result.getQuizzAmount());
			Double finalResult = result.getQuizzAmount() * correctPercentage;
			dto.setFinalResult(String.format("%.2f", finalResult));
			dto.setUsername(result.getUsername());
			listResults.add(dto);
		});
		
		listResults.stream().sorted(Comparator.comparing(UserResult::getFinalResult).reversed());
		
		ParameterizedResult resultParam = new ParameterizedResult();
		resultParam.setUserResults(listResults);
		return resultParam;
	}
	
	/**
	 * @param userChoice
	 * @return
	 * @throws MovieNotFoundException
	 */
	@PostMapping("/save-user-choice")
    public void saveChoice(@RequestBody UserChoice userChoice)  {

		Result result =  resultRepo
				.findById(userChoice.getUsername())
				.orElse(new Result());
		
		if (result.getUsername() == null || result.getUsername().isEmpty()) {
			result.setUsername(userChoice.getUsername());
			result.setPoints(userChoice.getPoint());
			result.setQuizzAmount(1d);
		} else {
			result.setPoints(result.getPoints() + userChoice.getPoint());
			result.setQuizzAmount(result.getQuizzAmount() + 1);
		}
		
		resultRepo.save(result);
    }
}