<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html lang="ko">

<head>
 <%-- Bootstrap 반응형에 맞게 설정, css파일의 스타일 참조설정 --%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
  <title>우리들의 미니멀 챌린지::작심삼일</title>
 
	<!-- bootswatch united theme -->
<link rel="stylesheet" href="../../../css/bootstrap.min.css"> 
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard-dynamic-subset.css" />
	<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">    
	<style>
body {
	font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
}
</style>
</head>


 <!-- SidebarNav.jsp 파일을 포함시키기. -->
<%@ include file="/view/sidebarNav.jsp" %>
	
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>

 <script>
$(document).ready(function(){
	$("#writeNoticeForm").on("submit", function(){
		let title = $("#title");
		if(title.val()==""){
			alert("제목은 필수입력입니다.");
			title.focus();
			return  false;
		}
		let content = $("#content");
		if(content.val()==""){
			alert("내용은 필수입력입니다.");
			content.focus();
			return  false;
		}
		
		//필수입력되었으면 submit진행
		$("#writeNoticeForm").attr("action", "/notice/write.do");
		$("#writeNoticeForm").attr("method", "post");
		return ture;
	});
	
});
</script>
<body>
<!-- 내용 -->		
<div id="bodyContent">	    
 <div class="container">
 
 
 	<h1><b> 공지 작성</b></h1><br/><br/><br/>
     <!-- page title -->
	 
	 <form id=writeNoticeForm action="/notice/write.do" method="post" onsubmit="return validateForm()">
	  <div  class="mb-2">	
	  	<label for="title"  class="form-label"><b>제목</b></label>
	  	<input type="text" name="title" id="title"  class="form-control" placeholder="제목을 입력하세요" /><br/>
	  </div>
	  <div  class="mb-2">	
	  	<label for="content"  class="form-label"><b>내용</b></label>
	  	<textarea name="content" id="content" class="form-control"  rows="10" cols="50" placeholder="비속어는 사용할 수 없습니다 &#10;내용은 구체적으로 작성해주세요." style="white-space: pre-line;"></textarea><br/>
	  </div>
	<div class="d-flex justify-content-end">
	  <c:if test="${user.id eq 'admin'}">
	 	<button  type="reset"  class="btn btn-list btn btn-outline-primary" style="margin-left: 5px;"><b>취소</b></button>
	 	<button  type="submit" class="btn btn-list btn btn-outline-primary" style="margin-left: 5px;"><b>등록</b></button>
	  </c:if>
	  </div> 
	 </form>
 </div>
 </div>
<%@ include file="../bootstrap4js.jsp" %> 



</body>

<script>
    function validateForm() {
        // 금지 단어 목록
        const forbiddenWords = ["씨발", "미친", "개새끼", "시1발","썅","병신","미쳤"];
        
        // 제목과 내용에서 입력된 값을 가져온다
        const title = document.getElementById("title").value;
        const content = document.getElementById("content").value;
        
        // 금지된 단어가 있는지 확인.
        for (const word of forbiddenWords) {
            if (title.includes(word) || content.includes(word)) {
                alert("금지된 단어가 포함되어 있습니다. 등록할 수 없습니다.");
                return false; // 등록을 막습니다.
            }
        }
        
        // 모든 조건을 통과하면 등록을 허용합니다.
        return true;
    }
</script>

</html>