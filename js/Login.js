$(function() {
	$("#login_button").click(function() {
		//alert(1);
		var userName = $("input[name=user_name]");
		var password = $("input[name=password]");
		var userName_name = userName.attr("name");
		var userName_text = userName.attr("value");
		var passwd_name = password.attr("name");
		var passwd_text = password.attr("value");
		//alert(2);
		params = {userName_name:userName_text, passwd_name:passwd_text};
		//alert(userName_name + "=" + userName_text + ";" + passwd_name + "=" + passwd_text);
		$("#login_table").hide("clip", {}, 700, "");
		$("#login_form").submit();
	});
});

/*
function login_click() {
	alert(0);
	this.effect("clip",{},700,function() {
		alert(1);
		var userName = $("input[name=user_name]").arrt("name");
		var password = $("input[name=password]");
		alert(2);
		//var parameters = {userName.attr("name"):userName.attr("text"), password.attr("name"):password.attr("text")};
		$.get("main_page.do", parameters, function(data) {
			alert(data);
		});
		$.getJSON("main_page.do", params, function(data) {
			alert(data);
			$("#login_table").effect("clip", {}, 700, "");
		});
	});
}
*/