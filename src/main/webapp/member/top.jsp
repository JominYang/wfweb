<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../js/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		sy.ns('index');
		var a1 = document.URL;
		var a1 = a1.substring(a1.lastIndexOf('/')+1,a1.length);
		var a2 = $('#nav-ul li a');
		var a3 = $('#nav-ul li');
		for(var i=0;i<a2.length;i++) {
			if(a1==$(a2[i]).attr('href')) {
				$(a3[i]).addClass('nav-selected').siblings().removeClass('nav-selected');
			}
		}
		$('#filter ul li').click(function(){
			$('#filter ul li').filter('.active').removeClass('active');
			$(this).addClass('active');
		});
		index.dialog = $('#dialog').dialog(
			{
				title : '值班签到',
				iconCls : 'icon-edit',
				modal : true,
				closed : true,
				width : 310,
				buttons : [
						{
							text : '签到',
							iconCls : 'icon-ok',
							plain : true,
							handler : function() {
								$('#dutyorder').submit();
							}
						},{
							text : '取消',
							iconCls : 'icon-no',
							plain : true,
							handler : function() {
								index.dialog.dialog('close');
							}
						}]
			}).dialog('close');
			index.duty = function(){
				index.dialog.dialog('open');
			};
			index.dutyorder = $('#dutyorder').form({
				url : sy.bp()+'/login.do?method=signIn&member_no=<%=session.getAttribute("loginNo") %>',
				success : function(r) {
					var d = $.parseJSON(r);
					if (d.success) {
						index.dialog.dialog('close');
					}
					$.messager.show({
						msg : d.msg,
						title : '提示'
					});
				}
			});
			index.dutyover = function() {
  				$.messager.confirm('请确认','确定要现在下班？',function(b){
					if(b){
						$.ajax({
							url : sy.bp()+'/login.do?method=signOut',
							data : {
								
							},
							dataType : 'json',
							cache : false,
							success : function(response) {
								$.messager.show({
									msg : response.msg,
									title : '提示'
								});
							}
						});
					}
				});
			};
			index.profile = function() {
				$.ajax({
						url : sy.bp()+'/memberX.do?method=getSinger',
						data : {
							member_no : <%=session.getAttribute("loginNo")%>
						},
						dataType : 'json',
						cache : false,
						success : function(r) {
							index.profile_editForm.form('load',r);
							index.profile_editDialog.dialog('open');
						}
					});
			};
			index.profile_editDialog = $('#profile_editDialog').dialog({
				title : '个人信息',
				iconCls : 'icon-edit',
				modal:true,
				closed : true,
				width : 520,
				buttons : [{
					text : '修改密码&nbsp;&nbsp;&nbsp;&nbsp;',
					iconCls : 'icon-remove',
					plain : true,
					handler : function(){
						index.changepwd();
					}
				},{
					text : '修改',
					iconCls : 'icon-ok',
					plain : true,
					handler : function(){
						index.profile_editForm.submit();
					}
				},{
					text : '取消',
					iconCls : 'icon-undo',
					plain : true,
					handler : function(){
						index.profile_editDialog.dialog('close');
					}
				}]}).dialog('close');
				
				index.profile_editForm = $('#profile_editForm').form({
				url : sy.bp()+'/memberX.do?method=updateInfo', 
				success : function(r){
					var d = $.parseJSON(r);
					if(d.success){
						index.profile_editDialog.dialog('close');
					}
					$.messager.show({
							msg : d.msg,
							title : '提示'
						});
					}
				});
				
				
				index.pwd_Dialog = $('#pwd_Dialog').dialog({
					title : '修改密码',
					iconCls : 'icon-edit',
					modal:true,
					width : 360,
					buttons : [{
						text : '确认',
						iconCls : 'icon-ok',
						plain : true,
						handler : function(){
							index.pwd_Form.submit();
						}
					},{
						text : '取消',
						iconCls : 'icon-undo',
						plain : true,
						handler : function(){
							index.pwd_Dialog.dialog('close');
						}
				}]}).dialog('close');
				
				index.pwd_Form = $('#pwd_Form').form({
				url : sy.bp()+'/memberX.do?method=changePwd&member_no=<%=session.getAttribute("loginNo")%>',
				success : function(r){
					var d = $.parseJSON(r);
					if(d.success){
						index.pwd_Dialog.dialog('close');
					}
					$.messager.show({
							msg : d.msg,
							title : '提示'
						});
					}
				});
				
				index.changepwd = function(){
					index.profile_editDialog.dialog('close');
					index.pwd_Dialog.dialog('open');
				};
	});
