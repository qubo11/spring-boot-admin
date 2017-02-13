var Project = {
	init : function() {
		var mTable=DatatableTool.initDatatable("project-table",{
			"columnDefs":[ {
				'orderable' : false,
				'targets' : [ 0, 4 ]
			}, {
				"searchable" : false,
				"targets" : [ 0, 4 ]
			},{
				"width" : "400px",
				"targets" : 4
			} ],
			"order":[ [ 1, "asc" ] ],
			"bAutoWidth":true
		});
		Project.initModal();
		Project.initSaveUpdate();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除
		DatatableTool.initModal("project-wrapper",function(){
			DatatableTool.modalShow("#project-modal", "#project-form");
			Project.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#project-modal", "#project-form");
			Project.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("project-table","admin/db/project/delete",id);
		},function(ids){
			DatatableTool.deleteRows("project-table","admin/db/project/deleteBatch",ids);
		},function(id){
			Project.getDetailPage(id);
		});
		//属性
		Pjax.redirectAdd("#project-wrapper .yellow-button");
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("project-wrapper",function(){
			DatatableTool.saveRow("admin/db/project/save",$("#project-form").serialize(),"project-table",function(rowNode,response){
				$("#project-modal").modal('hide');
				Project.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/db/project/update",$("#project-form").serialize(),"project-table",function(rowNode,response){
				$("#project-modal").modal('hide');
				Project.initModal();
			});
		});
	},
	getFormPage:function(id){
		AjaxTool.html("admin/db/project/formPage",{
			id:id
		},function(html){
			$("#project-modal .modal-body").html(html);
		});
	},
	getDetailPage:function(id){
		AjaxTool.html("admin/db/project/detailPage",{
			id:id
		},function(html){
			$("#detail-modal .modal-body").html(html);
		});
	}
};

jQuery(document).ready(function() {
	Project.init();
});