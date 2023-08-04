<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <title>우리들의 미니멀 챌린지::작심삼일</title>
<link rel="stylesheet" href="../../../css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard-dynamic-subset.css" />
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">


<style>


	h3 {
        text-align: center;
        font-size: 28px; 
        margin-bottom: 50px;
        margin-top: 20px;
    }
	
  body {
  font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
        text-align: center; 
    }
    
 /*  문의내역이없습니다. 가운데 정렬  */
	.table {
        margin: 0 auto;
	}
   .no-records {
        text-align: center;
    }

    table {
        margin: 0 auto;
        margin-bottom: 50px;
    }

	.Write {
		float: right;
		margin-right: 20px;
		margin-top:10px;
	}

	.search{
		margin-top:30px;
        margin: 0 auto;
	}
	.search select,
    .search input[type="text"],
    .search input[type="submit"] {
        margin-top: 20px;
    }
    
    .search input[type="text"] {
        width: 300px;
	}
	
     .PageNation {
        text-align: center;
        margin-top: 20px;
        margin: 0 auto;
        display: flex; 
        justify-content: center; 
    }
    
    
   
    .search {
        margin-top: 30px;
        margin-bottom: 100px; 
        margin: 0 auto;
    }
    
  /*  페이지네이션 가운데 정렬  */
    .PageNation {
        text-align: center;
        margin-top: 20px;
        margin-bottom: 100px; 
        margin: 0 auto;
        display: flex; 
        justify-content: center; 
    }
   
</style>
<script>
var Write = document.getElementById("Write");
	function Write() {
		console.log(Write)
	}
</script>
</head> 
<body>
 <!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">

	<h3 style="margin-bottom:50px; margin-top:20px;"><p class="text-primary">문의내역</p></h3>

	<!-- 관리자로 로그인시 보이는 화면 --------------------------------------------------------->
	<c:if test="${id.equals('admin')}">
		<table border="1" class="table" >
			<thead>
				<th>No.</th>
				<th>회원번호</th>
				<th>회원아이디</th>
				<th>문의제목</th>
				<th>작성일</th>
				<th>답변</th>
			</thead>
		<c:forEach var="n" items="${list}" begin="0" end="14" step="1">
			<tbody>
				<tr>
				<td>${n.num}</td>
				<td>${n.mem_no}</td>
				<td>${n.id}</td>
				<td><a href="/qna/Detail.do?qna_no=${n.qna_no}&id=${id}&n.id=${n.id}" style="text-decoration: none;">${n.qna_title}</a></td>
				<td>${n.qna_date}</td>
				<c:if test="${n.qna_fin_check == 'N'}"><td>답변전</td></c:if>
				<c:if test="${n.qna_fin_check == 'Y'}"><td>답변완료</td></c:if>
			</tr>
		</tbody>
	</c:forEach>
	</table>
	</c:if>
	<!-- 회원으로 로그인시 보이는 화면 --------------------------------------------------------->
	<c:if test="${not empty list && !id.equals('admin')}">
		<table border="1" class="table">
			<thead >
				<th>문의제목</th>
				<th>작성일</th>
				<th>답변상태</th>
			</thead>
	<c:forEach var="n" items="${list}" begin="0" end="14" step="1">
		<tbody>
			<tr>
				<td><a href="/qna/Detail.do?qna_no=${n.qna_no}&id=${id}"  style="text-decoration: none;">${n.qna_title}</a></td>
				<td>${n.qna_date}</td>
				<c:if test="${n.qna_fin_check == 'N'}"><td>답변전</td></c:if>
				<c:if test="${n.qna_fin_check == 'Y'}"><td>답변완료</td></c:if>
			</tr>
	</c:forEach>
		</tbody>
			</table>
				</c:if>
					<c:if test="${empty list}">
		 				 <table>
		 					 <tbody>
								<tr>
									<td colspan="4" class="no-records">문의 내역이 없습니다.</td>
								<tr>
							</tbody>
						</table>
					</c:if>
					
				
					
<!-- 문의하기  --------------------------------------------------------->
<div class="Write">
	<a href="/qna/Write.do?id=${id}"><button type="button" class="btn btn-primary" id="Write" >문의하기</button></a>
</div>

<!-- 검색  // 회원은 제목으로만 조회 --------------------------------------------------------->
<div class="search" style="margin-bottom:30px; margin-left:70px;">
	<form action="${cPath}/qna/Read.do" >
		<select name="f">
			<optgroup label="----선택---">
				<c:if test="${id.equals('admin')}">
					<option ${(param.f == "id")?"selected":"" } value="id" >아이디</option>
				</c:if>
				<option ${(param.f == "qna_title")?"selected":""} value="qna_title">문의제목</option>
			</optgroup>
		</select>
			<input type="text" name="q" value="${param.q}">
			<input type="hidden" name="p" value="${p}">
			<input type="hidden" name="id" value="${id}">
			<input type="submit" name="Search" class="Search" value="검색" >
</div>

<!-- 5단위 페이징 처리 ------------------------------------------------------------------->
<c:set var="page" value="${(empty param.p == null)?1:param.p}"/>
<c:set var="startnum" value="${page-(page-1)%5}"/>
<c:set var="endnum" value="${startnum + 4}" />
<c:set var="lastpage" value="${lastpage}" />
	
	<div class="PageNation" style="margin-left:auto;margin-right:auto;">
		  <ul class="pagination">
		    <li class="page-item disabled mt-4">
		      <c:if test="${startnum > 1}">
		        <a class="page-link" href="?id=${id}&p=${startnum-1}&f=${param.f}&q=${param.q}">이전</a>
		      </c:if>
		    </li>
		    <li class="page-item disabled">
		      <c:if test="${startnum <= 1}">
		        <a class="page-link" onclick="alert('이전 페이지가 없습니다.');">이전</a>
		      </c:if>
		    </li>
		
		    <c:forEach var="i" begin="0" end="4">
		      <li class="page-item">
		        <a class="page-link" href="?id=${id}&p=${startnum+i}&f=${param.f}&q=${param.q}">${startnum+i}</a>
		      </li>
		    </c:forEach>
		    
		    <li class="page-item">
		      <c:if test="${startnum+5 <= lastpage}">
		        <a class="page-link" href="?id=${id}&p=${startnum + 5}&f=${param.f}&q=${param.q}" class="btn btn-next">다음</a>
		      </c:if>
		    </li>
		    <li class="page-item">
		      <c:if test="${startnum+5 > lastpage}">
		        <a class="page-link" onclick="alert('다음 페이지가 없습니다.');">다음</a>
		      </c:if>
		    </li>
		  </ul>
		  
	</div>
</form>
</div>
</body>
</html>