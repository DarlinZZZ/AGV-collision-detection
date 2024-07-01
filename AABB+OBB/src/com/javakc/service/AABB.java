package com.javakc.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * 类名：AABB
 * 类的功能：AABB碰撞检测
 * @author DarlinZZZ
 *
 */
public class AABB {
	//二维数组array存储了所有路段的上下左右的极值坐标（20代表路段数量，8代表路段的上下左右的极值坐标）
	int[][] array = new int[20][8];
	//二维数组aabbcontact存储了所有AABB碰撞的路段对（一对有两条路段）的PathID（55代表AABB碰撞的路段对，2代表这对路段的PathID）
	int[][] aabbcontact = new int[55][2];
	
	/**
	 * 方法名：getaabb
	 * 方法功能：返回array数组
	 * 返回值类型：一维整型数组
	 * 返回值含义：全 图所有路段的AABB包围盒上下左右极值
	 * @return array[]
	 */
	public int[] getaabb(int num) {
		return array[num];
	}
	
	/**
	 * 方法名：getaabbByNum
	 * 方法功能：返回aabbcontact数组
	 * 返回值类型：一维整型数组
	 * 返回值含义：发生AABB碰撞的bezier对的pathID（示例：[2,4]）
	 * @param num 获取行数
	 * @return aabbcontact[]
	 */
	public int[] getaabbByNum(int num) {
		return aabbcontact[num];
	}
	
	/**
	 * 法名：getlinenum
	 * 方法功能：获取csv文件的行数
	 * 返回值含义：csv文件的行数
	 * @return csv文件的行数
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
	 * 方法名：ita
	 * 方法功能：从csv中读取全图所有路段的AABB包围盒上下左右极值
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
	 * 方法名：aabb_contact
	 * 方法功能：运用AABB包围盒算法初步比较路径之间是否碰撞
	 * 返回值含义：1=discontact; 0=contact
	 * @param line1 array数组的行数
	 * @param line2 array数组的行数
	 * @return 1/0
	 */
	public int comparison_aabb(int line1, int line2) {
		//第一个矩形的几何中心的横坐标（AABB算法中的分离轴就是基本坐标轴）
		int a1x = array[line1][0] + Math.abs(array[line1][4]-array[line1][0])/2;
		//第一个矩形的几何中心的纵坐标
		int a1y = array[line1][3] + Math.abs(array[line1][1]-array[line1][5])/2;
			
		//第二个矩形的几何中心的横坐标
		int a2x = array[line2][0] + Math.abs(array[line2][4]-array[line2][0])/2;
		//第二个矩形的几何中心的纵坐标
		int a2y = array[line2][3] + Math.abs(array[line2][1]-array[line2][5])/2;

		//两矩形长的一半之和与两几何中心的长度的横坐标相比
		if (Math.abs(array[line1][4]-array[line1][0])/2 + Math.abs(array[line2][4]-array[line2][0])/2 <= Math.abs(a2x-a1x)) {
			return 1;
		}
		else {
			//两矩形宽的一半之和与两几何中心的长度的纵坐标相比
			if(Math.abs(array[line1][1]-array[line1][5])/2 + Math.abs(array[line2][1]-array[line2][5])/2 > Math.abs(a2y-a1y)) {
				return 0;
			}
			else {
				return 1;
			}
		}
	}
	
	/**
	 * 方法名：aabb_contact
	 * 方法功能：AABB碰撞检测（包含输入，检测，输出）
	 */
	public void aabb_contact() {
		ita();
		int c = 0;
		//行数
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
	 * 方法名：printout
	 * 方法功能：输出发生aabb碰撞的pathID
	 */
	public void printout() {  //for check
	 		for(int i=0; i<55; i++){
	            for (int j=0; j<2; j++)
	                System.out.println("aabbcontact["+i+"]["+j+"]" + aabbcontact[i][j]);
	    	}
	 	}
}
