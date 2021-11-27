package com.example.biz;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.movie.service.MovieService;

@SpringBootTest
class MovieCafeWebAppApplicationTests {

	@Autowired
	private MovieService movieService;
	
	@Test
	public void test1() {
		assertNotNull(this.movieService);
	}
	
	@Test
	public void test2() {
		System.out.println(this.movieService.retrieveMovieDetail(1).toString());
	}

}