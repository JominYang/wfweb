<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
  <head>
	<jsp:include page="../js/inc.jsp"></jsp:include>	
	<style type="text/css">
		.tableForm td {
		background: #E0ECFF;
		font-family: "微软雅黑";
		font-size: 12px;
		cellpadding: 5px;
		width: 200PX;
		}
	</style>
  	<script type="text/javascript">

  		sy.ns('attendance');
  		$(function(){
  			attendance.editRow = undefined;
  			attendance.queryAttendanceForm = $('#queryAttendanceForm').form();
  			
  			//查看所有未处理出勤单
  			attendance.all_datagrid = $('#attendance_datagrid').datagrid({
	  				url : sy.bp()+'/attendance.do?method=getAttendance',
	  				pagination : true,
					pageSize : 10,
					pageList : [10,20,30,40,50],
					fit : true,
					fitColumns : true,
					nowrap : false,
					border : false,
					idField : 'ATTENDANCE_NO',
					sortName : 'ATTENDANCE_NO',
					sortOrder : 'asc',
					columns : [[{
						title : '客户姓名',
						field : 'client_name',
						width : 50,
						align : 'center'
					},{
						title : '联系方式',
						field : 'client_phone',
						width : 50,
						align : 'center'
					},{
						title : '报单日期',
						field : 'register_date',
						width : 100,
						align : 'center'
					},{
						title : '报单时间',
						field : 'register_time',
						width : 100,
						align : 'center'
					},{
						title : '问题描述',
						field : 'problem_describe',
						width : 100,
						align : 'center'
					},{
						title : '值班队员',
						field : 'duty_member_name',
						width : 100,
						align : 'center'
					}]],
  						toolbar : ['-',{
  							text : '删除',
  							iconCls : 'icon-remove',
  							handler : function(){
  								var rows = member.all_datagrid.datagrid('getSelections');
  								if(rows.length > 0) {
  									$.messager.confirm('请确认','您确定要删除所选队员吗？',function(b){
  										if(b){
  											var nos = [];
  											for(var i=0;i<rows.length;i++){
  												nos.push(rows[i].member_no);
  											}
  											$.ajax({
  												url : sy.bp()+'/member.do?method=delMember',
  												data : {
  													nos : nos.join(',')
  												},
  												dataType : 'json',
  												success : function(r){
  													member.all_datagrid.datagrid('load'),
  													member.all_datagrid.datagrid('unselectAll'),
  													$.messager.show({
  														title : '提示',
  														msg : '删除队员成功！'
  													});
  												}
  											});
  										}
  									});
  								}else {
  									$.messager.alert('提示','请选择要删除的队员！','warning');
  								}
  							}
  						},'-',{
  							text : '修改',
  							iconCls : 'icon-edit',
  							handler : function(){
  								member.edit();
  							}
  						},'-',{
  							text : '取消',
  							iconCls : "icon-undo",
  							handler : function(){
  								editRow = undefined;
  								member.all_datagrid.datagrid('rejectChanges');
  								member.all_datagrid.datagrid('unselectAll');
  							}
  						},'-']
	  			});
  			//搜索
  			attendance.searchAttendance = function() {
  				attendance.all_datagrid.datagrid('load',sy.serializeObject(attendance.queryAttendanceForm));
  			};
  			//修改队员dialog
  			member.editDialog = $('#member_editDialog').dialog({
				title : '修改队员',
				iconCls : 'icon-edit',
				modal:true,
				closed : true,
				width : 640,
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					plain : true,
					handler : function(){
						member.editForm.submit();
					}
				},{
					text : '重写',
					iconCls : 'icon-undo',
					plain : true,
					handler : function(){
						member.editForm.find('input').val('');
					}
				}],
				onOpen : function() {
					setTimeout(function() {
						member.editForm.find('input[name=member_no]').focus();
					}, 1);
				}
			}).dialog('close');
  			//修改队员表单
  			member.editForm = $('#member_editForm').form({
				url : sy.bp()+'/memberX.do?method=edit', 
				success : function(r){
					var d = $.parseJSON(r);
					if(d.success){
						member.editDialog.dialog('close');
						member.all_datagrid.datagrid('load');
					}else{
						member.editForm.find('input[name=member_no]').val('');
						member.editForm.find('input[name=member_no]').focus();
					}
					$.messager.show({
							msg : d.msg,
							title : '提示'
						});
					}
				});
  			
  			member.edit = function(){
  				var rows = member.all_datagrid.datagrid('getSelections');
				if(rows.length == 1) {
					$.messager.progress({
						text : 'progressing...',
						intervar : 100
					});
					$.ajax({
						url : sy.bp()+'/memberX.do?method=getSinger',
						data : {
							member_no : rows[0].member_no
						},
						dataType : 'json',
						cache : false,
						success : function(r) {
							member.editForm.form('load',r);
							member.editDialog.dialog('open');
							$.messager.progress('close');
						}
					});
				} else {
					$.messager.alert('提示', '请选择一项要编辑的记录！', 'error');
				}
  			};
  		});
  	
  	</script>
  </head>
  
  <body style="background-color: #F5F5F5" class="easyui-layout" fit="true" border="false">
  	<div region="north" title="" split="true" style="height: 110px;overflow: hidden;" border="false">
       <form id="queryAttendanceForm" name="queryAttendanceForm" action="" method="POST">
				<table class="tableForm" cellpadding="5px" align="center">
					<tr>
						<td colspan="4" align="center"><b>还单管理</b></td>
					</tr>
    				<tr>
    					<td align="left"><b>根据队员姓名或工号查询出勤单</b></td>
    					<td align="left">姓名<input name="member_name" type="text" style="width:100px;"/><b style="float: right;">或者</b></td>
    					<td align="left">工号<input name="member_no" type="text" style="width:100px;"/>
    					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="attendance.searchAttendance();" style="float: right" plain="true">检索</a></td>
    				</tr>
				</table>
			</form>
  	</div>
  	<div region="center" title="" border="false">
  		<form id="attendance_datagrid"></form>
  	</div>
  <!-- 
  	<div id="member_editDialog" >  
    		<form id="member_editForm" method="post">
    			<table class="tableForm datagrid-toolbar">
					<tr>
						<td align="right">客户姓名</td>
						<td><input name="" readonly="readonly" style="width: 200px;" /></td>
						<td align="right">联系方式</td>
						<td><input name="" style="width: 200px;" /></td>
					</tr>
					<tr>
						<td align="right">地址</td>
						<td><input name="member_name" style="width: 200px;" /></td>
						<td align="right">秋秋</td>
						<td><input name="member_qq" style="width: 200px;" /></td>
					</tr>
					<tr>
						<td align="right">密码</td>
						<td><input name="member_psw" style="width: 200px;" /></td>
						<td align="right">生日</td>
						<td><input name="member_birth" style="width: 200px;" /></td>
					</tr>
					<tr>
						<td align="right">班级</td>
						<td><input name="member_class" style="width: 200px;" /></td>
						<td align="right">状态</td>
						<td><select name="member_state" style="width: 200px;">
							<option value="在队">在队</option>
							<option value="退队">退队</option>
							<option value="临时">临时</option>
						</select></td>
					</tr>
					<tr>
						<td align="right">宿舍</td>
						<td><input name="member_room" style="width: 200px;" /></td>
					</tr>
					<tr>
						<td align="right">手机</td>
						<td><input name="member_phone" style="width: 200px;" /></td>
					</tr>
					<tr>
						<td align="right">邮箱</td>
						<td><input name="member_email" style="width: 200px;" /></td>
					</tr>
				</table>
    		</form>
		</div> 
	 -->
       
  </body>
</html>
