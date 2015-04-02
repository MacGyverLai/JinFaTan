<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="js/JinFaTan.js"></script>

<c:set var="seqNo" value="1" />
<table id="tab_list" class="table">
	<thead><tr>
		<th>No.</th>
		<th>戶長</th>
		<th>住家電話</th>
		<th>聯絡電話一</th>
		<th>聯絡電話二</th>
		<th>縣/市</th>
		<th>地址</th>
	</tr></thead>
	<tbody>
	<c:forEach var="family" items="${familys}">
	<tr class="table_row">
		<td>${seqNo}</td>
		<td>${family.householder.name}</td>
		<td>${family.housePhone}</td>
		<td>${family.phone_1}</td>
		<td>${family.phone_2}</td>
		<td>${family.city} ${family.area}</td>
		<td>${family.address}</td>
		<td style="display: none;">${family.id}</td>
	</tr>
	<c:set var="seqNo">${seqNo + 1}</c:set>
	</c:forEach>
	</tbody>
</table>
<c:set var="pageCount" value="1"/>

<div class="pagination"><ul>
<li class="disabled"><a>&laquo;</a></li>
<c:if test="${startPage > 1}">
	<li><a class="changePage" href="#">1</a></li>
	<li><a>...</a></li>
</c:if>
<c:forEach var="pageCount" begin="${startPage}" end="${endPage}">
	<c:if test="${pageCount != currentPage}"><li><a class="changePage" href="#">${pageCount}</a></li></c:if>
	<c:if test="${pageCount == currentPage}"><li class="active"><a>${pageCount}</a></li></c:if>
</c:forEach>
<c:if test="${pageAmount > endPage}">
	<li><a>...</a></li>
	<li><a class="changePage" href="#">${pageAmount}</a></li>
</c:if>
<li class="disabled"><a>&raquo;</a></li>
</ul></div>

<!-- 
分頁 &lt;
<c:if test="${startPage > 1}"><a class="changePage" href="#">1</a>...</c:if>
<c:forEach var="pageCount" begin="${startPage}" end="${endPage}">
	<c:if test="${pageCount != startPage}">,</c:if>
	<c:if test="${pageCount != currentPage}"><a class="changePage" href="#"></c:if>
	${pageCount}<c:if test="${pageCount != currentPage}"></a></c:if>
</c:forEach>
<c:if test="${pageAmount > endPage}">...<a class="changePage" href="#">${pageAmount}</a></c:if>
 &gt;
 -->