<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="${basePath}/js/modal.js"></script>
<form>
	<fieldset disabled>
		<div class="form-group">
			<label for="recipient-name" class="control-label">您的审批意见:</label> <input
				type="text" class="form-control" id="recipient-name">
		</div>
		<div class="form-group">
			<label for="message-text" class="control-label">审批结果:</label>
			<textarea class="form-control" id="message-text">${result}</textarea>
		</div>
	</fieldset>
	<!-- 禁止交互 -->
</form>