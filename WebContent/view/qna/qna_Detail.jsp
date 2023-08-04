<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의사항 상세조회 페이지</title>
<link rel="stylesheet" href="../../../css/bootstrap.min.css">
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>

<script>
function del() {
	  if (confirm("정말 삭제하시겠습니까?")) {
	    var deleteForm = document.getElementById("deleteForm");
	    deleteForm.submit();
	    return true;
	  }
	  return false;
	}
	
 function showWriteGroup() {
    var writeGroup = document.getElementById("Writegroup");
    if(writeGroup.style.display === "none"){
	    writeGroup.style.display = "block";
    }else{
    	writeGroup.style.display = "none";
    }
} 
//관리자 등록버튼
 function anWrite() {
        var qna_a_con = document.getElementById("qna_a_con");

        if (qna_a_con.value.trim() === "") {
            alert("내용을 입력해주세요.");
            qna_a_con.focus();
            return false; 
        } else {
            confirm("답변이 등록되었습니다.");
            qna_a_con.setAttribute("value", qna_a_con.value.trim());
            return true;
        }
    }



document.addEventListener("DOMContentLoaded", function () {
    const accordionButtons = document.querySelectorAll(".accordion-button");

    accordionButtons.forEach((button) => {
      button.addEventListener("click", () => {
        // Get the parent element of the clicked button, which is the "accordion-item"
        const accordionItem = button.closest(".accordion-item");

        // Get the accordion body corresponding to the clicked button
        const accordionBody = accordionItem.querySelector(".accordion-body");

        // Toggle the "show" class to make the accordion body visible
        accordionBody.classList.toggle("show");
      });
    });
  });

</script>
<style>
.myqnawrite {
  padding: 20px 0; /* Add vertical padding of 20px, and keep horizontal padding as 0 */
}
 

