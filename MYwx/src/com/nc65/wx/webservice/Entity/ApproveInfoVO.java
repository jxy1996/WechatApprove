package com.nc65.wx.webservice.Entity;

public class ApproveInfoVO {
	/**单据主键*/
	private String billid;
	/**单据类型编码*/
	private String djlxbm;
	/**审批人编码*/
	private String apprcode;
//*************************************以上参数外系统调用需要
	/**审批人姓名*/
	private String apprman;
	/**行号*/
	private String irow;
	/**单据号*/
	private String vbillcode;
	/**公司名称*/
	private String orgname;
	/**到手时间*/
	private String times;
	/**单据日期*/
	private String dbilldate;
	/**发送时间*/
	private String senddate;
	/**发送人编码*/
	private String sendrcode;
	/**发送人姓名*/
	private String sendrman;
	/**审批状态*/
	private String spztname;
	/**处理时间*/
	private String dealdate;
	/**审批结果*/
	private String approveresult;
	/**审批意见*/
	private String checknote;
	/**单据类型名称*/
	private String djlxzw;
	/** 金额*/
	private String je;
	/** 摘要*/
	private String zhy;
	
	public String getJe() {
		return je;
	}
	public void setJe(String je) {
		this.je = je;
	}
	public String getZhy() {
		return zhy;
	}
	public void setZhy(String zhy) {
		this.zhy = zhy;
	}
	public String getDjlxzw() {
		return djlxzw;
	}
	public void setDjlxzw(String djlxzw) {
		this.djlxzw = djlxzw;
	}
	public String getSenddate() {
		return senddate;
	}
	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}
	public String getSendrman() {
		return sendrman;
	}
	public void setSendrman(String sendrman) {
		this.sendrman = sendrman;
	}
	public String getSpztname() {
		return spztname;
	}
	public void setSpztname(String spztname) {
		this.spztname = spztname;
	}
	public String getApprman() {
		return apprman;
	}
	public void setApprman(String apprman) {
		this.apprman = apprman;
	}
	public String getBillid() {
		return billid;
	}
	public void setBillid(String billid) {
		this.billid = billid;
	}
	public String getSendrcode() {
		return sendrcode;
	}
	public void setSendrcode(String sendrcode) {
		this.sendrcode = sendrcode;
	}
	public String getVbillcode() {
		return vbillcode;
	}
	public void setVbillcode(String vbillcode) {
		this.vbillcode = vbillcode;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getIrow() {
		return irow;
	}
	public void setIrow(String irow) {
		this.irow = irow;
	}
	public String getDbilldate() {
		return dbilldate;
	}
	public void setDbilldate(String dbilldate) {
		this.dbilldate = dbilldate;
	}
	public String getDjlxbm() {
		return djlxbm;
	}
	public void setDjlxbm(String djlxbm) {
		this.djlxbm = djlxbm;
	}
	public String getApprcode() {
		return apprcode;
	}
	public void setApprcode(String apprcode) {
		this.apprcode = apprcode;
	}
	public String getDealdate() {
		return dealdate;
	}
	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}
	public String getChecknote() {
		return checknote;
	}
	public void setChecknote(String checknote) {
		this.checknote = checknote;
	}
	public String getApproveresult() {
		return approveresult;
	}
	public void setApproveresult(String approveresult) {
		this.approveresult = approveresult;
	}
	@Override
	public String toString() {
		return "ApproveInfoVO [billid=" + billid + ", djlxbm=" + djlxbm
				+ ", apprcode=" + apprcode + ", apprman=" + apprman + ", irow="
				+ irow + ", vbillcode=" + vbillcode + ", orgname=" + orgname
				+ ", times=" + times + ", dbilldate=" + dbilldate
				+ ", senddate=" + senddate + ", sendrcode=" + sendrcode
				+ ", sendrman=" + sendrman + ", spztname=" + spztname
				+ ", dealdate=" + dealdate + ", approveresult=" + approveresult
				+ ", checknote=" + checknote + ", djlxzw=" + djlxzw + ", je="
				+ je + ", zhy=" + zhy + "]";
	}

}
