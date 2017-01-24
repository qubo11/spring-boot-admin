var Controller = {
	init : function() {
		var mTable=DatatableTool.initDatatable("controller-table",{
			"columnDefs":[ {
				'orderable' : false,
				'targets' : [ 0, 5 ]
			}, {
				"searchable" : false,
				"targets" : [ 0, 5 ]
			},{
				"width" : "200px",
				"targets" : 5
			} ],
			"order":[ [ 1, "asc" ] ],
			"bAutoWidth":true
		});
		Controller.initModal();
		Controller.initSaveUpdate();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除
		DatatableTool.initModal("controller-wrapper",function(){
			DatatableTool.modalShow("#controller-modal", "#controller-form");
			Controller.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#controller-modal", "#controller-form");
			Controller.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("controller-table","admin/db/controller/delete",id);
		},function(ids){
			DatatableTool.deleteRows("controller-table","admin/db/controller/deleteBatch",ids);
		},function(id){
			Controller.getDetailPage(id);
		});
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("controller-wrapper",function(){
			DatatableTool.saveRow("admin/db/controller/save",$("#controller-form").serialize(),"controller-table",function(rowNode,response){
				$("#controller-modal").modal('hide');
				Controller.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/db/controller/update",$("#controller-form").serialize(),"controller-table",function(rowNode,response){
				$("#controller-modal").modal('hide');
				Controller.initModal();
			});
		});
	},
	getFormPage:function(id){
		var pojoId = $("input[name='pojoId']").val();
		AjaxTool.html("admin/db/controller/formPage",{
			id:id,
			pojoId:pojoId
		},function(html){
			$("#controller-modal .modal-body").html(html);
		});
	},
	getDetailPage:function(id){
		AjaxTool.html("admin/db/controller/detailPage",{
			id:id
		},function(html){
			$("#detail-modal .modal-body").html(html);
		});
	}
};

jQuery(document).ready(function() {
	Controller.init();
});