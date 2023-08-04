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
table{
	padding: 20px;
}
th{
	width:70px;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>

</head>
<body>

 <!-- Navigation -->
  <!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">
  
  <div class="container">
  <h2 class="mt-5 mb-4 text-center">이벤트</h2>
  <div style="margin:50px">
	<form method="get" action="${cPath}/event/apply.do">
	<input type="hidden" name="no" id="no" value="${event.event_no}"/>
	<table class="table">
		<tbody>
			<tr >
				<th scope="row"><h4>제목</h4></th>
				<td><h4>${event.event_title}</h4></td>
			</tr>
			<tr >
				<th scope="row">등록일</th>
				<td><fmt:formatDate value="${event.event_regdate}" pattern="yyyy/MM/dd"/></td>
			</tr>
			<tr >
				<th scope="row">내용</th> 
				<td style="white-space: pre-wrap;">${event.event_con}</td>
			</tr>
		</tbody>
	</table>
	</div>
	<div style="text-align: center;">
		<!-- 만약 신청버튼이 있으면(만들었으면), 활성화 또는 보여지기 요청주소: apply.do -->
		<c:if test="${event.has_btn=='Y'}">
			<input type="submit" id="participation" class="btn btn-primary" onclick="isValid()" value="신청하기" style="text-align:center"/>
		</c:if>
	</div>
	<div style="margin:50px">
		
		<c:set var="pageNo" value="${empty param.pageNo?'1':param.pageNo}"/>
		<p style="float:right; display:inline;">
			<a href="${cPath}/event/list.do?pageNo=${pageNo}" class="btn btn-outline-primary" style="margin: 0 0 0 30px">목록보기</a>
		</p>
		<!--관리자면 수정삭제 버튼보여주기-->
		<c:if test="${AUTH_USER.id=='admin'}"> 
		<p style="float:right; display:inline;">
			<a href="${cPath}/event/modify.do?no=${event.event_no}" class="btn btn-outline-warning" >이벤트 수정</a>
			<a href="${cPath}/event/delete.do?no=${event.event_no}" onclick="var result = confirm('정말 삭제하시겠습니까?'); if(result == true){return true;} else{return false;}" class="btn btn-outline-danger">이벤트 삭제</a>
			<a href="${cPath}/event/memberlist.do?no=${event.event_no}" class="btn btn-outline-secondary">이벤트 신청 목록 조회하기</a>
		</p>
		</c:if>
	</div>
	</form>
</div>
<pre>
<!-- 관리자가 신청 버튼을 생성한 게시글이라면
로그인한 회원은 신청 버튼을 볼 수 있음.
하지만 뱃지 개수가 일정 수 이상인 사람만 신청이 가능함.
	-> 신청 되었다 안내. / 뱃지 갯수가 부족하다 안내.

관리자는 상세 조회하면 글 수정하거나 삭제할 수 있음.
관리자는 상세 조회하면 신청한 회원 목록을 볼 수 있음. - 따로 버튼 만들까?
	신청한 회원 목록에는 선정여부(기본값 - N), 체크박스, 회원 번호. 회원 아이디, 회원 닉네임, 가입일, 획득한 뱃지 개수, 성공한 챌린지 개수, 실패한 챌린지 개수 를 볼 수 있음.
	체크박스에 체크한 뒤에 선택 완료 버튼을 클릭하면, 이벤트에 선정/당첨 된 것으로 처리됨. -->
</pre>
<!-- C3D에 맞는 js -->
<%@ include file="../bootstrap4js.jsp" %>
<script type="text/javascript">
function isValid(){
	let id = ${AUTH_USER.id}
	if(id==null){
		alert("로그인한 회원만 이용 가능합니다.")
		location.href=<%=request.getContextPath()%>+"/login.do";
		return false;
	} else if (id == 'admin'){
		alert("관리자는 신청할 수 없습니다.");
		return false;
	}
}
</script>
</div>
</body>
</html>