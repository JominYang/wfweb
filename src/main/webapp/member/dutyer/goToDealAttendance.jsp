<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head>
  	
    <jsp:include page="../../js/inc.jsp"></jsp:include>
    <style type="text/css">
		.goToDeal a:hover {
			text-decoration: underline;
			color: red;
		}
	</style>	
    <script type="text/javascript">
	  		$(function(){
	  			sy.ns('attendance');
	  			$('#get_time').datetimebox({
				showSeconds : false,
				editable : false,
				onSelect : function(date) {
				}
			});
				$('#get_member').combobox({
				url : sy.bp() + '/memberX.do?method=getMembersName',
				editable : false,
				valueField : 'member_name',
				textField : 'member_name',
				onSelect : function() {
				}
			});
  			
  			attendance.datagrid = $('#attendance_datagrid').datagrid({
	  				url : sy.bp()+'/attendance.do?method=get&no=modify&attendanceState=未处理',
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
						width : 70,
						align : 'center'
					},{
						title : '客户姓名',
						field : 'client_name',
						width : 40,
						align : 'center'
					},{
						title : '联系方式',
						field : 'client_phone',
						width : 50,
						align : 'center'
					},{
						title : '地址',
						field : 'address',
						width : 70,
						align : 'center'
					},{
						title : '问题描述',
						field : 'problem_describe',
						width : 100,
						align : 'center'
					},{
						title : '状态',
						field : 'atten_state',
						width : 30,
						align : 'center'
					},{
						title : '',
						field : 'attendance_no',
						width : 35,
						align : 'center',
						formatter : function(value, rowData, rowIndex) {
							return '<span style="display:inline-block;vertical-align:middle;"></span><div class="goToDeal"><a style="" href="javascript:void(0);" onclick="attendance.goToDeal(' + rowIndex + ');">拿单</a>&nbsp;<a style="" href="javascript:void(0);" onclick="attendance.voidIt(' + rowIndex + ');">作废</a></div>';
						}
				}]]
	  			});
	  			attendance.voidIt = function(index) {
	  				var rows = attendance.datagrid.datagrid('getRows');
					var row = rows[index];
	  				$.messager.confirm('请确认','确定要作废该出勤单吗？',function(b){
						if(b){
							$.ajax({
								url : sy.bp()+'/attendance.do?method=voidIt',
								data : {
									attendance_voidno : row.attendance_no
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
	  			attendance.goToDeal = function(index) {
	  				var rows = attendance.datagrid.datagrid('getRows');
					var row = rows[index];
					attendance.attendance_no = row.attendance_no;
					attendance.dialogForm = $('#dialogForm').form({
					url : sy.bp()+'/attendance.do?method=goToDeal&attendance_no='+attendance.attendance_no,
					success : function(r) {
						var d = $.parseJSON(r);
						if (d.success) {
							attendance.dialog.dialog('close');
							attendance.datagrid.datagrid('load');
						}
						$.messager.show({
							msg : d.msg,
							title : '提示'
						});
					}
				});
					attendance.dialog.dialog('open');
					
	  			};
	  			attendance.dialog = $('#dialog').dialog(
						{
							title : '拿单出勤',
							iconCls : 'icon-edit',
							modal : true,
							closed : true,
							width : 310,
							buttons : [
									{
										text : '确定',
										iconCls : 'icon-save',
										plain : true,
										handler : function() {
											attendance.dialogForm.submit();
										}
									}]
					}).dialog('close');
				
  			});
  	</script>
  </head>
  <body style="background-color: #F5F5F5;font-size: 14px;" class="easyui-layout" fit="true" border="false">
    <div region="center" title="" border="false" style="background-color: #ebf3fb;border-radius:5px;padding-bottom: 30px;">
    	<div align=left style="font-size: 18px;color: #eb1446;line-height: 30px;"><b>&nbsp;&nbsp;&gt;&gt;未处理出勤单</b></div>
  		<form id="attendance_datagrid"></form>
  	</div>
  	<div id="dialog">
  		<form id="dialogForm" method="post">
			<table>
				<tr>
					<td align="right">拿单队员</td>
					<td><input name="get_member" id="get_member" type="text" style="width: 210px" /></td>
				</tr>
				<tr>
					<td align="right">拿单时间</td>
					<td class="calendarBox"><input id="get_time" name="get_time" type="text" style="width: 210px; " /></td>
				</tr>
			</table>
  		</form>
  	</div>
  </body>
</html>
