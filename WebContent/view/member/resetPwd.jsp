<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
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
 width: 119%;
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

.button {
width: 23%;
padding: 5px;
font-size: 13px;
font-weight:bold;
color: #fff;
background-color: #FF8C00;
border: none;
border-radius: 4px;
cursor: pointer;
outline: none;
margin:4px;
}

.button:hover {
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
</head>
<body>
 <h2><a href='#'><img src='../img/c3d.png' class='logoimg'></a> 비밀번호 재설정</h2>  
 <div class="container">
  <form id="resetPwdForm" action="${cPath}/resetPwd.do" method="post"> 
 <table>
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><br></td>
</tr>	
<tr>
	<th class="title">새 비밀번호</th>
	<td><input type="password" name="newPwd" id="newPwd" class="input-group"  placeholder="6~50자 영대소문자,숫자,특수문자" pattern="[A-Za-z0-9!@#$%^&*()_-+=[]{}~?:;`|/]{6,50}$"/>
	</td>
</tr>
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><c:if test="${errors.newPwd}"><span class="error">새 비밀번호를 입력하세요</span></c:if></td>
</tr>	
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><br></td>
</tr>	
<tr>
	<th rowspan="2" class="title">비밀번호 확인</th>
	<td colspan="2"><input type="password" name="pwdConfirm" id="pwdConfirm" class="input-group" />
	<input type="hidden" name="authPass" id="authPass" value="false">
</td>
</tr>	
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><c:if test="${errors.pwdConfirm}"><span class="error">비밀번호가 일치하지 않습니다</span></c:if></td>
</tr>	
<tr>
	<th rowspan="1"></th>
	<td colspan="2"><br></td>
</tr>	
</table>
 	<div style="text-align:center">
	 	<input  type="submit" value="비번변경" class="button"/>
	 	<input  type="reset"  value="취소" class="button"/>
	</div> 
  </form>		
 </div>
</body>
</html>