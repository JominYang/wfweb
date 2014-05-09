<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
</head>


   <!-- 导入头部信息 -->
   <jsp:include page="top.jsp" flush="true">
       <jsp:param name="" value="" /> 
    </jsp:include>


	<div id="page">
		<div class="container" id="page-container" style="height: 540px;">
			
		</div>


       	  <footer id="footer">
				<p>&copy; 2012 WFcsu, Inc. All rights reserved.</p>
		     </footer>  
 	
</body>
</html>

