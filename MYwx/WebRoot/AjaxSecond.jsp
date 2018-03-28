<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1"
	name="viewport">
<title id="title">${title}</title>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
	<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/Second.js"></script>

	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="exampleModalLabel">
						审批结果&emsp;<small>Result</small>
					</h4>
				</div>
				<div class="modal-body">
					<!-- 此处放具体页面的位置 -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary">返回待审批单据页面</button>
				</div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- modal -->

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2 col-md-2"></div>
			<div class="col-xs-12 col-sm-8 col-md-8 table-responsive ">
				<h3>
					单据详情<small>&emsp;${indextype[0].djlxzw}</small>
				</h3>
				<br>
				<table id="detail" class="table table-hover ">
					<tbody>
						<tr>
							<th><strong>单据号：</strong></th>
							<th>${vbillcode}</th>
						</tr>
						<tr>
							<th><strong>制单人：</strong></th>
							<th>${indextype[indextype.size()-1].sendrman}</th>
						</tr>
						<tr>
							<th><strong>金额：</strong></th>
							<th>${indextype[0].je}</th>
						</tr>
						<tr>
							<th><strong>摘要：</strong></th>
							<th>${indextype[0].zhy}</th>
						</tr>
						<tr>
							<th><strong>当前审批人：</strong></th>
							<th>${indextype[0].apprman}</th>
						</tr>
						<tr>
							<th><strong>公司名称：</strong></th>
							<th>${indextype[0].orgname}</th>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-sm-2 col-md-2"></div>
		</div>
		<!-- 单据详情显示 -->

	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2 col-md-2"></div>
			<div class="col-xs-12 col-sm-8 col-md-8 ">
				<h3>
					审批流程<small>&emsp;单据号：${vbillcode}</small>
				</h3>
				<br>
				<table class="table table-striped table-hover table-responsive">
					<tr>
						<th>发送人</th>
						<th>发送日期</th>
						<th>审批人</th>
						<th>审批日期</th>
						<th>审批历时</th>
						<th>审批意见</th>
						<th>批语</th>
					</tr>
					<tr>
						<c:forEach items="${indextype}" var="e" varStatus="s">
							<tr>
								<td>${e.sendrman}</td>
								<td>${e.senddate}</td>
								<td>${e.apprman}</td>
								<td>${e.dealdate}</td>
								<td>${e.times}</td>
								<td>${e.approveresult}</td>
								<td>${e.checknote}</td>
							</tr>
						</c:forEach>
						<!-- 审批意见循环 -->
				</table>
				<div id="lab" class="col-lg-6 col-centered">
					<div class="input-group dropup">

						<input type="text" class="form-control" aria-label="..."
							placeholder="审批意见" id="views">
						<div class="input-group-btn">
							<button type="button" class="btn btn-default dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">
								审批 <span class="caret"></span> <span class="sr-only">Toggle
									Dropdown</span>
							</button>
							<ul id="typeUl" class="dropdown-menu dropdown-menu-right">
								<li><a href="#">通过</a></li>
								<li><a href="#">驳回制单人</a></li>
								<li><a href="#">驳回上一步</a></li>
							</ul>
						</div>
						<!-- /btn-group -->
					</div>
					<!-- /input-group -->

				</div>
				<!-- /.col-lg-6 -->

			</div>
			<!-- 审批信息 -->
		</div>
	</div>
</body>
</html>