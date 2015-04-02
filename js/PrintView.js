
$(function() {
	$.datepicker.setDefaults( $.datepicker.regional[ "zh-TW" ] );
	$("#performDate").datepicker({ dateFormat: "yy/mm/dd" });
	
	$("#selectable").selectable({
		stop: function() {
			var result = $("#select-result").empty();
			$(".ui-selected", this).each(function() {
				var index = $("#selectable li").index(this);
				var memberId = $(".memberId:eq(" + index + ")").text();
				result.append(memberId + ":");
			});
		}
	});
	
	$("#preView_button").click(function () {
		var memberIds = $("#select-result").text();
		var performDate = $("#performDate").val();
		var printFormat = $("#print_format").val();
		window.open("ajax_PreviewReport.do?memberIds=" + memberIds + "&performDate=" + 
			performDate + "&printFormat=" + printFormat);
		//$("#preview_area").load("ajax_PreviewReport.do", { memberIds: memberIds });
	});
});