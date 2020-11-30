<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container"><br/>
	<p class="mb-3"><a id="font-h3" href="/">영화게시판</a></p>
	
	<!-- 글 상세보기 영역-->
	<form>
		<!-- 이 글을 쓴 User의 id -->	<input type="hidden" id="mviewUserId" name="mviewUserId" value="${mview.user.id}"> 
		<!-- 현재 로그인한 User의 id --><input type="hidden" id="prinUserId" name="prinUserId" value="${principal.user.id}"> 
		<!-- 현재 게시글의 bnum -->		<input type="hidden" id="bnum" name="bnum" value="${mview.bnum}">
		
		<div class="card">
		
			<!-- 제목, 작성자 -->
			<div class="card-header pt-3">
				<div class="row align-items-center justify-content-between">
					<div class="col-8">
					    <h5 class="card-title board--title">${mview.title}</h5>
					</div>
					<div class="col-4 text-right">
						<h6 class="text-muted">${mview.username}</h6>
					</div>
				</div>
			</div>
			
			<!-- 작성일, 조회수 -->
		  	<div class="card-body">
				<div class="text-right text-muted m-0">
					<fmt:parseDate value="${mview.regdate}" pattern="yyyy-MM-dd'T'HH:mm" var="parseDateTime" type="both"/>
					<fmt:formatDate var="dateParse" pattern="yyyy-MM-dd HH:mm" value="${parseDateTime}"/>
					<ul class="list-group bnum--hitcount">
						<li class="d-flex justify-content-between">
							<div class="d-flex">
								<p><small id="lineAfter">글번호 ${mview.bnum} </small>
								   <small>조회수 ${mview.hitcount}</small></p>
							</div>
							<div>
								<p><small>${dateParse}</small></p>
							</div>
						</li>
					</ul>
				</div>
			
				${mview.content}
		
				<!-- 수정, 삭제 버튼 영역 : principal id 대조 -->	
				<c:if test="${mview.user.id == principal.user.id}">
					<div class="row">
						<div class="col-8">
							<a id="updateForm" href="/updateForm/${mview.bnum}"
								class="btn btn-outline-secondary btn-sm rounded-pill" >글수정</a>
						</div>
						<div class="col-4 text-right">
							<input type="button" id="deleteBtn" value="삭제" class="btn btn-outline-danger btn-sm rounded-pill mb-3">
						</div>
					</div>
				</c:if>
				<!-- 수정, 삭제 버튼 영역 끝 -->
				
				<!-- 추천버튼 -->
				<div class="text-center">
					<button type="button" id="likeBtn" class="btn btn-outline-danger btn-sm btn--circle"><span class="rec_count">0</span></button>
				</div>
				
			</div>
		</div>
	</form>
	<!-- 글 상세보기 영역 끝-->
	
	<!-- 댓글 목록 -->
	<div class="card-header text-muted m-0">Comments</div>
	<c:forEach var="reply" items="${mview.reply}">
		<fmt:parseDate value="${reply.regdate}" pattern="yyyy-MM-dd'T'HH:mm" var="parseRegdate" type="both"/>
		<fmt:formatDate var="regParse" pattern="yyyy-MM-dd HH:mm" value="${parseRegdate}"/>
	
		<div class="card">
			<div class="row align-items-center p-3">
				<div class="col-10">
					<div class="font-italic text-muted reply--writer">작성자: ${reply.user.username}</div>
					<div class="replies--content">${reply.msg}</div>
				</div>
				<div class="col-2 text-right">
					<div class="font-italic text-muted reply--writer">${regParse}</div>
							<c:if test="${reply.user.id == principal.user.id}">
								<div><button type="button" id="deleteBadge" 
										onclick="javascript:replyDelete(${mview.bnum}, ${reply.cnum})" class="btn-sm text-muted badge" >삭제</button></div>
							</c:if>
				</div><hr/>
			</div>
		</div>
	</c:forEach>
	
	<!-- 댓글 입력 : 로그인 안하면 창이 안보임 -->
	<sec:authorize access="isAuthenticated()">
		<div class="card replies">
			<div class="card-body reply--box">
					<div class="input-group mb-3">
							<textarea id="msg" class="form-control"></textarea>
							<input type="hidden" id="bnum" value="${mview.bnum}">
							<input type="hidden" id="user_id" value="${principal.user.id}">
				    	<div class="input-group-append">
							<button type="button" id="replyBtn" class="btn btn-sm btn-outline-primary">댓글</button>
						</div>
				    </div>
			</div>
		</div>
	</sec:authorize>
</div>
<script src="/resources/js/board.js"></script>
<script>
//게시글 추천수
function likeCount(){
	var bnum=$("#bnum").val();
	$.ajax({
		url: "/boardView/likeCount/"+bnum,
		type: "post",
		success: function(count){
			$(".rec_count").html(count);
		},
		error: function(error){
			alert("추천수 출력 실패");
		}
	})
}; //likeCount
likeCount();
</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>