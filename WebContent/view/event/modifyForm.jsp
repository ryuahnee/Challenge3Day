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
	<h2 class="mt-5 mb-4 text-center">이벤트 게시글 수정</h2>
	<form action="${cPath}/event/modify.do" method="post">
		<div>
			<label for="no">글번호</label>
			<input type="text" name="no" id="no" class="form-control" value="${mer.event_no}" readonly="readonly" />
		</div>
		<div>
			<label for="title">제목</label>
			<input type="text" name="title" id="title" class="form-control" value="${mer.event_title}" />
		</div>
		<div>
			<label for="content">내용</label>
			<textarea name="content" id="content" class="form-control">${mer.event_con}</textarea>
		</div>
		<div class="form-group">
			<a>본문의 신청버튼 생성 여부 : ${mer.has_btn}</a>
			<input type="radio" id="participation01" name="participation" value="Y"> 신청버튼 생성
			<input type="radio" id="participation02" name="participation" value="N"> 신청버튼 생성하지 않음
		</div>
		
		<div style="float:right">
			<a href="${cPath}/event/list.do" class="btn btn-outline-primary" style="magin:10px;">목록 보기</a>
			<button type="submit" class="btn btn-warning" style="magin:10px;">수정하기</button>
			<a href="${cPath}/event/delete.do?no=${mer.event_no}" class="btn btn-danger" style="magin:10px;">삭제하기</a>
			<button  type="reset" class="btn btn-outline-secondary" style="magin:10px;">수정 취소하기</button>
		</div>
	</form>
	</div>
</div>
</body>
</html>