package com.imdb.result.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imdb.result.model.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, String>{

}
