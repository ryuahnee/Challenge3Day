<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

  </script>

</head>
<body onload="myBadge()">
 <!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">
    
    
<div class="container">
	
<div class="row">
<div id="chall" class="col-sm-6" style="margin:0 auto;">

	<form action="${cPath}/myPage/list.do">
		<div style="margin:15px">
		<select name="chall_result" class="form-select" style="width:200px; display:inline;" aria-describedby="move"> 
			<option ${(param.chall_result == 'I')?"selected":""} value="I" >진행중인 챌린지</option>
			<option ${(param.chall_result == 'F')?"selected":""} value="F" >종료된 챌린지</option>
			<option ${(param.chall_result == 'my')?"selected":""} value="my" >내가만든 챌린지</option>
		</select>
		<input type="submit" id="move" value="이동" class="btn btn-outline-primary" style="margin: 0px 0px 2px 5px ">
		</div>
		<%-- ---- 시도 ---- --%>
		<div>
			<c:forEach var="n" items="${list}" begin="0" end="20" step="1">
				<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyyMMdd" var="today" />
				<fmt:formatDate value="${n.start_date}" pattern="yyyyMMdd" var="startDate" />
			<div style="max-width: 23rem" class="
						<c:choose>
							<c:when test="${n.chall_ing_status == '0'}">card border-danger mb-3</c:when>
							<c:when test="${n.chall_ing_status == '1'}">card border-warning mb-3</c:when>
							<c:when test="${n.chall_ing_status == '2'}">card border-info mb-3</c:when>
							<c:otherwise>background-color: transparent;</c:otherwise>
						</c:choose>
			">

  				<div class="card-header">
  					<c:choose>
						<c:when test="${n.chall_ing_status == '0'}">0일차 성공</c:when>
						<c:when test="${n.chall_ing_status == '1'}">1일차 성공</c:when>
						<c:when test="${n.chall_ing_status == '2'}">2일차 성공</c:when>
					</c:choose></div>
 					<div class="card-body">
    					<h4 class="card-title">${n.chall_title}</h4>
    					<table>
    						<tr>
    							<td class="card-text"><strong>Start</strong>: ${n.start_date} <br/> <strong>Last</strong>: ${n.final_date}</td>
    							<c:choose>
				   					<c:when test="${today == startDate && n.chall_ing_status == '0'}">
					   					<td style="padding: 0px 15px 0px 65px">
					   						<button type="button"  class="btn btn-primary" onclick="select('${n.certifi_words}','${n.chall_no}','${n.chall_result}','${n.chall_ing_status}','${n.cate_no}')">완료인증하기</button>
										</td>
									</c:when>
									<c:when test="${today - startDate == 1 && n.chall_ing_status == '1' || today - startDate == 2 && n.chall_ing_status == '2'}">
										<td style="padding: 0px 15px 0px 65px">
											<button type="button" class="btn btn-primary" onclick="select('${n.certifi_words}','${n.chall_no}','${n.chall_result}','${n.chall_ing_status}','${n.cate_no}')">완료인증하기</button>
										</td>
									</c:when>
									<c:otherwise>
										<td style="padding: 0px 15px 0px 69px">
											<button class="btn btn-primary disabled" style="font-size:0.9em">유효하지 않은<br/>인증</button>
										</td>
									</c:otherwise>
								</c:choose>
    						</tr>
    					</table>
  					</div>
			</div>
			</c:forEach>
				
		</div>
		
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
	<a href="${cPath}/member/detail.do?no=${AUTH_USER.mem_no}&pageNo=1" class="btn btn-outline-primary"> 회원 정보 수정</a>
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
// 인증문구 입력
function select(certifiWords, chall_no, chall_result, chall_ing_status, cate_no) {
    if (chall_ing_status === "0" || chall_ing_status === "1") {
        	 var certify = prompt("인증문구를 입력해주세요 : " + certifiWords + "");
        	 const userInput = certify;
        if (certifiWords === userInput) {
            window.location.href = "/myPage/stateUpdate.do?chall_no=" + chall_no + "&chall_result=" + chall_result;
            alert("멋져요! 화이팅!!");
        } else {
            alert("인증 문구가 다릅니다. 다시 한번 확인해주세요.");
        }
    } else if (chall_ing_status === "2") {
        var opin_con = prompt("작심삼일 성공 ! 인증 소감을 입력해주세요 : " + " ex) 나도 이제 작심삼일! ",'도전완료! 나도 이제 탈 작심삼일 !');
        window.location.href = "/myPage/challengeopinion.do?chall_no=" + chall_no + "&opin_con=" + opin_con +"&cate_no=" + cate_no;
    }
}

</script>
<!-- C3D에 맞는 js -->
<%@ include file="../bootstrap4js.jsp" %>
</div>
</body>
</html>