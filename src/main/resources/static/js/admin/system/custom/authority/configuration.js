var Configuration = {
	functionId : null,
	init : function() {
		Configuration.initRoleList();
		Configuration.saveAuthority();
	},
	initTreeView : function() {
		$.jstree.destroy();
		$('#function-tree').jstree({
			'plugins' : [ "wholerow" , "types" ],
			'core' : {
				"themes" : {
					"responsive" : false
				},
				'data' : function(node, cb) {
					var roleId=$(".list-group-item.active").data("id");
					AjaxTool.post("admin/role/getOwnFunctionTree",{
						roleId:roleId
					},function(data){
						cb(data);
					});
				}
			},
			"types" : {
				"default" : {
					"icon" : "fa fa-folder icon-state-warning icon-lg"
				},
				"file" : {
					"icon" : "fa fa-file icon-state-warning icon-lg"
				}
			}
		}).bind("select_node.jstree", function (event, data) {
			var instance = $('#function-tree').jstree(true);
			var node = data.node;
			//判断是否是叶子节点
			if(instance.is_leaf(node)){
				Configuration.functionId = node.id;
				Configuration.initAuthority(node.id);
			}
        });
	},
	initRoleList:function(){
		$(".list-group-item").unbind("click");
		$(".list-group-item").bind("click",function(){
			var id=$(this).data("id");
			var preId=$(".list-group-item.active").data("id");
			if(id==preId){
				return;
			}
			$(".list-group-item.active").removeClass("active");
			$(this).addClass("active");
			Configuration.initTreeView();
			Configuration.functionId = null;
			$("#authority-configuration-wrapper #authority-checkbox").html("");
		});
	},
	initAuthority:function(functionId){
		var roleId=$(".list-group-item.active").data("id");
		AjaxTool.post("admin/authority/getAuthorityCheckboxHtml",{
			roleId:roleId,
			functionId:functionId
		},function(response){
			$("#authority-configuration-wrapper #authority-checkbox").html(response.html);
		});
	},
	saveAuthority:function(){
		$(".save-button").click(function(){
			var roleId=$(".list-group-item.active").data("id");
			var authorityIds = "";
			$("#authority-checkbox input[type='checkbox']:checked").each(function(index){
				if(index==0){
					authorityIds += $(this).val();
				}else{
					authorityIds += ","+$(this).val();
				}
			});
			if(roleId == null){
				Toast.show("权限配置提醒","请选择角色");
			}
			if(Configuration.functionId == null){
				Toast.show("权限配置提醒","请选择功能");
			}
			AjaxTool.post("admin/authority/configureAuthority",{
				roleId : roleId,
				functionId : Configuration.functionId,
				authorityIds : authorityIds
			},function(response){
				Toast.show("权限配置提醒",response.message);
			});
		});
	}
};

jQuery(document).ready(function() {
	Configuration.init();
});