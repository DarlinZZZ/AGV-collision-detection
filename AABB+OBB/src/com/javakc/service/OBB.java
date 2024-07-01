package com.javakc.service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * ������OBB
 * ��Ĺ��ܣ�OBB��ײ���
 * @author DarlinZZZ
 *
 */
public class OBB {
	CountMethod CM = new CountMethod();
	//һά����array�洢�����ļ���ַaddress1��ָ��csv�ļ��е�ĳһ�У�8������һ��������Ķ����8������ֵ��
	int [] array = new int [8];
	//��ά����array�洢�����ļ���ַaddress2��ָ��csv�ļ��е������У�10000����csv�ļ�������������8������һ��������Ķ����8������ֵ��
	int [][] arrays = new int [10000][8];
	//һά����fixdata�洢�˾������㴦������ݣ��������array[]�е�8�����ݣ�
	double [] fixdata = new double [10];
	
	/**
	 * ��������getarrays
	 * �������ܣ�����array����
	 * ����ֵ���ͣ�һά��������
	 * ����ֵ���壺��һ�����Ƚ�bezier�е�ĳһ���������������
	 * @return array[]
	 */
	public int [] getarrays() {
		return array;
	}

	/**
	 * ��������getArrayByNum
	 * �������ܣ�����arrays��ά�����е�ĳЩԪ��
	 * ����ֵ���ͣ�һά��������
	 * ����ֵ���壺�ڶ������Ƚ�bezier�е�ĳЩ�������������
	 * @param num
	 * @return arrays[num][]
	 */
	public int[] getArrayByNum(int num) {
		return arrays[num];
	}

	/**
	 * ��������count_bezi2
	 * �������ܣ����һ���ӵڶ���bezier�л�ȡ�Ķ������ݣ���������ٸ�����ײ
	 * @param bezi2_which �ڶ���bezier�л�ȡĳһ����������
	 * @param bezi_begin ��һ��bezier�е���ʼ��ȡ����λ��
	 * @param time ѭ������
	 */
	public void count_bezi2(int bezi2_which, int bezi_begin, int time) {	
		long startTime = System.currentTimeMillis();
		int pos = 0;
		fixdata(bezi2_which);
		itas(bezi_begin,time);
		for(int p=bezi_begin; p<bezi_begin+time; p++) {
			if(obb(p) == 1) {
				pos++;
			}
		}  
		System.out.println("DISCOMPACT NUM =" + pos);
		long endTime = System.currentTimeMillis();
    	System.out.println("all running time = " + (endTime - startTime) + "ms");
	}  
	
	/**
	 * ��������count_bezi
	 * �������ܣ����һ���ӵ�һ��bezier�л�ȡ�Ķ������ݣ���������ٸ�����ײ��������ķ�����ͬ���ڻ�ȡ�����bezier��ַ��ͬ��
	 * @param bezi_which ��һ��bezier�л�ȡĳһ����������
	 * @param bezi2_begin �ڶ���bezier�е���ʼ��ȡ����λ��
	 * @param time ѭ������
	 */
	public void count_bezi(int bezi_which, int bezi2_begin, int time) {	
		long startTime = System.currentTimeMillis();
		int pos = 0;
		fixdata(bezi_which);
		itas(bezi2_begin,time);
		for(int p=bezi2_begin; p<bezi2_begin+time; p++) {
			if(obb(p) == 1) {
				pos++;
			}
		}  
		System.out.println("DISCOMPACT NUM =" + pos);
		long endTime = System.currentTimeMillis();
    	System.out.println("all running time = " + (endTime - startTime) + "ms");
	}
	
