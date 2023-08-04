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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<style>
body {
	font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
}
</style>
 <script>
$(document).ready(function(){
	$("#writeForm").on("submit", function(){
		let titleO = $("#chall_title");
		if(titleO.val()==""){
			alert("제목은 필수입력입니다.");
			titleO.focus();
			return  false;
		}
		let conO = $("#chall_con");
		if(conO.val()==""){
			alert("내용은 필수입력입니다.");
			conO.focus();
			return  false;
		}
		
		//필수입력되었으면 submit진행
		$("#writeForm").attr("action", "/chall/write.do");
		$("#writeForm").attr("method", "post");
		return ture;
	});
	
});
</script>
 
</head>
<body>
  <!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">
 <div class="container">
	 <h2  class="mt-4 mb-3 text-center">챌린지 만들기</h2>
	 <form id="writeForm" action="${cPath}/chall/write.do" method="post">
 	  <label for="cate_no" class="form-label">카테고리</label>
        <select id="numberSelect" name="cate_no" id="cate_no"  class="form-control">
            <option value="1">운동</option>
            <option value="2">생활습관</option>
            <option value="3">자기계발</option>
        </select>
	  <div  class="mb-2">	
	  	<label for="chall_title"  class="form-label">제목</label>
	  	<input type="text" name="chall_title" id="chall_title"  class="form-control" placeholder="제목을 입력하세요"/><br/>
 	  </div>
	  <div  class="mb-2">	
	  	<label for="chall_con"  class="form-label">내용</label>
	  	<textarea name="chall_con"  minlength="10" id="chall_con" class="form-control"  rows="5" cols="30" placeholder="내용을 입력하세요" ><c:out value="${content}" /></textarea><br/>
	  </div>
	  <div  class="mb-2">	
	  	<label for="certifi_words"  class="form-label">인증문구</label>
		<input type="text" name="certifi_words" id="certifi_words"  class="form-control" placeholder="인증문구를 입력하세요. 입력하지 않으면 기본 인증문구가 등록됩니다."/><br/>
	  </div>
	  <div class="submit">
		   <input type="submit" id="submit" value="등록하기" class="btn btn-outline-primary">
	 </div>
	 </form>
	 </div>
	 </div>
</body>
</html>


