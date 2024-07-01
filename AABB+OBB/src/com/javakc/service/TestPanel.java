package com.javakc.service;
import java.util.Scanner;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
 * 类名：TestPanel
 * 接口名：JPanel
 * 类的功能：画图
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
     * 功能：初始化、实例化
     */
    public TestPanel(){
    	//一维数组array存储了由文件地址address1所指的csv文件中的某一行（8代表这一行所代表的顶点的8个坐标值）（外传入）
        array = new int [8];
        //二维数组arrays存储了由文件地址address2所指的csv文件中的所有行（10000代表csv文件的所有行数，8代表这一行所代表的顶点的8个坐标值）（外传入）
        arrays = new int [10000][8];
      //二维数组array_aabb存储了所有路段的上下左右的极值坐标（20代表路段数量，8代表路段的上下左右的极值坐标）（外传入）
        array_aabb = new int[20][8];
        inputobb = new OBB();
        aabb = new AABB();
        inputstep = new STEP();
        output = new CSV();
        ptp = new PTP();
    }
    
    /**
     * 方法名：showMethod
     * 犯法功能：重绘
     */
    public void showMethod(){
        this.repaint();
    }
	
    
    /**
     * 方法名：paint
     * 方法功能：绘图（顶点）
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
	 * 方法名：obbprintimage
	 * 方法功能：用户输入，并1对多绘图。
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