<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>우리들의 미니멀 챌린지::작심삼일</title>
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
 <link href="https://webfontworld.github.io/gmarket/GmarketSans.css" rel="stylesheet">
 <style>
   body {
    font-family: 'GmarketSans', sans-serif;
    background-color: #FFF5EE;
   }

   .container {
    max-width: 400px;
    margin:0 auto;
    padding: 40px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
    }

   .logo {
	margin-top: 10px;
	font-weight: bold;
	font-size: 50px;
	letter-spacing: 3px;
    color: #FF8C00;
    }

   .input-group {
    margin: 20px 0;
    }
    
   .input-group label {
    display: block;
    font-size: 14px;
    text-align: left;
    color: #777;
    margin-bottom: 8px;
    }

    .input-group input {
    width: 100%;
    padding: 12px;
    font-size: 16px;
    border: 1px solid #ddd;
    border-radius: 4px;
    outline: none;
    }

    .input-group input:focus {
    border-color: #FF8C00;
    }
    
    .login-button {
     width: 100%;
     padding: 12px;
     font-size: 20px;
     font-weight: bold;
     color: #fff;
     background-color: #FF8C00;
     border: 2px solid #FF8C00;
     border-radius: 50px;
     cursor: pointer;
     outline: none;
     }

    .login-button:hover {
     color: #FF8C00;
     background-color: #fff;
     border : 2px solid #FF8C00;
    }
    
    .logoimg{
    width: 35%;
    margin: 0;
    }

    .footer {
     margin-top: 20px;
     margin-top: 20px;
     font-size: 14px;
    }
    

	.footer a {
	color: dimgrey;
	opacity: 0.6;
    text-decoration: none;
	}

	.footer a:hover {
	color: dimgrey;
	opacity: 1;
    text-decoration: none;
  	font-weight: bold;
	}
	
    </style>
</head>

<body>
 <div class="container">
 <img src="../img/login.png" class="logoimg">
   <form id="loginForm" action="${cPath}/login.do" method="post">
    <div class="input-group">
     <label for="id"></label>
      <input type="text" id="id" name="id" placeholder="아이디">
       <c:if test="${errors.id}"><div style="color:red"><br/>아이디를 입력하세요</div></c:if>
    </div>
    <div class="input-group">
     <label for="pwd"></label>
      <input type="password" id="pwd" name="pwd" placeholder="비밀번호">
       <c:if test="${errors.pwd}"><div style="color:red"><br/>비밀번호를 입력하세요</div></c:if>
       <c:if test="${errors.idOrPwNoMatch}"><div style="color:red"><br/>아이디 또는 비밀번호가 일치하지 않습니다</div></c:if>
    </div>
    <button type="submit" class="login-button">로 그 인</button>
   </form>
  <div class="footer"><a href="${cPath}/join.do">회원가입</a> | <a href="#" onclick="openLinkPopup('${cPath}/searchId.do')">아이디 찾기</a> | <a href="#" onclick="openLinkPopup('${cPath}/searchPwd.do')">비밀번호 찾기</a></div>
 </div>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
   <script>
      function openLinkPopup(linkURL) {
          window.open(linkURL, '_blank', 'width=480,height=300');
      }
    </script>  
</body>
</html>
