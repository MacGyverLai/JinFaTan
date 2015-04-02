$(function() {
	$("#order_info_list").load("ajax_OrderInfoList.do");
	
	$("#input_order_year").change(function() {
		$("#order_grid").trigger("reloadGrid");
		$("#order_info_list").load("ajax_OrderInfoList.do");
	});
	
	$("#order_filter_button").click(function() {
		$("#order_grid").trigger("reloadGrid");
		$("#order_info_list").load("ajax_OrderInfoList.do");
	});
	
	$("#order_filter_clean_button").click(function() {
		$("#order_filter_name").val("");
		$("#order_grid").trigger("reloadGrid");
		$("#order_info_list").load("ajax_OrderInfoList.do");
	});
	
	$("#order_grid").jqGrid({
		url: "ajax_listOrder.do",
		datatype: "json",
		colNames: ['Order_Year', 'FamilyId', "戶長", '預約日期', '預約時段', '已付款', '登記時間', '更新時間', '備註'],
		colModel: [
			{ name: 'orderYear', index: 'orderYear', hidden: true},
			{ name: 'familyId', index: 'familyId', sorttable: true, hidden: true, editable: true,
				editrules: { required: true }
			},
			{ name: "houseHolder", index: "houseHolder", width: 120, editable: true, editoptions: {
//					dataInit: function(elem) {
//						$(elem).val("MacGyver Lai");
//					}
				} 
			},
			{ name: 'orderDate', index: 'orderDate', formatter: "date", 
				formatoptions: { newformat: "Y-m-d" }, 
				editable: true,
				editrules: { required: true },
				editoptions: { 
					dataInit: function(elem) { $(elem).datepicker({ dateFormat: "yy-mm-dd" }); } 
				}, width: 120, sorttable: false 
			},
//			{ name: 'orderDate', index: 'orderDate', editable: true, editoptions: { dataInit: function(elem) { $(elem).datepicker({ dateFormat: "yy-mm-dd"}); } }, width: 200, sorttable: false },
			{
				name: "orderPeriod", width: 120, editable: true, edittype: "select", 
				editoptions: { value: "上午:上午;下午:下午;晚上:晚上;五點半 ~ 七點:五點半 ~ 七點;七點 ~ 八點半:七點 ~ 八點半;八點半 ~ 十點:八點半 ~ 十點;十點 ~ 十一點半:十點 ~ 十一點半;未定:未定" }, sorttable: false
			},
			{ name: "checkout", index: 'checkout', width: 60, editable: true, edittype: "checkbox", editoptions: { value: "是:否" }, sorttable: true },
			{ name: "insertTime", formatter: "date", formatoptions: { srcformat: "Y-m-d H:i:s", newformat: "Y-m-d H:i:s" }, width: 150, editable: false, sorttable: false },
			{ name: "updateTime", formatter: "date", formatoptions: { srcformat: "Y-m-d H:i:s", newformat: "Y-m-d H:i:s" }, width: 150, editable: false, sorttable: false },
			{ name: "memo", width: 270, edittype: "textarea", editable: true, sorttable: false }
		],
		rowNum: 20,
//		rowList: [10,20,30],
		pager: "#order_pager",
//		sortname: "orderDate",
		sortorder: "desc",
//		height: "90%",
		height: "auto",
		autowidth: true,
		viewrecords: true,
		jsonReader: {
			repeatitems: false
		},
		editurl: "ajax_editOrder.do",
		caption: "訂單列表",
		mtype: "POST",
		postData: { 
			inputOrderYear: function() { return $("#input_order_year").val() },
			inputHouseholder: function() { return $("#order_filter_name").val() }
		}
	});
	
	$("#order_grid").jqGrid("navGrid", "#order_pager",
		{ edit: true, add: true, del: true, search: false },
		{ 	closeAfterEdit: true,
			beforeShowForm: function(formId) {
				
				$.getJSON("ajax_beforeEditOrder.do", { familyId: $("#familyId").val() }, function(data) {
					var clashString = "";
					$.each(data, function(index, val) {
						
						if (val.householder == "是")
							clashString += "<font color='red'>";
						clashString += val.clashDate.substring(0, 10) + 
								"&nbsp;&nbsp; 冲 &nbsp;&nbsp;" + val.name + "<br />";
						if (val.householder == "是")
							clashString += "</font>";
					});
					
					$(".bottominfo").html(clashString);
				});
			},
			bottominfo: " ", // have to save space
			afterSubmit: function(response, postdata) {
				if (response.responseText == "true") {
					$("#order_alert p").text("資料修改成功！");
				} else {
					$("#order_alert p").text("資料修改失敗！");
				}
				
				$("#order_alert").dialog("open");
				$("#order_info_list").load("ajax_OrderInfoList.do");
				return [response.responseText, "修改成功"];
			}
		},
		{ 
			closeAfterAdd: true,
			beforeShowForm: function(formId) {
//				alert("Form object length: " + $(formId).length);
//				$(formId).data("houseHolder", "555");
//				alert("houseHolder = " + $(formId).data("houseHolder"));
//				$(formId)[0].houseHolder="555";
				
//				var aaa = $(formId)[0];
//				alert($(aaa).html());
				
				$("#familyId").val(familyId);
				$("#houseHolder").val(familyName);
				
//				alert($(aaa).html());
				
				$.getJSON("ajax_beforeEditOrder.do", { familyId: familyId }, function(data) {
					var clashString = "";
					$.each(data, function(index, val) {
						
						if (val.householder == "是")
							clashString += "<font color='red'>";
						clashString += val.clashDate.substring(0, 10) + 
								"&nbsp;&nbsp; 冲 &nbsp;&nbsp;" + val.name + "<br />";
						if (val.householder == "是")
							clashString += "</font>";
					});
					
					$(".bottominfo").html(clashString);
				});
			},
			bottominfo: " ",
			afterSubmit: function(response, postdata) {
				if (response.responseText == "true") {
					$("#order_alert p").text("新增預約成功！");
				} else {
					$("#order_alert p").text("新增預約失敗，也許該年已經有預約！");
				}
				
				$("#order_alert").dialog("open");
				$("#order_info_list").load("ajax_OrderInfoList.do");
				return [response.responseText, "新增成功"];
			}
		},
		{ 
			closeAfterAdd: true,
//			delData: { familyId: $("#familyId").text() }
			serializeDelData: function(postData) {
				var rowData = $("#order_grid").getRowData(postData.id);
				return { id: postData.id, oper: postData.oper, 
					orderYear: rowData.orderYear, familyId: rowData.familyId, 
					houseHolder: rowData.houseHolder, orderDate: rowData.orderDate, 
					orderPeriod: rowData.orderPeriod, checkout: rowData.checkout, 
					memo: rowData.memo };
			},
			afterSubmit: function(response, postdata) {
//				alert("return content is " + response.responseText);
//				alert("response data = " + $(response).data());
				if (response.responseText == "true") {
					$("#order_alert p").text("預約刪除成功！");
				} else {
					$("#order_alert p").text("預約刪除失敗！");
				}
				
				$("#order_alert").dialog("open");
				$("#order_info_list").load("ajax_OrderInfoList.do");
				return [response.responseText, "刪除成功"];
			}
		}
	);
	
	$("#order_alert").dialog({ autoOpen: false, show: "fold", hide: "puff", resizable: false,
		buttons:{
			"確定": function() {
				$(this).dialog("close");
			}
		}
	});
});

function initDataBeforeAddGrid(form, oper) {
	alert("Oper is " + oper);
	if (oper == "add") {
		alert("Form = " + form);
	}
}
