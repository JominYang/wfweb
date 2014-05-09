<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="../js/inc.jsp"></jsp:include>
<style type="text/css">
		.tableForm td {
		background: #E0ECFF;
		font-family: "微软雅黑";
		font-size: 13px;
		cellpadding: 5px;
		width: 200PX;
		line-height: 16px;
		}
	</style>

<title></title>

<script type="text/javascript">
	$(function() {
		sy.ns('extrawork');
		$('#startTime').datebox({
			showSeconds : false,
			editable : false
		});

		$('#endTime').datebox({
			showSeconds : false,
			editable : false
		});

		$('#startTimeEdit').datetimebox({
			showSeconds : false,
			editable : false,
			onSelect : function(date) {
			}
		});

		$('#endTimeEdit').datetimebox({
			showSeconds : false,
			editable : false,
			onSelect : function(date) {
			}
		});

		extrawork.extraworkers = $('#extraworkers').combobox({
			url : sy.bp() + '/memberX.do?method=getMembersName',
			editable : false,
			valueField : 'member_no',
			textField : 'member_name',
			multiple : true,
			onSelect : function() {

			}
		});

		extrawork.extraworker = $('#extraworker1').combobox({
			url : sy.bp() + '/memberX.do?method=getMembersName',
			editable : false,
			valueField : 'member_no',
			textField : 'member_name',
			onSelect : function(data) {

			}
		});
		extrawork.extraworker = $('#extraworker2').combobox({
			url : sy.bp() + '/memberX.do?method=getMembersName',
			editable : false,
			valueField : 'member_no',
			textField : 'member_name',
			onSelect : function(data) {

			}
		});

		extrawork.editRow = undefined;
		extrawork.queryExtraWorkForm = $('#queryExtraWorkForm').form();
		//查看所有加班条
		extrawork.all_datagrid = $('#extrawork_datagrid')
				.datagrid(
						{
							url : sy.bp()
									+ '/extraWork.do?method=get&no=nopass&extra_add_no='+<%=session.getAttribute("loginNo") %>,
							pagination : true,
							pageSize : 10,
							pageList : [ 10, 20, 30 ],
							fit : true,
							fitColumns : true,
							nowrap : false,
							border : false,
							idField : 'query_id',
							sortName : 'extra_date1',
							sortOrder : 'asc',
							columns : [ [ {
								title : '开始时间',
								field : 'extra_date1',
								width : 60,
								align : 'center'
							}, {
								title : '结束时间',
								field : 'extra_date2',
								width : 60,
								align : 'center'
							}, {
								title : '时长',
								field : 'extra_hours',
								width : 15,
								align : 'center'
							}, {
								title : '工作内容',
								field : 'extra_content',
								width : 120,
								align : 'center'
							},{
								title : '加班人',
								field : 'extra_members_name',
								width : 120,
								align : 'center'
							},{
								title : '加班状态',
								field : 'extra_state',
								width : 30,
								align : 'center'
							} ] ],
							toolbar : [
									'-',
									{
										text : '删除',
										iconCls : 'icon-remove',//图标
										handler : function() {
											var rows = extrawork.all_datagrid.datagrid('getSelections');
													console.info(rows.length);
											if (rows.length > 0) {
												$.messager
														.confirm(
																'请确认',
																'你确定删除选中项？',
																function(b) {
																	if (b) {
																		var nos = [];
																		for ( var i = 0; i < rows.length; i++) {
																			nos.push(rows[i].query_id);
																		}
																		
																		$
																				.ajax({
																					url : sy
																							.bp()
																							+ '/extraWork.do?method=del',
																					data : {
																						nos : nos
																								.join(',')
																					},
																					dataType : 'json',
																					success : function(
																							r) {
																								extrawork.all_datagrid
																										.datagrid('load'),
																								extrawork.all_datagrid
																										.datagrid('unselectAll'),
																								$.messager
																										.show({
																											title : '提示',
																											msg : '删除成功！'
																										});
																					}
																				});
																	}
																});
											} else {
												$.messager
														.alert('提示',
																'请选择要删除的加班！',
																'warning');
											}
										}
									},
									'-',
									{
										text : '取消',
										iconCls : "icon-undo",
										handler : function() {
											editRow = undefined;
											extrawork.all_datagrid
													.datagrid('rejectChanges');
											extrawork.all_datagrid
													.datagrid('unselectAll');
										}
									}, '-' ]
						});

		extrawork.searchExtrawork = function() {
			extrawork.all_datagrid.datagrid('load', sy.serializeObject(extrawork.queryExtraWorkForm));
		};
	});
</script>
</head>

<body style="background-color: #F5F5F5;font-size: 14px;" class="easyui-layout" fit="true" border="false">
	<!-- <div region="north" title="" split="true"
		style="height: 40px;overflow: hidden;" border="false">
		<form id="queryExtraWorkForm" name="queryExtraWorkForm" action=""
			method="post">
			<table>
				<tr>
					<td>开始日期：</td>
					<td class="calendarBox"><input id="startTime" name="startDate"
						type="text" style="width: 210px; " />
					</td>
					<td>&nbsp;&nbsp;</td>
					<td>结束日期：</td>
					<td class="calendarBox"><input id="endTime" name="endDate"
						type="text" style="width: 210px; " />
					</td>
					<td>&nbsp;&nbsp;</td>
					<td><input type="button"
						onclick="extrawork.searchExtrawork();" value="查询"
						class="super-emphasize" style="padding: 2px 0 2px 0;" />
					</td>
				</tr>
			</table>
		</form>
	</div> -->

	<div region="center" title="" border="false" style="background-color: #ebf3fb;border-radius:5px;padding-bottom: 30px;">
		<div align=left style="font-size: 18px;color: #eb1446;line-height: 30px;"><b>&nbsp;&nbsp;&gt;&gt;未通过加班表</b></div>
		<form id="extrawork_datagrid"></form>
	</div>
</body>
</html>
