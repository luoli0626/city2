<style type="text/css">
table.tableForm
{
border-collapse: separate;
border-spacing: 8px 
}
table.tableForm th
{ text-align:right
  
}

</style>
<table class="tableForm">
	<tbody >
			<tr>
				<th>账号：</th>
				<td>
					 ${teacher.loginname}
				</td>
			</tr>
			<tr>
				<th>姓名：</th>
				<td>
					 ${teacher.name}
				</td>
			</tr>
			 <tr>
				<th>性别：</th>
				<td>
				   <#if teacher.sex=="1" >
					 男
					<#else>
					 女
					</#if>
				</td>
			</tr>
			<tr>
				<th>生日：</th>
				<td>
			   		 ${teacher.birth}
				</td>
			</tr>
			<tr>
				<th>家乡：</th>
				<td COLSPAN=3>
			   	 ${teacher.hometown}
				</td>
			</tr>	
			<tr>
				<th>毕业学校：</th>
				<td>
			   		 ${teacher.gradusch}
				</td>
			</tr>
			<tr>
				<th>专业：</th>
				<td>
			   		 ${teacher.gradumajor}
				</td>
			</tr>
								
		</tbody>
</table>


 
 
 
 
 
 