<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="../js/inc.jsp"></jsp:include>
    <script type="text/javascript">
	$(function(){
		sy.ns('extrawork');
		$('#startTime').datebox({
			showSeconds:false,
			editable : false
		   });
		
		$('#endTime').datebox({
			showSeconds:false,
			editable : false
		   });
		
		extrawork.editRow = undefined;
		extrawork.queryExtraWorkForm = $('#queryExtraWorkForm').form();
		//查看所有加班条
			extrawork.all_datagrid = $('#extrawork_datagrid').datagrid({
  				url : sy.bp()+'/extraWork.do?method=get&no=modify&extra_no='+<%=session.getAttribute("loginNo") %>,
  				pagination : true,
				pageSize : 10,
				pageList : [10,20,30],
				fit : true,
				fitColumns : true,
				nowrap : false,
				border : false,
				idField : 'query_id',
				sortName : 'extra_date1',
				sortOrder : 'desc',
				columns : [[{
					title : '开始时间',
					field : 'extra_date1',
					width : 80
				},{
					title : '结束时间',
					field : 'extra_date2',
					width : 80
				},{
					title : '时长',
					field : 'extra_hours',
					width : 30
				},{
					title : '工作内容',
					field : 'extra_content',
					width : 160
				}]]
  			});
			
			extrawork.searchExtrawork = function() {
				extrawork.all_datagrid.datagrid('load',sy.serializeObject(extrawork.queryExtraWorkForm));
			};
		});
	
	</script>
</head>
  
  <body style="background-color: #F5F5F5;font-size: 14px;" class="easyui-layout" fit="true" border="false">
  		<!--<div region="north" title="" style="height: 40px;overflow: hidden;" border="false">
        <form id="queryExtraWorkForm" name="queryExtraWorkForm" action="" method="post">
       
         <table>
           <tr>
             <td>开始日期：</td>
             <td class="calendarBox"><input id="startTime" name="startDate" type="text" style="width: 210px; " /></td>
            <td>&nbsp;&nbsp;</td> <td>结束日期：</td>
             <td class="calendarBox"><input id="endTime" name="endDate" type="text" style="width: 210px; " /></td>
             <td>&nbsp;&nbsp;</td><td><input type="button" onclick="extrawork.searchExtrawork();" value="查询" class="super-emphasize" style="padding: 2px 0 2px 0;"/></td>
	       </tr>
          </table>
       </form></div> -->
       
       	<div region="center" title="" border="false" style="background-color: #ebf3fb;border-radius:5px;padding-bottom: 30px;">
       		<div align=left style="font-size: 18px;color: #eb1446;line-height: 30px;"><b>&nbsp;&nbsp;&gt;&gt;我的加班表</b></div>
  			<form id="extrawork_datagrid"></form>
  		</div> 
  </body>
</html>
