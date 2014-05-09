<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>WFcsu</title>
<link type="text/css" rel="stylesheet" href="../css/main.css" />
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
								<a id="add" href="addExtraWork.jsp" target="extraWork">登记加班</a>
								
							</li>
							<h3></h3>
							<li>
							    <a id="query" href="modifyExtraWork.jsp" target="extraWork">登记加班（未审核）</a>
							</li>
							
							<li>
							    <a id="query" href="notpassExtrawork.jsp" target="extraWork">登记加班（未通过）</a>
							</li>
							<h3></h3>
							<li>
							    <a id="query" href="myExtrawork.jsp" target="extraWork">我的加班</a>
							</li>
							<h3></h3>
						</ul>
					</div>
				
				</div>
			</aside>
			
			<section id="main" class="purchase">
				
				 <iframe name="extraWork" width="100%" height="100%" frameborder="0" scrolling="no" src="./extraworkMain.jsp">
				 </iframe>
			</section>
			
		</div>
		
	</div>


    <!-- 导入底部信息 -->

    <jsp:include page="../footer.html" flush="true">
       <jsp:param name="" value="" /> 
    </jsp:include>


</html>
