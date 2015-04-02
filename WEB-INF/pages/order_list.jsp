<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="js/ui.datepicker-zh-TW.js"></script>
<script type="text/javascript" src="js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="js/grid.locale-tw.js"></script>
<script type="text/javascript" src="js/OrderChange.js"></script>

<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<style type="text/css">
	.ui-jqgrid .ui-jqgrid-htable th div {
		height: auto;
	    overflow:hidden;
	    padding-right:4px;
	    padding-top:2px;
	    position:relative;
	    vertical-align:text-top;
	    white-space:normal !important;
	}
	.ui-jqgrid-view { font-size: large; }
	.ui-jqgrid .ui-jqgrid-pager { font-size: large; padding: 1px !important; }
	.ui-jqgrid .ui-pg-input { font-size: large; }
	.ui-jqdialog { display: none; width: 500px; position: absolute; padding: .2em; font-size:large; overflow:visible;}
</style>

<div id="order_grid_area">
	顯示年份：
	<select id="input_order_year">
		<c:forEach var="orderYearItem" items="${orderYearList}">
			<option>${orderYearItem}</option>
		</c:forEach>
	</select>
	<span style="margin-left: 5%;">
		<input type="text" id="order_filter_name" value="" />
	</span>
	<span style="margin-left: 5%;">
		<input type="text" id="order_filter_date" value="未開放" />
	</span>
	<span>
		<button type="button" id="order_filter_button" class="btn">過濾</button> 
	</span>
	<span>
		 <button type="button" id="order_filter_clean_button" class="btn">清除過濾結果</button>
	</span>
	<br />
	<table id="order_grid"></table>
	<div id="order_pager"></div>
</div>

<h4>目前預約情形：</h4>
<div id="order_info_list"></div>
 
<!-- 
<div id="add_order_area">
	<input id="mac123" type="text" />
	<button>新增</button>
</div>
 -->
 
<div id="order_alert" title="注意">
	<p></p>
</div>