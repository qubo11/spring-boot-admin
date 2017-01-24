var DaoMethodForm = {
	baseTypes : [],
	init : function() {
		DaoMethodForm.setQueryChange($("select[name='query']"));
		$("select[name='query']").change(function(){
			DaoMethodForm.setQueryChange($(this));
			DaoMethodForm.setModifyingChange($("select[name='modifying']"));
		});
		DaoMethodForm.setModifyingChange($("select[name='modifying']"));
		$("select[name='modifying']").change(function(){
			DaoMethodForm.setModifyingChange($(this));
		});
		
		DaoMethodForm.setStandardClick();
		$(".base-types span").each(function(){
			DaoMethodForm.baseTypes.push($(this).text());
		});
		
		DaoMethodForm.setMethodParameterClick($("select[name='methodName']"));
		$("select[name='methodName']").change(function(){
			DaoMethodForm.setMethodParameterClick($(this));
		});
	},
	setQueryChange : function($this){
		var value=$this.find("option:selected").val();
		if(value=='是'){
			$(".modifying-wrapper").show();
			$(".querySentence-wrapper").show();
			$("input[name='name']").prop("readonly",false);
			$("select[name='returnType'] option").eq(3).show();
			$(".parameter-wrapper").hide();
			$(".methodName-wrapper").hide();
		}else{
			$(".modifying-wrapper").hide();
			$(".querySentence-wrapper").hide();
			$("input[name='name']").prop("readonly",true);
			$("select[name='returnType'] option").eq(3).hide();
			$(".parameter-wrapper").show();
			$(".methodName-wrapper").show();
			$("select[name='modifying'] option").eq(0).prop("selected",true);
		}
	},
	setModifyingChange : function($this){
		var value=$this.find("option:selected").val();
		if(value=='是'){
			$("select[name='returnType']").prop("disabled",true);
			$("select[name='returnType'] option").eq(3).prop("selected",true);
		}else{
			$("select[name='returnType']").prop("disabled",false);
			$("select[name='returnType'] option").eq(3).prop("selected",false);
		}
	},
	setStandardClick : function(){
		$("#parameter-add").click(function(){
			var html=$(".parameter-template-wrapper").html();
			$(".parameter-content").append(html);
			DaoMethodForm.setParameterDelete();
			//设置不同操作，填写不同表单内容
			$(".parameter-wrapper select[name='operation']:last").change(function(){
				DaoMethodForm.setTypeClick($(this));
			});
			DaoMethodForm.setTypeClick($(".parameter-wrapper select[name='operation']:last"));
			//设置不同属性，填写不同表单内容
			$(".parameter-wrapper select[name='property']:last").change(function(){
				DaoMethodForm.setPropertyClick($(this));
			});
			DaoMethodForm.setPropertyClick($(".parameter-wrapper select[name='property']:last"));
		});
		$("#parameter-generate").click(function(){
			DaoMethodForm.generate();
		});
	},
	setParameterDelete : function(){
		$(".parameter-delete").unbind("click");
		$(".parameter-delete").bind("click",function(){
			$(this).parent().parent().remove();
		});
	},
	setTypeClick : function($this){
		var $parent=$this.parent().parent().parent();
		var value=$this.val();
		if(value=='And'||value=='Or'){
			$parent.find("select[name='property'] option:last").show();
			$parent.find("select[name='property'] option:last").prop("selected",true);
			$parent.find("select[name='property']").prop("disabled",true);
			$parent.find("input[name='addProperty']").prop("disabled",true);
			$parent.find("select[name='addPropertyType']").prop("disabled",true);
		}else{
			$parent.find("select[name='property'] option:last").hide();
			$parent.find("select[name='property'] option:first").prop("selected",true);
			$parent.find("select[name='property']").prop("disabled",false);
			$parent.find("select[name='property'] option:first").click();
		}
	},
	setPropertyClick : function($this){
		var $parent=$this.parent().parent().parent();
		var value=$this.val();
		var isExists=false;
		for(var i=0;i<DaoMethodForm.baseTypes.length;i++){
			var baseType=DaoMethodForm.baseTypes[i];
			if(value.indexOf(baseType)>=0){
				isExists=true;
			}
		}
		if(isExists){
			$parent.find("input[name='addProperty']").prop("disabled",true);
			$parent.find("select[name='addPropertyType']").prop("disabled",true);
		}else{
			$parent.find("input[name='addProperty']").prop("disabled",false);
			$parent.find("select[name='addPropertyType']").prop("disabled",false);
		}
	},
	setMethodParameterClick : function($this){
		var value=$this.val();
		if(value=='findTop*By' || value=='findFirst*By'){
			$("input[name='methodParameter']").prop("readonly",false);
		}else{
			$("input[name='methodParameter']").prop("readonly",true);
		}
	},
	generate : function(){
		var operations=new Array(0);
		var properties=new Array(0);
		var addProperties=new Array(0);
		var addPropertyTypes=new Array(0);
		$(".parameter-wrapper").find(".parameter").each(function(){
			var operation = $(this).find("select[name='operation']").val();
			var property = $(this).find("select[name='property']").val();
			var addProperty = $(this).find("input[name='addProperty']").val();
			var addPropertyType = $(this).find("select[name='addPropertyType']").val();
			operations.push(operation);
			properties.push(property);
			addProperties.push(addProperty);
			addPropertyTypes.push(addPropertyType);
		});
		var methodName = $("select[name='methodName']").val();
		if(methodName=='findTop*By' || methodName=='findFirst*By'){
			var methodParameter = $("input[name='methodParameter']").val();
			methodName = methodName.replace("*",methodParameter);
		}
		AjaxTool.post("admin/db/daoMethod/generate",{
			methodName : methodName,
			operations : $.toJSON(operations),
			properties : $.toJSON(properties),
			addProperties : $.toJSON(addProperties),
			addPropertyTypes : $.toJSON(addPropertyTypes)
		},function(response){
			if(response.result){
				$("input[name='name']").val(response.obj);
			}
		});
	}
};

jQuery(document).ready(function() {
	DaoMethodForm.init();
});