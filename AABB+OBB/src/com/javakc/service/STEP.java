package com.javakc.service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 类名：STEP
 * 类的功能：对于全图所有路段的优化碰撞检测
 * @author DarlinZZZ
 *
 */
public class STEP {
	AABB inputaabb = new AABB();
	CountMethod CM = new CountMethod();
	//二维数组arrays_bezi存储了map中某一条路段的所有顶点的坐标数据（1000代表某一路段的所有顶点数量，8代表某一顶点的坐标数据）
	double [][] arrays_bezi = new double [1000][8];
	//二维数组arrays_bezi2存储了map中某一条路段的所有顶点的坐标数据（1000代表某一路段的所有顶点数量，8代表某一顶点的坐标数据）
	double [][] arrays_bezi2 = new double [1000][8];
	//二维数组aabbcontact存储了所有AABB碰撞的路段对（一对有两条路段）的PathID（55代表AABB碰撞的路段对，2代表这对路段的PathID）（外传入）
	int[][] aabbcontact = new int[55][2];
	//二维数组state存储了所有AABB碰撞的路段对（一对有两条路段）的PathID，以及是否碰撞(contact/discontact)及碰撞情况（具体碰撞点）（6分为路段名1，路段名2，contact/discontact?，空格，具体碰撞点1，具体碰撞点2）
	String [][] state = new String[55][6];
		
	/**
	 * 方法名：getstateByNum
	 * 方法功能：获取某些道路是否碰撞的状态（contact/discontact)
	 * 返回值类型：一维字符型数组
	 * 返回值含义：contact/discontact
	 * @param num 获取的行数
	 * @return state[]
	 */
	public String[] getstateByNum(int num) {
		return state[num];
	}
	
	/**
	 * 方法名：getarrays_bezi
	 * 方法功能：从第一个bezier中获取某些顶点的坐标数据
	 * 返回值类型：一维浮点型数组
	 * 返回值含义：顶点的坐标数据
	 * @param num 获取的行数
	 * @return arrays_bezi[]
	 */
	public double[] getarrays_bezi(int num) {
		return arrays_bezi[num];
	}
	
	/**
	 * 方法名：getarrays_bezi2
	 * 方法功能：从第二个bezier中获取某些顶点的坐标数据
	 * 返回值类型：一维浮点型数组
	 * 返回值含义：顶点的坐标数据
	 * @param num
	 * @return arrays_bezi2[]
	 */
	public double[] getarrays_bezi2(int num) {
		return arrays_bezi2[num];
	}
	
	/**
	 * 方法名：step
	 * 方法功能：对于全图的所有路段，先进行AABB的检测，然后再进行分步长的OBB检测。
	 * @param x_threshhold 投影在x轴上的安全阈值
     * @param y_threshhold 投影在y轴上的安全阈值
     * @param step 步长
	 */
	public void step(double x_threshhold, double y_threshhold, int step) {
		int a = 0;	
		inputaabb.aabb_contact();
		for(int abn = 0; abn<55; abn++)
		{
			aabbcontact[abn] = inputaabb.getaabbByNum(abn);
		}
		for(a=0;a<55;a++) {
			if(aabbcontact[a][0]!=0 && aabbcontact[a][1] != 0) {
				state[a][3]="";
				step_input(a,aabbcontact[a][0]-1,aabbcontact[a][1]-1);
				if(step_circu(x_threshhold, y_threshhold, step,a) == 1) {
					System.out.println("DISCONTACT");
					state[a][2] = "DISCONTACT";
				}
				else {
					System.out.println("CONTACT");
					state[a][2] = "CONTACT";
				}
			}
		}
	}	
	
