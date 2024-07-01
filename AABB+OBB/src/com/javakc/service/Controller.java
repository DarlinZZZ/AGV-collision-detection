package com.javakc.service;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * 类名：Controller
 * 类功能：控制器
 * @author DarlinZZZ
 *
 */
public class Controller {
	PTP ptp = new PTP();
	OBB obb = new OBB();
	CSV csv = new CSV();
	AABB aabb = new AABB();
	STEP step = new STEP();
	//二维数组aabbcontact存储了所有AABB碰撞的路段对（一对有两条路段）的PathID（55代表AABB碰撞的路段对，2代表这对路段的PathID）（外传入）
	int[][] aabbarray = new int[55][2];
	//二维数组arrays_bezi存储了map中某一条路段的所有顶点的坐标数据（1000代表某一路段的所有顶点数量，8代表某一顶点的坐标数据）
	double [][] arrays_bezi = new double [1000][8];
	//二维数组arrays_bezi2存储了map中某一条路段的所有顶点的坐标数据（1000代表某一路段的所有顶点数量，8代表某一顶点的坐标数据）
	double [][] arrays_bezi2 = new double [1000][8];
	
	/**
	 * 方法名：AABBcheck
	 * 方法功能：模式1:Map,AABB碰撞检测，并存入二维数组中
	 */
	public void AABBcheck() {
		aabb.aabb_contact();
		for(int abn = 0; abn<55; abn++){
			aabbarray[abn] = aabb.getaabbByNum(abn);
		}
	}
	
	/**
	 * 方法名：csv_inputanalysis
	 * 方法功能：模式1:Map,针对AABB碰撞的若干对BEZIER，将其每个顶点的坐标数据存入数组中
	 */
	public void csv_inputanalysis(){
		aabb.aabb_contact();
		for(int abn = 0; abn<55; abn++){
			aabbarray[abn] = aabb.getaabbByNum(abn);
		}
		int a = 0;
		for(a=0;a<55;a++) {
			if(aabbarray[a][0]!=0 && aabbarray[a][1] != 0) {
				step.step_input(a,aabbarray[a][0]-1,aabbarray[a][1]-1);
			}
		}
		for(int abn = 0; abn<1000; abn++){
			arrays_bezi[abn] = step.getarrays_bezi(abn);
			arrays_bezi2[abn] = step.getarrays_bezi2(abn);
		}
		step.printout1();
		step.printout2();	
	}
	
	/**
	 * 方法名：OBBcheck
	 * 方法功能：模式1:Map,OBB碰撞检测
	 * @param x_threshhold 投影在x轴上的安全阈值
	 * @param y_threshhold 投影在y轴上的安全阈值
	 * @param steps 步长
	 */
	public void OBBcheck(double x_threshhold, double y_threshhold, int steps) {
		step.step(x_threshhold, y_threshhold, steps);
	}
	
	/**
	 * 方法名：outputcsv
	 * 方法功能：模式1:Map,将OBB碰撞检测后的路段情况输入到新创建的csv文件中
	 * @param x_threshhold 投影在x轴上的安全阈值
	 * @param y_threshhold 投影在y轴上的安全阈值
	 * @param steps 步长
	 */
	public void outputcsv(double x_threshhold, double y_threshhold, int steps) {
		csv.csv(x_threshhold, y_threshhold, steps);
	}
	
	/**
	 * 方法名：outputcsv
	 * 方法功能：模式2:PATHvsPATH,输入路径后两个路段进行OBB碰撞检测
	 */
	public void ptp(double xyuzhi, double yyuzhi, int step, String addr1, String addr2) {
		ptp.pathtopath(xyuzhi, yyuzhi, step, addr1, addr2);
	}
	
	/**
	 * 方法名：outputcsv
	 * 方法功能：模式3:OBB+printimage,1对多的OBB检测并且画图
	 */
	public void OBBimage() {
		 TestFrame frame = new TestFrame();
	     TestPanel panel = new TestPanel();
	     frame.setBounds(50, 50, 1800, 1800);
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.add(panel,BorderLayout.CENTER);
	     frame.setVisible(true);
	     panel.obbprintimage();
	}
	

}
