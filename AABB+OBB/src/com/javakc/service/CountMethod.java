package com.javakc.service;

/**
 * 类名：CountMethod
 * 类功能：所有的数据处理中的计算方法
 * @author DarlinZZZ
 *
 */
public class CountMethod {

	/**
	 * 方法名：radians_x
	 * 方法功能：已知两点坐标，求线段对于x轴的弧度，并返回弧度
	 * @param coordinates1_x 第一个点的横坐标
	 * @param coordinates1_y 第一个点的纵坐标
	 * @param coordinates2_x 第二个点的横坐标
	 * @param coordinates2_y 第二个点的纵坐标
	 * @return rotation
	 */
	public double radians_x(double coordinates1_x, double coordinates1_y, double coordinates2_x, double coordinates2_y) {
		double r_x = (coordinates1_y-coordinates2_y)/(coordinates1_x-coordinates2_x);
		r_x = Math.atan(r_x);
		r_x = Math.toDegrees(r_x);
	    double rotation = r_x;
		rotation = Math.toRadians(rotation);
		return rotation;
	}
		
	/**
	 * 方法名：radians_y
	 * 方法功能：已知两点坐标，求线段对于y轴的弧度，并返回弧度
	 * @param coordinates1_x 第一个点的横坐标
	 * @param coordinates1_y 第一个点的纵坐标
	 * @param coordinates2_x 第二个点的横坐标
	 * @param coordinates2_y 第二个点的纵坐标
	 * @return rotation
	 */
	public double radians_y(double coordinates1_x, double coordinates1_y, double coordinates2_x, double coordinates2_y) {
		double r_y = (coordinates2_x-coordinates1_x)/(coordinates2_y-coordinates1_y);
		r_y = Math.atan(r_y);
		r_y = Math.toDegrees(r_y);
		double rotation = r_y;
		rotation = Math.toRadians(rotation);
		return rotation;
	}
			
	/**
	 * 方法名：pointtopointdistance
	 * 方法功能：计算点到点矢量长度并返回
	 * 返回值类型：浮点型
	 * @param coordinates1_x 第一个点的横坐标
	 * @param coordinates1_y 第一个点的纵坐标
	 * @param coordinates2_x 第二个点的横坐标
	 * @param coordinates2_y 第二个点的纵坐标
	 * @return ptpvd
	 */
	public double pointtopointvectordistance(double coordinates1_x, double coordinates1_y, double coordinates2_x, double coordinates2_y) {
		double ptpvd = Math.abs(coordinates1_x * coordinates2_x + coordinates1_y * coordinates2_y);  
	    return ptpvd;	
	}
		
	/**
	 * 方法名：pointtopointdistance
	 * 方法功能：计算点到点线段长度并返回
	 * 返回值类型：浮点型
	 * @param coordinates1_x 第一个点的横坐标
	 * @param coordinates1_y 第一个点的纵坐标
	 * @param coordinates2_x 第二个点的横坐标
	 * @param coordinates2_y 第二个点的纵坐标
	 * @return ptpd
	 */
	public double pointtopointdistance(double coordinates1_x, double coordinates1_y, double coordinates2_x, double coordinates2_y) {
		double ptpd = Math.sqrt((coordinates2_y - coordinates1_y) * (coordinates2_y - coordinates1_y) + (coordinates2_x - coordinates1_x) * (coordinates2_x - coordinates1_x));
	    return ptpd;	
	}

	/**
	 * 方法名：comparison_1
	 * 方法功能：基于某一个方块的轴检测是否碰撞
	 * 返回值含义：1=不碰撞； 0=碰撞
	 * @param distance1_ktvvd_x 第一个矩形中心到边长中点的在x轴上投影距离
	 * @param distance2_ktvvd_x 第二个矩形中心到边长中点的在x轴上投影距离
	 * @param distance1_ktvvd_y 第一个矩形中心到边长中点的在y轴上投影距离
	 * @param distance2_ktvvd_y 第二个矩形中心到边长中点的在y轴上投影距离
	 * @param distance_ktkd_x 第一个矩形中心到第二个矩形中心的线段距离
	 * @param distance_ktkd_y 第一个矩形中心到第二个矩形中心的线段距离
	 * @return 1/0
	 */
	public int comparison_1(double distance1_ktvvd_x, double distance2_ktvvd_x, double distance1_ktvvd_y, double distance2_ktvvd_y, double distance_ktkd_x, double distance_ktkd_y) {  
		if ((distance1_ktvvd_x + distance2_ktvvd_x) < distance_ktkd_x)	{
			System.out.println("DISCONTACT!");
			return 1;
		}
		else if ((distance1_ktvvd_y + distance2_ktvvd_y) < distance_ktkd_y) {
			System.out.println("DISCONTACT!");
			return 1;
		}
		else {
			return 0;
		}	
	}
	
