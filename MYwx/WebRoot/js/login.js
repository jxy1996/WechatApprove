/**
 * 检验登录用户密码事件
 * @author jxy
 */
function f1() {
	chackNull();
	var url = "loginCheck.do";
	var params = {
		"username" : $("#userid").val(),
		"password" : $("#password").val()
	};
	$.post(url, params, function(data) {
		console.log(data);
		var jsonObj = JSON.parse(data);
		console.log(jsonObj.code);
		console.log(jsonObj.msg);
		if (jsonObj.code == 1) {
			var r = confirm(jsonObj.msg);
			if (r) {
				WeixinJSBridge.call('closeWindow');
			} else {
				WeixinJSBridge.call('closeWindow');
			}
		} else if (jsonObj.code == -1) {
			$("#psw").addClass("has-error");
			$("#pswSpan").removeClass("hid");
			alert(jsonObj.msg);
		}
	});
}
/**
 * 关闭当前页面
 * @author jxy
 */
function closewin() {
	if (navigator.userAgent.indexOf("Firefox") != -1 || navigator.userAgent.indexOf("Chrome") != -1) {
		window.location.href = "about:blank";
		window.close();
	} else {
		window.opener = null;
		window.open("", "_self");
		window.close();
	}
}
/**
 * 检查输入是否为空
 * @author jxy
 */
function chackNull() {
	var userid = $("#userid").val();
	var psw = $("#password").val();
	if (userid == "") {
		$("#user").addClass("has-warning");
		$("#userSpan2").removeClass("hid");
	}
	if (psw == "") {
		$("#psw").addClass("has-warning");
		$("#pswSpan2").removeClass("hid");
	}
}
/**
 * 点击输入框时移除css样式
 * @author jxy
 */
function clean() {
	$("#user").removeClass("has-warning");
	$("#userSpan2").addClass("hid");
	$("#psw").removeClass("has-warning has-error");
	$("#pswSpan2").addClass("hid");

}