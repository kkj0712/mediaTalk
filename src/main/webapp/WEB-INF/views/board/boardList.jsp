<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<style>
.movieList{
	border: 0;
    outline: 0;
    color: rgb(41, 42, 50);
}
</style>
<div class="container"><br/>
		<!-- 글쓰기 버튼 : 로그인안한 사람이 클릭하면 로그인창으로 이동 -->
		<div class="d-flex justify-content row p-2" >
			<div class="col-8">
				<p class="mb-3"><a id="font-h3" href="/">MediaTalk</a></p>
			</div>
			<div class="col-4" id="col--4">
				<input type="button" class="btn btn-outline-primary btn-sm  rounded-pill" value="글쓰기" onclick="location.href='/user/boardForm'" >
			</div>
		</div>
		
		<!-- 테이블 영역 시작-->
		<div class="d-flex justify-content-center">
			<table class="table table-row" style="background-color: white;">
				<tr class="text-center">
					<th style="width: 10%">글번호</th>
					<th style="width: 50%">제목</th>
					<th style="width: 10%">추천수</th>
					<th style="width: 10%">작성자</th>
					<th style="width: 10%">조회수</th>
					<th style="width: 10%">작성일</th>
				</tr>

				<c:forEach items="${mlist.content}" var="mboard">
					
					<!-- 일반회원 게시판 글목록 -->
					<tr class="text-center">
						<td>${mboard.bnum}</td>
						<td class="text-left">
							<a href="/boardView/${mboard.bnum}" id="board--view">${mboard.title}  </a>
							
							<!-- 댓글 0이면 안나타남 -->
							<c:choose>
								<c:when test="${mboard.replycnt==0}"></c:when>
								<c:otherwise>
									<span id="replyCount" class="badge badge-secondary badge-pill">${mboard.replycnt}</span>
								</c:otherwise>
							</c:choose>
							
						</td>
						<td>${mboard.good}</td>
						<td>${mboard.username}</td>
						<td>${mboard.hitcount}</td>
						
						<fmt:parseDate value="${mboard.regdate}" pattern="yyyy-MM-dd'T'HH:mm" var="parseDateTime" type="both"/>
						<fmt:formatDate var="dateParse" pattern="MM-dd" value="${parseDateTime}"/>
						<td>${dateParse}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- 테이블 영역 끝 -->
		
		<!-- 페이징 영역 시작 -->
		<div class="text-xs-center">
			<ul class="pagination justify-content-center">
			
				<!-- 이전 -->
				<c:choose>
					<c:when test="${mlist.first}"></c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="/?field=${param.field}&word=${param.word}&page=0">처음</a></li>
						<li class="page-item"><a class="page-link" href="/?field=${param.field}&word=${param.word}&page=${mlist.number-1}">&larr;</a></li>
					</c:otherwise>
				</c:choose>
	
				<!-- 페이지 그룹 -->
					<c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
						<c:choose>
							<c:when test="${mlist.pageable.pageNumber+1 == i}">
								<li class="page-item disabled"><a class="page-link" href="/?field=${param.field}&word=${param.word}&page=${i-1}">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li class="page-item"><a class="page-link" href="/?field=${param.field}&word=${param.word}&page=${i-1}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					<!-- 다음 -->
					<c:choose>
						<c:when test="${mlist.last}"></c:when>
						<c:otherwise>
							<li class="page-item "><a class="page-link" href="/?field=${param.field}&word=${param.word}&page=${mlist.number+1}">&rarr;</a></li>
							<li class="page-item "><a class="page-link" href="/?field=${param.field}&word=${param.word}&page=${mlist.totalPages-1}">마지막</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
		</div>
		<!-- 페이징 영역 끝 -->
		
		<!-- 검색 영역 시작-->
		<form action="/" class="form-inline d-flex justify-content-end"
			method="GET">
			<select name="field" id="field" class="form-control form-control-sm">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select> 
			<input type="text" id="word" name="word" class="form-control form-control-sm"
				style="margin: 10px;"> 
			<input type="submit" class="btn btn-outline-info btn-sm" value="검색">
		</form>
		<!-- 검색 영역 끝 -->
</div>
</body>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>