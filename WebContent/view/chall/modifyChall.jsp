<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" /> 
<!DOCTYPE html>
<html lang="ko">
<head>
 <meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 <title>우리들의 미니멀 챌린지::작심삼일</title>
  <!-- bootswatch united theme -->
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
		$("#modifyForm").on("submit", function(){
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
			$("#modifyForm").attr("action", "/chall/modify.do");
			$("#modifyForm").attr("method", "post");
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
	<h2  class="mt-4 mb-3 text-center">챌린지 수정</h2>
	<form id="modifyForm" action="${cPath}/chall/modify.do" method="post">
    <div class="form-group">
    <label for="no">카테고리번호</label>
    <select name="cate_no" id="cate_no" class="form-control">
        <option value="1" ${modReq.cate_no eq 1 ? 'selected' : ''}>운동</option>
        <option value="2" ${modReq.cate_no eq 2 ? 'selected' : ''}>생활습관</option>
        <option value="3" ${modReq.cate_no eq 3 ? 'selected' : ''}>자기계발</option>
    </select>
</div>
	 <div class="form-group">
	   <label for="no">글번호</label>
	   <input type="text" name="no" id="no" class="form-control" value="${modReq.chall_no}" readonly="readonly"/>
	  </div>
	 <div class="form-group">
	 	<label for="title">제목</label>
	 	<input type="text" name="chall_title" id="chall_title" class="form-control" value="${modReq.chall_title}"/>
	 </div>
	 <div class="form-group">
	 	<label for="content">내용</label>
	 	<textarea rows="5" name="chall_con" id="chall_con" class="form-control">${modReq.chall_con}</textarea>
	 </div>
	 <div class="form-group">
	 	<label for="content">인증문구</label>
	 	<input type="text" name="certifi_words" id="certifi_words" class="form-control" value="${modReq.certifi_words}"/>
	 </div>

	<div class="d-flex justify-content-end">
     <a href="${cPath}/chall/list.do"   class="btn btn-outline-primary">목록보기</a> 
     <button type="submit" class="btn btn-outline-primary">수정하기</button>
	</div>
   </form>
 </div>
 	</div>
 	       	
</body>
</html>