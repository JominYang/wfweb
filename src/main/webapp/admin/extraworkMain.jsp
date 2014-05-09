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
									+ '/extraWork.do?method=rank',
							pagination : false,
							fit : true,
							fitColumns : true,
							nowrap : false,
							border : true,
							idField : 'query_id',
							sortName : 'member_no',
							sortOrder : 'desc',
							columns : [ [ {
								title : '队员姓名',
								field : 'member_name',
								width : 100,
								align : 'center'
							}, {
								title : '加班数量(个)',
								field : 'month_extrawork_num',
								width : 100,
								align : 'center'
							}, {
								title : '累计时长(H)',
								field : 'month_extrawork_hours',
								width : 100,
								align : 'center'
							}] ]});
						$('#extrawork_self').datagrid({
							url : sy.bp()+'/extraWork.do?method=my&member_no=<%=session.getAttribute("loginNo") %>',
							pagination : false,
							fit : true,
							fitColumns : true,
							nowrap : false,
							border : true,
							idField : 'member_no',
							columns : [ [ {
								title : '本月加班(个) ',
								field : 'month_extrawork_num',
								width : 60,
								align : 'center'
							}, {
								title : '累计时长(H)',
								field : 'month_extrawork_hours',
								width : 60,
								align : 'center'
							}] ]});
							
				$('#extrawork_self1').datagrid({
							url : sy.bp()+'/extraWork.do?method=permonth&member_no=<%=session.getAttribute("loginNo") %>',
							pagination : false,
							fit : true,
							fitColumns : true,
							nowrap : false,
							border : true,
							idField : 'member_no',
							columns : [ [ {
								title : '月份',
								field : 'member_no',
								width : 60,
								align : 'center'
							}, {
								title : '加班(个)',
								field : 'month_extrawork_num',
								width : 60,
								align : 'center'
							}, {
								title : '时长(H)',
								field : 'month_extrawork_hours',
								width : 60,
								align : 'center'
							}] ]});
  	});
  </script>
  </head>
  
  <body>
  <div style="width: 45%; float: left;margin-right: 5%; font-size: 15px;font-family: 微软雅黑" >
  	<div style="height:95px;background-color: #ebf3fb;border-radius:5px;padding-top: 20px;">
	  	<div align=left style="font-size: 18px;color: #eb1446"><b>&nbsp;&nbsp;&gt;&gt;加班累计</b></div><br/>
	    <form id="extrawork_self"></form></div>
	<div style="height:340px;background-color: #ebf3fb;border-radius:5px;padding-top: 20px;margin-top: 20px;">
	  	<div align=left style="font-size: 18px;color: #eb1446"><b>&nbsp;&nbsp;&gt;&gt;往月回顾</b></div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（从每月1号起）
	    <form id="extrawork_self1"></form></div>
    
  </div>
  <div style="float: left;width: 50%;height:480px;padding-top:20px;border-radius:5px;background-color: #ebf3fb;font-size: 15px;font-family: 微软雅黑">
  	<div align=left style="color: #eb1446;font-size: 18px;"><b>&nbsp;&nbsp;&gt;&gt;本月加班风云榜</b></div><br/>
  	<form id="extrawork_ranking"></form>
  </div>
  </body>
</html>
