/**
 * 请假流程任务办理
 */

// 用于保存加载的详细信息
var detail = {};
 
/**
 * 加载详细信息
 * @param {Object} id
 */
function loadDetail(id, withVars, callback) {

    var dialog = this;
    $.getJSON(ctx + '/oneCardController.ftl?detail&id=' + id+"&timetemp="+new Date(), function(data) {
        detail = data;
        
        $.each(data, function(k, v) {
			// 格式化日期
		//	if (k == 'applyTime' || k == 'startTime' || k == 'endTime') {
		//		alert(k+" "+v);
		//		$('.view-info td[name=' + k + ']', dialog).text(v);
		//	} else {
			
			   $('.view-info td[name=' + k + ']', dialog).text(v);
		
		//	}
        });
		if ($.isFunction(callback)) {
			callback(data);
		}
    });
}


function loadDetailWithTaskVars(leaveId, taskId, callback) {
    var dialog = this;
    $.getJSON(ctx + '/oneCardController.ftl?detailwithvars&id=' + leaveId + "&taskId=" + taskId+"&timetemp="+new Date(), function(data) {
        detail = data;
        $.each(data, function(k, v) {
        	 // 格式化日期
			if (k == 'applyTime' || k == 'startTime' || k == 'endTime') {
				$('.view-info td[name=' + k + ']', dialog).text(new Date(v).format('yyyy-MM-dd hh:mm'));
			} else {
	            $('.view-info td[name=' + k + ']', dialog).text(v);
			}
        });
		if ($.isFunction(callback)) {
			callback(data);
		}
    });
}




/**
 * 完成任务
 * @param {Object} taskId
 */
function complete(taskId, variables) {
    var dialog = this;
    
	// 转换JSON为字符串
    var keys = "", values = "", types = "";
	if (variables) {
		$.each(variables, function() {
			if (keys != "") {
				keys += ",";
				values += ",";
				types += ",";
			}
			keys += this.key;
			values += this.value;
			types += this.type;
		});
	}
	
	$.blockUI({
        message: '<h2><img src="' + ctx + '/static/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
    });
	
	// 发送任务完成请求
    $.post(ctx + '/oneCardController.ftl?complete&id=' + taskId+"&timetemp="+new Date(), {
        keys: keys,
        values: values,
        types: types
    }, function(resp) {
		$.unblockUI();
        if (resp == 'success') {
            alert('任务完成');
            location.reload();
        } else {
            alert('操作失败!');
        }
    });
}


/*
 * 使用json方式定义每个节点的按钮
 * 以及按钮的功能
 * 
 * open:打开对话框的时候需要处理的任务
 * btns:对话框显示的按钮
 */
