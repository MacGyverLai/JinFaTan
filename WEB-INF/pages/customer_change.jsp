<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="js/ui.datepicker-zh-TW.js"></script>
<script type="text/javascript" src="js/CustomerChange.js"></script>

<form id="cuatomer_modify_form">
<div id="householder_change_h" class="householder_change">
	<span>戶長</span><a id="householder_name_h">${family.householder.name}</a><br />
	<span>住家電話</span><input type="text" id="housePhone_h" name="housePhone" value="${family.housePhone}" /><br />
	<span>聯絡電話一</span><input type="text" id="phone_1_h" name="phone_1" value="${family.phone_1}" /><br />
	<span>聯絡電話二</span><input type="text" id="phone_2_h" name="phone_2" value="${family.phone_2}" /><br />
	<span>縣/市</span>
	<select id="city_select_h" name="city">
	<c:forEach var="city" items="${cityList}">
		<c:if test="${city == family.city}">
			<option selected="selected">${city}</option>
		</c:if>
		<c:if test="${city != family.city}">
			<option>${city}</option>
		</c:if>
	</c:forEach>
		<!-- 
		<c:if test="${family.city == '新北市三芝區'}">
			<option selected="selected">新北市三芝區</option>
		</c:if>
		<c:if test="${family.city != '新北市三芝區'}">
			<option>新北市三芝區</option>
		</c:if>
		<c:if test="${family.city == '新北市淡水區'}">
			<option selected="selected">新北市淡水區</option>
		</c:if>
		<c:if test="${family.city != '新北市淡水區'}">
			<option>新北市淡水區</option>
		</c:if>
		<c:if test="${family.city == '新北市石門區'}">
			<option selected="selected">新北市石門區</option>
		</c:if>
		<c:if test="${family.city != '新北市石門區'}">
			<option>新北市石門區</option>
		</c:if>
		<c:if test="${family.city == '新北市萬里區'}">
			<option selected="selected">新北市萬里區</option>
		</c:if>
		<c:if test="${family.city != '新北市萬里區'}">
			<option>新北市萬里區</option>
		</c:if>
		<c:if test="${family.city == '新北市金山區'}">
			<option selected="selected">新北市金山區</option>
		</c:if>
		<c:if test="${family.city != '新北市金山區'}">
			<option>新北市金山區</option>
		</c:if>
		<c:if test="${family.city == '新北市板橋區'}">
			<option selected="selected">新北市板橋區</option>
		</c:if>
		<c:if test="${family.city != '新北市板橋區'}">
			<option>新北市板橋區</option>
		</c:if>
		<c:if test="${family.city == '新北市汐止區'}">
			<option selected="selected">新北市汐止區</option>
		</c:if>
		<c:if test="${family.city != '新北市汐止區'}">
			<option>新北市汐止區</option>
		</c:if>
		<c:if test="${family.city == '新北市深坑區'}">
			<option selected="selected">新北市深坑區</option>
		</c:if>
		<c:if test="${family.city != '新北市深坑區'}">
			<option>新北市深坑區</option>
		</c:if>
		<c:if test="${family.city == '新北市石碇區'}">
			<option selected="selected">新北市石碇區</option>
		</c:if>
		<c:if test="${family.city != '新北市石碇區'}">
			<option>新北市石碇區</option>
		</c:if>
		<c:if test="${family.city == '新北市瑞芳區'}">
			<option selected="selected">新北市瑞芳區</option>
		</c:if>
		<c:if test="${family.city != '新北市瑞芳區'}">
			<option>新北市瑞芳區</option>
		</c:if>
		<c:if test="${family.city == '新北市平溪區'}">
			<option selected="selected">新北市平溪區</option>
		</c:if>
		<c:if test="${family.city != '新北市平溪區'}">
			<option>新北市平溪區</option>
		</c:if>
		<c:if test="${family.city == '新北市雙溪區'}">
			<option selected="selected">新北市雙溪區</option>
		</c:if>
		<c:if test="${family.city != '新北市雙溪區'}">
			<option>新北市雙溪區</option>
		</c:if>
		<c:if test="${family.city == '新北市貢寮區'}">
			<option selected="selected">新北市貢寮區</option>
		</c:if>
		<c:if test="${family.city != '新北市貢寮區'}">
			<option>新北市貢寮區</option>
		</c:if>
		<c:if test="${family.city == '新北市新店區'}">
			<option selected="selected">新北市新店區</option>
		</c:if>
		<c:if test="${family.city != '新北市新店區'}">
			<option>新北市新店區</option>
		</c:if>
		<c:if test="${family.city == '新北市坪林區'}">
			<option selected="selected">新北市坪林區</option>
		</c:if>
		<c:if test="${family.city != '新北市坪林區'}">
			<option>新北市坪林區</option>
		</c:if>
		<c:if test="${family.city == '新北市烏來區'}">
			<option selected="selected">新北市烏來區</option>
		</c:if>
		<c:if test="${family.city != '新北市烏來區'}">
			<option>新北市烏來區</option>
		</c:if>
		<c:if test="${family.city == '新北市永和區'}">
			<option selected="selected">新北市永和區</option>
		</c:if>
		<c:if test="${family.city != '新北市永和區'}">
			<option>新北市永和區</option>
		</c:if>
		<c:if test="${family.city == '新北市中和區'}">
			<option selected="selected">新北市中和區</option>
		</c:if>
		<c:if test="${family.city != '新北市中和區'}">
			<option>新北市中和區</option>
		</c:if>
		<c:if test="${family.city == '新北市土城區'}">
			<option selected="selected">新北市土城區</option>
		</c:if>
		<c:if test="${family.city != '新北市土城區'}">
			<option>新北市土城區</option>
		</c:if>
		<c:if test="${family.city == '新北市三峽區'}">
			<option selected="selected">新北市三峽區</option>
		</c:if>
		<c:if test="${family.city != '新北市三峽區'}">
			<option>新北市三峽區</option>
		</c:if>
		<c:if test="${family.city == '新北市樹林區'}">
			<option selected="selected">新北市樹林區</option>
		</c:if>
		<c:if test="${family.city != '新北市樹林區'}">
			<option>新北市樹林區</option>
		</c:if>
		<c:if test="${family.city == '新北市鶯歌區'}">
			<option selected="selected">新北市鶯歌區</option>
		</c:if>
		<c:if test="${family.city != '新北市鶯歌區'}">
			<option>新北市鶯歌區</option>
		</c:if>
		<c:if test="${family.city == '新北市三重區'}">
			<option selected="selected">新北市三重區</option>
		</c:if>
		<c:if test="${family.city != '新北市三重區'}">
			<option>新北市三重區</option>
		</c:if>
		<c:if test="${family.city == '新北市新莊區'}">
			<option selected="selected">新北市新莊區</option>
		</c:if>
		<c:if test="${family.city != '新北市新莊區'}">
			<option>新北市新莊區</option>
		</c:if>
		<c:if test="${family.city == '新北市泰山區'}">
			<option selected="selected">新北市泰山區</option>
		</c:if>
		<c:if test="${family.city != '新北市泰山區'}">
			<option>新北市泰山區</option>
		</c:if>
		<c:if test="${family.city == '新北市林口區'}">
			<option selected="selected">新北市林口區</option>
		</c:if>
		<c:if test="${family.city != '新北市林口區'}">
			<option>新北市林口區</option>
		</c:if>
		<c:if test="${family.city == '新北市蘆洲區'}">
			<option selected="selected">新北市蘆洲區</option>
		</c:if>
		<c:if test="${family.city != '新北市蘆洲區'}">
			<option>新北市蘆洲區</option>
		</c:if>
		<c:if test="${family.city == '新北市五股區'}">
			<option selected="selected">新北市五股區</option>
		</c:if>
		<c:if test="${family.city != '新北市五股區'}">
			<option>新北市五股區</option>
		</c:if>
		<c:if test="${family.city == '新北市八里區'}">
			<option selected="selected">新北市八里區</option>
		</c:if>
		<c:if test="${family.city != '新北市八里區'}">
			<option>新北市八里區</option>
		</c:if>
		<c:if test="${family.city == '基隆市'}">
			<option selected="selected">基隆市</option>
		</c:if>
		<c:if test="${family.city != '基隆市'}">
			<option>基隆市</option>
		</c:if>
		<c:if test="${family.city == '台北市'}">
			<option selected="selected">台北市</option>
		</c:if>
		<c:if test="${family.city != '台北市'}">
			<option>台北市</option>
		</c:if>
		<c:if test="${family.city == '桃園縣'}">
			<option selected="selected">桃園縣</option>
		</c:if>
		<c:if test="${family.city != '桃園縣'}">
			<option>桃園縣</option>
		</c:if>
		<c:if test="${family.city == '新竹市'}">
			<option selected="selected">新竹市</option>
		</c:if>
		<c:if test="${family.city != '新竹市'}">
			<option>新竹市</option>
		</c:if>
		<c:if test="${family.city == '新竹縣'}">
			<option selected="selected">新竹縣</option>
		</c:if>
		<c:if test="${family.city != '新竹縣'}">
			<option>新竹縣</option>
		</c:if>
		<c:if test="${family.city == '苗栗縣'}">
			<option selected="selected">苗栗縣</option>
		</c:if>
		<c:if test="${family.city != '苗栗縣'}">
			<option>苗栗縣</option>
		</c:if>
		<c:if test="${family.city == '台中市'}">
			<option selected="selected">台中市</option>
		</c:if>
		<c:if test="${family.city != '台中市'}">
			<option>台中市</option>
		</c:if>
		<c:if test="${family.city == '彰化縣'}">
			<option selected="selected">彰化縣</option>
		</c:if>
		<c:if test="${family.city != '彰化縣'}">
			<option>彰化縣</option>
		</c:if>
		<c:if test="${family.city == '南投縣'}">
			<option selected="selected">南投縣</option>
		</c:if>
		<c:if test="${family.city != '南投縣'}">
			<option>南投縣</option>
		</c:if>
		<c:if test="${family.city == '雲林縣'}">
			<option selected="selected">雲林縣</option>
		</c:if>
		<c:if test="${family.city != '雲林縣'}">
			<option>雲林縣</option>
		</c:if>
		<c:if test="${family.city == '嘉義市'}">
			<option selected="selected">嘉義市</option>
		</c:if>
		<c:if test="${family.city != '嘉義市'}">
			<option>嘉義市</option>
		</c:if>
		<c:if test="${family.city == '嘉義縣'}">
			<option selected="selected">嘉義縣</option>
		</c:if>
		<c:if test="${family.city != '嘉義縣'}">
			<option>嘉義縣</option>
		</c:if>
		<c:if test="${family.city == '台南市'}">
			<option selected="selected">台南市</option>
		</c:if>
		<c:if test="${family.city != '台南市'}">
			<option>台南市</option>
		</c:if>
		<c:if test="${family.city == '高雄市'}">
			<option selected="selected">高雄市</option>
		</c:if>
		<c:if test="${family.city != '高雄市'}">
			<option>高雄市</option>
		</c:if>
		<c:if test="${family.city == '屏東縣'}">
			<option selected="selected">屏東縣</option>
		</c:if>
		<c:if test="${family.city != '屏東縣'}">
			<option>屏東縣</option>
		</c:if>
		<c:if test="${family.city == '台東縣'}">
			<option selected="selected">台東縣</option>
		</c:if>
		<c:if test="${family.city != '台東縣'}">
			<option>台東縣</option>
		</c:if>
		<c:if test="${family.city == '花蓮縣'}">
			<option selected="selected">花蓮縣</option>
		</c:if>
		<c:if test="${family.city != '花蓮縣'}">
			<option>花蓮縣</option>
		</c:if>
		<c:if test="${family.city == '宜蘭縣'}">
			<option selected="selected">宜蘭縣</option>
		</c:if>
		<c:if test="${family.city != '宜蘭縣'}">
			<option>宜蘭縣</option>
		</c:if>
		<c:if test="${family.city == '澎湖縣'}">
			<option selected="selected">澎湖縣</option>
		</c:if>
		<c:if test="${family.city != '澎湖縣'}">
			<option>澎湖縣</option>
		</c:if>
		<c:if test="${family.city == '金門縣'}">
			<option selected="selected">金門縣</option>
		</c:if>
		<c:if test="${family.city != '金門縣'}">
			<option>金門縣</option>
		</c:if>
		<c:if test="${family.city == '連江縣'}">
			<option selected="selected">連江縣</option>
		</c:if>
		<c:if test="${family.city != '連江縣'}">
			<option>連江縣</option>
		</c:if>
		 -->
	</select>
	<select id="area_select_h" name="town">
	<c:forEach var="area" items="${areaList}">
		<c:if test="${area == family.area}">
			<option selected="selected">${area}</option>
		</c:if>
		<c:if test="${area != family.area}">
			<option>${area}</option>
		</c:if>
	</c:forEach>
	</select><br />
	<span>地址</span><input type="text" id="address_h" style="width: 35%;" name="address" value="${family.address}" /><br />
	<input type="text" style="display: none;" id="family_Id_h" name="family_Id" value="${family.id}" />
