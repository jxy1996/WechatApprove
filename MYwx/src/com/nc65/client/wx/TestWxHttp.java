package com.nc65.client.wx;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
public class TestWxHttp {
	public static void main(String[] args)    
    {   
        try  
        {   
//            String urlstr="http://www.hehailin.com.cn/WxApp/CoreServlet";   
                String urlstr="http://pc-20170713tfiz:8080/ysgWeiXin/CoreServlet";   
                URL url=new URL(urlstr);   
                URLConnection conn=url.openConnection();   
  
                //�����ǹؼ�����ʾ����Ҫ���������������   
                conn.setDoOutput(true);   
  
                //������������   
                OutputStreamWriter out=new OutputStreamWriter(conn.getOutputStream());   
  
                //�������Ҷ�����һ���˺���Ϣ���ֶ�+����   
                String str="<xml>"
                		+ "<URL><![CDATA[��������ַ]]></URL>"
                		+ "<ToUserName><![CDATA[���ں�id]]></ToUserName>"
                		+ "<FromUserName><![CDATA[���openId]]></FromUserName>"
                		+ "<CreateTime>1388135075</CreateTime>"
                		+ "<MsgType><![CDATA[text]]></MsgType>"
                		+ "<Content><![CDATA[���]]></Content>"
                		+ "<MsgId>1234567890123456</MsgId>"
                		+ "</xml>";   
                //������д��   
                out.write(str);
                out.flush();
                out.close();

                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line=null;
                int lineNum=1;
                while((line=reader.readLine())!=null)
                {
                    ++lineNum;
                        System.out.println(line);
                }
        }
        catch (Exception x)
        {
            System.out.println(x.toString());
        }
    }
}
