<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
	<head>
		<link type="text/css" rel="stylesheet" href="../css/layout.css" />
		<jsp:include page="../js/inc.jsp"></jsp:include>	
		<title></title>
		<script type="text/javascript">
			sy.ns('extrawork');
			$(function(){
				$('#extraStartTime').datetimebox({
					showSeconds:false,
					editable : false
				});
				
				$('#extraEndTime').datetimebox({
					showSeconds:false,
					editable : false
				});
				
				extrawork.extraworkers = $('#extraworkers').combobox({
					url : sy.bp() + '/memberX.do?method=getMembersName',
	        		editable : false,
	        		valueField :'member_no',
	        		textField : 'member_name',
	        		multiple : true,
	        		onSelect:function(data){
	        			
	        		}
				});
				extrawork.extraworker = $('#extraworker').combobox({
					url : sy.bp() + '/memberX.do?method=getMembersName',
	        		editable : false,
	        		valueField :'member_no',
	        		textField : 'member_name',
	        		onSelect:function(data){
	        			
	        		}
				});
				
				extrawork.uid = sy.UUID();
				extrawork.addExtraSubmit = function() {
					extrawork.startTime = $('#addExtraForm').find('input[name=extra_date1]').val();
					extrawork.endTime = $('#addExtraForm').find('input[name=extra_date2]').val();
					extrawork.startDate = extrawork.startTime.substring(0,10);
					extrawork.endDate = extrawork.endTime.substring(0,10);
					extrawork.startHour = extrawork.startTime.substring(11,13);
					extrawork.startMinu = extrawork.startTime.substring(14,16);
					extrawork.endHour = extrawork.endTime.substring(11,13);
					extrawork.endMinu = extrawork.endTime.substring(14,16);
					
					extrawork.hourLength = extrawork.endHour - extrawork.startHour;
					extrawork.minuLength = (extrawork.endMinu - extrawork.startMinu)/60;
					extrawork.timeLength = extrawork.hourLength + extrawork.minuLength + '';
					
					extrawork.hours = extrawork.timeLength.substring(0,3);
					if(extrawork.startDate != extrawork.endDate) {
						$.messager.alert('搞笑吧','你确定你加班了好几天？','error');
					} else {
						if(extrawork.timeLength <= 0 ) {
							$.messager.alert('搞笑吧','你的时间怎么填的？','error');

						}else {
							$('#extraworkHours').val(extrawork.hours);
							$.messager.confirm('请确认','信息无误，添加加班?',function(b){
								if(b) {
									$('#addExtraForm').form('submit',{
										url : sy.bp()+'/extraWork.do?method=add&uid=' + extrawork.uid,
										success : function(r) {
											var d = $.parseJSON(r);
											$.messager.show({
												title : '提示',
												msg : d.msg
											});
											if(d.success==true){
												$('#addExtraForm input:not(#addExtraButton)').val('');
												$('#addExtraForm').find('textarea').val('');
												$('#extraworkers').combobox('clear');
												$('#extraworker').combobox('clear');
											}
										}
									});
								}
							});
						}
					}
					
				};
				extrawork.countHours = function (){
						extrawork.startTime = $('#addExtraForm').find('input[name=extra_date1]').val();
						extrawork.endTime = $('#addExtraForm').find('input[name=extra_date2]').val();
						extrawork.startDate = extrawork.startTime.substring(0,10);
						extrawork.endDate = extrawork.endTime.substring(0,10);
						extrawork.startHour = extrawork.startTime.substring(11,13);
						extrawork.startMinu = extrawork.startTime.substring(14,16);
						extrawork.endHour = extrawork.endTime.substring(11,13);
						extrawork.endMinu = extrawork.endTime.substring(14,16);
						
						extrawork.hourLength = extrawork.endHour - extrawork.startHour;
						extrawork.minuLength = (extrawork.endMinu - extrawork.startMinu)/60;
						extrawork.timeLength = extrawork.hourLength + extrawork.minuLength + '';
						
						extrawork.hours = extrawork.timeLength.substring(0,3);
						if(extrawork.startDate != extrawork.endDate) {
							$.messager.alert('搞笑吧','你确定你加班了好几天？','error');
						} else {
							if(extrawork.timeLength <= 0 ) {
								$.messager.alert('搞笑吧','你的时间怎么填的？','error');

							}else {
								$('#extraworkHours').val(extrawork.hours);
							}
						}
				  };
			});
			
			
		</script>
	</head>

	<body style="margin: 0; padding: 0; background-color: #FFFFFF;">
		<div align="center" class="MyForm" style="height: 100%">
			<div class="form-box">
				<h3 style="color: #BD1a21">
					请填写加班信息>>
				</h3>
				<form name="addExtraWorkForm" id="addExtraForm" action="" method="post">
					<div align="center">

						<table cellpadding="4">
							<tr>
								<td>
									<table cellpadding="4">

										<tr>
											<td class="theader">
												开始时间
											</td>
											<td class="calendarBox">
												<input id="extraStartTime" name="extra_date1" type="text" style="width: 210px; " />
											</td>
										</tr>
										
										<tr>
											<td class="theader" >
												加班时长
											</td>
											<td class="calendarBox">
												<input onclick="extrawork.countHours();" readonly="readonly" id="extraworkHours" name="extra_hours" type="text" style="width: 210px; "/>
											</td>
										</tr>

									</table>
								</td>
								<td>
									<table cellpadding="4">
									    <tr>
											<td class="theader">
												结束时间
											</td>
											<td class="calendarBox">
												<input name="extra_date2" id="extraEndTime" type="text" style="width: 210px; "/>
											</td>
										</tr>
										<tr>
											<td class="theader">
												加班人
											</td>
											<td class="calendarBox">
												<input name="extra_members" id="extraworkers" type="text" style="width: 210px; "/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="theader">
									<textarea name="extra_content" cols="56" rows="10" placeholder="加班内容（请详细填写！）" style="width: 650px;font-size: 16px;"></textarea>
								</td>
							</tr>
							
							<tr>
								<td class="theader calendarBox">
									添加人&nbsp;&nbsp;&nbsp;&nbsp;<input name="extra_add_member" id="extraworker" name="member_no" type="text" style="width: 100px"/>
								</td>
								<td>
									<input class="super-emphasize" type="button" id="addExtraButton" value="提交" onclick="extrawork.addExtraSubmit();"/>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
