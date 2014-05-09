<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
	    <meta http-equiv="X-UA-Compatible" content="IE=9,chrome=1" />
	    <meta name="viewport" content="width=device-width,initial-scale=1,target-densitydpi=device-dpi" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" />
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css" />
		<title>WFCSU</title>
	  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoginPage.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Tour.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoginForm.css">
		<style type="text/css">
			#container a{
				font-size: 18px;
				color: black;
			}
			#container a:HOVER {
				text-decoration: underline;
			}
		</style>
	</head>
	<body>
  		<div class="logo-bar">
			<div id="logo-container">
				<a id="brand"><img src="${pageContext.request.contextPath}/img/logo.png" /></a>
			</div>
      	</div>
  
  
  
		<div id="container-boundingbox" class="wrapper">
			
      		<div id="container" class="wrapper" style="height: 480px;">
      			<div style="height: 80px;"></div>
      			<div style="width: 600px;height: 300px;margin:auto; border: 2px dotted red;">
      				<div style="height: 60px;"></div>
      				<div align="center" style="width: 300px;margin: auto;height: 30px;font-size:18px;font-family: 微软雅黑;">
      					<c:if test="${error!=null }">
      					<div style="height: 40px;">对不起！系统出现错误！</div>
      					<div style="height: 80px;color: red;">${error}</div></c:if>
      					<c:if test="${msg!=null }">
      					<div style="height: 40px;">*************************</div>
      					<div style="height: 80px;color: red;">${msg}</div></c:if>
      					<c:if test="${msg==null&&error==null }">
      					<div style="height: 40px;">对不起！系统出现错误！</div>
      					<div style="height: 80px;color: red;">您访问的页面不存在！</div></c:if>
      					<br>
      					<a href="${pageContext.request.contextPath}/index.jsp">返回WFcsu首页</a>
      				</div>
      			</div>
        		
			</div>
		</div>

		<div class="shadow wrapper">
  			<img width="1000" src="${pageContext.request.contextPath}/img/curved-drop-shadow.png" />
		</div>

		<div class="footer wrapper">
			<a class="footer-entry" href="${pageContext.request.contextPath}/index.jsp">回到首页</a>
  			<a class="footer-entry" href="${pageContext.request.contextPath}/about.html">关于我们</a>
			<a class="footer-entry" href="${pageContext.request.contextPath}/termsofus.html">服务条款</a>
			<span class="footer-entry last">&copy; 2012 WFcsu</span>
		</div>
</body>
</html>
