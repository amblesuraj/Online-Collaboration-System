$(function () {
 
	$("#opencomment").click(function () {
        $(".CommentList").toggle(500);
    });

//    $("#loveBtn").hide();
//    $("#loveBtn").click(function () {
//        $(this).hide();
//        $("#notloveBtn").show();
//
//    });
//
//    $("#notloveBtn").click(function () {
//        $("#notloveBtn").hide();
//        $("#loveBtn").show();
//
//    });
    
   
 
    
    
    $(".reply").one("click",function () {

    	$(".mainComment").hide();
    	$(".replyComment").show();
    	
    	if($(".replyText").empty())
    		{
    		$(".replyText").empty().append('<a href="" class="blue-text" >@SurajAmble &nbsp;</a>');
    		}
        
        
    });

    

    $('.carousel.carousel-slider').carousel({
        fullWidth: true,
        indicators: true,
        dist:150
    });

    window.emojiPicker = new EmojiPicker({
        emojiable_selector: '[data-emojiable=true]',
        assetsPath: '../../app-assets/lib/img',
        popupButtonClasses: 'fa fa-smile-o'

    });
    window.emojiPicker.discover();
    
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
    
    $("#notloveBtn").on('click',addLike);
    
    $("#loveBtn").on('click',deleteLike);
    
    /*
     * End of ajax
     */
    getUserLikes();
    checkUserLikeExist();
});



function updoadFile(){
	$.ajax({
	      url: '/rest/user/saveProfileImage',
	      type: 'POST',
	      processData: false,
	      contentType: false,
	      data: new FormData($("#profilePictureForm")[0]),
	    enctype: 'multipart/form-data',
	      dataType: 'json',
	      success: function (response) {
	        if(response == "success"){
	          
	        	alert("Phofile Photo added successfully");
	        	console.log(response);
	        }else{
	        	console.log("user not Updated Successfully");
	        } 
	      }
	   });
}


function addLike(){
	
	var postId = $("#postId").val();
	var userId = $("#userId").val();
	
	$.ajax({
	      url: "addlike/"+postId+"/"+userId,
	      type: 'POST',
	      success: function (response) {
	        if(response){
	        	getUserLikes();
	        	$("#notloveBtn").hide();
				$("#loveBtn").show();
	        	$("#no_of_likes").text(response);
	        	console.log(response);
	        }else{
	        	
	        	$("#notloveBtn").show();
				$("#loveBtn").hide();
				$("#no_of_like").text(response);
				$("#no_of_likes").text(response);
	        	console.log("user not  liked post from addLike()");
	        } 
	      }
	   });
}

function deleteLike(){
	
	var postId = $("#postId").val();
	var userId = $("#userId").val();
	
	$.ajax({
	      url: "deletelike/"+postId+"/"+userId,
	      type: 'POST',
	      success: function (response) {
	        if(response){
	        	$("#loveBtn").hide();
				$("#notloveBtn").show();
	        	
	        	$("#no_of_likes").text(response);
	        	$("#no_of_like").text(response);
	        	getUserLikes();
	        	console.log(response);
	        }else{
	        	$("#notloveBtn").hide();
				$("#loveBtn").show();
	        	console.log("user not  liked post From deleteLike()");
	        	$("#no_of_likes").text(response);
	        	$("#no_of_like").text(response);
	        } 
	      }
	   });
}




function getUserLikes()
{
	var postId = $("#postId").val();
	var userId = $("#userId").val();
	
	$.ajax({
		url : '/getlikes/'+postId,
		type: 'GET',
		success : function(data) {
			/*$("#email_error").html(data);*/
			if(data)
			{
				$("#no_of_likes").text(data);
				$("#no_of_like").text(data);
				console.log("User Liked post from getLikes()");
			}
			else{
				
				console.log("user didn't like post from getLikes()");
			}
		}
	});
}  


function checkUserLikeExist(){
	
	var postId = $("#postId").val();
	var userId = $("#userId").val();
	
	$.ajax({
	      url: "userExists/"+postId+"/"+userId,
	      type: 'GET',
	      success: function (response) {
	        if(response == true){
	        	$("#notloveBtn").hide();
				$("#loveBtn").show();
	        	console.log("user like exists from checkuserExists"+response);
	        }else{
	        	
	        	$("#loveBtn").hide();
				$("#notloveBtn").show();
	        	console.log("user not  liked post");
	        } 
	      }
	   });
}

