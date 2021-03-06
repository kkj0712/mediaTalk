<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>               

	<div class="jumbotron">
		
		<div id="boxDiv">
			<h1 class="display-4">베스트셀러</h1><hr/>
			<p class="lead">${targetDt} 기준 / 인터파크 베스트셀러</p>
		</div>
		
		<div id="slick--div">
		  	<div class="autoplay">
			    <c:forEach items="${list}" var="book">
			    	<div class="card--my">
			    		<div class="card--title">${book.rank}</div>
						<div class="card--body">
							<img src='${book.cover}' onclick="location.href='${book.link}'" alt='${book.bookNm}' style="width: 180px; height: 260px;">
						</div>
					</div>
				</c:forEach>
		  	</div>
		</div>
    </div>
<script type="text/javascript" src="/resources/js/slick.js"></script>
<script>
$('.autoplay').slick({
	  slidesToShow: 5,
	  slidesToScroll: 1,
	  autoplay: true,
	  dots : true, 
	  autoplaySpeed: 2000,
	  draggable : true, 	//드래그 가능 여부 
	  responsive: [ // 반응형 웹 구현 옵션
			{  
				breakpoint: 960, //화면 사이즈 960px
				settings: {
					//위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
					slidesToShow:3 
				} 
			},
			{ 
				breakpoint: 768, //화면 사이즈 768px
				settings: {	
					//위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
					slidesToShow:2 
				} 
			}
		]
});
</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>   