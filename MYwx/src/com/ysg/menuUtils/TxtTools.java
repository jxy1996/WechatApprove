package com.ysg.menuUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class TxtTools {
//	public static Long VERSION = Long.valueOf("1489766400000");
	public static final boolean TYPE1 = true;
	public static final boolean isTest = false;//by hhl
	public static String NC_CODE = "";
	public final static boolean printSelect = true,testData = true,printXML = false,printDebug = true,printError = true,printNormal = false;
	public final static int DEBUG = 0,NORMAL = 1,ERROR = -1,SELECT = 2,XML=-2;
	
	public final static String NEEDAPPR = "11",HASAPPR = "12",HASDISAPPR = "13";
	public final static String APPRINFOSEARCH = "21",APPRACTION = "22",BANGDING = "23";
//	public final static String BANGDING = "31";

	//----------------------------------------测试的时候可以开启打印语句
	public static void print(String content)
	{
		if(isTest)
		if(print(DEBUG))
		{
			writeFile(getName(DEBUG),content);
		}
	}
	public static void print(String content,int n)
	{
		if(print(n))
		{
			writeFile(getName(n),content);
		}
	}
	public static void print(String content,String name)
	{
		if(print(DEBUG))
		{
			writeFile(name,name+"****"+content);
		}
	}
	public static void print(String content,int n,String path)
	{
		if(print(n))
		{
			writeFile(getName(n),content,path);
		}
	}
	public static void print(String content,String name,int n,String path)
	{
		if(print(n))
		{
			writeFile(name,content,path);
		}
	}
	private static String getName(int n)
	{
		if(n==DEBUG)return "Debug";
		if(n==NORMAL)return "Normal";
		if(n==ERROR)return "Error";
		if(n==SELECT)return "Select";
		if(n==XML)return "Xml";
		return "ELS";
	}
	
	private static boolean print(int n)
	{
		if(n==DEBUG)return printDebug;
		if(n==NORMAL)return printNormal;
		if(n==ERROR)return printError;
		if(n==SELECT)return printSelect;
		if(n==XML)return printXML;
		return false;
	}
	public static void writeFile(String name,String content)// throws Exception
	{
		String url = "c:\\";
		writeFile(url+getTime(0)+"-"+name+".log","\n"+getTime(1)+" "+content,DEBUG);
	}

	public static void writeFile(String name,String content,String path)// throws Exception
	{
		String url = "c:\\";
		if(url==null)
		{
			return ;
		}
		writeFile(url+getTime(0)+"-"+name+".log","\n"+getTime(1)+" "+content,DEBUG);
	}
	
	private static String getTime(int t) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String str = dateformat.format(System.currentTimeMillis());
		String[] sp = str.split(" ");
		if(t==0)str = sp[0];
		if(t==1)str = sp[1];
		return str;
	}
	/**
	 * 写文件
	 * @param fileName	路径
	 * @param content	内容
	 */
	private static void writeFile(String fileName, String content,int n) {
		try {
			//打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			getFolder(fileName.substring(0,fileName.lastIndexOf('\\')));
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 如果文件夹不存在就创建文件夹
	 * @param path
	 * @return
	 */
	private static boolean getFolder(String path)
	{
		 File file = new File(path);
		 if(!file.exists())
		 file.mkdirs();
		 return true;
	}
	
    public static void printLine(){
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        // 注意!这里是下标为2的,而不是为1的
        StackTraceElement tmp = trace[2];
        System.out.println( tmp.getClassName() + "." + tmp.getMethodName() 
            + "(" + tmp.getFileName() + ":" + tmp.getLineNumber() + ")" );
    }
}
