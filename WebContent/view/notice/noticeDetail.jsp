<%@page import="notice.model.Notice" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  <c:set var="cPath" value="<%=request.getContextPath() %>" />  
<!DOCTYPE html>
<html>

<head>
<title></title>
  <!-- jQuery 스크립트를 Bootstrap 스크립트보다 먼저 로드 -->
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>

<%-- Bootstrap 반응형에 맞게 설정, css파일의 스타일 참조설정 --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
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
  .contentN {
    height: 600px; /* 원하는 높이로 조정하세요 */
  }
</style>

</head>

<body>
<!-- SidebarNav.jsp 파일을 포함시키기. -->
<%@ include file="/view/sidebarNav.jsp" %>


<!-- ---------- main 공지사항 상세보기 ----------------- -->
<div id="bodyContent">	
<div class="container">
		<table class="table">
				<tbody>
			
					<tr>
						<th scope="row" colspan="3">제목</th>
						<td class="text-left text-indent text-strong text-orange" colspan="3">${notice.notiTitle}</td>
					</tr>
					<tr>
						<th scope="row" colspan="5">작성일</th>
						<td class="text-left text-indent" >${notice.notiDate}</td>
					</tr>
					<tr>
					</tr>
				</tbody>	
			</table>
				
			<table class="table" style="margin-top: 20px;">
			  <tbody>
			    <tr class="contentN">
			      <td class="text-center text-indent" colspan="4" style="white-space: pre-wrap;">${notice.notiContent}</td>
			    </tr>
			  </tbody>
			</table>
	
	  
			
	<!--  버튼 영역 : 목록, 수정 ,삭제 -->

	<!-- d-flex:한개의 row를 block레벨로 차지 
	   flex-start:왼쪽정렬(기본)/ flex-end:오른쪽정렬 / flex-center:가운데정렬
	   justify-content-end : 오른쪽정렬-->

	<div class="d-flex justify-content-end" style="margin-top: 20px;">
	 <c:set var="pageNo" value="${empty param.pageNo?1:param.pageNo}" /> 
     <a href="${cPath}/notice/list.do?pageNo=${pageNo}" class="btn btn-outline-primary" style="margin-left: 5px;">목록</a>
     
     <!--  버튼 관리자한테만 보이고 수정,삭제 가능 함 -->
 	<c:if test="${user.id eq 'admin'}">
    <a href="${cPath}/notice/modify.do?notiNo=${notice.notiNo}" class="btn btn-outline-primary" style="margin-left: 5px;">수정</a>
	</c:if>

	  	<c:if test="${user.id eq 'admin'}">
	   <a href="${cPath}/notice/delete.do?notiNo= ${notice.notiNo}" class="btn btn-outline-primary" style="margin-left: 5px;"
		   onclick="var result=confirm('정말 삭제하시겠습니까?'); 
		   if(result==true){a
			   return true;
			   } else{
				   return false;
				   }">삭제</a>
	 </c:if> 

	</div>
 </div>
 </div>
<%@ include file="../bootstrap4js.jsp" %> 
</body>
</html>