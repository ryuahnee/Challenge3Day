 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<style>
  .d-grid {
    width: 100%;
  }
body {
	font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
        text-align: center; /* Center-align all elements inside the body */
    }
.submit button[type="submit"]{
	width: 2000px
}

.submit {
    display: flex;
    justify-content: center;
  }

  .form-check {
    display: flex;
    justify-content: center;
  }

  .form-check-label {
    margin-left: 5px;
  }
  
  #qna_orifile_name,
  #qna_con,
  #qna_title {
    width: 1000px;
    margin: 0 auto;
  }
  
   #id,
  .form-group,
  .form-check,
  .submit {
    width: 1000px;
    margin: 0 auto;
  }

  #id input {
    width: 300px;
  }
  #qna_title{
  margin-bottom: 20px;
  }
 
  .agree {
    width: 1000px;
    margin: 0 auto;
  }
  
   .form-group {
    margin-bottom: 10px; 
  }

  .form-group strong {
    display: inline-block;
    width: 150px; /* "ID" and "문의유형" */
  }

  .form-group input[type="text"],
  .form-group textarea {
    width: 100%;
  }
  
   .agree {
   width: 1000px;
   border: solid #E6E6E6; 
   border-radius: 10px;
   margin-top: 10px;
   margin-right: 100px;
   margin: 0 auto;
  }
  
	.table.table-hover thead,
	.table.table-hover tbody,
	.table.table-hover tfoot,
	h3 {
  text-align: center;
}
  .userid {
    width: 1000px;
    margin: 0 auto;
  }
  
  .agreetable {
    width: 1180px;
    margin: 0 auto;
    border: 1px solid #E6E6E6; 
    padding: 10px; 
    margin-top: 15px;
    border-radius: 10px;
    margin-bottom: 10px;
  }
  .form-check,
  .btn btn-lg btn-primary{
  	 margin: 0 auto;
  }

  .agreetable-content {
    width: 1000px;
    margin: 0 auto;
  }
   @media (max-width: 768px) {
    .agreetable,
    #qna_orifile_name,
    #qna_con,
    #qna_title,
    #id input,
    .form-group,
    .form-check,
    .submit {
      width: 80%; 
      margin: 0 auto;
    }
  }
  
  /* 첨부파일 안내문구 */
  .text-secondary{
  	font-size: 15px;
  }

</style>
<meta charset="UTF-8">
  <title>우리들의 미니멀 챌린지::작심삼일</title>
<link rel="stylesheet" href="../../../css/bootstrap.min.css">
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard-dynamic-subset.css" />
<!-- 사용자 정의 CSS 파일을 Bootstrap CSS 파일 이후에 포함시킵니다. -->
	<link rel="stylesheet" href="/css/sidebarNav.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script>

function qnaWrite() {
	
	let isagree = document.getElementById("flexCheckChecked");
	
 if(isagree.checked === false){
		alert("약관에 동의해주세요.")
		isagree.focus();
		return false;
	}else{
		return true;
	}
}	

</script>
</head>

