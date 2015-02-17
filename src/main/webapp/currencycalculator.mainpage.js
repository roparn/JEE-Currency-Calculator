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
	$("#mainform").submit(function(e){
		e.preventDefault();
	});
	$('#validate').click(function () {
		if (!form.valid()) {
			console.log("Form is invalid!");
			return false;
		}
		$.ajax({
			type: "POST",
			url: "Controller",
			data: form.serialize(),
			dataType: "json",
			mimeType: 'application/json',

			success: function (data) {
				$('#results').text("Results:");
				for (var i=0; i < data.length; i++) {
					console.log(data[i]);
					$('#results').append("<br />" + data[i].source + ": " + data[i].amount + " " + data[i].inCurrency + " = " + data[i].result + " "+ data[i].outCurrency);
				}
				console.log(data);
			},
			error: function (data) {
				console.log(data);
				$('#results').text("Problem occured: " + data.responseJSON.error);
			}
		});
		return false;
	});
});
$(function() {
	$("#datepicker").datepicker({ dateFormat: "dd.mm.yy" });
});