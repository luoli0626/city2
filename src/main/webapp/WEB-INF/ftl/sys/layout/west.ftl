<#include "/inc/meta.ftl"/>
 <link rel="stylesheet" href="static/css/styles.css" type="text/css"></link>
<script type="text/javascript" charset="UTF-8">
	var tree;
	var ticketNew = '${(ticket)!''}';
	$(function() {	
		var url = '${ctx}/main/tree';
			$.post(url,function(da){
			menu(da);
				}); 
			});
		function menu(TreeModel){
			for(var i = 0; i < TreeModel.length;i++){
			$('#menu').accordion('add', {
			title: TreeModel[i].text,
			content: TreeP(TreeModel[i].children),
			selected: false
					});
				}
			}
	
			function TreeP(parent){
			 if(null != parent){
			 
			 var text = '';
			for(var i=0;i<parent.length;i++){
			  //alert(parent[i].attributes.src);
			var state = parent[i].state+'';
			text +=  '<div name="thisdiv" class = "txt002"><a name="thisa" title="'+parent[i].text+'" href="javascript:void(0);" onclick="gotoPage(this,\''+parent[i].attributes.src+'\',\''+parent[i].text+'\',\''+parent[i].attributes.funb+'\',\''+parent[i].id+'\')"  >'+parent[i].text+'</a></div>';
			}
			return text;
			}
			 }

			 
			 function gotoPage(obj,path,text,funb,appId){ 
			   $("div[name=thisdiv]").attr("class","txt002"); 
			   $(obj).parent().attr("class","txt002_hover");
				if ( null != path  && path != '') {
					var href;
					if (/^\//.test(path)) {/*以"/"符号开头的,说明是本项目地址*/
						if(path.indexOf("?",0) != -1) {
							href = path.substr(1)+"&funb="+funb+"&appId="+appId+"&ticket="+ticketNew;
						} else {
							href = path.substr(1)+"?funb="+funb+"&appId="+appId+"&ticket="+ticketNew;
						}
					} else {
						if(path.indexOf("?",0) != -1) {
							href = path+"&funb="+funb+"&appId="+appId+"&ticket="+ticketNew;
						} else {
							href = path+"?funb="+funb+"&appId="+appId+"&ticket="+ticketNew;
						}
					}
					addTabFun({
						src : href,
						title :text
					});
				} else {
					
				}
			}
			 

</script>
	

	<div class="easyui-panel" fit="true" border="false">
	  

	<div id="menu" class="easyui-accordion" region="center">
	</div>

		<div data-options="region:'center'">  
         	</div>  


</div>





