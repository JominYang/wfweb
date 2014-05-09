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
  		sy.ns('member');
  		$(function(){
  			member.editRow = undefined;
  			member.queryMemberForm = $('#queryMemberForm').form();
  			
  		//查看所有队员
  			member.all_datagrid = $('#memberall_datagrid').datagrid({
	  				url : sy.bp()+'/member.do?method=getMember',
	  				pagination : true,
					pageSize : 10,
					pageList : [10,20,30,40,50],
					fit : true,
					fitColumns : true,
					nowrap : false,
					border : false,
					idField : 'member_no',
					sortName : 'member_no',
					sortOrder : 'asc',
					columns : [[{
						title : '工号',
						field : 'member_no',
						width : 50,
						align : 'center'
					},{
						title : '姓名',
						field : 'member_name',
						width : 50,
						align : 'center'
					},{
						title : '专业班级',
						field : 'member_class',
						width : 100,
						align : 'center'
					},{
						title : '联系方式',
						field : 'member_phone',
						width : 100,
						align : 'center'
					},{
						title : '队员宿舍',
						field : 'member_room',
						width : 100,
						align : 'center'
					},{
						title : '队员状态',
						field : 'member_state',
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
  								member.edit(2);
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
  		
  			//选择队员角色下拉选
  			$('#memberStateSelect').combobox({
  				editable : false,
  				onSelect:function(data) {
  					member.memberState = encodeURIComponent(data.value);
  					if(member.memberState!=null && member.memberState!="null") {
  					member.selected_datagrid = $('#member_datagrid').datagrid({
  		  				url : sy.bp()+'/member.do?method=getMember&memberState=' + member.memberState,
  		  				pagination : true,
  						pageSize : 10,
  						pageList : [10,20,30,40,50],
  						fit : true,
  						fitColumns : true,
  						nowrap : false,
  						border : false,
  						idField : 'member_no',
  						sortName : 'member_no',
  						sortOrder : 'asc',
  						columns : [[{
  							title : '工号',
  							field : 'member_no',
  							width : 50,
  							align : 'center'
  						},{
  							title : '姓名',
  							field : 'member_name',
  							width : 50,
  							align : 'center'
  						},{
  							title : '专业班级',
  							field : 'member_class',
  							width : 100,
  							align : 'center'
  						},{
  							title : '联系方式',
  							field : 'member_phone',
  							width : 100,
  							align : 'center'
  						},{
  							title : '队员宿舍',
  							field : 'member_room',
  							width : 100,
  							align : 'center'
  						},{
  							title : '邮箱地址',
  							field : 'member_email',
  							width : 100,
  							align : 'center'
  						}]],
  						toolbar : ['-',{
  							text : '删除',
  							iconCls : 'icon-remove',
  							handler : function(){
  								var rows = member.selected_datagrid.datagrid('getSelections');
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
  													member.selected_datagrid.datagrid('load'),
  													member.selected_datagrid.datagrid('unselectAll'),
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
  								member.edit(1);
  							}
  						},'-',{
  							text : '取消',
  							iconCls : "icon-undo",
  							handler : function(){
  								editRow = undefined;
  								member.selected_datagrid.datagrid('rejectChanges');
  								member.selected_datagrid.datagrid('unselectAll');
  							}
  						},'-']
  		  			});}else{
  		  				$.messager.alert('提示','请选择队员角色撒！','warning');
  		  			}
  				}
  			});
  			
  			
  			//搜索
  			member.searchMember = function() {
  				member.all_datagrid.datagrid('load',sy.serializeObject(member.queryMemberForm));
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
  			
  			member.edit = function(obj){
  				var rows = null;
  				if(obj == 1){
  					rows = member.selected_datagrid.datagrid('getSelections');
  				}
  				else if(obj == 2){
  					rows = member.all_datagrid.datagrid('getSelections');
  				}
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
  	<div region="north" title="" split="false" style="height: 80px;overflow: hidden;" border="false">
       <form id="queryMemberForm" name="queryMemberForm" action="" method="POST">
				<table class="tableForm" cellpadding="5px" align="center">
					<tr>
						<td colspan="4" align="center"><b>队员信息管理</b></td>
					</tr>
					<tr>
    					<td align="left" colspan="2"><b>队员角色</b>&emsp;（正式队员、临时队员、退队队员）</td>
    					<td align="left" colspan="2">
    						<select id="memberStateSelect" style="width:125px;">
    							<option value="null">请选择...</option>
    							<option value="在队">正式队员</option>
    							<option value="退队">退队队员</option>
    							<option value="临时">临时队员</option>
    						</select></td>
    				</tr>
				</table>
			</form>
  	</div>
  	<div region="center" title="" border="false" fit="false" style="padding-bottom: 10px;">
  		<form id="member_datagrid"></form>
  		<form id="memberall_datagrid"></form>
  	</div>
  	
  	<div id="member_editDialog" >  
    		<form id="member_editForm" method="post">
    			<table class="tableForm datagrid-toolbar">
					<tr>
						<td align="right">工号</td>
						<td><input name="member_no" readonly="readonly" style="width: 200px;" /></td>
						<td align="right">家乡</td>
						<td><input name="member_home" style="width: 200px;" /></td>
					</tr>
					<tr>
						<td align="right">姓名</td>
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
  </body>
</html>
