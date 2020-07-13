package com.biz.score.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.biz.score.domain.ScoreVO;

public class ScoreServiceImplV1 implements ScoreService {

	private List<ScoreVO> scoreList; // 스코어 VO를 담을 리스트
	private Scanner scan; // 파일을 읽어들이는것
	private String fileName; // 스트링형 파일의 이름

	private String[] strSubjects; // 서브젝트
	private Integer[] intScores; // 인트 스코어

	private int[] totalSum; // 총합
	private int[] totalAvg; // 평균

	public ScoreServiceImplV1() { // 기본 메서드
		scoreList = new ArrayList<ScoreVO>();
		scan = new Scanner(System.in);
		fileName = "src/com/biz/score/data/score.txt";

		strSubjects = new String[] { "국어", "영어", "수학", "음악" };
		intScores = new Integer[strSubjects.length];
		totalSum = new int[strSubjects.length];
		totalAvg = new int[strSubjects.length];
	}

	public void loadscore() {
		FileReader filereader = null;
		BufferedReader buffer = null;

		try {
			filereader = new FileReader(this.fileName);
			buffer = new BufferedReader(filereader);
			String reader = "";

			while (true) {
				reader = buffer.readLine();
				if (reader == null) {
					break;
				}
				String[] scores = reader.split(":");
				ScoreVO scoreVO = new ScoreVO();

				scoreVO.setNum(scores[0]);
				scoreVO.setKor(Integer.valueOf(scores[1]));
				scoreVO.setEng(Integer.valueOf(scores[2]));
				scoreVO.setMath(Integer.valueOf(scores[3]));
				scoreVO.setMusic(Integer.valueOf(scores[4]));
				scoreList.add(scoreVO);
			}

		} catch (Exception e) {
			System.out.println("over....");

		}

	}

	private Integer Check(String score) {

		if (score.equals("END")) {
			return -1;
		}

		Integer intScore = null;
		try {
			intScore = Integer.valueOf(score);
		} catch (Exception e) {
			System.out.println("점수는 숫자만 가능합니다.");
			System.out.println("입력한 문자열 : " + score);
			return null;
		}

		if (intScore < 0 || intScore > 100) {
			System.out.println("점수는 0 ~ 100까지만 가능합니다. 다시 입력해주세요");
			return null;
		}

		return intScore;
	}

	public boolean inputscore() {

		ScoreVO scoreVO = new ScoreVO();

		System.out.println("학번(END:종료) >>");
		String scNum = scan.nextLine();
		if (scNum.equals("END")) {
			return false;
		}

		int intNum = 0;
		try {
			intNum = Integer.valueOf(scNum);
		} catch (Exception e) {
			System.out.println("학번은 숫자만 가능");
			System.out.println("입력한 문자열");
			return true;
		}
		if (intNum < 1 || intNum > 99999) {
			System.out.println("학번은1~99999번까지 가능");
			return true;
		}
		scNum = String.format("%05d", intNum);

		for (ScoreVO sVO : scoreList) {
			if (sVO.getNum().equals(scNum)) {
				System.out.println(scNum + " 학생의 성적이 이미 등록 되어 있습니다");
				return true;
			}
		}
		scoreVO.setNum(scNum);
		for (int i = 0; i < strSubjects.length; i++) {
			System.out.printf("%s 점수 (종료 : END) >> ", strSubjects[i]);
			String score = scan.nextLine();
			Integer intScore = this.Check(score);

			if (intScore == null) {
				i--;
				continue;
			} else if (intScore < 0) {
				return false;
			}
			intScores[i] = intScore;
		}
		scoreVO.setKor(intScores[0]);
		scoreVO.setEng(intScores[1]);
		scoreVO.setMath(intScores[2]);
		scoreVO.setMusic(intScores[3]);

		scoreList.add(scoreVO);
		this.saveScoreVO(scoreVO);

		return true;

	}

	@Override
	public void calcSum() {

		for (ScoreVO scoreVO : scoreList) {
			int sum = scoreVO.getKor();
			sum += scoreVO.getEng();
			sum += scoreVO.getMath();
			sum += scoreVO.getMusic();
			sum += scoreVO.getKor();
			scoreVO.setSum(sum);
		}

	}

	@Override
	public void calcAvg() {
		for (ScoreVO scoreVO : scoreList) {
			int sum = scoreVO.getSum();
			float avg = (float) sum / 4;
			scoreVO.setAvg(avg);

		}

	}