</div>
<br />
<div id="member_change_h" class="member_change">
	<div>
		<span style="padding-left: 4%; padding-right: 0%">戶長</span>
		<span style="padding-right: 12%">姓名</span>
		<span style="padding-right: 7%">排行</span>
		<span style="padding-right: 5%">生年</span>
		<span style="padding-right: 8%">生月</span>
		<span style="padding-right: 5%">生日</span>
		<span style="padding-right: 6%">生辰</span>
		<span style="padding-right: 0%">光明燈</span>
		<span style="padding-right: 0%">車號</span>
	</div>
	<c:set var="memberNo" value="0" />
	<ul id="sortable" style="list-style: url('css/images/oie_8333170qoXKiV5.png') none inside;">
	<c:forEach var="member" items="${members}">
		<li class="member_row_h">
			<c:if test="${member.name == family.householder.name}">
				<input type="radio" name="houseHolder" class="householder_h" checked="checked"  value="${memberNo}" />
			</c:if>
			<c:if test="${member.name != family.householder.name}">
				<input type="radio" name="houseHolder" class="householder_h" value="${memberNo}" />
			</c:if>
			<input type="text" class="name_h" name="name" value="${member.name}" />
			<select class="order" name="order">
			<c:forEach var="order" items="${orderList}">
				<c:if test="${member.order == order}">
					<option selected="selected">${order}</option>
				</c:if>
				<c:if test="${member.order != order}">
					<option>${order}</option>
				</c:if>
			</c:forEach>
				<!-- 
				<c:if test="${member.order == '信士'}">
					<option selected="selected">信士</option>
				</c:if>
				<c:if test="${member.order != '信士'}">
					<option>信士</option>
				</c:if>
				<c:if test="${member.order == '信女'}">
					<option selected="selected">信女</option>
				</c:if>
				<c:if test="${member.order != '信女'}">
					<option>信女</option>
				</c:if>
				<c:if test="${member.order == '仝妻'}">
					<option selected="selected">仝妻</option>
				</c:if>
				<c:if test="${member.order != '仝妻'}">
					<option>仝妻</option>
				</c:if>
				<c:if test="${member.order == '長男'}">
					<option selected="selected">長男</option>
				</c:if>
				<c:if test="${member.order != '長男'}">
					<option>長男</option>
				</c:if>
				<c:if test="${member.order == '長女'}">
					<option selected="selected">長女</option>
				</c:if>
				<c:if test="${member.order != '長女'}">
					<option>長女</option>
				</c:if>
				<c:if test="${member.order == '次男'}">
					<option selected="selected">次男</option>
				</c:if>
				<c:if test="${member.order != '次男'}">
					<option>次男</option>
				</c:if>
				<c:if test="${member.order == '次女'}">
					<option selected="selected">次女</option>
				</c:if>
				<c:if test="${member.order != '次女'}">
					<option>次女</option>
				</c:if>
				<c:if test="${member.order == '三男'}">
					<option selected="selected">三男</option>
				</c:if>
				<c:if test="${member.order != '三男'}">
					<option>三男</option>
				</c:if>
				<c:if test="${member.order == '三女'}">
					<option selected="selected">三女</option>
				</c:if>
				<c:if test="${member.order != '三女'}">
					<option>三女</option>
				</c:if>
				<c:if test="${member.order == '四男'}">
					<option selected="selected">四男</option>
				</c:if>
				<c:if test="${member.order != '四男'}">
					<option>四男</option>
				</c:if>
				<c:if test="${member.order == '四女'}">
					<option selected="selected">四女</option>
				</c:if>
				<c:if test="${member.order != '四女'}">
					<option>四女</option>
				</c:if>
				<c:if test="${member.order == '五男'}">
					<option selected="selected">五男</option>
				</c:if>
				<c:if test="${member.order != '五男'}">
					<option>五男</option>
				</c:if>
				<c:if test="${member.order == '五女'}">
					<option selected="selected">五女</option>
				</c:if>
				<c:if test="${member.order != '五女'}">
					<option>五女</option>
				</c:if>
				<c:if test="${member.order == '父親'}">
					<option selected="selected">父親</option>
				</c:if>
				<c:if test="${member.order != '父親'}">
					<option>父親</option>
				</c:if>
				<c:if test="${member.order == '母親'}">
					<option selected="selected">母親</option>
				</c:if>
				<c:if test="${member.order != '母親'}">
					<option>母親</option>
				</c:if>
				<c:if test="${member.order == '媳婦'}">
					<option selected="selected">媳婦</option>
				</c:if>
				<c:if test="${member.order != '媳婦'}">
					<option>媳婦</option>
				</c:if>
				<c:if test="${member.order == '男孫'}">
					<option selected="selected">男孫</option>
				</c:if>
				<c:if test="${member.order != '男孫'}">
					<option>男孫</option>
				</c:if>
				<c:if test="${member.order == '孫女'}">
					<option selected="selected">孫女</option>
				</c:if>
				<c:if test="${member.order != '孫女'}">
					<option>孫女</option>
				</c:if>
				<c:if test="${member.order == '岳父'}">
					<option selected="selected">岳父</option>
				</c:if>
				<c:if test="${member.order != '岳父'}">
					<option>岳父</option>
				</c:if>
				<c:if test="${member.order == '岳母'}">
					<option selected="selected">岳母</option>
				</c:if>
				<c:if test="${member.order != '岳母'}">
					<option>岳母</option>
				</c:if>
				<c:if test="${member.order == '女婿'}">
					<option selected="selected">女婿</option>
				</c:if>
				<c:if test="${member.order != '女婿'}">
					<option>女婿</option>
				</c:if>
				 -->
				<c:if test="${member.order == ''}">
					<option selected="selected"></option>
				</c:if>
				<c:if test="${member.order != ''}">
					<option>  </option>
				</c:if>
			</select>
			<input type="text" class="birthYear" name="birthYear" value="${member.birthYear}" />
			<select class="birthMonth" name="birthMonth">
				<c:if test="${member.birthMonth == '正'}">
					<option selected="selected">正</option>
				</c:if>
				<c:if test="${member.birthMonth != '正'}">
					<option>正</option>
				</c:if>
				<c:if test="${member.birthMonth == '二'}">
					<option selected="selected">二</option>
				</c:if>
				<c:if test="${member.birthMonth != '二'}">
					<option>二</option>
				</c:if>
				<c:if test="${member.birthMonth == '三'}">
					<option selected="selected">三</option>
				</c:if>
				<c:if test="${member.birthMonth != '三'}">
					<option>三</option>
				</c:if>
				<c:if test="${member.birthMonth == '四'}">
					<option selected="selected">四</option>
				</c:if>
				<c:if test="${member.birthMonth != '四'}">
					<option>四</option>
				</c:if>
				<c:if test="${member.birthMonth == '五'}">
					<option selected="selected">五</option>
				</c:if>
				<c:if test="${member.birthMonth != '五'}">
					<option>五</option>
				</c:if>
				<c:if test="${member.birthMonth == '六'}">
					<option selected="selected">六</option>
				</c:if>
				<c:if test="${member.birthMonth != '六'}">
					<option>六</option>
				</c:if>
				<c:if test="${member.birthMonth == '七'}">
					<option selected="selected">七</option>
				</c:if>
				<c:if test="${member.birthMonth != '七'}">
					<option>七</option>
				</c:if>
				<c:if test="${member.birthMonth == '八'}">
					<option selected="selected">八</option>
				</c:if>
				<c:if test="${member.birthMonth != '八'}">
					<option>八</option>
				</c:if>
				<c:if test="${member.birthMonth == '九'}">
					<option selected="selected">九</option>
				</c:if>
				<c:if test="${member.birthMonth != '九'}">
					<option>九</option>
				</c:if>
				<c:if test="${member.birthMonth == '十'}">
					<option selected="selected">十</option>
				</c:if>
				<c:if test="${member.birthMonth != '十'}">
					<option>十</option>
				</c:if>
				<c:if test="${member.birthMonth == '十一'}">
					<option selected="selected">十一</option>
				</c:if>
				<c:if test="${member.birthMonth != '十一'}">
					<option>十一</option>
				</c:if>
				<c:if test="${member.birthMonth == '十二'}">
					<option selected="selected">十二</option>
				</c:if>
				<c:if test="${member.birthMonth != '十二'}">
					<option>十二</option>
				</c:if>
				<c:if test="${member.birthMonth == '又二'}">
					<option selected="selected">又二</option>
				</c:if>
				<c:if test="${member.birthMonth != '又二'}">
					<option>又二</option>
				</c:if>
				<c:if test="${member.birthMonth == '又三'}">
					<option selected="selected">又三</option>
				</c:if>
				<c:if test="${member.birthMonth != '又三'}">
					<option>又三</option>
				</c:if>
				<c:if test="${member.birthMonth == '又四'}">
					<option selected="selected">又四</option>
				</c:if>
				<c:if test="${member.birthMonth != '又四'}">
					<option>又四</option>
				</c:if>
				<c:if test="${member.birthMonth == '又五'}">
					<option selected="selected">又五</option>
				</c:if>
				<c:if test="${member.birthMonth != '又五'}">
					<option>又五</option>
				</c:if>
				<c:if test="${member.birthMonth == '又六'}">
					<option selected="selected">又六</option>
				</c:if>
				<c:if test="${member.birthMonth != '又六'}">
					<option>又六</option>
				</c:if>
				<c:if test="${member.birthMonth == '又七'}">
					<option selected="selected">又七</option>
				</c:if>
				<c:if test="${member.birthMonth != '又七'}">
					<option>又七</option>
				</c:if>
				<c:if test="${member.birthMonth == '又八'}">
					<option selected="selected">又八</option>
				</c:if>
				<c:if test="${member.birthMonth != '又八'}">
					<option>又八</option>
				</c:if>
				<c:if test="${member.birthMonth == '又九'}">
					<option selected="selected">又九</option>
				</c:if>
				<c:if test="${member.birthMonth != '又九'}">
					<option>又九</option>
				</c:if>
				<c:if test="${member.birthMonth == '又十'}">
					<option selected="selected">又十</option>
				</c:if>
				<c:if test="${member.birthMonth != '又十'}">
					<option>又十</option>
				</c:if>
				<c:if test="${member.birthMonth == '吉'}">
					<option selected="selected">吉</option>
				</c:if>
				<c:if test="${member.birthMonth != '吉'}">
					<option>吉</option>
				</c:if>
			</select>
			<input type="text" class="birthDay" name="birthDay" value="${member.birthDay}" />
			<select class="birth_time" name="birthTime">
				<c:if test="${member.birthTime == '子'}">
					<option value="子" selected="selected">00 ~ 01 (子)</option>
				</c:if>
				<c:if test="${member.birthTime != '子'}">
					<option value="子">00 ~ 01 (子)</option>
				</c:if>
				<c:if test="${member.birthTime == '丑'}">
					<option value="丑" selected="selected">01 ~ 03 (丑)</option>
				</c:if>
				<c:if test="${member.birthTime != '丑'}">
					<option value="丑">01 ~ 03 (丑)</option>
				</c:if>
				<c:if test="${member.birthTime == '寅'}">
					<option value="寅" selected="selected">03 ~ 05 (寅)</option>
				</c:if>
				<c:if test="${member.birthTime != '寅'}">
					<option value="寅">03 ~ 05 (寅)</option>
				</c:if>
				<c:if test="${member.birthTime == '卯'}">
					<option value="卯" selected="selected">05 ~ 07 (卯)</option>
				</c:if>
				<c:if test="${member.birthTime != '卯'}">
					<option value="卯">05 ~ 07 (卯)</option>
				</c:if>
				<c:if test="${member.birthTime == '辰'}">
					<option value="辰" selected="selected">07 ~ 09 (辰)</option>
				</c:if>
				<c:if test="${member.birthTime != '辰'}">
					<option value="辰">07 ~ 09 (辰)</option>
				</c:if>
				<c:if test="${member.birthTime == '巳'}">
					<option value="巳" selected="selected">09 ~ 11 (巳)</option>
				</c:if>
				<c:if test="${member.birthTime != '巳'}">
					<option value="巳">09 ~ 11 (巳)</option>
				</c:if>
				<c:if test="${member.birthTime == '午'}">
					<option value="午" selected="selected">11 ~ 13 (午)</option>
				</c:if>
				<c:if test="${member.birthTime != '午'}">
					<option value="午">11 ~ 13 (午)</option>
				</c:if>
				<c:if test="${member.birthTime == '未'}">
					<option value="未" selected="selected">13 ~ 15 (未)</option>
				</c:if>
				<c:if test="${member.birthTime != '未'}">
					<option value="未">13 ~ 15 (未)</option>
				</c:if>
				<c:if test="${member.birthTime == '申'}">
					<option value="申" selected="selected">15 ~ 17 (申)</option>
				</c:if>
				<c:if test="${member.birthTime != '申'}">
					<option value="申">15 ~ 17 (申)</option>
				</c:if>
				<c:if test="${member.birthTime == '酉'}">
					<option value="酉" selected="selected">17 ~ 19 (酉)</option>
				</c:if>
				<c:if test="${member.birthTime != '酉'}">
					<option value="酉">17 ~ 19 (酉)</option>
				</c:if>
				<c:if test="${member.birthTime == '戌'}">
					<option value="戌" selected="selected">19 ~ 21 (戌)</option>
				</c:if>
				<c:if test="${member.birthTime != '戌'}">
					<option value="戌">19 ~ 21 (戌)</option>
				</c:if>
				<c:if test="${member.birthTime == '亥'}">
					<option value="亥" selected="selected">21 ~ 23 (亥)</option>
				</c:if>
				<c:if test="${member.birthTime != '亥'}">
					<option value="亥">21 ~ 23 (亥)</option>
				</c:if>
				<c:if test="${member.birthTime == '夜子'}">
					<option value="夜子" selected="selected">23 ~ 00 (夜子)</option>
				</c:if>
				<c:if test="${member.birthTime != '夜子'}">
					<option value="夜子">23 ~ 00 (夜子)</option>
				</c:if>
				<c:if test="${member.birthTime == '吉'}">
					<option selected="selected">吉</option>
				</c:if>
				<c:if test="${member.birthTime != '吉'}">
					<option>吉</option>
				</c:if>
			</select>
			<c:if test="${member.light}">
					<input type="checkbox" class="light" name="light" checked="checked" value="${memberNo}" />
			</c:if>
			<c:if test="${not member.light}">
					<input type="checkbox" class="light" name="light" />
			</c:if>
			<input type="text" class="asset" name="asset" value="${member.asset}" />
			<input type="text" style="display: none;" class="member_id" name="memberId" value="${member.id}" />
			<span class="remove_member_row_h badge badge-important">-</span>
		</li>
		<c:set var="memberNo">${memberNo + 1}</c:set>
	</c:forEach>
	</ul>
</div>
<span>
	<span id="add_member_row_h" class="badge badge-success">+</span>
</span>
<br /><br /><br />
<div id="button_area_h">
	<button class="btn btn-large" type="button" id="change_button_h">送出</button>
</div>
</form>

<div id="customer_alert_h" title="警告">
	<p></p>
</div>
<div id="customer_confirm_h" title="注意">
	<p>確定要刪除此成員？</p>
</div>
<div id="modify_confirm_h" title="注意">
	<p>確定要修改客戶相關資料？</p>
</div>