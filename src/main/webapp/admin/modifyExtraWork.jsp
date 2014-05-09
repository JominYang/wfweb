<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
									+ '/extraWork.do?method=get&no=modify',
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
										text : '修改',
										iconCls : 'icon-edit',
										handler : function() {
											extrawork.edit();
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
			extrawork.all_datagrid.datagrid('load', sy
					.serializeObject(extrawork.queryExtraWorkForm));
		};
		//修改加班dialog
		extrawork.editDialog = $('#extrawork_editDialog')
				.dialog(
						{
							title : '修改加班',
							iconCls : 'icon-edit',
							modal : true,
							closed : true,
							width : 640,
							buttons : [
									{
										text : '保存',
										iconCls : 'icon-save',
										plain : true,
										handler : function() {
											extrawork.startTime = $(
													'#extrawork_editForm')
													.find(
															'input[name=extra_date1]')
													.val();
											extrawork.endTime = $(
													'#extrawork_editForm')
													.find(
															'input[name=extra_date2]')
													.val();
											extrawork.startDate = extrawork.startTime
													.substring(0, 10);
											extrawork.endDate = extrawork.endTime
													.substring(0, 10);
											extrawork.startHour = extrawork.startTime
													.substring(11, 13);
											extrawork.startMinu = extrawork.startTime
													.substring(14, 16);
											extrawork.endHour = extrawork.endTime
													.substring(11, 13);
											extrawork.endMinu = extrawork.endTime
													.substring(14, 16);

											extrawork.hourLength = extrawork.endHour
													- extrawork.startHour;
											extrawork.minuLength = (extrawork.endMinu - extrawork.startMinu) / 60;
											extrawork.timeLength = extrawork.hourLength
													+ extrawork.minuLength + '';

											extrawork.hours = extrawork.timeLength
													.substring(0, 3);
											if (extrawork.startDate != extrawork.endDate) {
												$.messager.alert('搞笑吧',
														'你确定你加班了好几天？', 'error');
											} else {
												if (extrawork.timeLength <= 0) {
													$.messager.alert('搞笑吧',
															'你的时间怎么填的？',
															'error');

												} else {
													$('#extra_hours').val(
															extrawork.hours);
													extrawork.editForm.submit();
												}
											}

										}
									},
									{
										text : '重写',
										iconCls : 'icon-undo',
										plain : true,
										handler : function() {
											extrawork.editForm.find('input')
													.val('');
										}
									} ],
							onOpen : function() {
								setTimeout(function() {
									extrawork.editForm.find(
											'input[name=member_no]').focus();
								}, 1);
							}
						}).dialog('close');
		//修改加班表单
		extrawork.editForm = $('#extrawork_editForm').form({
			url : sy.bp() + '/extraWork.do?method=edit',
			success : function(r) {
				var d = $.parseJSON(r);
				if (d.success) {
					extrawork.editDialog.dialog('close');
					extrawork.all_datagrid.datagrid('load');
				} else {
					extrawork.editForm.find('input[name=member_no]').val('');
					extrawork.editForm.find('input[name=member_no]').focus();
				}
				$.messager.show({
					msg : d.msg,
					title : '提示'
				});
			}
		});

		extrawork.edit = function() {
			var rows = extrawork.all_datagrid.datagrid('getSelections');
			if (rows.length == 1) {
				$.messager.progress({
					text : 'progressing...',
					intervar : 100
				});
				$.ajax({
					url : sy.bp() + '/extraWork.do?method=getOne',
					data : {
						query_id : rows[0].query_id
					},
					dataType : 'json',
					cache : false,
					success : function(r) {
						extrawork.editForm.form('load', r);
						extrawork.editDialog.dialog('open');
						$.messager.progress('close');
					}
				});
			} else {
				$.messager.alert('提示', '请选择一项要编辑的记录！', 'error');
			}
		};

		extrawork.countHours = function() {
			extrawork.startTime = $('#extrawork_editForm').find(
					'input[name=extra_date1]').val();
			extrawork.endTime = $('#extrawork_editForm').find(
					'input[name=extra_date2]').val();
			extrawork.startDate = extrawork.startTime.substring(0, 10);
			extrawork.endDate = extrawork.endTime.substring(0, 10);
			extrawork.startHour = extrawork.startTime.substring(11, 13);
			extrawork.startMinu = extrawork.startTime.substring(14, 16);
			extrawork.endHour = extrawork.endTime.substring(11, 13);
			extrawork.endMinu = extrawork.endTime.substring(14, 16);

			extrawork.hourLength = extrawork.endHour - extrawork.startHour;
			extrawork.minuLength = (extrawork.endMinu - extrawork.startMinu) / 60;
			extrawork.timeLength = extrawork.hourLength + extrawork.minuLength
					+ '';

			extrawork.hours = extrawork.timeLength.substring(0, 3);
			if (extrawork.startDate != extrawork.endDate) {
				$.messager.alert('搞笑吧', '你确定你加班了好几天？', 'error');
			} else {
				if (extrawork.timeLength <= 0) {
					$.messager.alert('搞笑吧', '你的时间怎么填的？', 'error');

				} else {
					$('#extra_hours').val(extrawork.hours);
				}
			}
		};
	});
</script>
</head>

<body style="background-color: #F5F5F5" class="easyui-layout" fit="true"
	border="false">
	<div region="north" title="" split="true"
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
	</div>

	<div region="center" title="" border="false" style="background-color: #ebf3fb;border-radius:5px;">
		<form id="extrawork_datagrid"></form>
	</div>

	<div id="extrawork_editDialog">
		<form id="extrawork_editForm" name="extrawork_editForm" method="post">
			<table class="tableForm datagrid-toolbar">
				<tr>
					<td><input name="query_id" style="width: 200px; display: none" />
					</td>
				</tr>
				<tr>
					<td align="right">开始时间</td>
					<td class="calendarBox"><input id="startTimeEdit"
						name="extra_date1" type="text" style="width: 210px; " />
					</td>
					<td align="right">结束时间</td>
					<td class="calendarBox"><input id="endTimeEdit"
						name="extra_date2" type="text" style="width: 210px; " />
					</td>
				</tr>
				<tr>
					<td align="right">加班时长</td>
					<td><input name="extra_hours" id="extra_hours"
						onclick="extrawork.countHours();" type="text" style="width: 210px" />
					</td>
					<td align="right">填写人</td>
					<td><input name="extra_add_member" disabled="disabled" id="extraworker1" type="text" style="width: 210px" />
					</td>
				<tr>
				<tr>
					<td align="right">加班人</td>
					<td><input name="extra_nos" id="extraworkers" type="text"
						style="width: 210px" />
					</td>
					<td align="right">审核人</td>
					<td><input name="verify_no" id="extraworker2" name="member_no"
						type="text" style="width: 210px" />
					</td>
				<tr>
					<td align="right">加班状态</td>
					<td><select name="extra_state" style="width: 210px;">
							<option value="未审核">未审核</option>
							<option value="未通过">未通过</option>
							<option value="通过">通过</option>
					</select>
					</td>
				</tr>
				<tr>
					<td align="right">加班内容</td>
					<td colspan="3"> <textarea name="extra_content" rows="5" cols="50"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
