package com.javakc.service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 类名：PTP
 * 类功能：模式2：PATHvsPATH,两条路段的OBB检测
 * @author DarlinZZZ
 *
 */
public class PTP {
	AABB inputaabb = new AABB();
	STEP s = new STEP();
	CountMethod CM = new CountMethod();
	//二维数组arrays_bezi存储address1所指的csv文件的所有数据（1000代表行数，8代表矩形的四个顶点坐标数据）
	double [][] arrays_bezi = new double [1000][8];
	//二维数组arrays_bezi2存储address2所指的csv文件的所有数据（1000代表行数，8代表矩形的四个顶点坐标数据）
	double [][] arrays_bezi2 = new double [1000][8];
	//一维字符型数组state存储的是第一个PathID，第二个PathID，以及是否碰撞（contact/discontact)
	String[] state = new String[3];
	
	/**
	 * 方法名：getarrays_bezi
	 * 方法功能：返回arrays_bezi二维数组中的某些元素
	 * 返回值类型：一维浮点型数组
	 * 返回值含义：第一条被比较bezier中的某些顶点的坐标数据
	 * @param num 获取行数
	 * @return arrays_bezi[]
	 */
	public double[] getarrays_bezi(int num) {
		return arrays_bezi[num];
	}
	
	/**
	 * 方法名：getarrays_bezi2
	 * 方法功能：返回arrays_bezi2二维数组中的某些元素
	 * 返回值类型：一维浮点型数组
	 * 返回值含义：第一条被比较bezier中的某些顶点的坐标数据
	 * @param num 获取行数
	 * @return arrays_bezi2[]
	 */
	public double[] getarrays_bezi2(int num) {
		return arrays_bezi2[num];
	}

	/**
	 * 方法名：pathtopath
	 * 方法功能：两条bezier进行OBB检测
	 * @param xyuzhi 投影在x轴上的安全阈值
 	 * @param yyuzhi 投影在y轴上的安全阈值
 	 * @param step 步长
	 * @param addr1 csv文件1的路径地址
	 * @param addr2 csv文件2的路径地址
	 */
	public void pathtopath(double xyuzhi, double yyuzhi, int step, String addr1, String addr2) {
		ptp_input(addr1, addr2);
		if(ptp_circu(xyuzhi, yyuzhi, step) == 1) {
			System.out.println("DISCONTACT");
			state[2] = "DISCONTACT";
		}
		else {
			System.out.println("CONTACT");
			state[2] = "CONTACT";
		}
	}	
	
	/**
	 * 方法名：itas_bezi
	 * 方法功能：获取用户输入的地址并且将地址下的csv文件解析，将数据存入内存中（arrays_bezi）
	 * @param address csv文件1的路径地址
	 */
	public void itas_bezi(String address) {
		String csvFile = address;
		state[0] = csvFile;
        String line = "";
        int b=-1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
            	if(++b >= 0 && b<1000) {
            		int i = 0;
            		StringTokenizer bz = new StringTokenizer(line," ,");
                    while(bz.hasMoreTokens()){
                    	
                    	String temp = bz.nextToken();
                    	if(++i > 5&&i<14) {
                    		double x = Double.parseDouble(String.valueOf(temp));
                    		arrays_bezi[b][i-6] = x;
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
	 * 方法功能：获取用户输入的地址并且将地址下的csv文件解析，将数据存入内存中（arrays_bezi2）
	 * @param address csv文件2的路径地址
	 */
	public void itas_bezi2(String address) {
		String csvFile = address;
		state[1] = csvFile;
        String line = "";
        int b=-1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
            	if(++b >= 0 && b<1000) {
            		int i = 0;
            		StringTokenizer bz = new StringTokenizer(line," ,");
                    while(bz.hasMoreTokens()){
                    	
                    	String temp = bz.nextToken();
                    	if(++i > 5&&i<14) {
                    		double x = Double.parseDouble(String.valueOf(temp));
                    		arrays_bezi2[b][i-6] = x;
                    	}
                    }  
            	}      
            }
        } catch (IOException e) {
            e.printStackTrace();  
          }  
    }
	
	/**
	 * 方法名：ptp_input
	 * 方法功能：数据存入
	 * @param addr1 csv文件1的路径地址
	 * @param addr2 csv文件2的路径地址
	 */
 	public void ptp_input(String addr1, String addr2) {  //check pass
 		long startTime = System.currentTimeMillis();
 		itas_bezi(addr1);
 		itas_bezi2(addr2);
        long endTime = System.currentTimeMillis();
    	System.out.println("input1+2 running time = " + (endTime - startTime) + "ms");
 	}

 	/**
 	 * 方法名：ptp_circu
	 * 方法功能：两条路径OBB检测
	 * 返回值含义：1=不碰撞；0=碰撞
 	 * @param xyuzhi 投影在x轴上的安全阈值
 	 * @param yyuzhi 投影在y轴上的安全阈值
 	 * @param step 步长
 	 * @return 1/0
 	 */
 	public int ptp_circu(double xyuzhi, double yyuzhi, int step) {
 		int contact = 1;
 		int step_f = 10;
 		for(int i=31; i<969; i=i+step) {
 			for(int j=31; j<969; j=j+step) {
 				if(s.step_single(i,j) == 1) {
 					if(s.step_sepa(xyuzhi, yyuzhi, i,j) == 3||s.step_sepa(xyuzhi, yyuzhi, i,j) == 2) {
 						if(s.step_further(i,j ,step_f) == 0) {
 							contact = 0;
 							System.out.println("i = " + i);
 							System.out.println("j = " + j);
 							return(contact);
 						}
 					}
 				}
 				else if(s.step_single(i,j) == 0) {
 					System.out.println("i = " + i);
					System.out.println("j = " + j);
 					contact = 0;
 					return(contact);
 				}
 			}
 		}
 		return(contact);
 	}
}