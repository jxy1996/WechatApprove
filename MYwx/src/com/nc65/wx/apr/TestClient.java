package com.nc65.wx.apr;
import net.sf.json.JSONObject;
public class TestClient {
	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		obj.element("usercode", "zhenghaiqi");//�����˱���
		obj.element("ncBilltype", "2646");//��������
		obj.element("ncBillid", "0001A8100000000ZZNZ6");//��������
		obj.element("actionCode", "APPROVE");
		obj.element("approveResult", "N");//Y����ͨ����R�����Ƶ��ˣ�N������һ��������
		obj.element("approveNote", "��ͬ�⣬���ص���һ����");
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
