<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head>
    <link type="text/css" rel="stylesheet" href="../css/layout.css" />
    <jsp:include page="../js/inc.jsp"></jsp:include>	
    <jsp:include page="../js/address.jsp"></jsp:include>
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
		}
		.tableForm select{
			font-size: 13px;
			font-family: "微软雅黑";
		}
	</style>
  	<script type="text/javascript">

  		
  		$(function(){
  			sy.ns('attendance');
  			$('#registerTime,#backTime,#getTime').datetimebox({
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
  			attendance.editRow = undefined;
  			attendance.queryAttendanceForm = $('#queryAttendanceForm').form();
  			
  		//根据出勤单状态查询
  			$('#attendanceStateSelect').combobox({
  				editable : false,
  				onSelect:function(data) {
  					attendance.attendanceState = encodeURIComponent(data.value);
  					if(attendance.attendanceState != null && attendance.attendanceState != "null"){
  					attendance.selected_datagrid = $('#attendance_datagrid').datagrid({
  		  			url : sy.bp()+'/attendance.do?method=get&no=modify&attendanceState=' + attendance.attendanceState,
  		  			pagination : true,
					pageSize : 10,
					pageList : [10,20,30,40,50],
					fit : true,
					fitColumns : true,
					nowrap : false,
					border : false,
					idField : 'attendance_no',
					sortName : 'attendance_no',
					sortOrder : 'desc',
					columns : [[{
						title : '客户姓名',
						field : 'client_name',
						width : 50,
						align : 'center'
					},{
						title : '联系方式',
						field : 'client_phone',
						width : 80,
						align : 'center'
					},{
						title : '地址',
						field : 'address',
						width : 80,
						align : 'center'
					},{
						title : '报单时间',
						field : 'register_time',
						width : 80,
						align : 'center'
					},{
						title : '问题描述',
						field : 'problem_describe',
						width : 100,
						align : 'center'
					},{
						title : '值班队员',
						field : 'duty_member',
						width : 50,
						align : 'center'
					},{
						title : '状态',
						field : 'atten_state',
						width : 50,
						align : 'center'
					}]],
  						toolbar : ['-',{
  							text : '删除',
  							iconCls : 'icon-remove',
  							handler : function(){
  								var rows = attendance.selected_datagrid.datagrid('getSelections');
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
  													attendance.selected_datagrid.datagrid('load'),
  													attendance.selected_datagrid.datagrid('unselectAll'),
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
  							text : '查看修改',
  							iconCls : 'icon-edit',
  							handler : function(){
  								attendance.edit(2);
  							}
  						},'-',{
  							text : '取消',
  							iconCls : "icon-undo",
  							handler : function(){
  								editRow = undefined;
  								attendance.selected_datagrid.datagrid('rejectChanges');
  								attendance.selected_datagrid.datagrid('unselectAll');
  							}
  						},'-']
  		  			});}else{
  		  				$.messager.alert('提示','请选择出勤单状态！！！','warning');
  		  			}
  				}
  			});
  			
  			//查看出勤单
  			attendance.all_datagrid = $('#attendanceall_datagrid').datagrid({
	  				url : sy.bp()+'/attendance.do?method=get&no=modify',
	  				pagination : true,
					pageSize : 10,
					pageList : [10,20,30,40,50],
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
						width : 50,
						align : 'center'
					},{
						title : '联系方式',
						field : 'client_phone',
						width : 80,
						align : 'center'
					},{
						title : '地址',
						field : 'address',
						width : 80,
						align : 'center'
					},{
						title : '报单时间',
						field : 'register_time',
						width : 80,
						align : 'center'
					},{
						title : '问题描述',
						field : 'problem_describe',
						width : 100,
						align : 'center'
					},{
						title : '值班队员',
						field : 'duty_member',
						width : 50,
						align : 'center'
					},{
						title : '状态',
						field : 'atten_state',
						width : 50,
						align : 'center'
					}]],
					toolbar : ['-',{
							text : '删除',
							iconCls : 'icon-remove',
							handler : function(){
								var rows = attendance.all_datagrid.datagrid('getSelections');
								console.info(rows);
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
  							text : '查看修改',
  							iconCls : 'icon-edit',
  							handler : function(){
  								attendance.edit(1);
  							}
  						},'-',{
  							text : '取消',
  							iconCls : "icon-undo",
  							handler : function(){
  								editRow = undefined;
  								attendance.all_datagrid.datagrid('rejectChanges');
  								attendance.all_datagrid.datagrid('unselectAll');
  							}
  						},'-']
	  			});
  			//搜索
  			attendance.searchAttendance = function() {
  				attendance.all_datagrid.datagrid('load',sy.serializeObject(attendance.queryAttendanceForm));
  				attendance.selected_datagrid.datagrid('load',sy.serializeObject(attendance.queryAttendanceForm));
  			};
  			//修改出勤单dialog
  			attendance.editDialog = $('#attendance_editDialog').dialog({
				title : '修改出勤单',
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
						attendance.editForm.find('input[name=attendance_no]').focus();
					}, 1);
				}
			}).dialog('close');
  			//修改出勤单表单
  			attendance.editForm = $('#attendance_editForm').form({
				url : sy.bp()+'/attendance.do?method=edit', 
				success : function(r){
					var d = $.parseJSON(r);
					if(d.success){
						attendance.editDialog.dialog('close');
						attendance.all_datagrid.datagrid('load');
					}else{
						attendance.editForm.find('input[name=member_no]').val('');
						attendance.editForm.find('input[name=member_no]').focus();
					}
					$.messager.show({
							msg : d.msg,
							title : '提示'
						});
					}
				});
  			
  			attendance.edit = function(obj){
  				var rows = null;
  				if(obj == 1 ){
  					 rows = attendance.all_datagrid.datagrid('getSelections');
  				}
  				else if(obj == 2){
  					rows = attendance.selected_datagrid.datagrid('getSelections');
  				}
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
  		});
  	
  	</script>
	<script type="text/javascript">
	  function queryWay(obj){
		  if(obj == '1'){
			  document.getElementById('queryWayTime').style.display = "none";
			  document.getElementById('queryWay').style.display = "block";
			  document.getElementById('queryWay').innerHTML = "<input name='client_name' type='text'>";
		  }
		  if(obj == '2'){
			  document.getElementById('queryWayTime').style.display = "none";
			  document.getElementById('queryWay').style.display = "block";
			  document.getElementById('queryWay').innerHTML = "<input name='get_member' type='text'>";
		  }
		  if(obj == '3'){
			  document.getElementById('queryWayTime').style.display = "none";
			  document.getElementById('queryWay').style.display = "block";
			  document.getElementById('queryWay').innerHTML = "<input name='atten_member' type='text'>";
		  }
		  if(obj == '4'){
			  document.getElementById('queryWay').style.display = "none";
			  document.getElementById('queryWayTime').style.display = "block";
		  }
		  
		  
	  }
	
	</script>

  </head>
  
  <body style="background-color: #F5F5F5" class="easyui-layout" fit="true" border="false" onload="setup();">
      <div region="north" title="" split="false" style="height: 26px;overflow: hidden;" border="false">
       	<form id="queryAttendanceForm" name="queryAttendanceForm" action="" method="POST">
       		<div class="calendarBox" style="float: left">
       			<select  id="attendanceStateSelect" style="width:200px;">
				                	<option value="null">请选择出勤单状态...</option>
						            <option value="未处理">未处理</option>
						            <option value="处理中">处理中</option>
						            <option value="已完成">已完成</option>
						            <option value="已作废">已作废</option>
				</select>
       		</div>
       		<div style="float: right">
       			<div style="float: left;font-size: 11px;">
       				<select>
	                    <option value="0">请选择检索方式</option>
	                    <option value="1" onclick="queryWay('1')">客户姓名 </option>
			            <option value="2" onclick="queryWay('2')">拿单队员姓名</option>
			            <option value="3" onclick="queryWay('3')">出勤队员姓名</option>
			            <option value="4" onclick="queryWay('4')">报单日期</option>
					</select>
       			</div>
				<div  style="float: left">
					<div id="queryWay">
       			    </div> 
   			    	<div id="queryWayTime" style="display: none">
   			    		  <input id='register_date' name='register_time' type='text' value=''/>
   			    	</div>
				</div>
				<div style="float: right"><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="attendance.searchAttendance();" style="float: right" plain="true">检索</a></div>
       		</div>
			</form>
  	</div>
  	<div region="center" title="" border="false">
  		<form id="attendance_datagrid"></form>
  		<form id="attendanceall_datagrid"></form>
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
								<input id="registerTime" type="text" name="register_time"/>
							</td>
						<td align="center">值班队员</td>
						<td  class="theader calendarBox"><input name="duty_member" id="duty_member"  type="text" style="width: 184px"/></td>
					</tr>
					<tr>
						<td align="center">电脑类型</td>
						<td class="theader">
								<select name="computer_type" style="width: 184px;">
										<option>笔记本</option>
										<option>台式机</option>
										<option>U盘</option>
										<option>其他</option>
								</select>
						</td>
						<td align="center">系统类型</td>
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
						<td align="center">备注</td>
						<td colspan="3"><input name="atten_remark" style="width: 284px;" /></td>
					</tr>
					<tr><td colspan="4" style="height: 10px;background-color: green;"></td></tr>
					<tr>
						<td align="center">拿单时间</td>
						<td class="calendarBox"><input name="get_time" id="getTime" style="width: 184px;" /></td>
						<td align="center">拿单队员</td>
						<td  class="theader calendarBox"><input name="get_member" id="get_member"  type="text" style="width: 184px"/></td>
						
						
					</tr>
					<tr>
						<td align="center">还单时间</td>
							<td class="calendarBox">
								<input id="backTime" type="text" name="back_time" style="width: 184px;"/>
							</td>
						<td align="center">还单值班队员</td>
						<td  class="theader calendarBox"><input name="back_duty_member" id="back_duty_member"  type="text" style="width: 184px"/></td>
					    
					</tr>
					<tr>
						<td align="center">出勤单状态</td>
						<td>
							<select name="atten_state" style="width: 184px;">
										<option value="未处理">未处理</option>
										<option value="处理中">处理中</option>
										<option value="已完成">已完成</option>
										<option value="已作废">已作废</option>
							</select>
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
