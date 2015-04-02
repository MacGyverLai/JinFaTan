
var familyId = 0;
var familyName = "";

$(function() {
	$("#customer_alert_l").dialog({ autoOpen: false, show: "fold", hide: "puff", resizable: false,
		buttons:{
			"確定": function() {
				$(this).dialog("close");
			}
		} 
	});
	$("#customer_confirm").dialog({ autoOpen: false, modal: true, resizable: false ,
		buttons: {
			"確定": function() {
				$("#householder_list").load("ajax_deleteCustomer.do", { familyId: familyId });
				$("#member_list").html("");
				
				$("#customer_alert_l p").text("客戶資料刪除成功！");
				$("#customer_alert_l").dialog("open");
				$(this).dialog("close");
			},
			"取消": function() {
				$(this).dialog("close");
			}
		}
	});

	$("#customer_list tr").click(function() {
		// handle row color
		$("#customer_list tr").css("background-color", "#eeeeee");
		$(this).css("background-color", "#ffd080");
		
		// enable button
		$("#modify_button").removeAttr("disabled");
		$("#delete_button").removeAttr("disabled");
		
		var rowInfo = $(this).children();
		var familyIdColumn = $(rowInfo[7]);
		familyId = $(familyIdColumn).text();
		
		$("#member_list").load("ajax_MemberList.do", {familyId: familyId});
		
		// handle modify area
		var householder_name = $(rowInfo[1]).text();
		var housePhone = $(rowInfo[2]).text();
		var phone_1 = $(rowInfo[3]).text();
		var phone_2 = $(rowInfo[4]).text();
		var city = $(rowInfo[5]).text();
		var address = $(rowInfo[6]).text();
		$("#householder_name_h").text(householder_name);
		$("#housePhone_h").val(housePhone);
		$("#phone_1_h").val(phone_1);
		$("#phone_2_h").val(phone_2);
		$("#city_h").val(city);
		$("#address_h").val(address);
		$("#householder_Id_h").text(familyId);
	});

	$(".table_row").click(function() {
	
		// handle row color
		$(".table_row").css("background-color", "#eeeeee");
		$(this).css("background-color", "#ffd080");
		
		// enable button
		$("#modify_button").removeAttr("disabled");
		$("#delete_button").removeAttr("disabled");
		
		var rowInfo = $(this).children();
		var familyIdColumn = $(rowInfo).get(7);
		familyId = $(familyIdColumn).text();
		familyName = $(rowInfo[1]).text();
		
		$("#member_list").load("ajax_MemberList.do", {familyId: familyId});
		
//		$.ajax({
//			type: "POST",
//			url: "ajax_MemberList.do",
//			dataType: "xml",
//			data: {FamilyId: $(familyId).text()},
//			error: function(ex) {
//				alert("call /ajax_MemberList.do fail.");
//				alert("status = " + ex.status());
//			},
//			success: function(data) {
//				alert("member list callback data = " + data);
//				$("#member_list").html(data);
//			}
//		});
		
//		$.post("/ajax_MemberList.do", {FamilyId: $(familyId).text()}, function(data) {
//			alert("member list callback data = " + data);
//			$("#member_list").html(data);
//		});
	});
	
//	$("#search_button").click(function() {
//		// disabled button
//		//$("#modify_button").Attr("disabled", "disabled");
//		//alert("search_value = " + $("#search_value").val());
//		alert("search_button size = " + $("#search_button").size());
//		
//		$("#householder_list").load("ajax_searchCustomer.do", { searchItem: $("#search_item").val(), 
//				searchValue: $("#search_value").val() });
//		
//		$("#member_list").html("");
//		$("#search_value").val("");
//	});
	
	// click delete button
	$("#delete_button").click(function(event) {
		$("#customer_confirm").dialog("open");
	});
	
	$(".changePage").click(function() {
		var selectPage = $(this).text();
		$("#householder_list").load("ajax_searchCustomer.do", { searchItem: $("#search_item").val(), 
			searchValue: $("#search_value").val(), page: selectPage });
		$("#member_list").html("");
	});
});

function search_button() {
	$("#householder_list").load("ajax_searchCustomer.do", { searchItem: $("#search_item").val(), 
			searchValue: $("#search_value").val(), page: "1" });
	$("#member_list").html("");
	
//	$("#member_list").html("");
//	$("#search_value").val("");
}

function reset_button() {
	$("#search_value").val("");
	search_button();
}
