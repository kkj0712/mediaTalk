<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link rel="stylesheet" href="resources/css/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<form method="post" id="joinForm" class="modal-content animate" novalidate>
	<h1 id="logo-h1"><a href="/">MediaTalk</a></h1>
	<h3 id="font-h3">회원가입</h3>
	<input type="text" placeholder="아이디" name="username" id="username"	required>
	<input type="text" placeholder="이메일" name="email"	id="email" class="email" required>
	<input type="password" placeholder="비밀번호" name="password" id="password" required><br/>

	<!--recaptcha-->
	<div class="g-recaptcha" data-sitekey="6LdtH-IZAAAAAL1hGI9zXM9Fvx5VJUKfKEzCqynA" style="text-align: center;"></div><br/>
	<input type="submit" id="joinBtn" name="joinBtn" value="회원가입">
</form>
<div class="form--div">
	<div id="info">
		<span>이미 가입하셨나요? <a id="loginTag" href="/login">로그인</a></span>
	<hr>
	</div>
	<a href="/oauth2/authorization/google" id="googleBtn"><img src="/resources/img/google.png" id="googleImg"> 구글 로그인</a>
	<a href="/oauth2/authorization/facebook" id="facebookBtn"><img src="/resources/img/facebook.png" id="facebookImg"> 페이스북 로그인</a>
</div>
<script src="/resources/js/user.js"></script>
<script>
window.onload = function() {
    $('.g-recaptcha > div').css("width","").css("height","");
}
</script>