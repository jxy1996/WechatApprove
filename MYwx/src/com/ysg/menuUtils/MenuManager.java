package com.ysg.menuUtils;
/**
 * 菜单管理器类
 * 
 * @date 2013-09-10
 */
public class MenuManager {
	public static void main(String[] args) {
		//公众号名称
		String[] names = {"何海林订阅号","鸿鑫制造服务号"};
		//公众号appId
		String[] appIDs = {"wx03f50a537286c643","wx7ec638d3787666fc"};
		//公众号ppSecret
		String[] appSecrets = {"5088eb7d2d11a4b74bd69ff32de884a2","1768863aa4af262e141af2da7e201d49"};
		//公众号ID
		String[] pks = {"gh_65d887d8d9a3","gh_c551594ca2fd"};
		int index = 1;
		// 第三方用户唯一凭证
		String appId = appIDs[index];
		// 第三方用户唯一凭证密钥
		String appSecret = appSecrets[index];
//4_IsPFRMr0tdfKs9qXqDwfWfMpr_V9RmSjQJQFt17VghUFi3FW2Fi4m-UKsXT4xsuo0KqO0GtLLLT1GL34ppzkKwJbaMWQYT0tuBxt6c36FDz4BqcyNHjhkPHbbCZ0W82kMcjNaBEEnrBNBNauJEKbABAEZF
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
//		at = new AccessToken();
//4_IsPFRMr0tdfKs9qXqDwfWfMpr_V9RmSjQJQFt17VghUFi3FW2Fi4m-UKsXT4xsuo0KqO0GtLLLT1GL34ppzkKwJbaMWQYT0tuBxt6c36FDz4BqcyNHjhkPHbbCZ0W82kMcjNaBEEnrBNBNauJEKbABAEZF
//		at.setToken("4_IsPFRMr0tdfKs9qXqDwfWfMpr_V9RmSjQJQFt17VghUFi3FW2Fi4m-UKsXT4xsuo0KqO0GtLLLT1GL34ppzkKwJbaMWQYT0tuBxt6c36FDz4BqcyNHjhkPHbbCZ0W82kMcjNaBEEnrBNBNauJEKbABAEZF");
//		at.setExpiresIn(7200);

        System.out.println(at.getToken()); 
		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(),at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				System.out.println("菜单创建成功！");
			else
				System.out.println("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("待审批");
		btn11.setType("click");
		btn11.setKey(TxtTools.NEEDAPPR);

		ClickButton btn12 = new ClickButton();
		btn12.setName("已审批");
		btn12.setType("click");
		btn12.setKey(TxtTools.HASAPPR);

		ClickButton btn13 = new ClickButton();
		btn13.setName("已驳回");
		btn13.setType("click");
		btn13.setKey("13");
//
//		CommonButton btn14 = new CommonButton();
//		btn14.setName("历史上的今天");
//		btn14.setType("click");
//		btn14.setKey("14");

		ClickButton btn21 = new ClickButton();
		btn21.setName("流程查看");
		btn21.setType("click");
		btn21.setKey(TxtTools.APPRINFOSEARCH);

		ClickButton btn22 = new ClickButton();
		btn22.setName("流程审批");
		btn22.setType("click");
		btn22.setKey(TxtTools.APPRACTION);

		ClickButton btn23 = new ClickButton();
		btn23.setName("绑定NC");
		btn23.setType("click");
		btn23.setKey(TxtTools.BANGDING);
//
//		CommonButton btn24 = new CommonButton();
//		btn24.setName("人脸识别");
//		btn24.setType("click");
//		btn24.setKey("24");
//
//		CommonButton btn25 = new CommonButton();
//		btn25.setName("聊天唠嗑");
//		btn25.setType("click");
//		btn25.setKey("25");
//		ViewButton btn11 = new ViewButton();  
//	    btn11.setName("测试11");  
//	    btn11.setUrl("http://www.qq.com");  
	    ViewButton btn31 = new ViewButton();
		btn31.setName("待审批");
		btn31.setType("view");
		btn31.setUrl("http://www.hehailin.com.cn/MYwx/needApprove.do");

		ViewButton btn32 = new ViewButton();
		btn32.setName("已审批");
		btn32.setType("view");
		btn32.setUrl("http://www.hehailin.com.cn/MYwx/hasApprove.do");

		ViewButton btn33 = new ViewButton();
		btn33.setName("已驳回");
		btn33.setType("view");
		btn33.setUrl("http://www.hehailin.com.cn/MYwx/hasReject.do");
		
		ViewButton btn34 = new ViewButton();
		btn34.setName("绑定NC");
		btn34.setType("view");
		btn34.setUrl("http://www.hehailin.com.cn/MYwx/login.jsp");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("协同查询");
		mainBtn1.setSub_button(new ClickButton[] { btn11, btn12, btn13 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("流程操作");
		mainBtn2.setSub_button(new ClickButton[] { btn21, btn22,btn23 });

		ViewButtons mainBtn3 = new ViewButtons();
		mainBtn3.setName("更多体验");
		mainBtn3.setSub_button(new ViewButton[] { btn31,btn32,btn33,btn34 });

		/**
		 * 这是公众号目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1,mainBtn2,mainBtn3 });

		return menu;
	}
}
