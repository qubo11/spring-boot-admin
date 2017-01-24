var PropertyForm = {
	init : function() {
		PropertyForm.initInput();
	},
	initInput:function(){
		PropertyForm.setInput($("select[name='type']"));
		PropertyForm.setId($("select[name='isId']"));
		
		$("select[name='type']").change(function(){
			PropertyForm.setInput($(this));
		});
		$("select[name='isId']").change(function(){
			PropertyForm.setId($(this));
		});
		$("select[name='relation']").change(function(){
			PropertyForm.setRelation($(this));
		});
		$("select[name='manage']").change(function(){
			PropertyForm.setManage($(this));
		});
	},
	setInput:function($this){
		//显示不同类型，不同的输入项
		var val=$this.find("option:selected").val();
		if(val=="Other"){
			//如果是其他类型，则显示其他类型的输入框
			$(".columnName-wrapper").hide();
			$(".otherType-wrapper").show();
			
			$(".relation-wrapper,.foreign-key-wrapper").show();
			$(".base-wrapper,.number-wrapper").hide();
			PropertyForm.setRelation($("select[name='relation']"));
			PropertyForm.setManage($("select[name='manage']"));
		}else{
			$(".columnName-wrapper").show();
			$(".otherType-wrapper").hide();
			$(".otherType-wrapper").hide();
			
			$(".relation-wrapper,.foreign-key-wrapper").hide();
			switch (val) {
			case "String":
				$(".base-wrapper,.number-wrapper,.length-wrapper").show();
				$(".precision-wrapper,.scale-wrapper").hide();
				break;
			case "Integer":
				$(".base-wrapper,.number-wrapper,.precision-wrapper").show();
				$(".length-wrapper,.scale-wrapper").hide();
				break;
			case "Double":
				$(".base-wrapper,.number-wrapper,.precision-wrapper,.scale-wrapper").show();
				$(".length-wrapper").hide();
				break;
			default:
				$(".base-wrapper").show();
				$(".number-wrapper").hide();
				break;
			}
		}
	},
	setId:function($this){
		//如果是主键，唯一/空默认为是/不是
		var val=$this.find("option:selected").val();
		if(val=='是'){
			$("select[name='unique'] option").eq(1).prop("selected",true);
			$("select[name='unique']").prop("disabled",true);
			$("select[name='nullable'] option").eq(1).prop("selected",true);
			$("select[name='nullable']").prop("disabled",true);
		}else{
			$("select[name='unique'] option").eq(0).prop("selected",true);
			$("select[name='unique']").prop("disabled",false);
			$("select[name='nullable'] option").eq(0).prop("selected",true);
			$("select[name='nullable']").prop("disabled",false);
		}
	},
	setRelation:function($this){
		//如果是一对一/多对多，需要设置是否是管理端或者被管理端
		var val=$this.find("option:selected").val();
		if(val=='OneToOne'||val=='ManyToMany'){
			$(".manage-wrapper").show();
			$(".joinColumns-wrapper").hide();
			$(".joinTable-wrapper").hide();
			$(".mappedBy-wrapper").hide();
			$(".inverseJoinColumns-wrapper").hide();
			
			PropertyForm.setManage($("select[name='manage']"));
		}else if(val=='OneToMany'){
			$(".manage-wrapper").hide();
			$(".joinColumns-wrapper").hide();
			$(".mappedBy-wrapper").show();
			$(".inverseJoinColumns-wrapper").hide();
		}else if(val=='ManyToOne'){
			$(".manage-wrapper").hide();
			$(".joinColumns-wrapper").show();
			$(".mappedBy-wrapper").hide();
			$(".inverseJoinColumns-wrapper").hide();
		}
	},
	setManage:function($this){
		//根据管理或者被管理端，设置相应输入
		var val=$this.find("option:selected").val();
		if(val=='1'){
			$(".joinColumns-wrapper").show();
			var manageVal=$("select[name='relation'] option:selected").val();
			if(manageVal=='ManyToMany'){
				$(".joinTable-wrapper").show();
				$(".inverseJoinColumns-wrapper").show();
			}else{
				$(".joinTable-wrapper").hide();
				$(".inverseJoinColumns-wrapper").hide();
			}
			$(".mappedBy-wrapper").hide();
		}else{
			$(".joinColumns-wrapper").hide();
			$(".mappedBy-wrapper").show();
			$(".inverseJoinColumns-wrapper").hide();
		}
	}
};

jQuery(document).ready(function() {
	PropertyForm.init();
});