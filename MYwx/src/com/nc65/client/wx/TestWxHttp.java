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
  
                //这里是关键，表示我们要向链接里输出内容   
                conn.setDoOutput(true);   
  
                //获得连接输出流   
                OutputStreamWriter out=new OutputStreamWriter(conn.getOutputStream());   
  
                //这里是我定义了一组账号信息，字段+数据   
                String str="<xml>"
                		+ "<URL><![CDATA[你的请求地址]]></URL>"
                		+ "<ToUserName><![CDATA[公众号id]]></ToUserName>"
                		+ "<FromUserName><![CDATA[你的openId]]></FromUserName>"
                		+ "<CreateTime>1388135075</CreateTime>"
                		+ "<MsgType><![CDATA[text]]></MsgType>"
                		+ "<Content><![CDATA[你好]]></Content>"
                		+ "<MsgId>1234567890123456</MsgId>"
                		+ "</xml>";   
                //把数据写入   
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
