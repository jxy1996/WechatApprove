package com.nc65.wx.webservice.service;

import java.util.List;

import javax.servlet.ServletException;
import javax.xml.rpc.ServiceException;

import com.nc65.wx.webservice.Entity.ApproveInfoVO;
import com.nc65.wx.webservice.Entity.ApproveString;

public interface ApproveService {

	/**
	 * 返回单据列表
	 * @param type  查询类型 hasApprove:已审批 needApprove:待审批 hasReject:已驳回
	 * @param findName 筛选单据
	 * @return
	 * @throws ServiceException 
	 */
	public List<ApproveInfoVO>doGetClientList(String type,String findName) throws ServiceException;
	
	/**
	 * 单据审批 
	 * @param vbillcode  单据号
	 * @param appr 审批人编码
	 * @param appResult 审批状态 Y审批通过；R驳回制单人，N驳回上一个审批人
	 * @param appNote 审批意见
	 * @return
	 * @throws ServletException 
	 */
	public String approveBill(String vbillcode,String appr,String appResult,String appNote) throws ServletException;
	

	/**
	 * 根据Url判断返回页面标题
	 * @param url
	 * @return
	 * @throws ServiceException 
	 */
	public String doDudgeByUrl(String url) throws  ServiceException;
	
	/**
	 * 
	 * 校检绑定账号
	 * @param code 账号 
	 * @param psw 密码
	 * @throws ServiceException
	 */
	public String checkLogin(String code,String psw)throws  ServiceException;
}
