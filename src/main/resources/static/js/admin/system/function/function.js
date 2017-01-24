var Function = {
	init : function() {
		var mTable=DatatableTool.initDatatable("function-table",{
			"columnDefs":[ {
				'orderable' : false,
				'targets' : [ 0, 7 ]
			}, {
				"searchable" : false,
				"targets" : [ 0, 7 ]
			},{
				"width" : "100px",
				"targets" : 7
			} ],
			"order":[ [ 1, "asc" ] ],
			"bAutoWidth":true
		});
		
		Function.initModal();
		Function.initSaveUpdate();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除
		DatatableTool.initModal("function-wrapper",function(){
			DatatableTool.modalShow("#function-modal", "#function-form");
			Function.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#function-modal", "#function-form");
			
			Function.getFormPage(id);
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("function-table","admin/function/delete",id);
		},function(ids){
			DatatableTool.deleteRows("function-table","admin/function/deleteBatch",ids);
		});
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("function-wrapper",function(){
			DatatableTool.saveRow("admin/function/save",$("#function-form").serialize(),"function-table",function(rowNode,response){
				$("#function-modal").modal('hide');
				Function.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/function/update",$("#function-form").serialize(),"function-table",function(rowNode,response){
				$("#function-modal").modal('hide');
				Function.initModal();
				//如果更新了父部门名称，就需要把所有子部门的父部门名称改掉
				var data=response.addData;
				if(data&&data.length>0){
					for(i in data){
						$("#"+data[i]).find("td").eq(4).html(response.obj.name);
					}
				}
			});
		});
	},
	getFormPage : function(id){
		AjaxTool.html("admin/function/functionFormPage",{
			id:id
		},function(html){
			$("#function-modal .modal-body").html(html);
		});
	}
};

jQuery(document).ready(function() {
	Function.init();
});