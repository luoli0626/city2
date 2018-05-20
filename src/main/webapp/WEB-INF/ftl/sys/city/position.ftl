<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var pointDialog;
	var positionDialog;
	$(function() {
	
		$('#positionType').combobox({
		    url:'${ctx}/cityPage/positionTypeSelect',
		    valueField:'id',
		    textField:'name',
		    required : true
		});
		
		positionForm = $('#positionForm').form();

		datagrid = $('#datagrid').datagrid({
			url : '${ctx}/cityPage/positionList',
			toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			rownumbers: true,
			pagination : true,
			pageSize:30,
			pageList:[10,20,30,50,100],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			sortName : 'id',
			singleSelect : true,
			frozenColumns : [ [ {
				field : 'id',
				title : 'id',
				width : $(this).width()*0.2,
				sortable : true,
				hidden:true
			}] ],
			columns : [ [ {
				field : 'name',
				title : '地点名称',
				width :$(this).width()*0.2,
			},
			{
				field : 'longitude',
				title : '经度',
				width :$(this).width()*0.2,
			},
			{
				field : 'latitude',
				title : '纬度',
				width :$(this).width()*0.2,
			},
			{
			    title : '操作',
			    field : '1',
			    width : $(this).width() * 0.13,
			    formatter:function(value,rec,i){
			    var btnHtml="";   
			    btnHtml="<a href=' javascript:edit("+i+");' plain='true'  iconcls='icon-edit' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px;'>编辑</span></span></a >";
			    btnHtml+="<a href='javascript:remove("+i+");' plain='true'  iconcls='icon-remove' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove' style='padding-left: 20px;'>删除</span></span></a >";
			    return btnHtml;
		    }
		    }] ],
			onLoadSuccess:function(data) {
				console.log(data);
			}
		});
		
		
		positionDialog = $('#positionDialog').show().dialog({
			modal : true,
			title : '添加地点',
			width:500,
			height:300,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					if(positionForm.form('validate')){
					console.log($("#positionType").combobox('getValue'));
						var formData={
								"positionId":$("#positionId").val(),
								"positionName":$("#positionName").val(),
								"longitude":$("#regFund").val(),
								"latitude":$("#staffNum").val(),
								"positionType":$("#positionType").combobox('getValue')
						};	
						$.ajax({
							url:'${ctx}/cityPage/alterPosition',
							data:JSON.stringify(formData),
							contentType:"application/json",
				 			success:function(result){
								if (result.success) {	
									console.log(result.success);					 
									positionDialog.dialog('close');
									$.messager.alert('地址'+result.msg,"成功");
									datagrid.datagrid('reload');
								}else{
									positionDialog.dialog('close');
									$.messager.alert('地址'+result.msg,"成功");
									datagrid.datagrid('reload');
								}
							}
						});
					}
				}
			} ]
		}).dialog('close');
		
		pointDialog = $('#pointDialog').show().dialog({
			modal : true,
			title : "获取经纬度",
			width: ($(window).width())*0.95,
   			height:($(window).height())*0.8,
			onBeforeClose:function(){
				
			},
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
				getPointsave();
				}
			} ]
		}).dialog('close');
	});

	function searchFun() {
		datagrid.datagrid('load', {
			positionName : $('#toolbar input[name=positionName]').val(),
		});
	}
	
	
	function append(flag) {
		
		$('#positionDialog').dialog('setTitle', '<font">添加地点</font>');  
		$('#positionDialog').dialog('open');
		positionForm.form('clear');
		
	}
	
	function edit(index){
		$('#positionDialog').dialog('setTitle', '<font">地点信息</font>');  
	        var rows = $("#datagrid").datagrid("getRows")[index];
	        console.log(rows);
			$('#positionDialog').dialog('open');
			positionForm.form('clear');
			$("#positionId").val(rows.id);
			$("#positionType").combobox('setValue',rows.type);
			positionForm.form('load', {
				"positionName":rows.name,
				"regFund":rows.longitude,
			    "staffNum":rows.latitude,
		});
	}
	
	function remove(index) {
		var ids = [];
		var rows = $("#datagrid").datagrid("getRows")[index];
		if (rows) {
			$.messager.confirm('请确认', '您要删除当前所选地址？', function(r) {
				if (r) {	
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/removePositions',
						data : JSON.stringify(ids),
						cache : false,
						dataType : "json",
						contentType:"application/json",
						success : function(data) {
							if(data.success){
								datagrid.datagrid('unselectAll');
								datagrid.datagrid('reload');
								$.messager.alert('地址删除',"成功");
							}
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
	}
	
	function clearFun() {
		$('#toolbar input').val('');
		datagrid.datagrid('load', {});
	}
	
	function getPointsave(){
	
	  $("#regFund").val($("#longitude").val());
	  $("#staffNum").val($("#latitude").val());
	   pointDialog.dialog("close");
	}
	function getPoint(){
	 
	 //清空覆盖物  
            map.clearOverlays(); 
            pointDialog.dialog("open");
	  var provincesname='河北省石家庄';
	  myGeo.getPoint(provincesname, function (point) {  
            setPoint(point);  
			$("#longitude").val(point.lng);  
			$("#latitude").val(point.lat); 
        }, "全国");
	}
	
</script>



</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;">
			<fieldset class="my_fieldset">
				<legend class="my_legend">检索</legend>
				<table>
					<tr>
						<td>地点名称：</td>
						<td colspan="2"><input name="positionName" class="basic_input" />
						</td>
						<td>&nbsp;<a class="easyui-linkbutton" iconCls="icon-search"  plain='true' onclick="searchFun();" href="javascript:void(0);">查 找</a>&nbsp;
						<a class="easyui-linkbutton" iconCls="icon-empty"  plain='true' onclick="clearFun();" href="javascript:void(0);">清 空</a>
						</td>
					</tr>
				</table>
			</fieldset>
			
			
		    <div class="buttonList">
				<#if fmfn.checkButton(requestURI,"icon-add")>
					<a class="easyui-linkbutton" iconCls="icon-add"  plain='true' onclick="append('1');"  href="javascript:void(0);">增加</a> 
				</#if>
				<#if fmfn.checkButton(requestURI,"icon-remove")>
					<a class="easyui-linkbutton" iconCls="icon-remove"  plain='true' onclick="userRole();"  href="javascript:void(0);">删除</a> 
				</#if>
			</div>
			
			
		</div>
		<table id="datagrid" border="1"></table>
	</div>
	
	<div id="positionDialog" style="display: none;overflow: hidden;background:#FFFFFF;padding:20px 20px;">
		<form id="positionForm" method="post" enctype="multipart/form-data">
			<fieldset class="my_fieldset">
				<legend class="my_legend">地点信息</legend>
				<table class="tableForm">
					<tr style="display: none;">
						<td id="positionId" name="positionId">
						</td>
					</tr>
					<tr>
						<th >地点名称：</th>
						<td>
							<input name="positionName" id="positionName" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					
					<tr>
						<th >所属类型：</th>
						<td>
							<input name="positionType" id="positionType" class="easyui-validatebox" />
						</td>
					</tr>
				<tr>
					<th>经 度：</th>
					<td><input id="regFund" name="regFund" class="easyui-validatebox" required="true" style="width: 150px;" /></td>
				</tr>
				<tr>
					<th>纬 度：</th>
					<td><input id="staffNum" name="staffNum" class="easyui-validatebox" required="true" style="width: 150px;" /></td>
				</tr>				
				<tr>
					<th><a href="#" onclick="getPoint();">选择地点对应经纬度</a></th>
				</tr>				
				</table>
			</fieldset>
		</form>
	</div>
		<div id="pointDialog" style="display: none;overflow: hidden;background:#FFFFFF;padding:20px 20px;">
	<div id="r-result" style="width:100%; font-size:14px;">
		 <input id="cityName" type="text" style="width:100px; margin-right:10px;" />
		<a href="#"  onclick="theLocation()" >搜索您要添加的位置</a>
		 </br>
		 </br>
	     </div>
			<div style="width: 100%; height: 400px; border: 1px solid gray; float: left;" id="mapdiv"></div> 
			
	     <div  style="width:100%; font-size:14px;">
	     </br>
	               经度：<input id="longitude" type="text" readonly="readonly" style="width:100px; margin-right:10px;"/>
		维度： <input id="latitude" type="text" readonly="readonly"  style="width:100px; margin-right:10px;"/>
		</div>
	</div>
</body>
</html>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=8277432c34c6f58ba75d3a541ed2559e"></script>  

<script type="text/javascript" charset="UTF-8">
var organDialog;
	var pointDialog;
	var organForm;
	var selectedNodeId;
	var parentName;
	var url;
	var orgTypeData=${orgType!'[]'};
    // 百度地图API功能
	var map = new BMap.Map("mapdiv");
	//创建地址解析的实例  
    var myGeo = new BMap.Geocoder(); 
	setPoint(new BMap.Point(116.331398,39.897445));
	function setPoint(point){
	    $("#longitude").val(point.lng);
	    $("#latitude").val(point.lat);
		map.centerAndZoom(point,12); 
		map.enableScrollWheelZoom(true);
		var marker = new BMap.Marker(point);// 创建标注
		map.addOverlay(marker);             // 将标注添加到地图中
		marker.enableDragging();           // 可拖拽
		 //创建信息窗口  
            var infoWindow = new BMap.InfoWindow("请拖动红色图标选择项目<br/>所在位置。");  
            //显示窗口  
            marker.openInfoWindow(infoWindow);  
            //点击监听  
            marker.addEventListener("click", function () {  
                this.openInfoWindow(infoWindow);  
            });  
            //拖动监听  
            marker.addEventListener("dragend", function (e) {  
			
                //坐标赋值  
                $("#longitude").val(e.point.lng);
	            $("#latitude").val(e.point.lat); 
            });  
	}
		// 用经纬度设置地图中心点
	function theLocation(){
		var city =  $("#cityName").val();
		if(city != ""){
			map.centerAndZoom(city,11);      // 用城市名设置地图中心点
			
			//清空覆盖物  
            map.clearOverlays();    
            myGeo.getPoint(city, function (point) {  
            setPoint(point);  
			$("#longitude").val(point.lng);  
			$("#latitude").val(point.lat); 
        }, "全国"); 
			
		}
	}
	</script>