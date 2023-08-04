<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
</style>
</head>
<body>
  <!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">
 <div class="container">
	<h2  class="mt-4 mb-3 text-center">챌린지 상세보기</h2>
	
	<table class="table mt-3">
	 <tbody>
	  <tr>
	   <th scope="row" style="width:15%;">글번호</th>
	   <td>${chall.challNo}</td>
	  </tr>
	  <tr>
	   <th scope="row">챌린지 제목</th>
	   <td>${chall.challTitle}</td>
	  </tr>
	  <tr>
	   <th scope="row" style="width:15%;">카테고리명</th>
	   <td>${chall.cateName}</td>
	  </tr>
	  <tr>
	   <th scope="row">작성자 닉네임</th>
	   <td>${chall.nickname}</td>
	  </tr>
	  <tr>
	   <th scope="row">현재 진행중인 회원의 수</th>
	   <td>${isChall}</td>
	  </tr>
	  <tr>
	   <th scope="row">작성일</th>
	   <td><fmt:formatDate value="${chall.challWriteDate}"   pattern="yyyy년 M월 d일" /></td>
	  </tr>
	  <tr>
	   <th scope="row" >내용</th>
	   <td style="white-space: pre-wrap;">${chall.challCon}</td>
	  </tr>
</table>
</div>

 	
 	<div style="text-align: center;">
    <a href="${cPath}/chall/list.do" class="btn btn-outline-primary" >챌린지 목록보기</a>
    <c:if test="${AUTH_USER.id !='admin'}">
 		<a href="${cPath}/chall/challing.do?chall_no=${chall.challNo}" class="btn btn-outline-primary">도전하기</a><!-- 마이페이지로 이동하기로 바꾸기 -->
    </c:if>
    <c:if test="${AUTH_USER.id eq 'admin'}">
        <a href="${cPath}/chall/modify.do?no=${chall.challNo}" class="btn btn-outline-primary">챌린지 수정하기</a>
        <a href="${cPath}/chall/delete.do?no=${chall.challNo}" onclick="var result=confirm('정말 삭제하시겠습니까?'); if(result==true){return true;} else{return false;}" class="btn btn-outline-primary">챌린지 삭제하기</a>
    </c:if>
</div>
 	
 	
 	
 <c:forEach var="challopin" items="${challopin}"> 
   <div class="container">
 	 	<table class="table table-bordered mt-3">
 	  <tr>
	   <th scope="row" style="width:15%;">닉네임</th>
	   <td>${challopin.nickname}</td>
	  </tr> 
	   <tr>
	   <th scope="row">성공 소감</th>
	   <td>${challopin.opinCon}</td>
	  </tr>
	  </table>
	  </div> 
 </c:forEach> 
 
 </div>
</body>
</html>

