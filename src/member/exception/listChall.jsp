<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>작심삼일</title>
 <link rel="stylesheet" href="../../../css/bootstrap.min.css"> 
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard-dynamic-subset.css" />
<style>
body {
	font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
}
</style>
  <style>
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
    .challtable{
    	padding: 15px 10px;
    }
  </style>
</head>
    

<body>

	<div class="wrap">
  <nav class="navbar navbar-expand-sm bg-light justify-content-center">
	  <ul class="navbar-nav mr-auto">
	    <li class="nav-item">
	      <a class="nav-link" href="/chall/cate.do?cate_no=1">운동</a>
	    </li>
	    <li class="nav-item">
	      <a class="nav-link" href="/chall/cate.do?cate_no=2">생활습관</a>
	    </li>
	    <li class="nav-item">
	      <a class="nav-link" href="/chall/cate.do?cate_no=3">자기계발</a>
	    </li>
	  </ul>
 	</nav> 
 	
 	<div>
 	<%--검색기능 구현 --%>
<form action="search.do?" method="get">
	
	<select name="option" class="form-select" style="width:100px; display:inline;">
		<option value="chall_title">제목</option>
		<option value="chall_con">내용</option>
	</select>
		<input type="search" name="searchCon" id="searchCon" placeholder="검색어를 입력하세요" maxlength="100" class="form-control" style="width:200px; display:inline;"/>
		<input type="submit" class="btn btn-outline-primary" style="margin: 0px 0px 2px 5px " value="검색"/>
		<c:if test="${AUTH_USER.id != null}"> <a href="/chall/write.do" class="btn btn-outline-primary" style="float: right">챌린지 등록하기</a></c:if>
</form>

<div class="row">
<div class="card-columns">
  <c:set var="i" value="0" />
  	<c:set var="j" value="3" />
	  <table >
	  	<c:forEach var="chall" items="${challPage.content}" varStatus="status">
  		<c:if test="${i%j == 0}">
  		<tr>
  		</c:if>
  			<td class="challtable"> 
  			<a href="<%=request.getContextPath()%>/chall/read.do?no=${chall.challNo}&pageNo=${nowPage}">
  			<div class="card">
  				
                <img class="card-img-top" src="/img/${chall.cateNo}.jpg" alt="Card image cap" style="width:350px; height: 240px;" />
                
                <c:if test="${chall.memNo == 1}"><span class="badge bg-primary"  style="width:50px; margin: 15px;">공식</span></c:if>
                <c:if test="${chall.memNo != 1}"><span class="badge bg-light" style="width:100px; margin: 15px;">${chall.nickname}</span></c:if>
                <div class="card-body">
                    <a >${chall.challTitle}</a>
                    <p class="card-text comment" style="float:right;">${chall.cateName}</p>
                </div>
            </div>
                </a>
            </td>
  		<c:if test="${i%j == j-1}">
  		</tr>
  		</c:if>
  		<c:set var="i" value="${i+1}" />
  		</c:forEach>
  	  </table>
</div><!-- card-columns -->
</div><!-- row -->
  
  </div> <!-- 검색~본문 -->
  </div> <!-- wrap -->
	
 	
 	<%--paganation 출력부분   --%>
 	 <nav aria-label="Page navigation">
	 <ul class="pagination justify-content-center" style="margin:0 0">
 	  <c:if test="${challPage.startPage>5}">
	   <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/chall/<c:choose><c:when test="${param.searchCon!=null}">search.do</c:when><c:otherwise>list.do</c:otherwise></c:choose>?pageNo=${challPage.startPage-5}&option=${option}&searchCon=${searchCon}">이전</a></li>
	  </c:if> 
	  
	  <c:forEach var="pNo"  begin="${challPage.startPage}" 
						    end="${challPage.endPage}"  step="1">
	   <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/chall/<c:choose><c:when test="${param.searchCon!=null}">search.do</c:when><c:otherwise>list.do</c:otherwise></c:choose>?pageNo=${pNo}&option=${option}&searchCon=${searchCon}">${pNo}</a></li>
	  </c:forEach>
	   
	  <c:if test="${challPage.endPage< challPage.totalPages}"> 
	   <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/chall/<c:choose><c:when test="${param.searchCon!=null}">search.do</c:when><c:otherwise>list.do</c:otherwise></c:choose>?pageNo=${challPage.startPage+5}&option=${option}&searchCon=${searchCon}">다음</a></li>
	  </c:if> 
	 </ul> 	
    </nav>
    

</body>
</html>
