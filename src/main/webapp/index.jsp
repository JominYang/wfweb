<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>



<html>
	<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9,chrome=1" />
    <meta name="viewport" content="width=device-width,initial-scale=1,target-densitydpi=device-dpi" />
	<link rel="Shortcut Icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="css/reset.css" />
    <link rel="stylesheet" href="css/header.css" />
    <link rel="stylesheet" href="css/layout.css" />
    <jsp:include page="js/inc.jsp"></jsp:include>
    <jsp:include page="js/address.jsp"></jsp:include>
	<title>WFCSU</title>
  	<link rel="stylesheet" href="css/LoginPage.css">
	<link rel="stylesheet" href="css/Tour.css">
	<link rel="stylesheet" href="css/LoginForm.css">
	
	<style type="text/css">
	#login-submit a:hover{text-decoration: underline;}
	*{margin:0;padding:0;}
	/* focus */
	#focus{width:600px;height:460px;overflow:hidden;position:relative;margin-right: 5px;border: 1px solid #E4E4E4;}
	#focus ul{height:560px;position:absolute;padding: 0;margin: 0;}
	#focus ul li{float:left;width:600px;height:460px;overflow:hidden;position:relative;background:#000;}
	#focus ul li div{position:absolute;overflow:hidden;}
	#focus .btnBg{position:absolute;width:600px;height:20px;left:0;bottom:0;background:#000;}
	#focus .btn{position:absolute;width:580px;height:10px;padding:5px 10px;right:0;bottom:0;text-align:right;}
	#focus .btn span{display:inline-block;_display:inline;_zoom:1;width:25px;height:10px;_font-size:0;margin-left:5px;cursor:pointer;background:#fff;}
	#focus .btn span.on{background:#fff;}
	#focus .preNext{width:45px;height:100px;position:absolute;top:180px;background:url(img/sprite.png) no-repeat 0 0;cursor:pointer;}
	#focus .pre{left:0;}
	#focus .next{right:0;background-position:right top;}
	</style>
	
	</head>
	<script type="text/javascript">
	 sy.ns('atten');
	 $(function(){
		 setup();
	     $("#login_a").click(function(){   
	    	 $("#navbar-form").submit();
          });
	     
	    	
	    	
	var sWidth = $("#focus").width(); //获取焦点图的宽度（显示面积）
	var len = $("#focus ul li").length; //获取焦点图个数
	var index = 0;
	var picTimer;
	
	//以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
	var btn = "<div class='btnBg'></div><div class='btn'>";
	for(var i=0; i < len; i++) {
		btn += "<span></span>";
	}
	btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
	$("#focus").append(btn);
	$("#focus .btnBg").css("opacity",0.5);

	//为小按钮添加鼠标滑入事件，以显示相应的内容
	$("#focus .btn span").css("opacity",0.4).mouseover(function() {
		index = $("#focus .btn span").index(this);
		showPics(index);
	}).eq(0).trigger("mouseover");

	//上一页、下一页按钮透明度处理
	$("#focus .preNext").css("opacity",0.2).hover(function() {
		$(this).stop(true,false).animate({"opacity":"0.5"},300);
	},function() {
		$(this).stop(true,false).animate({"opacity":"0.2"},300);
	});

	//上一页按钮
	$("#focus .pre").click(function() {
		index -= 1;
		if(index == -1) {index = len - 1;}
		showPics(index);
	});

	//下一页按钮
	$("#focus .next").click(function() {
		index += 1;
		if(index == len) {index = 0;}
		showPics(index);
	});

	//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
	$("#focus ul").css("width",sWidth * (len));
	
	//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
	$("#focus").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},4000); //此4000代表自动播放的间隔，单位：毫秒
	}).trigger("mouseleave");
	
	//显示图片函数，根据接收的index值显示相应的内容
	function showPics(index) { //普通切换
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		$("#focus ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
		//$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
		$("#focus .btn span").stop(true,false).animate({"opacity":"0.4"},300).eq(index).stop(true,false).animate({"opacity":"1"},300); //为当前的按钮切换到选中的效果
	}
	     });
	</script>
	<body>
	
	

  		<div class="logo-bar">
			<div id="logo-container">
				<a id="brand"><img src="img/logo.png" /></a>
				<div style="float:right">
				<form id="navbar-form" action="login.do?method=login" method="post">
				<input type="text" placeholder="工号"  id="navbar-input" name="no"/>
				<input type="password" placeholder="密码"  id="navbar-input" name="psw"/>
				<div id="login-submit">
					[<a href="javascript:void(0)" id="login_a">登录</a>]
				</div>
				</form>
				</div>
			</div>
		
      	</div>
  
  
  
		<div id="container-boundingbox" class="wrapper">
      		<div id="container" class="wrapper">
        		<div class="LoginPage">
					<div class="page-header">
    					<h1><b>网服&nbsp;-&nbsp;热忱为您服务</b></h1>
    				</div>
					
						<div id="focus" style="float: left">
							<ul>
							    <li><a target="_blank" href="#"><img alt="" src="img/i1.jpg" /></a></li>
							    <li><a target="_blank" href="#"><img alt="" src="img/i2.jpg" /></a></li>
							    <li><a target="_blank" href="#"><img alt="" src="img/i3.jpg" /></a></li>
							    <li><a target="_blank" href="#"><img alt="" src="img/i4.jpg" /></a></li>
							    <li><a target="_blank" href="#"><img alt="" src="img/i5.jpg" /></a></li>
							</ul>
						</div>
					
	
	
					<div class="column11">
						<div class="LoginForm">
							<div class="callout-box" style="height:430px">
								<div class="reg-header">
									<h3 style="color: #BD1a21"><b>我要报修</b>==></h3>
								</div>
								<div class="signin">
									<form id="attenByClientForm" name="attenByClientForm" action="attendance.do?method=addByClient" method="post">
									<table>
										<tr>
											<td>您的姓名:</td>
											<td colspan="2"><input name="client_name" class="text" type="text" style="margin-left:20px; width: 210px;"/></td>
										</tr>
										<tr>
											<td>联系方式:</td>
											<td colspan="2"><input name="client_phone" class="text" type="text" style="margin-left:20px; width: 210px;"/></td>
										</tr>
										<tr>
											<td>您的地址:</td>
											<td><select name="client_address_a" id="s1"  style="height:30px;width:70px;margin-left:20px;padding-top:6px; vertical-align:middle; font-size: 14px;"></select>
									    <select name="client_address_b" id="s2" style="height:30px;width:70px; font-size: 14px;padding-top:6px;vertical-align:middle;"></select>
									    <input name="client_address_c" type="text" style="width: 50px;font-size: 14px;line-height: 20px;" />房</td>
									    </tr>
									</table>
									<br/>
									<table>
										<tr>
											<td>电脑:
												<select name="computer_type" style="height:30px;width:80px;margin-left:5px;margin-right:40px;vertical-align:middle;padding-top:6px; font-size: 14px;line-height: 30px;">
													<option>笔记本</option>
													<option>台式机</option>
													<option>U盘</option>
													<option>其他</option>
												</select></td>
											<td>系统:
												<select name="system_type" style="height:30px;width:80px;margin-left:5px;font-size: 14px;vertical-align:middle;padding-top:6px;line-height: 30px;">
													<option>XP</option>
													<option>Win7</option>
													<option>Linux</option>
													<option>多系统</option>
													<option>其他</option>
												</select></td>
										</tr>
										<tr><td colspan="2"><textarea placeholder="故障描述（详细点吧~~）" name="problem_describe" rows="4" cols="10" style="width: 300px;font-size: 15px;margin-top: 15px;"></textarea></td></tr>
										<tr><td colspan="2"><input placeholder="备注" name="atten_remark" class="text" type="text" value="" style="width: 300px;margin-bottom: 10px;font-size: 15px;"/></td></tr>
										<tr><td colspan="2"><center><input class="super-emphasize" type="submit" value="马上报修"/></center></td></tr>
									</table>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="shadow wrapper">
  			<img width="1000" src="img/curved-drop-shadow.png" />
		</div>

		<div class="footer wrapper">
			<a class="footer-entry" href="http://wfcsu.com" target="_blank">网服论坛</a>
  			<a class="footer-entry" href="./about.html">关于我们</a>
			<a class="footer-entry" href="./termsofus.html">服务条款</a>
			<a class="footer-entry" href="mailto:enjoylast@gmail.com" target="_blank">问题反馈</a>
			<span class="footer-entry last">&copy; 2012 WFcsu</span>
		</div>
</body>
</html>
