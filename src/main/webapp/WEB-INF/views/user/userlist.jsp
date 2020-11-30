<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<div class="container"><br/>
	
	<!-- 검색 영역 시작-->
	<form action="/admin/userlist" class="form-inline d-flex justify-content-end"
		method="GET">
		<select name="field" id="field" class="form-control form-control-sm">
			<option value="username">아이디</option>
			<option value="email">이메일</option>
		</select> 
		<input type="text" id="word" name="word" class="form-control form-control-sm"
			style="margin: 10px;"> 
		<input type="submit" class="btn btn-outline-info btn-sm" value="검색">
	</form>
	<!-- 검색 영역 끝 -->

	<!-- 테이블 영역 시작-->
	<table class="table table-hover">
	
	<caption>회원 수: ${ulist.totalElements}</caption><!-- 회원 수 -->
		<tr>
			<th>번호</th>
			<th>아이디</th>
			<th>이메일</th>
			<th>가입일</th>
		</tr>
		<c:forEach items="${ulist.content}" var="user">
			<tr>
				<td>${user.id}</td>
				<td><a href="/admin/view/${user.id}">${user.username}</a></td>
				<td>${user.email}</td>
				<td>${user.createDate}</td>
			</tr>
		</c:forEach>
	</table>
	<!-- 테이블 영역 끝 -->
	
	<!-- 페이징 영역 시작 -->
	<div class="text-xs-center">
		<ul class="pagination justify-content-center">
		
			<!-- 이전 -->
			<c:choose>
				<c:when test="${ulist.first}"></c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="/admin/userlist/?field=${param.field}&word=${param.word}&page=0">처음</a></li>
					<li class="page-item"><a class="page-link" href="/admin/userlist/?field=${param.field}&word=${param.word}&page=${ulist.number-1}">&larr;</a></li>
				</c:otherwise>
			</c:choose>

			<!-- 페이지 그룹 -->
			<c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
				<c:choose>
					<c:when test="${ulist.pageable.pageNumber+1 == i}">
						<li class="page-item disabled"><a class="page-link" href="/admin/userlist/?field=${param.field}&word=${param.word}&page=${i-1}">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="/admin/userlist/?field=${param.field}&word=${param.word}&page=${i-1}">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			<!-- 다음 -->
			<c:choose>
				<c:when test="${ulist.last}"></c:when>
				<c:otherwise>
					<li class="page-item "><a class="page-link" href="/admin/userlist/?field=${param.field}&word=${param.word}&page=${ulist.number+1}">&rarr;</a></li>
					<li class="page-item "><a class="page-link" href="/admin/userlist/?field=${param.field}&word=${param.word}&page=${ulist.totalPages-1}">마지막</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	<!-- 페이징 영역 끝 -->
</div>