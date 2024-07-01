package com.javakc.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * 类名：CSV
 * 类功能：创建新csv并输入碰撞结果
 * @author DarlinZZZ
 *
 */
public class CSV
{
    STEP s = new STEP();
    
    /**
     * 方法名：csv
     * 方法功能：输入碰撞路段pathID以及碰撞结果
     * @param x_threshhold 投影在x轴上的安全阈值
     * @param y_threshhold 投影在y轴上的安全阈值
     * @param step 步长
     */
    public void csv(double x_threshhold, double y_threshhold, int step){
    	long startTime = System.currentTimeMillis();
    	//一维数组state存储了所有AABB碰撞的路段对（一对有两条路段）的PathID，以及是否碰撞(contact/discontact)（3分为路段名1，路段名2，contact/discontact?）
    	String [] state = new String[3];
    	String [] path = {"path1ID","path2ID","state","","contactcube1","contactcube2"};
    	long endTime = System.currentTimeMillis();
    	System.out.println("COUNTING running time = " + (endTime - startTime) + "ms");
    	long startTime1 = System.currentTimeMillis();
    	s.step(x_threshhold, y_threshhold, step);
    	int num = 0;
    	ArrayList<String[]> list = new ArrayList<>();
    	list.add(path);
    	for(num = 0; num <55; num++) {
    		state = s.getstateByNum(num);	
    		list.add(state);
    	}
        boolean Flag=createCsvFile(list,"C:\\CSVDir","csvFile");
        long endTime1 = System.currentTimeMillis();
        System.out.println("output running time = " + (endTime1 - startTime1) + "ms");
        if (Flag == true)
        {
            System.out.print("CSV created success");
        }else {
            System.out.print("CSV created failed");
        }
    }
 
    /**
     * 方法名：createCsvFile
     * 方法功能：在指定路径下创建csv文件
     * @param rows 数组
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return flag
     */
    public static boolean createCsvFile(List<String[]> rows, String filePath, String fileName)
    {

        boolean flag = true;
 

        BufferedWriter fileOutputStream = null;
 
        try{

            String fullPath = filePath+ File.separator+fileName+".csv";
            File file = new File(fullPath);
            if (!file.getParentFile().exists())    
            {
                file.getParentFile().mkdirs();
            }
            if (file.exists())     
            {
                file.delete();
            }
            file = new File(fullPath);
            file.createNewFile();
 
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMaximumFractionDigits(10);   
 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 
            fileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"GB2312"),1024);
 
            Iterator<String[]> ite = rows.iterator();
 
            while (ite.hasNext())
            {
                Object[] rowData = (Object[])ite.next();
                for(int i=0;i<rowData.length;i++)
                {
                    Object obj = rowData[i];  
                    String field = "";
                    if (null != obj)
                    {
                        if (obj.getClass() == String.class)   
                        {
                            field = (String)obj;
                        }else if (obj.getClass() == Double.class || obj.getClass() == Float.class)  
                        {
                            field = formatter.format(obj);  
                        }else if (obj.getClass() == Integer.class || obj.getClass() == Long.class | obj.getClass() == Short.class || obj.getClass() == Byte.class)
                        {
            
                            field += obj;
                        }else if (obj.getClass() == Date.class)  
                        {
                            field = sdf.format(obj);
                        }else {
                            field = " ";  
                        }
                       
                        if (i<rowData.length-1)     
                        {
                            fileOutputStream.write("\""+field+"\""+",");
                        }else {
         
                            fileOutputStream.write("\""+field+"\"");
                        }
                    }
       
                    if (ite.hasNext())
                    {
                        //fileOutputStream.newLine();
                    }
                }
                fileOutputStream.newLine();  
            }
            fileOutputStream.flush();
        }catch (Exception e)
        {
            flag = false;
            e.printStackTrace();
        }finally {
            try{
                fileOutputStream.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return flag;
    }
}