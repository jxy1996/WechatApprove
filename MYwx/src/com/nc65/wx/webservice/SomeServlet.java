package com.nc65.wx.webservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.nc65.client.wx.Decryption;
import com.nc65.client.wx.ItfApproveErmBillLocator;
import com.nc65.client.wx.ItfApproveErmBillSOAP11BindingStub;
import com.nc65.wx.webservice.Entity.ApproveInfoVO;
import com.nc65.wx.webservice.Entity.ApproveString;
import com.nc65.wx.webservice.service.ApproveService;
import com.nc65.wx.webservice.service.impl.ApproveServiceImpl;
import com.ysg.CoreService;

import net.sf.json.JSONObject;

public class SomeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ApproveInfoVO> list = new ArrayList<ApproveInfoVO>();
	private ApproveService apService = new ApproveServiceImpl();
	

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 获得请求资源路径
		String uri = request.getRequestURI();
		// 分析请求资源路径
		String action = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
		String userAgent = request.getHeader("user-agent").toLowerCase();
		System.out.println(userAgent);
		// if(userAgent.indexOf("micromessenger")>-1){//微信客户端
		if ("/hasReject".equals(action)) {// 已驳回
			request.setAttribute("title", ApproveString.Retitle);// 已驳回单据列表
			request.setAttribute("legend", ApproveString.Relegend);// 已驳回
			request.setAttribute("type", "hasRselect");
			request.getRequestDispatcher("AjaxFirst.jsp").forward(request, response);
		} else if ("/hasApprove".equals(action)) {// 已审批
			request.setAttribute("title", ApproveString.Aptitle);//已审批单据列表
			request.setAttribute("legend", ApproveString.Aplegend);
			request.setAttribute("type", "hasAselect");
			request.getRequestDispatcher("AjaxFirst.jsp").forward(request, response);
		} else if ("/needApprove".equals(action)) {// 待审批
			request.setAttribute("title", ApproveString.Netitle);
			request.setAttribute("legend", ApproveString.Nelegend);
			request.setAttribute("type", "selectNeedApprove");
			request.getRequestDispatcher("AjaxFirst.jsp").forward(request, response);
		} else if ("/FindneedApprove".equals(action)) {
			//动态显示页面
			String findName = request.getParameter("findName");
			String type = request.getParameter("type");
			JSONObject jsonObject = new JSONObject();
			try {
				list = apService.doGetClientList(type, findName);
			} catch (ServiceException e) {
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
				jsonObject.put("error", e.getMessage());
				jsonObject.put("state", ApproveString.ERROR);
				out.write(jsonObject.toString());
				closePW(out);
				return;
			}
			jsonObject.put("indextype", list);
			jsonObject.put("state", ApproveString.SUCCESS);
			response.getWriter().write(jsonObject.toString());
		} else if ("/deGetDetail".equals(action)) {
			// 单据详情查看
			String vbillcode = request.getParameter("vbillcode");
			String url = request.getParameter("url");
			String type = null;
			try {
				list = apService.doGetClientList(vbillcode, null);
				type = apService.doDudgeByUrl(url);
			} catch (ServiceException e) {
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("Error.jsp").forward(request, response);
			}
			request.setAttribute("indextype", list);
			request.setAttribute("vbillcode", vbillcode);
			request.setAttribute("title", type);
			request.getRequestDispatcher("AjaxSecond.jsp").forward(request, response);
		} else if ("/Result".equals(action)) {
			String appr = "shaobj";// 审批人 zhangt shaobj
			String vbillno = request.getParameter("vbillcode");// 单号
			String appNote = request.getParameter("view");// 意见

			String appResult = request.getParameter("type");// 反馈 Y-通过 R-驳回制单
															// N-驳回
			String result = apService.approveBill(vbillno, appr, appResult, appNote);
			request.setAttribute("result", result);
			request.getRequestDispatcher("modal.jsp").forward(request, response);
		} else if ("/loginCheck".equals(action)) {
			String code = request.getParameter("username");
			String pwd = request.getParameter("password");
			JSONObject js = null;
			try {
				String x = apService.checkLogin(code, pwd);
				js = JSONObject.fromObject(x);
				out.print(js);
				closePW(out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("/login".equals(action)) {
			response.sendRedirect("login.jsp");
		}
		list.clear();
	// else{
	// System.out.println("PC端浏览"); 
	//	request.setAttribute("error","请在微信移动端打访问页面");
	//	request.getRequestDispatcher("Error.jsp").forward(request, response);
	// };
	// }
	}
	//关闭输出流
	public void closePW(PrintWriter pw){
		pw.flush();
		pw.close();
	}
	

}
