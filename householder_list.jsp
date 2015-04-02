<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<script type="text/javascript" src="js/jquery-1.7.min.js"></script>

<div id="tab_list">

<% 
java.util.List test = new java.util.ArrayList( 6 ); 
test.add( "Test String 1" ); 
test.add( "Test String 2" ); 
test.add( "Test String 3" ); 
test.add( "Test String 4" ); 
test.add( "Test String 5" ); 
test.add( "Test String 6" ); 
request.setAttribute( "customerList", test ); 
%>

<display:table id="mac_tab" name="customerList" >
</display:table>
 
 <h3>test test</h3>
 
<script type="text/javascript">
	
	$(function() {
		alert("into householder list...");
		//$("#tab_list").load("ajax_HouseHolderList.do");
		
		$("#mac_tab").click(function() {
			alert("mac_tab be clicked...");
		});
	});
</script>

</div>