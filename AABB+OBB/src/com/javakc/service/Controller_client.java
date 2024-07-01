package com.javakc.service;

import java.util.Scanner;

/**
 * 类名：Controller_client
 * 类的功能：用户的控制器，控制Controller类
 * @author DarlinZZZ
 *
 */
public class Controller_client {
	Controller control = new Controller();
	
	/**
	 * 方法名：controller_client
	 * 方法功能：实现用户的控制器（共三个模式，若干个功能）
	 */
	public void controller_client() {
		try (Scanner in = new Scanner(System.in)) {
			System.out.println("Which Mode (1 = Map / 2 = PATHvsPATH / 3 = OBB+printimage) ?");
			int Mode = in.nextInt();
			if (Mode == 1) {
				System.out.println("Which function (1 = AABB check / 2 = csv input&analysis / 3 = OBB check / 4 = output csv) ?");
				int function1 = in.nextInt();
				if (function1 == 1) {
					control.AABBcheck();
				}
				else if (function1 == 2) {
					control.csv_inputanalysis();
				}
				
				else if (function1 == 3) {
					System.out.println("x_threshhold = ? (more accurate if the value is smaller)");
					double x_threshhold = in.nextDouble();
					System.out.println("y_threshhold = ? (more accurate if the value is smaller)");
					double y_threshhold = in.nextDouble();
					System.out.println("step = ? (more accurate if the value is smaller)");
					int step = in.nextInt();
					control.OBBcheck(x_threshhold, y_threshhold, step);
				}
				
				else if (function1 == 4) {
					System.out.println("x_threshhold = ? (more accurate if the value is smaller)");
					double x_threshhold = in.nextDouble();
					System.out.println("y_threshhold = ? (more accurate if the value is smaller)");
					double y_threshhold = in.nextDouble();
					System.out.println("step = ? (more accurate if the value is smaller)");
					int step = in.nextInt();
					control.outputcsv(x_threshhold, y_threshhold, step);
				}
				
				else {
					System.out.println("There is no such function!");
				}
			}
			
			else if (Mode == 2) {
				System.out.println("x_threshhold = ? (more accurate if the value is smaller)");
				double x_threshhold = in.nextDouble();
				System.out.println("y_threshhold = ? (more accurate if the value is smaller)");
				double y_threshhold = in.nextDouble();
				System.out.println("step = ? (more accurate if the value is smaller)");
				int step = in.nextInt();
				String temp = in.nextLine();
				System.out.println(temp);
				System.out.println("bezi address1 (example:/Users/13916/Desktop/bezi1.csv)?");
				String address1 = in.nextLine();
				System.out.println("bezi address2 (example:/Users/13916/Desktop/bezi2.csv)?");
				String address2 = in.nextLine();
//				System.out.println("********************");
//				System.out.println(address1);
//				System.out.println("********************");
				control.ptp(x_threshhold, y_threshhold, step, address1, address2);
			}
			
			else if (Mode == 3) {
				control.OBBimage();
			}
			
			else {
				System.out.println("There is no such Mode!");
			}
		}
	}
}
