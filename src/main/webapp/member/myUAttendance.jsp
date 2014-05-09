<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
  <head>
  	
    <jsp:include page="../js/inc.jsp"></jsp:include>
    <jsp:include page="../js/address.jsp"></jsp:include>
    <style type="text/css">
		.details a:hover {
			text-decoration: underline;
			color: red;
		}
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
		}
		.tableForm select{
			font-size: 13px;
			font-family: "微软雅黑";
			height: 26px;
		}
	</style>	
    <script type="text/javascript">
    		
	  		$(function(){
	  		sy.ns('attendance');
  			$('#backTime,#getTime,#registerTime').datetimebox({
  				showSeconds:false,
  				editable : false
  			   });
  			$('#get_member,#duty_member,#back_duty_member').combobox({
				url : sy.bp() + '/member.do?method=getMemberName',
        		editable : false,
        		valueField :'member_name',
        		textField : 'member_name',
        		onSelect:function(data){
        		}
			});
  			$('#atten_member1,#atten_member2').combobox({
				url : sy.bp() + '/member.do?method=getMemberName',
        		editable : false,
        		valueField :'member_no',
        		textField : 'member_name',
        		onSelect:function(data){
        		}
			});
  			
  			attendance.datagrid = $('#attendance_datagrid').datagrid({
	  				url : sy.bp()+'/attendance.do?method=get&no=modify1&get_member='+'<%=session.getAttribute("loginName")%>'+'&attendanceState=处理中',
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
						title : '状态',
						field : 'atten_state',
						width : 25,
						align : 'center'
					},{
						title : '类型',
						field : 'atten_type',
						width : 30,
						align : 'center'
					},{
						title : '',
						field : 'attendance_no',
						width : 35,
						align : 'center',
						formatter : function(value, rowData, rowIndex) {
							return '<span style="display:inline-block;vertical-align:middle;"></span><div class="details"><a style="" href="javascript:void(0);" onclick="attendance.edit(' + rowIndex + ');">修改</a>&nbsp;<a style="" href="javascript:void(0);" onclick="attendance.voidIt(' + rowIndex + ');">作废</a></div>';
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
	  			attendance.edit = function(index) {
	  				var rows = attendance.datagrid.datagrid('getRows');
					var row = rows[index];
					$.ajax({
						url : sy.bp()+'/attendance.do?method=getAttenById',
						data : {
							attendance_no : row.attendance_no
						},
						dataType : 'json',
						cache : false,
						success : function(response) {
							attendance.editForm.form('load',response);
						}
					});  
					attendance.dialog.dialog('open');
	  			};
	  			attendance.dialog = $('#attendance_editDialog').dialog({
				title : '查看出勤单',
				iconCls : 'icon-search',
				modal:true,
				width : 640,
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					plain : true,
					handler : function(){
						attendance.editForm.submit();
					}
				}]
			}).dialog('close');
			attendance.editForm = $('#attendance_editForm').form({
				url : sy.bp()+'/attendance.do?method=edit', 
				success : function(r){
					var d = $.parseJSON(r);
					if(d.success){
						attendance.dialog.dialog('close');
						attendance.datagrid.datagrid('load');
					}
					$.messager.show({
							msg : d.msg,
							title : '提示'
						});
					}
				}); 
  			});
  	</script>
  </head>
  <body style="background-color: #F5F5F5;font-size: 14px;" class="easyui-layout" fit="true" border="false" onload="setup();">
    <div region="center" title="" border="false" style="background-color: #ebf3fb;border-radius:5px;padding-bottom: 30px;">
    	<div align=left style="font-size: 18px;color: #eb1446;line-height: 30px;"><b>&nbsp;&nbsp;&gt;&gt;我的待处理出勤单</b></div>
  		<form id="attendance_datagrid"></form>
  	</div>
  	<div id="attendance_editDialog" style="height: 500px;width: 500px;font-size: 14px;margin-bottom: -20px;">  
    		<form id="attendance_editForm" name="attendance_editForm" method="post">
    			<table class="tableForm datagrid-toolbar">
					<tr>
					    <td style="display: none"><input name="attendance_no"/></td>
						<td align="center">客户姓名</td>
						<td class="theader"><input name="client_name" style="width: 184px;" /></td>
						<td align="center">联系方式</td>
						<td><input name="client_phone" style="width: 184px;" /></td>
					</tr>
			    	<tr>
						<td align="center">地址</td>
						<td colspan="3"> 
								<input name="client_address_a" type="text" style="width: 80px;margin-bottom: -10px;"/>
								村/区
								<input name="client_address_b" type="text" style="width: 80px;margin-bottom: -10px;"/>
								栋/舍
								<input name="client_address_c" type="text" style="width: 80px;margin-bottom: -10px;"/>
								房</td>
					</tr>
					<tr>
						<td align="center">报单时间</td>
							<td class="calendarBox">
								<input disabled="disabled" id="registerTime" type="text" name="register_time"/>
							</td>
						<td align="center">值班队员</td>
						<td  class="theader calendarBox"><input disabled="disabled" name="duty_member" id="duty_member"  type="text" style="width: 184px"/></td>
					</tr>
					<tr>
						<td align="center">电脑类型</td>
						<td class="theader">
								<select name="computer_type" style="width: 184px;">
										<option value="笔记本">笔记本</option>
										<option value="台式机">台式机</option>
										<option value="U盘">U盘</option>
										<option value="其他">其他</option>
								</select>
						</td>
						<td align="center">系统类型</td>
						<td class="theader">
							<select name="system_type" style="width: 184px;">
										<option value="XP">XP</option>
										<option value="Win7">Win7</option>
										<option value="Linux">Linux</option>
										<option value="多系统">多系统</option>
										<option value="其他">其他</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="center">备注</td>
						<td colspan="3"><input name="atten_remark" style="width: 284px;" /></td>
					</tr>
					<tr><td colspan="4" style="height: 10px;background-color: green;"></td></tr>
					<tr>
						<td align="center">拿单时间</td>
						<td><input readonly="readonly" name="get_time" id="getTime" style="width: 184px;" /></td>
						<td align="center">拿单队员</td>
						<td  class="theader calendarBox"><input name="get_member" id="get_member"  type="text" style="width: 184px"/></td>
						
						
					</tr>
					<tr>
						<td align="center">还单时间</td>
							<td class="calendarBox">
								<input disabled="disabled" id="backTime" type="text" name="back_time" style="width: 184px;"/>
							</td>
						<td align="center">还单值班队员</td>
						<td class="theader calendarBox"><input disabled="disabled" name="back_duty_member" id="back_duty_member"  type="text" style="width: 184px"/></td>
					    
					</tr>
					<tr>
						<td align="center">出勤单状态</td>
						<td>
							<input readonly="readonly" name="atten_state" style="width: 184px;" />
							
						</td>
					    <td align="center">出勤队员</td>
						<td  class="theader calendarBox">
							<input name="atten_member1" id="atten_member1"  type="text" style="width: 92px"/>
						    <input name="atten_member2" id="atten_member2"  type="text" style="width: 92px"/>
						</td>
					</tr>
					
					<tr>
						<td align="center">问题描述</td>
						<td><textarea name="problem_describe" rows="4" cols="100" style="width: 184px;margin-bottom: -3px;"></textarea></td>
					    <td align="center">解决方法</td>
						<td><textarea name="solution" rows="4" cols="100" style="width: 184px;margin-bottom: 5px;"></textarea></td>
					</tr>
				</table>
    		</form>
		</div>  
  </body>
</html>
