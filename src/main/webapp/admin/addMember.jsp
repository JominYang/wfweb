<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
	<head>
		<jsp:include page="../js/inc.jsp"></jsp:include>
		<link type="text/css" rel="stylesheet" href="../css/layout.css" />
		<title></title>
		
		<script type="text/javascript">
		$(function(){
					
		});
		function addMemberSubmit() {
			$.messager.confirm('请确认','信息无误，添加队员?',function(b){
				if(b) {
					$('#addMemberForm').form('submit',{
						url : sy.bp()+'/member.do?method=addMember',
						success : function(r) {
							var d = $.parseJSON(r);
							$.messager.show({
								title : '提示',
								msg : d.msg
							});
							if(d.success==true){
								$('#addMemberForm input:not(#addMemberButton)').val('');
							}
						}
					});
				}
			});
		}
	</script>
	</head>
	

	<body style="margin: 0;padding: 0;background-color: #FFFFFF;">
		<div align="center" class="MyForm" style="height: 100%">
			<div class="form-box">
				<h3 style="color: #BD1a21">
					请输入队员信息>>
				</h3>
				<form id="addMemberForm" name="MemberForm" action="" method="post">
					<table>
						<tr>
							<td class="theader">
								队员工号
							</td>
							<td>
								<input name="member_no" type="text" />
							</td>
							<td class="theader">
								队员姓名
							</td>
							<td>
								<input name="member_name" type="text" />
							</td>
						</tr>
						<tr>
							<td class="theader">
								专业班级
							</td>
							<td>
								<input name="member_class" type="text" />
							</td>
							<td class="theader">
								联系方式
							</td>
							<td>
								<input name="member_phone" type="text" />
							</td>
						</tr>
						<tr>
							<td class="theader">
								电子邮箱
							</td>
							<td>
								<input name="member_email" type="text" />
							</td>
							<td class="theader">
								秋秋号码
							</td>
							<td>
								<input name="member_qq" type="text" />
							</td>
						</tr>
						<tr>
							<td class="theader">
								队员生日
							</td>
							<td>
								<input name="member_birth" type="text" />
							</td>
							<td class="theader">
								队员宿舍
							</td>
							<td>
								<input name="member_room" type="text" />
							</td>
						</tr>
						<tr>
							<td class="theader">
								队员家乡
							</td>
							<td>
								<input name="member_home" type="text" />
							</td>
							<td class="theader">
								是否在队:
							</td>
							<td>
								<select name="member_state" style="height: 28px; padding-top: 5px;margin-top: -12px;">
									<option value="在队">
										在队
									</option>
									<option value="退队">
										退队
									</option>
								</select>

							</td>
						</tr>

						<tr align="right">
							<td colspan="4">
								<input id="addMemberButton" class="super-emphasize" type="button" value="确定添加" onclick="addMemberSubmit();" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
