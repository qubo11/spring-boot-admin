var Authority = {
		init : function() {
			var mTable=DatatableTool.initDatatable("authority-table",{
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
			
			Authority.initModal();
			Authority.initSaveUpdate();
		},
		initModal:function(){
			//初始化modal,增加/修改/删除/批量删除
			DatatableTool.initModal("authority-wrapper",function(){
				DatatableTool.modalShow("#authority-modal", "#authority-form");
				Authority.getFormPage(-1);
				
				$("#save").removeClass("hidden");
				$("#update").addClass("hidden");
			},function(id){
				DatatableTool.modalShow("#authority-modal", "#authority-form");
				Authority.getFormPage(id);
				
				$("#save").addClass("hidden");
				$("#update").removeClass("hidden");
			},function(id){
				DatatableTool.deleteRow("authority-table","admin/authority/delete",id);
			},function(ids){
				DatatableTool.deleteRows("authority-table","admin/authority/deleteBatch",ids);
			});
		},
		initSaveUpdate:function(){
			//绑定保存和修改按钮
			DatatableTool.bindSaveAndUpdate("authority-wrapper",function(){
				DatatableTool.saveRow("admin/authority/save",$("#authority-form").serialize(),"authority-table",function(rowNode,response){
					DatatableTool.modalHide("#authority-modal");
					Authority.initModal();
				});
			},function(){
				DatatableTool.updateRow("admin/authority/update",$("#authority-form").serialize(),"authority-table",function(rowNode,response){
					DatatableTool.modalHide("#authority-modal");
					Authority.initModal();
				});
			});
		},
		getFormPage:function(id){
			AjaxTool.html("admin/authority/formPage",{
				id:id
			},function(html){
				$("#authority-modal .modal-body").html(html);
			});
		}
};

jQuery(document).ready(function() {
	Authority.init();
});