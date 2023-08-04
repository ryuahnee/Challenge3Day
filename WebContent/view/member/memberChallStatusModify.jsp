<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <!-- Bootstrap 4 CSS -->
 <link rel="stylesheet" href="../../bootstrap/bootstrap.min.css">
 <link href="https://webfontworld.github.io/gmarket/GmarketSans.css" rel="stylesheet">
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<style>
body {
   
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
.mytitle{
color:darkgray;
font-size:16px;
}

th{
text-align:center;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
jQuery(document).ready(function($) {
    $("#challBtn").on("click",function(){     
         let chall_no = $("#chall_no").val(); 
         
         //이메일 필수입력
         if(chall_no===""){ 
             $('#challName').text("챌린지 번호를 입력하세요");
            $("#chall_no").focus();
            return false;
         }
         
      $.ajax({
        type:"get",
        url:<%=request.getContextPath()%>"/searchCahll.do?chall_no="+chall_no,
        contentType:'application/x-www-form-urlencoded; charset=euc-kr',
        success:
          function(response){//response안에 응답내용이 문자열로 변환된 json객체를 받는다
             $('#challName').text(response);
          },
        error://오류발생 호출 함수. 4xx 또는 5xx
             // jqXHR: XMLHttpRequest 객체
             // textStatus: 에러 상태를 설명하는 문자열
             // errorThrown: 에러의 예외 객체 (예외가 발생하지 않으면 undefined)
           function(jqXHR, textStatus, errorThrown) {
            console.log("error:",textStatus,errorThrown);     
             alert("아작스1에러"+textStatus);      
         }
       });//ajax()끝
     });//$("#emailBtn").click(function()끝
         
 });
</script>
<title>우리들의 미니멀 챌린지::작심삼일</title>
</head>
<body>
<%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">	
<div class="mycontainer">
	<h1 class="">챌린지 정보 수정</h1><br/><br/>
	<h5 class=""><span class="nickname">${member.mem_name} </span> 님의 챌린지 정보 입니다</h5>
	<form id="MemberChallStatusForm" action="${cPath}/member/challModify.do" method="post" onsubmit="return validCK();">
 	<div class="container">	
	<table class="table">
	 <tbody>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">관리 번호</th>
	   <td>
	   <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
	   <input type="hidden" name="oldPageNo" id="oldPageNo" value="${oldPageNo}"/>
	   <input type="hidden" name="chall_manual_no" id="chall_manual_no" value="${mcs.chall_manual_no}"/>
	   ${mcs.chall_manual_no}
	    </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">챌린지 번호</th>
	   <td>
	   	<input type="number" name="chall_no" id="chall_no" class="" value="${mcs.chall_no}" min=1/>
	   <c:if test="${errors.chall_no}"><div style="color:red">참여하는 챌린지의 번호를 입력하세요</div></c:if>
	   <button type="button" id="challBtn" class="btn btn-secondary">제목 보기</button><br/>
	   <span id="challName" calss=""></span>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">회원 번호</th>
	   <td>
	   <input type="number" name="mem_no" id="mem_no" class="" value="${mcs.mem_no}" min=1/>
	   <c:if test="${errors.mem_no}"><div style="color:red">회원번호를 입력하세요</div></c:if>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">진행 상태</th>
	   <td>
	   	<input type="number" name="chall_ing_status" id="chall_ing_status" min="0" max="3" value="${mcs.chall_ing_status}"/>
		<c:if test="${errors.chall_ing_status}"><div style="color:red">진행상태를 입력하세요</div></c:if>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">시작 날짜</th>
	   <td>
        <input type="date" name="start_date" id="start_date" class="" value="<fmt:formatDate value="${mcs.start_date}" pattern="yyyy-MM-dd"/>" required="required"/>
        <c:if test="${errors.start_date}"><div style="color:red">시작일을 선택하세요</div></c:if></td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">최종 날짜</th>
	   <td>
	   	<input type="date" name="final_date" id="final_date" value="<fmt:formatDate value="${mcs.final_date}" pattern="yyyy-MM-dd"/>" required="required"/>
        <c:if test="${errors.final_date}"><div style="color:red">최종일을 선택하세요</div></c:if>
	   </td>
	  </tr>
	  <tr>
	   <th scope="row" bgcolor="#f5f5f5">결과</th>
	   <td>
	   	<input type="text" name="chall_result" id="chall_result" class="" value="${mcs.chall_result}" required="required"/>
		<c:if test="${errors.chall_result}"><div style="color:red">결과를 입력하세요</div></c:if>
	   </td>
	  </tr>
	 </tbody>	
	</table>
	
	<!-- button --> 
	<div class="d-flex justify-content-end" style="padding: 10px 20px;">
	<a href="${cPath}/member/challList.do?no=${mcs.mem_no}&pageNo=${pageNo}&oldPageNo=${oldPageNo}" class="btn btn-outline-primary" style="margin: 6px;">변경취소</a>
	<input type="submit" value="수정" class="btn btn-outline-primary" style="margin: 6px;"/>
	<a href="${cPath}/member/challDelete.do?memNo=${mcs.mem_no}&no=${mcs.chall_manual_no}&pageNo=${pageNo}&oldPageNo=${oldPageNo}" class="btn btn-primary" style="margin: 6px;">삭제</a>
 	</div>
</div>
</form>
</div>
<script>
		//date 날짜제한
		var now_utc = Date.now() // 지금 날짜를 밀리초로
		var timeOff = new Date().getTimezoneOffset()*60000; // 분단위를 밀리초로 변환 getTimezoneOffset()은 현재 시간과의 차이를 분 단위로 반환
		var today = new Date(now_utc-timeOff).toISOString().split("T")[0];// new Date(now_utc-timeOff).toISOString()은 '2022-05-11T18:09:38.134Z'를 반환
		document.getElementById("start_date").setAttribute("max", today);
		document.getElementById("final_date").setAttribute("max", today);
</script>
 <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</div>
 </body>
 </html>