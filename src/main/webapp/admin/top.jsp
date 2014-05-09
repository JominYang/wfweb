<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  
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
		
		index.option_dialog = $('#option_dialog').dialog({
			title : '',
			iconCls : 'icon-edit',
			modal:true,
			width : 310,
			buttons : [{
				text : '取消',
				iconCls : 'icon-undo',
				plain : true,
				handler : function(){
					index.option_dialog.dialog('close');
				}
			},{
				text : '修改月起始日',
				iconCls : 'icon-edit',
				plain : true,
				handler : function(){
					index.option_dialog.dialog('close');
					index.start_month.dialog('open');
				}
			},{
				text : '修改值班时间',
				iconCls : 'icon-edit',
				plain : true,
				handler : function(){
					index.option_dialog.dialog('close');
					index.duty_time.dialog('open');
				}
		}]}).dialog('close');
		
		index.start_month = $('#start_month').dialog({
			title : '',
			iconCls : 'icon-edit',
			modal:true,
			width : 310,
			buttons : [{
				text : '取消',
				iconCls : 'icon-undo',
				plain : true,
				handler : function(){
					index.start_month.dialog('close');
				}
			},{
				text : '确定',
				iconCls : 'icon-edit',
				plain : true,
				handler : function(){
					index.start_month_form.submit();
				}
			}]}).dialog('close');
		index.duty_time = $('#duty_time').dialog({
			title : '',
			iconCls : 'icon-edit',
			modal:true,
			width : 420,
			buttons : [{
				text : '取消',
				iconCls : 'icon-undo',
				plain : true,
				handler : function(){
					index.duty_time.dialog('close');
				}
			},{
				text : '确定',
				iconCls : 'icon-edit',
				plain : true,
				handler : function(){
					index.duty_time_form.submit();
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
		
		index.start_month_form = $('#start_month_form').form({
			url : sy.bp()+'/admin.do?method=startM',
			success : function(r){
				var d = $.parseJSON(r);
				if(d.success){
					index.start_month.dialog('close');
				}
				$.messager.show({
						msg : d.msg,
						title : '提示'
					});
				}
			});
		index.duty_time_form = $('#duty_time_form').form({
			url : sy.bp()+'/admin.do?method=dutyTime',
			success : function(r){
				var d = $.parseJSON(r);
				if(d.success){
					index.duty_time.dialog('close');
				}
				$.messager.show({
						msg : d.msg,
						title : '提示'
					});
				}
			});
		
		index.changepwd = function(){
			index.pwd_Dialog.dialog('open');
		};
		
		index.option = function(){
			index.option_dialog.dialog('open');
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
				<a class="brand" href="/">WFmanager</a>
				
				<ul id="nav-ul" class="nav">
					<li>
						<a href="main.jsp">首页</a>
					</li>
					<li>
						<a href="memberManager.jsp">队员管理</a>
					</li>
					<li>
						<a href="attendanceManager.jsp">出勤管理</a>
					</li>
					<li>
						<a href="extraWorkManager.jsp">加班管理</a>
					</li>
					<li>
						<a href="salaryManager.jsp">工资管理</a>
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
									<li class="account-switch-enter" style="">
										<a onclick="index.changepwd();">
											<i class="icon_account_setting"></i>
											修改密码
										</a>
									</li>
									<li class="account-switch-enter" style="">
										<a onclick="index.option();">
											<i class="icon_account_setting"></i>
											选&nbsp;&nbsp;项
										</a>
									</li>
									<li>
										<a href="login.do?method=logOut">
											<i class="icon_account_exit"></i>
											注&nbsp;&nbsp;销
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
	<div id="option_dialog"></div>
	<div id="start_month">
		<form id="start_month_form" method="post">
			<table>
				<tr>
					<td class="td_right" align="center" style="width: 130px;">计算工资时，每月始于</td>
					<td><input name="start_month" type="text" style="width: 50px" />(1-30)日</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="duty_time">
		<table>
			<form id="duty_time_form" method="post">
			<tr>
				<td>第一个班始于</td><td><input id="duty_time1" name="duty_time1" style="width: 80px;"/>(HH:mm:ss)，&nbsp;&nbsp;时长</td><td><input name="duty_time_hours1" style="width: 30px;" />小时</td>
			</tr>
			<tr>
				<td>第二个班始于</td><td><input id="duty_time2" name="duty_time2" style="width: 80px;" />(HH:mm:ss)，&nbsp;&nbsp;时长</td><td><input name="duty_time_hours2" style="width: 30px;" />小时</td>
			</tr>
			<tr>
				<td>第三个班始于</td><td><input id="duty_time3" name="duty_time3" style="width: 80px;" />(HH:mm:ss)，&nbsp;&nbsp;时长</td><td><input name="duty_time_hours3" style="width: 30px;" />小时</td>
			</tr>
			<tr>
				<td>第四个班始于</td><td><input id="duty_time4" name="duty_time4" style="width: 80px;" />(HH:mm:ss)，&nbsp;&nbsp;时长</td><td><input name="duty_time_hours4" style="width: 30px;" />小时</td>
			</tr>
			<tr>
				<td>第四个班止于</td><td><input id="duty_time5" name="duty_time5" style="width: 80px;"/>(HH:mm:ss)</td>
			</tr>
			</form>
		</table>
	</div>

</html>
