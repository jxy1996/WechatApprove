package com.nc65.wx.webservice.service.impl;

import java.util.List;

import javax.servlet.ServletException;
import javax.xml.rpc.ServiceException;

import com.nc65.wx.webservice.Entity.ApproveInfoVO;
import com.nc65.wx.webservice.Entity.ApproveString;
import com.nc65.wx.webservice.service.ApproveService;
import com.nc65.wx.webservice.utils.AllApproveUtils;

import net.sf.json.JSONObject;

/**
 * 业务层
 * @author jxy
 *
 */
public class ApproveServiceImpl implements ApproveService{
	private AllApproveUtils appUtils = new AllApproveUtils();

	@Override
	public List<ApproveInfoVO> doGetClientList(String type, String findName) throws ServiceException {
		if(type==null&&type!="needApprove"&&type!="hasApprove"&&type!="hasReject"){
			throw new ServiceException("系统繁忙，请稍后重试");
		}
		List<ApproveInfoVO> list = null;
		if(findName == null){
			list =  appUtils.doGetClientList(type);
		}else{
			list = appUtils.doGetClientList(findName, type);
		}
		if( list.size()<1){
			throw new ServiceException("您没有"+findName+"类型的单据");
		}
		return list;
	}

	@Override
	public String approveBill(String vbillcode, String appr, String appResult, String appNote) throws ServletException {
		String  retStr = appUtils.approveBill(vbillcode, appr, appResult, appNote);
		if(retStr==null)throw new ServletException("系统繁忙，请稍后重试");
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
//			JSONObject data = (JSONObject) objx.get("Data");
			result = "审批成功!";
		}
		return result;
	}

	@Override
	public String doDudgeByUrl(String url) throws ServiceException {
		if("hasApprove".equals(url)){
			return ApproveString.AptitleDe;
		}else if("needApprove".equals(url)){
			return ApproveString.NetitleDe;
		}else if("hasReject.do".equals(url)){
			return ApproveString.RetitleDe;
		}else{
			throw new ServiceException("系统繁忙，请稍后重试");
		}
	}

	@Override
	public String checkLogin(String code, String psw) throws ServiceException {
		//账号密码为空在login.js有过校检，此处不再重复
		String result = appUtils.chackLogin(code, psw);
		if(result == null)throw new ServiceException("系统繁忙，请稍后重试");
		return result;
	}

}
