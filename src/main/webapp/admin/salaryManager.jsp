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
							<li>
								<a href="./wage/duty.jsp" target="mview">值班统计</a>
							</li>
							<li>
							    <a href="./wage/extrawork.jsp" target="mview">加班统计</a>
							</li>
						</ul>
					</div>
				</div>
			</aside>
			
			<section id="main" class="purchase">
			
				 <iframe name="mview" width="100%" height="100%" frameborder="0" scrolling="no">
				 </iframe>
			</section>
		</div>
	</div>

 	<footer id="footer">
	  	<p>&copy; 2012 WFcsu, Inc. All rights reserved.</p>
    </footer>  
 	
</body>
</html>

