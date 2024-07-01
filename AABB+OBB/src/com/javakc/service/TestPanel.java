package com.javakc.service;
import java.util.Scanner;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
 * ������TestPanel
 * �ӿ�����JPanel
 * ��Ĺ��ܣ���ͼ
 * @author DarlinZZZ
 *
 */
public class TestPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	OBB inputobb;
	AABB aabb;
	STEP inputstep;
	CSV output;
	PTP ptp;
    
    private int [] array;
    private int [][] array_aabb;
    private int [][] arrays;

    /**
     * ���ܣ���ʼ����ʵ����
     */
    public TestPanel(){
    	//һά����array�洢�����ļ���ַaddress1��ָ��csv�ļ��е�ĳһ�У�8������һ��������Ķ����8������ֵ�����⴫�룩
        array = new int [8];
        //��ά����arrays�洢�����ļ���ַaddress2��ָ��csv�ļ��е������У�10000����csv�ļ�������������8������һ��������Ķ����8������ֵ�����⴫�룩
        arrays = new int [10000][8];
      //��ά����array_aabb�洢������·�ε��������ҵļ�ֵ���꣨20����·��������8����·�ε��������ҵļ�ֵ���꣩���⴫�룩
        array_aabb = new int[20][8];
        inputobb = new OBB();
        aabb = new AABB();
        inputstep = new STEP();
        output = new CSV();
        ptp = new PTP();
    }
    
    /**
     * ��������showMethod
     * �������ܣ��ػ�
     */
    public void showMethod(){
        this.repaint();
    }
	
    
    /**
     * ��������paint
     * �������ܣ���ͼ�����㣩
     */
	public void paint(Graphics g){
        super.paint(g);
        if(array!=null){
            g.drawLine(array[0],array[1],array[2],array[3]);
            g.drawLine(array[4],array[5],array[6],array[7]);
            g.drawLine(array[0],array[1],array[6],array[7]);
            g.drawLine(array[2],array[3],array[4],array[5]);
            g.drawLine(725-600,750-600,775-600,750-600);
            g.drawLine(750-600,725-600,750-600,775-600);
        }
        if(arrays!=null){
        		for(int t=0; t<10000; t++) {
            	g.drawLine(arrays[t][0],arrays[t][1],arrays[t][2],arrays[t][3]);
                g.drawLine(arrays[t][4],arrays[t][5],arrays[t][6],arrays[t][7]);
                g.drawLine(arrays[t][0],arrays[t][1],arrays[t][6],arrays[t][7]);
                g.drawLine(arrays[t][2],arrays[t][3],arrays[t][4],arrays[t][5]);
            }
        }
        if(array_aabb!=null) {
        	for(int ab=0; ab<20; ab++) {
        		g.drawRect(array_aabb[ab][0], array_aabb[ab][3], Math.abs(array_aabb[ab][4]-array_aabb[ab][0]), Math.abs(array_aabb[ab][1]-array_aabb[ab][5]));
        	}
        }
    }
	
	/**
	 * ��������obbprintimage
	 * �������ܣ��û����룬��1�Զ��ͼ��
	 */
	public void obbprintimage() {
		try (Scanner in = new Scanner(System.in)) {
			System.out.println("Which csv do you get cube from ?");
			String csv = in.nextLine();
			if (csv.equals("bezi10000")) {
				System.out.println("Which state of cube in bezi ?");
				int bezi_which = in.nextInt();
				System.out.println("Which to begin in bezi2 ?");
				int bezi2_begin = in.nextInt();
				System.out.println("how many to test ?");
				int time = in.nextInt();
				inputobb.count_bezi(bezi_which,bezi2_begin,time);
			}
			else if (csv.equals("bezi210000")) {
				System.out.println("Which state of cube in bezi2 ?");
				int bezi2_which = in.nextInt();
				System.out.println("Which to begin in bezi ?");
				int bezi_begin = in.nextInt();
				System.out.println("how many to test ?");
				int time = in.nextInt();
				inputobb.count_bezi2(bezi2_which,bezi_begin,time);
			}
			else {
				System.out.println("There is no such file name !");
			}
		}
		array = inputobb.getarrays();
		
		for(int n = 0; n<10000; n++)
		{
			arrays[n] = inputobb.getArrayByNum(n);
		}
		showMethod();
	}
}