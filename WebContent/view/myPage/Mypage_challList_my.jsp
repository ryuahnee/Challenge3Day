<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>우리들의 미니멀 챌린지::작심삼일</title>
<link rel="stylesheet" href="../../../css/bootstrap.min.css"> 
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard-dynamic-subset.css" />
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style>
body {
	font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
}
.mytooltip {
  position: relative;
  display: inline-block;
}

.mytooltip .mytooltiptext {
  visibility: hidden;
  width: 120px;
  background-color: #FF8C00;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 0;
  position: absolute;
  z-index: 1;
  top: 100%;
  left: 50%;
  margin-left: -60px;
  /* Fade in tooltip - takes 1 second to go from 0% to 100% opac: */
  opacity: 0;
  transition: opacity 1s;
}

.mytooltip .mytooltiptext::after {
  content: "";
  position: absolute;
  bottom: 100%;
  left: 50%;
  margin-left: -5px;
  border-width: 5px;
  border-style: solid;
  border-color: transparent transparent #FF8C00 transparent;
}

.mytooltip:hover .mytooltiptext {
  visibility: visible;
  opacity: 1;
}


</style>
 
</head>
<body onload="myBadge()">
 <!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">
<div class="container">
	
<div class="row">	
<div id="chall" class="col-sm-6">		
	<form action="${cPath}/myPage/list.do">
		<div style="margin:15px">
		<select name="chall_result" class="col-sm-1 form-select" style="width:200px; display:inline;">
			<option ${(param.chall_result == 'I')?"selected":""} value="I" >진행중인 챌린지</option>
			<option ${(param.chall_result == 'F')?"selected":""} value="F" >종료된 챌린지</option>
			<option ${(param.chall_result == 'my')?"selected":""} value="my" >내가만든 챌린지</option>
		</select>
		<input type="submit" value="이동" class="btn btn-outline-primary">
		</div>
		<table class="table table-hover" style="vertical-align:middle">
				<thead>
				<tr>
					<th>Challenge</th>
					<th>Create Date</th>
					<th>category</th>
					<th>with</th>
					<th colspan="2">수정 및 삭제</th>
				</tr>
				</thead>
		<c:forEach var="n" items="${list}" begin="0" end="20" step="1">
			<tbody>
				<tr>
					<td>${n.chall_title}</td>
					<td>${n.chall_write_date}</td>
					<td>${n.cate_name}</td>
					<td>${n.member_count}</td>
					<c:choose>
						<c:when test="${n.member_count == 0 }">
							<td style="padding: 8px auto"><a href="${cPath}/chall/modify.do?no=${n.chall_no}"><button type="button" class="btn btn-outline-warning">수정</button></a></td>
							<td style="padding: 8px auto"><a href="${cPath}/chall/delete.do?no=${n.chall_no}"><button type="button" class="btn btn-outline-danger">삭제</button></a></td>
						</c:when>
						<c:otherwise>
							<td colspan="2" style="padding: 8px auto"><button type="button" class="btn btn-secondary disabled">수정/삭제 불가</button></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</tbody>
		</c:forEach>
		</table>
	</form>
</div>
<div id="badge" class="col-sm-6">
<h2>MY Badge Collection</h2>
<c:set var="i" value="0" />
	<c:set var="j" value="3" />
	<table>
	  <c:forEach  var="mono" items="${monoBadgeList}">
	  <c:set  var="_count" value="${_count+1}" />
	    <c:if test="${i%j == 0}">
	    <tr>
	    </c:if>
	       <td id="b${_count}" style="padding:10px 30px 50px 0px;">
	       		<div id="d${_count}" class="mytooltip">
	       			<img src="../../../img/badge/mo${_count}.png" id="a${_count}" style="width:150px;"/>
	       			<span id="c${_count}"> </span>
	       		</div>
	       </td>
	    <c:if test="${i%j == j-1}">
	    </tr>
	    </c:if>
	    <c:set var="i" value="${i+1}" />
	  </c:forEach>
	</table>
<div>
	<a href="${cPath}/member/detail.do?no=${AUTH_USER.mem_no}" class="btn btn-outline-primary"> 회원 정보 수정</a>
</div>
</div><!-- 뱃지 -->
</div><!-- row -->
</div><!-- container -->
<script>
// 내가 획득한 뱃지면 색깔을 컬러로 바꿔줌. (성공)
function myBadge(){ 
	<c:forEach var="color" items="${memberBdgList}">
		document.getElementById('a${color.badge_no}').src='../../../img/badge/color${color.badge_no}.png';
		document.getElementById('c${color.badge_no}').innerHTML = "${color.badge_name} ${color.ach_date}";
		document.getElementById('c${color.badge_no}').className+="mytooltiptext";
	</c:forEach>
}

</script>
<!-- C3D에 맞는 js -->
<%@ include file="../bootstrap4js.jsp" %>
</div>
</body>
</html>