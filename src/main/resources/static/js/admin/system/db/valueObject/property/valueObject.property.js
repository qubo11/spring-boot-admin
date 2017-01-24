var ValueObjectProperty = {
	init : function() {
		var mTable=DatatableTool.initDatatable("valueObject-property-table",{
			"columnDefs":[ {
				'orderable' : false,
				'targets' : [ 0, 5 ]
			}, {
				"searchable" : false,
				"targets" : [ 0, 5 ]
			},{
				"width" : "150px",
				"targets" : 5
			} ],
			"order":[ [ 1, "asc" ] ],
			"bAutoWidth":true
		});
		ValueObjectProperty.initModal();
		ValueObjectProperty.initSaveUpdate();
		ValueObjectProperty.initSync();
	},
	initModal:function(){
		//初始化modal,增加/修改/删除/批量删除
		DatatableTool.initModal("valueObject-property-wrapper",function(){
			DatatableTool.modalShow("#valueObject-property-modal", "#valueObject-property-form");
			ValueObjectProperty.getFormPage(-1);
			
			$("#save").removeClass("hidden");
			$("#update").addClass("hidden");
		},function(id){
			DatatableTool.modalShow("#valueObject-property-modal", "#valueObject-property-form");
			ValueObjectProperty.getFormPage(id);
			
			$("#save").addClass("hidden");
			$("#update").removeClass("hidden");
		},function(id){
			DatatableTool.deleteRow("valueObject-property-table","admin/db/valueObject/property/delete",id);
		},function(ids){
			DatatableTool.deleteRows("valueObject-property-table","admin/db/valueObject/property/deleteBatch",ids);
		},function(id){
			ValueObjectProperty.getDetailPage(id);
		});
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate("valueObject-property-wrapper",function(){
			DatatableTool.saveRow("admin/db/valueObject/property/save",$("#valueObject-property-form").serialize(),"valueObject-property-table",function(rowNode,response){
				$("#valueObject-property-modal").modal('hide');
				ValueObjectProperty.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/db/valueObject/property/update",$("#valueObject-property-form").serialize(),"valueObject-property-table",function(rowNode,response){
				$("#valueObject-property-modal").modal('hide');
				ValueObjectProperty.initModal();
			});
		});
	},
	getFormPage:function(id){
		var valueObjectId = $("input[name='valueObjectId']").val();
		AjaxTool.html("admin/db/valueObject/property/formPage",{
			id:id,
			valueObjectId:valueObjectId
		},function(html){
			$("#valueObject-property-modal .modal-body").html(html);
		});
	},
	getDetailPage:function(id){
		AjaxTool.html("admin/db/valueObject/property/detailPage",{
			id:id
		},function(html){
			$("#detail-modal .modal-body").html(html);
		});
	},
	initSync : function(){
		$(".sync-button").click(function(){
			var valueObjectId = $("input[name='valueObjectId']").val();
			AjaxTool.post("admin/db/valueObject/property/sync",{
				valueObjectId:valueObjectId
			},function(response){
				Toast.show("同步提醒",response.message);
				if(response.result){
					DatatableTool.clear("valueObject-property-table");
					for(var i=0;i<response.obj.length;i++){
						var dtrv = response.obj[i];
						var id = dtrv.obj;
						var data = dtrv.data;
						DatatableTool.addRow("valueObject-property-table",id,data);
					}
					ValueObjectProperty.initModal();
				}
			});
		});
	}
};

jQuery(document).ready(function() {
	ValueObjectProperty.init();
});