<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
  <head>
  	  <jsp:include page="../../js/inc.jsp"></jsp:include>
  	  <script type="text/javascript">
  	  	$(function(){
  	  	sy.ns('duty');
	  		duty.statisDutyForm = $('#statisDutyForm').form();
  	  		duty.statisDuty = function() {
  	  		duty.datagrid.datagrid('load',sy.serializeObject(duty.statisDutyForm));
		};
		duty.datagrid =$('#attenAll').datagrid({
			url : sy.bp()+'/admin.do?method=duty',
			pagination : false,
			fit : false,
			fitColumns : false,
			border : true,
			idField : 'date',
			columns : [ [ {
				field : 'date',
				width : 100,
				align : 'center'
			}, {
				field : 'l1',
				width : 40,
				align : 'center'
			}, {
				field : 'l2',
				width : 40,
				align : 'center'
			}, {
				field : 'l3',
				width : 40,
				align : 'center'
			}, {
				field : 'l4',
				width : 40,
				align : 'center'
			}, {
				field : 'l5',
				width : 40,
				align : 'center'
			}, {
				field : 'l6',
				width : 40,
				align : 'center'
			},{
				field : 'l7',
				width : 40,
				align : 'center'
			},{
				field : 'l8',
				width : 40,
				align : 'center'
			},{
				field : 'l9',
				width : 40,
				align : 'center'
			},{
				field : 'l10',
				width : 40,
				align : 'center'
			},{
				field : 'l11',
				width : 40,
				align : 'center'
			},{
				field : 'l12',
				width : 40,
				align : 'center'
			},{
				field : 'l13',
				width : 40,
				align : 'center'
			},{
				field : 'l14',
				width : 40,
				align : 'center'
			},{
				field : 'l15',
				width : 40,
				align : 'center'
			},{
				field : 'l16',
				width : 40,
				align : 'center'
			},{
				field : 'l17',
				width : 40,
				align : 'center'
			},{
				field : 'l18',
				width : 40,
				align : 'center'
			},{
				field : 'l19',
				width : 40,
				align : 'center'
			},{
				field : 'l20',
				width : 40,
				align : 'center'
			},{
				field : 'l21',
				width : 40,
				align : 'center'
			},{
				field : 'l22',
				width : 40,
				align : 'center'
			},{
				field : 'l23',
				width : 40,
				align : 'center'
			},{
				field : 'l24',
				width : 40,
				align : 'center'
			},{
				field : 'l25',
				width : 40,
				align : 'center'
			},{
				field : 'l26',
				width : 40,
				align : 'center'
			},{
				field : 'l27',
				width : 40,
				align : 'center'
			},{
				field : 'l28',
				width : 40,
				align : 'center'
			},{
				field : 'l29',
				width : 40,
				align : 'center'
			},{
				field : 'l30',
				width : 40,
				align : 'center'
			}]]});
  	  	});
  	  	
  	  </script>
  </head>
  
  <body>
  	<div region="north" title="" split="true" style="height: 40px;overflow: hidden;" border="false">
       <form id="statisDutyForm" action="" method="post">
       
         <table>
           <tr>
             <td><b><div style="color: #BD1a21">工资统计:值班</div></b></td>
             
             <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td> 
             <td>输入年月</td>
             <td><input id="yearmonth" name="yearmonth" type="text" style="width: 120px; " value="YYYY-mm" onfocus="if(value=='YYYY-mm'){value='';}" onblur="if(value==''){value='YYYY-mm';}"/></td>
	         <td><input type="button" onclick="duty.statisDuty();"  value="查询" /></td>
	          
	       </tr>
          </table>
       
       </form>
         </div>
        	<div region="center" border="false" style="font-family: 微软雅黑;">
  			<form id="attenAll"></form>
  		</div>
  </body>
</html>
