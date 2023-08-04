<%@page import="notice.model.Notice" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" />

<!DOCTYPE html>
<html lang="ko">

<head>

	<%-- Bootstrap 반응형에 맞게 설정, css파일의 스타일 참조설정 --%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

 	 <title>우리들의 미니멀 챌린지::작심삼일</title>
	<!-- bootswatch united theme -->
	<link rel="stylesheet" href="../../../css/bootstrap.min.css"> 
	<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	<!-- custom-font.css를 추가합니다 -->
    <link rel="stylesheet" type="text/css" href="../css/pretendard.css" />
</head>

<style>

body {
	font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
}
</style>
 <style>
    .searchTool {
        text-align: center;
        margin-top: 20px;
        margin: 0 auto;
        display: flex; /* Use flexbox layout */
        justify-content: center; /* Center the content horizontally */
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

    /* pill 버튼들 사이의 간격을 띄우기 위해 마진 추가 */
    .nav-pills .nav-link {
        margin-right: 10px;
    }

    /* 버튼 크기를 키우기 위해 패딩과 폰트 사이즈 조절 */
    .nav-pills .nav-link {
        padding: 10px 20px; /* 원하는 패딩값 조정 */
        font-size: 16px; /* 원하는 폰트 사이즈 조정 */
    }
  
  </style>
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

	<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
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
    
	
	<!--  상단 탭 버튼--> 
<div class="tab mb-4">
   <ul class="nav nav-pills">
        <li class="nav-item">
            <a class="nav-link active" href="${cPath}/notice/list.do">공지사항</a>
        </li>
         <li class="nav-item">
            <a class="nav-link active" href="${cPath}/event/list.do">이벤트</a>
        </li>
    </ul>
</div>

			
	<!-- ---------- main 공지사항 리스트 ----------------- -->
	
	 <%-- NoticeListController로 부터 
 	 request.setAttribute("nowPage", pageNo); //현재페이지
     request.setAttribute("articlePage", articlePage); 
 
	 NoticePage noticePage에는  
	      총 게시글수포함(getTotal()호출)
		  article목록포함(getContent()호출) 
		  int  totalPages;	//총페이지수   
		  int  startPage;	//시작페이지  
		  int  endPage;	//끝페이지--%> 


	<!-- Board List table -->
		<table class="table table-hover" style="text-align:center; margin: 0 auto; min-width: 800px;">
			<thead>
				<tr class="table-active">
					<th style="text-align: center;">공지번호</th>
					<th style="text-align: center;">제목</th>
					<th style="text-align: center;">작성일</th>
				</tr>
			</thead>
			<tbody><%-- 총게시글(수가 0이면)없으면  true리턴, 그렇지 않으면 false --%>
			<c:choose>
			    <c:when test="${empty noticePage.content}">
			        <tr>
			            <td colspan="3">게시글이 존재하지 않습니다.</td>
			        </tr>
			    </c:when>
			    <c:otherwise>
			        <!-- 검색결과 있으면 가져온 가지고 올 list들을 list로 형변환 -->
			        <c:forEach var="n" items="${noticePage.content}">
			            <tr>
			                <!-- read.do?no=상세하게보고싶은글번호&pageNo=현재페이지 -->
			                <td class="title indent text-align-left">${n.notiNo}</td> <%-- 공지번호 --%>
			                <td class="title indent text-align-left">
			                    <a href="${cPath}/notice/read.do?notiNo=${n.notiNo}&pageNo=${nowPage}">${n.notiTitle}</a>
			                </td>
			                <td>${n.notiDate}</td> <!-- 공지날짜 -->
			            </tr>
			        </c:forEach>
			    </c:otherwise>
			</c:choose>

			</tbody>
		</table>


<!--  공지 작성 버튼  -->		
		<div class="container text-right mt-4 d-flex justify-content-end"> <!-- 버튼과의 간격을 추가하기 위해 mt-4 (마진 탑 4) 클래스를 적용 -->
		    <c:if test="${user.id eq 'admin'}">
		        <a href="${cPath}/notice/write.do" class="btn btn-primary">공지작성</a>
		    </c:if>
		</div>
		
<!-- - 하단 검색창  -->
		<div class=searchTool style="margin-left:auto;margin-right:auto;">
			 <form method="get" name="search" action="${cPath}/notice/list.do"> 
				<table class="pull-right">
					<tr>
						<!--  name, id 검색 옵션 입력받은 파라미터 명이 됨  -->
						<td><select class="form-control" name="searchField">
								<option value="0">선택</option>
								<option value="noti_title">제목</option>
								<option value="noti_con">내용</option>
						</select></td>
						<td><input type="text" name="searchText" id="searchText"
							class="form-control" placeholder="검색어 입력"  maxlength="100"></td>
						<td><button type="submit" class="btn btn-outline-primary">검색</button></td>
					</tr>

				</table>
			</form>
			</div>
			
		
	<%--페이지네이션 출력부분 --------------------------------------------   --%>
	<nav aria-label="Page navigation"> <!--  justify-content-center클래스는 가운데정렬  -->
	    <ul class="pagination justify-content-center mt-4" style="margin:0 0">
    
        <%--<c:if>를 이용하여 노출여부가 달라진다 --%>
        <c:if test="${noticePage.startPage > 5}">
            <li class="page-item"><a class="page-link" href="${cPath}/notice/list.do?pageNo=${noticePage.startPage-5}">prev</a></li>
        </c:if>

        <c:forEach var="pNo" begin="${noticePage.startPage}" end="${noticePage.endPage}" step="1">
            <li class="page-item"><a class="page-link" href="${cPath}/notice/list.do?pageNo=${pNo}">${pNo}</a></li>
        </c:forEach>

        <%--<c:if>를 이용하여 노출여부가 달라진다 --%>
        <c:if test="${noticePage.endPage < noticePage.totalPages}">
            <li class="page-item"><a class="page-link" href="${cPath}/notice/list.do?pageNo=${noticePage.startPage+5}">next</a></li>
        </c:if>
    </ul>
</nav>
</div> <!--  container 닫기 -->
	</div> <!-- bodyContent 닫기 -->	
</body>
<!-- Bootstrap JS (jQuery가 필요) -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</html>
