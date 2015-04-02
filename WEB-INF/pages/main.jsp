<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Jin Fa Tan CRM</title>
		
	<script type="text/javascript" src="js/jquery-1.6.4.min.js"></script>
	<!-- 
	<script type="text/javascript" src="http://www.w3schools.com/jquery/jquery.js"></script>
	 -->
	
	<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
	
	<script src="js/bootstrap.min.js"></script>
	
	<!-- 不用dataTables, 直接用ajax寫
	<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
	 -->
	<script type="text/javascript">
		$(function() {
			$("#menu").tabs();
			$("#title_area span").load("main_SystemInfo.do");
		});
	</script>
	<link type="text/css" href="css/jquery-ui-1.7.2.custom.css" rel="stylesheet" />
	<link type="text/css" href="css/JinFaTan.css" rel="stylesheet" />
	<link type="text/css" href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	
	<!-- 
	<link type="text/css" href="css/demo_page.css" rel="stylesheet" />
	<link type="text/css" href="css/demo_table.css" rel="stylesheet" />
	 -->
	 
	 <style type="text/css">
	 	body {
	 		font-family: verdana;
	 		color: black;
	 		font-size: 16px;
	 		line-height: 18px;
	 	}
	 	button, input {
	 		font-family: verdana;
	 		color: black;
			font-size: 16px;
	 		line-height: 22px;
	 		padding: 0;
	 		border: 0;
	 	}
	 	select,textarea,input[type="text"],input[type="password"],input[type="datetime"],input[type="datetime-local"],input[type="date"],input[type="month"],input[type="time"],input[type="week"],input[type="number"],input[type="email"],input[type="url"],input[type="search"],input[type="tel"],input[type="color"],.uneditable-input { 
	 		display: inline-block;
	 		padding: 4px 6px;
	 		margin-bottom: 10px;
	 		font-size: 16px;
	 		line-height: 22px;
	 		color: black;
	 		-webkit-border-radius: 4px;
	 		-moz-border-radius: 4px;
	 		border-radius: 4px;
	 		vertical-align: middle;
	 	}
	 	select {
	 		font-family: verdana;
	 		color: black;
			font-size: 16px;
	 		line-height: 22px;
	 		padding: 0;
	 		width:220px;
	 		border:1px solid #cccccc;
	 		background-color:#ffffff;
	 	}
	 </style>
</head>
<body>
	<div id="title_area" style="font-size: 150%;">
		進法壇 客戶管理系統
		<span></span>
	</div>
	<div id="menu">
		<ul>
			<c:if test="${Authorise <= 10}">
				<li><a href="main_CustomerList.do">客戶資料</a></li>
				<li><a href="main_CustomerAdd.do">新增客戶</a></li>
				<li><a href="main_CustomerManager.do">管理客戶資料</a></li>
				<li><a href="main_CustomerPrint.do">檢視與列印</a></li>
				<li><a href="main_CustomerOrder.do">預約管理</a></li>
			</c:if>
			<c:if test="${Authorise <= 5}">
				<!-- 
				<li><a href="main_UserList.do">檢視使用者</a></li>
				<li><a href="main_UserAdd.do">新增使用者</a></li>
				 -->
				<li><a href="main_StatisticsReport.do">統計資料表</a></li>
			</c:if>
			<c:if test="${Authorise <= 1}">
				<li><a href="main_SystemParameters.do">系統參數設定</a></li>
				<li><a href="main_UserManager.do">管理使用者</a></li>
			</c:if>
			<!-- marked for Logout should be a button 
			<li><a href="main_Logout.do">登出</a></li>
			 -->
		</ul>
		<div id="menu-1"></div>
	</div>
</body>
</html>
