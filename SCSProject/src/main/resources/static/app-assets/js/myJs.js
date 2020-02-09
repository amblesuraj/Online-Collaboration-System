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

    $("#profileImage").click(function () {
        $("#profileFile").click();
    });

    $("#profileFile").change(function () {
        alert("file Uploaded");
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
     * Save User info in session
     * */
//    var formData = new FormData();
    
   /* $("#formValidate").submit(function(e){
    		
    	e.preventDefault();
    	
    	 $.ajax({
    	      url: '/rest/updateUser',
    	      type: 'POST',
    	      data: formData,
    	      processData: false,
    	      contentType: false,
    	      dataType: 'json',
    	      success: function (response) {
    	        if(response == "success"){
    	          
    	        	alert("user Updated Successfully");
    	        	console.log(response);
    	        }else{
    	        	console.log("user not Updated Successfully");
    	        } 
    	      }
    	   });
    });*/
    

});