	@Override
	public void scoreList() {
		Arrays.fill(totalSum, 0);
		Arrays.fill(totalAvg, 0);
		
		System.out.println("성적 일람표");
		System.out.println("========================================================");
		System.out.println("학번\t|국어\t|영어\t|수학\t|음악\t|총점\t|평균\t|");
		System.out.println("--------------------------------------------------------");

		for (ScoreVO sVO : scoreList) {

			System.out.printf("%s\t|", sVO.getNum());
			System.out.printf("%d\t|", sVO.getKor());
			System.out.printf("%d\t|", sVO.getEng());
			System.out.printf("%d\t|", sVO.getMath());
			System.out.printf("%d\t|", sVO.getMusic());
			System.out.printf("%d\t|", sVO.getSum());
			System.out.printf("%5.2f\t|\n", sVO.getAvg());
			
			totalSum[0] += sVO.getKor();
			totalSum[1] += sVO.getEng();
			totalSum[2] += sVO.getMath();
			totalSum[3] += sVO.getMusic();
		}
		System.out.println("--------------------------------------------------------");
		System.out.print("과목총점|");
		int sumAndSum =0;
		for(int sum : totalSum) {
			System.out.printf("%s\t|",sum);
			sumAndSum +=sum;
		}
		System.out.printf("%s\t|\n",sumAndSum);
		
		System.out.print("과목평균|");
		float avgAndAvg = 0f;
		for(int sum : totalSum) {
			float avg = (float)sum /totalSum.length;
			System.out.printf("%5.2f\t|",avg);
			avgAndAvg += avg;
		}
		System.out.printf("\t|%5.2f\t|\n",(float)avgAndAvg / scoreList.size());
		System.out.println("========================================================");
		
		

	}

	@Override
	public void savescore() {

		PrintStream peam = null;

		String saveFile = "src/com/biz/score/data/scoreList.txt";

		try {
			// fileWriter = new FileWriter(saveFile,true);
			peam = new PrintStream(saveFile);

			Arrays.fill(totalSum, 0);
			Arrays.fill(totalAvg, 0);

			peam.println("성적 일람표");
			peam.println("========================================================");
			peam.printf("학번\t|국어\t|영어\t|수학\t|음악\t|총점\t|평균\t|\n");
			peam.println("--------------------------------------------------------");

			for (ScoreVO sVO : scoreList) {

				peam.printf("%s\t\t|", sVO.getNum());
				peam.printf("%d\t\t|", sVO.getKor());
				peam.printf("%d\t\t|", sVO.getEng());
				peam.printf("%d\t\t|", sVO.getMath());
				peam.printf("%d\t\t|", sVO.getMusic());
				peam.printf("%d\t\t|", sVO.getSum());
				peam.printf("%5.2f\t\t|\n", sVO.getAvg());

				totalSum[0] += sVO.getKor();
				totalSum[1] += sVO.getEng();
				totalSum[2] += sVO.getMath();
				totalSum[3] += sVO.getMusic();
			}
			peam.println("--------------------------------------------------------");
			peam.print("과목총점|");
			int sumAndSum = 0;
			for (int sum : totalSum) {
				peam.printf("%s\t|", sum);
				sumAndSum += sum;
			}
			peam.printf("%s\t|\n", sumAndSum);

			peam.print("과목평균|");
			float lastAvg = 0f;
			for (int sum : totalSum) {
				float avg = (float) sum / totalSum.length;
				peam.printf("%5.2f\t|", avg);
				lastAvg += avg;
			}
			peam.printf("\t|%5.2f\t\t|\n", (float) lastAvg / scoreList.size());
			peam.println("========================================================");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void saveScoreVO(ScoreVO scoreVO) {

		FileWriter fileWriter = null;
		PrintWriter pWriter = null;

		try {
			fileWriter = new FileWriter(this.fileName, true);
			pWriter = new PrintWriter(fileWriter);

			pWriter.printf("%s:", scoreVO.getNum());
			pWriter.printf("%d:", scoreVO.getKor());
			pWriter.printf("%d:", scoreVO.getEng());
			pWriter.printf("%d:", scoreVO.getMath());
			pWriter.printf("%d\n", scoreVO.getMusic());
			pWriter.flush();
			pWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