	/**
	 * 方法名：itas_bezi
	 * 方法功能：将由输入a1所指的某条bezier的具体顶点坐标数据存入内存（二维数组）中
	 * @param a 数组state中的行数
	 * @param a1 读取的路段
	 */
	public void itas_bezi(int a, int a1) {
		String csvFile =   "/Users/13916/Desktop/map.csv";
		state[a][0] = (a1+1)+"";
        String line = "";
        int b=-1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
            	if(++b >= a1*1000 && b<(a1*1000+1000)) {
            		int i = 0;
            		StringTokenizer bz = new StringTokenizer(line," ,");
                    while(bz.hasMoreTokens()){
                    	
                    	String temp = bz.nextToken();
                    	if(++i > 5&&i<14) {
                    		double x = Double.parseDouble(String.valueOf(temp));
                    		//int x_mm = Integer.parseInt(new java.text.DecimalFormat("0").format((x+4) * 100));
                    		arrays_bezi[b-a1*1000][i-6] = x;
                    	}
                    }  
            	}      
            }
        } catch (IOException e) {
            e.printStackTrace();  
          }  
    }
	
	/**
	 * 方法名：itas_bezi2
	 * 方法功能：将由输入a2所指的某条bezier的具体顶点坐标数据存入内存（二维数组）中
	 * @param a 数组state中的行数
	 * @param a2 读取的路段
	 */
	public void itas_bezi2(int a, int a2) {
		String csvFile =   "/Users/13916/Desktop/map.csv";
		state[a][1] = (a2+1)+"";
        String line = "";
        int b=-1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
            	if(++b >= a2*1000 && b<(a2*1000+1000)) {
            		int i = 0;
            		StringTokenizer bz = new StringTokenizer(line," ,");
                    while(bz.hasMoreTokens()){
                    	
                    	String temp = bz.nextToken();
                    	if(++i > 5&&i<14) {
                    		double x = Double.parseDouble(String.valueOf(temp));
                    		arrays_bezi2[b-a2*1000][i-6] = x;
                    	}
                    }  
            	}      
            }
        } catch (IOException e) {
            e.printStackTrace();  
          }  
    }
	
	/**
	 * 方法名：step_input
	 * 方法功能：顶点数据存入（并计算存入时间）
	 * @param a 数组state中的行数
	 * @param a1 读入1的行数（读取的路段）
	 * @param a2 读入2的行数（读取的路段）
	 */
 	public void step_input(int a, int a1, int a2) {  //check pass
 		long startTime = System.currentTimeMillis();
 		itas_bezi(a,a1);
 		itas_bezi2(a,a2);
        long endTime = System.currentTimeMillis();
    	System.out.println("input1+2 running time = " + (endTime - startTime) + "ms");
    	//printout1();  //check pass
    	//printout2();  //check pass
 	}
 	
 	/**
 	 * 方法名：printout1
	 * 方法功能：检测存入是否正确
 	 */
 	public void printout1() {  //for check
 		for(int i=0; i<1000; i++){
            for (int j=0; j<8; j++)
                System.out.println("arrays_bezi["+i+"]["+j+"]" + arrays_bezi[i][j]);
    	}
 	}
 	
 	/**
 	 * 方法名：printout1
	 * 方法功能：检测存入是否正确
 	 */
 	public void printout2() {  //for check
 		for(int i=0; i<1000; i++){
            for (int j=0; j<8; j++)
                System.out.println("arrays_bezi2["+i+"]["+j+"]" + arrays_bezi2[i][j]);
    	}
 	}

 	// input part end
 	
 	/**
 	 * 方法名：step_circu
 	 * 方法功能：两条路径之间的完整碰撞检测
 	 * 返回值含义：1=discontact;0=contact
 	 * @param x_threshhold 投影在x轴上的安全阈值
     * @param y_threshhold 投影在y轴上的安全阈值
     * @param step 步长
 	 * @param a 数组state中的行数
 	 * @return 1/0
 	 */
 	public int step_circu(double x_threshhold, double y_threshhold, int step, int a) {
 		int contact = 1;
 		int step_f = 10;
 		for(int i=31; i<969; i=i+step) {
 			for(int j=31; j<969; j=j+step) {
 				if(step_single(i,j) == 1) {
 					if(step_sepa(x_threshhold, y_threshhold, i, j) == 3||step_sepa(x_threshhold, y_threshhold, i, j) == 2) {
 						if(step_further(i, j ,step_f) == 0) {
 							contact = 0;
 							System.out.println("i = " + i);
 							System.out.println("j = " + j);
 							state[a][4] = i+"";
 							state[a][5] = j+"";
 							return(contact);
 						}
 					}
 				}
 				else if(step_single(i,j) == 0) {
 					System.out.println("i = " + i);
					System.out.println("j = " + j);
					state[a][4] = i+"";
					state[a][5] = j+"";
 					contact = 0;
 					return(contact);
 				}
 			}
 		}
 		return(contact);
 	}
 	
 	/**
 	 * 方法名：step_further
 	 * 方法功能：针对两条路径中较为接近的顶点的周围做步长较小的碰撞检测。
 	 * 返回值含义：1=discontact;0=contact
 	 * @param step 粗步长
 	 * @param step2 粗步长
 	 * @param step_f 细步长step_further
 	 * @return 1/0
 	 */
 	public int step_further(int step, int step2, int step_f) {
 		int fc = 1;
 		for(int i = step-30; i < step+30; i=i+step_f ) {
 			for(int j = step-30; j < step+30; j=j+step_f) {
 				if(step_single(i,j) == 0) {
 					fc = 0;
 					System.out.println("further contact");
 					return(fc);
 				}
 			}
 		}
 		return(fc);
 	}
 	
 	/**
 	 * 方法名：step_single
 	 * 方法功能：两个顶点之间的碰撞检测
 	 * 返回值含义：1=discontact;0=contact：
 	 * @param step
 	 * @param step2
 	 * @return 1/0
 	 */
 	public int step_single(int step, int step2) {
 		//第一个矩形（一顶点）的垂直于长的分离轴的旋转弧度
 		double rotation1_x = CM.radians_x(arrays_bezi[step][0], arrays_bezi[step][1], arrays_bezi[step][2], arrays_bezi[step][3]);
 		//第一个矩形（一顶点）的垂直于宽的分离轴的旋转弧度
        double rotation1_y = -CM.radians_y(arrays_bezi[step][0], arrays_bezi[step][1], arrays_bezi[step][2], arrays_bezi[step][3]);
       
        //第一个矩形（一顶点）的几何中心的横坐标（基于普通坐标轴）
        double a1x = (arrays_bezi[step][0] + arrays_bezi[step][4])/2;
        //第一个矩形（一顶点）的几何中心的纵坐标（基于普通坐标轴）
        double a1y = (arrays_bezi[step][1] + arrays_bezi[step][5])/2;
        
        //第一个矩形（一顶点）的几何中心到长的中点的矢量的横坐标值（基于普通坐标轴）
        double ktm1x1 = (arrays_bezi[step][6] + arrays_bezi[step][0])/2-(arrays_bezi[step][0] + arrays_bezi[step][4])/2;  //kernel to the middle vertex vextor x value
        //第一个矩形（一顶点）的几何中心到长的中点的矢量的纵坐标值（基于普通坐标轴）
      	double ktm1y1 = (arrays_bezi[step][7] + arrays_bezi[step][1])/2-(arrays_bezi[step][1] + arrays_bezi[step][5])/2;  //kernel to the middle vertex vextor y value
      	//第一个矩形（一顶点）的几何中心到宽的中点的矢量的横坐标值（基于普通坐标轴）
      	double ktm1x2 = (arrays_bezi[step][6] + arrays_bezi[step][4])/2-(arrays_bezi[step][0] + arrays_bezi[step][4])/2;  //kernel to the other middle vertex vextor x value
      	//第一个矩形（一顶点）的几何中心到宽的中点的矢量的纵坐标值（基于普通坐标轴）
      	double ktm1y2 = (arrays_bezi[step][7] + arrays_bezi[step][5])/2-(arrays_bezi[step][1] + arrays_bezi[step][5])/2;  //kernel to the other middle vertex vextor y value
      	
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
      	double dis1_ktvvd_x = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_y, b1y_y) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_y, b1y_y);
      	//第一个矩形（一顶点）的几何中心到长的中点的矢量在第一个矩形垂直于长的分离轴上的投影长度
      	double dis1_ktvvd_y = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_x, b1y_x) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_x, b1y_x);
      	
      	//以下处理数据方法一样，只不过是另一个矩形
      	double a2x = (arrays_bezi2[step2][0] + arrays_bezi2[step2][4])/2;
        double a2y = (arrays_bezi2[step2][1] + arrays_bezi2[step2][5])/2;
       	double ktm2x1 = (arrays_bezi2[step2][6] + arrays_bezi2[step2][0])/2-(arrays_bezi2[step2][0] + arrays_bezi2[step2][4])/2;  //kernel to the middle vertex vextor x value
       	double ktm2y1 = (arrays_bezi2[step2][7] + arrays_bezi2[step2][1])/2-(arrays_bezi2[step2][1] + arrays_bezi2[step2][5])/2;  //kernel to the middle vertex vextor y value
       	double ktm2x2 = (arrays_bezi2[step2][6] + arrays_bezi2[step2][4])/2-(arrays_bezi2[step2][0] + arrays_bezi2[step2][4])/2;  //kernel to the other middle vertex vextor x value
       	double ktm2y2 = (arrays_bezi2[step2][7] + arrays_bezi2[step2][5])/2-(arrays_bezi2[step2][1] + arrays_bezi2[step2][5])/2;  //kernel to the other middle vertex vextor y value
       	double b2x_x = Math.cos(rotation1_x);
       	double b2y_x = Math.sin(rotation1_x);
       	double b2x_y = Math.cos(rotation1_y);
       	double b2y_y = Math.sin(rotation1_y);
       	//第二个矩形（一顶点）的几何中心到长的中点的矢量在第一个矩形垂直于宽的分离轴上的投影长度
       	double dis2_ktvvd_x = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_y, b2y_y) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_y, b2y_y);
       	//第二个矩形（一顶点）的几何中心到长的中点的矢量在第一个矩形垂直于长的分离轴上的投影长度
       	double dis2_ktvvd_y = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_x, b2y_x) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_x, b2y_x);
 	    
       	//以下的两个矩形的几何中心的距离计算
       	//首先基于某一个矩形的垂直于长的分离轴的旋转弧度
       	double rotation_ptp_x = rotation1_x;
       	//首先基于某一个矩形的垂直于宽的分离轴的旋转弧度
      	double rotation_ptp_y = rotation1_y;
      	
      	//首先基于某一个矩形的垂直于长的分离轴的旋转弧度的余弦值
      	double ptpx_x = Math.cos(rotation_ptp_x);
      	//首先基于某一个矩形的垂直于长的分离轴的旋转弧度的正弦值
      	double ptpy_x = Math.sin(rotation_ptp_x);
        //首先基于某一个矩形的垂直于宽的分离轴的旋转弧度的余弦值
      	double ptpx_y = Math.cos(rotation_ptp_y);
        //首先基于某一个矩形的垂直于宽的分离轴的旋转弧度的正弦值
      	double ptpy_y = Math.sin(rotation_ptp_y);
      	
      	//这里由于pointtopointvectordistance计算方法，确实是反过来的
    	//计算几何中心到几何中心的线段在垂直于宽的分离轴上的投影长度（首先基于某一个矩形的垂直于宽的分离轴的旋转弧度） 
      	double dis_ktkd_x = CM.pointtopointvectordistance(a2x-a1x, a2y-a1y, ptpx_y, ptpy_y) ;
      	//计算几何中心到几何中心的线段在垂直于长的分离轴上的投影长度（首先基于某一个矩形的垂直于长的分离轴的旋转弧度） 
    	double dis_ktkd_y = CM.pointtopointvectordistance(a2x-a1x, a2y-a1y, ptpx_x, ptpy_x) ;
    	
        //check if contact
    	/**
    	 *说明1：com_1和com_sc每个都进行对于某个cube的两条分离轴比较，一共四条分离轴，要各进行一次比较。
    	 *说明2：com_1基于第一个矩形的两条分离轴进行投影比较，com_sc基于第二个矩形的两条分离轴进行投影比较。
    	 */
    	//com_1 = 0/1，判断基于第一个矩形的两条分离轴上是否碰撞
      	int com_1 = CM.comparison_1(dis1_ktvvd_x, dis2_ktvvd_x, dis1_ktvvd_y, dis2_ktvvd_y, dis_ktkd_x, dis_ktkd_y);  //compare the distance of two cubes if contact or not
      	
      	//if this cube's axis is disqualified, than compare the other cube's axis. to determine whether two cubes are compact should compare four axises in total.
      	if (com_1 == 0) {  
      		//第二个矩形（一顶点）的垂直于长的分离轴的旋转弧度
      		double rotation2_x_sc = CM.radians_x(arrays_bezi2[step2][0], arrays_bezi2[step2][1], arrays_bezi2[step2][2], arrays_bezi2[step2][3]);
      		//第二个矩形（一顶点）的垂直于宽的分离轴的旋转弧度
            double rotation2_y_sc = -CM.radians_y(arrays_bezi2[step2][0], arrays_bezi2[step2][1], arrays_bezi2[step2][2], arrays_bezi2[step2][3]);

         	//第二个矩形（一顶点）的垂直于长的分离轴的旋转弧度的余弦值
          	double b1x_x_sc = Math.cos(rotation2_x_sc);
          	//第二个矩形（一顶点）的垂直于长的分离轴的旋转弧度的正弦值
          	double b1y_x_sc = Math.sin(rotation2_x_sc);
          	//第二个矩形（一顶点）的垂直于宽的分离轴的旋转弧度的余弦值
          	double b1x_y_sc = Math.cos(rotation2_y_sc);
          	//第二个矩形（一顶点）的垂直于宽的分离轴的旋转弧度的正弦值
          	double b1y_y_sc = Math.sin(rotation2_y_sc);
          	
          	//第一个矩形（一顶点）的几何中心到长的中点的矢量在第二个矩形垂直于宽的分离轴上的投影长度
          	double dis1_ktvvd_x_sc = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_y_sc, b1y_y_sc) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_y_sc, b1y_y_sc);
          	//第一个矩形（一顶点）的几何中心到长的中点的矢量在第二个矩形垂直于长的分离轴上的投影长度
          	double dis1_ktvvd_y_sc = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_x_sc, b1y_x_sc) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_x_sc, b1y_x_sc);
      	
          	//都是转为正余弦值，为了显示清晰，第一个矩形的计算和第二个矩形分开
          	double b2x_x_sc = Math.cos(rotation2_x_sc);
          	double b2y_x_sc = Math.sin(rotation2_x_sc);
          	double b2x_y_sc = Math.cos(rotation2_y_sc);
          	double b2y_y_sc = Math.sin(rotation2_y_sc);
      	
          	//第二个矩形（一顶点）的几何中心到长的中点的矢量在第二个矩形垂直于宽的分离轴上的投影长度
          	double dis2_ktvvd_x_sc = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_y_sc, b2y_y_sc) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_y_sc, b2y_y_sc);
          	//第二个矩形（一顶点）的几何中心到长的中点的矢量在第二个矩形垂直于宽的分离轴上的投影长度
          	double dis2_ktvvd_y_sc = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_x_sc, b2y_x_sc) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_x_sc, b2y_x_sc);
          
          	//check pass
            //其次基于另一个矩形的垂直于长的分离轴的旋转弧度的余弦值
          	double ptpx_x_sc = Math.cos(rotation2_x_sc);
          	//其次基于另一个矩形的垂直于长的分离轴的旋转弧度的正弦值
          	double ptpy_x_sc = Math.sin(rotation2_x_sc);
          	//其次基于另一个矩形的垂直于宽的分离轴的旋转弧度的余弦值
          	double ptpx_y_sc = Math.cos(rotation2_y_sc);
          	//其次基于另一个矩形的垂直于宽的分离轴的旋转弧度的正弦值
          	double ptpy_y_sc = Math.sin(rotation2_y_sc);
          	
          	//计算几何中心到几何中心的线段在垂直于宽的分离轴上的投影长度（其次基于另一个矩形的垂直于宽的分离轴的旋转弧度） 
        	double dis_ktkd_x_sc = CM.pointtopointvectordistance(a2x-a1x, a2y-a1y, ptpx_y_sc, ptpy_y_sc) ;
        	//计算几何中心到几何中心的线段在垂直于长的分离轴上的投影长度（其次基于另一个矩形的垂直于长的分离轴的旋转弧度） 
        	double dis_ktkd_y_sc = CM.pointtopointvectordistance(a2x-a1x, a2y-a1y, ptpx_x_sc, ptpy_x_sc) ;

        	//再次比较第二个矩形的分离轴上的投影比较，如果com_1和com_sc都显示碰撞则碰撞，否则不碰撞
        	int com_sc = CM.comparison_sc(dis1_ktvvd_x_sc, dis2_ktvvd_x_sc, dis1_ktvvd_y_sc, dis2_ktvvd_y_sc, dis_ktkd_x_sc, dis_ktkd_y_sc);  //compare the distance of two cubes if contact or not
      	    return(com_sc);
      	}
    	return (com_1);
 	}
 	
 	/**
 	 * 方法名：step_sepa
 	 * 方法功能：对两个不碰撞的cube（每个顶点是一个cube），进行按位置分类，以此判定是否接近安全阈值
 	 * 返回值类型：int型
 	 * 返回值含义：1=x,y轴都不能接近；2=x轴接近；3=y轴接近;4=x,y轴都接近。
 	 * @param x_threshhold
 	 * @param y_threshhold
 	 * @param step
 	 * @param step2
 	 * @return 1/0
 	 */
 	public int step_sepa(double x_threshhold, double  y_threshhold, int step, int step2) {
 		//详情看方法：step_single，处理方法一样
 		double rotation1_x = CM.radians_x(arrays_bezi[step][0], arrays_bezi[step][1], arrays_bezi[step][2], arrays_bezi[step][3]);
        double rotation1_y = -CM.radians_y(arrays_bezi[step][0], arrays_bezi[step][1], arrays_bezi[step][2], arrays_bezi[step][3]);
        
        double a1x = (arrays_bezi[step][0] + arrays_bezi[step][4])/2;
        double a1y = (arrays_bezi[step][1] + arrays_bezi[step][5])/2;
      	double ktm1x1 = (arrays_bezi[step][6] + arrays_bezi[step][0])/2-(arrays_bezi[step][0] + arrays_bezi[step][4])/2;  //kernel to the middle vertex vextor x value
      	double ktm1y1 = (arrays_bezi[step][7] + arrays_bezi[step][1])/2-(arrays_bezi[step][1] + arrays_bezi[step][5])/2;  //kernel to the middle vertex vextor y value
      	double ktm1x2 = (arrays_bezi[step][6] + arrays_bezi[step][4])/2-(arrays_bezi[step][0] + arrays_bezi[step][4])/2;  //kernel to the other middle vertex vextor x value
      	double ktm1y2 = (arrays_bezi[step][7] + arrays_bezi[step][5])/2-(arrays_bezi[step][1] + arrays_bezi[step][5])/2;  //kernel to the other middle vertex vextor y value
      	double b1x_x = Math.cos(rotation1_x);
      	double b1y_x = Math.sin(rotation1_x);
      	double b1x_y = Math.cos(rotation1_y);
      	double b1y_y = Math.sin(rotation1_y);
      	
      	double dis1_ktvvd_x = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_x, b1y_x) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_x, b1y_x);
      	double dis1_ktvvd_y = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_y, b1y_y) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_y, b1y_y);
      	
      	double a2x = (arrays_bezi2[step2][0] + arrays_bezi2[step2][4])/2;
        double a2y = (arrays_bezi2[step2][1] + arrays_bezi2[step2][5])/2;
       	double ktm2x1 = (arrays_bezi2[step2][6] + arrays_bezi2[step2][0])/2-(arrays_bezi2[step2][0] + arrays_bezi2[step2][4])/2;  //kernel to the middle vertex vextor x value
       	double ktm2y1 = (arrays_bezi2[step2][7] + arrays_bezi2[step2][1])/2-(arrays_bezi2[step2][1] + arrays_bezi2[step2][5])/2;  //kernel to the middle vertex vextor y value
       	double ktm2x2 = (arrays_bezi2[step2][6] + arrays_bezi2[step2][4])/2-(arrays_bezi2[step2][0] + arrays_bezi2[step2][4])/2;  //kernel to the other middle vertex vextor x value
       	double ktm2y2 = (arrays_bezi2[step2][7] + arrays_bezi2[step2][5])/2-(arrays_bezi2[step2][1] + arrays_bezi2[step2][5])/2;  //kernel to the other middle vertex vextor y value
       	double b2x_x = Math.cos(rotation1_x);
       	double b2y_x = Math.sin(rotation1_x);
       	double b2x_y = Math.cos(rotation1_y);
       	double b2y_y = Math.sin(rotation1_y);
       	
       	double dis2_ktvvd_x = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_x, b2y_x) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_x, b2y_x);
       	double dis2_ktvvd_y = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_y, b2y_y) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_y, b2y_y);
 	
       	double rotation_ptp_x = rotation1_x;
      	double rotation_ptp_y = rotation1_y;
      	double ptpx_x = Math.cos(rotation_ptp_x);
      	double ptpy_x = Math.sin(rotation_ptp_x);
      	double ptpx_y = Math.cos(rotation_ptp_y);
      	double ptpy_y = Math.sin(rotation_ptp_y);
      	
    	double dis_ktkd_x = CM.pointtopointvectordistance(a2x-a1x, a2y-a1y, ptpx_x, ptpy_x) ;
    	double dis_ktkd_y = CM.pointtopointvectordistance(a2x-a1x, a2y-a1y, ptpx_y, ptpy_y) ;
    	//cp_xy返回1，2，3，4，分别对应不碰撞时的两矩形情况
    	int cp_xy = CM.comparison_xy(x_threshhold,y_threshhold,dis1_ktvvd_x, dis2_ktvvd_x, dis1_ktvvd_y, dis2_ktvvd_y, dis_ktkd_x, dis_ktkd_y);
    	System.out.println(cp_xy);
    	return (cp_xy);
 	}
  	
 	
}
