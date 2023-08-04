<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- bootswatch united theme -->
	<link rel="stylesheet" href="../css/bootstrap.min.css">
 	<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
  	<link rel="stylesheet" href="css/slideBnr.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
 
 
    <title>자동롤링배너</title>
    
<style>

 .carousel-inner img {
     max-width: 1200px;
     max-height: 400px;
     width: auto;
     height: auto;
     margin: 0 auto;
 }



 /* 이전/다음 버튼 스타일 설정 */
 .carousel-control-prev,
 .carousel-control-next {
     width: 5%; /* 버튼 너비 조정 */
     opacity: 0.5; /* 버튼의 투명도 설정 */
     background-color: rgba(255, 255, 255, 0.5); /* 버튼 배경색 설정 */
     /*버튼 위치 조정합니다 */
     top: 50%;
     transform: translateY(-50%);
     z-index: 10; 
 }

 /* 버튼에 마우스를 올렸을 때의 스타일 설정 */
 .carousel-control-prev:hover,
 .carousel-control-next:hover {
     opacity: 1; /* 버튼의 투명도를 100%로 설정하여 투명도 없음으로 변경합니다. */
 }
 
/* 미디어 쿼리를 사용하여 화면 크기에 따라 버튼 위치 조정 */
@media (max-width: 1200px) {
    .carousel-control-prev,
    .carousel-control-next {
        /* 버튼 위치를 조정합니다 */
        right: auto;
        left: 0;
    }
}
 

</style>
<div class="slideBnr-container">
   <div class="row justify-content-center">
        <div id="slideBnr" class="carousel slide" data-ride="carousel">
            <!-- 이미지하단 버튼 -->
       	 	 <ol class="carousel-indicators">
                <li  data-target="#slideBnr" id="btn1" data-slide-to="0" class="active"
                    aria-current="true" aria-label="Slide 1"></li>
                <li data-target="#slideBnr" id="btn2" data-slide-to="1" aria-label="Slide 2"></li>
                <li  data-target="#slideBnr" id="btn3" data-slide-to="2" aria-label="Slide 3"></li>
            </ol>
            <!--  배너 이미지  -->
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="/img/mainBnr/bnrT1.jpg" class="d-block w-100" alt="배너테스트1">
                </div>
                <div class="carousel-item">
                    <img src="/img/mainBnr/bnrT2.jpg" class="d-block w-100" alt="배너테스트2">
                </div>
                <div class="carousel-item">
                    <img src="/img/mainBnr/bnrT3.png" class="d-block w-100" alt="배너테스트3">
                </div>
            </div>
            <!-- 슬라이드 좌우 이동 -->
            <button class="carousel-control-prev" data-target="#slideBnr" type="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </button>
            <button class="carousel-control-next" data-target="#slideBnr" type="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </button>
        </div>
    </div>
</div>
  
 <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

 <script>
 	//슬라이드 속도 설정 
 	$(document).ready(function () {
 	$('#slideBnr').carousel({ 
 		interval:1000 
 		});
 
 	//슬라이드 인디케이터 (하단버튼) 설정
	$("#btn1").click(function(){
	    $("#slideBnr").carousel(0);
	});
	  $("btn2").click(function(){
	    $("#slideBnr").carousel(1);
	});
	  $("#btn3").click(function(){
	    $("#slideBnr").carousel(2);
	});
	  
	//슬라이드 이전,다음 설정 
	$(".carousel-control-prev").click(function(){
   		 $("#slideBnr").carousel("prev");
  	});
 	
	$(".carousel-control-next").click(function(){
    	$("#slideBnr").carousel("next");
	});
});
	 	
</script>

 	
    	
