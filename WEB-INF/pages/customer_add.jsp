<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="js/ui.datepicker-zh-TW.js"></script>
<script type="text/javascript" src="js/CustomerChange.js"></script>

<span>
	<b>生年計算工具: </b>
	<select class="jia_zhi" id="tian_gan">
		<option>甲</option>
		<option>乙</option>
		<option>丙</option>
		<option>丁</option>
		<option>戊</option>
		<option>己</option>
		<option>庚</option>
		<option>辛</option>
		<option>壬</option>
		<option>癸</option>
	</select> 
	<select class="jia_zhi" id="di_zhi">
		<option>子</option>
		<option>丑</option>
		<option>寅</option>
		<option>卯</option>
		<option>辰</option>
		<option>巳</option>
		<option>午</option>
		<option>未</option>
		<option>申</option>
		<option>酉</option>
		<option>戌</option>
		<option>亥</option>
	</select>
</span>
&nbsp;&nbsp;&nbsp;或&nbsp;&nbsp;&nbsp;
<span>
	<b>大約年齡 </b>
	<input type="text" name="age_input" id="age_input" class="animal_select" style="width: 5%;" />
	<b>生肖 </b>
	<select id="animal_select" class="animal_select">
		<option>鼠</option>
		<option>牛</option>
		<option>虎</option>
		<option>兔</option>
		<option>龍</option>
		<option>蛇</option>
		<option>馬</option>
		<option>羊</option>
		<option>猴</option>
		<option>雞</option>
		<option>狗</option>
		<option>豬</option>
	</select>
</span>
&nbsp;&nbsp;&nbsp;民國 <span id="result_years"></span>
<br /><br />
<form id="cuatomer_add_form">
<div id="householder_change" class="householder_change">
	<span>戶長</span><a id="householder_name" /><br />
	<span>住家電話</span><input type="text" name="housePhone" /><br />
	<span>聯絡電話一</span><input type="text" name="phone_1" /><br />
	<span>聯絡電話二</span><input type="text" name="phone_2" /><br />
	<span>縣/市</span>
	<select id="city_select" name="city">
	<c:forEach var="city" items="${cityList}">
		<c:if test="${city == '新北市'}">
			<option selected="selected">${city}</option>
		</c:if>
		<c:if test="${city != '新北市'}">
			<option>${city}</option>
		</c:if>
	</c:forEach>
		<!--
		<option>新北市三芝區</option>
		<option>新北市淡水區</option>
		<option>新北市石門區</option>
		<option>新北市萬里區</option>
		<option>新北市金山區</option>
		<option>新北市板橋區</option>
		<option>新北市汐止區</option>
		<option>新北市深坑區</option>
		<option>新北市石碇區</option>
		<option>新北市瑞芳區</option>
		<option>新北市平溪區</option>
		<option>新北市雙溪區</option>
		<option>新北市貢寮區</option>
		<option>新北市新店區</option>
		<option>新北市坪林區</option>
		<option>新北市烏來區</option>
		<option>新北市永和區</option>
		<option>新北市中和區</option>
		<option>新北市土城區</option>
		<option>新北市三峽區</option>
		<option>新北市樹林區</option>
		<option>新北市鶯歌區</option>
		<option>新北市三重區</option>
		<option>新北市新莊區</option>
		<option>新北市泰山區</option>
		<option>新北市林口區</option>
		<option>新北市蘆洲區</option>
		<option>新北市五股區</option>
		<option>新北市八里區</option>
		<option>基隆市</option>
		<option>台北市</option>
		<option>桃園縣</option>
		<option>新竹市</option>
		<option>新竹縣</option>
		<option>苗栗縣</option>
		<option>台中市</option>
		<option>彰化縣</option>
		<option>南投縣</option>
		<option>雲林縣</option>
		<option>嘉義市</option>
		<option>嘉義縣</option>
		<option>台南市</option>
		<option>高雄市</option>
		<option>屏東縣</option>
		<option>台東縣</option>
		<option>花蓮縣</option>
		<option>宜蘭縣</option>
		<option>澎湖縣</option>
		<option>金門縣</option>
		<option>連江縣</option>
		 -->
	</select>
	<select id="area_select" name="town">
	<c:forEach var="area" items="${areaList}">
		<c:if test="${area == '三芝區'}">
			<option selected="selected">${area}</option>
		</c:if>
		<c:if test="${area != '三芝區'}">
			<option>${area}</option>
		</c:if>
	</c:forEach>
	</select><br />
	<span>地址</span><input style="width: 35%;" type="text" name="address" /><br />
