<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title id="title">NC账号绑定</title>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/style1.css" />
</head>
<body>
	<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/login.js"></script>
	<div class="container">
		<div class="row row-centered">
			<div class="well col-md-6 col-centered">
				<h2>绑定账号</h2>
				<form:form action="" method="post" role="form">
					<div id="user" class="input-group input-group-md">
						<span class="input-group-addon" id="sizing-addon1"><i
							class="glyphicon glyphicon-user" aria-hidden="true"></i></span> <input
							type="text" class="form-control" id="userid" name="userid"
							placeholder="请输入NC账账户" onclick="clean();" /> <span id="userSpan2"
							class="glyphicon glyphicon-warning-sign form-control-feedback hid"
							aria-hidden="true"></span> <span id="inputError2Status"
							class="sr-only">(error)</span>
					</div>
					<br>
					<div id="psw" class="input-group input-group-md">
						<span class="input-group-addon" id="sizing-addon1"><i
							class="glyphicon glyphicon-lock"></i></span> <input type="password"
							class="form-control" id="password" name="password"
							placeholder="请输入NC密码" /> <span id="pswSpan"
							class="glyphicon glyphicon-remove form-control-feedback hid"
							aria-hidden="true"></span> <span id="pswSpan2"
							class="glyphicon glyphicon-warning-sign form-control-feedback hid"
							aria-hidden="true"></span> <span id="inputError2Status"
							class="sr-only">(error)</span>
					</div>
					<br />
					<button type="submit" class="btn btn-success btn-block"
						onclick="f1();">绑定</button><!-- 绑定按钮 -->
				</form:form><!-- 绑定账号表单 -->
			</div>
		</div>
	</div>
</body>
</html>