	/**
	 * 方法名：comparison_sc
	 * 方法功能：基于另一个方块的轴检测是否碰撞
	 * 返回值含义：1=不碰撞； 0=碰撞
	 * @param distance1_ktvvd_x_sc 第一个矩形中心到边长中点的在另一个矩形的x轴上投影距离
	 * @param distance2_ktvvd_x_sc 第二个矩形中心到边长中点的在另一个矩形的x轴上投影距离
	 * @param distance1_ktvvd_y_sc 第一个矩形中心到边长中点的在另一个矩形的y轴上投影距离
	 * @param distance2_ktvvd_y_sc 第二个矩形中心到边长中点的在另一个矩形的y轴上投影距离
	 * @param distance_ktkd_x_sc 第一个矩形中心到第二个矩形中心的线段距离
	 * @param distance_ktkd_y_sc 第一个矩形中心到第二个矩形中心的线段距离
	 * @return 1/0
	 */
	public int comparison_sc(double distance1_ktvvd_x_sc, double distance2_ktvvd_x_sc, double distance1_ktvvd_y_sc, double distance2_ktvvd_y_sc, double distance_ktkd_x_sc, double distance_ktkd_y_sc) {  
		if ((distance1_ktvvd_x_sc + distance2_ktvvd_x_sc) < distance_ktkd_x_sc)	{
			System.out.println("DISCONTACT!");
			return 1;
		}
		else if ((distance1_ktvvd_y_sc + distance2_ktvvd_y_sc) < distance_ktkd_y_sc) {
			System.out.println("DISCONTACT!");
			return 1;
		}
		else {
			System.out.println("CONTACT!");
			return 0;
		}	
	}
	
	/**
 	 * 方法名：comparison_xy
 	 * 方法功能：对两个不碰撞的cube（每个顶点是一个cube），进行按位置分类的具体计算
 	 * 返回值类型：int型
 	 * 返回值含义：1=x,y轴都不能接近；2=x轴接近；3=y轴接近;4=x,y轴都接近。
 	 * @param x_threshhold x轴上的安全阈值
 	 * @param y_threshhold y轴上的安全阈值
 	 * @param distance1_ktvvd_x 第一个矩形中心到边长中点的在x轴上投影距离
	 * @param distance2_ktvvd_x 第二个矩形中心到边长中点的在x轴上投影距离
	 * @param distance1_ktvvd_y 第一个矩形中心到边长中点的在y轴上投影距离
	 * @param distance2_ktvvd_y 第二个矩形中心到边长中点的在y轴上投影距离
	 * @param distance_ktkd_x 第一个矩形中心到第二个矩形中心的线段距离
	 * @param distance_ktkd_y 第一个矩形中心到第二个矩形中心的线段距离
 	 * @return 1/2/3/4
 	 */
	public int comparison_xy(double x_threshhold, double y_threshhold, double distance1_ktvvd_x, double distance2_ktvvd_x, double distance1_ktvvd_y, double distance2_ktvvd_y, double distance_ktkd_x, double distance_ktkd_y) {  
		int x_xy = 0;
		if ((distance1_ktvvd_x + distance2_ktvvd_x) < distance_ktkd_x)	{
			if ((distance1_ktvvd_y + distance2_ktvvd_y) < distance_ktkd_y) {
				System.out.println("distance_ktkd_y-(distance1_ktvvd_y + distance2_ktvvd_y) = " + (distance_ktkd_y-(distance1_ktvvd_y + distance2_ktvvd_y)));
				x_xy = 1;
			}
			else {
				if(distance_ktkd_x - (distance1_ktvvd_x + distance2_ktvvd_x) < x_threshhold) {
					System.out.println("distance_ktkd_x-(distance1_ktvvd_x + distance2_ktvvd_x) = " + (distance_ktkd_x-(distance1_ktvvd_x + distance2_ktvvd_x)));
					x_xy = 2;
				}
			}
		}
		else if ((distance1_ktvvd_y + distance2_ktvvd_y) < distance_ktkd_y) {
			if(distance_ktkd_y - (distance1_ktvvd_y + distance2_ktvvd_y) < y_threshhold) {
				System.out.println("distance_ktkd_y-(distance1_ktvvd_y + distance2_ktvvd_y) = " + (distance_ktkd_y-(distance1_ktvvd_y + distance2_ktvvd_y)));
				x_xy = 3;
			}
		}
		else {
			x_xy = 4;
		}
		return(x_xy);
    }
}