var handleOpts = {
		
		techAudit: {
			width: 300,
			height: 300,
			open: function(id) {
				// 打开对话框的时候读取请假内容
				loadDetail.call(this, id);
			},
			btns: [{
				text: '同意',
				click: function() {
					var taskId = $(this).data('taskId');
	
					// 设置流程变量
					complete(taskId, [{
						key: 'school_techPass',
						value: true,
						type: 'B'
					}]);
				}
			}, {
				text: '驳回',
				click: function() {
					var taskId = $(this).data('taskId');
					
					$('<div/>', {
						title: '填写驳回理由',
						html: "<textarea id='techBackReason' style='width: 250px; height: 60px;'></textarea>"
					}).dialog({
						modal: true,
						open: function() {
							
						},
						buttons: [{
							text: '驳回',
							click: function() {
								var techBackReason = $('#techBackReason').val();
								if (techBackReason == '') {
									alert('请输入驳回理由！');
									return;
								}
								
								// 设置流程变量
								complete(taskId, [{
									key: 'school_techPass',
									value: false,
									type: 'B'
								}, {
									key: 'techBackReason',
									value: techBackReason,
									type: 'S'
								}]);
							}
						}, {
							text: '取消',
							click: function() {
								$(this).dialog('close');
								$('#deptLeaderAudit').dialog('close');
							}
						}]
					});
				}
			}, {
				text: '取消',
				click: function() {
					$(this).dialog('close');
				}
			}]
		},	
	  //学校审核
		schAudit: {
			width: 300,
			height: 300,
			open: function(id) {
				// 打开对话框的时候读取请假内容
				loadDetail.call(this, id);
			},
			btns: [{
				text: '同意',
				click: function() {
					var taskId = $(this).data('taskId');
					
					// 设置流程变量
					complete(taskId, [{
						key: 'school_schPass',
						value: true,
						type: 'B'
					}]);
				}
			}, {
				text: '驳回',
				click: function() {
					var taskId = $(this).data('taskId');
					
					$('<div/>', {
						title: '填写驳回理由',
						html: "<textarea id='schBackReason' style='width: 250px; height: 60px;'></textarea>"
					}).dialog({
						modal: true,
						open: function() {
							
						},
						buttons: [{
							text: '驳回',
							click: function() {
								var schBackReason = $('#schBackReason').val();
								if (schBackReason == '') {
									alert('请输入驳回理由！');
									return;
								}
								
								// 设置流程变量
								complete(taskId, [{
									key: 'school_schPass',
									value: false,
									type: 'B'
								}, {
									key: 'schBackReason',
									value: schBackReason,
									type: 'S'
								}]);
							}
						}, {
							text: '取消',
							click: function() {
								$(this).dialog('close');
								$('#deptLeaderAudit').dialog('close');
							}
						}]
					});
				}
			}, {
				text: '取消',
				click: function() {
					$(this).dialog('close');
				}
			}]
		},
	//资助科
	zizhuAudit: {
		width: 300,
		height: 300,
		open: function(id) {
			// 打开对话框的时候读取请假内容
			loadDetail.call(this, id);
		},
		btns: [{
			text: '同意',
			click: function() {
				var taskId = $(this).data('taskId');
				
				// 设置流程变量
				complete(taskId, [{
					key: 'school_zizhuPass',
					value: true,
					type: 'B'
				}]);
			}
		}, {
			text: '驳回',
			click: function() {
				var taskId = $(this).data('taskId');
				
				$('<div/>', {
					title: '填写驳回理由',
					html: "<textarea id='zizhuBackReason' style='width: 250px; height: 60px;'></textarea>"
				}).dialog({
					modal: true,
					buttons: [{
						text: '驳回',
						click: function() {
							var hrBackReason = $('#zizhuBackReason').val();
							if (hrBackReason == '') {
								alert('请输入驳回理由！');
								return;
							}
							
							// 设置流程变量
							complete(taskId, [{
								key: 'school_zizhuPass',
								value: false,
								type: 'B'
							}, {
								key: 'zizhuBackReason',
								value: hrBackReason,
								type: 'S'
							}]);
						}
					}, {
						text: '取消',
						click: function() {
							$(this).dialog('close');
							$('#deptLeaderAudit').dialog('close');
						}
					}]
				});
			}
		}, {
			text: '取消',
			click: function() {
				$(this).dialog('close');
			}
		}]
	},
	
	userModify: {
		width: 330,
		height: 320,
		open: function(id, taskId) {
			var dialog = this;
			
			$('#startTime,#endTime', this).datetimepicker({
	            stepMinute: 5
	        });
			
			// 打开对话框的时候读取请假内容
			loadDetailWithTaskVars.call(this, id, taskId, function(data) {
				// 显示驳回理由
				$('.info').show().html("<b>辅导员意见：</b>" + (data.variables.techBackReason || "") 
						             + "<br/><b>学院意见：</b>" + (data.variables.schBackReason || "")
						             + "<br/><b>资助科意见：</b>" + (data.variables.zizhuBackReason || "")
						             //+ "<br/><b>学院意见：</b>" + (data.variables.schBackReason || "")
						            	);
				// 读取原请假信息
				$('#modifyApplyContent #onecardType option[value=' + data.onecardType + ']').attr('selected', true);
				$('#modifyApplyContent #startTime').val(data.startTime);
				$('#modifyApplyContent #endTime').val(data.endTime);
				$('#modifyApplyContent #reason').val(data.reason);
				
			});
			$('#modifyApplyContentFieldset').hide();
			// 切换状态
			$("#radio").buttonset().change(function(){
				var type = $(':radio[name=reApply]:checked').val();
				if (type == 'true') {
					$('#modifyApplyContent').show();
					$('#modifyApplyContentFieldset').show();
				} else {
					$('#modifyApplyContent').hide();
					$('#modifyApplyContentFieldset').hide();
				}
			});
		},
		btns: [{
			text: '提交',
			click: function() {
				var taskId = $(this).data('taskId');
				var reApply = $(':radio[name=reApply]:checked').val();
				
				// 提交的时候把变量
				complete(taskId, [{
					key: 'reApply',
					value: reApply,
					type: 'B'
				}, {
					key: 'onecardType',
					value: $('#modifyApplyContent #onecardType').val(),
					type: 'S'
				}, {
					key: 'startTime',
					value: $('#modifyApplyContent #startTime').val(),
					type: 'D'
				}, {
					key: 'endTime',
					value: $('#modifyApplyContent #endTime').val(),
					type: 'D'
				}, {
					key: 'reason',
					value: $('#modifyApplyContent #reason').val(),
					type: 'S'
				}]);
			}
		},{
			text: '取消',
			click: function() {
				$(this).dialog('close');
			}
		}]
	},
	yikatongBack: {
		width: 330,
		height: 380,
		open: function(id, taskId) {
			// 打开对话框的时候读取请假内容
			loadDetail.call(this, id, taskId);
			$('#realityStartTime,#realityEndTime').datetimepicker({
	            stepMinute: 5
	        });
		},
		btns: [{
			text: '提交',
			click: function() {
				var realityStartTime = $('#realityStartTime').val();
				var realityEndTime = $('#realityEndTime').val();
				
				if (realityStartTime == '') {
					alert('请选择实际开始时间！');
					return;
				}
				
				if (realityEndTime == '') {
					alert('请选择实际结束时间！');
					return;
				}
				
				var taskId = $(this).data('taskId');
				complete(taskId, [{
					key: 'realityStartTime',
					value: realityStartTime,
					type: 'D'
				}, {
					key: 'realityEndTime',
					value: realityEndTime,
					type: 'D'
				}]);
			}
		},{
			text: '取消',
			click: function() {
				$(this).dialog('close');
			}
		}]
	}
};

/**
 * 办理流程
 */
function handlethis(taskId) {
	alert("111");
	 $.getJSON(ctx + '/oneCardController.ftl?getTask&taskId=' + taskId+"&timetemp="+new Date(), function(data) {
		 
		var taskDefinitionKey=data.workflowTaskEntity.taskDefinitionKey;
		var tname=data.workflowTaskEntity.name;
		var  rowid=data.id;
		var  taskId=data.workflowTaskEntity.id;
		
		//tkey,tname,rowId,
		// 使用对应的模板
		$('#' + taskDefinitionKey).data({
			taskId: taskId
		}).dialog({
			title: '流程办理[' + tname + ']',
			modal: true,
			width: handleOpts[taskDefinitionKey].width,
			height: handleOpts[taskDefinitionKey].height,
			open: function() {
				handleOpts[taskDefinitionKey].open.call(this, rowid, taskId);
			},
			buttons: handleOpts[taskDefinitionKey].btns
		});
	 });
 }