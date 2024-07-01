package com.javakc.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * ������AABB
 * ��Ĺ��ܣ�AABB��ײ���
 * @author DarlinZZZ
 *
 */
public class AABB {
	//��ά����array�洢������·�ε��������ҵļ�ֵ���꣨20����·��������8����·�ε��������ҵļ�ֵ���꣩
	int[][] array = new int[20][8];
	//��ά����aabbcontact�洢������AABB��ײ��·�ζԣ�һ��������·�Σ���PathID��55����AABB��ײ��·�ζԣ�2�������·�ε�PathID��
	int[][] aabbcontact = new int[55][2];
	
	/**
	 * ��������getaabb
	 * �������ܣ�����array����
	 * ����ֵ���ͣ�һά��������
	 * ����ֵ���壺ȫ ͼ����·�ε�AABB��Χ���������Ҽ�ֵ
	 * @return array[]
	 */
	public int[] getaabb(int num) {
		return array[num];
	}
	
	/**
	 * ��������getaabbByNum
	 * �������ܣ�����aabbcontact����
	 * ����ֵ���ͣ�һά��������
	 * ����ֵ���壺����AABB��ײ��bezier�Ե�pathID��ʾ����[2,4]��
	 * @param num ��ȡ����
	 * @return aabbcontact[]
	 */
	public int[] getaabbByNum(int num) {
		return aabbcontact[num];
	}
	
	/**
	 * ������getlinenum
	 * �������ܣ���ȡcsv�ļ�������
	 * ����ֵ���壺csv�ļ�������
	 * @return csv�ļ�������
	 */
	public int getlinenum() {
		String csvFile =   "/Users/13916/Desktop/mapAABB.csv";
        int linenum = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	while ((br.readLine()) != null) {
        		linenum++;
            }
        }catch (IOException e) {
            e.printStackTrace();  
          } 
		return(linenum);
	}
	
	/**
	 * ��������ita
	 * �������ܣ���csv�ж�ȡȫͼ����·�ε�AABB��Χ���������Ҽ�ֵ
	 */
	public void ita() {
		String csvFile =   "/Users/13916/Desktop/mapAABB.csv";
        String line = "";
        int linenum = getlinenum();
        int b=-1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
            	if(++b >= 0&&b<linenum+1) {
            	//if(++b = state) {
            		int i = 0;
            		StringTokenizer bz = new StringTokenizer(line," ,");
                    while(bz.hasMoreTokens()){
                    	
                    	String temp = bz.nextToken();
                    	if(++i >= 0&&i<8) {
                    		double x = Double.parseDouble(String.valueOf(temp));
                    		int x_mm = Integer.parseInt(new java.text.DecimalFormat("0").format((x+4) * 100));
                    		array[b][i] = x_mm;
                    	}
                    }  
            	}      
            }
        } catch (IOException e) {
            e.printStackTrace();  
          }   	
    }
	
	/**
	 * ��������aabb_contact
	 * �������ܣ�����AABB��Χ���㷨�����Ƚ�·��֮���Ƿ���ײ
	 * ����ֵ���壺1=discontact; 0=contact
	 * @param line1 array���������
	 * @param line2 array���������
	 * @return 1/0
	 */
	public int comparison_aabb(int line1, int line2) {
		//��һ�����εļ������ĵĺ����꣨AABB�㷨�еķ�������ǻ��������ᣩ
		int a1x = array[line1][0] + Math.abs(array[line1][4]-array[line1][0])/2;
		//��һ�����εļ������ĵ�������
		int a1y = array[line1][3] + Math.abs(array[line1][1]-array[line1][5])/2;
			
		//�ڶ������εļ������ĵĺ�����
		int a2x = array[line2][0] + Math.abs(array[line2][4]-array[line2][0])/2;
		//�ڶ������εļ������ĵ�������
		int a2y = array[line2][3] + Math.abs(array[line2][1]-array[line2][5])/2;

		//�����γ���һ��֮�������������ĵĳ��ȵĺ��������
		if (Math.abs(array[line1][4]-array[line1][0])/2 + Math.abs(array[line2][4]-array[line2][0])/2 <= Math.abs(a2x-a1x)) {
			return 1;
		}
		else {
			//�����ο��һ��֮�������������ĵĳ��ȵ����������
			if(Math.abs(array[line1][1]-array[line1][5])/2 + Math.abs(array[line2][1]-array[line2][5])/2 > Math.abs(a2y-a1y)) {
				return 0;
			}
			else {
				return 1;
			}
		}
	}
	
	/**
	 * ��������aabb_contact
	 * �������ܣ�AABB��ײ��⣨�������룬��⣬�����
	 */
	public void aabb_contact() {
		ita();
		int c = 0;
		//����
		int linenum = getlinenum() - 1 ;  //linenum = 10;
		for(linenum = getlinenum() - 1 ; linenum>=0; linenum--){
			for(int l = 1; l<=linenum; l++) {
				if(comparison_aabb(linenum,linenum - l) == 0) {
					aabbcontact[c][0] = (linenum+1);
					aabbcontact[c][1] = (linenum+1-l);
					c++;
					System.out.println("path" + (linenum+1) + "&" + "path" + (linenum+1-l) + " AABB contact");
					
				}
				else {
					System.out.println("path" + (linenum+1) + "&" + "path" + (linenum+1-l) + " AABB discontact");
				}
			}
		}
		printout();
	}
	
	/**
	 * ��������printout
	 * �������ܣ��������aabb��ײ��pathID
	 */
	public void printout() {  //for check
	 		for(int i=0; i<55; i++){
	            for (int j=0; j<2; j++)
	                System.out.println("aabbcontact["+i+"]["+j+"]" + aabbcontact[i][j]);
	    	}
	 	}
}
