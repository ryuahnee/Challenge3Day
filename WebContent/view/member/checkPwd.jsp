<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://webfontworld.github.io/gmarket/GmarketSans.css" rel="stylesheet">
<style>
body {
    font-family: 'GmarketSans', sans-serif;
    background-color: #FFF5EE;
}

.container {
    max-width: 600px;
    margin: 5px 2px;
    padding: 0px 60px 10px;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.error {
    color: red;
    font-size: 12px;
}

.send {
    color: #696969;
    font-size: 12px;
}

.title {
    color: #000;
    font-size: 15px;
    text-align: left;
}

img {
    width: 13%;
    margin: auto;
    padding: 20px auto;
    cursor: pointer;
}

.id {
color: #FF8C00;
font-weight: bold;
font-size: 24px;
}

.pwd {
text-decoration: none;
text-align: right;
color:dimgrey;
font-size:14px;
padding:4px;
}
.pwd:hover {
font-weight: bold;
}

.input-group {
 width: 70%;
 margin: 4px 4px;
 padding: 5px;
}
    
.input-group label {
 display: block;
 font-size: 14px;
 text-align: left;
 color: #777;
 margin-bottom: 8px;
}

.input-group input {
 padding: 5px;
 font-size: 14px;
 border: 1px solid #ddd;
 border-radius: 4px;
 outline: none;
}

.input-group input:focus {
 border-color: #FF8C00;
}

button {
width: 27%;
padding: 8px;
font-size: 14px;
font-weight:bold;
color: #fff;
background-color: #FF4500;
border: none;
border-radius: 10px;
cursor: pointer;
outline: none;
text-align: center;
}

button:hover {
color: #fff;
background-color: #B22222;
border : none;
box-shadow: 0 3px 8px rgba(0, 0, 0, 0.1);
}
     
</style>
<title>우리들의 미니멀 챌린지::작심삼일</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
 $(function(){
	 $("#pwdBtn").on("click",function(){
         pwd = $("#pwd").val();
         mem_no = $("#mem_no").val();
		 
         //비번필수입력
         if(pwd===""){ 
             $('#alert').text("비밀번호를 입력해주세요");
            $("#pwd").focus();
            return false;
         }
         
	      $.ajax({
	          type:"post",
	          url:<%=request.getContextPath()%>"/checkPwd.do?memNo="+mem_no+"&pwd="+pwd,
	          success:
	            function(response){
	        	  if(response==="Match"){
			      $.ajax({
			          type:"get",
			          url:<%=request.getContextPath()%>"/member/delete.do?no="+mem_no+"&pageNo=1",
		    	      success:
		        	    function(response){
			              opener.location.href = "/logout.do";
			              window.close();
			            },
		    	      error:
		        	     function(jqXHR, textStatus, errorThrown) {
		            	  console.log("error:",textStatus,errorThrown);     
		                  alert("회원탈퇴에 실패했습니다inner");
		          	 }
		         	});			 
	        		  
	        	  }else{
		             $('#alert').text("비밀번호가 일치하지 않습니다");
	        	  }
	            },
	          error:
	             function(jqXHR, textStatus, errorThrown) {
	              console.log("error:",textStatus,errorThrown);     
	               alert("회원탈퇴에 실패했습니다");
	           }
	      });
	 });
});
</script>
</head>
<body>
<h2><a href='#'><img src='../img/c3d.png' class='logoimg'></a> 비밀번호 확인</h2>
<div class="send">*회원탈퇴를 위해서 비밀번호를 확인합니다</div>
<div class="send">*비밀번호가 일치하면 탈퇴가 진행되고 메인페이지로 이동합니다</div>
<div class="container">
<br/>
<br/>
<input type="hidden" name="mem_no" id="mem_no" value="${mem_no}"/>
<label for="pwd" class="title">비밀번호 </label><input type="password" name="pwd" id="pwd" class="input-group"/>
<br/><div id="alert"></div>
<br/>
<br/>
<div style="text-align: center"><button type="button" id="pwdBtn">탈퇴하기</button></div>
<br/>
</div>
</body>
</html>