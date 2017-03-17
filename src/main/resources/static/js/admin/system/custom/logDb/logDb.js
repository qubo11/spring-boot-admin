var LogDb = {
	init : function() {
		var mTable = DatatableTool.initDatatableServer("logDb-table", {
			"url" : "admin/logDb/getLogDbDatatable",
			"data" : function(){
				var username=$("input[name='username']").val();
				var operationType=$("select[name='operationType'] option:selected").val();
				var description=$("input[name='description']").val();
				var dateTime1=$("input[name='dateTime1']").val();
				var dateTime2=$("input[name='dateTime2']").val();
				var methodName=$("input[name='methodName']").val();
				var executeTime1=$("input[name='executeTime1']").val();
				var executeTime2=$("input[name='executeTime2']").val();
				var result=$("input[name='result']").val();
				return {
					username:username,
					operationType:operationType,
					description:description,
					dateTime1:dateTime1,
					dateTime2:dateTime2,
					methodName:methodName,
					executeTime1:executeTime1,
					executeTime2:executeTime2,
					result:result
				};
			}
		}, null, function() {
			LogDb.initModal();
		});
		$(".query-button").click(function(){
			mTable.ajax.reload();
		});
		Plugins.initHourDate("input[name='dateTime1']");
		Plugins.initHourDate("input[name='dateTime2']");
	},
	initModal:function(){
		DatatableTool.initDetailModal("logDb-wrapper",function(id){
			DatatableTool.modalShow("#detail-modal");
			AjaxTool.html("admin/logDb/logDbDetailPage",{
				id:id
			},function(html){
				$("#detail-modal .modal-body").html(html);
			});
		});
	}
};

jQuery(document).ready(function() {
	LogDb.init();
});