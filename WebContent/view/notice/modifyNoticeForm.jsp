<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
  <c:set var="cPath" value="<%=request.getContextPath() %>" /> 
<!DOCTYPE html>
<html lang="ko">

<head>
  <title>우리들의 미니멀 챌린지::작심삼일</title>    
 <meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
 <title></title>
 <!-- Bootstrap 4 CSS -->
 <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
 
  <!-- bootswatch united theme -->
<link rel="stylesheet" href="../../../css/bootstrap.min.css"> 
 <link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard-dynamic-subset.css" />
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
<link rel="stylesheet" href="/css/sidebarNav.css">
    
</head>
<!-- jQuery 스크립트를 Bootstrap 스크립트보다 먼저 로드 -->
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<body>
<!-- SidebarNav.jsp 파일을 포함시키기. -->
<%@ include file="/view/sidebarNav.jsp" %>

<!-- 내용 -->		    
<div id="bodyContent">	
 <div class="container">
     <!-- page title -->
	 
	 <form action="${cPath}/notice/modify.do" method="post" onsubmit="return validateForm()">
	  <div  class="mb-2">	
	  	<label for="title"  class="form-label">공지번호</label>
	  	<input type="text" name="no" id="no"  class="form-control"  value="${modReq.notiNo}" readonly="readonly"/><br/>
	  </div>
	  
	  <div  class="mb-2">	
	  	<label for="title"  class="form-label">제목</label>
	  	<input type="text" name="title" id="title"  class="form-control"  value="${modReq.notiTitle}" /><br/>
	  <c:if test="${errors.notiTitle}"><span class="error">제목을 입력하세요</span></c:if>
	  </div>
	  <div  class="mb-2">	
	  	<label for="content"  class="form-label">내용</label>
	  	<textarea name="content" id="content" class="form-control"  rows="10" cols="50">${modReq.notiContent}</textarea><br/>
	  <c:if test="${errors.notiContent}"><span class="error">내용을 입력하세요</span></c:if>
	  </div>
	  
	  <!--  button  -->
	  <div>
	  <div class="d-flex justify-content-center">
	 	<c:set var="pageNo" value="${empty param.pageNo?1:param.pageNo}" /> 
    	 <a href="${cPath}/notice/list.do?pageNo=${pageNo}" class="btn btn-outline-primary" style="margin-left: 5px;">목록</a>
    	
    	<c:if test="${user.id eq 'admin'}">
	  	 <button type="submit"class="btn btn-list btn btn-outline-primary " style="margin-left: 5px;">수정</button> 
	  	 </c:if>
	 	
	 	<c:if test="${user.id eq 'admin'}">
	 	 <a href="${cPath}/notice/delete.do?notiNo=${modReq.notiNo}" class="btn btn-outline-primary" style="margin-left: 5px;">삭제</a> 
		  </c:if>
		  
	  </div> 
	 </form>
 </div>
 
<%@ include file="../bootstrap4js.jsp" %> 


</div>
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