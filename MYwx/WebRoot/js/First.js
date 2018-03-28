/**
 * 添加点击事件/获取点击元素
 * @author jxy
 */
$(function(data){
	var url = window.location.pathname;
	deGetListDetail(url);
	//$("#ListNav").on("click",".ok",doFind);
	$(".dropdown-menu").click(function (e) {
		doClick(e);
		var oncli = $(e.target).html();
		if(oncli.length>10){
			
		}else{
		 var  FromName = {"findName":oncli,"type":doGetType(url)};
		 doSelect(FromName);
		 }
	})
    //$(".nav > li > ul > li > a").click(setClickOnNav);
	setOnclick();
	//setClickOnNav();
})
/**
 * 页面显示当前模块
 * @author jxy
 */
function doClick(e){
	$(".dropdown").removeClass("active");
	$(e.target).parent().parent().parent().addClass("active");
}

/**
 * 点击后关闭导航条
 * @author jxy

function setClickOnNav(){
		$('#collapse').addClass("collapsed");  
		$('#collapse').attr("aria-expanded",false);  
		$("#bs-example-navbar-collapse-1").removeClass("in");  
		$("#bs-example-navbar-collapse-1").attr("aria-expanded",false);  
        $(".find").val("");
}
*/


/**
 * 根据Url判断当前页面状态（待审批、已审批、已驳回）
 * @author jxy
 */
function doGetType(url){
	var fnum = url.lastIndexOf('/');
	var type = url.slice(fnum+1,url.lastIndexOf('.'));
	return type;
}
/**
 * 生成页面
 * @author jxy
 */
function deGetListDetail(url){
	var params = {"type":doGetType(url)};
	doSelect(params);
}
/**
 * 添加‘详情按钮’点击事件
 * @author jxy
 */
function setOnclick(){
	var url = window.location.pathname;
	$(".btn-xs").click(function(e){
		var vbillcode = $(e.target).parent().prev().prev().html();
		doGoDetail(vbillcode,url);
	})  
}
/**
 * ‘详情按钮’点击事件
 * @author jxy
 */
function doGoDetail(vbillcode,url){
	var dnum = url.lastIndexOf('.');
	var xnum = url.lastIndexOf('/');
	var paramS = url.slice(xnum+1, dnum);
	var billcode = vbillcode;
	var type ="deGetDetail"; 
	location.href = type+".do?vbillcode="+billcode+"&url="+paramS;
	
}
/**
 * 显示页面
 * @author jxy
 */
function doSelect(data){
	var title = $("title").html();
	var url = "FindneedApprove.do";
	var params = data;
	$.post(url,params,function(result){
		var data = JSON.parse(result);
		if(data.state == 1){
			setTableBodyRows(data.indextype);
			setOnclick();
		}else{
			alert(data.error);
		}
	});
}
/**
 * 显示页面
 * @author jxy
 */
function setTableBodyRows(list){
	var tBody  = $("#tbody");
	tBody.empty();
	var tds = '<td>[djlxzw]</td> '+
	          '<td>[vbillcode]</td>'+
	'<td>[sendrman]</td>'+
	'<td><button type="button" class="btn btn-primary btn-xs">详情</button></td>';
	for(var i in list){
		var tr=$('<tr></tr>');
		 tr.append(
		    tds.replace('[djlxzw]', list[i].djlxzw)
		    .replace('[vbillcode]', list[i].vbillcode)
		    .replace('[sendrman]', list[i].sendrman));
		 tBody.append(tr);
	}
		
		    
}
/**
 * 获取搜索框内的值
 * @author jxy

function doFind(){
	var url = window.location.pathname;
	var params = {"findName" : $(".find").val(),"type":doGetType(url)};
	doSelect(params);
	setClickOnNav();
}
 */