<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>WFcsu</title>
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" type="text/css" href="../css/zns_style.css" />
<script type="text/javascript" src="../js/zns_3dsc.js"></script>
<script>
  function changeIframe(){
       document.getElementById('div1').style.display= "none";
       document.getElementById('iframe').style.display= "block";
  }
</script>
</head>


   <!-- 导入头部信息 -->
   <jsp:include page="top.jsp" flush="true">
       <jsp:param name="" value="" /> 
    </jsp:include>


 <!-- ------center------- -->
	<div id="page">
		<div class="container" id="page-container">
			<aside id="sidebar" class="sidebar">
				<div id="filter" class="filter module">
					<div class="module_inner">
						<ul>
							<li>
								<a id="add" href="addMember.jsp" target="member" onclick="changeIframe();">增加队员</a>
								
							</li>
							<li>
							    <a id="query" href="modifyMember.jsp" target="member" onclick="changeIframe();">管理队员</a>
								
							</li>
						</ul>
					</div>
				
				</div>
			</aside>
			
			<section id="main" class="purchase">
				
			    <div id="iframe" style="display: none;height: 100%">
				 <iframe name="member" width="100%" height="100%" frameborder="0" scrolling="no">
				</iframe>
				</div> 
				
				  <div id="div1"> 
				       <c:forEach var="list" items="${memberList3D}" begin="0" end="6">
						<a href="#" class="red">${list.member_name}</a>
					  </c:forEach>
					   <c:forEach var="list" items="${memberList3D}" begin="7" end="12">
						<a href="#" class="blue">${list.member_name}</a>
					  </c:forEach>
					  <c:forEach var="list" items="${memberList3D}" begin="13" end="19">
						<a href="#" class="black">${list.member_name}</a>
					  </c:forEach>
					   <c:forEach var="list" items="${memberList3D}" begin="20" end="23">
						<a href="#" class="green">${list.member_name}</a>
					  </c:forEach>
					   <c:forEach var="list" items="${memberList3D}" begin="24" end="26">
						<a href="#" class="yellow">${list.member_name}</a>
					  </c:forEach>
					   <c:forEach var="list" items="${memberList3D}" begin="27">
						<a href="#" class="gray">${list.member_name}</a>
					  </c:forEach>
				</div>
				
				 
				
				
			</section>
			
		</div>
		
	</div>


    <!-- 导入底部信息 -->

    <jsp:include page="../footer.html" flush="true">
       <jsp:param name="" value="" /> 
    </jsp:include>


</html>