<body>
 <!-- SidebarNav.jsp 파일을 포함시키기. -->
  <%@ include file="/view/sidebarNav.jsp" %>
  <div id="bodyContent">
		<h3 style="margin-bottom:40px; margin-top:20px; text-align: left; text-decoration: none; margin-left:215px;"><a href="/qna/List.do?id=${id}"  style="text-decoration: none;">1:1 문의하기</a></h3>				<!-- 클릭시  list로 이동 -->
			<form action="${cPath}/qna/Write.do" method="post" enctype="multipart/form-data">
				<div class="form-group">
					  <fieldset disabled="">
					    <div class="row">
					      <div class="col-md-2">
					        <label class="form-label" for="disabledInput"><strong>ID:</strong></label>
					      </div>
					      <div class="col-md-4">
					        <input class="form-control" id="disabledInput" type="text" placeholder="${id}" disabled="">
					      </div>
					    </div>
					  </fieldset>
					</div>
					
		<%--문의 유형--%>	
					<div class="form-group">
						<div class="row">
							<div class="col-md-2">
					  			<strong>문의유형 <span id="star">*</span></strong>
					  		</div>
						<div class="col-md-10">
							<input class="form-control form-control-lg" type="text" id="qna_title" name="qna_title" placeholder="(필수)문의제목을 작성해주세요." required="required">
						</div>
					</div>
		<%--문의 내용--%>		
    			<div class="form-group">
    				<div class="row">
						<div class="col-md-2">
    						<strong>문의내용 <a id="star">*</a></strong>
    					</div>
    				<div class="col-md-10">
					    <span><textarea class="form-control" name="qna_con" id="qna_con" placeholder="(필수)문의내용을 자세하게 작성해주세요. 자세한내용을 작성해주시면 더욱 신속히 답변드릴수 있습니다." rows="9" cols="33" required="required"></textarea></span>
    				</div>
    			</div>
    				<hr align="center" width="118%">
    	<%--파일 업로드--%>					
    			<div class="form-group" style="margin-top: 20px;">
     				 <input class="form-control" type="file" id="qna_orifile_name" name="qna_orifile_name" >
     				 <p class="text-secondary">*첨부파일은 최대 50MB까지 등록 가능합니다.</p>
  				</div>
  				<hr align="center" width="118%">
  				
  			<!-- 약관동의 -->
    			<div class="agreetable">
	    			<div class="text" style="width:99%;">    
	      				(필수) 개인정보 수집·이용에 대한 안내<br/><br/>
	     	 			(주)작심삼일은 이용자 문의를 처리하기 위해 다음과 같이 개인정보를 수집 및 이용하며,
	     				이용자의 개인정보를 안전하게 취급하는데 최선을 다하고 있습니다.
	      			</div>
	      			<div style="margin-top: 10px;">
	    				<table class="table table-hover"style="border: 1px solid #E6E6E6; , margin-top: 10px;">
					        <thead>
					        	<tr>
							        <th style="border: 1px solid #E6E6E6;" scope="row">수집 항목</th>
								    <th style="border: 1px solid #E6E6E6;" scope="row">수집 목적</th>
								    <th style="border: 1px solid #E6E6E6;" scope="row">보유 기간</th>
					        	</tr>
					        </thead>
					        <tbody style="height: 50px;">
					        	<tb>
						        	<td style="border: 1px solid #E6E6E6;" >(필수)이메일 주소,성함 <br/></td>
						        	<td style="border: 1px solid #E6E6E6;" >문의·요청·불편사항 확인 및 처리결과 회신</td>
						        	<td style="border: 1px solid #E6E6E6;" >3년간 보관 후 지체없이 파기</td>
					        	</tb>
					        </tbody>
					  	</table>
				  	</div>
				    <div class="text">
					      <a>위 동의를 거부 할 권리가 있으며, 동의를 거부하실 경우 문의 처리 및 결과 회신이 제한됩니다.</a><br/>
					      	문의등록 후 관리자 답변 이후 수정 및 삭제가 불가합니다.
				     </div>
				 </div>
				<div class="form-check">
			        	<input class="form-check-input" type="checkbox" value="" id="flexCheckChecked">
			        	<label class="form-check-label" for="flexCheckChecked">해당 내용을 확인 및 동의합니다.</label>
			    </div>	
     <!-- 문의등록 -->
        <div class="d-grid gap-2" style="margin-top: 20px; width: 1180px; margin: 0 auto;">
            <button type="submit" class="btn btn-lg btn-primary" id="submit" onclick="return qnaWrite()">문의등록</button>
        </div>
			</form>
			</div>


<%-- 임시저장 : C:\webstudy\jspwk\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\work\Catalina\localhost\ROOT\upload 
			실제 주소 :C:\webstudy\jspwk\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Challenge3Days\upload\qna
		--%>
	</body>	
</html>