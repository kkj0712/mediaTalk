<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %> 
<div class="container"><br/>

	<!-- 마이페이지 내 메뉴 이동 -->
	<ul class="nav nav-tabs">
	  <li class="nav-item">
		<a class="nav-link" id="font-h3" href="/user/mypage">마이페이지</a>
	  </li>
	  <li class="nav-item">
		<a class="nav-link" id="font-h3" href="/user/mypage/writingList">게시글</a>
	  </li>
	  <li class="nav-item">
		<a class="nav-link" id="font-h3" href="/user/mypage/replyList">댓글</a>
	  </li>
	</ul>
	<!-- 마이페이지 내 메뉴 이동 끝-->

	<br/><p style="text-align: right;">내가 쓴 댓글</p>

	<!-- 테이블 영역 시작-->
	<div class="d-flex justify-content-center">
		<table class="table table-row">
			<caption>총 댓글 수: ${myreplyList.totalElements}</caption>
			<tr class="text-center">
				<th style="width: 10%">댓글 번호</th>
				<th style="width: 50%">내용</th>
				<th style="width: 10%">조회수</th>
				<th style="width: 10%">작성일</th>
			</tr>
			<c:forEach items="${myreplyList.content}" var="myreplies">
				<tr class="text-center">
					<td>${myreplies.cnum}</td>
					<td class="text-left">
						<a href="/movie_boardView/${myreplies.movieboard.bnum}" id="board--view">${myreplies.msg}  </a>
					</td>
					<td>${myreplies.movieboard.hitcount}</td>
				<fmt:parseDate value="${myreplies.regdate}" pattern="yyyy-MM-dd'T'HH:mm" var="parseDateTime" type="both"/>
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
				<c:when test="${myreplyList.first}"></c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="/user/mypage/replyList/?page=0">처음</a></li>
					<li class="page-item"><a class="page-link" href="/user/mypage/replyList/?page=${myreplyList.number-1}">&larr;</a></li>
				</c:otherwise>
			</c:choose>

			<!-- 페이지 그룹 -->
				<c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
					<c:choose>
						<c:when test="${myreplyList.pageable.pageNumber+1 == i}">
							<li class="page-item disabled"><a class="page-link" href="/user/mypage/replyList/?page=${i-1}">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="/user/mypage/replyList/?page=${i-1}">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
				<!-- 다음 -->
				<c:choose>
					<c:when test="${myreplyList.last}"></c:when>
					<c:otherwise>
						<li class="page-item "><a class="page-link" href="/user/mypage/replyList/?page=${myreplyList.number+1}">&rarr;</a></li>
						<li class="page-item "><a class="page-link" href="/user/mypage/replyList/?page=${myreplyList.totalPages-1}">마지막</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
	</div>
	<!-- 페이징 영역 끝 -->
</div>
<script src="/resources/js/user.js"></script>