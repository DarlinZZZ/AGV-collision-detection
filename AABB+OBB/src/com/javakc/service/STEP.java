package com.javakc.service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * ������STEP
 * ��Ĺ��ܣ�����ȫͼ����·�ε��Ż���ײ���
 * @author DarlinZZZ
 *
 */
public class STEP {
	AABB inputaabb = new AABB();
	CountMethod CM = new CountMethod();
	//��ά����arrays_bezi�洢��map��ĳһ��·�ε����ж�����������ݣ�1000����ĳһ·�ε����ж���������8����ĳһ������������ݣ�
	double [][] arrays_bezi = new double [1000][8];
	//��ά����arrays_bezi2�洢��map��ĳһ��·�ε����ж�����������ݣ�1000����ĳһ·�ε����ж���������8����ĳһ������������ݣ�
	double [][] arrays_bezi2 = new double [1000][8];
	//��ά����aabbcontact�洢������AABB��ײ��·�ζԣ�һ��������·�Σ���PathID��55����AABB��ײ��·�ζԣ�2�������·�ε�PathID�����⴫�룩
	int[][] aabbcontact = new int[55][2];
	//��ά����state�洢������AABB��ײ��·�ζԣ�һ��������·�Σ���PathID���Լ��Ƿ���ײ(contact/discontact)����ײ�����������ײ�㣩��6��Ϊ·����1��·����2��contact/discontact?���ո񣬾�����ײ��1��������ײ��2��
	String [][] state = new String[55][6];
		
	/**
	 * ��������getstateByNum
	 * �������ܣ���ȡĳЩ��·�Ƿ���ײ��״̬��contact/discontact)
	 * ����ֵ���ͣ�һά�ַ�������
	 * ����ֵ���壺contact/discontact
	 * @param num ��ȡ������
	 * @return state[]
	 */
	public String[] getstateByNum(int num) {
		return state[num];
	}
	
	/**
	 * ��������getarrays_bezi
	 * �������ܣ��ӵ�һ��bezier�л�ȡĳЩ�������������
	 * ����ֵ���ͣ�һά����������
	 * ����ֵ���壺�������������
	 * @param num ��ȡ������
	 * @return arrays_bezi[]
	 */
	public double[] getarrays_bezi(int num) {
		return arrays_bezi[num];
	}
	
	/**
	 * ��������getarrays_bezi2
	 * �������ܣ��ӵڶ���bezier�л�ȡĳЩ�������������
	 * ����ֵ���ͣ�һά����������
	 * ����ֵ���壺�������������
	 * @param num
	 * @return arrays_bezi2[]
	 */
	public double[] getarrays_bezi2(int num) {
		return arrays_bezi2[num];
	}
	
	/**
	 * ��������step
	 * �������ܣ�����ȫͼ������·�Σ��Ƚ���AABB�ļ�⣬Ȼ���ٽ��зֲ�����OBB��⡣
	 * @param x_threshhold ͶӰ��x���ϵİ�ȫ��ֵ
     * @param y_threshhold ͶӰ��y���ϵİ�ȫ��ֵ
     * @param step ����
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
	 * ��������itas_bezi
	 * �������ܣ���������a1��ָ��ĳ��bezier�ľ��嶥���������ݴ����ڴ棨��ά���飩��
	 * @param a ����state�е�����
	 * @param a1 ��ȡ��·��
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
	 * ��������itas_bezi2
	 * �������ܣ���������a2��ָ��ĳ��bezier�ľ��嶥���������ݴ����ڴ棨��ά���飩��
	 * @param a ����state�е�����
	 * @param a2 ��ȡ��·��
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
	 * ��������step_input
	 * �������ܣ��������ݴ��루���������ʱ�䣩
	 * @param a ����state�е�����
	 * @param a1 ����1����������ȡ��·�Σ�
	 * @param a2 ����2����������ȡ��·�Σ�
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
 	 * ��������printout1
	 * �������ܣ��������Ƿ���ȷ
 	 */
 	public void printout1() {  //for check
 		for(int i=0; i<1000; i++){
            for (int j=0; j<8; j++)
                System.out.println("arrays_bezi["+i+"]["+j+"]" + arrays_bezi[i][j]);
    	}
 	}
 	
 	/**
 	 * ��������printout1
	 * �������ܣ��������Ƿ���ȷ
 	 */
 	public void printout2() {  //for check
 		for(int i=0; i<1000; i++){
            for (int j=0; j<8; j++)
                System.out.println("arrays_bezi2["+i+"]["+j+"]" + arrays_bezi2[i][j]);
    	}
 	}

 	// input part end
 	
