<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <!-- Bootstrap 4 CSS -->
 <link rel="stylesheet" href="../../bootstrap/bootstrap.min.css">
 <link href="https://webfontworld.github.io/gmarket/GmarketSans.css" rel="stylesheet">
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<style>
body {
    font-family: 'GmarketSans', sans-serif;
    background-color: #FFF;
}

.nickname{
font-size:26px;
color:#fc7c24;
}
</style>
<title>우리들의 미니멀 챌린지::작심삼일</title>
</head>
<body>
<!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">	
  <form action="${cPath}/member/challList.do" method="get">
 <div class="container">
	<h1  class="mt-5 mb-4">회원챌린지목록</h1><br/>
	<h5 class=""><span class="nickname">${member.mem_name} </span> 님의 챌린지 목록 입니다</h5>
	<input type="hidden" name="no" id="no" value="${mem_no}"/>
 	
 	<!-- Board List table -->
 	<div style="text-align: right;">총 챌린지수 : ${memberChallListPage.total}명  | 현재페이지 : ${nowPage}
 	<c:set var="oldPageNo" value="${empty param.oldPageNo?1:param.oldPageNo}" /> 
 	</div>
 	<table class="table table-hover">
 	 <thead  class="thead-light">
 	  <tr>
 	  	<th scope="col">챌린지 관리 번호</th>
 	  	<th scope="col">챌린지 번호</th>
 	  	<th scope="col">챌린지 이름</th>
 	  	<th scope="col">회원번호</th>
 	  	<th scope="col">진행 상태</th>
 	  	<th scope="col">시작일</th>
 	  	<th scope="col">완료일</th>
 	  	<th scope="col">결과</th>
 	  	<th scope="col">수정</th>
 	  </tr>
 	 </thead>
 	 <tbody> <%-- 총게시글(수가 0이면)없으면  true리턴, 그렇지 않으면 false --%>
 	  <c:if test="${memberChallListPage.hasNoChallenges()}"> 
 	  <tr>
 	  	<td colspan="9" style="text-align: center;">참여한 챌린지가 없습니다</td>
 	  </tr>
 	  </c:if>
 	 
 	  <%-- <c:forEach>반복문이용 1페이지당 출력할 게시글수 만큼 반복 출력 시작 
 	  <c:forEach var="변수명"  items="배열명 또는 컬렉션" >--%>
 	  <c:forEach var="chall"  items="${memberChallListPage.content}" >
 	  <tr>
 	  	<td>${chall.chall_manual_no}</td>
 	  	<td>${chall.chall_no}</td>
 	  	<td>${chall.chall_title}</td>
 	  	<td>${chall.mem_no}</td>
 	  	<td>${chall.chall_ing_status}</td>
 	  	<td><fmt:formatDate value="${chall.start_date}" pattern="yyyy/MM/dd"/></td>
 	  	<td><fmt:formatDate value="${chall.final_date}" pattern="yyyy/MM/dd"/></td>
 	  	<td>${chall.chall_result}</td>
 	  	<td>
 	  	<a href="${cPath}/member/challModify.do?memNo=${chall.mem_no}&no=${chall.chall_manual_no}&pageNo=${nowPage}&oldPageNo=${oldPageNo}">수정</a>
 	  	</td>
 	  </tr>
 	  </c:forEach>
 	 </tbody>
 	</table>
    <a href="${cPath}/member/list.do?pageNo=${oldPageNo}" class="btn btn-outline-primary">회원 목록</a>
    <input type="hidden" name="oldPageNo" id="oldPageNo" value="${oldPageNo}"/>
 	<br/>
    <br/>
 	

 	<div class="input-group mb-3" style="width:500px; margin-left:auto;margin-right:auto;">
 	<select name="option" id="option" class="form-select" style="border-radius: 6px;">
 	 <option value="s.chall_manual_no">챌린지 관리 번호</option>
 	 <option value="s.chall_no">챌린지 번호</option>
 	 <option value="b.chall_title">챌린지 이름</option>
 	 <option value="s.chall_result">결과</option>
 	</select>
      <input type="text" name="searchCon" id="searchCon" placeholder="검색어를 입력하세요" class="form-control" aria-describedby="button-addon2" style="margin:0px 9px;border-radius: 6px;"/>
      <button type="submit" id="button-addon2" class="btn btn-secondary my-2 my-sm-0" style="border-radius: 6px;">검색</button>
    </div>
    <br/>
 	<%--paganation 출력부분 --------------------------------------------   --%>
<c:if test="${memberChallListPage.startPage!=0}">
  <ul class="pagination"  style="justify-content: center;">
 	<c:if test="${memberChallListPage.startPage>5}">
   <li class="page-item">
		<a href="${cPath}/member/challList.do?pageNo=${memberChallListPage.startPage-5}&no=${mem_no}&option=${option}&searchCon=${searchCon}&oldPageNo=${oldPageNo}" class="page-link">&lt;&lt;이전</a>
   </li>
	</c:if>
	<c:forEach var="pNo" begin="${memberChallListPage.startPage}" end="${memberChallListPage.endPage}" step="1">
   <li class="page-item">
		 <a href="${cPath}/member/challList.do?pageNo=${pNo}&no=${mem_no}&option=${option}&searchCon=${searchCon}&oldPageNo=${oldPageNo}" class="page-link">${pNo}</a>
   </li>
	</c:forEach>
	<c:if test="${memberChallListPage.endPage<memberChallListPage.totalPages}">
   <li class="page-item">
		<a href="${cPath}/member/challList.do?pageNo=${memberChallListPage.startPage+5}&no=${mem_no}&option=${option}&searchCon=${searchCon}&oldPageNo=${oldPageNo}" class="page-link">next&gt;&gt;</a>
   </li>
	</c:if> 	
 </ul>
</c:if>
 </div>
  </form>
 <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</div>
</body>
</html>