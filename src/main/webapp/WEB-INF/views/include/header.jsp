<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MediaTalk</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="/resources/css/style.css"> 
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>

  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
  
  <link rel="stylesheet" type="text/css" href="/resources/css/slick.css"/>
  <link rel="stylesheet" type="text/css" href="/resources/css/slick-theme.css"/>
</head>

<body>
<header class="navHeader">
	<nav>
	  <ul class="ulHeader">
	    <li id="li2"><a id="LogoH" onclick="location.href='/'">MediaTalk</a></li>
	    <li id="li1"><a id="btnH" class="movieList" onclick="location.href='/movie'">영화</a></li>
	    <li id="li1"><a id="btnH" class="tvList" onclick="location.href='/tv'">TV 프로그램</a></li>
	    <li id="li2"><a id="btnH" class="bookList" onclick="location.href='/book'">책</a></li>
	    <li id="li1-navList"></li>
	    
	    <sec:authorize access="isAnonymous()">
			<li id="li2-mar0"><a id="loginH" href="/login">로그인</a></li>
			<li id="li2"><a id="joinH" href="/register.go">회원가입</a></li>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<li id="li2-mar0"><a id="adminH" href="/admin/userlist">관리</a></li>
			<li id="li2"><a id="logoutH" href="/logout">로그아웃</a></li>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_USER') or hasRole('ROLE_MANAGER')">
			<li id="li2-mar0"><a id="adminH" href="/user/mypage">마이페이지</a></li>
			<li id="li2"><a id="logoutH" href="/logout">로그아웃</a></li>
		</sec:authorize>
	  </ul>
	</nav>
	
</header>