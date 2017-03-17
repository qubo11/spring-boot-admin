$(document).ready(function() {
	showAvatar();
	Pjax.init(".nav-pajx", ".page-content", "admin/home", function() {
	}, function($current) {
		clearNavStatus($current);
	});
	Pjax.refresh(".page-content", "admin/home", function(functionUrl) {
		functionUrl = Tools.replaceAll(functionUrl, "[a-zA-Z]", "");
		functionUrl = Tools.replaceAll(functionUrl, "\\D", "");
		clearNavStatus($("." + functionUrl));
	});
	Pjax.onpopstate(".page-content", "admin/home", function(functionUrl) {
		functionUrl = Tools.replaceAll(functionUrl, "[a-zA-Z]", "");
		functionUrl = Tools.replaceAll(functionUrl, "\\D", "");
		clearNavStatus($("." + functionUrl));
	});
	logout();
});
function clearNavStatus($current) {
	$(".nav-item.active").removeClass("active");
	$current.parent().addClass("active");
	if ($current.find(".third-nav")) {
		$current.parent().parent().parent().addClass("active");
		$current.parent().parent().parent().parent().parent().addClass("active");
	}
}
//头像显示
function showAvatar(){
	var userId=$("input[name='userId']").val();
	AjaxTool.get("admin/user/checkAvatar",{
		userId:userId
	},function(response){
		if(response.result){
			var ctx=$("input[name='ctx']").val()+"/";
			$(".user-avatar").attr("src",ctx+"admin/user/showAvatar?userId="+userId);
		}
	});
}
function logout(){
	$(".icon-logout").click(function(){
		$(".logout").trigger();
	});
}