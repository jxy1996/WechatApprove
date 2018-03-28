<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>${title}</title>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/variables.css" />
</head>
<body>
	
	<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/First.js"></script>

	<ul class="nav nav-pills">
		<li role="presentation" class="dropdown"><a
			class="dropdown-toggle" data-toggle="dropdown" href="#" role="button"
			aria-haspopup="true" aria-expanded="false">财务领域<span
				class="caret"></span>
		</a>
			<ul class="dropdown-menu">
				<li><a href="#">报销单</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">借款单</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">付款单</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">付款申请单</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">付款结算单</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">收款结算单</a></li>
			</ul></li>
			

		<li role="presentation" class="dropdown"><a
			class="dropdown-toggle" data-toggle="dropdown" href="#" role="button"
			aria-haspopup="true" aria-expanded="false"> HR领域 <span
				class="caret"></span>
		</a>
			<ul class="dropdown-menu">
				<li><a href="#">入职申请</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">转正申请</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">离职申请</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">薪资发放申请</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">调配申请</a></li>
			</ul></li>

		<li role="presentation" class="dropdown"><a
			class="dropdown-toggle" data-toggle="dropdown" href="#" role="button"
			aria-haspopup="true" aria-expanded="false">固定资产领域 <span
				class="caret"></span>
		</a>
			<ul class="dropdown-menu">
				<li><a href="#">盘点单</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">转库单</a></li>
			</ul></li>

		<li role="presentation" class="dropdown"><a
			class="dropdown-toggle" data-toggle="dropdown" href="#" role="button"
			aria-haspopup="true" aria-expanded="false">供应链领域<span
				class="caret"></span>
		</a>
			<ul class="dropdown-menu">
				<li><a href="#">出库申请单</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">采购计划</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="#">出库申请单</a></li>
			</ul></li>

	</ul>
          
	<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<div class="container-fluid">
		<div class="row row-centered">
			<div class="col-sm-2 col-md-2"></div>
			<div class="col-xs-12 col-sm-8 col-md-8 col-centered">
				<form action="login.do" method="post">
					<fieldset>
						<legend id="legend">${legend}</legend>
						<!--  <table class="gridtable">-->
						<table class="table table-striped table-hover table-responsive">
							<thead>
								<th>单据类型</th>
								<th>单号</th>
								<th>发送人</th>
								<th>操作</th>
							</thead>
							<tbody id="tbody">

							</tbody>
						</table>

					</fieldset>
				</form>
			</div>
			<!-- 审批信息 -->
		</div>
	</div>
</body>
</html>