package com.javakc.service;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * ������Controller
 * �๦�ܣ�������
 * @author DarlinZZZ
 *
 */
public class Controller {
	PTP ptp = new PTP();
	OBB obb = new OBB();
	CSV csv = new CSV();
	AABB aabb = new AABB();
	STEP step = new STEP();
	//��ά����aabbcontact�洢������AABB��ײ��·�ζԣ�һ��������·�Σ���PathID��55����AABB��ײ��·�ζԣ�2�������·�ε�PathID�����⴫�룩
	int[][] aabbarray = new int[55][2];
	//��ά����arrays_bezi�洢��map��ĳһ��·�ε����ж�����������ݣ�1000����ĳһ·�ε����ж���������8����ĳһ������������ݣ�
	double [][] arrays_bezi = new double [1000][8];
	//��ά����arrays_bezi2�洢��map��ĳһ��·�ε����ж�����������ݣ�1000����ĳһ·�ε����ж���������8����ĳһ������������ݣ�
	double [][] arrays_bezi2 = new double [1000][8];
	
	/**
	 * ��������AABBcheck
	 * �������ܣ�ģʽ1:Map,AABB��ײ��⣬�������ά������
	 */
	public void AABBcheck() {
		aabb.aabb_contact();
		for(int abn = 0; abn<55; abn++){
			aabbarray[abn] = aabb.getaabbByNum(abn);
		}
	}
	
	/**
	 * ��������csv_inputanalysis
	 * �������ܣ�ģʽ1:Map,���AABB��ײ�����ɶ�BEZIER������ÿ��������������ݴ���������
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
	 * ��������OBBcheck
	 * �������ܣ�ģʽ1:Map,OBB��ײ���
	 * @param x_threshhold ͶӰ��x���ϵİ�ȫ��ֵ
	 * @param y_threshhold ͶӰ��y���ϵİ�ȫ��ֵ
	 * @param steps ����
	 */
	public void OBBcheck(double x_threshhold, double y_threshhold, int steps) {
		step.step(x_threshhold, y_threshhold, steps);
	}
	
	/**
	 * ��������outputcsv
	 * �������ܣ�ģʽ1:Map,��OBB��ײ�����·��������뵽�´�����csv�ļ���
	 * @param x_threshhold ͶӰ��x���ϵİ�ȫ��ֵ
	 * @param y_threshhold ͶӰ��y���ϵİ�ȫ��ֵ
	 * @param steps ����
	 */
	public void outputcsv(double x_threshhold, double y_threshhold, int steps) {
		csv.csv(x_threshhold, y_threshhold, steps);
	}
	
	/**
	 * ��������outputcsv
	 * �������ܣ�ģʽ2:PATHvsPATH,����·��������·�ν���OBB��ײ���
	 */
	public void ptp(double xyuzhi, double yyuzhi, int step, String addr1, String addr2) {
		ptp.pathtopath(xyuzhi, yyuzhi, step, addr1, addr2);
	}
	
	/**
	 * ��������outputcsv
	 * �������ܣ�ģʽ3:OBB+printimage,1�Զ��OBB��Ⲣ�һ�ͼ
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
