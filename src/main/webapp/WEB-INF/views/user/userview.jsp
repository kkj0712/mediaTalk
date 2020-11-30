<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %> 
<style>
input {
	margin: 0px !important;
}
</style>
<div class="container"><br/>
	<h3 id="font-h3">${user.email} 상세정보</h3>
	<p style="text-align: right;">회원가입일: ${user.createDate}</p>
	
<form action="update" method="post">
	<input type="hidden" value="${user.id}" id="id" name="id">
	<div class="input-group mb-3">
		<div class="input-group-prepend">
			<span class="input-group-text">제공자</span>
		</div>
		<input type="text" class="form-control" value="${user.provider}" readonly="readonly" id="provider" name ="provider">
		<div class="input-group-prepend">
			<span class="input-group-text">제공자ID</span>
		</div>
	    <input type="text" value="${user.providerId}" readonly="readonly" id="providerId" name ="providerId" class="form-control">
	</div>
	
	<div class="input-group mb-3">
		<div class="input-group-prepend">
			<span class="input-group-text">ID</span>
		</div>
	    <input type="text" value="${user.username}" readonly="readonly" id="usernameView" name ="usernameView" class="form-control">
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
	    <input type="text" value="${user.password}" readonly="readonly" id="passwordView" name ="passwordView" class="form-control">
	</div>
	
	<div class="input-group mb-3">
		<div class="input-group-prepend">
			<span class="input-group-text">등급</span>
		</div>
	    <input type="text" value="${user.grade}" id="grade" name ="grade" class="form-control">
		<div class="input-group-prepend">
			<span class="input-group-text">권한</span>
		</div>
		<select name="role" id="role" class="form-control">
		  	<option value="ROLE_USER" class="form-control">ROLE_USER</option>
		   	<option value="ROLE_MANAGER" class="form-control">ROLE_MANAGER</option>
		   	<option value="ROLE_ADMIN" class="form-control">ROLE_ADMIN</option>
		</select>
	</div>
	
	<div class="input-group mb-3">
		<div class="input-group-prepend">
			<span class="input-group-text">신고</span>
		</div>
	    <input type="text" value="${user.caution}" readonly="readonly" id="caution" name ="caution" class="form-control">
	</div>
	
  	<input type="button" id="updateBtn" name="updateBtn" value="수정" class="btn btn-warning">
 	<input type="button" id="deleteBtn" name="deleteBtn" value="삭제"  class="btn btn-danger">
</form>
</div>
<script src="/resources/js/user.js"></script>
<script>
//회원정보 중 role 권한 보여주기
$("select[name=role] option").each(function(){
	if($(this).val()=="${user.role}"){
		$(this).prop("selected", true);
	}
});
</script>