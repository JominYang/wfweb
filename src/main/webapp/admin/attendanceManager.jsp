<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>WFcsu</title>
<link type="text/css" rel="stylesheet" href="../css/main.css" />
</head>
<jsp:include page="top.jsp" flush="true">
	<jsp:param name="" value="" />
</jsp:include>
<div id="page">
	<div class="container" id="page-container">
		<aside id="sidebar" class="sidebar">
		<div id="filter" class="filter module">
			<div class="module_inner">
				<ul>
					<li><a id="add" href="addAttendance.jsp" target="attendance">增加出勤单</a>
					</li>
					<li><a id="add" href="modifyAttendance.jsp"
						target="attendance">管理出勤单</a></li>
					<li><a id="add" href="checkAttendance.jsp" target="attendance">审核出勤单</a>
					</li>
					<li><a id="statistics" href="statisticsAttendance.jsp"
						target="attendance">统计出勤单</a></li>
				</ul>
			</div>
		</div>
		</aside>
		<section id="main" class="purchase"> <iframe
			name="attendance" width="100%" height="100%" frameborder="0"
			scrolling="no" src="./attendanceMain.jsp"> </iframe> </section>
	</div>
</div>

<jsp:include page="../footer.html" flush="true">
	<jsp:param name="" value="" />
</jsp:include>


</html>
