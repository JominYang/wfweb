<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
  <head>
  	
    <jsp:include page="../js/inc.jsp"></jsp:include>
    <style type="text/css">
		.details a:hover {
			text-decoration: underline;
			color: red;
		}
	</style>
	<style type="text/css">
		.tableForm td {
		background: #E0ECFF;
		font-family: "微软雅黑";
		font-size: 13px;
		cellpadding: 5px;
		width: 200PX;
		height: 36px;
		}
		.tableForm input{
			font-size: 13px;
			font-family: "微软雅黑";
			height: 26px;
			width: 184px;
		}
		.tableForm select{
			font-size: 13px;
			font-family: "微软雅黑";
		}
		.tableForm textarea{
			font-size: 13px;
			font-family: "微软雅黑";
		}
		
	</style>	
    <script type="text/javascript">
    		
	  		$(function(){
	  		sy.ns('attendance');
  			attendance.datagrid = $('#attendance_datagrid').datagrid({
	  				url : sy.bp()+'/attendance.do?method=get&no=check1&attendanceState=临时',
	  				pagination : true,
					pageSize : 10,
					pageList : [10,20,30,40,50],
					fit : true,
					fitColumns : true,
					nowrap : false,
					border : false,
					idField : 'attendance_no',
					sortName : 'register_time',
					sortOrder : 'desc',
					columns : [[{
						title : '报单时间',
						field : 'register_time',
						width : 60,
						align : 'center'
					},{
						title : '客户姓名',
						field : 'client_name',
						width : 30,
						align : 'center'
					},{
						title : '联系方式',
						field : 'client_phone',
						width : 45,
						align : 'center'
					},{
						title : '地址',
						field : 'address',
						width : 60,
						align : 'center'
					},{
						title : '问题描述',
						field : 'problem_describe',
						width : 100,
						align : 'center'
					},{
						title : '',
						field : 'attendance_no',
						width : 35,
						align : 'center',
						formatter : function(value, rowData, rowIndex) {
							return '<span style="display:inline-block;vertical-align:middle;"></span><div class="details"><a style="" href="javascript:void(0);" onclick="attendance.goToDeal(' + rowIndex + ');">拿单</a></div>';
						}
				}]]
	  			});
  			
	  			attendance.goToDeal = function(index) {
	  				var rows = attendance.datagrid.datagrid('getRows');
					var row = rows[index];
					attendance.attendance_no = row.attendance_no;
					
					$.messager.confirm('请确认','确定要去处理该出勤单吗？',function(b){
						if(b){
							$.ajax({
								url : sy.bp()+'/attendance.do?method=goToDeal&net=net&get_member=<%=session.getAttribute("loginName")%>',
								data : {
									attendance_no : attendance.attendance_no
								},
								dataType : 'json',
								cache : false,
								success : function(response) {
									attendance.datagrid.datagrid('load');
									$.messager.show({
										msg : response.msg,
										title : '提示'
									});
								}
							});
						}
					});
					
	  			};
  			});
  	</script>
  </head>
  <body style="background-color: #F5F5F5;font-size: 14px;" class="easyui-layout" fit="true" border="false">
    <div region="center" title="" border="false" style="background-color: #ebf3fb;border-radius:5px;padding-bottom: 30px;">
    	<div align=left style="font-size: 18px;color: #eb1446;line-height: 30px;"><b>&nbsp;&nbsp;&gt;&gt;网上报单</b></div>
  		<form id="attendance_datagrid"></form>
  	</div>
  </body>
</html>
