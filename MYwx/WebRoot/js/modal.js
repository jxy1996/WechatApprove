/**
 * 模态框事件
 * @author jxy
 */
$(function() {
	$("#exampleModal").on("click", ".btn-primary", doSeeYouAgain);
	var view = $("#exampleModal").data("view");
	$("#recipient-name").val(view);
	//模态框关闭时
	$("#exampleModal").on("hidden.bs.modal", function() {
		$("#exampleModal").removeData("view");
	});
});
/**
 * 按钮点击事件
 * @author jxy
 */
function doSeeYouAgain() {
	location.href = "needApprove.do";
}