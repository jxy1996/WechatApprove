package com.nc65.wx.webservice.utils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.nc65.client.wx.Decryption;
import com.nc65.client.wx.ItfApproveErmBillLocator;
import com.nc65.client.wx.ItfApproveErmBillSOAP11BindingStub;
import com.nc65.wx.apr.HttpUrl;
import com.nc65.wx.webservice.Entity.ApproveInfoVO;
import com.nc65.wx.webservice.Entity.ApproveString;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AllApproveUtils {
	private Logger log = Logger.getLogger(AllApproveUtils.class.getSimpleName());
	private List<ApproveInfoVO> lst = new ArrayList<ApproveInfoVO>();

	/**
	 * 实例化接口对象
	 * 
	 * @return
	 */
	public ItfApproveErmBillSOAP11BindingStub doGetItf() {
		ItfApproveErmBillSOAP11BindingStub pss = null;
		try {
			ItfApproveErmBillLocator locator = new ItfApproveErmBillLocator(); // 获取连接对象
			java.net.URL url = new java.net.URL(locator.getItfApproveErmBillSOAP11port_httpAddress());// 获取webservice地址
			pss = new ItfApproveErmBillSOAP11BindingStub(url, locator);// 实例化对象
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.INFO, e.getMessage());
		}
		return pss;
	}
	/**
	 * 校检绑定账号
	 * @param code 账号 
	 * @param psw 密码
	 * @return
	 */
	public String chackLogin(String code,String psw){
		String x= null;
		try {
			code = Decryption.encrypt(code, Decryption.key);
			psw = Decryption.encrypt(psw, Decryption.key);
			 x = doGetItf().needBindings(code, psw);
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.INFO, e.getMessage());
		}
		return x;
	}
	
	/**
	 * 
	 * @param vbillcode  单据号
	 * @param appr 审批人编码
	 * @param appResult 审批状态 Y审批通过；R驳回制单人，N驳回上一个审批人
	 * @param appNote 审批意见
	 * @return
	 * @throws ServletException 
	 */
	public String approveBill(String vbillcode,String appr,String appResult,String appNote) throws ServletException {
		try {
			String [] x = doGetItf().getPK(vbillcode);
			JSONObject obj = new JSONObject();
			obj.element("usercode", appr);//审批人编码
			obj.element("ncBilltype", x[0]);//单据类型
			obj.element("ncBillid", x[1]);//单据主键
			obj.element("actionCode", "APPROVE");
			obj.element("approveResult", appResult);
			obj.element("approveNote", appNote);
			obj.element("workflowtype","3");//增加工作类型属性  3代表审批流，4代表工作流
			String param = "Parameter=" + obj.toString();
			String strURL = ApproveString.strURL;
			HttpUrl http = new HttpUrl();
			String retStr = null;
			try {
				retStr = http.Submit(strURL, param);
			} catch (Exception e) {
				e.printStackTrace();
				log.log(Level.INFO, e.getMessage());
			}
			return retStr;
		} catch (RemoteException e) {
			e.printStackTrace();
			log.log(Level.INFO, e.getMessage());
		}
		return null;
	}

	/**
	 * 调用接口返回所有用户（暂写死）的数据
	 * 
	 * @return 已驳回
	 */
	public String doGetHasRejectStr() {
		try {
			return doGetItf().hasReject("zhoutingting", "扩展参数");// 调用websrvice方法
																// 用户暂时写死
		} catch (RemoteException e) {
			e.printStackTrace();
			log.log(Level.INFO, e.getMessage());
		}
		return null;
	}

	/**
	 * 同上
	 * 
	 * @return 已审批
	 */
	public String doGetHasApproveStr() {
		try {
			return doGetItf().hasApprove("zhoutingting", "扩展参数");
		} catch (RemoteException e) {
			e.printStackTrace();
			log.log(Level.INFO, e.getMessage());
		}
		return null;
	}

	/**
	 * 同上
	 * 
	 * @return 待审批
	 */
	public String doGetNeedApproveStr() {
		try {
			return doGetItf().needApprove("zhoutingting", "扩展参数");
		} catch (RemoteException e) {
			e.printStackTrace();
			log.log(Level.INFO, e.getMessage());
		}
		return null;
	}

	/**
	 * 单据详情
	 * 
	 * @param vbillcode
	 *            单据号
	 * @return
	 */
	public String doGetNeedAppByCode(String vbillcode) {
		try {
			return doGetItf().approveErmBill(vbillcode, "扩展参数");
		} catch (RemoteException e) {
			e.printStackTrace();
			log.log(Level.INFO, e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param type
	 *            查询类型 hasApprove:已审批 needApprove:待审批 hasReject:已驳回
	 * @return
	 * @throws ServiceException
	 */
	public JSONArray dogetArr(String type) throws ServiceException {
		String x = null;
		if ("needApprove".equals(type)) {
			x = doGetNeedApproveStr();
		} else if ("hasApprove".equals(type)) {
			x = doGetHasApproveStr();
		} else if ("hasReject".equals(type)) {
			x = doGetHasRejectStr();
		} else {
			x = doGetNeedAppByCode(type);
		}
		if (x == null)
			throw new ServiceException("系统繁忙，请稍后重试");
		JSONObject js = JSONObject.fromObject(x);
		JSONObject data = (JSONObject) JSONObject.fromObject(js.get("data"));
		JSONArray arr = null;
		if (!type.equals("needApprove") && !type.equals("hasApprove") && !type.equals("hasReject")) {
			try {
				arr = data.getJSONArray("approve");
				return arr;
			} catch (Exception e) {
				e.printStackTrace();
				log.log(Level.INFO, e.getMessage());
				throw new ServiceException("查询不到该业务，请稍后重试");
			}
		}
		try {
			arr = data.getJSONArray(type);
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.INFO, e.getMessage());
			throw new ServiceException("查询不到该业务，请稍后重试");
		}
		return arr;
	}

	/**
	 * 生成ApproveInfoVO
	 * 
	 * @param json
	 * @param i
	 * @return
	 */
	public ApproveInfoVO doGetApproveInfoVo(JSONObject json) {
		ApproveInfoVO info = new ApproveInfoVO();
		if (!json.has("je")) {
			info.setJe("该单据无金额");
		} else {
			info.setJe(json.getString("je"));
		}
		if (!json.has("zhy")) {
			info.setZhy("该单据无摘要");
		} else {
			info.setZhy(json.getString("zhy"));
		}
		if (!json.has("dealdate")) {
			info.setChecknote("");
		} else {
			info.setDealdate(json.getString("dealdate"));
		}
		if (!json.has("checknote")) {
			info.setChecknote("");
		} else {
			info.setChecknote(json.getString("checknote"));
		}
		if (!json.has("approveresult")) {
			info.setApproveresult("");
		} else {
			info.setApproveresult(json.getString("approveresult"));
		}
		info.setApprcode(json.getString("apprcode"));
		info.setApprman(json.getString("apprman"));
		info.setBillid(json.getString("billid"));
		info.setDbilldate(json.getString("dbilldate"));
		info.setDjlxbm(json.getString("djlxbm"));
		info.setIrow(json.getString("irow"));
		info.setOrgname(json.getString("orgname"));
		info.setSenddate(json.getString("senddate"));
		info.setSendrcode(json.getString("sendrcode"));
		info.setSendrman(json.getString("sendrman"));
		info.setSpztname(json.getString("spztname"));
		info.setTimes(json.getString("times"));
		info.setVbillcode(json.getString("vbillcode"));
		info.setDjlxzw(json.getString("djlxzw"));

		return info;
	}

	/**
	 * 
	 * @param type
	 *            查询类型 hasApprove:已审批 needApprove:待审批 hasReject:已驳回
	 * @return
	 * @throws ServiceException
	 */
	public List<ApproveInfoVO> doGetClientList(String type) throws ServiceException {
		JSONArray arr = dogetArr(type);
		int size = arr.size();// 获取数组长度
		for (int i = 0; i < size; i++) {
			JSONObject json = (JSONObject) arr.getJSONObject(i);
			lst.add(doGetApproveInfoVo(json));
		}
		return lst;
	}

	/**
	 * 
	 * @param findName
	 *            筛选单据
	 * @param type
	 *            查询类型 hasApprove:已审批 needApprove:待审批 hasReject:已驳回
	 * @return
	 * @throws ServiceException
	 */
	public List<ApproveInfoVO> doGetClientList(String findName, String type) throws ServiceException {
		JSONArray arr = dogetArr(type);
		int size = arr.size();// 获取数组长度
		for (int i = 0; i < size; i++) {
			JSONObject json = (JSONObject) arr.getJSONObject(i);
			if (findName.equals(json.getString("djlxzw"))) {
				lst.add(doGetApproveInfoVo(json));
			}
		}
		return lst;
	}
}
