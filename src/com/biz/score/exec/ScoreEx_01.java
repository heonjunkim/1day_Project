package com.biz.score.exec;

import java.util.Scanner;

import com.biz.score.service.ScoreService;
import com.biz.score.service.ScoreServiceImplV1;

public class ScoreEx_01 {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		ScoreService vice = new ScoreServiceImplV1();
		vice.loadscore();

		while (true) {

			System.out.println("=============================================");
			System.out.println(">성적처리 시스템<");
			System.out.println("=============================================");
			System.out.println("1. 학생성적 등록");
			System.out.println("2. 성적일람표 출력");
			System.out.println("3. 성적파일 생성");
			System.out.println("=============================================");
			System.out.println("exit. 업무종료");
			System.out.println("=============================================");
			System.out.print("업무 선택 >> ");

			String strMenu = scan.nextLine();

			if (strMenu.equalsIgnoreCase("exit")) {
				break;
			}

			int intMenu = 0;

			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
				System.out.println("메뉴는 숫자로 선택하세요");
				continue;
			}

			if (intMenu == 1) {
				while (vice.inputscore())

					vice.calcSum();
				vice.calcAvg();

			} else if (intMenu == 2) {

				vice.calcSum();
				vice.calcAvg();
				vice.scoreList();
			} else if (intMenu == 3) {
				vice.savescore();
			}
		}

		System.out.println("종료");
	}

}
