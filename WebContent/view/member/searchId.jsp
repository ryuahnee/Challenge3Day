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
background-color: #D2691E;
}
     
</style>
<title>우리들의 미니멀 챌린지::작심삼일</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
 $(function(){
         let email = ""; 
         let inputCode = ""; 
         let authCode = "";
    $("#emailBtn").on("click",function(){     
         email = $("#email").val(); 
         
         //이메일 필수입력
         if(email===""){ 
             $('#alert').text("가입하신 이메일을 입력해주세요")
            $("#email").focus();
            return false;
         }
         
         //이메일 정규식
         var emailPattern= /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
         if(!emailPattern.test(email)) {
             $('#alert').text("유효하지 않은 이메일 형식입니다");
            $("#email").focus();
         return false;
         }
         
         /*
         보안상 get방식으로 처리한다면 이렇게 해줘도 됨 근데 지금은 get으로 받을때는 폼을 보여주게만 작업해둬서 에러는 안나지만 결과가 나오지는 않음
        type:"get", //요청타입."get" "post"
        url:"${cPath}/member/search.do?searchMember"+userInput,   //요청url.예)"/board/list.do"
         */
         
      $.ajax({
        type:"get",
        url:<%=request.getContextPath() %>"/sendEmail.do?email="+email,
        success:
          function(response){//response안에 응답내용이 문자열로 변환된 json객체를 받는다
			console.log(response);
            if(response==="EmailNotFound"){
             $('#alert').text("입력하신 아이디로 가입된 회원이 없습니다")
            } else {
            	$('#alert').empty();
             $('#alert0').text("메일을 전송했습니다. 시간이 소요될 수 있습니다") 
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
	            	$.get(<%=request.getContextPath() %>"/showId.do", { "email": email, "auth": response },function(data){
	            		var jsonObj = JSON.parse(data);
		            	console.log(jsonObj);
		            	
		            	var body=$("#body");
		            	body.empty();
		            	
		            	var ShowId ="<h2><a href='#'><img src='../img/c3d.png' class='logoimg'></a> 아이디 찾기 성공</h2><div class='container'>"+jsonObj.members[0]+"(으)로 가입된 아이디는<br/><br/><span class='id'>"+jsonObj.members[1]+"</span>입니다.<br/><br/><br/><a href='searchPwd.do' class='pwd'>비밀번호 찾기</a></div>";
		            	body.append(ShowId);
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
<h2><a href='#'><img src='../img/c3d.png' class='logoimg'></a> 아이디 찾기</h2>
<div class="send">*이메일로 인증 코드가 발송됩니다</div>
<div class="container">
<table>
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><br></td>
</tr>	
<tr>
	<th rowspan="1" class="title">이메일</th>
	<td colspan="2"><div><input type="text" name="email" id="email"  maxlength="30" class="input-group"> <button type="button" id="emailBtn" class="">메일 발송</button></div></td>
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
	<th rowspan="1" class="title"><a>인증번호</a></th>
	<td colspan="2"><div><input type="text" name="authCode" id="authCode"  maxlength="10" disabled="disabled" class="input-group">
	<button type="button" id="authBtn" disabled="disabled" class="">인증하기</button></div></td>
</tr>	
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><div id="alert2" class="error"></div></td>
</tr>	
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><br></td>
</tr>	
</table>
</div>
</body>
<script>
const form = document.signUpForm;

function emailValCheck(){
	var emailPattern= /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	var email = form.email.value;
	if(!check(emailPattern, email, "유효하지 않은 이메일 주소입니다.")) {
		return false;
	}
    return true;
}
</script>
</html>