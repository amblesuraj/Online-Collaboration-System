$(function() {

	$("#opencomment").click(function() {
		$(".CommentList").toggle(500);
	});

	
	getNotifications();
	$(".notification-badge").hide();
	getNotificationCount();
	
		
	
	
	
	// $("#loveBtn").hide();
	// $("#loveBtn").click(function () {
	// $(this).hide();
	// $("#notloveBtn").show();
	//
	// });
	//
	// $("#notloveBtn").click(function () {
	// $("#notloveBtn").hide();
	// $("#loveBtn").show();
	//
	// });

	$(".reply")
			.one(
					"click",
					function() {

						$(".mainComment").hide();
						$(".replyComment").show();

						if ($(".replyText").empty()) {
							$(".replyText")
									.empty()
									.append(
											'<a href="" class="blue-text" >@SurajAmble &nbsp;</a>');
						}

					});

	$('.carousel.carousel-slider').carousel({
		fullWidth : true,
		indicators : true,
		dist : 150
	});

	window.emojiPicker = new EmojiPicker({
		emojiable_selector : '[data-emojiable=true]',
		assetsPath : '../../app-assets/lib/img',
		popupButtonClasses : 'fa fa-smile-o'

	});
	window.emojiPicker.discover();

	/*
	 * 
	 * File upload ajax
	 */
	// profile image
	$("#profileImage").click(function() {
		$("#profileFile").click();
	});

	// profile background
	$("#profileBg").click(function() {
		$("#profileBgFile").click();
	});

	/*
	 * $("#profileFile").on('change',uploadFile);
	 * 
	 * $("#notloveBtn").on('click',addLike);
	 * 
	 * $("#loveBtn").on('click',deleteLike);
	 */

	/*
	 * End of ajax
	 */

	// checkUserLikeExist();
	// jQuery(".edittopic").click(function() {
	// var btnId = $(this).data('id');
	// jQuery('.t_edit_cont[data-id=' + btnId + ']').toggle();
	// });
	$('span[id^="loveBtn_"]').hide();

	$('span[id^="notloveBtn_"]')
			.click(
					function(e) {

						e.preventDefault();
						var postId = $(this).data('id');
						var userId = $("#userId").val();

						$
								.ajax({
									url : "addlike/" + postId + "/" + userId,
									type : 'POST',
									success : function(response) {
										if (response == "added") {
										/*	 getUserLikes();
											getNotifications();*/
//											getNotificationCount();
											$(
													'span[id^="notloveBtn_'
															+ postId + '"]')
													.hide();
											$(
													'span[id^="loveBtn_'
															+ postId + '"]')
													.show();
											$('loveBtn')
													.addClass(
															"animate__animated animate__heartBeat");

											console.log($('span[id^="loveBtn_'
													+ postId + '"]'));
											console
													.log($('span[id^="notloveBtn_'
															+ postId + '"]'));

											var toastHTML = '<i class="material-icons yellow-text">thumb_up</i>&nbsp;<span>You liked Post</span>';
											M.toast({
												html : toastHTML
											});
											console.log(response);
										} else {

											console
													.log("user not  liked post from addLike()");
										}
									}
								});

					});

	$('span[id^="loveBtn_"]')
			.click(
					function(e) {

						e.preventDefault();
						var postId = $(this).data('id');
						var userId = $("#userId").val();
						console.log(postId);

						$
								.ajax({
									url : "deletelike/" + postId + "/" + userId,
									type : 'POST',
									success : function(response) {
										if (response == "deleted") {
											getNotifications();
											getNotificationCount();
											$(
													'span[id^="notloveBtn_'
															+ postId + '"]')
													.show();
											$('notloveBtn')
													.addClass(
															"animate__animated animate__heartBeat");
											$(
													'span[id^="loveBtn_'
															+ postId + '"]')
													.hide();
											// getUserLikes();
											var toastHTML = '<i class="material-icons red-text">thumb_down</i>&nbsp;<span>You dislike post</span>';
											M.toast({
												html : toastHTML
											});
											console.log(response);
										} else {
											console
													.log("user not  liked post From deleteLike()");

										}
									}
								});
					});
	
	
	$(".notification-button").click(function(){
		$.ajax({
			url : "/readAll",
			method:"POST",
			success : function(result) {
				if (result == "success") {
				
					setTimeout(() => {
						console.log("Notification status changed to READ");
						$(".notification-badge").hide();
					}, 1000);
					
					
					
				} else {
					
					console.log("Notification status cannot change");
					$(".notification-badge").show();
					$(".notification-badge").text(data);
				
				} 
			}
		});
	});
	

});

function getNotifications() {
	$.ajax({
				url : "/notifications",
				success : function(result) {
					$("#notifications-dropdown").html('<li><h6>NOTIFICATIONS</h6></li>');
					if (result.length > 0) {
						
						$.each(result,function(i,v){
							
							var htmlStr = "<li><a class='grey-text text-darken-2' href='#!'><span class='material-icons icon-bg-circle red small'>favorite</span>" +
							result[i].message +"<time class='media-meta' data-livestamp="+result[i].date+"></time></li>"
							
							$("#notifications-dropdown").append(htmlStr);
							
							console.log(result[i].message);
						});						
					} else if(result.length <= 0) {
						
						$("#notifications-dropdown")
								.append('<li class="center-align"><span class="red-text">No Notifications to display</span></li>');
					}
				}
			});
}