.mydetail,
.myqnawrite {
  max-width: 1200px; /* Set the maximum width for the content */
  margin: 0 auto; /* Center the content horizontally */
  padding: 0 20px 40px; /* Add horizontal padding to the content and increase the bottom padding to add more vertical space */
}

 .card-body {
    height: 100px; 
    max-height: none; /* Set max-height to none to override any other height constraints */
    overflow-y: auto; /* Add a scrollbar if the content exceeds the height */
  }

  .card-text {
    white-space: pre-wrap; /* Allow line breaks for the card-text */
  }

 .card-body {
    height: 500px; /* Set a fixed height of 100px */
    max-height: none; /* Set max-height to none to override any other height constraints */
    overflow-y: auto; /* Add a scrollbar if the content exceeds the height */
  }

  .card-text {
    white-space: pre-wrap; /* Allow line breaks for the card-text */
  }

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

  .card {
    height: 300px; 
    outline-color: red;
  }

  .card-body {
    height: 100%; 
  }

  .card-text {
    overflow-y: auto; /* Add scroll bar for card-text if content exceeds the height */
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
  

  
</style>

</head>
<body>
 	<!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">
 		<div class="mydetail">
 	
	<h3 style="margin-bottom:50px; margin-top:20px;"><p class="text-primary">문의내역</p></h3>
		<c:if test="${id.equals('admin')}">
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
		</c:if>
		
		<c:if test="${qna_no == n.qna_no && !id.equals('admin')}">
			<table class="table table-hover" >
			  <thead>
				  <tr>
				      <th scope="row">작성일</th>
				      <th scope="row">답변</th>
				      <th scope="row">파일첨부</th>
				    </tr>
				    </thead>
				  <tr>
					  <td>${n.qna_date}</td>
					  <c:if test="${n.qna_fin_check == 'N'}"><td>답변전</td></c:if>
					  <c:if test="${n.qna_fin_check == 'Y'}"><td>답변완료</td></c:if>
					  <c:if test="${n.qna_orifile_name != null && !empty n.qna_orifile_name}"><td> Y</td></c:if>
					  <c:if test="${empty n.qna_orifile_name}"><td> N</td></c:if>
					</tr>
				</table>
		</c:if>
		
		<%-- 문의 내역 --%>
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
					
		<div class="d-flex justify-content-end">
		<%-- 답변이 N이면 목록/수정/삭제--%>
			<c:if test="${n.qna_fin_check == 'N'}">
				<a href="/qna/List.do?id=${id}"><button type="button" class="btn btn-outline-secondary">목록보기</button></a>
				<a href="/qna/Modify.do?qna_no=${n.qna_no}"><button type="button" class="btn btn-outline-primary">수정</button></a>
					<form id="deleteForm" action="${cPath}/qna/Delete.do" method="post">
						<input type="hidden" id="qna_no" name="qna_no" value="${n.qna_no}">
						<input type="hidden" id="qna_fin_check" name="qna_fin_check" value="${n.qna_fin_check}">
						<input type="hidden" id="qna_orifile_name" name="qna_orifile_name" value="${n.qna_orifile_name}">
						<button type="submit" class="btn btn-outline-primary" onclick="return del();">삭제</button>
					</form>
			</c:if>
			
		<%--  답변이 Y이면 목록보기만 보인다. --%>
			<c:if test="${n.qna_fin_check == 'Y'}">
				<a href="/qna/List.do?id=${id}"><button type="button" class="btn btn-outline-primary">목록보기</button></a>
			</c:if>
		</div>

											<%--	!! 관리자 답변 영역	!!	 --%>
	
	<%-- 답변 내역이 Y일때 내용이보이고 //  N일경우 내역없음으로출력된다 --%>
		<c:if test="${n.qna_fin_check == 'Y'}">
	
	<%-- 답변 내용 --%>
		<div class="card mb-3" style="margin-top: 20px;">
			<h3 class="card-header">A. 답변내용 		<a style="font-size: 15px;">답변일 : ${an.qna_a_date}</a></h3>
		  		<div class="card-body" style="margin-bottom : 50px;" >
		    		<h6 class="card-subtitle text-muted" style="margin-bottom: -100px;">답변드립니다.</h6>
		  		</div>
		  			<div class="card-body" >
		    			<p class="card-text" style="white-space: pre-wrap;">${an.qna_a_con}</p>
		    			
		  			</div>
		<%-- 답변 수정 및 삭제하기 --%>
			  			<div class="card-footer text-muted">
			   				<c:if test="${id.equals('admin')}">
								<form action="${cPath}/qnaAn/Modifypage.do">
							  		<input type="hidden" id="qna_no" name="qna_no" value='${qna_no}'>
							  		<input type="hidden" id="id" name="id" value='${id}'>
							  		<button type="submit" id="qna_anmodifyButton2" class="btn btn-outline-secondary">답변수정</button>
								</form>
							</c:if>
						</div>
			</c:if>
			<!-- </div>
			</div> -->
			
	<c:if test="${n.qna_fin_check == 'N' && !id.equals('admin')}">
		<table style="width: 100%; margin-top: 50px;">
	    	<tbody>
	     		<tr>
	        		<td colspan="4" style="text-align: center;">문의 내역이 없습니다.</td>
	      		</tr>
	    	</tbody>
	  	</table>	
	</c:if>
	
	<%-- 문의 답변하기  --%>
	<div class="myqnawrite" style="padding : 0px;">
  		<form action="/qnaAn/Write.do" method="post">
	<div class="myqnaAnWrit">
    		<c:if test="${id.equals('admin') && n.qna_fin_check == ('N')}">
    			<div class="text-center">
      				<button type="button" class="btn btn-outline-primary" onclick="showWriteGroup()">문의답변하기</button>
      			</div>
    		</c:if>
    			<div class="Writegroup" id="Writegroup" style="display: none;"> 
      				<div class="id">
        				<div class="form-group">
         					 <fieldset disabled="">
           						 <div class="row" style="margin-bottom : -10px;">
              						<div class="col-md-2" style="margin-top:5px; font-size: 20px;">
                						<label class="form-label" for="disabledInput">ID:</label>
             						</div>
              						<div class="col-md-4" style="margin-left:-120px;">
                						<input class="form-control" id="disabledInput" type="text" placeholder="${id}" disabled="">
                						<input type="hidden" name="qna_no" value="${n.qna_no}">
             						</div>
           				 		</div>
          					</fieldset>
        				</div>
      				</div>
      				
      				
      			<div class="card mb-3" style="margin-top: 20px;">
        			<h3 class="card-header">A. 답변내용</h3>
        				<div class="card-body">
         					<p class="card-text">
            				<textarea class="form-control" name="qna_a_con" id="qna_a_con" rows="5.5" placeholder="(필수)답변을 작성해주세요."></textarea>
          					</p>
        					</div>
        					
        					<div class="card-footer text-muted">
         						<input type="hidden" name="qna_no" value="${n.qna_no}">
         						<input type="hidden" name="mem_email" value="${n.mem_email}">
         						<input type="hidden" name="userid" value="${n.id}">
          						<button class="btn btn-outline-primary" type="submit" onclick="return anWrite('${qna_a_con}');">확인</button>
        					</div>
      			</div>
    		</div>
		</div>
  </form>
</div><!-- mydetail -->
</div><!--  bodyContent -->
</body>
</html>
