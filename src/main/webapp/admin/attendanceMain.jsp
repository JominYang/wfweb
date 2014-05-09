<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
  <jsp:include page="../js/inc.jsp"></jsp:include>
  <style type="text/css">
  	.ptable {
  		font-family: "微软雅黑";
  	}
  	.ptable .left{
  		background:#EDF4FE;
  		width: 100px;
  	}
  	.ptable .right{
  		background:#E0ECFF;
  		width: 200px;
  	}
  </style>
  <script type="text/javascript">
  	$(function(){
  				$('#extrawork_ranking')
				.datagrid(
						{
							url : sy.bp()
									+ '/attendance.do?method=rank',
							pagination : false,
							fit : true,
							fitColumns : true,
							nowrap : false,
							border : true,
							idField : 'query_id',
							columns : [ [ {
								title : '队员姓名',
								field : 'member_name',
								width : 60,
								align : 'center'
							}, {
								title : '出勤数量',
								field : 'month_atten_num',
								width : 60,
								align : 'center'
							}] ]});
						$('#extrawork_self').datagrid({
							url : sy.bp()+'/attendance.do?method=my&member_no=<%=session.getAttribute("loginNo") %>&member_name=<%=session.getAttribute("loginName") %>',
							pagination : false,
							fit : true,
							fitColumns : true,
							nowrap : false,
							border : true,
							idField : 'member_no',
							columns : [ [ {
								title : '本月出勤 ',
								field : 'month_atten_num',
								width : 60,
								align : 'center'
							}, {
								title : '待解决出勤',
								field : 'not_deal',
								width : 60,
								align : 'center'
							}] ]});
							
							
							$('#extrawork_self1').datagrid({
							url : sy.bp()+'/attendance.do?method=permonth&member_no=<%=session.getAttribute("loginNo") %>',
							pagination : false,
							fit : true,
							fitColumns : true,
							nowrap : false,
							border : true,
							idField : 'member_no',
							columns : [ [ {
								title : '月份',
								field : 'date',
								width : 60,
								align : 'center'
							}, {
								title : '出勤数量',
								field : 'count',
								width : 60,
								align : 'center'
							}] ]});
  	});
  </script>
  </head>
  
  <body>
  <div style="width: 45%; float: left;margin-right: 5%; font-size: 15px;font-family: 微软雅黑" >
  	<div style="height:95px;background-color: #ebf3fb;border-radius:5px;padding-top: 20px;">
	  	<div align=left style="font-size: 18px;color: #eb1446"><b>&nbsp;&nbsp;&gt;&gt;我出的勤</b></div><br/>
	    <form id="extrawork_self"></form></div>
	<div style="height:340px;background-color: #ebf3fb;border-radius:5px;padding-top: 20px;margin-top: 20px;">
	  	<div align=left style="font-size: 18px;color: #eb1446"><b>&nbsp;&nbsp;&gt;&gt;往月回首</b></div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（从每月1号起）
	    <form id="extrawork_self1"></form></div>
    
  </div>
  <div style="float: left;width: 50%;height:470px;padding-top:20px;border-radius:5px;background-color: #ebf3fb;font-size: 15px;font-family: 微软雅黑">
  	<div align=left style="color: #eb1446;font-size: 18px;"><b>&nbsp;&nbsp;&gt;&gt;谁是"出勤王子"</b></div><br/>
  	<form id="extrawork_ranking"></form>
  </div>
  </body>
</html>
