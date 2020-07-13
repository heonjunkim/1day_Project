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
			System.out.println(">����ó�� �ý���<");
			System.out.println("=============================================");
			System.out.println("1. �л����� ���");
			System.out.println("2. �����϶�ǥ ���");
			System.out.println("3. �������� ����");
			System.out.println("=============================================");
			System.out.println("exit. ��������");
			System.out.println("=============================================");
			System.out.print("���� ���� >> ");

			String strMenu = scan.nextLine();

			if (strMenu.equalsIgnoreCase("exit")) {
				break;
			}

			int intMenu = 0;

			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
				System.out.println("�޴��� ���ڷ� �����ϼ���");
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

		System.out.println("����");
	}

}
