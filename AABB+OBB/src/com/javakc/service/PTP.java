package com.javakc.service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * ������PTP
 * �๦�ܣ�ģʽ2��PATHvsPATH,����·�ε�OBB���
 * @author DarlinZZZ
 *
 */
public class PTP {
	AABB inputaabb = new AABB();
	STEP s = new STEP();
	CountMethod CM = new CountMethod();
	//��ά����arrays_bezi�洢address1��ָ��csv�ļ����������ݣ�1000����������8������ε��ĸ������������ݣ�
	double [][] arrays_bezi = new double [1000][8];
	//��ά����arrays_bezi2�洢address2��ָ��csv�ļ����������ݣ�1000����������8������ε��ĸ������������ݣ�
	double [][] arrays_bezi2 = new double [1000][8];
	//һά�ַ�������state�洢���ǵ�һ��PathID���ڶ���PathID���Լ��Ƿ���ײ��contact/discontact)
	String[] state = new String[3];
	
	/**
	 * ��������getarrays_bezi
	 * �������ܣ�����arrays_bezi��ά�����е�ĳЩԪ��
	 * ����ֵ���ͣ�һά����������
	 * ����ֵ���壺��һ�����Ƚ�bezier�е�ĳЩ�������������
	 * @param num ��ȡ����
	 * @return arrays_bezi[]
	 */
	public double[] getarrays_bezi(int num) {
		return arrays_bezi[num];
	}
	
	/**
	 * ��������getarrays_bezi2
	 * �������ܣ�����arrays_bezi2��ά�����е�ĳЩԪ��
	 * ����ֵ���ͣ�һά����������
	 * ����ֵ���壺��һ�����Ƚ�bezier�е�ĳЩ�������������
	 * @param num ��ȡ����
	 * @return arrays_bezi2[]
	 */
	public double[] getarrays_bezi2(int num) {
		return arrays_bezi2[num];
	}

	/**
	 * ��������pathtopath
	 * �������ܣ�����bezier����OBB���
	 * @param xyuzhi ͶӰ��x���ϵİ�ȫ��ֵ
 	 * @param yyuzhi ͶӰ��y���ϵİ�ȫ��ֵ
 	 * @param step ����
	 * @param addr1 csv�ļ�1��·����ַ
	 * @param addr2 csv�ļ�2��·����ַ
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
	 * ��������itas_bezi
	 * �������ܣ���ȡ�û�����ĵ�ַ���ҽ���ַ�µ�csv�ļ������������ݴ����ڴ��У�arrays_bezi��
	 * @param address csv�ļ�1��·����ַ
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
	 * ��������itas_bezi2
	 * �������ܣ���ȡ�û�����ĵ�ַ���ҽ���ַ�µ�csv�ļ������������ݴ����ڴ��У�arrays_bezi2��
	 * @param address csv�ļ�2��·����ַ
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
	 * ��������ptp_input
	 * �������ܣ����ݴ���
	 * @param addr1 csv�ļ�1��·����ַ
	 * @param addr2 csv�ļ�2��·����ַ
	 */
 	public void ptp_input(String addr1, String addr2) {  //check pass
 		long startTime = System.currentTimeMillis();
 		itas_bezi(addr1);
 		itas_bezi2(addr2);
        long endTime = System.currentTimeMillis();
    	System.out.println("input1+2 running time = " + (endTime - startTime) + "ms");
 	}

 	/**
 	 * ��������ptp_circu
	 * �������ܣ�����·��OBB���
	 * ����ֵ���壺1=����ײ��0=��ײ
 	 * @param xyuzhi ͶӰ��x���ϵİ�ȫ��ֵ
 	 * @param yyuzhi ͶӰ��y���ϵİ�ȫ��ֵ
 	 * @param step ����
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