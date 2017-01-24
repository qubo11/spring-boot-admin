var ValueObject = {
	init : function() {
		var mTable=DatatableTool.initDatatable("valueObject-table",{
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
		ValueObject.initModal();
		ValueObject.initSaveUpdate();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除
		DatatableTool.initModal("valueObject-wrapper",function(){
			DatatableTool.modalShow("#valueObject-modal", "#valueObject-form");
			ValueObject.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#valueObject-modal", "#valueObject-form");
			ValueObject.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("valueObject-table","admin/db/valueObject/delete",id);
		},function(ids){
			DatatableTool.deleteRows("valueObject-table","admin/db/valueObject/deleteBatch",ids);
		},function(id){
			ValueObject.getDetailPage(id);
		});
		//属性
		Pjax.redirectAdd("#valueObject-wrapper .yellow-button");
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("valueObject-wrapper",function(){
			DatatableTool.saveRow("admin/db/valueObject/save",$("#valueObject-form").serialize(),"valueObject-table",function(rowNode,response){
				$("#valueObject-modal").modal('hide');
				ValueObject.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/db/valueObject/update",$("#valueObject-form").serialize(),"valueObject-table",function(rowNode,response){
				$("#valueObject-modal").modal('hide');
				ValueObject.initModal();
			});
		});
	},
	getFormPage:function(id){
		AjaxTool.html("admin/db/valueObject/formPage",{
			id:id
		},function(html){
			$("#valueObject-modal .modal-body").html(html);
		});
	},
	getDetailPage:function(id){
		AjaxTool.html("admin/db/valueObject/detailPage",{
			id:id
		},function(html){
			$("#detail-modal .modal-body").html(html);
		});
	}
};

jQuery(document).ready(function() {
	ValueObject.init();
});