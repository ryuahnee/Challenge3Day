<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
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
    max-width: 400px;
    margin:0 auto;
    padding: 40px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
    }
    
    .button {
     width: 35%;
     padding: 12px;
     font-size: 14px;
     font-weight:bold;
     color: #fff;
     background-color: #FF8C00;
     border: none;
     border-radius: 10px;
     cursor: pointer;
     outline: none;
     }

    .button:hover {
     color: #fff;
     background-color: #D2691E;
     border : none;
     box-shadow: 0 3px 8px rgba(0, 0, 0, 0.1);
    }
</style>
<title>우리들의 미니멀 챌린지::작심삼일</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
$(document).ready(function() {
    $("#login").on("click", function() {
      opener.location.href = <%=request.getContextPath()%>"/login.do";
      window.close();
    });
  });
</script>
</head>
<body>
<div class="container">
비밀번호가 변경되었습니다<br/>
다시 로그인하세요<br/><br/><br/>
<button type="button" id="login" class="button">로그인 페이지로</button>
</div>
</body>
</html>