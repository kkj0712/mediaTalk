$(function(){
	//회원가입 유효성 검사
	var joinValid=$("#joinForm").validate({
		rules:{
			username:{required : true, minlength : 2, remote:{type:"post", url:"nameCheck"}},
			email:{required : true, email : true, remote:{type:"post", url:"emailCheck"}},
			password:{required : true, rangelength:[8,16]}
		},
		messages:{
			username:{
				required : "아이디는 필수 입력입니다.",
				minlength : "최소 2글자 이상 입력해주세요.",
				remote: "이미 존재하는 아이디입니다."
			},
			email: {
				required : "이메일은 필수 입력입니다.",
				email : "이메일 형식이 아닙니다.", 
				remote: "이미 존재하는 이메일입니다."
			},
			password:{
				required : "비밀번호는 필수 입력입니다.",
				rangelength : "8~16 이내로 입력해주세요." 
			}
		},
		errorElement : 'span', 
		errorClass: 'error',
		validClass: 'vaild' ,
		
	    highlight: function(errorElement) {$(errorElement).css('background', '#ffdddd');},
	    unhighlight: function(errorElement) {$(errorElement).css('background', '#e6ffe6');},

		submitHandler: function(form) {
            $.ajax({
                url: '/VerifyRecaptcha', 
                type: 'POST',
                data: {
					recaptcha: $("#g-recaptcha-response").val()
				},
                success: function (resp) {
					if(resp==1){
						form.submit();
						alert("회원가입 되었습니다.");
					}else{
						alert("reCaptcha 검사를 해주세요");
					}
                } //success
            }); //ajax
        } //submitHandler 
	}); //회원가입 유효성 검사 끝
	
	//로그인 유효성 검사
	var loginValid=$("#loginForm").validate({
		rules:{
			username:{required : true, minlength : 2},
			password:{required : true, rangelength:[8,16]}
		},
		messages:{
			username: {
				required : "아이디는 필수 입력입니다.",
				minlength : "최소 2글자 이상 입력해주세요."
			},
			password:{
				required : "비밀번호는 필수 입력입니다.",
				rangelength : "8~16 이내로 입력해주세요." 
			}
		},
		errorElement : 'span', 
		errorClass: 'error',
		validClass: 'vaild' ,
		
	    highlight: function(errorElement) {$(errorElement).css('background', '#ffdddd');},
	    unhighlight: function(errorElement) {$(errorElement).css('background', '#e6ffe6');}
	});//로그인 유효성 검사 끝
	
	//회원정보 수정 버튼 눌렀을때
	$("#updateBtn").on("click", function(){
		var dataParam={
			"id":$("#id").val(),
			"email":$("#emailView").val(),
			"grade": $("#grade").val(),
			"role":$("#role").val()
		}
		var id=$("#id").val();
		$.ajax({
			type:"PUT",
			url:"/admin/update/"+id,
			data: JSON.stringify(dataParam),
			contentType: "application/json;charset=utf-8",
		})
		.done(function(resp){
			alert(resp+" 님 정보 수정 완료");
		})
		.fail(function(error){
			alert(error+"error");
		})
	}); //회원정보 수정 버튼 눌렀을때 끝
	
	//회원정보 삭제 버튼 눌렀을때
	$("#deleteBtn").on("click", function(){
		if(confirm("정말 삭제하시겠습니까?")){
			var dataParam={
			    "id":$("#id").val(),
				"email":$("#emailView").val()
			}
			$.ajax({
				type:"DELETE",
				url: "/admin/delete/"+$("#id").val(),
				data: JSON.stringify(dataParam),
				contentType:"application/json;charset=utf-8",
			})
			.done(function(resp){
				alert(resp+" 회원 삭제 완료");
				location.href="/admin/userlist";
			})
			.fail(function(error){
				alert(error+"error");
			})
		}
	});//회원정보 삭제 버튼 눌렀을때 끝
	
	//마이페이지 회원정보 수정
	$("#myUpdateBtn").on("click", function(){
		//공백일때
		if($("#passwordView").val()=="" || $("#password_check").val()==""){
			$("#attention").text("비밀번호를 입력하세요");
			$("#pwd").focus();
			return false;
		}else {$("#attention").text("");}
		//암호 일치확인
		if($("#passwordView").val()!=$("#password_check").val()){
			$("#attention").text("비밀번호를 불일치");
			$("#password_check").focus();
			return false;
		}else {$("#attention").text("");}
		//암호 길이 확인
		if($("#passwordView").val().length<8 || $("#passwordView").val().length>16){
	        $("#attention").text("비밀번호는 8글자~16글자 사이여야 합니다.");
	        return false;
	    }else {$("#attention").text("");}
	    //암호확인 길이 확인
		if($("#password_check").val().length<8 || $("#password_check").val().length>16){
	        $("#attention").text("비밀번호는 8글자~16글자 사이여야 합니다.");
	        return false;
	    }else {$("#attention").text("");}

	    //수정하기
		$.ajax({
			type: "put",
			url: "/user/mypage/update",
			data: {
				"id": $("#id").val(),
				"password": $("#password_check").val()
			},
			success:function(){
				alert("비밀번호 변경 완료");
			},
			error: function(e){
				alert("error"+e);
			}
		});
	}); //마이페이지 회원정보 수정 끝

	//마이페이지 회원탈퇴
	$("#myDeleteBtn").on("click", function(){
		if(confirm("정말 탈퇴하시겠습니까?")){
			var id=$("#id").val();
			$.ajax({
				type:"DELETE",
				url: "/user/mypage/delete/"+id,
				data: JSON.stringify(id),
				contentType:"application/json;charset=utf-8",
			})
			.done(function(resp){
				alert("탈퇴 완료");
				location.href="/";
			})
			.fail(function(error){
				alert(error+"error");
			})
		}
	});//마이페이지 회원탈퇴 끝
}); //ready