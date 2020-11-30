<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %> 
<style>
input {
	margin: 0px !important;
}
</style>
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

	<fmt:parseDate value="${user.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parseDateTime" type="both"/>
	<fmt:formatDate var="dateParse" pattern="yyyy-MM-dd HH:mm" value="${parseDateTime}"/>
				
	<br/><p style="text-align: right;">회원가입일: ${dateParse}</p>
	
<form action="mypageUpdate" method="post" id="myUpdateForm" name="myUpdateForm" novalidate>
	<input type="hidden" value="${user.id}" id="id" name="id">
	
	<div class="input-group mb-3">
		<div class="input-group-prepend">
			<span class="input-group-text">ID</span>
		</div>
	    <input type="text" value="${user.username}" readonly="readonly" id="usernameView" name ="usernameView" class="form-control" >
	</div>
	
	<div class="input-group mb-3">
		<div class="input-group-prepend">
			<span class="input-group-text">이메일</span>
		</div>
	    <input type="text" value="${user.email}" readonly="readonly" id="emailView" name ="emailView" class="form-control">
	</div>
	
	<div class="input-group mb-3">
		<div class="input-group-prepend">
			<span class="input-group-text">비밀번호</span>
		</div>
		<input type="password" placeholder="비밀번호" id="passwordView" name ="passwordView" class="form-control" required><br/>
		<div class="input-group-prepend">
			<span class="input-group-text">비밀번호</span>
		</div>
		<input type="password" placeholder="비밀번호 확인" id="password_check" name ="password_check" class="form-control" required><br/>
	</div>
	
	<div style="text-align: right; color:red;">
		<span id="attention" ></span>
	</div>
		
  	<input type="button" id="myUpdateBtn" value="비밀번호 변경" class="btn btn-warning">
 	<input type="button" id="myDeleteBtn" value="회원탈퇴"  class="btn btn-danger">
</form>
</div>
<script src="/resources/js/user.js"></script>