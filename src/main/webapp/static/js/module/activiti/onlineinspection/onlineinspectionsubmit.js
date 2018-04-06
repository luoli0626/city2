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

function ajaxcombotree(obj){
	/* 机构树的下拉选择*/
	$("#"+obj).combotree({
	url : ctx+'/organ/findOrgTreeByLoginRole',
	animate : false,
	checkbox : true,
	multiple : true,
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
}
/*
 * 使用json方式定义每个节点的按钮
 * 以及按钮的功能
 * 
 * open:打开对话框的时候需要处理的任务
 * btns:对话框显示的按钮
 */
var handleOpts = {
		usertask1: {
			width: 300,
			height: 300,
			open: function(id) {
				loadDetail.call(this, id);
			},
			btns: [{
				text: '送检验员',
				handler: function() {
					var taskId =taskIdid;
					  var SubmitDialog=$('<div/>', {
						title: '选择用户组',
						html: "<select id='parentId' name='parentId' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
						  ajaxcombotree("parentId");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId]').val();
							complete(groupid,taskId, [{
								key: 'jydeptpass',
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
				text: '送业务部',
				handler: function() {
				var taskId =taskIdid;
					  var SubmitDialog=$('<div/>', {
						title: '选择用户组',
						html: "<select id='parentId1' name='parentId1' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId1");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId1]').val();
							complete(groupid,taskId, [{
								key: 'jydeptpass',
								value: false,
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
				text: '取消',
				handler: function() {
					//$(this).dialog('close');
					workDialog.dialog('close');
				}
			}]
		},
		usertask2: {
			width: 300,
			height: 300,
			open: function(id) {
				loadDetail.call(this, id);
			},
			btns: [{
				text: '送检验部',
				handler: function() {
				
					var taskId =taskIdid;
					  var SubmitDialog=$('<div/>', {
						title: '选择用户组',
						html: "<select id='parentId2' name='parentId2' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId2");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId2]').val();
							complete(groupid,taskId, [{
								key: 'ywdeptpass',
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
				text: '结束',
				handler: function() {
				   complete("",taskId, [{
								key: 'ywdeptpass',
								value: false,
								type: 'B'
							}]);
				}
			}, {
				text: '取消',
				handler: function() {
					workDialog.dialog('close');
				}
			}]
		},
		usertask3: {
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
						html: "<select id='parentId3' name='parentId3' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId3");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId3]').val();
							complete(groupid,taskId, [{
								key: 'jyuserpass',
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
				text: '退回',
				handler: function() {
				  
				
					var taskId =taskIdid;
					  var SubmitDialog=$('<div/>', {
						title: '选择用户组',
						html: "<select id='parentId4' name='parentId4' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId4");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId4]').val();
							complete(groupid,taskId, [{
								key: 'jyuserpass',
								value: false,
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
				text: '取消',
				handler: function() {
					workDialog.dialog('close');
				}
			}]
		},
		usertask4: {
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
						html: "<select id='parentId5' name='parentId5' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId5");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId5]').val();
							complete(groupid,taskId, "");
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
				text: '取消',
				handler: function() {
					workDialog.dialog('close');
				}
			}]
		},
		usertask5: {
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
						html: "<select id='parentId6' name='parentId6' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId6");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId6]').val();
							complete(groupid,taskId, "");
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
				text: '取消',
				handler: function() {
					workDialog.dialog('close');
				}
			}]
		},
		usertask6: {
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
						html: "<select id='parentId7' name='parentId7' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId7");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId7]').val();
							complete(groupid,taskId, "");
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
				text: '取消',
				handler: function() {
					workDialog.dialog('close');
				}
			}]
		},
		usertask7: {
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
						html: "<select id='parentId8' name='parentId8' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     ajaxcombotree("parentId8");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId8]').val();
							complete(groupid,taskId, [{
								key: 'shdeptpass',
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
			},  {
				text: '退回报告',
				handler: function() {
					var taskId =taskIdid;
					  var SubmitDialog=$('<div/>', {
						title: '选择用户组',
						html: "<select id='parentId10' name='parentId10' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId10");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId10]').val();
							complete(groupid,taskId, [{
								key: 'shbeckbgpass',
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
				text: '取消',
				handler: function() {
					workDialog.dialog('close');
				}
			}]
		},
		usertask8: {
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
						html: "<select id='parentId11' name='parentId11' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     ajaxcombotree("parentId11");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId11]').val();
							complete(groupid,taskId, [{
								key: 'spdeptpass',
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
				text: '退回报告',
				handler: function() {
					var taskId =taskIdid;
					  var SubmitDialog=$('<div/>', {
						title: '选择用户组',
						html: "<select id='parentId13' name='parentId13' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId13");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId13]').val();
							complete(groupid,taskId, [{
								key: 'spbeckbgpass',
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
				text: '退回审核',
				handler: function() {
					var taskId =taskIdid;
					  var SubmitDialog=$('<div/>', {
						title: '选择用户组',
						html: "<select id='parentId14' name='parentId14' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     ajaxcombotree("parentId14");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId14]').val();
							complete(groupid,taskId, [{
								key: 'spbeckshpass',
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
				text: '退回报告',
				handler: function() {
					var taskId =taskIdid;
					  var SubmitDialog=$('<div/>', {
						title: '选择用户组',
						html: "<select id='parentId15' name='parentId15' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     ajaxcombotree("parentId15");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId15]').val();
							complete(groupid,taskId, [{
								key: 'shbeckbgpass',
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
				text: '取消',
				handler: function() {
					workDialog.dialog('close');
				}
			}]
		},
		usertask9: {
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
						html: "<select id='parentId16' name='parentId16' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId16");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId16]').val();
							complete(groupid,taskId, "");
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
				text: '取消',
				handler: function() {
					workDialog.dialog('close');
				}
			}]
		},
		usertask10: {
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
						html: "<select id='parentId17' name='parentId17' style='width: 200px;' required='true'></select>"
					}).dialog({
						modal: true,
						onOpen: function() {
							/* 机构树的下拉选择*/
					     	ajaxcombotree("parentId17");
						},
						buttons: [{
							text: '提交',
							handler: function() {
							// 设置流程变量
							var groupid=$('[name=parentId17]').val();
							complete(groupid,taskId, "");
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
				text: '取消',
				handler: function() {
					workDialog.dialog('close');
				}
			}]
		},
		usertask11: {
			width: 300,
			height: 300,
			open: function(id) {
				loadDetail.call(this, id);
			},
			btns: [{
				text: '归档',
				handler: function() {
					var taskId =taskIdid;
					complete("",taskId, "");
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