<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<script type="text/javascript" src="js/ui.datepicker-zh-TW.js"></script>
<script type="text/javascript" src="js/PrintView.js"></script>

<style type="text/css">
	#selectable .ui-selecting { background: #FECA40; }
	#selectable .ui-selected { background: #F39814; color: white; }
	#selectable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
	#selectable li { margin: 3px; padding: 0.4em; height: 18px; }
</style>

<div>
	<span>姓名</span>
	<span>排行</span>
	<span>生年</span>
	<span>生月</span>
	<span>生日</span>
	<span>生辰</span>
	<span>生肖</span>
	<span>點亮光明燈</span>
</div>
<form>
<ol id="selectable">
<c:forEach var="member" items="${members}">
<li class="ui-widget-content">
	<span>${member.name}</span>
	<span>${member.order}</span>
	<span>${member.sixtyYear}</span>
	<span style="display: none;">${member.birthYear}</span>
	<span>${member.birthMonth}</span>
	<span>${member.birthDay}</span>
	<span>${member.birthTime}</span>
	<span>${member.animal}</span>
	<c:if test="${member.light}">
		<span>有</span>
	</c:if>
	<c:if test="${not member.light}">
		<span>無</span>
	</c:if>
	<c:if test="${member.taiSui}">
		<span>*** 太歲當頭 ***</span>
	</c:if>
	<span class="memberId" style="display: none;">${member.id}</span>
</li>
</c:forEach>
</ol>
</form>
<span id="select-result" style="display: none;">none</span>
<br />
<span><b>列印格式：</b></span>
<select name="print_format" id="print_format">
	<option value="1">福成宮 - 祭改</option>
	<option value="2">福成宮 - 補運</option>
	<option value="3">進法壇 - 祭改</option>
	<option value="4">進法壇 - 補運</option>
	<option value="5">單戶報表輸出</option>
	<!-- 
	<option value="6">全部報表輸出</option>
	 -->
</select>
<br /><br />
<span><b>祭祀日期：</b></span>
<input type="text" id="performDate" />
<button id="preView_button" class="btn btn-large">列印預覽</button>
<dir id="preview_area">
</dir>


