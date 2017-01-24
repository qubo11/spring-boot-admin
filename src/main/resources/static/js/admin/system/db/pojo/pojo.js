var Pojo = {
	init : function() {
		var mTable=DatatableTool.initDatatable("pojo-table",{
			"columnDefs":[ {
				'orderable' : false,
				'targets' : [ 0, 5 ]
			}, {
				"searchable" : false,
				"targets" : [ 0, 5 ]
			},{
				"width" : "450px",
				"targets" : 5
			} ],
			"order":[ [ 1, "asc" ] ],
			"bAutoWidth":true
		});
		Pojo.initModal();
		Pojo.initSaveUpdate();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除
		DatatableTool.initModal("pojo-wrapper",function(){
			DatatableTool.modalShow("#pojo-modal", "#pojo-form");
			Pojo.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#pojo-modal", "#pojo-form");
			Pojo.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("pojo-table","admin/db/pojo/delete",id);
		},function(ids){
			DatatableTool.deleteRows("pojo-table","admin/db/pojo/deleteBatch",ids);
		},function(id){
			Pojo.getDetailPage(id);
		});
		//属性
		Pjax.redirectAdd("#pojo-wrapper .yellow-button");
		//dao
		Pjax.redirectAdd("#pojo-wrapper .purple-button");
		//service
		Pjax.redirectAdd("#pojo-wrapper .green-sharp-button");
		//vo
		Pjax.redirectAdd("#pojo-wrapper .purple-sharp-button");
		//controller
		Pjax.redirectAdd("#pojo-wrapper .blue-sharp-button");
		//页面
		Pjax.redirectAdd("#pojo-wrapper .green-jungle-button");
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("pojo-wrapper",function(){
			DatatableTool.saveRow("admin/db/pojo/save",$("#pojo-form").serialize(),"pojo-table",function(rowNode,response){
				$("#pojo-modal").modal('hide');
				Pojo.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/db/pojo/update",$("#pojo-form").serialize(),"pojo-table",function(rowNode,response){
				$("#pojo-modal").modal('hide');
				Pojo.initModal();
			});
		});
	},
	getFormPage:function(id){
		AjaxTool.html("admin/db/pojo/formPage",{
			id:id
		},function(html){
			$("#pojo-modal .modal-body").html(html);
		});
	},
	getDetailPage:function(id){
		AjaxTool.html("admin/db/pojo/detailPage",{
			id:id
		},function(html){
			$("#detail-modal .modal-body").html(html);
		});
	},
	generateCode:function(){
		$(".generate-button").click(function(){
			var ids="";
			$(".checkboxes:checked").each(function(index){
				var id=$(this).parent().parent().parent().attr("id");
				if(index==0){
					ids=id;
				}else{
					ids+=","+id;
				}
			});
		});
	}
};

jQuery(document).ready(function() {
	Pojo.init();
});