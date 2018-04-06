<div style="padding:10px 10px" class='taskList'>
	<table style="width:100%;height:100%;">
			
			 <#if taskList?exists>
	    	 <#list taskList as key>
	    	 <tr>
	    		<td><img src="${static}/images/task_label.png"> <a href="javascript:editProcess(${key.id});" >${key.dataTime}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${key.name}（${key.processType}）在${key.orgName}${key.sceneName}场景发现问题</a></a></td>
	    	 </tr>
	    	 </#list>
	        </#if>
				
			
		
	</table>
</div>
<style>
	.taskList tr{
		height:21px;
	}
	.taskList tr td{
		padding-left:15px;
		width:180px;
	}
	.taskList tr td a span{
		color:red;
	}
</style>
<script type="text/javascript" charset="UTF-8">

  //编辑
    function editProcess(index){
	    top.addTabFun({src:'${ctx}/process/editProcess?id='+index,title:'问题确认'});
	}
</script>