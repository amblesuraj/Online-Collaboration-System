$(function () {
 
	$("#opencomment").click(function () {
        $(".CommentList").toggle(500);
    });

    $("#loveBtn").hide();
    $("#loveBtn").click(function () {
        $(this).hide();
        $("#notloveBtn").show();

    });

    $("#notloveBtn").click(function () {
        $("#notloveBtn").hide();
        $("#loveBtn").show();

    });
    
   
 
    
    
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
    
    /*
     * End of ajax
     */
    
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