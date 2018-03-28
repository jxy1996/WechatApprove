package com.ysg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nc65.client.wx.Decryption;
import com.nc65.client.wx.ItfApproveErmBillLocator;
import com.nc65.client.wx.ItfApproveErmBillSOAP11BindingStub;
import com.nc65.wx.apr.HttpUrl;
import com.nc65.wx.webservice.Entity.ApproveInfoVO;
import com.ysg.menuUtils.TxtTools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ysg.weixinutils.response.Article;
import ysg.weixinutils.response.NewsMessage;
import ysg.weixinutils.response.TextMessage;

/**
 * 核心服务类
 * 
 * @date 2013-09-10
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 换行符仍然是"\n"
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
//			TxtTools.print(requestMap.toString(), TxtTools.DEBUG, null);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			//两种获取整形时间的方法。 获取到的结果表示当时时间距离1970年1月1日0时0分0秒0毫秒的毫秒数。公众平台api中消息创建时间CreateTime，它表示1970年1月1日0时0分0秒至消息创建时所间隔的秒数，注意是间隔的秒数，不是毫秒数！
			//long longTime1 = System.currentTimeMillis();  
		    //long longTime2 = new java.util.Date().getTime();  
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				 // 文本消息内容  
			    String content = requestMap.get("Content"); 
			    
				respContent = "您发送的是文本消息！\n谢谢！您发送的内容为："+content; 
				
				// 创建图文消息  
                NewsMessage newsMessage = new NewsMessage();  
                newsMessage.setToUserName(fromUserName);  
                newsMessage.setFromUserName(toUserName);  
                newsMessage.setCreateTime(new Date().getTime());  
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
                newsMessage.setFuncFlag(0);  

    			textMessage.setContent(respContent);
    			respMessage = MessageUtil.textMessageToXml(textMessage);
    			
                List<Article> articleList = new ArrayList<Article>(); 
                if(content.startsWith("lcck#"))
                {
                	String tmp = content;
                	String[] arr = tmp.split("#");
                	if(arr.length!=2)
                	{
                		textMessage.setContent("输入参数不正确!请重新输入");
                		respMessage = MessageUtil.textMessageToXml(textMessage);
                		return respMessage;
                	}
                	String vbillcode = arr[1];
                	List<ApproveInfoVO> lst = getApproveList(vbillcode);
                	Article article = new Article();  
                	article.setTitle("单号【"+vbillcode+"】查询结果如下:");
                	StringBuffer bf = new StringBuffer(0);
                	for(ApproveInfoVO v : lst)
                	{
                		bf.append("序号:"+v.getIrow()+"#发送人:"+v.getSendrman()+"#发送时间:"+v.getSenddate()+"#审批人:"+v.getApprman()+"#审批时间:"+v.getDealdate()+"#审批意见:"+v.getChecknote()+"\n\n");
                	}
                	article.setDescription(bf.toString());
                    // 图文消息中可以使用QQ表情、符号表情  
                    // 将图片置为空  
//                    article.setPicUrl("");  
//                    article.setUrl("#");  
                    articleList.add(article);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }
                else if(content.startsWith("apr#"))
                {
                	String tmp = content;
                	String arr[] = tmp.split("#");
                	if(arr.length!=5)
                	{
                		textMessage.setContent("输入参数不正确!请重新输入");
                		respMessage = MessageUtil.textMessageToXml(textMessage);
                		return respMessage;
                	}
                	final int APPR=1,BILLNO=2,RESULT=3,NOTE=4;
                	String str = approveBill(arr[APPR],arr[BILLNO],arr[RESULT],arr[NOTE]);
                    Article article1 = new Article();  
                    article1.setTitle("审批结果:");
                    article1.setDescription(str+"--来自微信审批");  
                    articleList.add(article1);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }
                else if(content.startsWith("bd#"))
                {
                	String tmp = content;
                	String arr[] = tmp.split("#");
                	if(arr.length!=3)
                	{
                		textMessage.setContent("输入参数不正确!请重新输入");
                		respMessage = MessageUtil.textMessageToXml(textMessage);
                		return respMessage;
                	}
                	String msg = bangding(arr[1], arr[2]);
                    Article article1 = new Article();  
                    article1.setTitle("绑定结果:");
                    article1.setDescription(msg+"--来自微信操作");  
                    articleList.add(article1);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
            		return respMessage;
                }
                // 单图文消息  
//                if ("1".equals(content)) {
//                    Article article = new Article();  
//                    article.setTitle("微信公众帐号开发教程1");  
//                    article.setDescription("单图文消息描述");  
//                    article.setPicUrl("http://www.huangyejishi.com/uploadfile/2013/0908/20130908104228351.jpg");  
//                    article.setUrl("http://www.baidu.com");  
//                    articleList.add(article);  
//                    // 设置图文消息个数  
//                    newsMessage.setArticleCount(articleList.size());  
//                    // 设置图文消息包含的图文集合  
//                    newsMessage.setArticles(articleList);  
//                    // 将图文消息对象转换成xml字符串  
//                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
//                }  
//                // 单图文消息---不含图片  
//                else if ("2".equals(content)) {
//                    Article article = new Article();  
//                    article.setTitle("微信公众帐号开发教程2");  
//                    // 图文消息中可以使用QQ表情、符号表情  
//                    article.setDescription("单图文消息描述" + EmojiUtils.emoji(0x1F6B9)  
//                            + "，单图文消息描述\n\n单图文消息描述\n\n单图文消息描述");  
//                    // 将图片置为空  
//                    article.setPicUrl("");  
//                    article.setUrl("http://www.baidu.com");  
//                    articleList.add(article);  
//                    newsMessage.setArticleCount(articleList.size());  
//                    newsMessage.setArticles(articleList);  
//                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
//                }  
//                // 多图文消息  
//                else if ("3".equals(content)) {  
//                    Article article1 = new Article();  
//                    article1.setTitle("微信公众帐号开发教程\n引言");  
//                    article1.setDescription("");  
//                    article1.setPicUrl("http://www.huangyejishi.com/uploadfile/2013/0908/20130908104228351.jpg");  
//                    article1.setUrl("http://www.baidu.com");  
//  
//                    Article article2 = new Article();  
//                    article2.setTitle("第2篇\n微信公众帐号的类型");  
//                    article2.setDescription("");  
//                    article2.setPicUrl("http://www.huangyejishi.com/uploadfile/2013/0908/20130908104228351.jpg");  
//                    article2.setUrl("http://www.baidu.com");  
//  
//                    Article article3 = new Article();  
//                    article3.setTitle("第3篇\n开发模式启用及接口配置");  
//                    article3.setDescription("");  
//                    article3.setPicUrl("http://www.huangyejishi.com/uploadfile/2013/0908/20130908104228351.jpg");  
//                    article3.setUrl("http://www.baidu.com");  
//  
//                    articleList.add(article1);  
//                    articleList.add(article2);  
//                    articleList.add(article3);  
//                    newsMessage.setArticleCount(articleList.size());  
//                    newsMessage.setArticles(articleList);  
//                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
//                }  
//                // 多图文消息---首条消息不含图片  
//                else if ("4".equals(content)) {  
//                    Article article1 = new Article();  
//                    article1.setTitle("微信公众帐号开发教程Java版");  
//                    article1.setDescription("");  
//                    // 将图片置为空  
//                    article1.setPicUrl("");  
//                    article1.setUrl("http://www.baidu.com");  
//  
//                    Article article2 = new Article();  
//                    article2.setTitle("第4篇\n消息及消息处理工具的封装");  
//                    article2.setDescription("");  
//                    article2.setPicUrl("http://www.huangyejishi.com/uploadfile/2013/0908/20130908104228351.jpg");  
//                    article2.setUrl("http://www.baidu.com");  
//  
//                    Article article3 = new Article();  
//                    article3.setTitle("第5篇\n各种消息的接收与响应");  
//                    article3.setDescription("");  
//                    article3.setPicUrl("http://www.huangyejishi.com/uploadfile/2013/0908/20130908104228351.jpg");  
//                    article3.setUrl("http://www.baidu.com");  
//  
//                    Article article4 = new Article();  
//                    article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");  
//                    article4.setDescription("");  
//                    article4.setPicUrl("http://www.huangyejishi.com/uploadfile/2013/0908/20130908104228351.jpg");  
//                    article4.setUrl("http://www.huangyejishi.com");  
//  
//                    articleList.add(article1);  
//                    articleList.add(article2);  
//                    articleList.add(article3);  
//                    articleList.add(article4);  
//                    newsMessage.setArticleCount(articleList.size());  
//                    newsMessage.setArticles(articleList);  
//                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
//                }  
//                // 多图文消息---最后一条消息不含图片  
//                else if ("5".equals(content)) {  
//                    Article article1 = new Article();  
//                    article1.setTitle("第7篇\n文本消息中换行符的使用");  
//                    article1.setDescription("");  
//                    article1.setPicUrl("http://www.huangyejishi.com/uploadfile/2013/0908/20130908104228351.jpg");  
//                    article1.setUrl("http://www.baidu.com");  
//  
//                    Article article2 = new Article();  
//                    article2.setTitle("第8篇\n文本消息中使用网页超链接");  
//                    article2.setDescription("");  
//                    article2.setPicUrl("http://www.huangyejishi.com/uploadfile/2013/0908/20130908104228351.jpg");  
//                    article2.setUrl("http://www.baidu.com");  
//  
//                    Article article3 = new Article();  
//                    article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");  
//                    article3.setDescription("");  
//                    // 将图片置为空  
//                    article3.setPicUrl("");  
//                    article3.setUrl("http://www.baidu.com");  
//  
//                    articleList.add(article1);  
//                    articleList.add(article2);  
//                    articleList.add(article3);  
//                    newsMessage.setArticleCount(articleList.size());  
//                    newsMessage.setArticles(articleList);  
//                    respMessage = MessageUtil.newsMessageToXml(newsMessage); 				
//			  }
                return respMessage;
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！/微笑";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是<a href=\"http://www.24hs.cn/\">链接</a>消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    NewsMessage newsMessage = new NewsMessage();  
                    newsMessage.setToUserName(fromUserName);  
                    newsMessage.setFromUserName(toUserName);  
                    newsMessage.setCreateTime(new Date().getTime());  
                    newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
                    newsMessage.setFuncFlag(0);  
                    List<Article> articleList = new ArrayList<Article>(); 
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");  
                    //电击事件
                    if (eventKey.equals(TxtTools.NEEDAPPR)) {
                    	Article article = new Article();  
                    	article.setTitle("待审批单据如下:");
                    	List<ApproveInfoVO> lst = getNeedApprove("lixiaofeng");
                    	StringBuffer bf = new StringBuffer();
						try {
							for(ApproveInfoVO v : lst)
							{
								bf.append("单据号:"+v.getVbillcode()+"#发送人:"+v.getSendrman()+"#发送时间:"+v.getSenddate()+"\n");
							}
						} catch (NullPointerException e) {
							bf.append("当前无待审批单据!");
						}
						catch (Exception e) {
							bf.append("当前操作异常!");
						}
                    	article.setDescription(bf.toString());
                        articleList.add(article);  
                        newsMessage.setArticleCount(articleList.size());  
                        newsMessage.setArticles(articleList);  
                        respMessage = MessageUtil.newsMessageToXml(newsMessage); 
                        return respMessage;
                    } else if (eventKey.equals(TxtTools.HASAPPR)) {
                    	Article article = new Article();  
                    	article.setTitle("已审批单据如下:");
                    	List<ApproveInfoVO> lst = getHasApprove("hujunfang");
                    	StringBuffer bf = new StringBuffer(0);
                    	try {
							for(ApproveInfoVO v : lst)
							{
								bf.append("单据号:"+v.getVbillcode()+"#发送人:"+v.getSendrman()+"#发送时间:"+v.getSenddate()+"\n");
							}
						} catch (NullPointerException e) {
							bf.append("当前无已审批单据!");
						}
                    	catch (Exception e) {
							bf.append("当前操作异常!");
						}
                    	article.setDescription(bf.toString());
                        articleList.add(article);  
                        newsMessage.setArticleCount(articleList.size());  
                        newsMessage.setArticles(articleList);  
                        respMessage = MessageUtil.newsMessageToXml(newsMessage); 
                        return respMessage;
                    }
                    else if (eventKey.equals(TxtTools.HASDISAPPR)) {
                    	Article article = new Article();  
                    	article.setTitle("已驳回单据如下:");
                    	List<ApproveInfoVO> lst = getHasReject("liyouzhun");
                    	StringBuffer bf = new StringBuffer(0);
                    	try {
							for(ApproveInfoVO v : lst)
							{
								bf.append("单据号:"+v.getVbillcode()+"#发送人:"+v.getSendrman()+"#发送时间:"+v.getSenddate()+"\n");
							}
						} catch (NullPointerException e) {
							bf.append("当前无已驳回单据!");
						} catch (Exception e) {
							bf.append("当前操作异常!");
						}
                    	article.setDescription(bf.toString());
                        articleList.add(article);  
                        newsMessage.setArticleCount(articleList.size());  
                        newsMessage.setArticles(articleList);  
                        respMessage = MessageUtil.newsMessageToXml(newsMessage); 
                        return respMessage;
                    }
//                    else if (eventKey.equals("14")) {  
//                        respContent = "历史上的今天菜单项被点击！";  
//                    }
                    else if (eventKey.equals(TxtTools.APPRINFOSEARCH)) {  
                    	getApproveList("bxfk130000201510300022");
                        respContent = "单据审批流程查看";  
                    } else if (eventKey.equals(TxtTools.APPRACTION)) {  
                        respContent = "审批操作";  
                    } 
//                    else if (eventKey.equals("23")) {  
//                        respContent = "美女电台菜单项被点击！";  
//                    } else if (eventKey.equals("24")) {  
//                        respContent = "人脸识别菜单项被点击！";  
//                    } else if (eventKey.equals("25")) {  
//                        respContent = "聊天唠嗑菜单项被点击！";  
//                    }
                    else if (eventKey.equals(TxtTools.BANGDING)) {
                        respContent = "绑定NC";  
                    }
//                    else if (eventKey.equals("32")) {  
//                        respContent = "电影排行榜菜单项被点击！";  
//                    } else if (eventKey.equals("33")) {  
//                        respContent = "幽默笑话菜单项被点击！";  
//                    }  
				}
			}

			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
	
	//***************************************webservice调用***********************************
	private static List<ApproveInfoVO> getHasReject(String usercode)
	{
		try {
			ItfApproveErmBillLocator locator = new ItfApproveErmBillLocator(); //获取连接对象
			java.net.URL url = new java.net.URL(locator.getItfApproveErmBillSOAP11port_httpAddress());//获取webservice地址
			ItfApproveErmBillSOAP11BindingStub pss = new ItfApproveErmBillSOAP11BindingStub(url,locator);//实例化对象
			String x = pss.hasReject(usercode, null);//调用websrvice方法
			JSONObject js = JSONObject.fromObject(x);
			JSONObject data = (JSONObject) JSONObject.fromObject(js.get("data"));
			JSONArray arr = null;
			try {
				arr = data.getJSONArray("hasReject");
			} catch (Exception e) {
				return null;
			}
			int size = arr.size();//获取数组长度
			List lst = new ArrayList();
			for(int i=0;i<size;i++)
			{
				JSONObject json = (JSONObject)arr.getJSONObject(i);
				ApproveInfoVO info = new ApproveInfoVO();
				info.setOrgname(json.getString("orgname"));
				info.setDbilldate(json.getString("dbilldate"));
				info.setSenddate(json.getString("senddate"));
				info.setSendrcode(json.getString("sendrcode"));
				info.setSendrman(json.getString("sendrman"));
				info.setVbillcode(json.getString("vbillcode"));
				lst.add(info);
			}
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

	private static List<ApproveInfoVO> getHasApprove(String usercode)
	{
		try {
			ItfApproveErmBillLocator locator = new ItfApproveErmBillLocator(); //获取连接对象
			java.net.URL url = new java.net.URL(locator.getItfApproveErmBillSOAP11port_httpAddress());//获取webservice地址
			ItfApproveErmBillSOAP11BindingStub pss = new ItfApproveErmBillSOAP11BindingStub(url,locator);//实例化对象
			String x = pss.hasApprove(usercode, null);//调用websrvice方法
			JSONObject js = JSONObject.fromObject(x);
			JSONObject data = (JSONObject) JSONObject.fromObject(js.get("data"));
			JSONArray arr = null;
			try {
				arr = data.getJSONArray("hasApprove");
			} catch (Exception e) {
				return null;
			}
			int size = arr.size();//获取数组长度
			List lst = new ArrayList();
			for(int i=0;i<size;i++)
			{
				JSONObject json = (JSONObject)arr.getJSONObject(i);
				ApproveInfoVO info = new ApproveInfoVO();
				info.setOrgname(json.getString("orgname"));
				info.setDbilldate(json.getString("dbilldate"));
				info.setSenddate(json.getString("senddate"));
				info.setSendrcode(json.getString("sendrcode"));
				info.setSendrman(json.getString("sendrman"));
				info.setVbillcode(json.getString("vbillcode"));
				lst.add(info);
			}
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

	private static List<ApproveInfoVO> getNeedApprove(String usercode)
	{
		try {
			ItfApproveErmBillLocator locator = new ItfApproveErmBillLocator(); //获取连接对象
			java.net.URL url = new java.net.URL(locator.getItfApproveErmBillSOAP11port_httpAddress());//获取webservice地址
			ItfApproveErmBillSOAP11BindingStub pss = new ItfApproveErmBillSOAP11BindingStub(url,locator);//实例化对象
			String x = pss.needApprove(usercode, null);//调用websrvice方法
			JSONObject js = JSONObject.fromObject(x);
			JSONObject data = (JSONObject) JSONObject.fromObject(js.get("data"));
			JSONArray arr = null;
			try {
				arr = data.getJSONArray("needApprove");
			} catch (Exception e) {
				return null;
			}
			int size = arr.size();//获取数组长度
			List lst = new ArrayList();
			for(int i=0;i<size;i++)
			{
				JSONObject json = (JSONObject)arr.getJSONObject(i);
				ApproveInfoVO info = new ApproveInfoVO();
				info.setOrgname(json.getString("orgname"));
				info.setDbilldate(json.getString("dbilldate"));
				info.setSenddate(json.getString("senddate"));
				info.setSendrcode(json.getString("sendrcode"));
				info.setSendrman(json.getString("sendrman"));
				info.setVbillcode(json.getString("vbillcode"));
				lst.add(info);
			}
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

	private static List<ApproveInfoVO> getApproveList(String vbillcode)
	{
		try {
			ItfApproveErmBillLocator locator = new ItfApproveErmBillLocator(); //获取连接对象
			java.net.URL url = new java.net.URL(locator.getItfApproveErmBillSOAP11port_httpAddress());//获取webservice地址
			ItfApproveErmBillSOAP11BindingStub pss = new ItfApproveErmBillSOAP11BindingStub(url,locator);//实例化对象
			String x = pss.approveErmBill(vbillcode, "扩展参数");//调用websrvice方法
			JSONObject js = JSONObject.fromObject(x);
			JSONObject data = (JSONObject) JSONObject.fromObject(js.get("data"));
			JSONArray arr = null;
			try {
				arr = data.getJSONArray("approve");
			} catch (Exception e) {
				return null;
			}
			int size = arr.size();//获取数组长度
			List<ApproveInfoVO> lst = new ArrayList();
			for(int i=0;i<size;i++)
			{
				JSONObject json = (JSONObject)arr.getJSONObject(i);
				ApproveInfoVO info = new ApproveInfoVO();
				info.setDealdate(json.getString("dealdate"));
				info.setApprcode(json.getString("apprcode"));
				info.setApprman(json.getString("apprman"));
				info.setBillid(json.getString("billid"));
				info.setDbilldate(json.getString("dbilldate"));
				info.setApproveresult(json.getString("approveresult"));
				info.setDjlxbm(json.getString("djlxbm"));
				info.setIrow(json.getString("irow"));
				info.setOrgname(json.getString("orgname"));
				info.setSenddate(json.getString("senddate"));
				info.setSendrcode(json.getString("sendrcode"));
				info.setSendrman(json.getString("sendrman"));
				info.setSpztname(json.getString("spztname"));
				info.setChecknote(json.getString("checknote"));
				info.setTimes(json.getString("times"));
				info.setVbillcode(json.getString("vbillcode"));
				lst.add(info);
			}
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	public static String approveBill(String appr,String vbillno,String appResult,String appNote)
	{
		try {
			ItfApproveErmBillLocator locator = new ItfApproveErmBillLocator(); //获取连接对象
			java.net.URL url = new java.net.URL(locator.getItfApproveErmBillSOAP11port_httpAddress());//获取webservice地址
			ItfApproveErmBillSOAP11BindingStub pss = new ItfApproveErmBillSOAP11BindingStub(url,locator);//实例化对象
			String[] x = pss.getPK(vbillno);//调用websrvice方法
			JSONObject obj = new JSONObject();
			obj.element("usercode", appr);//审批人编码
			obj.element("ncBilltype", x[0]);//单据类型
			obj.element("ncBillid", x[1]);//单据主键
			obj.element("actionCode", "APPROVE");
			obj.element("approveResult", appResult);//Y审批通过；R驳回制单人，N驳回上一个审批人
			obj.element("approveNote", appNote);
			obj.element("workflowtype","3");//增加工作类型属性  3代表审批流，4代表工作流
			String param = "Parameter=" + obj.toString();
			String strURL = "http://hehailin.com.cn:8011/portal/shareCenterApprove";
			HttpUrl http = new HttpUrl();
			String retStr = http.Submit(strURL, param);
			JSONObject objx = new JSONObject().fromObject(retStr);
			String code = (String) objx.get("Code");
			String result = null;
			if("500".equals(code))
			{
				String data = (String) objx.get("Data");
				result = "审批失败!"+data;
			}
			else if("200".equals(code))
			{
//				JSONObject data = (JSONObject) objx.get("Data");
				result = "审批成功!";
			}
			return result;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static String bangding(String code,String pwd)
	{
		try {
			ItfApproveErmBillLocator locator = new ItfApproveErmBillLocator(); //获取连接对象
			java.net.URL url = new java.net.URL(locator.getItfApproveErmBillSOAP11port_httpAddress());//获取webservice地址
			ItfApproveErmBillSOAP11BindingStub pss = new ItfApproveErmBillSOAP11BindingStub(url,locator);//实例化对象
			
			code = Decryption.encrypt(code, Decryption.key);
			pwd = Decryption.encrypt(pwd, Decryption.key);

			String x = pss.needBindings(code, pwd);//调用websrvice方法
			JSONObject js = JSONObject.fromObject(x);
			String msg = (String) js.get("msg");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}