function getNotificationCount()
{
	
	
	$.ajax({
		
		url:"/noOfunreadNotifications",
		success:function(data){
			if(data > 0 )
			{
				
				$(".notification-badge").show();
				$(".notification-badge").text(data);
			}
			else
			{
				$(".notification-badge").hide();
			}
		}
		
	});
	
}

/*
 * $(".like").click(function(){
 * 
 * var postId = $(this).data('id'); var userId = $("#userId").val();
 * 
 * var clickBtn = $(this); var action = ''; if(clickBtn.hasClass("fa-heart-o")) {
 * action = "like"; alert(action); } else if(clickBtn.hasClass("fa-heart")) {
 * action = "dislike"; alert(action); }
 * 
 * $.ajax({ url: "addlike/"+postId+"/"+userId, type: 'POST', success: function
 * (response) { if(response == "added"){ if(action == "like") {
 * clickBtn.removeClass("fa-heart-o").addClass("fa-heart");
 * clickBtn.removeClass("like").addClass("dislike");
 *  } else if(action == "dislike") {
 * clickBtn.removeClass("fa-heart").addClass("fa-heart-o"); }
 * console.log(response); }else{
 * 
 * 
 * $("#no_of_like").text(response); $("#no_of_likes").text(response);
 * console.log("user not liked post from addLike()"); } } });
 * 
 * 
 * 
 * });
 * 
 * $(".dislike").click(function(){
 * 
 * var postId = $(this).data('id'); var userId = $("#userId").val();
 * 
 * var clickBtn = $(this); var action = ''; if(clickBtn.hasClass("fa-heart")) {
 * action = "like"; alert(action); } else if(clickBtn.hasClass("fa-heart-o")) {
 * action = "dislike"; alert(action); }
 * 
 * $.ajax({ url: "deletelike/"+postId+"/"+userId, type: 'POST', success:
 * function (response) { if(response == "deleted"){ if(action == "dislike") {
 * clickBtn.removeClass("fa-heart").addClass("fa-heart-o");
 * 
 * clickBtn.removeClass("dislike").addClass("like"); } else if(action == "like") {
 * clickBtn.removeClass("fa-heart").addClass("fa-heart-o");
 *  } console.log(response); }else{
 * 
 * 
 * $("#no_of_like").text(response); $("#no_of_likes").text(response);
 * console.log("user not liked post from addLike()"); } } });
 * 
 * 
 * 
 * });
 */

/*
 * function updoadFile(){ $.ajax({ url: '/rest/user/saveProfileImage', type:
 * 'POST', processData: false, contentType: false, data: new
 * FormData($("#profilePictureForm")[0]), enctype: 'multipart/form-data',
 * dataType: 'json', success: function (response) { if(response == "success"){
 * 
 * alert("Phofile Photo added successfully"); console.log(response); }else{
 * console.log("user not Updated Successfully"); } } }); }
 */

/*
 * function addLike(){
 * 
 * var postId = $("#postId").val(); var userId = $("#userId").val();
 * 
 * $.ajax({ url: "addlike/"+postId+"/"+userId, type: 'POST', success: function
 * (response) { if(response == "added"){ getUserLikes();
 * $("#notloveBtn").hide(); $("#loveBtn").show(); var toastHTML = '<i
 * class="material-icons yellow-text">thumb_up</i>&nbsp;<span>You liked Post</span>';
 * M.toast({html: toastHTML}); console.log(response); }else{
 * 
 * $("#notloveBtn").show(); $("#loveBtn").hide(); console.log("user not liked
 * post from addLike()"); } } }); }
 * 
 * function deleteLike(){
 * 
 * var postId = $("#postId").val(); var userId = $("#userId").val();
 * 
 * $.ajax({ url: "deletelike/"+postId+"/"+userId, type: 'POST', success:
 * function (response) { if(response == "deleted"){ $("#loveBtn").hide();
 * $("#notloveBtn").show(); getUserLikes(); var toastHTML = '<i
 * class="material-icons red-text">thumb_down</i>&nbsp;<span>You dislike post</span>';
 * M.toast({html: toastHTML}); console.log(response); }else{
 * $("#notloveBtn").hide(); $("#loveBtn").show(); console.log("user not liked
 * post From deleteLike()");
 *  } } }); }
 */

/*
 * function getUserLikes() { var postId =
 * $('span[id^="notloveBtn_"]').data('id'); var userId = $("#userId").val();
 * 
 * $.ajax({ url : '/getlikes/'+postId, type: 'GET', success : function(data) {
 * $("#email_error").html(data); if(data >= 0) {
 * $("#no_of_likes"+postId).text(data); $("#no_of_like"+postId).text(data);
 * console.log("User Liked post from getLikes()"); } else{
 * 
 * console.log("user didn't like post from getLikes()"); } } }); }
 */

/*
 * function checkUserLikeExist(){
 * 
 * var postId = $('span[id^="notloveBtn_"]').data('id'); var userId =
 * $("#userId").val(); console.log(postId); $.ajax({ url:
 * "userExists/"+postId+"/"+userId, type: 'GET', success: function (response) {
 * if(response == true){ $('span[id^="notloveBtn_' + postId + '"]').show();
 * $('span[id=^="loveBtn_' + postId + '"]').show(); console.log("user like
 * exists from checkuserExists"+response); }else{ $('span[id=^="notloveBtn_' +
 * postId + '"]').show(); $('span[id=^="loveBtn_' + postId + '"]').hide();
 * $("#loveBtn").hide(); $("#notloveBtn").show(); console.log("user not liked
 * post"); } } }); }
 */