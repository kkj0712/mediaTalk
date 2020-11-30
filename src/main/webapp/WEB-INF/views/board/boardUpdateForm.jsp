<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<div class="container"><br/>

	<!-- bnum, title, content -->
	<form method="post">
    	<input type="hidden" id="bnum" name ="bnum" value="${board.bnum}">

		<div class="form-group">
	    	<input type="text" value="${board.title}" id="title" name ="title" placeholder="제목" class="form-control rounded-pill">
		</div>
		<div class="form-group">
	    	<textarea id="content" name ="content" class="form-control summernote">${board.content}</textarea>
		</div>
	</form>
	
	<!-- 수정, 취소 버튼 -->
	<div class="d-flex justify-content-center">
		<input type="button" id="updateBtn" class="btn btn-outline-warning rounded-pill mr-1" value="수정">
		<input type="button" id="resetSign" class="btn btn-outline-secondary rounded-pill" value="취소">
	</div>
</div>
<script src="/resources/js/board.js"></script>