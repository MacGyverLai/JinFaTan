<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table id="order_info" class="table">
  <thead><tr>
    <th>預約日期</th>
    <th>時間區間</th>
    <th>目前人數</th>
  </tr></thead>
  <c:forEach var="orderInfoItem" items="${orderInfoList}">
    <tr>
	  <td><fmt:formatDate value="${orderInfoItem.orderDate}" pattern="yyyy-MM-dd" /></td> 
	  <td>${orderInfoItem.orderPeriod}</td>
	  <td>${orderInfoItem.amount}</td>
	</tr>
  </c:forEach>
</table>


