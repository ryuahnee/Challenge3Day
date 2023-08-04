<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의사항 관리자 페이지</title>
 <!-- bootswatch united theme -->
<link rel="stylesheet" href="../../../css/bootstrap.min.css"> 
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<style>
body {
	font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
}
</style>
<style>

.qnaWrite{
	height: 50px;
	width: 250px;
	font-size: 17px;
	margin-bottom: 10px;
}

.con strong{
	display: block;
	float: left;
	margin-right: 14px;
}

#qna_con{
	width:600px;
	height:300px;
	font-size:20px;
}


tr,th,td{
 	text-align: center;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.center-textarea {
    width: 80%; /* Adjust the width as needed */
    margin: 0 auto; /* Center align the textarea */
  }
  
  .larger-card-body {
    height: 400px; /* Adjust the height as needed */
  }

  .mydetail,
  .myqnaAnWrit {
    max-width: 1200px; /* Set the maximum width for the content */
    margin: 0 auto; /* Center the content horizontally */
    padding: 0 20px; /* Add horizontal padding to the content */
  }
  


</style>
<script>

function anWrite() {
	
	let qna_a_con = document.getElementById("qna_a_con");
		
		if(qna_a_con.value ===""){
			alert("내용을 입력해주세요.")
			qna_a_con.focus();
			return false;
	} 
		alert("수정이 완료되었습니다.");
		return true;
}



function del() {
	  if (confirm("정말 삭제하시겠습니까?")) {
	    var deleteForm = document.getElementById("deleteForm");
	    deleteForm.submit();
	    return true;
	  }
	  return false;
	}
</script>
</head>
<body>
<!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">
	<div class="mydetail">
		<div class="myqnadetail">
		
			<h3>문의내역</h3>
					<table class="table table-hover" border="1">
				  <thead>
					  <tr>
					      <th scope="row">글번호</th>
					      <th scope="row">회원번호</th>
					      <th scope="row">작성자ID</th>
					      <th scope="row">작성일</th>
					      <th scope="row">답변</th>
					      <th scope="row">파일첨부</th>
				   	 </tr>
				    </thead>
				  	 	<tr>
						  <td>${n.qna_no}</td>
						  <td>${n.mem_no}</td>
						  <td>${n.id}</td>
						  <td>${n.qna_date}</td>
						  <c:if test="${n.qna_fin_check == 'N'}"><td>답변전</td></c:if>
						  <c:if test="${n.qna_fin_check == 'Y'}"><td>답변완료</td></c:if>
						  <c:if test="${n.qna_orifile_name != null && !empty n.qna_orifile_name}"><td> Y</td></c:if>
						  <c:if test="${empty n.qna_orifile_name}"><td> N</td></c:if>
						</tr>
					</table>
					
		<%-- 회원 문의 내역 --%>
			<div class="card mb-3">
				<h3 class="card-header">Q. 문의내역</h3>
			  		<div class="card-body">
			    		<h5 class="card-subtitle text-muted">${n.qna_title}</h5>
			  		</div>
				  	<div class="card-body">
				    	<p class="card-text">${n.qna_con}</p>
				  	</div>
				 	<div class="card-footer text-muted" >
				  		<c:if test="${n.qna_orifile_name != null && !empty n.qna_orifile_name}">
				 			<p class="text-primary">${n.qna_orifile_name}  <a id ="qna_orifile_name" class="card-link" name="qna_orifile_name" download href="/upload/qna/${n.qna_orifile_name}" >다운로드</a></p>
				  		</c:if>
				  	</div>
			</div>
			
			<div class="dropdown-menu" style="">
	      		<a class="dropdown-item" href="#">Action</a>
	     	</div>
     	</div>
     	
     	
     <%-- 목록보기--%>
		<div class="d-flex justify-content-end">
				<a href="/qna/List.do?id=${id}"><button type="button" class="btn btn-outline-primary">목록보기</button></a>
		</div>
		
	<%-- 답변내역 수정--%>	
		<form action="/qnaAn/Modify.do?qna_no=${qna_no}&id=${id}" method="post">
			<div>
				<strong>답변 내역 </strong>
			</div>	
		<%-- ID값 출력 --%>	
		<div class="Writegroup" id="Writegroup" style="display: none;">
			<div class="id">
					<div class="form-group">
					  <fieldset disabled="">
					    <div class="row">
					      <div class="col-md-2">
					        <label class="form-label" for="disabledInput">ID:</label>
					      </div>
					      <div class="col-md-4">
					        <input class="form-control" id="disabledInput" type="text" placeholder="${id}" disabled="">
					        <input type="hidden" name="qna_no" value="${n.qna_no}">
					      </div>
					    </div>
					  </fieldset>
					</div>
			</div>
			</div>
		<%-- 답변 내용 수정 폼 --%>	
   		<div class="myqnaAnWrit" style="margin : -20px;">
        <div class="card mb-3" style="margin-top: 20px;">
          <h3 class="card-header">A. 답변내용 <a style="font-size: 15px;">답변일 : ${an.qna_a_date} </a></h3>
          <div class="card-body">
            <h6 class="card-subtitle text-muted">답변드립니다.</h6>
          </div>
          <div class="card-body">
            <p class="card-text">
              <textarea class="form-control" name="qna_a_con" id="qna_a_con" rows="5" placeholder="(필수)답변을 작성해주세요.">${an.qna_a_con}</textarea>
            </p>
          </div>
          <div class="card-footer text-muted d-flex justify-content-end"> <!-- Add justify-content-end class -->
            <input type="hidden" id="id" name="id" value="${id}">
            <input type="hidden" id="nid" name="nid" value="${n.id}">
            <input type="hidden" id="qna_no" name="qna_no" value="${qna_no}">
            <%-- 답변 수정--%>
            <button type="submit" class="btn btn-outline-primary" onclick="return anWrite()">답변수정</button>
          </form> <!-- Close the form here -->
          <form action="${cPath}/qnaAn/Delete.do" method="post">
            <input type="hidden" id="qna_no" name="qna_no" value="${n.qna_no}">
            <%-- 답변 삭제--%>
            <button type="submit" id="andel()" class="btn btn-outline-primary" onclick="return del();">답변삭제</button>
          </form>
          </div>
        </div>
      </div>
    </div>
    </div>
</body>
</html>