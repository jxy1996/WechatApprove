/**
 * 按钮动作/打开模态框
 * @author jxy
 */
$(function() {
	var url = window.location.search;
	doCheckUrl(url);
	$("#typeUl").click(function(e) {
		var typeZW = $(e.target).html();
		var type = dojudge(typeZW);
		var r = confirm("确认" + typeZW + "?");
		if (r) {
			var view = document.getElementById("views").value;
			$("#exampleModal").data("view", view);
			var vbillcode = $("#exampleModal").data("vbillcode");
			var url = "Result.do?type=" + type + "&view=" + view + "&vbillcode=" + vbillcode;
			$("#exampleModal .modal-body").load(url, function() {
				$("#exampleModal").modal('show');
			});
		}
	})
})
/**
 * 获取单据号，判断当前页面是否是待审批详情
 * @author jxy
 */
function doCheckUrl(url) {
	var tnum = url.lastIndexOf('=');
	var paramS = url.slice(tnum + 1);
	if ("needApprove" != paramS) {
		$("#lab").addClass("hid");
	} else {
		var num = url.indexOf('=');
		var vbillcode = url.slice(num+1, tnum-4);
		$("#exampleModal").data("vbillcode", vbillcode);
	}
}
/**
 * 判断审批状态
 * @author jxy
 */
function dojudge(data) {
	var type = 'Y';
	var typeZW = data;
	if ("通过" != (typeZW)) {
		type = typeZW == "驳回制单人" ? 'R' : 'N';
	}
	return type;
}