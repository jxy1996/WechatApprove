package com.nc65.wx.apr;
import net.sf.json.JSONObject;
public class TestClient {
	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		obj.element("usercode", "zhenghaiqi");//审批人编码
		obj.element("ncBilltype", "2646");//单据类型
		obj.element("ncBillid", "0001A8100000000ZZNZ6");//单据主键
		obj.element("actionCode", "APPROVE");
		obj.element("approveResult", "N");//Y审批通过；R驳回制单人，N驳回上一个审批人
		obj.element("approveNote", "不同意，驳回到上一个人");
		String param = "Parameter=" + obj.toString();
		String strURL = "http://120.79.52.247:8011/portal/shareCenterApprove";
		HttpUrl http = new HttpUrl();
		try {
			String retStr = http.Submit(strURL, param);
			System.out.println(retStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
