var User = {
	ctx : $("input[name='ctx']").val()+"/",
	init : function() {
		var mTable=DatatableTool.initDatatable("user-table",{
			"columnDefs":[ {
				'orderable' : false,
				'targets' : [ 0, 7 ]
			}, {
				"searchable" : false,
				"targets" : [ 0, 7 ]
			}, {
				"width" : "30px",
				"targets" : 0
			}, {
				"width" : "300px",
				"targets" : 7
			}],
			"order":[ [ 1, "asc" ] ],
			"bAutoWidth":false
		});
		
		User.initModal();
		User.initSaveUpdate();
		User.userRoleUpdateSubmit();
		User.userDepartmentUpdateSubmit();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除/详情/部门/角色/头像
		DatatableTool.initModal("user-wrapper",function(){
			DatatableTool.modalShow("#user-modal", "#user-form");
			User.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#user-modal");
			User.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("user-table","admin/user/delete",id);
		},function(ids){
			DatatableTool.deleteRows("user-table","admin/user/deleteBatch",ids);
		},function(id){
			//详情
			DatatableTool.modalShow("#detail-modal");
			$("#detail-modal .modal-body").html("");
			AjaxTool.html("admin/user/userDetailPage",{
				id:id
			},function(html){
				$("#detail-modal .modal-body").html(html);
			});
		});
		DatatableTool.initAddModal("user-wrapper",function(id){
			//部门
			DatatableTool.modalShow("#yellow-modal", "#user-department-form");
			$("input[name='userId']").val(id);
			User.requestDepartmentCheckBox(id);
		},function(id){
			//角色
			DatatableTool.modalShow("#purple-modal", "#user-role-form");
			$("input[name='userId']").val(id);
			User.requestRoleCheckBox(id);
		},function(id){
			//头像
			DatatableTool.modalShow("#green-sharp-modal", "#user-department-form");
			User.getAvatarPage(id);
		});
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("user-wrapper",function(){
			DatatableTool.saveRow("admin/user/save",$("#user-form").serialize(),"user-table",function(rowNode,response){
				DatatableTool.modalHide("#user-modal");
				User.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/user/update",$("#user-form").serialize(),"user-table",function(rowNode,response){
				DatatableTool.modalHide("#user-modal");
				User.initModal();
			});
		});
	},
	getFormPage:function(id){
		AjaxTool.html("admin/user/userFormPage",{
			id:id
		},function(html){
			$("#user-modal .modal-body").html(html);
		});
	},
	getAvatarPage:function(id){
		AjaxTool.html("admin/user/avatarPage",{
			id:id
		},function(html){
			$("#green-sharp-modal .modal-body").html(html);
			
			$("#user-avatar-form").FileUploadDestory();
			var uploader=$("#user-avatar-form").FileUpload({
				url:User.ctx+"admin/user/uploadAvatar",
				fileType: "image"
			});
			uploader.done(function(data){
				if(data.result){
					$(".user-avatar-img").attr("src",User.ctx+"admin/user/showAvatar?userId="+data.result.obj+"&uuid="+Tools.getUUID());
				}
			});
		});
	},
	userRoleUpdateSubmit : function() {
		$("#role-save").click(function(){
			var roleid = "";
			var roleName="";
			$("#purple-modal input:checked").each(function(index) {
				if (index == 0) {
					roleid = $(this).val();
					roleName=$(this).parent().find("label").text();
				} else {
					roleid += "," + $(this).val();
					roleName+= "," +$(this).parent().find("label").text();
				}
			});
			var id=$("input[name='userId']").val();
			AjaxTool.post("admin/user/updateUserRole", {
				"userId" : id,
				"roleId" : roleid
			}, function(response) {
				DatatableTool.modalHide("#pueple-modal");
				Toast.show("角色提醒",response.message);
				if (response.result) {
					User.initModal();
				}
			});
		});
	},
	userDepartmentUpdateSubmit : function() {
		$("#department-save").click(function(){
			var departmentId = "";
			var departmentName="";
			$("#yellow-modal input:checked").each(function(index) {
				if (index == 0) {
					departmentId = $(this).val();
					departmentName=$(this).parent().find("label").text();
				} else {
					departmentId += "," + $(this).val();
					departmentName+= "," +$(this).parent().find("label").text();
				}
			});
			var id=$("input[name='userId']").val();
			AjaxTool.post("admin/user/updateUserDepartment", {
				"userId" : id,
				"departmentId" : departmentId
			}, function(response) {
				DatatableTool.modalHide("#yellow-modal");
				Toast.show("部门提醒",response.message);
				if (response.result) {
					User.initModal();
				}
			});
		});
	},
	requestRoleCheckBox:function(){
		var id=$("input[name='userId']").val();
		AjaxTool.post("admin/role/getRoleCheckboxHtml",{
			userId:id
		},function(response){
			$("#role-checkbox").html(response.html);
		});
	},
	requestDepartmentCheckBox:function(){
		var id=$("input[name='userId']").val();
		AjaxTool.post("admin/department/getDepartmentCheckboxHtml",{
			userId:id
		},function(response){
			$("#department-checkbox").html(response.html);
		});
	}
};

jQuery(document).ready(function() {
	User.init();
});