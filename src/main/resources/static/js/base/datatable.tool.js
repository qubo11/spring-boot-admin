var DatatableTool = function() {
	
	function checkUrl(url) {
		var ctxObj=$("input[name='ctx']");
		if(ctxObj.length>0){
			var ctx=ctxObj.val();
			url=ctx+"/"+url;
		}
		return url;
	}

	var initDatatable = function(tableId, options,drawCallback) {
		var defaultOptions={
			bAutoWidth:true,
			columnDefs:null,
			order:null
		};
		if(options){
			if(options.bAutoWidth){
				defaultOptions.bAutoWidth=options.bAutoWidth;
			}
			if(options.columnDefs){
				defaultOptions.columnDefs=options.columnDefs;
			}
			if(options.order){
				defaultOptions.order=options.order;
			}
		}
		var i18nUrl = "plugins/datatables/dataTables.chinese.txt";
		var mTable = $('#' + tableId).DataTable({
			'oLanguage' : {
				'sUrl' : checkUrl(i18nUrl)
			},
			"pageLength" : 10,// 每页长度
			"bFilter" : true, // 搜索
			"bLengthChange" : true,// 改变每页长度
			"lengthMenu" : [ [ 10, 20, 30 ], [ 10, 20, 30 ] ],// 可以改变的每页长度
			"bStateSave" : true,
			"columnDefs" : defaultOptions.columnDefs,
			"order" : defaultOptions.order,
			"drawCallback" : function(settings) {
				if (drawCallback != null) {
					drawCallback();
				}
			},
			"responsive" : true,
			"bAutoWidth":defaultOptions.bAutoWidth
		});
		$('#' + tableId).find('.group-checkable').change(function() {
			var set = jQuery(this).attr("data-set");
			var checked = jQuery(this).is(":checked");
			jQuery(set).each(function() {
				if (checked) {
					$(this).prop("checked", true);
				} else {
					$(this).prop("checked", false);
				}
			});
		});
		return mTable;
	}

	function initDatatableServer(tableId, ajax,options,
			drawCallback) {
		var defaultOptions={
			bAutoWidth:true,
			columns:null
		};
		if(options){
			if(options.bAutoWidth){
				defaultOptions.bAutoWidth=options.bAutoWidth;
			}
			if(options.columns){
				defaultOptions.columns=options.columns;
			}
		}
		var i18nUrl = "plugins/datatables/dataTables.chinese.txt";
		var mTable = $('#' + tableId).DataTable({
			'oLanguage' : {
				'sUrl' : checkUrl(i18nUrl)
			},
			"pageLength" : 20,
			"bFilter" : false, // 过滤功能
			"bLengthChange" : false, // 改变每页显示数据数量
			"ordering" : false,
			"deferRender" : true,
			"processing" : false,
			"serverSide" : true,
			"bStateSave" : true,
			"ajax" : {
				"type" : "post",
				"url" : checkUrl(ajax.url),
				"dataSrc" : "data",
				"data" : function(data) {
					if (ajax.data != null) {
						var param = ajax.data();
						param.start = data.start;
						param.length = data.length;
						return param;
					} else {
						return data;
					}
				}
			},
			"columns" : defaultOptions.columns,
			"drawCallback" : function(settings) {
				if (drawCallback != null) {
					drawCallback();
				}
			},
			"responsive" : true,
			"bAutoWidth":defaultOptions.bAutoWidth
		});
		$('#' + tableId).find('.group-checkable').change(function() {
			var set = jQuery(this).attr("data-set");
			var checked = jQuery(this).is(":checked");
			jQuery(set).each(function() {
				if (checked) {
					$(this).prop("checked", true);
				} else {
					$(this).prop("checked", false);
				}
			});
		});
		return mTable;
	}
	
	function initEditorDatatable(tableId,columnDefs){
		var i18nUrl = "plugins/datatables/dataTables.chinese.txt";
		var mTable=$("#"+tableId).DataTable({
			'oLanguage' : {
				'sUrl' : checkUrl(i18nUrl)
			},
			"paging" : false,
			"bFilter" : false,
			"bLengthChange" : false,
			"columnDefs" : columnDefs,
			"bInfo" : false,
			"responsive" : true,
			"autoWidth":false
		});
		return mTable;
	}
	
	function clear(tableId){
		var mTable=$("#"+tableId).DataTable();
		mTable.clear().draw();
	}
	
	var addRow = function(tableId,id,data){
		var mTable = $('#' + tableId).DataTable();
		var rowNode = mTable.row.add(data).draw().node();
		$(rowNode).attr("id",id);
	}
	
	var saveRow = function(url, data, tableId, successFunction,completeFunction) {
		var promise = $.ajax({
			url : checkUrl(url),
			data : data,
			type : "post",
			dataType : "json"
		});
		promise.then(function(response) {
			Toast.show("提示", response.message);
			if (!response.result) {
			} else {
				var mTable = $('#' + tableId).DataTable();
				if(response.data){
					var rowNode = mTable.row.add(response.data).draw().node();
					$(rowNode).attr("id", response.obj);
					if (successFunction != null) {
						successFunction($(rowNode), response);
					}
				}
			}
		});
		promise.then(function(){
			if(completeFunction!=null){
				completeFunction();
			}
		});
	}

	var updateRow = function(url, data, tableId, successFunction ,completeFunction) {
		var promise = $.ajax({
			url : checkUrl(url),
			data : data,
			type : "post",
			dataType : "json"
		});
		promise.then(function(response) {
			Toast.show("提示", response.message);
			if (!response.result) {
			} else {
				var mTable = $('#' + tableId).DataTable();
				var tr = mTable.row("#" + response.obj);
				var oriData = tr.data();
				var data = response.data;
				if(response.data){
					for (var i = 0; i < data.length; i++) {
						oriData[i] = data[i];
					}
					mTable.row("#" + response.obj).data(oriData).draw();
					var rowNode = mTable.row("#" + response.obj).node();
					if (successFunction != null) {
						successFunction(rowNode, response);
					}
				}
			}
		});
		promise.then(function(){
			if(completeFunction!=null){
				completeFunction();
			}
		});
	}

	var deleteRows = function(tableId, url, ids) {
		var mTable = $('#' + tableId).DataTable();
		swal({
			title : "删除提醒",
			text : "是否确定删除？",
			type : "warning",
			allowOutsideClick : true,
			showConfirmButton : true,
			showCancelButton : true,
			confirmButtonClass : "btn-success",
			cancelButtonClass : "btn-danger",
			closeOnConfirm : true,
			closeOnCancel : true,
			confirmButtonText : "确定",
			cancelButtonText : "取消",
		}, function(isConfirm) {
			if (isConfirm) {
				var promise = $.ajax({
					url : checkUrl(url),
					data : {
						ids : ids
					},
					type : "post",
					dataType : "json"
				});
				promise.then(function(response) {
					Toast.show("删除提醒",response.message);
					if (response.result) {
						var idArr = ids.split(",");
						for (i in idArr) {
							$("#" + idArr[i]).addClass("row-delete");
						}
						mTable.rows(".row-delete").remove().draw();
						// 删除关联的ids
						var idArr = response.obj;
						if (idArr && idArr.length > 0) {
							for (i in idArr) {
								$("#" + idArr[i]).addClass("row-delete");
							}
							mTable.rows(".row-delete").remove().draw();
						}
					}
				});
			}
		});
	}

	var deleteRow = function(tableId, url, id) {
		var mTable = $('#' + tableId).DataTable();
		swal({
			title : "删除提醒",
			text : "是否确定删除？",
			type : "warning",
			allowOutsideClick : true,
			showConfirmButton : true,
			showCancelButton : true,
			confirmButtonClass : "btn-success",
			cancelButtonClass : "btn-danger",
			closeOnConfirm : true,
			closeOnCancel : true,
			confirmButtonText : "确定",
			cancelButtonText : "取消",
		}, function(isConfirm) {
			if (isConfirm) {
				var promise = $.ajax({
					url : checkUrl(url),
					data : {
						id : id
					},
					type : "post",
					dataType : "json"
				});
				promise.then(function(response) {
					Toast.show("删除提醒",response.message);
					if (response.result) {
						// 删除本身的id
						mTable.rows("#" + id).remove().draw();
						// 删除关联的ids
						var idArr = response.obj;
						if (idArr && idArr.length > 0) {
							for (i in idArr) {
								$("#" + idArr[i]).addClass("row-delete");
							}
							mTable.rows(".row-delete").remove().draw();
						}
					}
				});
			}
		});
	}

	var modalShow = function(modalId, formId) {
		$(modalId).modal('show');
		modalClose(modalId, formId);
	}

	var modalClose = function(modalId, formId) {
		$(modalId).on('hide.bs.modal', function(e) {
			resetForm(formId);
			formStatus(formId, false);
		});
	}

	var formStatus = function(formId, status) {
		if (status) {
			$(formId + " input").each(function() {
				if ($(this).val()) {
					$(this).addClass("edited");
				}
			});
			// $(formId + " input[type='text']").addClass("edited");
			// $(formId + " input[type='password']").addClass("edited");
			// $(formId + " input[type='number']").addClass("edited");
			$(formId + " textarea").addClass("edited");
		} else {
			$(formId + " input[type='text']").removeClass("edited");
			$(formId + " input[type='password']").removeClass("edited");
			$(formId + " input[type='number']").removeClass("edited");
			$(formId + " textarea").removeClass("edited");
		}
	}

	var resetForm = function(formId) {
		if (formId == null) {
			return;
		}
		$(formId + " input[type='text']").val("");
		$(formId + " input[type='password']").val("");
		$(formId + " input[type='number']").val("");
		$(formId + " input[type='hidden']").val("");
		$(formId + " textarea").val("");
		resetRadio(formId);
		resetCheckbox(formId);
		
		//如果是file上传，需要清空已经上传信息
		$(formId).FileUpload().destory();
	}

	function resetRadio(formId) {
		$(formId + " input[type='radio']").attr("checked", false);
	}

	function resetCheckbox(formId) {
		$(formId + " input[type='checkbox']:checked").attr("checked", false);
	}
	
	//初始化modal:增加,修改,删除,批量删除modal
	var initModal = function(id,saveFunction,updateFunction,deleteFunction,deleteBatchFunction,detailFunction){
		initSaveModal(id,saveFunction);
		initUpdateModal(id,updateFunction);
		initDeleteModal(id,deleteFunction);
		initDetailModal(id,detailFunction);
	}
	
	//初始化删除modal:删除,批量删除modal
	var initDeleteModal = function(id,deleteFunction,deleteBatchFunction){
		deleteModal(id,deleteFunction);
		deleteBatchModal(id,deleteBatchFunction);
	}
	
	//初始化附加的modal
	var initAddModal = function(id,yellowFunction,purpleFunction,greenSharpFunction,blueSharpFunction,greenJungleFunction,purpleSharpFunction){
		initYellowModal(id,yellowFunction);
		initPurpleModal(id,purpleFunction);
		initGreenSharpModal(id,greenSharpFunction);
		initBlueSharpModal(id,blueSharpFunction);
		initGreenJungleModal(id,greenJungleFunction);
		initPurpleSharpModal(id,purpleSharpFunction);
	}
	
	var initSaveModal = function(id,saveFunction){
		$("#"+id+" .save-button").unbind("click");
		$("#"+id+" .save-button").bind("click",function() {
			if(saveFunction){
				saveFunction();
			}
		});
	}
	
	var initUpdateModal = function(id,updateFunction){
		$("#"+id+" .update-button").unbind("click");
		$("#"+id+" .update-button").bind("click", function() {
			if(updateFunction){
				var id=$(this).data("id");
				updateFunction(id);
			}
		});
	}
	
	var initDeleteModal = function(id,deleteFunction){
		$("#"+id+" .delete-button").unbind("click");
		$("#"+id+" .delete-button").bind("click",function(){
			if(deleteFunction){
				var id=$(this).data("id");
				deleteFunction(id);
			}
		});
	}
	
	var initDeleteBatchModal = function(id,deleteBatchFunction){
		$("#"+id+" .batch-delete-button").unbind("click");
		$("#"+id+" .batch-delete-button").bind("click",function(){
			var ids="";
			$(".checkboxes:checked").each(function(index){
				var id=$(this).parent().parent().parent().attr("id");
				if(index==0){
					ids=id;
				}else{
					ids+=","+id;
				}
			});
			if(ids!=''){
				if(deleteBatchFunction){
					deleteBatchFunction(ids);
				}
			}
		});
	}
	
	var initDetailModal = function(id,detailFunction){
		$("#"+id+" .detail-button").unbind("click");
		$("#"+id+" .detail-button").bind("click", function() {
			if(detailFunction){
				var id=$(this).data("id");
				detailFunction(id);
			}
		});
	}
	
	var initPurpleModal = function(id,purpleFunction){
		$("#"+id+" .purple-button").unbind("click");
		$("#"+id+" .purple-button").bind("click",function(){
			if(purpleFunction){
				var id=$(this).data("id");
				purpleFunction(id);
			}
		});
	}

	var initYellowModal = function(id,yellowFunction){
		$("#"+id+" .yellow-button").unbind("click");
		$("#"+id+" .yellow-button").bind("click",function(){
			if(yellowFunction){
				var id=$(this).data("id");
				yellowFunction(id);
			}
		});
	}
	
	var initGreenSharpModal = function(id,greenSharpFunction){
		$("#"+id+" .green-sharp-button").unbind("click");
		$("#"+id+" .green-sharp-button").bind("click",function(){
			if(greenSharpFunction){
				var id=$(this).data("id");
				greenSharpFunction(id);
			}
		});
	}
	
	var initPurpleSharpModal = function(id,purpleSharpFunction){
		$("#"+id+" .purple-sharp-button").unbind("click");
		$("#"+id+" .purple-sharp-button").bind("click",function(){
			if(purpleSharpFunction){
				var id=$(this).data("id");
				purpleSharpFunction(id);
			}
		});
	}
	
	var initBlueSharpModal = function(id,blueSharpFunction){
		$("#"+id+" .blue-sharp-button").unbind("click");
		$("#"+id+" .blue-sharp-button").bind("click",function(){
			if(blueSharpFunction){
				var id=$(this).data("id");
				blueSharpFunction(id);
			}
		});
	}
	
	var initGreenJungleModal = function(id,greenJungleFunction){
		$("#"+id+" .green-jungle-button").unbind("click");
		$("#"+id+" .green-jungle-button").bind("click",function(){
			if(greenJungleFunction){
				var id=$(this).data("id");
				greenJungleFunction(id);
			}
		});
	}
	
	var initCustomModal = function(buttonId,buttonFunction){
		$(buttonId).unbind("click");
		$(buttonId).bind("click",function(){
			if(buttonFunction){
				var id=$(this).data("id");
				buttonFunction(id);
			}
		});
	}
	
	//绑定保存和修改
	var bindSaveAndUpdate = function(id,updateFunction,saveFunction){
		bindUpdate(id,updateFunction);
		bindSave(id,saveFunction);
	}
	
	var bindSave = function(id,saveFunction){
		$("#"+id+" #save").unbind("click");
		$("#"+id+" #save").bind("click",function(){
			if(saveFunction){
				saveFunction();
			}
		});
	}
	
	var bindUpdate = function(id,updateFunction){
		$("#"+id+" #update").unbind("click");
		$("#"+id+" #update").bind("click",function(){
			if(updateFunction){
				updateFunction();
			}
		});
	}
	
	return {
		initDatatable : function(tableId, options,drawCallback) {
			return initDatatable(tableId, options,drawCallback);
		},
		initDatatableServer : function(tableId, ajax, options,drawCallback) {
			return initDatatableServer(tableId, ajax,options,drawCallback);
		},
		initEditorDatatable : function(tableId,columnDefs){
			return initEditorDatatable(tableId,columnDefs);
		},
		clear : function(tableId){
			clear(tableId);
		},
		initModal : function(id,saveFunction,updateFunction,deleteFunction,deleteBatchFunction,detailFunction){
			initModal(id,saveFunction,updateFunction,deleteFunction,deleteBatchFunction,detailFunction);
		},
		initDeleteModal : function(id,deleteFunction,deleteBatchFunction){
			initDeleteModal(id,deleteFunction,deleteBatchFunction);
		},
		initAddModal : function(id,yellowFunction,purpleFunction,greenSharpFunction,blueSharpFunction,greenJungleFunction,purpleSharpFunction){
			initAddModal(id,yellowFunction,purpleFunction,greenSharpFunction,blueSharpFunction,greenJungleFunction,purpleSharpFunction);
		},
		initCustomModal : function(buttonId,buttonFunction){
			initCustomModal(buttonId,buttonFunction);
		},
		initSaveModal : function(id,saveFunction){
			initSaveModal(id,saveFunction);
		},
		initUpdateModal : function(id,updateFunction){
			initUpdateModal(id,updateFunction);
		},
		initDetailModal : function(id,detailFunction){
			initDetailModal(id,detailFunction);
		},
		bindSaveAndUpdate : function(id,saveFunction,updateFunction){
			bindSave(id,saveFunction);
			bindUpdate(id,updateFunction);
		},
		saveRow : function(url, data, tableId, successFunction,completeFunction) {
			saveRow(url, data, tableId, successFunction,completeFunction);
		},
		updateRow : function(url, data, tableId, successFunction,completeFunction) {
			updateRow(url, data, tableId, successFunction,completeFunction);
		},
		modalClose : function(modalId, formId) {
			modalClose(modalId, formId);
		},
		modalShow : function(modalId, formId) {
			modalShow(modalId, formId);
		},
		deleteRow : function(tableId, url, id) {
			deleteRow(tableId, url, id);
		},
		deleteRows : function(tableId, url, ids) {
			deleteRows(tableId, url, ids);
		},
		formStatus : function(formId, status) {
			formStatus(formId, status);
		},
		addRow : function(tableId,id,data){
			addRow(tableId,id,data)
		}
	}
}();