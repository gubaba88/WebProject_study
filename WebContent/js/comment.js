/**
 * 
 */
$("#comment_form").submit(function() {
	// 유효성 체크
	if (!$("#comWriter").val().trim()) {
		alert("작성자를 입력해주세요");
		$("#comWriter").val("")
		return false;
	}
	if (!$("#comContent").val().trim()) {
		alert("내용을 입력해주세요");
		$("#comContent").val("");
		return false;
	}
	
	$.ajax({
		url : action,
		type : "Post",
		data : {
			comWriter : $("#comWriter"),
			comContent : $("#comContent"),
			postNumber : $("#postNumber"),
			gameNumber : $("#gameNumber"),
			},
		dataType : "json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(result) {
			// alert("success: "+ result);
			setReviewListHtml(result); // 서버에서 비동기로 받은 데이터로 html요소를 다시 구성함. 댓글
										// 리스팅

			// 요소의 속성을 변경. 댓글 입력 부분
			$("#your-rate").val(loginUserRate);
			$("#review-write").attr("type", "hidden");
			$("#review-delete").attr("type", "button");
			$("#review-update-form").attr("type", "button");
			$("#your-comment").attr("readonly", "true");
		},
		error : function(a, b, c) {
			alert("XMLHttpRequest: " + a.responseText);
			alert("예외 원인: " + c);
		}
	});
	
	if ($("#comment_name").val() && $("#comment_content").val()) {
		date = new Date();
		yy = date.getFullYear();
		mm = date.getMonth() + 1;
		dd = date.getDate();
		hh = date.getHours();
		mi = date.getMinutes();
		ss = date.getSeconds();
		today = yy + "-" + mm + "-" + dd + " " + hh + ":" + mi + ":" + ss;
		new_li = $("<li>");
		new_li.addClass("comment_item");
		writer_p = $("<p>");
		writer_p.addClass("writer");
		name_span = $("<span>");
		name_span.addClass("name");
		name_span.html($("#comment_name").val() + "님");
		date_span = $("<span>");
		date_span.html(" / " + today + " ");
		del_input = $("<input>");
		del_input.prop({
			"type" : "button",
			"value" : "X",
			"class" : "btn btn-outline-dark p-2 py-0",
			"style" : " "
		});
		del_input.addClass("delete_btn");
		content_p = $("<p>");
		content_p.html($("#comment_content").val());
		writer_p.append(name_span).append(date_span).append(del_input);
		new_li.append(writer_p).append(content_p);
		$("#comment_list").append(new_li);
		$("#comment_name").val("");
		$("#comment_content").val("");
		return false;
	}
});