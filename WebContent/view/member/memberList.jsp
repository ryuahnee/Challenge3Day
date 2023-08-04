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
<link rel="stylesheet" href="../../bootstrap/bootstrap.css">
 <link href="https://webfontworld.github.io/gmarket/GmarketSans.css" rel="stylesheet">
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<style>
body {
    font-family: 'GmarketSans', sans-serif;
    background-color: #FFF;
}
</style>
<title>우리들의 미니멀 챌린지::작심삼일</title>
<script src="../../js/jquery-3.7.0.min.js"></script>
</head>
<body>
<!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">	
 <form action="${cPath}/member/list.do" method="get">
 <div class="container">
	<h1  class="mt-5 mb-4">회원목록</h1>
 	
 	<!-- Board List table -->
 	<div style="text-align: right;">총 회원수 : ${memberPage.total}명  | 현재페이지 : ${nowPage}</div>
 	<table class="table table-hover">
 	 <thead  class="thead-light">
 	  <tr>
 	  	<th scope="col">No</th>
 	  	<th scope="col">ID</th>
 	  	<th scope="col">회원명</th>
 	  	<th scope="col">닉네임</th>
 	  	<th scope="col">생년월일</th>
 	  	<th scope="col">성별</th>
 	  	<th scope="col">가입일</th>
 	  	<th scope="col">이메일</th>
 	  	<th scope="col">마케팅 수신 동의</th>
 	  	<th scope="col">챌린지</th>
 	  </tr>
 	 </thead>
 	 <tbody> <%-- 총게시글(수가 0이면)없으면  true리턴, 그렇지 않으면 false --%>
 	  <c:if test="${memberPage.hasNoArticles()}"> 
 	  <tr>
 	  	<td colspan="10" style="text-align: center;">회원이 존재하지 않습니다</td>
 	  </tr>
 	  </c:if>
 	 
 	  <%-- <c:forEach>반복문이용 1페이지당 출력할 게시글수 만큼 반복 출력 시작 
 	  <c:forEach var="변수명"  items="배열명 또는 컬렉션" >--%>
 	  <c:forEach var="member"  items="${memberPage.content}" >
 	  <tr>
 	  	<td>${member.mem_no}</td>
 	  	<td><a href="${cPath}/member/detail.do?no=${member.mem_no}&pageNo=${nowPage}">${member.id}</a></td>
 	  	<td>${member.mem_name}</td>
 	  	<td>${member.nickname}</td>
 	  	<td><fmt:formatDate value="${member.birthyear}" pattern="yyyy/MM/dd"/></td>
 	  	<td>${member.gender}</td>
 	  	<td><fmt:formatDate value="${member.join_date}" pattern="yyyy/MM/dd"/></td>
 	  	<td>${member.mem_email}</td>
 	  	<td>${member.isMarketing}</td>
 	  	<td>
 	  	<a href="${cPath}/member/challList.do?no=${member.mem_no}&oldPageNo=${nowPage}">조회</a>
 	  	</td>
 	  </tr>
 	  </c:forEach>
 	 </tbody>
 	</table>
 	<br/>
 	
 	<%--검색창 --------------------------------------------   --%>
    <div class="input-group mb-3" style="width:500px; margin-left:auto;margin-right:auto;">
 	<select name="option" id="option" class="form-select" style="border-radius: 6px;">
 	 <option value="mem_name">이름</option>
 	 <option value="id">아이디</option>
 	 <option value="nickname">닉네임</option>
 	 <option value="mem_email">이메일</option>
 	</select>
      <input type="text" name="searchCon" id="searchCon" placeholder="검색어를 입력하세요" class="form-control" aria-describedby="button-addon2" style="margin:0px 9px;border-radius: 6px;"/>
      <button type="submit" id="button-addon2" class="btn btn-secondary my-2 my-sm-0" style="border-radius: 6px;">검색</button>
    </div>
    <br/>

 	<%--paganation 출력부분 --------------------------------------------   --%>
 	<c:if test="${memberPage.startPage!=0}">
 	<ul class="pagination"  style="justify-content: center;">
 	 <c:if test="${memberPage.startPage>5}">
 	  <li class="page-item">
		<a href="${cPath}/member/list.do?pageNo=${memberPage.startPage-5}&option=${option}&searchCon=${searchCon}" class="page-link">&lt;&lt;이전</a>
 	  </li>
 	 </c:if>
	<c:forEach var="pNo" begin="${memberPage.startPage}" end="${memberPage.endPage}" step="1">
 	  <li class="page-item">
		 <a href="${cPath}/member/list.do?pageNo=${pNo}&option=${option}&searchCon=${searchCon}" class="page-link">${pNo}</a> 
 	  </li>
	</c:forEach>
	<c:if test="${memberPage.endPage<memberPage.totalPages}">
 	  <li class="page-item">
		<a href="${cPath}/member/list.do?pageNo=${memberPage.startPage+5}&option=${option}&searchCon=${searchCon}" class="page-link">next&gt;&gt;</a>
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