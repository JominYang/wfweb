<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head>
  	
    <jsp:include page="../../js/inc.jsp"></jsp:include>
    <style type="text/css">
		.back a:hover {
			text-decoration: underline;
			color: red;
		}
	</style>	
    <script type="text/javascript">
    		
	  		$(function(){
	  			sy.ns('attendance');
	  			$('#back_time').datetimebox({
				showSeconds : false,
				editable : false,
				onSelect : function(date) {
				}
			});
				$('#atten_member1,#atten_member2').combobox({
				url : sy.bp() + '/memberX.do?method=getMembersName',
				editable : false,
				valueField : 'member_no',
				textField : 'member_name',
				onSelect : function() {
				}
			});
			$('#back_duty_member').combobox({
				url : sy.bp() + '/memberX.do?method=getMembersName',
				editable : false,
				valueField : 'member_name',
				textField : 'member_name',
				onSelect : function() {
				}
			});
  			
  			attendance.datagrid = $('#attendance_datagrid').datagrid({
	  				url : sy.bp()+'/attendance.do?method=get&no=modify1&attendanceState=处理中',
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
						title : '类型',
						field : 'atten_type',
						width : 25,
						align : 'center'
					},{
						title : '拿单队员',
						field : 'get_member',
						width : 30,
						align : 'center'
					},{
						title : '',
						field : 'attendance_no',
						width : 35,
						align : 'center',
						formatter : function(value, rowData, rowIndex) {
							return '<span style="display:inline-block;vertical-align:middle;"></span><div class="back"><a style="" href="javascript:void(0);" onclick="attendance.back(' + rowIndex + ');">还单</a>&nbsp;<a style="" href="javascript:void(0);" onclick="attendance.voidIt(' + rowIndex + ');">作废</a></div>';
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
	  			attendance.back = function(index) {
	  				var rows = attendance.datagrid.datagrid('getRows');
					var row = rows[index];
					attendance.attendance_no = row.attendance_no;
					attendance.dialogForm = $('#dialogForm').form({
					url : sy.bp()+'/attendance.do?method=back&attendance_no='+attendance.attendance_no,
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
							title : '还单登记',
							iconCls : 'icon-edit',
							modal : true,
							closed : true,
							width : 450,
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
    	<div align=left style="font-size: 18px;color: #eb1446;line-height: 30px;"><b>&nbsp;&nbsp;&gt;&gt;处理中出勤单</b></div>
  		<form id="attendance_datagrid"></form>
  	</div>
  	<div id="dialog">
  		<form id="dialogForm" method="post">
			<table>
				<tr>
					<td align="right">出勤队员(1)</td>
					<td><input name="atten_member1" id="atten_member1" type="text" style="width: 110px" /></td>
					<td align="right">出勤队员(2)</td>
					<td><input name="atten_member2" id="atten_member2" type="text" style="width: 110px" /></td>
				</tr>
				<tr>
					<td align="right">还单时间  </td>
					<td class="calendarBox"><input id="back_time" name="back_time" type="text" style="width: 110px; " /></td>
					<td align="right">值班队员  </td>
					<td><input id="back_duty_member" name="back_duty_member" type="text" style="width: 110px; " /></td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<textarea name="solution" rows="5" cols="45" onfocus="if(value=='请填写解决方法。'){value='';}" onblur="if(value==''){value='请填写解决方法。';}">请填写解决方法。</textarea>
					</td>
				</tr>
			</table>
  		</form>
  	</div>
  </body>
</html>
