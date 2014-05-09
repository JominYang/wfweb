<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <link type="text/css" rel="stylesheet" href="../css/layout.css" />
	<jsp:include page="../js/inc.jsp"></jsp:include>	
	<jsp:include page="../js/address.jsp"></jsp:include>
    
    <title>checkAttendance</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<script type="text/javascript">
	
	$(function(){
		sy.ns('attendance');
		attendance.editRow = undefined;
		$('#registerTime').datetimebox({
				showSeconds:false,
				editable : false
			   });
		attendance.duty_member = $('#duty_member').combobox({
			url : sy.bp() + '/member.do?method=getMemberName',
    		editable : false,
    		valueField :'member_name',
    		textField : 'member_name',
    		onSelect:function(data){
    		}
		});
		attendance.checkAttenForm = $('#checkAttenForm').form();
		//查看所有临时出勤单
			attendance.all_datagrid = $('#attendance_datagrid').datagrid({
  				url : sy.bp()+'/attendance.do?method=get&no=check',//sy.bp():/wfcsu
  				pagination : true,//分页工具栏
				pageSize : 15,
				pageList : [15,30],
				fit : true,
				fitColumns : true,
				nowrap : false,
				border : false,
				idField : 'attendance_no',
				sortName : 'attendance_no',
				sortOrder : 'asc',
				columns : [[{
					title : '客户姓名',
					field : 'client_name',
					width : 50
				},{
					title : '联系方式',
					field : 'client_phone',
					width : 70
				},{
					title : '地址',
					field : 'address',
					width : 100
				},{
					title : '电脑类型',
					field : 'computer_type',
					width : 40
				},{
					title : '系统类型',
					field : 'system_type',
					width : 40,
					align : 'center'
				},{
					title : '问题描述',
					field : 'problem_describe',
					width : 100,
					align : 'center'
				},{
					title : '备注',
					field : 'atten_remark',
					width : 80,
					align : 'center'
				}]],
					toolbar : ['-',{
							text : '删除',
							iconCls : 'icon-remove',//图标
							handler : function(){
  								var rows = attendance.all_datagrid.datagrid('getSelections');
  								if(rows.length > 0) {
  									$.messager.confirm('请确认','您确定要删除所选出勤单吗？',function(b){
  										if(b){
  											var nos = [];
  											for(var i=0;i<rows.length;i++){
  												nos.push(rows[i].attendance_no);
  											}
  											$.ajax({
  												url : sy.bp()+'/attendance.do?method=del',
  												data : {
  													nos : nos.join(',')
  												},
  												dataType : 'json',
  												success : function(r){
  													attendance.all_datagrid.datagrid('load'),
  													attendance.all_datagrid.datagrid('unselectAll'),
  													$.messager.show({
  														title : '提示',
  														msg : '删除出勤单成功！'
  													});
  												}
  											});
  										}
  									});
  								}else {
  									$.messager.alert('提示','请选择要删除的出勤单！','warning');
  								}
  							}
						},'-',{
							text : '修改',
							iconCls : 'icon-edit',
							handler : function(){
								attendance.edit();
							}
						},'-',{
							text : '取消',
							iconCls : "icon-undo",
							handler : function(){
								editRow = undefined;
								attendance.all_datagrid.datagrid('rejectChanges');
								attendance.all_datagrid.datagrid('unselectAll');
							}
						},'-',{
							text : '转为正式出勤单',
							iconCls : "icon-ok",
							handler : function(){
								attendance.check();
							}
						},'-']
  			});
			
			attendance.searchExtrawork = function() {
				attendance.all_datagrid.datagrid('load',sy.serializeObject(extrawork.checkAttenForm));
			};
			//修改加班dialog
  			attendance.editDialog = $('#attendance_editDialog').dialog({
				title : '修改加班',
				iconCls : 'icon-edit',
				modal:true,
				closed : true,
				width : 640,
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					plain : true,
					handler : function(){
						attendance.editForm.submit();
					}
				},{
					text : '重写',
					iconCls : 'icon-undo',
					plain : true,
					handler : function(){
						attendance.editForm.find('input').val('');
					}
				}],
				onOpen : function() {
					setTimeout(function() {
						attendance.editForm.find('input[name=client_name]').focus();
					}, 1);
				}
			}).dialog('close');
  			//修改出勤单表单
  			attendance.editForm = $('#attenByClientForm').form({
				url : sy.bp()+'/attendance.do?method=editClient', 
				success : function(r){
					var d = $.parseJSON(r);
					if(d.success){
						attendance.editDialog.dialog('close');
						attendance.all_datagrid.datagrid('load');
					}else{
						attendance.editForm.find('input[name=client_name]').val('');
						attendance.editForm.find('input[name=client_name]').focus();
					}
					$.messager.show({
							msg : d.msg,
							title : '提示'
						});
					}
				});
  			
  			//得到出勤单数据
  			attendance.edit = function(){
  				var rows = attendance.all_datagrid.datagrid('getSelections');
				if(rows.length == 1) {
					$.messager.progress({
						text : 'progressing...',
						intervar : 100
					});
					$.ajax({
						url : sy.bp()+'/attendance.do?method=getSinger',
						data : {
							attendance_no : rows[0].attendance_no
						},
						dataType : 'json',
						cache : false,
						success : function(r) {
							attendance.editForm.form('load',r);
							attendance.editDialog.dialog('open');
							$.messager.progress('close');
						}
					});
				} else {
					$.messager.alert('提示', '请选择一项要编辑的记录！', 'error');
				}
  			};

		    //将出勤单转正
			attendance.check = function(){
  				var rows = attendance.all_datagrid.datagrid('getSelections');
				if(rows.length == 1) {
					$.ajax({
						url : sy.bp()+'/attendance.do?method=changeType',
						data : {
							attendance_no : rows[0].attendance_no
						},
						dataType : 'json',
						cache : false,
						success : function(r) {
							$.messager.show({
								msg : r.msg,
								title : '提示'
							});
							attendance.all_datagrid.datagrid('load');
						}
					});
				} else {
					$.messager.alert('提示', '请选择要编辑的记录！', 'error');
				}
  			};
		});
	
	</script>

  </head>
  
  <body style="background-color: #F5F5F5" class="easyui-layout" fit="true" border="false" onload="setup();">
   <div region="north" title="" split="true" style="height: 40px;overflow: hidden;" border="false">
      	客户网上报单的所有出勤单的类型均为“临时”
   </div>
       
        	<div region="center" title="" border="false">
  			<form id="attendance_datagrid"></form>
  		</div>
  		
  			<div id="attendance_editDialog" >  
    		<form id="attenByClientForm" name="attenByClientForm" method="post">
    			<table class="tableForm datagrid-toolbar">
					<tr>
					    <td style="display: none"><input name="attendance_no"/></td>
						<td align="right">客户姓名</td>
						<td class="theader"><input name="client_name" style="width: 184px;" /></td>
						<td align="right">联系方式</td>
						<td><input name="client_phone" style="width: 184px;" /></td>
					</tr>
				<tr>
						<td align="right">地址</td>
						<td colspan="3"> 
								<select name="client_address_a" id="s1"></select>
								村/区
								 <select  name="client_address_b" id="s2"></select>
								栋/舍
								<input name="client_address_c" type="text" style="width: 80px;height: 26px;padding-top: 4px;"/>
								房</td>
					</tr> 
					<tr>
						<td align="right">电脑类型</td>
						<td class="theader">
								<select name="computer_type" style="width: 184px;">
										<option>笔记本</option>
										<option>台式机</option>
										<option>U盘</option>
										<option>其他</option>
								</select>
						</td>
						<td align="right">系统类型</td>
						<td class="theader">
							<select name="system_type" style="width: 184px;">
										<option>XP</option>
										<option>Win7</option>
										<option>Linux</option>
										<option>多系统</option>
										<option>其他</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">报单时间</td>
						<td class="calendarBox">
								<input id="registerTime" name="register_time" type="text" name="register"/>
						</td>
						<td align="right">备注</td>
						<td><input name="atten_remark" style="width: 184px;" /></td>
					</tr>
					
					<tr>
						<td align="right">值班队员</td>
						<td  class="theader calendarBox"><input name="duty_member" id="duty_member"  type="text" style="width: 184px"/></td>
						<td align="right">问题描述</td>
						<td><textarea name="problem_describe" rows="4" cols="100" style="width: 184px;"></textarea></td>
					</tr>
				</table>
    		</form>
		</div>  
  </body>
</html>
