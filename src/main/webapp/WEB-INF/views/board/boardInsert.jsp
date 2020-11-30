<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %> 
<div class="container"><br/>
<form method="post">
	<div class="form-group">
    	<input type="text" id="title" name ="title" placeholder="제목" class="form-control rounded-pill">
	</div>
	<div class="form-group">
    	<textarea id="content" name ="content" class="form-control summernote"></textarea>
	</div>
</form>
	<div class="d-flex justify-content-center">
		<input type="button" id="saveBtn" class="btn btn-outline-primary rounded-pill mr-1" value="글쓰기">
		<input type="button" id="resetSign" class="btn btn-outline-secondary rounded-pill" value="취소">
	</div>
</div>
<script src="/resources/js/board.js"></script>
