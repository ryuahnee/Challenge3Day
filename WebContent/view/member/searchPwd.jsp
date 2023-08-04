<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
body {
    font-family: 'GmarketSans', sans-serif;
    background-color: #FFF5EE;
}

.container {
    max-width: 600px;
    margin: 5px 2px;
    padding: 10px 50px 10px;
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
 width: 60%;
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
width: 28%;
padding: 5px;
font-size: 13px;
font-weight:bold;
color: #fff;
background-color: #FF8C00;
border: none;
border-radius: 4px;
cursor: pointer;
outline: none;
}

button:hover {
width: 28%;
padding: 5px;
font-size: 13px;
font-weight:bold;
color: #fff;
background-color: #D2691E;
border: none;
border-radius: 4px;
cursor: pointer;
outline: none;
}
     
</style>
<title>우리들의 미니멀 챌린지::작심삼일</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
 $(function(){
	 	 let id = ""
         let inputCode = ""; 
         let authCode = "";
    $("#emailBtn").on("click",function(){     
         id = $("#id").val(); 

         //이메일 필수입력
         if(id===""){ 
             $('#alert').text("아이디를 입력해주세요")
            $("#id").focus();
            return false;
         }

      $.ajax({
        type:"get",
        url:<%=request.getContextPath() %>"/sendEmail.do?id="+id,
        success:
          function(response){//response안에 응답내용이 문자열로 변환된 json객체를 받는다
            if(response==="EmailNotFound"){
             $('#alert').text("입력하신 아이디로 가입된 회원이 없습니다")
            } else {
            	$('#alert').empty();
             $('#alert0').text("메일을 전송했습니다. 시간이 소요될 수 있습니다") ;
            $('#authCode'). prop('disabled', false);
            $('#authBtn'). prop('disabled', false);
            $('#email'). prop('disabled', true);
            $('#emailBtn'). prop('disabled', true);
            authCode=response;
            }
            
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
    		 
       $("#authBtn").on("click",function(){ 
    	   inputCode = $("#authCode").val();
    	   
           //인증번호 필수입력
           if(inputCode===""){ 
             $('#alert2').text("인증번호를 입력해주세요"); 
              $("#authCode").focus();
              return false;
           }

	      $.ajax({
	        type:"get",
	        url:<%=request.getContextPath() %>"/authCheck.do",
	        data:{"authCode":authCode, "inputCode":inputCode},
	        success:
	          function(response){//response안에 응답내용이 문자열로 변환된 json객체를 받는다
	            if(response==="authCodeMatch"){
                    $.ajax({
                        type: "get",
                        url: <%=request.getContextPath() %>"/saveAuth.do", // 서버에 세션 저장을 위한 URL
                        data: {"authSuccess": "true","inputId": id},
                        success: function(response) {
                        	window.location.href = "/resetPwd.do";
                        	
                        },
                        error: function(jqXHR, textStatus, errorThrown) {
                            console.log("error:", textStatus, errorThrown);
                            alert("세션 저장 오류");
                        }
                    });
                 } else{
	              $("#authCode").focus();
	              $('#email'). prop('disabled', true);
	              $('#emailBtn'). prop('disabled', true);
	              $('#alert2').text("인증번호가 올바르지 않습니다"); 
	            }
	            
	          },
	        error://오류발생 호출 함수. 4xx 또는 5xx
	             // jqXHR: XMLHttpRequest 객체
	             // textStatus: 에러 상태를 설명하는 문자열
	             // errorThrown: 에러의 예외 객체 (예외가 발생하지 않으면 undefined)
	           function(jqXHR, textStatus, errorThrown) {
	            console.log("error:",textStatus,errorThrown);     
	             alert("아작스2에러"+textStatus);      
	         }
	       });//ajax()끝

    	   
       });
         
 });
 </script>
</head>
<body id="body">
<h2><a href='#'><img src='../img/c3d.png' class='logoimg'></a> 비밀번호 찾기</h2>
<div class="send">*계정에 등록된 이메일로 인증 코드가 발송됩니다</div>
<div class="container">
<table>
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><br></td>
</tr>	
<tr>
	<th class="title">아이디</th>
	<td><input type="text" name="id" id="id"  maxlength="30" class="input-group">
	<button type="button" id="emailBtn" class="">메일 발송</button>
	</td>
</tr>
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><div id="alert" class="error"></div><div id="alert0" class="send"></div></td>
</tr>	
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><br></td>
</tr>	
<tr>
	<th rowspan="2" class="title"><a>인증번호</a></th>
	<td colspan="2"><input type="text" name="authCode" id="authCode"  maxlength="10" disabled="disabled" class="input-group">
	<button type="button" id="authBtn" disabled="disabled" class="">인증하기</button>
	<input type="hidden" name="authPass" id="authPass" value="false">
</td>
</tr>	
<tr>
	<td colspan="2"><div id="alert2" class="error"></div></td>
</tr>	
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><br></td>
</tr>	
</table>
</div>
</body>
</html>