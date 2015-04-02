
$(function() {
	$.datepicker.setDefaults( $.datepicker.regional[ "zh-TW" ] );
	
	// for 民國 canlendar
//	$.datepicker._phoenixGenerateMonthYearHeader = $.datepicker._generateMonthYearHeader;
//	$.datepicker._generateMonthYearHeader = function(inst, drawMonth, drawYear, minDate, maxDate, 
//        secondary, monthNames, monthNamesShort) { 
//        var result = $($.datepicker._phoenixGenerateMonthYearHeader(inst, drawMonth, drawYear, minDate, maxDate,
//            secondary, monthNames, monthNamesShort)); 
//        result.children("select.ui-datepicker-year").children().each(function() { 
//            $(this).text('民國' + ($(this).text() - 1911) + '年'); 
//        }); 
//        if( inst.yearshtml ){ 
//            var origyearshtml = inst.yearshtml; 
//            setTimeout(function(){ 
//                //assure that inst.yearshtml didn't change. 
//                if( origyearshtml === inst.yearshtml ){ 
//                    inst.dpDiv.find('select.ui-datepicker-year:first').replaceWith(inst.yearshtml); 
//                    inst.dpDiv.find('select.ui-datepicker-year').children().each(function() { 
//                        $(this).text('民國' + ($(this).text() - 1911) + '年'); 
//                    }); 
//                } 
//                origyearshtml = inst.yearshtml = null; 
//            }, 0); 
//        } 
//        return result.html(); 
//    };
//	$(".birthday").datepicker({ defaultDate: "-20Y", changeMonth: true, changeYear: true });
	
//	$(".birthDay").datepicker({ dateFormat: "dd", defaultDate: "new Date('October 15, 1975')" });
	$(".birthDay").datepicker({ dateFormat: "dd", minDate: "-30", maxDate: "+30" });
	
	$("#customer_alert").dialog({ autoOpen: false, show: "fold", hide: "puff", resizable: false,
		buttons:{
			"確定": function() {
				$(this).dialog("close");
			}
		} 
	});
	$("#customer_alert_h").dialog({ autoOpen: false, show: "fold", hide: "puff", resizable: false,
		buttons:{
			"確定": function() {
				$(this).dialog("close");
			}
		} 
	});
	$("#customer_confirm_h").dialog({ autoOpen: false, modal: true, resizable: false ,
		buttons: {
			"確定": function() {
				$.post("ajax_deleteMember.do", { memberId: $(delete_member_Id).val() }, function(result) {
					if (result == "true") {
						$("#customer_alert_h p").text("家庭成員刪除成功！");
						$("#customer_alert_h").dialog("open");
						$(delete_member_Id).parent().remove();
					} else {
						$("#customer_alert_h p").text("家庭成員刪除失敗，請回報程式設計人員！");
						$("#customer_alert_h").dialog("open");
					}
				});
				$(this).dialog("close");
			},
			"取消": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#modify_confirm_h").dialog({ autoOpen: false, modal: true, resizable: false ,
		buttons: {
			"確定": function() {
				// check address text whether null
				if ($("#householder_change_h [name='city']").val() == "" || 
						$("#householder_change_h [name='address']").val() == "") {
					$("#customer_alert_h p").text("地址欄不能為空白！");
					$("#customer_alert_h").dialog("open");
					$(this).dialog("close");
					return;
				}
				
				// 檢查是否選擇戶長
				var haveHouseHolder = false;
				$(".householder_h").each(function() {
					if (this.checked)
						haveHouseHolder = true;
				});
				if (!haveHouseHolder) {
					$("#customer_alert_h p").text("請先選擇戶長！");
					$("#customer_alert_h").dialog("open");
					$(this).dialog("close");
					return;
				}
				
				// check text input whether null
				var verifyResult = true;
				$(".member_row_h :text").each(function() {
					if ($(this).val() == "") {
						if ($(this).attr("class") == "name") {
							$("#customer_alert_h p").text("姓名欄不能為空白！");
							$("#customer_alert_h").dialog("open");
							$("#modify_confirm_h").dialog("close");
							verifyResult = false;
						} else if ($(this).attr("class") == "birthYear") {
							$("#customer_alert_h p").text("生年欄不能為空白！");
							$("#customer_alert_h").dialog("open");
							$("#modify_confirm_h").dialog("close");
							verifyResult = false;
						} else if ($(this).attr("class") == "birthDay") {
							$("#customer_alert_h p").text("生日欄不能為空白！");
							$("#customer_alert_h").dialog("open");
							$("#modify_confirm_h").dialog("close");
							verifyResult = false;
						}
					}
				});
				if (!verifyResult)
					return;
				
				$.post("ajax_modifyCustomer.do", $("#cuatomer_modify_form").serialize(), function(result) {
					if (result == "true") {
						$("#customer_alert_h p").text("客戶資料修改成功！");
						$("#customer_alert_h").dialog("open");
					} else {
						$("#customer_alert_h p").text("客戶資料修改失敗，請回報程式設計人員！");
						$("#customer_alert_h").dialog("open");
					}
				});
				$(this).dialog("close");
			},
			"取消": function() {
				$(this).dialog("close");
			}
		}
	});
	
	// add one member row
	$("#add_member_row").click(function() {
		
		//$(".householder").unbind();
		//$(".birthDay").unbind();
		$(".member_row:eq(0)").clone(true).appendTo("#member_change");
		
		// reset class & clear id
		$(".birthDay").removeClass().addClass("birthDay");
		$(".birthDay").attr("id", null);
		
		//$(".householder").click(radio_click);
		$(".birthDay").datepicker({ dateFormat: "dd", minDate: "-30", maxDate: "+30" });
		
		// clear input text
		var lastIndex = $(".member_row").size() - 1;
		var allInput = $(".member_row:eq(" + lastIndex + ")").children();
		var inputName = $(allInput).get(1);
		$(inputName).val("");
		var inputYear = $(allInput).get(3);
		$(inputYear).val("");
		var inputDate = $(allInput).get(5);
		$(inputDate).val("0");
		$(allInput).get(7).checked = false;
		var inputAsset = $(allInput).get(8);
		$(inputAsset).val("");
	});
	
	$("#add_member_row_h").click(function() {
		$(".member_row_h:eq(0)").clone(true).appendTo("#member_change_h ul");
		
		// reset class & clear id
		$(".birthDay").removeClass().addClass("birthDay");
		$(".birthDay").attr("id", null);
		
		//$(".householder_h").click(radio_click);
		$(".birthDay").datepicker({ dateFormat: "dd", minDate: "-30", maxDate: "+30" });
		
		// clear input text
		var lastIndex = $(".member_row_h").size() - 1;
		var allInput = $(".member_row_h:eq(" + lastIndex + ")").children();
		var houseHolder = $(allInput).get(0);
		$(houseHolder).val(lastIndex);
		var inputName = $(allInput).get(1);
		$(inputName).val("");
		var inputYear = $(allInput).get(3);
		$(inputYear).val("");
		var inputDate = $(allInput).get(5);
		$(inputDate).val("0");
		$(allInput).get(7).checked = false;
		var inputAsset = $(allInput).get(8);
		$(inputAsset).val("");
		var inputMemberId = $(allInput).get(9);
		$(inputMemberId).val("-1");
	});
	
	// remove one member row
//	$("#remove_member_row").click(function(event) {
//		var index = $(".member_row").size();
//		alert("size = " + index);
//		if (index > 1) {
//			alert("into if...");
//			var member_row = $(".member_row")[index - 1];
//			$(member_row).remove();
//			alert("end if...");
//		}
//	});
	$(".remove_member_row").click(function(event) {
		var index = $(this).index();
		var size = $(".member_row").size();
		if (size > 1) {
			$(this).parent().remove();
		} else {
			$("#customer_alert p").text("至少需保留一位當戶長！");
			$("#customer_alert").dialog("open");
		}
	});
	
	$(".remove_member_row_h").click(function(event) {
		var index = $(this).index();
		var size = $(".member_row_h").size();
		if (size > 1) {
			var member_row = $(this).parent();
			var memberId = $(member_row).children()[9];
			delete_member_Id = memberId;
			if ($(memberId).val() != "-1")
				$("#customer_confirm_h").dialog("open");
			else 
				$(this).parent().remove();
		} else {
			$("#customer_alert_h p").text("至少需保留一位當戶長！");
			$("#customer_alert_h").dialog("open");
		}
	});
	
	// 送出 click
	$("#add_button").click(function() {
		//alert("encodeURI = " +  encodeURIComponent($("#house_phone").val()));
		//alert("names = " + toArrayString($(".name").toArray()));
		
		// check address text whether null
		if ($("#householder_change [name='city']").val() == "" || 
				$("#householder_change [name='address']").val() == "") {
			$("#customer_alert p").text("地址欄不能為空白！");
			$("#customer_alert").dialog("open");
			return;
		}
		
		// 檢查是否選擇戶長
		var haveHouseHolder = false;
		$(".householder").each(function() {
			if (this.checked)
				haveHouseHolder = true;
		});
		if (!haveHouseHolder) {
			$("#customer_alert p").text("請先選擇戶長！");
			$("#customer_alert").dialog("open");
			return;
		}
		
		// check text input whether null
		$(".member_row :text").each(function() {
			if ($(this).val() == "") {
				if ($(this).attr("class") == "name") {
					$("#customer_alert p").text("姓名欄不能為空白！");
					$("#customer_alert").dialog("open");
					return;
				} else if ($(this).attr("class") == "birthYear") {
					$("#customer_alert p").text("生年欄不能為空白！");
					$("#customer_alert").dialog("open");
					return;
				} else if ($(this).attr("class") == "birthDay") {
					$("#customer_alert p").text("生日欄不能為空白！");
					$("#customer_alert").dialog("open");
					return;
				}
			}
		});
		
		$.post("ajax_addCustomer.do", $("#cuatomer_add_form").serialize(), function(result) {
			if (result == "true") {
				$("#customer_alert p").text("客戶資料新增成功！");
				$("#customer_alert").dialog("open");
				clear_click();
			}
		});
	});
	
	// 送出修改 click
	$("#change_button_h").click(function() {
		$("#modify_confirm_h").dialog("open");
	});
	
	$(".householder_h").click(function() {
		var index = $(".householder_h").index(this);
		//$(this).val(index);
		$("#householder_name_h").text($(".name_h:eq(" + index + ")").val());
	});
	
	$(".light").click(function() {
		var index = $(".light").index(this);
		$(this).val(index);
	});
	
	// 地址選區
	$("#city_select").change(city_select_change);
	$("#city_select_h").change(city_select_change_h);
	
	// 異動戶長 click
	//$(".householder").change(radio_click);
	
	// 甲子轉民國
	$(".jia_zhi").change(function() {
		$("#result_years").load("ajax_calculateJiaZhi.do", { tian_gan: $("#tian_gan").val(), 
				di_zhi: $("#di_zhi").val() });
	});
	
	// 生肖轉民國
	$(".animal_select").change(function() {
		$("#result_years").load("ajax_calculateAnimal.do", { age_input: $("#age_input").val(),
				animal_select: $("#animal_select").val() });
	});
	
	$("#sortable").sortable();
	//$("#sortable").disableSelection();
	
});

var delete_member_Id = null;

function toArrayString(data) {
	var result = "[ ";
	for (var i = 0; i < data.length; i++) {
		if (i != 0)
			result += ",";
		result += $(data[i]).val();
	}
	result += " ]";
	
	return result;
}

function city_select_change(event) {
	var citySelect = $("#city_select").val();
	$.post("ajax_retvAreaByCity.do", { selected_city: citySelect } ,function(data) {
		$("#area_select").html("");
		$(data).each(function(index) {
			if (this == "三芝區")
				var optionItem = "<option selected='selected'>" + this + "</option>";
			else
				var optionItem = "<option>" + this + "</option>";					
			$("#area_select").append(optionItem);
		});
	}, "json");
}

function city_select_change_h(event) {
	var citySelect = $("#city_select_h").val();
	$.post("ajax_retvAreaByCity.do", { selected_city: citySelect } ,function(data) {
		$("#area_select_h").html("");
		$(data).each(function(index) {
			if (this == "三芝區")
				var optionItem = "<option selected='selected'>" + this + "</option>";
			else
				var optionItem = "<option>" + this + "</option>";					
			$("#area_select_h").append(optionItem);
		});
	}, "json");
}

function radio_change(event) {
	// retrieve click index
	var index = 0;
	var tmpIndex = 0;
	$(".householder").each(function() {
		if (this == event.target)
			index = tmpIndex;
		tmpIndex++;
	});
	
	// set radio box value
	event.target.value = index;
	
	var name = $(".name")[index];
	var householder_name = $(name).val();
	$("#householder_name").text(householder_name);
}

function light_change(event) {
	// retrieve click index
	var index = 0;
	var tmpIndex = 0;
	$(".light").each(function() {
		if (this == event.target)
			index = tmpIndex;
		tmpIndex++;
	});
	
	// set check box value
	event.target.value = index;
}

// 清除 click
function clear_click() {
	// reset member row
	var tmpHouseInfo = $("#member_change div:eq(0)");
	var houseInfo = $(tmpHouseInfo).clone(true);
	var tmpMemberInfo = $(".member_row")[0];
	var memberInfo = $(tmpMemberInfo).clone(true);
	
	$("#member_change").empty();
	$("#member_change").append(houseInfo);
	$("#member_change").append(memberInfo);
	
	// reset class & clear id
	$(".birthday").removeClass().addClass("birthday");
	$(".birthday").attr("id", null);
	
	//$(".householder").click(radio_click);
	$(".birthDay").datepicker({ dateFormat: "dd", minDate: "-30", maxDate: "+30" });
	
	$("#cuatomer_add_form").reset();
}