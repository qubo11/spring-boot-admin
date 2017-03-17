var Role = {
		init : function() {
			var mTable=DatatableTool.initDatatable("role-table",{
				"columnDefs":[ {
					'orderable' : false,
					'targets' : [ 0, 4 ]
				}, {
					"searchable" : false,
					"targets" : [ 0, 4 ]
				},{
					"width" : "100px",
					"targets" : 4
				} ],
				"order":[ [ 1, "asc" ] ],
				"bAutoWidth":true
			});
			
			Role.initModal();
			Role.initSaveUpdate();
		},
		initModal:function(){
			//初始化modal,增加/修改/删除/批量删除
			DatatableTool.initModal("role-wrapper",function(){
				DatatableTool.modalShow("#role-modal", "#role-form");
				Role.getFormPage(-1);
				
				$("#save").removeClass("hidden");
				$("#update").addClass("hidden");
			},function(id){
				DatatableTool.modalShow("#role-modal", "#role-form");
				Role.getFormPage(id);
				
				$("#save").addClass("hidden");
				$("#update").removeClass("hidden");
			},function(id){
				DatatableTool.deleteRow("role-table","admin/role/delete",id);
			},function(ids){
				DatatableTool.deleteRows("role-table","admin/role/deleteBatch",ids);
			});
		},
		initSaveUpdate:function(){
			//绑定保存和修改按钮
			DatatableTool.bindSaveAndUpdate("role-wrapper",function(){
				DatatableTool.saveRow("admin/role/save",$("#role-form").serialize(),"role-table",function(rowNode,response){
					DatatableTool.modalHide("#role-modal");
					Role.initModal();
				});
			},function(){
				DatatableTool.updateRow("admin/role/update",$("#role-form").serialize(),"role-table",function(rowNode,response){
					DatatableTool.modalHide("#role-modal");
					Role.initModal();
				});
			});
		},
		getFormPage:function(id){
			AjaxTool.html("admin/role/formPage",{
				id:id
			},function(html){
				$("#role-modal .modal-body").html(html);
			});
		}
};

jQuery(document).ready(function() {
	Role.init();
});