var Business = {
	init : function() {
		var mTable=DatatableTool.initDatatable("business-table",{
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
		Business.initModal();
		Business.initSaveUpdate();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除
		DatatableTool.initModal("business-wrapper",function(){
			DatatableTool.modalShow("#business-modal", "#business-form");
			Business.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#business-modal", "#business-form");
			Business.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("business-table","admin/db/business/delete",id);
		},function(ids){
			DatatableTool.deleteRows("business-table","admin/db/business/deleteBatch",ids);
		},function(id){
			Business.getDetailPage(id);
		});
		//vo
		Pjax.redirectAdd("#business-wrapper .yellow-button");
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("business-wrapper",function(){
			DatatableTool.saveRow("admin/db/business/save",$("#business-form").serialize(),"business-table",function(rowNode,response){
				$("#business-modal").modal('hide');
				Business.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/db/business/update",$("#business-form").serialize(),"business-table",function(rowNode,response){
				$("#business-modal").modal('hide');
				Business.initModal();
			});
		});
	},
	getFormPage:function(id){
		var pojoId = $("input[name='pojoId']").val();
		AjaxTool.html("admin/db/business/formPage",{
			id:id,
			pojoId:pojoId
		},function(html){
			$("#business-modal .modal-body").html(html);
		});
	},
	getDetailPage:function(id){
		AjaxTool.html("admin/db/business/detailPage",{
			id:id
		},function(html){
			$("#detail-modal .modal-body").html(html);
		});
	}
};

jQuery(document).ready(function() {
	Business.init();
});