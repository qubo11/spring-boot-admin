var Dao = {
	init : function() {
		var mTable=DatatableTool.initDatatable("dao-table",{
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
		Dao.initModal();
		Dao.initSaveUpdate();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除
		DatatableTool.initModal("dao-wrapper",function(){
			DatatableTool.modalShow("#dao-modal", "#dao-form");
			Dao.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#dao-modal", "#dao-form");
			Dao.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("dao-table","admin/db/dao/delete",id);
		},function(ids){
			DatatableTool.deleteRows("dao-table","admin/db/dao/deleteBatch",ids);
		},function(id){
			Dao.getDetailPage(id);
		});
		//方法
		Pjax.redirectAdd("#dao-wrapper .yellow-button");
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("dao-wrapper",function(){
			DatatableTool.saveRow("admin/db/dao/save",$("#dao-form").serialize(),"dao-table",function(rowNode,response){
				$("#dao-modal").modal('hide');
				Dao.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/db/dao/update",$("#dao-form").serialize(),"dao-table",function(rowNode,response){
				$("#dao-modal").modal('hide');
				Dao.initModal();
			});
		});
	},
	getFormPage:function(id){
		var pojoId = $("input[name='pojoId']").val();
		AjaxTool.html("admin/db/dao/formPage",{
			id:id,
			pojoId:pojoId
		},function(html){
			$("#dao-modal .modal-body").html(html);
		});
	},
	getDetailPage:function(id){
		AjaxTool.html("admin/db/dao/detailPage",{
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
	Dao.init();
});