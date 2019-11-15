$(function() {

	$("#formValidate").validate({
		rules : {
			fname : "required",
			lname : "required",
			uname : {
				required : true,
				minlength : 5
			},
			cemail : {
				required : true,
				email : true
			},
			crole : "required",
			ccomment : {
				required : true,
				minlength : 15
			},
			cgender : "required",
			cagree : "required",
		},
		// For custom messages
		messages : {
			uname : {
				required : "Enter a username",
				minlength : "Enter at least 5 characters"
			},

			errorElement : 'div',
			errorPlacement : function(error, element) {
				var placement = $(element).data('error');
				if (placement) {
					$(placement).append(error)
				} else {
					error.insertAfter(element);
				}
			}
		}

	});

});
