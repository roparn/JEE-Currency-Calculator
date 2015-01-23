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
});
$(function() {
	$("#datepicker").datepicker({ dateFormat: "dd.mm.yy" });
});