//취소
$("#resetSign").on("click", function(){
	if(confirm("작성한 글은 저장되지 않습니다.")){
		history.back(-1);
	}
});

//썸머노트
$('.summernote').summernote({
    placeholder: '*운영정책에 위배되는 게시물일 경우 통보없이 삭제됩니다.',
    tabsize: 2,
    height: 500
});

//추천 버튼
$("#likeBtn").click(function(){
	var bnum=$("#bnum").val();
	var userId=$("#prinUserId").val();
	if($("#prinUserId").val()==""){
		alert("추천은 로그인 후 가능합니다.");
	}else {
		//like Check: Likes에서 이 bnum에 user_id가 있는지 확인
		$.ajax({
			type: "POST",
			url: "/boardView/likeCheck/"+bnum,
			data: JSON.stringify(bnum),
			contentType: "application/json;charset=utf-8"
		})
		.done(function(resp){
			if(resp>=1){
				alert("이미 추천했습니다.")
			}else{
				likeSave();
			}
		}) //like Check의 done
		.fail(function(error){
			alert("like Check: "+error);
		})//like Check의 fail
	}
}); //likeBtn 끝

//추천
function likeSave(){
	var bnum=$("#bnum").val();
	$.ajax({
		type: "POST",
		url: "/boardView/likeSave/"+bnum,
		data: JSON.stringify(bnum),
		contentType: "application/json;charset=utf-8"
	})
	.done(function(resp){
		likeCount();
		$("#likeBtn").addClass('active');
	})
	.fail(function(error){
		alert("liking"+error);
	})
} //likeSave()

//댓글 삭제
function replyDelete(bnum, cnum){
	if(confirm("정말 삭제하시겠습니까?")){
		$.ajax({
			type:"DELETE",
			url: "/deleteReplyProc/"+bnum+"/reply/"+cnum
		})
		.done(function(resp){
			alert("댓글 삭제 완료");
			location.href="/boardView/"+bnum;
		})
		.fail(function(error){
			alert(error+"error");
		})
	}
} //deleteReply

let board={
		init:function(){
			//게시물 작성
			$("#saveBtn").on("click",()=>{
				this.saveBoard();
			});
				
			//게시물 삭제
			$("#deleteBtn").on("click",()=>{
				this.deleteBoard();
			});
			
			//게시물 수정
			$("#updateBtn").on("click",()=>{
				this.updateBoard();
			});

			//댓글 쓰기
			$("#replyBtn").on("click",()=>{
				this.saveReply();
			});
			
		},
		
		//게시물 작성
		saveBoard:function(){
			if($("#title").val()==""){
				alert("제목을 입력하세요!");
				$("#title").focus();
			}else if($("#content").val()==""){
				alert("내용을 입력하세요!");
				$("#content").focus();
			}else{
				var dataParam={
				title:$("#title").val(),
				content:$("#content").val()
				}
				$.ajax({
					type: "POST",
					url: "/user/boardSave",
					data: JSON.stringify(dataParam),
					contentType: "application/json;charset=utf-8",
				})
				.done(function(resp){
					alert("글 작성 완료");
					location.href="/";
				})
				.fail(function(error){
					alert("글 작성 실패");
				})
			}
		},
			
		//게시물 삭제
		deleteBoard:function(){
			if(confirm("정말 삭제하시겠습니까?")){
				var bnum=$("#bnum").val();
				$.ajax({
					type:"DELETE",
					url: "/deleteProc/"+bnum
				})
				.done(function(resp){
					alert("글 삭제 완료");
					location.href="/";
				})
				.fail(function(error){
					alert(error+"error");
				})
			}
		}, //delete
		
		//게시물 수정
		updateBoard:function(){
			var bnum=$("#bnum").val();
			var dataParam={
				"bnum":$("#bnum").val(),
				"title":$("#title").val(),
				"content":$("#content").val()
			};
			$.ajax({
				type:"PUT",
				url: "/updateProc/"+bnum,
				data:JSON.stringify(dataParam),
				contentType: "application/json;charset=utf-8",
			})
			.done(function(resp){
				alert("글 수정 완료");
				location.href="/boardView/"+bnum;
			})
			.fail(function(error){
				alert("error"+error);
			})
		}, //delete
		
		//댓글쓰기
		saveReply:function(){
			var dataParam={
				msg : $("#msg").val(),
				bnum: $("#bnum").val(),
				user_id: $("#user_id").val()
			};
			
			$.ajax({
				type:"post",
				url: `/boardView/${dataParam.bnum}/reply`,
				data: JSON.stringify(dataParam),
				contentType: 'application/json;charset=uft-8'
				})
				.done(function(){
					alert("댓글작성 완료");
					location.href=`/boardView/${dataParam.bnum}`
				})
				.fail(function(error){
					alert("댓글입력 실패");
				})
		} // saveReply
	};//board
board.init();