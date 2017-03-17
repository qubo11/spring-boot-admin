var Icon = {
	init : function() {
		var mTable=DatatableTool.initDatatable("icon-table",{
			"columnDefs":[ {
				'orderable' : false,
				'targets' : [ 0, 5 ]
			}, {
				"searchable" : false,
				"targets" : [ 0, 5 ]
			},{
				"width" : "100px",
				"targets" : 5
			} ],
			"order":[ [ 1, "asc" ] ],
			"bAutoWidth":true
		});
		
		Icon.initModal();
		Icon.initSaveUpdate();
		Icon.initPrettyPhoto();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除
		DatatableTool.initModal("icon-wrapper",function(){
			DatatableTool.modalShow("#icon-modal", "#icon-form");
			Icon.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#icon-modal", "#icon-form");
			Icon.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("icon-table","admin/icon/delete",id);
		},function(ids){
			DatatableTool.deleteRows("icon-table","admin/icon/deleteBatch",ids);
		});
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("icon-wrapper",function(){
			DatatableTool.saveRow("admin/icon/save",$("#icon-form").serialize(),"icon-table",function(rowNode,response){
				DatatableTool.modalHide("#icon-modal");
				Icon.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/icon/update",$("#icon-form").serialize(),"icon-table",function(rowNode,response){
				DatatableTool.modalHide("#icon-modal");
				Icon.initModal();
			});
		});
	},
	getFormPage:function(id){
		AjaxTool.html("admin/icon/formPage",{
			id:id
		},function(html){
			$("#icon-modal .modal-body").html(html);
			
			$("#icon-form").FileUploadDestory();
			var uploader=$("#icon-form").FileUpload({
				url:"attachment/upload",
				fileType: "image"
			});
			uploader.done(function(data){
				Toast.show("图标上传提醒",data.result.message);
				if(data.result){
					$("input[name='attachmentId']").val(data.result.id);
				}
			});
		});
	},
	initPrettyPhoto:function(){
		$(".prettyPhoto").prettyPhoto({
			animation_speed:'fast',
			show_title		: false,
			deeplinking		: false,
			social_tools: false,
		});
	}
};

jQuery(document).ready(function() {
	Icon.init();
});