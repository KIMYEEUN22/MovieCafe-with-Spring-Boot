package com.example.dao.movie;

import java.util.ArrayList;

import com.example.domain.movie.GuanramListVO;
import com.example.domain.movie.MovieGuanramVO;

public class GuanramDaoImpl implements GuanramDao {

	@Override
	public ArrayList<GuanramListVO> selectGuanramList(int movieNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertGuanram(MovieGuanramVO guanram) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareUserId(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteGuanram(int movieNo, String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMovieGuanram(int movieNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void upLikecount(int movieNo, String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public int selectGuanramLike(int movieNo, String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
