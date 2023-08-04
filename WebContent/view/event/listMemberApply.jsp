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
<style type="text/css">
table{
	border: 1px solid black;
	text-align: center;
}
th{
	border: 1px solid black;
	text-align: center;
}
td{
	border: 1px dashed black;
	text-align: center;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
	$("#eventmem_list").on("submit", function(){
		//체크박스 타입 필수 입력
		let rowcheckChecked = $("input[name='rowcheck']").is(":checked");
		if(!rowcheckChecked){
			alert('한 명 이상 선택하세요.');
			return  false;
		}
		
		
		//필수입력되었으면 submit진행
		$("#writeForm").attr("action", "/memberlist.do");
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
	<!-- page title -->
	<h2 class="mt-5 mb-4 text-center" >신청한 회원 목록 보기</h2>
	<br/>
	<br/>
	<br/>
	
<form action="${cPath}/event/memberlist.do" method="post" name="eventmem_list" id="eventmem_list">
<input type="hidden" name="no" value="${applicants.event_no}"/>
	<table class="table table-hover">
		<thead>
			<tr>
				<th><input type="checkbox" name="allcheck" onClick="allCheck()" class="form-check-input"></th>
				<th scope="col">회원번호</th>
				<th scope="col">아이디</th>
				<th scope="col">이름</th>
				<th scope="col">닉네임</th>
				<th scope="col">생년월일</th>
				<th scope="col">성별</th>
				<th scope="col">마케팅수신동의여부</th>
				<th scope="col">선정여부</th>
			</tr>
		</thead>
		<tbody>
			<%--신청한 회원이 없는 경우 --%>
			<c:if test="${memberPage.hasNoApplicants()}">
				<tr>
					<td colspan="9">신청자가 아직 없습니다.</td>
				</tr>
			</c:if>
			<%--신청한 회원이 있는 경우 --%>
			<c:forEach var="applicants" items="${memberPage.content}">
				<tr>
					<td><input type="checkbox" name="rowcheck" id="rowcheck" value="${applicants.event_appli_no}" onclick="formSubmit();" class="form-check-input"></td>
					<td>${applicants.mem_no}</td>
					<td>${applicants.id}</td>
					<td>${applicants.mem_name}</td>
					<td>${applicants.nickname}</td>
					<td>${applicants.birthyear}</td>
					<td>${applicants.gender}</td>
					<td>${applicants.isMarketing}</td>
					<td>${applicants.isSelector}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<div>
	<input type="submit" name="btn" value="해당 회원을 선정하기" class="btn btn-primary">
	<input type="submit" name="btn" value="해당 회원에 대한 선정을 취소하기" class="btn btn-secondary">
	<br/>
	<br/>
	<a href="javascript:window.history.back();" class="btn btn-outline-primary" style="float:right">뒤로가기</a>
</div>

</form>
</div>
<script>


/* function formSubmit(){
	//1) form요소에 접근
	//let formElement = document.forms[0];   폼컬렉션의 첫번째 요소로 접근. 여러개니까 -s.
	//let formElement = document.frm1; //폼객체의 이름으로 접근. 이렇게 계층구조로도 되지만 계층구조가 깊어질수록 번거로우니가 .getElementById()가 좋음.
	//상위객체.하위객체 (브라우저에서는 window가 최상위 객체)
	//window.document.폼객체.폼하위객체
	//window.document..폼하위객체
	let formElement = document.getElementById("eventmem_list"); //기능이니까 괄호. 괄호 속엔 큰따옴표.
	
	//유효성검사-필수입력, 글자 수 체크, 엉뚱한 값은 아닌지, 공백이 있는지, 이메일 주소가 올바른지, 비밀번호 확인이 비밀번호와 일치하는지
	
	//이름 필수입력
	//이름요소에 접근하여 이름값의 유무를 확인. 입력값이 없으면, 경고창에 id는 필수입력입니다. 라는 멘트를 출력.
	
	if(formElement.rowcheck.value==""){ //입력값이 없으면,  //건건이 if문으로 사용. else if안됨.
		window.alert('이름은 필수입력항목입니다.');
		return;
	}
	formElement.submit();
} */

function allCheck(){
	var ac = document.eventmem_list.allcheck;
	var rc = document.eventmem_list.rowcheck;
	
	if(ac.checked == true){
		for(i=0; i<rc.length; i++) {
			rc[i].checked = true;
		}
		alert("전체 행이 선택되었습니다.");
	} else {
		for(i=0; i<rc.length; i++){
			rc[i].checked = false;
		}
		alert("전체 행이 선택 해제 되었습니다.");
	}
}
</script>
</div>
</body>
</html>