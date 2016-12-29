var Individual = {
	init : function() {
		Individual.initIndividualInfo();
		Individual.initIndividualAvatar();
		Individual.initIndividualPassword();
	},
	initIndividualInfo:function(){
		//个人信息
		$(".individual-info-save").click(function(){
			AjaxTool.post("user/update",$("#individual-info-form").serialize(),function(response){
				Toast.show("个人信息修改提醒",response.message);
			});
		});
	},
	initIndividualAvatar:function(){
		var id=$("input[name='userAvatarId']").val();
		//头像
		AjaxTool.get("user/checkAvatar",{userId:id},function(response){
			if(response.result){
				$(".user-avatar-img").attr("src","user/showAvatar?userId="+id);
			}else{
				$(".user-avatar-img").attr("src","/images/avatar.png");
			}
		});
		var uploader=$("#user-avatar-form").FileUpload({
			url:"user/uploadAvatar",
			fileType: "image"
		});
		uploader.done(function(data){
			Toast.show("头像修改提醒",data.result.message);
			if(data.result){
				$(".user-avatar-img").attr("src","user/showAvatar?userId="+data.result.obj+"&uuid"+Tools.getUUID());
			}
		});
	},
	initIndividualPassword:function(){
		$(".user-pasword-modify").click(function(){
			AjaxTool.post("user/modifyPassword",$("#user-password-form").serialize(),function(response){
				Toast.show("密码修改提醒",response.message);
			});
		});
	}
};

jQuery(document).ready(function() {
	Individual.init();
});