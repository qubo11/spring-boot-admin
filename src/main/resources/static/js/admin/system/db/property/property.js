var Property = {
	init : function() {
		var mTable=DatatableTool.initDatatable("property-table",{
			"columnDefs":[ {
				'orderable' : false,
				'targets' : [ 0, 6 ]
			}, {
				"searchable" : false,
				"targets" : [ 0, 6 ]
			},{
				"width" : "150px",
				"targets" : 6
			} ],
			"order":[ [ 1, "asc" ] ],
			"bAutoWidth":true
		});
		Property.initModal();
		Property.initSaveUpdate();
	},
	initModal : function(){
		//初始化modal,增加/修改/删除/批量删除/详情
		DatatableTool.initModal("property-wrapper",function(){
			DatatableTool.modalShow("#property-modal", "#property-form");
			Property.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#property-modal", "#property-form");
			Property.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("property-table","admin/property/delete",id);
		},function(ids){
			DatatableTool.deleteRows("property-table","admin/property/deleteBatch",ids);
		},function(id){
			Property.getDetailPage(id);
		});
	},
	initSaveUpdate : function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("property-wrapper",function(){
			DatatableTool.saveRow("admin/db/property/save",$("#property-form").serialize(),"property-table",function(rowNode,response){
				$("#property-modal").modal('hide');
				Property.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/db/property/update",$("#property-form").serialize(),"property-table",function(rowNode,response){
				$("#property-modal").modal('hide');
				Property.initModal();
			});
		});
	},
	getFormPage : function(id){
		var pojoId = $("input[name='pojoId']").val();
		AjaxTool.html("admin/db/property/formPage",{
			id:id,
			pojoId:pojoId
		},function(html){
			$("#property-modal .modal-body").html(html);
		});
	},
	getDetailPage : function(id){
		AjaxTool.html("admin/db/property/formPage",{
			id:id
		},function(html){
			$("#detail-modal .modal-body").html(html);
		});
	},
};

jQuery(document).ready(function() {
	Property.init();
});