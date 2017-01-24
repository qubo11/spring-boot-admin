var DaoMethod = {
	init : function() {
		var mTable=DatatableTool.initDatatable("daoMethod-table",{
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
		DaoMethod.initModal();
		DaoMethod.initSaveUpdate();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除
		DatatableTool.initModal("dao-method-wrapper",function(){
			DatatableTool.modalShow("#daoMethod-modal", "#daoMethod-form");
			DaoMethod.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#daoMethod-modal", "#daoMethod-form");
			DaoMethod.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("daoMethod-table","admin/db/daoMethod/delete",id);
		},function(ids){
			DatatableTool.deleteRows("daoMethod-table","admin/db/daoMethod/deleteBatch",ids);
		},function(id){
			DaoMethod.getDetailPage(id);
		});
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("dao-method-wrapper",function(){
			DatatableTool.saveRow("admin/db/daoMethod/save",$("#daoMethod-form").serialize(),"daoMethod-table",function(rowNode,response){
				$("#daoMethod-modal").modal('hide');
				DaoMethod.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/db/daoMethod/update",$("#daoMethod-form").serialize(),"daoMethod-table",function(rowNode,response){
				$("#daoMethod-modal").modal('hide');
				DaoMethod.initModal();
			});
		});
	},
	getFormPage:function(id){
		var daoId=$("input[name='daoId']").val();
		AjaxTool.html("admin/db/daoMethod/formPage",{
			id:id,
			daoId:daoId
		},function(html){
			$("#daoMethod-modal .modal-body").html(html);
		});
	},
	getDetailPage:function(id){
		AjaxTool.html("admin/db/daoMethod/detailPage",{
			id:id
		},function(html){
			$("#detail-modal .modal-body").html(html);
		});
	}
};

jQuery(document).ready(function() {
	DaoMethod.init();
});