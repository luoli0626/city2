/**
 * 请假流程任务办理
 */
var  taskIdid;
var workDialog;

// 用于保存加载的详细信息
var detail = {};
 
/**
 * 加载详细信息
 * @param {Object} id
 */
function loadDetail(id, withVars, callback) {
    var dialog = this;
    $.getJSON(ctx + '/leave/detail?id=' + id, function(data) {
        detail = data;
        
        $.each(data, function(k, v) {
			// 格式化日期
			   $('.view-info td[name=' + k + ']', dialog).text(v);
        });
		if ($.isFunction(callback)) {
			callback(data);
		}
    });
}


function loadDetailWithTaskVars(leaveId, taskId, callback) {
    var dialog = this;
    $.getJSON(ctx + '/leave/detailwithvars?id=' + leaveId + "&taskId=" + taskId, function(data) {
        detail = data;
        $.each(data, function(k, v) {
        	 // 格式化日期
	            $('.view-info td[name=' + k + ']', dialog).text(v);
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
function complete(groupid,taskId, variables) {
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
	
	// 发送任务完成请求
    $.post(ctx + '/leave/complete?taskId=' + taskId+'&groupid='+groupid, {
        keys: keys,
        values: values,
        types: types
    }, function(resp) {
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
		
		deptLeaderAudit: {
			width: 300,
			height: 300,
			open: function(id) {
				loadDetail.call(this, id);
			},
			btns: [{
				text: '同意',
				handler: function() {
					var taskId =taskIdid;
					  var SubmitDialog=$('<div/>', {
						title: '选择用户组',
						html: "<select id='parentId' name='parentId' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	var parentSelect=$('#parentId').combotree({
								url : ctx+'/organ/findOrgTreeByLoginRole',
								animate : false,
								lines : !sy.isLessThanIe8(),
								onLoadSuccess : function(node, data) {
									var t = $(this);
									if (data) {
										$(data).each(function(index, d) {
											if (this.state == 'closed') {
												t.tree('expandAll');
											}
										});
									}
								}
							});
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId]').val();
							complete(groupid,taskId, [{
								key: 'deptLeaderPass',
								value: true,
								type: 'B'
							}]);
							}
						}, {
							text: '取消',
							handler: function() {
								SubmitDialog.dialog('close');
								
							}
						}]
					});
					
				}
			}, {
				text: '驳回',
				handler: function() {
					var taskId = taskIdid;
					var BackDialog=$('<div/>', {
						title: '填写驳回理由',
						html: "<textarea id='deptBackReason' style='width: 250px; height: 60px;'></textarea>"
					}).dialog({
						modal: true,
						open: function() {
							
						},
						buttons: [{
							text: '驳回',
							handler: function() {
								var deptBackReason = $('#deptBackReason').val();
								if (deptBackReason == '') {
									alert('请输入驳回理由！');
									return;
								}
								
								// 设置流程变量
								complete(taskId, [{
									key: 'deptLeaderPass',
									value: false,
									type: 'B'
								}, {
									key: 'deptBackReason',
									value: deptBackReason,
									type: 'S'
								}]);
							}
						}, {
							text: '取消',
							handler: function() {
								BackDialog.dialog('close');
								
							}
						}]
					});
				}
			}, {
				text: '取消',
				handler: function() {
					//$(this).dialog('close');
					workDialog.dialog('close');
				}
			}]
		},
		hrAudit: {
			width: 300,
			height: 300,
			open: function(id) {
				loadDetail.call(this, id);
			},
			btns: [{
				text: '同意',
				handler: function() {
				
					var taskId =taskIdid;
					  var SubmitDialog=$('<div/>', {
						title: '选择用户组',
						html: "<select id='parentId' name='parentId' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	var parentSelect=$('#parentId').combotree({
								url : ctx+'/organ/findOrgTreeByLoginRole',
								animate : false,
								checkbox : true,
								lines : !sy.isLessThanIe8(),
								onLoadSuccess : function(node, data) {
									var t = $(this);
									if (data) {
										$(data).each(function(index, d) {
											if (this.state == 'closed') {
												t.tree('expandAll');
											}
										});
									}
								}
							});
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId]').val();
							complete(groupid,taskId, [{
								key: 'hrPass',
								value: true,
								type: 'B'
							}]);
							}
						}, {
							text: '取消',
							handler: function() {
								SubmitDialog.dialog('close');
								
							}
						}]
					});
				}
			}, {
				text: '驳回',
				handler: function() {
					var taskId = taskIdid;
					
					var BackDialog=$('<div/>', {
						title: '填写驳回理由',
						html: "<textarea id='hrBackReason' style='width: 250px; height: 60px;'></textarea>"
					}).dialog({
						modal: true,
						open: function() {
							
						},
						buttons: [{
							text: '驳回',
							handler: function() {
								var hrBackReason = $('#hrBackReason').val();
								if (hrBackReason == '') {
									alert('请输入驳回理由！');
									return;
								}
								
								// 设置流程变量
								complete(taskId, [{
									key: 'hrPass',
									value: false,
									type: 'B'
								}, {
									key: 'hrBackReason',
									value: hrBackReason,
									type: 'S'
								}]);
							}
						}, {
							text: '取消',
							handler: function() {
								BackDialog.dialog('close');
								
							}
						}]
					});
				}
			}, {
				text: '取消',
				handler: function() {
					workDialog.dialog('close');
				}
			}]
		},
		modifyApply: {
		width: 330,
		height: 320,
		open: function(id, taskId) {
			var dialog = this;
			// 打开对话框的时候读取请假内容
			loadDetailWithTaskVars.call(this, id, taskId, function(data) {
				// 显示驳回理由
				$('.info').show().html("<b>部门意见：</b>" + (data.variables.deptBackReason || "") 
						             + "<br/><b>人事意见：</b>" + (data.variables.hrBackReason || "")
						            	);
				// 读取原请假信息
				$('#modifyApplyContent #askleaveinfoCount').val(data.askleaveinfoCount);
				$('#modifyApplyContent #askleaveinfoStarttime').val(data.askleaveinfoStarttime);
				$('#modifyApplyContent #askleaveinfoEndtime').val(data.askleaveinfoEndtime);
				$('#modifyApplyContent #askleaveinfoReason').val(data.askleaveinfoReason);
				
			});
			$('#modifyApplyContentFieldset').hide();
			// 切换状态
			$("input[name=reApply]").click(function(){
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
			handler: function() {
				var taskId = taskIdid;
				var reApply = $(':radio[name=reApply]:checked').val();
				
				// 提交的时候把变量
				complete(taskId, [{
					key: 'reApply',
					value: reApply,
					type: 'B'
				}, {
					key: 'onecardType',
					value: $('#modifyApplyContent #askleaveinfoCount').val(),
					type: 'S'
				}, {
					key: 'askleaveinfoStarttime',
					value: $('#modifyApplyContent #askleaveinfoStarttime').val(),
					type: 'D'
				}, {
					key: 'askleaveinfoEndtime',
					value: $('#modifyApplyContent #askleaveinfoEndtime').val(),
					type: 'D'
				}, {
					key: 'askleaveinfoReason',
					value: $('#modifyApplyContent #askleaveinfoReason').val(),
					type: 'S'
				}]);
			}
		},{
			text: '取消',
			handler: function() {
				$(this).dialog('close');
			}
		}]
	},
	reportBack:{
		width: 300,
		height: 300,
		open: function(id, taskId) {
			// 打开对话框的时候读取请假内容
			loadDetail.call(this, id, taskId);
			
		},
		btns: [{
			text: '提交',
			handler: function() {
				var realityStartTime = $('#realityStartTime').val();
				var realityEndTime = $('#realityEndTime').val();
				
				if (realityStartTime == '') {
					alert('请选择实际请假时间！');
					return;
				}
				
				if (realityEndTime == '') {
					alert('请选择实际请假时间！');
					return;
				}
				
				var taskId = taskIdid;
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
			handler: function() {
				$(this).dialog('close');
			}
		}]
	}
};


/**
 * 办理流程
 */

function handlethis(taskId) {
	 $.getJSON(ctx + '/leave/getTask?taskId=' + taskId, function(data) {
		var taskDefinitionKey=data.workflowTaskEntity.taskDefinitionKey;
		var tname=data.workflowTaskEntity.name;
		var  rowid=data.id;
		taskIdid=data.workflowTaskEntity.id;
		workDialog=$('#' + taskDefinitionKey).show().data({
			taskId: taskId
		}).dialog({
		    autoOpen: false,
			title: '流程办理[' + tname + ']',
			modal: true,
			width:handleOpts[taskDefinitionKey].width,
			height: handleOpts[taskDefinitionKey].height,
			onOpen: function() {
			  handleOpts[taskDefinitionKey].open.call(this, rowid, taskId);
			},
			buttons:handleOpts[taskDefinitionKey].btns
		});
	 });
 }