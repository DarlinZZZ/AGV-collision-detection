package com.javakc.service;

/**
 * ������CountMethod
 * �๦�ܣ����е����ݴ����еļ��㷽��
 * @author DarlinZZZ
 *
 */
public class CountMethod {

	/**
	 * ��������radians_x
	 * �������ܣ���֪�������꣬���߶ζ���x��Ļ��ȣ������ػ���
	 * @param coordinates1_x ��һ����ĺ�����
	 * @param coordinates1_y ��һ�����������
	 * @param coordinates2_x �ڶ�����ĺ�����
	 * @param coordinates2_y �ڶ������������
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
	 * ��������radians_y
	 * �������ܣ���֪�������꣬���߶ζ���y��Ļ��ȣ������ػ���
	 * @param coordinates1_x ��һ����ĺ�����
	 * @param coordinates1_y ��һ�����������
	 * @param coordinates2_x �ڶ�����ĺ�����
	 * @param coordinates2_y �ڶ������������
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
	 * ��������pointtopointdistance
	 * �������ܣ�����㵽��ʸ�����Ȳ�����
	 * ����ֵ���ͣ�������
	 * @param coordinates1_x ��һ����ĺ�����
	 * @param coordinates1_y ��һ�����������
	 * @param coordinates2_x �ڶ�����ĺ�����
	 * @param coordinates2_y �ڶ������������
	 * @return ptpvd
	 */
	public double pointtopointvectordistance(double coordinates1_x, double coordinates1_y, double coordinates2_x, double coordinates2_y) {
		double ptpvd = Math.abs(coordinates1_x * coordinates2_x + coordinates1_y * coordinates2_y);  
	    return ptpvd;	
	}
		
	/**
	 * ��������pointtopointdistance
	 * �������ܣ�����㵽���߶γ��Ȳ�����
	 * ����ֵ���ͣ�������
	 * @param coordinates1_x ��һ����ĺ�����
	 * @param coordinates1_y ��һ�����������
	 * @param coordinates2_x �ڶ�����ĺ�����
	 * @param coordinates2_y �ڶ������������
	 * @return ptpd
	 */
	public double pointtopointdistance(double coordinates1_x, double coordinates1_y, double coordinates2_x, double coordinates2_y) {
		double ptpd = Math.sqrt((coordinates2_y - coordinates1_y) * (coordinates2_y - coordinates1_y) + (coordinates2_x - coordinates1_x) * (coordinates2_x - coordinates1_x));
	    return ptpd;	
	}

	/**
	 * ��������comparison_1
	 * �������ܣ�����ĳһ������������Ƿ���ײ
	 * ����ֵ���壺1=����ײ�� 0=��ײ
	 * @param distance1_ktvvd_x ��һ���������ĵ��߳��е����x����ͶӰ����
	 * @param distance2_ktvvd_x �ڶ����������ĵ��߳��е����x����ͶӰ����
	 * @param distance1_ktvvd_y ��һ���������ĵ��߳��е����y����ͶӰ����
	 * @param distance2_ktvvd_y �ڶ����������ĵ��߳��е����y����ͶӰ����
	 * @param distance_ktkd_x ��һ���������ĵ��ڶ����������ĵ��߶ξ���
	 * @param distance_ktkd_y ��һ���������ĵ��ڶ����������ĵ��߶ξ���
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
	 * ��������comparison_sc
	 * �������ܣ�������һ������������Ƿ���ײ
	 * ����ֵ���壺1=����ײ�� 0=��ײ
	 * @param distance1_ktvvd_x_sc ��һ���������ĵ��߳��е������һ�����ε�x����ͶӰ����
	 * @param distance2_ktvvd_x_sc �ڶ����������ĵ��߳��е������һ�����ε�x����ͶӰ����
	 * @param distance1_ktvvd_y_sc ��һ���������ĵ��߳��е������һ�����ε�y����ͶӰ����
	 * @param distance2_ktvvd_y_sc �ڶ����������ĵ��߳��е������һ�����ε�y����ͶӰ����
	 * @param distance_ktkd_x_sc ��һ���������ĵ��ڶ����������ĵ��߶ξ���
	 * @param distance_ktkd_y_sc ��һ���������ĵ��ڶ����������ĵ��߶ξ���
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
 	 * ��������comparison_xy
 	 * �������ܣ�����������ײ��cube��ÿ��������һ��cube�������а�λ�÷���ľ������
 	 * ����ֵ���ͣ�int��
 	 * ����ֵ���壺1=x,y�ᶼ���ܽӽ���2=x��ӽ���3=y��ӽ�;4=x,y�ᶼ�ӽ���
 	 * @param x_threshhold x���ϵİ�ȫ��ֵ
 	 * @param y_threshhold y���ϵİ�ȫ��ֵ
 	 * @param distance1_ktvvd_x ��һ���������ĵ��߳��е����x����ͶӰ����
	 * @param distance2_ktvvd_x �ڶ����������ĵ��߳��е����x����ͶӰ����
	 * @param distance1_ktvvd_y ��һ���������ĵ��߳��е����y����ͶӰ����
	 * @param distance2_ktvvd_y �ڶ����������ĵ��߳��е����y����ͶӰ����
	 * @param distance_ktkd_x ��һ���������ĵ��ڶ����������ĵ��߶ξ���
	 * @param distance_ktkd_y ��һ���������ĵ��ڶ����������ĵ��߶ξ���
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
