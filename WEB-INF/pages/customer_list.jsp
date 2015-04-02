<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>Customer List</title>
</head>
<body>


</body>
</html>
 -->

<div id="search_area">
	<c:if test="${searchValue != null}">
		<input type="text" id="search_value" value=${searchValue} />
	</c:if>
	<c:if test="${searchValue == null}">
		<input type="text" id="search_value" value="" />
	</c:if>
	<select id="search_item">
		<c:if test="${searchItem == 'name'}">
			<option value="name" selected="selected">姓名</option>
		</c:if>
		<c:if test="${searchItem != 'name'}">
			<option value="name">姓名</option>
		</c:if>
		<c:if test="${searchItem == 'phone'}">
			<option value="phone" selected="selected">電話</option>
		</c:if>
		<c:if test="${searchItem != 'phone'}">
			<option value="phone">電話</option>
		</c:if>
		<c:if test="${searchItem == 'address'}">
			<option value="address" selected="selected">地址</option>
		</c:if>
		<c:if test="${searchItem != 'address'}">
			<option value="address">地址</option>
		</c:if>
	</select>
	<span>
		<button class="btn" type="button" id="search_button" onclick="search_button()">搜尋</button> 
	</span>
	<span>
		<button class="btn" type="button" id="reset_button" onclick="reset_button()">清除搜尋結果</button>
	</span>
</div>
<br />
<div id="display_area">
	<span id="householder_list">
	 	<jsp:include page="householder_list.jsp" flush="true"/>
	</span>
	<br /><br />
	<span id="member_list">
	</span>
</div>
<br />
<div id="modify_area" style="display: none;">

</div>
<div id="manager_area">
	<button class="btn btn-large" type="button" id="delete_button" disabled="disabled">刪除</button>
</div>

<div id="customer_alert_l" title="警告">
	<p></p>
</div>
<div id="customer_confirm" title="注意">
	<p>確定要刪除此成員？</p>
</div>