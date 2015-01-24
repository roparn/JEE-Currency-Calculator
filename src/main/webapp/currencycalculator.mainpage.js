$(document).ready(function() {
	var form = $('#mainform');
	form.validate({
		rules: {
			amount: {
	            required: true,
	            number: true
	        }
	    }
	});
    $('#validate').click(function () {
        form.valid();
    });
	$('#validate').submit(function () {
		$.ajax({
			type: "POST",
			url: "Controller",
			data: $('#mainform').serialize(),
			success: function (data) {
				console.log("data");
				$('#results').text(data.toString());
			}
		});
		return false;
	});
});
$(function() {
	$("#datepicker").datepicker({ dateFormat: "dd.mm.yy" });
});