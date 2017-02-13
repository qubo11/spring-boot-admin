var Guide = {
	hasPojo : false,
	hasDao : false,
	hasValueObject : false,
	init : function() {
		Guide.project();
	},
	project : function(){
		$("#guide-wrapper .mt-element-step .mt-step-col").eq(0).addClass("active");
		var projectId = $("input[name='projectId']").val();
		AjaxTool.html("admin/db/project/guidePage",{
			id:projectId
		},function(html){
			$("#guide-wrapper .guide-content").html(html);
			//保存项目
			Guide.saveButton(function(){
				AjaxTool.post("admin/db/project/saveOrUpdate",$("#project-form").serialize(),function(response){
					Toast.show("项目提醒",response.message);
					if(response.result){
						Guide.pojo();
					}
				});
			});
			//下一步
			Guide.nextButton(function(){
				Guide.pojo();
			});
		});
	},
	pojoSelect : function(){
		var projectId = $("input[name='projectId']").val();
		AjaxTool.html("admin/db/pojo/selectPojoPage",{
			projectId:projectId
		},function(html){
			$("#pojo-modal .modal-body").html(html);
			$(".select-pojo-confirm-button").click(function(){
				var id = $(this).data("id");
				$("input[name='globalPojoId']").val(id);
				Guide.pojo(id);
				$("#pojo-modal").modal('hide');
			});
		});
	},
	pojo : function(pojoId){
		$("#guide-wrapper .mt-element-step .mt-step-col").eq(1).addClass("active");
		var projectId = $("input[name='projectId']").val();
		AjaxTool.html("admin/db/pojo/guidePage",{
			projectId:projectId,
			id:pojoId
		},function(html){
			$("#guide-wrapper .guide-content").html(html);
			//如果实体存在，就打开属性设置
			var pojoId = $("#pojo-form input[name='id']").val();
			if(pojoId){
				Guide.hasPojo=true;
				Guide.pojoProperty();
			}
			//保存实体
			Guide.saveButton(function(){
				AjaxTool.post("admin/db/pojo/saveOrUpdate",$("#pojo-form").serialize(),function(response){
					Toast.show("实体提醒",response.message);
					if(response.result){
						if(!Guide.hasPojo){
							Guide.hasPojo=true;
							Guide.pojoProperty();
						}
						$("input[name='globalPojoId']").val(response.obj);
					}
				});
			});
			//下一步
			Guide.nextButton(function(){
				if(Guide.hasPojo){
					Guide.dao();
				}else{
					Toast.show("实体提醒","请先保存实体");
				}
			});
			//选择实体
			$(".select-pojo-button").click(function(){
				DatatableTool.modalShow("#pojo-modal",null);
				Guide.pojoSelect();
			});
		});
	},
	pojoProperty : function(){
		var pojoId = $("input[name='globalPojoId']").val();
		AjaxTool.html("admin/db/property/guidePage",{
			pojoId:pojoId
		},function(html){
			$("#guide-wrapper .guide-content #property-wrapper").html(html);
		});
	},
	dao : function(){
		$("#guide-wrapper .mt-element-step .mt-step-col").eq(2).addClass("active");
		var pojoId = $("input[name='globalPojoId']").val();
		AjaxTool.html("admin/db/dao/guidePage",{
			pojoId:pojoId
		},function(html){
			$("#guide-wrapper .guide-content").html(html);
			//设置pojoId
			$("input[name='pojoId']").val(pojoId);
			//如果dao存在，就打开dao方法设置
			var daoId = $("#dao-form input[name='id']").val();
			if(daoId){
				Guide.hasDao=true;
				Guide.daoMethod();
			}
			//保存dao
			Guide.saveButton(function(){
				AjaxTool.post("admin/db/dao/saveOrUpdate",$("#dao-form").serialize(),function(response){
					Toast.show("Dao层提醒",response.message);
					if(response.result){
						if(!Guide.hasDao){
							Guide.hasDao=true;
							Guide.daoMethod();
						}
					}
				});
			});
			//下一步
			Guide.nextButton(function(){
				Guide.valueObject();
			});
		});
	},
	daoMethod : function(){
		var daoId = $("#dao-form input[name='id']").val();
		AjaxTool.html("admin/db/daoMethod/guidePage",{
			daoId:daoId
		},function(html){
			$("#guide-wrapper .guide-content #dao-method-wrapper").html(html);
		});
	},
	valueObject : function(){
		$("#guide-wrapper .mt-element-step .mt-step-col").eq(3).addClass("active");
		var pojoId = $("input[name='globalPojoId']").val();
		AjaxTool.html("admin/db/valueObject/guidePage",{
			pojoId:pojoId
		},function(html){
			$("#guide-wrapper .guide-content").html(html);
			//设置pojoId
			$("input[name='pojoId']").val(pojoId);
			//如果值对象属性存在，就打开值对象属性设置
			var valueObjectId = $("#valueObject-form input[name='id']").val();
			if(valueObjectId){
				Guide.hasValueObject=true;
				Guide.valueObjectProperty();
			}
			//保存值对象
			Guide.saveButton(function(){
				AjaxTool.post("admin/db/valueObject/saveOrUpdate",$("#valueObject-form").serialize(),function(response){
					Toast.show("值对象提醒",response.message);
					if(response.result){
						if(!Guide.hasValueObject){
							Guide.hasValueObject=true;
							Guide.valueObjectProperty();
						}
					}
				});
			});
			//下一步
			Guide.nextButton(function(){
				Guide.business();
			});
		});
	},
	valueObjectProperty : function(){
		var valueObjectId = $("#valueObject-form input[name='id']").val();
		AjaxTool.html("admin/db/valueObject/property/guidePage",{
			valueObjectId:valueObjectId
		},function(html){
			$("#guide-wrapper .guide-content #valueObject-property-wrapper").html(html);
		});
	},
	business : function(){
		$("#guide-wrapper .mt-element-step .mt-step-col").eq(4).addClass("active");
		var pojoId = $("input[name='globalPojoId']").val();
		AjaxTool.html("admin/db/business/guidePage",{
			pojoId:pojoId
		},function(html){
			$("#guide-wrapper .guide-content").html(html);
			//设置pojoId
			$("input[name='pojoId']").val(pojoId);
			//保存业务层
			Guide.saveButton(function(){
				AjaxTool.post("admin/db/business/saveOrUpdate",$("#business-form").serialize(),function(response){
					Toast.show("业务层提醒",response.message);
				});
			});
			//下一步
			Guide.nextButton(function(){
				Guide.controller();
			});
		});
	},
	controller : function(){
		$("#guide-wrapper .mt-element-step .mt-step-col").eq(5).addClass("active");
		var pojoId = $("input[name='globalPojoId']").val();
		AjaxTool.html("admin/db/controller/guidePage",{
			pojoId:pojoId
		},function(html){
			$("#guide-wrapper .guide-content").html(html);
			//设置pojoId
			$("input[name='pojoId']").val(pojoId);
			//保存控制层
			Guide.saveButton(function(){
				AjaxTool.post("admin/db/controller/saveOrUpdate",$("#controller-form").serialize(),function(response){
					Toast.show("控制层提醒",response.message);
				});
			});
		});
	},
	saveButton : function(saveFunc){
		$(".guide-save-button").click(function(){
			if(saveFunc){
				saveFunc();
			}
		});
	},
	nextButton : function(nextFunc){
		$(".guide-next-button").click(function(){
			if(nextFunc){
				nextFunc();
			}
		});
	}
};

jQuery(document).ready(function() {
	Guide.init();
});