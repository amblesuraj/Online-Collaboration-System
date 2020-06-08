$(function() {

//	$("#formValidate").validate({
//		rules : {
//			fname : "required",
//			lname : "required",
//			username : {
//				required : true,
//				minlength : 5
//			},
//			email : {
//				required : true,
//				email : true
//			}
//			
//		},
//		// For custom messages
//		messages : {
//			username : {
//				required : "Enter a username",
//				minlength : "Enter at least 5 characters"
//			},
//
//			errorElement : 'div',
//			errorPlacement : function(error, element) {
//				var placement = $(element).data('error');
//				if (placement) {
//					$(placement).append(error)
//				} else {
//					error.insertAfter(element);
//				}
//			}
//		}
//
//	});
	
	/*
	 * 
	 *File upload ajax
	 */
	    //profile image
	    $("#profileImage").click(function () {
	        $("#profileFile").click();
	    });
	    
	  //profile background
	    $("#profileBg").click(function () {
	        $("#profileBgFile").click();
	    });
	    
	    
	    $("#profileFile").on('change',uploadFile);
	    
	    /*
	     * End of ajax
	     */
	

});


function uploadFile(){
	$.ajax({
	      url: '/user/saveProfileImage',
	      type: 'POST',
	      processData: false,
	      contentType: false,
	      data: new FormData($("#profilePictureForm")[0]),
	      enctype: 'multipart/form-data',
//	      dataType:'text',
	      cache:false,
	      success: function (response) {
	        if(response == "success"){
	        	var uid = $("#uId").val();
	        	
	        	console.log(uId);
	        	
	        	window.location.href = "/user/profile/"+uid;
	        	
	        
	        	var toastHTML = '<i class="material-icons yellow-text">thumb_up</i>&nbsp;<span>Phofile Photo added successfully</span>';
	        	  M.toast({html: toastHTML});
	        	  
	        	  getProfile();
	        	console.log("Phofile Photo added successfully");
	        }else{
	        	console.log("Phofile Photo not added successfully");
	        } 
	      },
	      error:function(err){
	    	  console.error(err);
	      }
	         
	      
	   });
}



function getProfile(){
	let uId = $("#uId").val();
	
	$.ajax({
	      url: '/user/profile/'+uId,
	      type: 'GET',
	      success: function (response) {
	        if(response == "success"){
	        	$.each(response, function(i,v){
	        		console.log(response[i].id);
	        		console.log(response[i].username);
	        		console.log(response[i].email);
	        		console.log(response[i].fname);
	        		console.log(response[i].lname);
	        		console.log(response[i].profilePicture);
	        	});
	        }else{
	        	console.log("Error");
	        } 
	      },
	      error:function(err){
	    	  console.error(err);
	      }
	         
	      
	   });
}