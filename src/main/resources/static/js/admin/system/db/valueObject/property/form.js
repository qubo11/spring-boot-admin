var ValueObjectForm = {
	baseTypes : [],
	init : function() {
		ValueObjectForm.setTypeSelectClick($("select[name='type-select'] option:selected"));
		$("select[name='type-select']").change(function(){
			var $this = $(this).find("option:selected");
			ValueObjectForm.setTypeSelectClick($this);
		});
		$("select[name='property']").change(function(){
			var $this = $(this).find("option:selected");
			ValueObjectForm.setPropertyClick($this);
		});
		ValueObjectForm.setValidateTypeClick($("select[name='validateType'] option:selected"));
		$("select[name='validateType']").change(function(){
			var $this = $(this).find("option:selected");
			ValueObjectForm.setValidateTypeClick($this);
		});
	},
	setPropertyClick : function($this){
		var name=$this.text();
		var type=$this.val();
		if(name=='无'){
			$("input[name='name']").val("");
		}else{
			$("input[name='name']").val(name);
		}
		$("input[name='type']").val(type);
	},
	setTypeSelectClick : function($this){
		var value=$this.val();
		if(value=='Other'){
			$("input[name='type']").val("");
		}else{
			$("input[name='type']").val(value);
		}
	},
	setValidateTypeClick : function($this){
		var value=$this.val();
		if(value=='Other'){
			$("input[name='regex']").prop("readonly",false);
		}else{
			$("input[name='regex']").prop("readonly",true);
		}
	}
};

jQuery(document).ready(function() {
	ValueObjectForm.init();
});