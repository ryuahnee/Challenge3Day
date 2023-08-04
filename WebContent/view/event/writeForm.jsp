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
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
	$("#writeForm").on("submit", function(){
		let titleO = $("#title");
		if(titleO.val()==""){
			alert("제목은 필수입력입니다.");
			titleO.focus();
			return  false;
		}
		let conO = $("#con");
		if(conO.val()==""){
			alert("내용은 필수입력입니다.");
			conO.focus();
			return  false;
		}
		//라디오 타입 필수 입력
		let participationChecked = $("input[name='participation']").is(":checked");
		console.log(participationChecked);
		if(!participationChecked){
			alert("신청버튼 생성 유무를 선택하세요.");
			return  false;
		}
		
		
		//필수입력되었으면 submit진행
		$("#writeForm").attr("action", "/event/write.do");
		$("#writeForm").attr("method", "post");
		return ture;
	});
	
});
</script>
</head>
<body>
 <!-- Navigation -->
 <!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">
  
 <div class="container">
<h2 class="mt-5 mb-4 text-center">이벤트 글 작성 하기</h2>
<form id="writeForm" action="${cPath}/event/write.do" method="post">

<p>
	제목:<br/><input type="text"  name="title" id="title" value="${param.title}" class="form-control">
</p>
<p>
	내용:<br/>
	<textarea name="con" id="con" rows="5" cols="30" class="form-control">${param.con}</textarea>
</p>
<div class="form-group">
	<input type="radio" id="participation01" name="participation" value="Y"> <label for="participation01">신청버튼 생성</label>
	<input type="radio" id="participation02" name="participation" value="N"> <label for="participation02">신청버튼 생성하지 않음</label>
</div>
<div style="float:right">
<input type="submit" value="새 글 등록" class="btn btn-primary">
<button type="reset"  class="btn btn-outline-secondary"><a href="${cPath}/event/list.do" style="text-decoration: none;">취소</a></button> <%-- 취소기능 있어야댐. 다시 목록으로 돌아가야 함. --%>
</div>
</form>
</div>
<!-- C3D에 맞는 js -->
<%@ include file="../bootstrap4js.jsp" %>
</div>
</body>
</html>