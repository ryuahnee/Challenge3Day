<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>우리들의 미니멀 챌린지::작심삼일</title>
  <!-- Bootstrap 4 CSS -->
 <link rel="stylesheet" href="../../bootstrap/bootstrap.min.css">
 <link href="https://webfontworld.github.io/gmarket/GmarketSans.css" rel="stylesheet">
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
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

.myorange{
color:#FF8C00;
}
.mypwd{
color:#bfbfbf;
font-style:italic;
font-size:14px;
}

th{
text-align:center;
}
</style>
</head>
<body>
 <!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">	
 <div class="mycontainer">
	<h1 class="">회원 정보 수정</h1><br/><br/>
	<h5 class=""><span class="nickname">${memReq.nickname} </span> 님의 회원정보 입니다</h5>
	<form id="MemberModifyForm" action="${cPath}/member/modify.do" method="post" onsubmit="return validCK();">	
	<table class="table">
	<thead>
	  <tr>
	  </tr>
	</thead>
	<c:if test="${AUTH_USER.id=='admin'}">
	 <tbody>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">회원번호</th>
	   <td>
	   <input type="text" name="no" id="no" value="${memReq.mem_no}" disabled="disabled"/>
	   <input type="hidden" name="mem_no" id="mem_no" value="${memReq.mem_no}"/>
	   <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">회원id</th>
	   <td>
	   <input type="text" name="id" id="id" class="" maxlength="15" placeholder="6~15자 영대소문자,숫자" pattern="[A-Za-z0-9]{6,15}$" value="${memReq.id}" required="required"/>
	   <c:if test="${errors.id}"><div style="color:red;">아이디를 입력하세요</div></c:if>
	   <c:if test="${errors.duplicatedId}"><div style="color:red">이미 사용중인 아이디입니다</div></c:if>
	    </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">비밀번호</th>
	   <td><span class="mypwd">*비밀번호는 본인만 변경할 수 있습니다</span></td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">회원명</th>
	   <td>
	   	<input type="text" name="mem_name" id="mem_name" class="" maxlength="45" placeholder="1~45자" value="${memReq.mem_name}" required="required"/>
		<c:if test="${errors.mem_name}"><div style="color:red">이름을 입력하세요</div></c:if>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">닉네임</th>
	   <td>
	   <input type="text" name="nickname" id="nickname" class="" maxlength="10" placeholder="1~10자" value="${memReq.nickname}" required="required"/>
	   <c:if test="${errors.nickname}"><div style="color:red">닉네임을 입력하세요</div></c:if>
	   <c:if test="${errors.duplicatedNickname}"><div style="color:red">이미 사용중인 닉네임입니다</div></c:if>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">생일</th>
	   <td>
	   	<input type="date" name="birthyear" id="birthyear" class="" min="1800-01-01" value="<fmt:formatDate value="${memReq.birthyear}" pattern="yyyy-MM-dd"/>" required="required"/>
		<c:if test="${errors.birthyear}"><div style="color:red">생일을 선택하세요</div></c:if>	    
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">성별</th>
	   <td>
        <input type="radio" name="gender" id="gender0" class="" value="남" <c:if test="${memReq.gender=='남'}">checked="checked"</c:if> />남성
        <input type="radio" name="gender" id="gender1" class="" value="여" <c:if test="${memReq.gender=='여'}">checked="checked"</c:if> />여성
        <input type="radio" name="gender" id="gender2" class="" value="선택안함" <c:if test="${memReq.gender=='선택안함'}">checked="checked"</c:if> />선택안함
        <c:if test="${errors.gender}"><div style="color:red">성별을 선택하세요</div></c:if></td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">가입일</th>
	   <td>
	   <input type="text" name="date" id="date" value="<fmt:formatDate value="${memReq.join_date}" pattern="yyyy년 MM월 dd일 "/>" disabled="disabled"/>
	   	<input type="hidden" name="join_date" id="join_date" value="${memReq.join_date}"/>
	   </td>
	  </tr>
	  <tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">이메일</th>
	   <td>
	   	<input type="text" name="mem_email" id="mem_email" class="" placeholder="ex) challenge3@gmail.com" pattern="[a-zA-Z0-9]+@[a-z]+\.[a-z]{2,3}" value="${memReq.mem_email}" required="required"/>
		<c:if test="${errors.mem_email}"><div style="color:red">이메일을 입력하세요</div></c:if>
		<c:if test="${errors.duplicatedEmail}"><div style="color:red">이미 사용중인 이메일입니다</div></c:if>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">마케팅수신동의</th>
	   <td>
	    <input type="radio" name="isMarketing" id="isMarketing0" class="" value="Y" <c:if test="${memReq.isMarketing eq 'Y'}">checked="checked"</c:if>/>동의
	    <input type="radio" name="isMarketing" id="isMarketing1" class="" value="N" <c:if test="${memReq.isMarketing eq 'N'}">checked="checked"</c:if>/>미동의
	   </td>
	  </tr>
	 </tbody>	
	</c:if>
	<%---------------------------------------------------------------------------------------------------------- --%>
	<c:if test="${AUTH_USER.id==memReq.id}">
	<p class="mypwd">
	<br/>
	<span>* 회원id/회원명/생일/성별/가입일은 수정 불가 항목입니다.</span><br/>
	<span>  변경을 위해서는 1:1 문의사항 페이지를 이용해주시기 바랍니다.</span><br/>
	</p>
	 <tbody>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">회원id</th>
	   <td>
	   <input type="hidden" name="mem_no" id="mem_no" value="${memReq.mem_no}">
	   <input type="hidden" name="id" id="id" value="${memReq.id}"/>
	   <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
	   <input type="text" value="${memReq.id}" disabled="disabled"/>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">비밀번호</th>
	   <td>
		<a href="#" onclick="openLinkPopup('${cPath}/searchPwd.do')" class="btn btn-secondary">비밀번호 재설정</a>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">회원명</th>
	   <td>
	   <input type="hidden" name="mem_name" id="mem_name" value="${memReq.mem_name}"/>
	   <input type="text" value="${memReq.mem_name}" disabled="disabled"/>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">닉네임<span class=myorange> *</span></th>
	   <td>
	   <input type="text" name="nickname" id="nickname" class="" maxlength="10" placeholder="1~10자" value="${memReq.nickname}"/>
	   <c:if test="${errors.nickname}"><div style="color:red">닉네임을 입력하세요</div></c:if>
	   <c:if test="${errors.duplicatedNickname}"><div style="color:red">이미 사용중인 닉네임입니다</div></c:if>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">생일</th>
	   <td>
	   <input type="hidden" name="birthyear" id="birthyear" value="${memReq.birthyear}"/>
	   <input type="text" value="<fmt:formatDate value="${memReq.birthyear}"   pattern="yyyy년 MM월 dd일"/>" disabled="disabled"/>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">성별</th>
	   <td>
	    <input type="hidden" name="gender" id="gender" value="${memReq.gender}"/>
	    <input type="radio" name="gender0" id="gender0" class="" value="남" <c:if test="${memReq.gender=='남'}">checked="checked"</c:if> disabled="disabled"/>남성
        <input type="radio" name="gender1" id="gender1" class="" value="여" <c:if test="${memReq.gender=='여'}">checked="checked"</c:if> disabled="disabled"/>여성
        <input type="radio" name="gender2" id="gender2" class="" value="선택안함" <c:if test="${memReq.gender=='선택안함'}">checked="checked"</c:if> disabled="disabled"/>선택안함
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">가입일</th>
	   <td>
	   	<input type="hidden" name="join_date" id="join_date" value="${memReq.join_date}"/>
	    <input type="text" value="<fmt:formatDate value="${memReq.join_date}"   pattern="yyyy년 MM월 dd일 "/>" disabled="disabled"/>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">이메일<span class="myorange"> *</span></th>
	   <td>
	   	<input type="text" name="mem_email" id="mem_email" class="" placeholder="ex) challenge3@gmail.com" pattern="[a-zA-Z0-9]+@[a-z]+\.[a-z]{2,3}" value="${memReq.mem_email}"/>
		<c:if test="${errors.mem_email}"><div style="color:red">이메일을 입력하세요</div></c:if>
		<c:if test="${errors.duplicatedEmail}"><div style="color:red">이미 사용중인 이메일입니다</div></c:if>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">마케팅수신동의</th>
	   <td>
	   	<input type="radio" name="isMarketing" id="isMarketing0" class="" value="Y" <c:if test="${memReq.isMarketing eq 'Y'}">checked="checked"</c:if>/>동의
	    <input type="radio" name="isMarketing" id="isMarketing1" class="" value="N" <c:if test="${memReq.isMarketing eq 'N'}">checked="checked"</c:if>/>미동의
	   </td>
	  </tr>
	 </tbody>	
	</c:if>
	</table>
	
	<!-- button --> 
	<div class="d-flex justify-content-end" style="padding: 10px 20px;">
	<a href="javascript:history.back();" class="btn btn-outline-primary" style="margin: 6px;">변경 취소</a>
	<input type="submit" class="btn btn-primary" style="margin: 6px;" value="수정"/>
	</div>
</form>
</div>
<script>
		//생일 입력 제한
		var now_utc = Date.now() // 지금 날짜를 밀리초로
		var timeOff = new Date().getTimezoneOffset()*60000; // 분단위를 밀리초로 변환 getTimezoneOffset()은 현재 시간과의 차이를 분 단위로 반환
		var today = new Date(now_utc-timeOff).toISOString().split("T")[0];// new Date(now_utc-timeOff).toISOString()은 '2022-05-11T18:09:38.134Z'를 반환
		document.getElementById("birthyear").setAttribute("max", today);

	      function openLinkPopup(linkURL) {
	          window.open(linkURL, '_blank', 'width=480,height=300');
	      }
</script>
 <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</div>
 </body>
 </html>