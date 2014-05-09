<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
							    <a id="add" href="addExtraWork.jsp" target="extraWork">增加加班</a><!-- 修改和删除放在查询中 -->
								
							</li>
							<li>
							    <a id="query" href="modifyExtraWork.jsp" target="extraWork">查询加班</a><!-- 修改和删除放在查询中 -->
								
							</li>
							<li>
								<a id="statistics" href="checkExtraWork.jsp" target="extraWork">审核加班</a>
							</li>
							<li>
								<a href="statisticsExtra.jsp" target="extraWork">统计加班</a>
							</li>
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