 	/**
 	 * ��������step_circu
 	 * �������ܣ�����·��֮���������ײ���
 	 * ����ֵ���壺1=discontact;0=contact
 	 * @param x_threshhold ͶӰ��x���ϵİ�ȫ��ֵ
     * @param y_threshhold ͶӰ��y���ϵİ�ȫ��ֵ
     * @param step ����
 	 * @param a ����state�е�����
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
 	 * ��������step_further
 	 * �������ܣ��������·���н�Ϊ�ӽ��Ķ������Χ��������С����ײ��⡣
 	 * ����ֵ���壺1=discontact;0=contact
 	 * @param step �ֲ���
 	 * @param step2 �ֲ���
 	 * @param step_f ϸ����step_further
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
 	 * ��������step_single
 	 * �������ܣ���������֮�����ײ���
 	 * ����ֵ���壺1=discontact;0=contact��
 	 * @param step
 	 * @param step2
 	 * @return 1/0
 	 */
 	public int step_single(int step, int step2) {
 		//��һ�����Σ�һ���㣩�Ĵ�ֱ�ڳ��ķ��������ת����
 		double rotation1_x = CM.radians_x(arrays_bezi[step][0], arrays_bezi[step][1], arrays_bezi[step][2], arrays_bezi[step][3]);
 		//��һ�����Σ�һ���㣩�Ĵ�ֱ�ڿ�ķ��������ת����
        double rotation1_y = -CM.radians_y(arrays_bezi[step][0], arrays_bezi[step][1], arrays_bezi[step][2], arrays_bezi[step][3]);
       
        //��һ�����Σ�һ���㣩�ļ������ĵĺ����꣨������ͨ�����ᣩ
        double a1x = (arrays_bezi[step][0] + arrays_bezi[step][4])/2;
        //��һ�����Σ�һ���㣩�ļ������ĵ������꣨������ͨ�����ᣩ
        double a1y = (arrays_bezi[step][1] + arrays_bezi[step][5])/2;
        
        //��һ�����Σ�һ���㣩�ļ������ĵ������е��ʸ���ĺ�����ֵ��������ͨ�����ᣩ
        double ktm1x1 = (arrays_bezi[step][6] + arrays_bezi[step][0])/2-(arrays_bezi[step][0] + arrays_bezi[step][4])/2;  //kernel to the middle vertex vextor x value
        //��һ�����Σ�һ���㣩�ļ������ĵ������е��ʸ����������ֵ��������ͨ�����ᣩ
      	double ktm1y1 = (arrays_bezi[step][7] + arrays_bezi[step][1])/2-(arrays_bezi[step][1] + arrays_bezi[step][5])/2;  //kernel to the middle vertex vextor y value
      	//��һ�����Σ�һ���㣩�ļ������ĵ�����е��ʸ���ĺ�����ֵ��������ͨ�����ᣩ
      	double ktm1x2 = (arrays_bezi[step][6] + arrays_bezi[step][4])/2-(arrays_bezi[step][0] + arrays_bezi[step][4])/2;  //kernel to the other middle vertex vextor x value
      	//��һ�����Σ�һ���㣩�ļ������ĵ�����е��ʸ����������ֵ��������ͨ�����ᣩ
      	double ktm1y2 = (arrays_bezi[step][7] + arrays_bezi[step][5])/2-(arrays_bezi[step][1] + arrays_bezi[step][5])/2;  //kernel to the other middle vertex vextor y value
      	
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
      	double dis1_ktvvd_x = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_y, b1y_y) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_y, b1y_y);
      	//��һ�����Σ�һ���㣩�ļ������ĵ������е��ʸ���ڵ�һ�����δ�ֱ�ڳ��ķ������ϵ�ͶӰ����
      	double dis1_ktvvd_y = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_x, b1y_x) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_x, b1y_x);
      	
      	//���´������ݷ���һ����ֻ��������һ������
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
       	//�ڶ������Σ�һ���㣩�ļ������ĵ������е��ʸ���ڵ�һ�����δ�ֱ�ڿ�ķ������ϵ�ͶӰ����
       	double dis2_ktvvd_x = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_y, b2y_y) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_y, b2y_y);
       	//�ڶ������Σ�һ���㣩�ļ������ĵ������е��ʸ���ڵ�һ�����δ�ֱ�ڳ��ķ������ϵ�ͶӰ����
       	double dis2_ktvvd_y = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_x, b2y_x) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_x, b2y_x);
 	    
       	//���µ��������εļ������ĵľ������
       	//���Ȼ���ĳһ�����εĴ�ֱ�ڳ��ķ��������ת����
       	double rotation_ptp_x = rotation1_x;
       	//���Ȼ���ĳһ�����εĴ�ֱ�ڿ�ķ��������ת����
      	double rotation_ptp_y = rotation1_y;
      	
      	//���Ȼ���ĳһ�����εĴ�ֱ�ڳ��ķ��������ת���ȵ�����ֵ
      	double ptpx_x = Math.cos(rotation_ptp_x);
      	//���Ȼ���ĳһ�����εĴ�ֱ�ڳ��ķ��������ת���ȵ�����ֵ
      	double ptpy_x = Math.sin(rotation_ptp_x);
        //���Ȼ���ĳһ�����εĴ�ֱ�ڿ�ķ��������ת���ȵ�����ֵ
      	double ptpx_y = Math.cos(rotation_ptp_y);
        //���Ȼ���ĳһ�����εĴ�ֱ�ڿ�ķ��������ת���ȵ�����ֵ
      	double ptpy_y = Math.sin(rotation_ptp_y);
      	
      	//��������pointtopointvectordistance���㷽����ȷʵ�Ƿ�������
    	//���㼸�����ĵ��������ĵ��߶��ڴ�ֱ�ڿ�ķ������ϵ�ͶӰ���ȣ����Ȼ���ĳһ�����εĴ�ֱ�ڿ�ķ��������ת���ȣ� 
      	double dis_ktkd_x = CM.pointtopointvectordistance(a2x-a1x, a2y-a1y, ptpx_y, ptpy_y) ;
      	//���㼸�����ĵ��������ĵ��߶��ڴ�ֱ�ڳ��ķ������ϵ�ͶӰ���ȣ����Ȼ���ĳһ�����εĴ�ֱ�ڳ��ķ��������ת���ȣ� 
    	double dis_ktkd_y = CM.pointtopointvectordistance(a2x-a1x, a2y-a1y, ptpx_x, ptpy_x) ;
    	
        //check if contact
    	/**
    	 *˵��1��com_1��com_scÿ�������ж���ĳ��cube������������Ƚϣ�һ�����������ᣬҪ������һ�αȽϡ�
    	 *˵��2��com_1���ڵ�һ�����ε��������������ͶӰ�Ƚϣ�com_sc���ڵڶ������ε��������������ͶӰ�Ƚϡ�
    	 */
    	//com_1 = 0/1���жϻ��ڵ�һ�����ε��������������Ƿ���ײ
      	int com_1 = CM.comparison_1(dis1_ktvvd_x, dis2_ktvvd_x, dis1_ktvvd_y, dis2_ktvvd_y, dis_ktkd_x, dis_ktkd_y);  //compare the distance of two cubes if contact or not
      	
      	//if this cube's axis is disqualified, than compare the other cube's axis. to determine whether two cubes are compact should compare four axises in total.
      	if (com_1 == 0) {  
      		//�ڶ������Σ�һ���㣩�Ĵ�ֱ�ڳ��ķ��������ת����
      		double rotation2_x_sc = CM.radians_x(arrays_bezi2[step2][0], arrays_bezi2[step2][1], arrays_bezi2[step2][2], arrays_bezi2[step2][3]);
      		//�ڶ������Σ�һ���㣩�Ĵ�ֱ�ڿ�ķ��������ת����
            double rotation2_y_sc = -CM.radians_y(arrays_bezi2[step2][0], arrays_bezi2[step2][1], arrays_bezi2[step2][2], arrays_bezi2[step2][3]);

         	//�ڶ������Σ�һ���㣩�Ĵ�ֱ�ڳ��ķ��������ת���ȵ�����ֵ
          	double b1x_x_sc = Math.cos(rotation2_x_sc);
          	//�ڶ������Σ�һ���㣩�Ĵ�ֱ�ڳ��ķ��������ת���ȵ�����ֵ
          	double b1y_x_sc = Math.sin(rotation2_x_sc);
          	//�ڶ������Σ�һ���㣩�Ĵ�ֱ�ڿ�ķ��������ת���ȵ�����ֵ
          	double b1x_y_sc = Math.cos(rotation2_y_sc);
          	//�ڶ������Σ�һ���㣩�Ĵ�ֱ�ڿ�ķ��������ת���ȵ�����ֵ
          	double b1y_y_sc = Math.sin(rotation2_y_sc);
          	
          	//��һ�����Σ�һ���㣩�ļ������ĵ������е��ʸ���ڵڶ������δ�ֱ�ڿ�ķ������ϵ�ͶӰ����
          	double dis1_ktvvd_x_sc = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_y_sc, b1y_y_sc) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_y_sc, b1y_y_sc);
          	//��һ�����Σ�һ���㣩�ļ������ĵ������е��ʸ���ڵڶ������δ�ֱ�ڳ��ķ������ϵ�ͶӰ����
          	double dis1_ktvvd_y_sc = CM.pointtopointvectordistance(ktm1x1, ktm1y1, b1x_x_sc, b1y_x_sc) + CM.pointtopointvectordistance(ktm1x2, ktm1y2, b1x_x_sc, b1y_x_sc);
      	
          	//����תΪ������ֵ��Ϊ����ʾ��������һ�����εļ���͵ڶ������ηֿ�
          	double b2x_x_sc = Math.cos(rotation2_x_sc);
          	double b2y_x_sc = Math.sin(rotation2_x_sc);
          	double b2x_y_sc = Math.cos(rotation2_y_sc);
          	double b2y_y_sc = Math.sin(rotation2_y_sc);
      	
          	//�ڶ������Σ�һ���㣩�ļ������ĵ������е��ʸ���ڵڶ������δ�ֱ�ڿ�ķ������ϵ�ͶӰ����
          	double dis2_ktvvd_x_sc = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_y_sc, b2y_y_sc) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_y_sc, b2y_y_sc);
          	//�ڶ������Σ�һ���㣩�ļ������ĵ������е��ʸ���ڵڶ������δ�ֱ�ڿ�ķ������ϵ�ͶӰ����
          	double dis2_ktvvd_y_sc = CM.pointtopointvectordistance(ktm2x1, ktm2y1, b2x_x_sc, b2y_x_sc) + CM.pointtopointvectordistance(ktm2x2, ktm2y2, b2x_x_sc, b2y_x_sc);
          
          	//check pass
            //��λ�����һ�����εĴ�ֱ�ڳ��ķ��������ת���ȵ�����ֵ
          	double ptpx_x_sc = Math.cos(rotation2_x_sc);
          	//��λ�����һ�����εĴ�ֱ�ڳ��ķ��������ת���ȵ�����ֵ
          	double ptpy_x_sc = Math.sin(rotation2_x_sc);
          	//��λ�����һ�����εĴ�ֱ�ڿ�ķ��������ת���ȵ�����ֵ
          	double ptpx_y_sc = Math.cos(rotation2_y_sc);
          	//��λ�����һ�����εĴ�ֱ�ڿ�ķ��������ת���ȵ�����ֵ
          	double ptpy_y_sc = Math.sin(rotation2_y_sc);
          	
          	//���㼸�����ĵ��������ĵ��߶��ڴ�ֱ�ڿ�ķ������ϵ�ͶӰ���ȣ���λ�����һ�����εĴ�ֱ�ڿ�ķ��������ת���ȣ� 
        	double dis_ktkd_x_sc = CM.pointtopointvectordistance(a2x-a1x, a2y-a1y, ptpx_y_sc, ptpy_y_sc) ;
        	//���㼸�����ĵ��������ĵ��߶��ڴ�ֱ�ڳ��ķ������ϵ�ͶӰ���ȣ���λ�����һ�����εĴ�ֱ�ڳ��ķ��������ת���ȣ� 
        	double dis_ktkd_y_sc = CM.pointtopointvectordistance(a2x-a1x, a2y-a1y, ptpx_x_sc, ptpy_x_sc) ;

        	//�ٴαȽϵڶ������εķ������ϵ�ͶӰ�Ƚϣ����com_1��com_sc����ʾ��ײ����ײ��������ײ
        	int com_sc = CM.comparison_sc(dis1_ktvvd_x_sc, dis2_ktvvd_x_sc, dis1_ktvvd_y_sc, dis2_ktvvd_y_sc, dis_ktkd_x_sc, dis_ktkd_y_sc);  //compare the distance of two cubes if contact or not
      	    return(com_sc);
      	}
    	return (com_1);
 	}
 	
 	/**
 	 * ��������step_sepa
 	 * �������ܣ�����������ײ��cube��ÿ��������һ��cube�������а�λ�÷��࣬�Դ��ж��Ƿ�ӽ���ȫ��ֵ
 	 * ����ֵ���ͣ�int��
 	 * ����ֵ���壺1=x,y�ᶼ���ܽӽ���2=x��ӽ���3=y��ӽ�;4=x,y�ᶼ�ӽ���
 	 * @param x_threshhold
 	 * @param y_threshhold
 	 * @param step
 	 * @param step2
 	 * @return 1/0
 	 */
 	public int step_sepa(double x_threshhold, double  y_threshhold, int step, int step2) {
 		//���鿴������step_single��������һ��
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
    	//cp_xy����1��2��3��4���ֱ��Ӧ����ײʱ�����������
    	int cp_xy = CM.comparison_xy(x_threshhold,y_threshhold,dis1_ktvvd_x, dis2_ktvvd_x, dis1_ktvvd_y, dis2_ktvvd_y, dis_ktkd_x, dis_ktkd_y);
    	System.out.println(cp_xy);
    	return (cp_xy);
 	}
  	
 	
}
