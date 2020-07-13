package com.biz.score.service;

import com.biz.score.domain.ScoreVO;

public interface ScoreService {
	public void loadscore();
	public boolean inputscore();
	
	public void saveScoreVO(ScoreVO scoreVO);
	public void calcSum();
	public void calcAvg();
	public void scoreList();
	public void savescore();
	

}
