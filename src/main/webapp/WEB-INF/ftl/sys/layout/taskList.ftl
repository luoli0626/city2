<div style="padding:10px 10px" class='taskList'>
	<table style="width:100%;height:100%;">
			
			 <#if taskList?exists>
	    	 <#list taskList as key>
	    	 <tr>
	    		<td><img src="${static}/images/task_label.png"> <#if key.state=="1"><a href="javascript:editProcess(${key.id},${key.state});" >${key.dataTime}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${key.name}（${key.processType}）在${key.orgName}${key.sceneName}场景发现问题</a></a><#else><a href="javascript:editProcess(${key.id},${key.state});" >${key.dataTime}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${key.name}（${key.processType}）在${key.orgName}${key.sceneName}场景发现问题已解决，<span>请审核</span></a></a></#if></td>
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
    function editProcess(index,state){
      if(state=="1"){
	    top.addTabFun({src:'${ctx}/process/editProcess?id='+index,title:'问题确认'});
	    }else{
	    top.addTabFun({src:'${ctx}/process/checkProcess?id='+index,title:'审核项目'});
	    }
	}
</script>