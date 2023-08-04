<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link href="https://webfontworld.github.io/gmarket/GmarketSans.css" rel="stylesheet">
  <!-- Bootstrap 4 CSS -->
 <link rel="stylesheet" href="../../bootstrap/bootstrap.min.css">
 <!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
<style>
body {
    font-family: 'GmarketSans', sans-serif;
    background-color: #F2F2F2;
}
table {
 width: 80%;
 border-collapse: collapse;
 margin: 40px auto;
}

.nickname{
font-size:26px;
color:#fc7c24;
}

.mycontainer{
max-width: 1000px;
margin: 0px auto;
padding: 60px 100px 100px;
background-color: #fff;
}

.mypwd{
color:#bfbfbf;
font-style:italic;
font-size:14px;
}
</style>
<title>우리들의 미니멀 챌린지::작심삼일</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
jQuery(document).ready(function($) {
	 var memNo="<c:out value='${member.mem_no}' />";
	 var pageNo="<c:out value='${oldPage}' />";
	 var memEmail="<c:out value='${member.mem_email}' />";
	 var pwd="<c:out value='${member.pwd}' />";
	 
	 $("#deleteF").on("click",function(){
		 var result = confirm('해당 회원을 강제 탈퇴시기겠습니까?');
		 
		 if(result){
		      $.ajax({
		          type:"get",
		          url:<%=request.getContextPath()%>"/member/delete.do?no="+memNo+"&pageNo="+pageNo,
		          success:
		            function(response){
		              //메일발송
		              $.get(<%=request.getContextPath()%>"/deleteEmail.do?email="+memEmail);
		              //멤버 딜리트하고 리턴된 페이지로 이동
		              let url="list.do?pageNo="+pageNo;
		              location.replace(url);
		            },
		          error:
		             function(jqXHR, textStatus, errorThrown) {
		              console.log("error:",textStatus,errorThrown);     
		               alert("회원삭제에 실패했습니다");      
		           }
		         });			 
		 }else{ }
	 });
 });
 </script>
</head>
<body>
<!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">	
 <div class="mycontainer">
	<h1>회원 상세 정보</h1><br/><br/>
	<h5 class=""><span class="nickname">${member.nickname} </span> 님의 회원정보 입니다</h5>
	<table class="table">
	 <tbody>
	  <tr>
	   <th scope="row" style="text-align:center;" bgcolor="#f5f5f5">회원번호</th>
	   <td>${member.mem_no}</td>
	  </tr>
	  <tr>
	   <th scope="row" style="text-align:center;" bgcolor="#f5f5f5">회원id</th>
	   <td>${member.id}</td>
	  </tr>
	  <tr>
	   <th scope="row" style="text-align:center;" bgcolor="#f5f5f5">비밀번호</th>
	   <td><span class="mypwd">*비밀번호는 조회할 수 없습니다</span></td>
	  </tr>
	  <tr>
	   <th scope="row" style="text-align:center;" bgcolor="#f5f5f5">회원명</th>
	   <td>${member.mem_name}</td>
	  </tr>
	  <tr>
	   <th scope="row" style="text-align:center;" bgcolor="#f5f5f5">닉네임</th>
	   <td>${member.nickname}</td>
	  </tr>
	  <tr>
	   <th scope="row" style="text-align:center;" bgcolor="#f5f5f5">생일</th>
	   <td><fmt:formatDate value="${member.birthyear}"   pattern="yyyy년 MM월 dd일"/></td>
	  </tr>
	  <tr>
	   <th scope="row" style="text-align:center;" bgcolor="#f5f5f5">성별</th>
	   <td>${member.gender}</td>
	  </tr>
	  <tr>
	   <th scope="row" style="text-align:center;" bgcolor="#f5f5f5">가입일</th>
	   <td><fmt:formatDate value="${member.join_date}"   pattern="yyyy년 MM월 dd일 "/> </td>
	  </tr>
	  <tr>
	   <th scope="row" style="text-align:center;" bgcolor="#f5f5f5">이메일</th>
	   <td>${member.mem_email}</td>
	  </tr>
	  <tr>
	   <th scope="row" style="text-align:center;" bgcolor="#f5f5f5">마케팅수신동의</th>
	   <td>${member.isMarketing}</td>
	  </tr>
	 </tbody>	
	</table>
	
	<!-- button --> 
	<!-- d-flex:한개의 row를 block레벨로 차지 
	   flex-start:왼쪽정렬(기본)/ flex-end:오른쪽정렬 / flex-center:가운데정렬
	   justify-content-end : 오른쪽정렬-->
	 <br>
	<div class="d-flex justify-content-end" style="padding: 10px 20px;">
	  <c:if test="${AUTH_USER.id eq 'admin'}">
     <a href="${cPath}/member/list.do?pageNo=${oldPage}" class="btn btn-outline-primary" style="margin: 6px;">목록보기</a>
	 </c:if>
     
	  <a href="${cPath}/member/modify.do?no=${member.mem_no}&pageNo=${oldPage}" class="btn btn-outline-primary" style="margin: 6px;">정보 수정</a>
		 
	  <c:if test="${AUTH_USER.id eq 'admin'}">
	  <button type="button" id="deleteF" class="btn btn-danger" style="margin: 6px;">회원 강퇴</button>
	 </c:if>

	  <c:if test="${AUTH_USER.id eq member.id}">
	  <button type="button" id="delete" onclick="openLinkPopup('${cPath}/checkPwd.do?memNo=${member.mem_no}')" class="btn btn-danger" style="margin: 6px;">회원 탈퇴</button>
	 </c:if> 
	</div>
 </div>

    <script>
      function openLinkPopup(linkURL) {
          window.open(linkURL, '_blank', 'width=500,height=300');
      }
    </script> 
     <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
 </div>
 </body>
 </html>