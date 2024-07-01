package com.javakc.service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 类名：OBB
 * 类的功能：OBB碰撞检测
 * @author DarlinZZZ
 *
 */
public class OBB {
	CountMethod CM = new CountMethod();
	//一维数组array存储了由文件地址address1所指的csv文件中的某一行（8代表这一行所代表的顶点的8个坐标值）
	int [] array = new int [8];
	//二维数组array存储了由文件地址address2所指的csv文件中的所有行（10000代表csv文件的所有行数，8代表这一行所代表的顶点的8个坐标值）
	int [][] arrays = new int [10000][8];
	//一维数组fixdata存储了经过计算处理的数据（处理的是array[]中的8个数据）
	double [] fixdata = new double [10];
	
	/**
	 * 方法名：getarrays
	 * 方法功能：返回array数组
	 * 返回值类型：一维整型数组
	 * 返回值含义：第一条被比较bezier中的某一个顶点的坐标数据
	 * @return array[]
	 */
	public int [] getarrays() {
		return array;
	}

	/**
	 * 方法名：getArrayByNum
	 * 方法功能：返回arrays二维数组中的某些元素
	 * 返回值类型：一维整型数组
	 * 返回值含义：第二条被比较bezier中的某些顶点的坐标数据
	 * @param num
	 * @return arrays[num][]
	 */
	public int[] getArrayByNum(int num) {
		return arrays[num];
	}

	/**
	 * 方法名：count_bezi2
	 * 方法功能：获得一个从第二条bezier中获取的顶点数据，并输出多少个不碰撞
	 * @param bezi2_which 第二个bezier中获取某一个顶点数据
	 * @param bezi_begin 第一个bezier中的起始获取数据位置
	 * @param time 循环次数
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
	 * 方法名：count_bezi
	 * 方法功能：获得一个从第一条bezier中获取的顶点数据，并输出多少个不碰撞（和上面的方法不同在于获取顶点的bezier地址不同）
	 * @param bezi_which 第一个bezier中获取某一个顶点数据
	 * @param bezi2_begin 第二个bezier中的起始获取数据位置
	 * @param time 循环次数
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
	 * 方法名：ita
	 * 方法功能：将由输入state所指的某条bezier的具体顶点坐标数据存入内存（二维数组）中
	 * @param state bezier行数
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
	 * 方法名：itas
	 * 方法功能：将由输入state所指的某几条bezier的具体顶点坐标数据存入内存（二维数组）中
	 * @param state bezier行数
	 * @param time 循环次数
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
	 * 方法名：fixdata
	 * 方法功能：将ita(int state)方法中存入的某一个顶点的坐标数据进行处理并存入新数组中，将数据固定防止重复调用。
	 * 返回值含义：返回1
	 * @param state1 第一个Bezier的行数
	 * @return 1/0
	 */
	public int fixdata(int state1) {
		long startTime = System.currentTimeMillis();
		ita(state1);
		//第一个矩形（一顶点）的垂直于长的分离轴的旋转弧度
        double rotation1_x = CM.radians_x(array[0], array[1], array[2], array[3]);
        //第一个矩形（一顶点）的垂直于宽的分离轴的旋转弧度
        double rotation1_y = -CM.radians_y(array[0], array[1], array[2], array[3]);
        
        //第一个矩形（一顶点）的几何中心的横坐标（基于普通坐标轴）
        double a1x = (array[0] + array[4])/2;
        //第一个矩形（一顶点）的几何中心的纵坐标（基于普通坐标轴）
        double a1y = (array[1] + array[5])/2;
        
        //第一个矩形（一顶点）的几何中心到长的中点的矢量的横坐标值（基于普通坐标轴）
      	double ktm1x1 = (array[6] + array[0])/2-(array[0] + array[4])/2;  //kernel to the middle vertex vextor x value
      	//第一个矩形（一顶点）的几何中心到长的中点的矢量的纵坐标值（基于普通坐标轴）
      	double ktm1y1 = (array[7] + array[1])/2-(array[1] + array[5])/2;  //kernel to the middle vertex vextor y value
      	//第一个矩形（一顶点）的几何中心到宽的中点的矢量的横坐标值（基于普通坐标轴）
      	double ktm1x2 = (array[6] + array[4])/2-(array[0] + array[4])/2;  //kernel to the other middle vertex vextor x value
    	//第一个矩形（一顶点）的几何中心到宽的中点的矢量的纵坐标值（基于普通坐标轴）
      	double ktm1y2 = (array[7] + array[5])/2-(array[1] + array[5])/2;  //kernel to the other middle vertex vextor y value
      	
      	//第一个矩形（一顶点）的垂直于长的分离轴的旋转弧度的余弦值
      	double b1x_x = Math.cos(rotation1_x);
      	//第一个矩形（一顶点）的垂直于长的分离轴的旋转弧度的正弦值
      	double b1y_x = Math.sin(rotation1_x);
      	//第一个矩形（一顶点）的垂直于宽的分离轴的旋转弧度的余弦值
      	double b1x_y = Math.cos(rotation1_y);
      	//第一个矩形（一顶点）的垂直于宽的分离轴的旋转弧度的正弦值
      	double b1y_y = Math.sin(rotation1_y);
      	
      	//这里由于pointtopointvectordistance计算方法，确实是反过来的
      	//第一个矩形（一顶点）的几何中心到长的中点的矢量在第一个矩形垂直于宽的分离轴上的投影长度
      	double dis1_ktvvd_x = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_x, b1y_x) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_x, b1y_x);
      	//第一个矩形（一顶点）的几何中心到长的中点的矢量在第一个矩形垂直于长的分离轴上的投影长度
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
	 * 方法名：obb
	 * 方法功能：一个顶点(ita(s))和多个顶点(itas(s))进行OBB检测
	 * 返回值含义：1=不碰撞;0=碰撞
	 * @param state2 第二个bezier的行数
	 * @return 1/0
	 */
	public int obb(int state2) {
		long startTime1 = System.currentTimeMillis();
		//详情请见STEP类的step_single，处理方法一样。
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