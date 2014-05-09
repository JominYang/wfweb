<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
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
						<ul><c:if test="${dutyer!=null }">
							<li>
								<a id="personal" href="dutyer/addAttendance.jsp" target="attendance">登记出勤单</a>
							</li><li>
								<a id="all" href="dutyer/netAttendance.jsp" target="attendance">登记网上单</a>
							</li><h3></h3></c:if>
							<c:if test="${dutyer!=null }">
							<li>
								<a id="personal" href="dutyer/goToDealAttendance.jsp" target="attendance">拿单出勤</a>
							</li>
							<li>
								<a id="personal" href="dutyer/backAttendance.jsp" target="attendance">还单登记</a>
							</li>
							<li>
								<a id="all" href="dutyer/toVoidAttendance.jsp" target="attendance">查看废单</a>
							</li></c:if>
							<c:if test="${dutyer==null }">
							<li>
								<a id="personal" href="goToDealAttendance.jsp" target="attendance">拿单出勤</a>
							</li>
							<li>
								<a id="personal" href="backAttendance.jsp" target="attendance">还单登记</a>
							</li>
							<li>
								<a id="all" href="toVoidAttendance.jsp" target="attendance">查看废单</a>
							</li></c:if>
							<li>
								<a id="all" href="allAttendance.jsp" target="attendance">查看出勤</a>
							</li>
							<h3></h3>
							<li>
								<a id="all" href="myUAttendance.jsp" target="attendance">我的出勤单（待处理）</a>
							</li>
							<li>
								<a id="all" href="myAttendance.jsp" target="attendance">我的出勤单（已完成）</a>
							</li>
							<h3></h3>
							<c:if test="${dutyer==null }">
							<li>
								<a id="all" href="netAttendance.jsp" target="attendance">网上报单系统</a>
							</li></c:if>
							
						</ul>
					</div>
				
				</div>
			</aside>
			<section id="main" class="purchase">
				 <iframe name="attendance" width="100%" height="100%" frameborder="0" scrolling="no" src="./attendanceMain.jsp">
				 </iframe>
			</section>
		</div>
	</div>
    <jsp:include page="../footer.html" flush="true">
       <jsp:param name="" value="" /> 
    </jsp:include>
</html>
