<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<display:table id="member_list" name="memberList">
	<display:column title="姓名" property="name"></display:column>
	<display:column title="排行" property="order"></display:column>
	<display:column title="生年" property="sixtyYear"></display:column>
	<display:column title="民國" property="birthYear"></display:column>
	<display:column title="生月" property="birthMonth"></display:column>
	<display:column title="生日" property="birthDay"></display:column>
	<display:column title="生辰" property="birthTime"></display:column>
	<display:column title="生肖" property="animal"></display:column>
	<display:column title="光明燈" style="width: 10%;">
		<c:if test="${member_list.light}">
			<span>有</span>
		</c:if>
		<c:if test="${not member_list.light}">
			<span>無</span>
		</c:if>
	</display:column>
	<display:column title="年齡" property="years"></display:column>
	<display:column title="車號" property="asset" style="width: 15%;"></display:column>
	<display:column title="備註" style="width: 12%;">
		<c:if test="${member_list.taiSui}">
			<span>*太歲當頭*</span>
		</c:if>
	</display:column>
	<display:column style="display: none;">${member_list.id}</display:column>
</display:table>


