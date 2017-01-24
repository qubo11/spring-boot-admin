var PojoForm = {
	init : function() {
		PojoForm.initSaveUpdate();
		PojoForm.initPropertyAdd();
	},
	initSaveUpdate:function(){
		//绑定保存和修改按钮
		DatatableTool.bindSaveAndUpdate(function(){
			DatatableTool.saveRow("admin/db/pojo/save",$("#pojo-form").serialize(),"pojo-table",function(rowNode,response){
				$("#pojo-modal").modal('hide');
				PojoForm.initModal();
			});
		},function(){
			DatatableTool.updateRow("admin/db/pojo/update",$("#pojo-form").serialize(),"pojo-table",function(rowNode,response){
				$("#pojo-modal").modal('hide');
				//如果更新了父部门名称，就需要把所有子部门的父部门名称改掉
				var data=response.addData;
				if(data&&data.length>0){
					for(i in data){
						$("#"+data[i]).find("td").eq(4).html(response.obj.name);
					}
				}
				PojoForm.initModal();
			});
		});
	},
	initPropertyAdd:function(){
		$(".property-add-button").click(function(){
			$("#property-modal").modal("show");
			AjaxTool.html("admin/db/property/formPage",{},function(html){
				$("#property-modal .modal-body").html(html);
			});
		});
	}
};

jQuery(document).ready(function() {
	PojoForm.init();
});