</div>
<br />
<div id="member_change" class="member_change">
	<div>
		<span style="padding-right: 0%">戶長</span>
		<span style="padding-right: 10%">姓名</span>
		<span style="padding-right: 7%">排行</span>
		<span style="padding-right: 5%">生年</span>
		<span style="padding-right: 8%">生月</span>
		<span style="padding-right: 5%">生日</span>
		<span style="padding-right: 7%">生辰</span>
		<span style="padding-right: 0%">光明燈</span>
		<span style="padding-right: 0%">車號</span>
	</div>
	<div class="member_row">
		<input type="radio" name="houseHolder" class="householder" value="0" onchange="radio_change(event)" />
		<input type="text" class="name" name="name" />
		<select class="order" name="order">
		<c:forEach var="order" items="${orderList}">
			<option>${order}</option>
		</c:forEach>
		<!-- 
			<option>信士</option>
			<option>信女</option>
			<option>仝妻</option>
			<option>長男</option>
			<option>長女</option>
			<option>次男</option>
			<option>次女</option>
			<option>三男</option>
			<option>三女</option>
			<option>四男</option>
			<option>四女</option>
			<option>五男</option>
			<option>五女</option>
			<option>父親</option>
			<option>母親</option>
			<option>媳婦</option>
			<option>男孫</option>
			<option>孫女</option>
			<option>岳父</option>
			<option>岳母</option>
			<option>女婿</option>
		 -->
			<option></option>
		</select>
		<input type="text" class="birthYear" name="birthYear" />
		<select class="birthMonth" name="birthMonth">
			<option selected="selected">正</option>
			<option>二</option>
			<option>三</option>
			<option>四</option>
			<option>五</option>
			<option>六</option>
			<option>七</option>
			<option>八</option>
			<option>九</option>
			<option>十</option>
			<option>十一</option>
			<option>十二</option>
			<option>又二</option>
			<option>又三</option>
			<option>又四</option>
			<option>又五</option>
			<option>又六</option>
			<option>又七</option>
			<option>又八</option>
			<option>又九</option>
			<option>又十</option>
			<option>吉</option>
		</select>
		<input type="text" class="birthDay" name="birthDay" value="0" />
		<select class="birth_time" name="birthTime" >
			<option value="子">00 ~ 01 (子)</option>
			<option value="丑">01 ~ 03 (丑)</option>
			<option value="寅">03 ~ 05 (寅)</option>
			<option value="卯">05 ~ 07 (卯)</option>
			<option value="辰">07 ~ 09 (辰)</option>
			<option value="巳">09 ~ 11 (巳)</option>
			<option value="午">11 ~ 13 (午)</option>
			<option value="未">13 ~ 15 (未)</option>
			<option value="申">15 ~ 17 (申)</option>
			<option value="酉">17 ~ 19 (酉)</option>
			<option value="戌">19 ~ 21 (戌)</option>
			<option value="亥">21 ~ 23 (亥)</option>
			<option value="夜子">23 ~ 00 (夜子)</option>
			<option selected="selected">吉</option>
		</select>
		<input type="checkbox" class="light" name="light" onchange="light_change(event)" />
		<input type="text" class="asset" name="asset" value="" />
		<span class="remove_member_row badge badge-important">-</span>
	</div>
</div>
<span>
	<span id="add_member_row" class="badge badge-success">+</span>
</span>
<br /><br /><br />
<div id="button_area">
	<button class="btn btn-large" type="button" id="add_button">送出</button>
	<input class="btn btn-large" type="reset" id="clear_button" onclick="clear_click()" />
</div>
</form>

<div id="customer_alert" title="警告">
	<p></p>
</div>