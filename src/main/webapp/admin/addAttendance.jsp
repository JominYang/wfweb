<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
	<head>
		<link type="text/css" rel="stylesheet" href="../css/layout.css" />
		<jsp:include page="../js/inc.jsp"></jsp:include>	
		<jsp:include page="../js/address.jsp"></jsp:include> 
		<title></title>
		<script type="text/javascript">
			sy.ns('attendance');
			$(function(){
				$('#registerTime').datetimebox({
					showSeconds:false,
					editable : false
				});
				
				attendance.attendanceDuty = $('#attendanceDuty').combobox({
					url : sy.bp() + '/member.do?method=getMemberName',
	        		editable : false,
	        		valueField :'member_name',
	        		textField : 'member_name',
	        		onSelect:function(data){
	        			
	        		}
				});
			});
			
			attendance.addAttendanceSubmit = function() {
						$.messager.confirm('请确认','信息无误，添加出勤单?',function(b){
							if(b) {
								$('#addAttendanceForm').form('submit',{
									url : sy.bp()+'/attendance.do?method=add',
									success : function(r) {
										var d = $.parseJSON(r);
										$.messager.show({
											title : '提示',
											msg : d.msg
										});
										if(d.success==true){
											$('#addAttendanceForm input:not(#submitButton)').val('');
											$('#addAttendanceForm').find('textarea').val('');
											$('#addAttendanceForm').find('input[name=atten_remark]').val('更多备注');
											$('#addAttendanceForm').find('select[name=client_address_a]').val('请选择');
											$('#addAttendanceForm').find('select[name=client_address_b]').val('请选择');
										}
									}
								});
							}
						});
			};
			
			
        </script>
	</head>

	<body style="margin: 0; padding: 0; background-color: #FFFFFF;" onload="setup();">
		<div align="center" class="MyForm" style="height: 100%">
			<div class="form-box">
				<h3>
					请填写出勤单信息>>
				</h3>
				<form name="addAttendanceForm" id="addAttendanceForm" action="" method="post">
					<div align="center">
						<table>
							<tr>
								<td>
									<table>
										<tr>
											<td class="theader">
												客户姓名
											</td>
											<td>
												<input type="text" name="client_name"/>
											</td>
										</tr>
										<tr>
											<td class="theader">
												报单时间
											</td>
											<td class="calendarBox">
												<input id="registerTime" type="text" name="register_time"/>
											</td>
										</tr>
									</table>
								</td>

								<td>
									<table>
										<tr>
											<td class="theader">
												联系方式
											</td>
											<td>
												<input type="text" name="client_phone"/>
											</td>
										</tr>
										<tr>
											<td class="theader">
												<input type="checkbox" value="拿到中心" name="toCenter"/>拿到中心
											</td>
											<td>
											    <input name="atten_remark" id="atten_remark" type="text" value="更多备注" onfocus="if(value=='更多备注'){value='';}" onblur="if(value==''){value='更多备注';}"/> 
											</td>
										</tr>

									</table>
								</td>
							</tr>

							<tr>
								<td class="theader" colspan="2">
									具体地址
									 <select name="client_address_a" id="s1"></select>
									村/区
									 <select  name="client_address_b" id="s2"></select>
									栋/舍
									<input name="client_address_c" type="text" style="width: 80px;height: 26px;padding-top: 4px;"/>
									房
								</td>
							</tr>

							<tr>
								<td class="theader">
									电脑
									<select name="computer_type">
										<option>笔记本</option>
										<option>台式机</option>
										<option>U盘</option>
										<option>其他</option>
									</select>
									系统:
									<select name="system_type">
										<option>XP</option>
										<option>Win7</option>
										<option>Linux</option>
										<option>多系统</option>
										<option>其他</option>
									</select>
								</td>
								
								<td>
									<table>
										<tr>
											<td class="theader">值班队员</td>
											<td  class="theader calendarBox"><input name="duty_member" id="attendanceDuty"  type="text" style="width: 100px"/></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="theader">
									故障描述<textarea name="problem_describe" rows="4" cols="100" style="width: 620px;font-size: 16px;"></textarea>
								</td>
							</tr>

							<tr align="center">
								<td colspan="2">
									<input class="super-emphasize" id="submitButton" type="button" value="提交" onclick="attendance.addAttendanceSubmit();"/>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
