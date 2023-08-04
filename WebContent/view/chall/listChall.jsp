<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>우리들의 미니멀 챌린지::작심삼일</title>
 <link rel="stylesheet" href="../../../css/bootstrap.min.css"> 
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard-dynamic-subset.css" />
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<style>

body {
	font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
}
      .wrap {
          width: 1100px;
          margin: auto;
      }

      .comment {
          color: orange;
          font-weight: bold;
      }
      
      .col {
        padding: 30px; /* 원하는 간격으로 조정 */
    }
   .challtable {
      padding: 15px 10px;
    }
    
    /* carousel 관련 스타일 설정 */
    .carousel-inner img {
      max-width: 1300px;
      max-height: 400px;
      width: auto;
      height: auto;
      margin: 0 auto;
    }

    /* 이전/다음 버튼 스타일 설정 */
    .carousel-control-prev,
    .carousel-control-next {
   	 position: absolute;
   	  top: 70%;
      width: 5%;
      opacity: 0.5;
      background-color: rgba(255, 255, 255, 0.5);
      z-index: 10;
    }
	


    /* 버튼에 마우스를 올렸을 때의 스타일 설정 */
    .carousel-control-prev:hover,
    .carousel-control-next:hover {
      opacity: 1;
    }

    /* 미디어 쿼리를 사용하여 화면 크기에 따라 버튼 위치 조정 */
    @media (max-width: 1300px) {
      .carousel-control-prev,
      .carousel-control-next {
        right: auto;
        left: 0;
      }
    }
    
  </style>
   <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
  <script>
    //슬라이드 속도 설정 
    $(document).ready(function () {
      $('#slideBnr').carousel({ 
        interval: 1800 
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
</head>
    

<body>
 <!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">
    <!--  슬라이드 배너 -->
    <div class="slideBnr-container mb-4">
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
              <img src="/img/mainBnr/bnrT1.jpg" class="d-block w-100" alt="배너1">
            </div>
            <div class="carousel-item">
              <img src="/img/mainBnr/bnrT2.jpg" class="d-block w-100" alt="배너2">
            </div>
            <div class="carousel-item">
              <img src="/img/mainBnr/bnrT3.jpg" class="d-block w-100" alt="배너3">
            </div>
          </div><!-- /.carousel-inner -->
          <!-- 슬라이드 좌우 이동 -->
          <a class="carousel-control-prev" href="#slideBnr" role="button" data-slide="prev">
	        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	        <span class="sr-only">Previous</span>
	      </a>
	      <a class="carousel-control-next" href="#slideBnr" role="button" data-slide="next">
	        <span class="carousel-control-next-icon" aria-hidden="true"></span>
	        <span class="sr-only">Next</span>
	      </a>
        </div><!-- /.carousel -->
      </div>
    </div><!-- /.slideBnr-container -->
    
    <div class="container">
    
      <div class="search mb-4">
        <%--검색기능 구현 --%>
        <form action="${cPath}/chall/search.do?" method="get">
          <select name="option" class="form-select" style="width:100px; display:inline;">
            <option value="chall_title">제목</option>
            <option value="chall_con">내용</option>
          </select>
          <input type="search" name="searchCon" id="searchCon" placeholder="검색어를 입력하세요" maxlength="100" class="form-control" style="width:200px; display:inline;"/>
          <input type="submit" class="btn btn-outline-primary" style="margin: 0px 0px 2px 5px " value="검색"/>
          <c:if test="${AUTH_USER.id != null}"> <a href="/chall/write.do" class="btn btn-outline-primary" style="float: right">챌린지 등록하기</a></c:if>
        </form>

<%-- --- 챌린지 카드 3열로 --- --%>
<div class="container">
  <div class="row">
  <c:forEach var="chall" items="${challPage.content}" varStatus="status">
    <div class="col-4">
      <div class="card mb-2 ml-2 mt-4">
         <a href="<%=request.getContextPath()%>/chall/read.do?no=${chall.challNo}&pageNo=${nowPage}">
        <img class="card-img-top" src="/img/${chall.cateNo}.jpg" style="height:225px"/>
         <c:if test="${chall.memNo == 1}">
            <span class="badge bg-primary" style="width:50px; margin: 15px;">공식</span>
          </c:if>
          <c:if test="${chall.memNo != 1}">
            <span class="badge bg-light" style="width:100px; margin: 15px;">${chall.nickname}</span>
          </c:if>
         <div class="card-body">
          <a>${chall.challTitle}</a>
          <p class="card-text comment" style="float:right;">${chall.cateName}</p>
        </div> <%-- card-body --%>
        </a>
      </div> <%-- card --%>
   	 </div> <%--col-4 --%>
    </c:forEach>
	</div><%-- container --%>
  
  </div> <!-- 검색~본문 -->

      
      
 	<%--paganation 출력부분   --%>
 	<nav aria-label="Page navigation">
	 <ul class="pagination justify-content-center" style="margin:0 0">
 	  <c:if test="${challPage.startPage>5}">
	   <li class="page-item"><a class="page-link" href="${cPath}/chall/<c:choose><c:when test="${param.searchCon!=null}">search.do</c:when><c:otherwise>list.do</c:otherwise></c:choose>?pageNo=${challPage.startPage-5}&option=${option}&searchCon=${searchCon}">이전</a></li>
	  </c:if> 
	  
	  <c:forEach var="pNo"  begin="${challPage.startPage}" 
						    end="${challPage.endPage}"  step="1">
	   <li class="page-item"><a class="page-link" href="${cPath}/chall/<c:choose><c:when test="${param.searchCon!=null}">search.do</c:when><c:otherwise>list.do</c:otherwise></c:choose>?pageNo=${pNo}&option=${option}&searchCon=${searchCon}">${pNo}</a></li>
	  </c:forEach>
	   
	  <c:if test="${challPage.endPage< challPage.totalPages}"> 
	   <li class="page-item"><a class="page-link" href="${cPath}/chall/<c:choose><c:when test="${param.searchCon!=null}">search.do</c:when><c:otherwise>list.do</c:otherwise></c:choose>?pageNo=${challPage.startPage+5}&option=${option}&searchCon=${searchCon}">다음</a></li>
	  </c:if> 
	 </ul> 	
    </nav>
	</div>
</div> <!-- container -->
</body>

</html>