	/**
	 * ��������ita
	 * �������ܣ���������state��ָ��ĳ��bezier�ľ��嶥���������ݴ����ڴ棨��ά���飩��
	 * @param state bezier����
	 */
	public void ita(int state) {
		String csvFile =   "/Users/13916/Desktop/bezi10000.csv";
        String line = "";
        int b=0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
            	if(++b > state&&b<state +2) {
            	//if(++b = state) {
            		int i = 0;
            		StringTokenizer bz = new StringTokenizer(line," ,");
                    while(bz.hasMoreTokens()){
                    	
                    	String temp = bz.nextToken();
                    	if(++i > 5&&i<14) {
                    		double x = Double.parseDouble(String.valueOf(temp));
                    		int x_mm = Integer.parseInt(new java.text.DecimalFormat("0").format((x+4) * 100));
                    		array[i-6] = x_mm;
                    	}
                    }  
            	}      
            }
        } catch (IOException e) {
            e.printStackTrace();  
          }   	
    }
	
	/**
	 * ��������itas
	 * �������ܣ���������state��ָ��ĳ����bezier�ľ��嶥���������ݴ����ڴ棨��ά���飩��
	 * @param state bezier����
	 * @param time ѭ������
	 */
	public void itas(int state, int time) {
		long startTime = System.currentTimeMillis();
		String csvFile =   "/Users/13916/Desktop/bezi210000.csv";
        String line = "";
        int b=0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
            	if(++b > state&&b<state +2+time) {
            	//if(++b = state) {
            		int i = 0;
            		StringTokenizer bz = new StringTokenizer(line," ,");
                    while(bz.hasMoreTokens()){
                    	
                    	String temp = bz.nextToken();
                    	if(++i > 5&&i<14) {
                    		double x = Double.parseDouble(String.valueOf(temp));
                    		int x_mm = Integer.parseInt(new java.text.DecimalFormat("0").format((x+4) * 100));
                    		arrays[b][i-6] = x_mm;
                    	}
                    }  
            	}      
            }
        } catch (IOException e) {
            e.printStackTrace();  
          }  
        long endTime = System.currentTimeMillis();
    	System.out.println("itas running time = " + (endTime - startTime) + "ms");
    }
	
	/**
	 * ��������fixdata
	 * �������ܣ���ita(int state)�����д����ĳһ��������������ݽ��д��������������У������ݹ̶���ֹ�ظ����á�
	 * ����ֵ���壺����1
	 * @param state1 ��һ��Bezier������
	 * @return 1/0
	 */
	public int fixdata(int state1) {
		long startTime = System.currentTimeMillis();
		ita(state1);
		//��һ�����Σ�һ���㣩�Ĵ�ֱ�ڳ��ķ��������ת����
        double rotation1_x = CM.radians_x(array[0], array[1], array[2], array[3]);
        //��һ�����Σ�һ���㣩�Ĵ�ֱ�ڿ�ķ��������ת����
        double rotation1_y = -CM.radians_y(array[0], array[1], array[2], array[3]);
        
        //��һ�����Σ�һ���㣩�ļ������ĵĺ����꣨������ͨ�����ᣩ
        double a1x = (array[0] + array[4])/2;
        //��һ�����Σ�һ���㣩�ļ������ĵ������꣨������ͨ�����ᣩ
        double a1y = (array[1] + array[5])/2;
        
        //��һ�����Σ�һ���㣩�ļ������ĵ������е��ʸ���ĺ�����ֵ��������ͨ�����ᣩ
      	double ktm1x1 = (array[6] + array[0])/2-(array[0] + array[4])/2;  //kernel to the middle vertex vextor x value
      	//��һ�����Σ�һ���㣩�ļ������ĵ������е��ʸ����������ֵ��������ͨ�����ᣩ
      	double ktm1y1 = (array[7] + array[1])/2-(array[1] + array[5])/2;  //kernel to the middle vertex vextor y value
      	//��һ�����Σ�һ���㣩�ļ������ĵ�����е��ʸ���ĺ�����ֵ��������ͨ�����ᣩ
      	double ktm1x2 = (array[6] + array[4])/2-(array[0] + array[4])/2;  //kernel to the other middle vertex vextor x value
    	//��һ�����Σ�һ���㣩�ļ������ĵ�����е��ʸ����������ֵ��������ͨ�����ᣩ
      	double ktm1y2 = (array[7] + array[5])/2-(array[1] + array[5])/2;  //kernel to the other middle vertex vextor y value
      	
      	//��һ�����Σ�һ���㣩�Ĵ�ֱ�ڳ��ķ��������ת���ȵ�����ֵ
      	double b1x_x = Math.cos(rotation1_x);
      	//��һ�����Σ�һ���㣩�Ĵ�ֱ�ڳ��ķ��������ת���ȵ�����ֵ
      	double b1y_x = Math.sin(rotation1_x);
      	//��һ�����Σ�һ���㣩�Ĵ�ֱ�ڿ�ķ��������ת���ȵ�����ֵ
      	double b1x_y = Math.cos(rotation1_y);
      	//��һ�����Σ�һ���㣩�Ĵ�ֱ�ڿ�ķ��������ת���ȵ�����ֵ
      	double b1y_y = Math.sin(rotation1_y);
      	
      	//��������pointtopointvectordistance���㷽����ȷʵ�Ƿ�������
      	//��һ�����Σ�һ���㣩�ļ������ĵ������е��ʸ���ڵ�һ�����δ�ֱ�ڿ�ķ������ϵ�ͶӰ����
      	double dis1_ktvvd_x = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_x, b1y_x) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_x, b1y_x);
      	//��һ�����Σ�һ���㣩�ļ������ĵ������е��ʸ���ڵ�һ�����δ�ֱ�ڳ��ķ������ϵ�ͶӰ����
      	double dis1_ktvvd_y = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_y, b1y_y) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_y, b1y_y);
    	fixdata[0] = a1x;
      	fixdata[1] = a1y;
      	fixdata[2] = ktm1x1;
      	fixdata[3] = ktm1y1;
      	fixdata[4] = ktm1x2;
      	fixdata[5] = ktm1y2;	
      	fixdata[6] = rotation1_x;
      	fixdata[7] = rotation1_y;
      	fixdata[8] = dis1_ktvvd_x;
      	fixdata[9] = dis1_ktvvd_y;	
      	
      	long endTime = System.currentTimeMillis();
    	System.out.println("fixdata running time = " + (endTime - startTime) + "ms");
      	return 1;
	}
	
	/**
	 * ��������obb
	 * �������ܣ�һ������(ita(s))�Ͷ������(itas(s))����OBB���
	 * ����ֵ���壺1=����ײ;0=��ײ
	 * @param state2 �ڶ���bezier������
	 * @return 1/0
	 */
	public int obb(int state2) {
		long startTime1 = System.currentTimeMillis();
		//�������STEP���step_single��������һ����
        double a2x = (arrays[state2][0] + arrays[state2][4])/2;
        double a2y = (arrays[state2][1] + arrays[state2][5])/2;
      	double ktm2x1 = (arrays[state2][6] + arrays[state2][0])/2-(arrays[state2][0] + arrays[state2][4])/2;  //kernel to the middle vertex vextor x value
      	double ktm2y1 = (arrays[state2][7] + arrays[state2][1])/2-(arrays[state2][1] + arrays[state2][5])/2;  //kernel to the middle vertex vextor y value
      	double ktm2x2 = (arrays[state2][6] + arrays[state2][4])/2-(arrays[state2][0] + arrays[state2][4])/2;  //kernel to the other middle vertex vextor x value
      	double ktm2y2 = (arrays[state2][7] + arrays[state2][5])/2-(arrays[state2][1] + arrays[state2][5])/2;  //kernel to the other middle vertex vextor y value
      	double b2x_x = Math.cos(fixdata[6]);
      	double b2y_x = Math.sin(fixdata[6]);
      	double b2x_y = Math.cos(fixdata[7]);
      	double b2y_y = Math.sin(fixdata[7]);
      	
      	double dis2_ktvvd_x = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_x, b2y_x) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_x, b2y_x);
      	double dis2_ktvvd_y = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_y, b2y_y) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_y, b2y_y);

      	//point to point part
      	
      	double rotation_ptp_x = fixdata[6];
      	double rotation_ptp_y = fixdata[7];
      	double ptpx_x = Math.cos(rotation_ptp_x);
      	double ptpy_x = Math.sin(rotation_ptp_x);
      	double ptpx_y = Math.cos(rotation_ptp_y);
      	double ptpy_y = Math.sin(rotation_ptp_y);
      	
    	double dis_ktkd_x = CM.pointtopointvectordistance(a2x-fixdata[0], a2y-fixdata[1], ptpx_x, ptpy_x) ;
    	double dis_ktkd_y = CM.pointtopointvectordistance(a2x-fixdata[0], a2y-fixdata[1], ptpx_y, ptpy_y) ;
      	int com_1 = CM.comparison_1(fixdata[8], dis2_ktvvd_x, fixdata[9], dis2_ktvvd_y, dis_ktkd_x, dis_ktkd_y);  //compare the distance of two cubes if contact or not

      	long endTime1 = System.currentTimeMillis();
     	System.out.println("OBB1 running time = " + (endTime1 - startTime1) + "ms");
      	if (com_1 == 0) {  //if this cube's axis is disqualified, than compare the other cube's axis. to determine whether two cubes are compact should compare four axises in total.
      		long startTime2 = System.currentTimeMillis();
      		double rotation2_x_sc = CM.radians_x(arrays[state2][0], arrays[state2][1], arrays[state2][2], arrays[state2][3]);
            double rotation2_y_sc = -CM.radians_y(arrays[state2][0], arrays[state2][1], arrays[state2][2], arrays[state2][3]);
                 
          	double b1x_x_sc = Math.cos(rotation2_x_sc);
          	double b1y_x_sc = Math.sin(rotation2_x_sc);
          	double b1x_y_sc = Math.cos(rotation2_y_sc);
          	double b1y_y_sc = Math.sin(rotation2_y_sc);
          	
          	double dis1_ktvvd_x_sc = CM.pointtopointvectordistance(fixdata[2], fixdata[3], b1x_x_sc, b1y_x_sc) + CM.pointtopointvectordistance(fixdata[4], fixdata[5], b1x_x_sc, b1y_x_sc);
          	double dis1_ktvvd_y_sc = CM.pointtopointvectordistance(fixdata[2], fixdata[3], b1x_y_sc, b1y_y_sc) + CM.pointtopointvectordistance(fixdata[4], fixdata[5], b1x_y_sc, b1y_y_sc);
      	
          	double b2x_x_sc = Math.cos(rotation2_x_sc);
          	double b2y_x_sc = Math.sin(rotation2_x_sc);
          	double b2x_y_sc = Math.cos(rotation2_y_sc);
          	double b2y_y_sc = Math.sin(rotation2_y_sc);
      	
          	double dis2_ktvvd_x_sc = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_x_sc, b2y_x_sc) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_x_sc, b2y_x_sc);
          	double dis2_ktvvd_y_sc = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_y_sc, b2y_y_sc) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_y_sc, b2y_y_sc);
          	
          	double ptpx_x_sc = Math.cos(rotation2_x_sc);
          	double ptpy_x_sc = Math.sin(rotation2_x_sc);
          	double ptpx_y_sc = Math.cos(rotation2_y_sc);
          	double ptpy_y_sc = Math.sin(rotation2_y_sc);
          	
        	double dis_ktkd_x_sc = CM.pointtopointvectordistance(a2x-fixdata[0], a2y-fixdata[1], ptpx_x_sc, ptpy_x_sc) ;
        	double dis_ktkd_y_sc = CM.pointtopointvectordistance(a2x-fixdata[0], a2y-fixdata[1], ptpx_y_sc, ptpy_y_sc) ;
        	
        	
        	int com_sc = CM.comparison_sc(dis1_ktvvd_x_sc, dis2_ktvvd_x_sc, dis1_ktvvd_y_sc, dis2_ktvvd_y_sc, dis_ktkd_x_sc, dis_ktkd_y_sc);  //compare the distance of two cubes if contact or not
        	long endTime2 = System.currentTimeMillis();
         	System.out.println("OBB2 running time = " + (endTime2 - startTime2) + "ms");
      	    return(com_sc);
      	}
    	return (com_1); 	 
	} 
}