</script>
<style type="text/css">
	.tableForm td{
		padding-bottom: 7px;
	}
	.tableForm .td_right {
		width: 80px;
		font-size: 15px;
	}
	.tableForm input{
		font-family:微软雅黑;
		width: 160px;
		height: 30px;
		font-size: 14px;
	}
</style>


<body>

  <nav class="topbar">
		<div class="topbar-inner">
			<div class="container"> 
				<a class="brand" href="/">WFcsu</a>
				
				<ul id="nav-ul" class="nav">
					<li>
						<a href="main.jsp">首页</a>
					</li>
					<li>
						<a href="attendanceSystem.jsp">出勤系统</a>
					</li>
					<li>
						<a href="extraWorkSystem.jsp">加班系统</a>
					</li>
					<li>
						<a href="salarySystem.jsp">工资系统</a>
					</li>
				</ul>
				<div class="topbar-infomation">
					<ul>
						<li class="email_drop">
							<span class="name"> <b>Welcome!</b>&nbsp;&nbsp;<font size="3+" color="yellow">${loginVo.member_name}</font></span>
						</li>
						<li class="account_drop">
							<a class="account">
								<span>&nbsp;</span>
								<i class="icon_account_arrow"> </i>
							</a>
							<div class="settings-container" data-account="">
								<ul id="account-dropdown">
									<li>
										<a onclick="index.profile();">
											<i class="icon_account_switch"></i>
											个人信息
										</a>
									</li>
									<c:if test="${dutyer==null }">
									<li class="account-switch-enter" style="">
										<a onclick="index.duty();">
											<i class="icon_account_setting"></i>
											值班签到
										</a>
									</li></c:if>
									<c:if test="${dutyer!=null }">
									<li class="account-switch-enter" style="">
										<a onclick="index.dutyover();">
											<i class="icon_account_setting"></i>
											下班签退
										</a>
									</li></c:if>
									<li>
										<a href="login.do?method=logOut">
											<i class="icon_account_exit"></i>
											退出
										</a>
									</li>	
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</nav>
	<div id="dialog" align="center">
		<form id="dutyorder" method="post">今天第
			<select name="number" style="width: 60px;height: 25px;font-size: 15px;font-family: 微软雅黑">
				<option value="">-&nbsp;&nbsp;-</option>
				<option value="1">-一-</option>
				<option value="2">-二-</option>
				<option value="3">-三-</option>
				<option value="4">-四-</option>
			</select>个班
		</form>
	</div>
	
	<div id="profile_editDialog" >  
    		<form id="profile_editForm" method="post">
    			<table class="tableForm datagrid-toolbar">
					<tr style="margin-bottom: 10px;">
						<td class="td_right" align="center">工号</td>
						<td><input name="member_no" readonly="readonly"/></td>
						<td class="td_right" align="center">家乡</td>
						<td><input name="member_home"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td class="td_right" align="center">姓名</td>
						<td><input name="member_name" readonly="readonly" /></td>
						<td class="td_right" align="center">秋秋</td>
						<td><input name="member_qq" /></td>
					</tr>
					<tr>
						<td class="td_right" align="center">状态</td>
						<td><select name="member_state" disabled="disabled" style="height: 30px;width: 160px;font-size: 16px;">
							<option value="在队">在队</option>
							<option value="退队">退队</option>
							<option value="临时">临时</option>
						</select></td>
						<td class="td_right" align="center">生日</td>
						<td><input name="member_birth" /></td>
					</tr>
					<tr>
						<td></td><td></td>
						<td class="td_right" align="center">班级</td>
						<td><input name="member_class"/></td>
					</tr>
					<tr>
						<td></td><td></td>
						<td class="td_right" align="center">宿舍</td>
						<td><input name="member_room"/></td>
					</tr>
					<tr>
						<td></td><td></td>
						<td class="td_right" align="center">手机</td>
						<td><input name="member_phone"/></td>
					</tr>
					<tr>
						<td></td><td></td>
						<td class="td_right" align="center">邮箱</td>
						<td><input name="member_email"/></td>
					</tr>
				</table>
    		</form>
		</div>
		<div id="pwd_Dialog" >  
    		<form id="pwd_Form" method="post">
    			<table class="tableForm">
				<tr>
					<td class="td_right" align="center">老密码</td>
					<td><input name="oldpwd" id="oldpwd" type="password" style="width: 210px" /></td>
				</tr>
				<tr>
					<td class="td_right" align="center">新密码</td>
					<td><input id="newpwd" name="newpwd" type="password" style="width: 210px; " /></td>
				</tr>
				<tr>
					<td class="td_right" align="center">确&nbsp;&nbsp;认</td>
					<td><input id="renewpwd" name="renewpwd" type="password" style="width: 210px; " /></td>
				</tr>
			</table>
    		</form>
		</div>

