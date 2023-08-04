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

.bigcontainer {
    max-width: 600px;
    margin: 0px auto 80px auto;
    padding: 10px 80px 50px;
    background-color: #FFF5EE;
}
.container {
    max-width: 600px;
    margin: 0px auto 80px auto;
    padding: 10px 80px 50px;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

img {
    width: 10%;
    margin: 0px auto;
    padding: 10px auto;
    cursor: pointer;
}
    
h1 {
    padding: 10px auto;
    max-width: 600px;
    text-align: left;
}

label {
    display: block;
    margin-bottom: 5px;
}

input[type="text"],
input[type="email"],
input[type="password"],
input[type="date"] {
    width: 95%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    margin-bottom: 15px;
}

input[type="radio"] {
    margin-right: 5px;
    margin-bottom: 15px;
}

input:focus {
    border-color: #FF8C00;
    }

textarea{
    width: 95%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    margin-bottom: 5px;
	
}

input[type="submit"] {
    width: 95%;
    padding: 10px;
    color: #fff;
    background-color: #FF8C00;
    border: 2px solid #FF8C00;
    border-radius: 50px;
    cursor: pointer;
    outline: none;
}

input[type="submit"]:hover {
    color: #FF8C00;
    background-color: #fff;
    border : 2px solid #FF8C00;
}
</style>

<title>우리들의 미니멀 챌린지::작심삼일</title>
<script src="../../js/jquery-3.7.0.js"></script>
</head>
<body>
<div class="bigcontainer">
    <h1><a href="#"></a><img src="../img/c3d.png" class="logoimg"></a><div>회원가입</div></h1>
    <div class="container">
        <form id="joinForm" action="${cPath}/join.do" method="post" onsubmit="return validCK();">
		    <label for="">ID</label>
		    <input type="text" name="id" id="id" class="" maxlength="15" placeholder="6~15자 영대소문자,숫자" pattern="[A-Za-z0-9]{6,15}$"/>
		    <c:if test="${errors.id}"><div style="color:red;">아이디를 입력하세요</div></c:if>
		    <c:if test="${errors.duplicatedId}"><div style="color:red">이미 사용중인 아이디입니다</div></c:if>
		   
		    <label for="">비밀번호</label>
		    <input type="password" name="pwd" id="pwd" class="" placeholder="6~50자 영대소문자,숫자,특수문자" pattern="[A-Za-z0-9!@#$%^&*()_-+=[]{}~?:;`|/]{6,50}$"/>
		    <c:if test="${errors.pwd}"><div style="color:red">비밀번호를 입력하세요</div></c:if>
		
		    <label for="">비밀번호 확인</label>
		    <input type="password" name="confirmPassword" id="confirmPassword" class="" placeholder="6~50자 영대소문자,숫자,특수문자" pattern="[A-Za-z0-9!@#$%^&*()_-+=[]{}~?:;`|/]{6,15}$"/>
		    <c:if test="${errors.confirmPassword}"><div style="color:red">확인용 비밀번호를 입력하세요</div></c:if>
		    <c:if test="${errors.notMatch}"><div style="color:red">비밀번호가 다릅니다</div></c:if>
		
		    <label for="">이름</label>
		    <input type="text" name="mem_name" id="mem_name" class="" maxlength="45" placeholder="1~45자"/>
		    <c:if test="${errors.mem_name}"><div style="color:red">이름을 입력하세요</div></c:if>
		
		    <label for="">닉네임</label>
		    <input type="text" name="nickname" id="nickname" class="" maxlength="10" placeholder="1~10자"/>
		    <c:if test="${errors.nickname}"><div style="color:red">닉네임을 입력하세요</div></c:if>
		    <c:if test="${errors.duplicatedNickname}"><div style="color:red">이미 사용중인 닉네임입니다</div></c:if>
		
		    <label for="">생일</label>
		    <input type="date" name="birthyear" id="birthyear" class="" value="sysdate" min="1800-01-01"/>
		    <c:if test="${errors.birthyear}"><div style="color:red">생일을 선택하세요</div></c:if>
		
		    <label for="">이메일</label>
		    <input type="text" name="mem_email" id="mem_email" class="" placeholder="ex) challenge3@gmail.com" pattern="[a-zA-Z0-9]+@[a-z]+\.[a-z]{2,3}"/>
		    <c:if test="${errors.mem_email}"><div style="color:red">이메일을 입력하세요</div></c:if>
		    <c:if test="${errors.duplicatedEmail}"><div style="color:red">이미 사용중인 이메일입니다</div></c:if>
		
		    <label for="">성별</label>
		    <input type="radio" name="gender" id="gender0" class="" value="남" checked="checked"/>남성
		    <input type="radio" name="gender" id="gender1" class="" value="여"/>여성
		    <input type="radio" name="gender" id="gender2" class="" value="선택안함"/>선택안함
		    <c:if test="${errors.gender}"><div style="color:red">성별을 선택하세요</div></c:if>
			<br/>
			<br/>
			<hr/>
			<br/>
		    <label for="">약관동의 [필수]</label>
		    <textarea rows="6" readonly="readonly" style="resize: none;">
[작심삼일 회원가입 필수 약관]

1.약관의 적용
이 약관은 작시삼일 웹사이트 회원가입과 관련하여 회원과 사이트 운영자 사이의 권리와 의무를 규정합니다.

2.개인정보의 수집과 이용
회원가입을 위해 제공하는 개인정보는 사이트의 서비스 제공 및 운영에 필요한 최소한의 정보를 수집하며, 개인정보 처리에 대한 내용은 개인정보처리방침에 따릅니다.

3.회원의 ID 및 비밀번호 관리
회원은 자신의 ID와 비밀번호를 관리해야 합니다. 이들 정보의 관리는 회원 본인의 책임이며, 제3자에게 이용을 허락해서는 안 됩니다. 회원은 자신의 ID 또는 비밀번호가 타인에 의해 도용되거나 무단 사용된 사실을 발견한 경우 즉시 사이트 운영자에게 알려야 합니다.

4.서비스 이용 제한
사이트 운영자는 회원이 다음 각 호에 해당하는 경우 사전 통보 없이 회원의 서비스 이용을 제한할 수 있습니다.

5.타인의 개인정보를 도용하는 경우
-서비스 운영에 심각한 장애를 초래하는 경우
-사이트에 위해를 가하는 행위를 하는 경우
-게시물의 관리
-회원이 사이트에 게시하는 게시물은 회원 본인의 책임 하에 작성되어야 합니다. 게시물이 제3자의 저작권 등을 침해하거나 불법적인 내용을 포함하는 경우, 사이트 운영자는 이를 삭제하거나 해당 게시물을 수정할 수 있습니다.

6.책임 제한
사이트 운영자는 회원의 귀책사유로 인한 서비스 이용의 장애에 대해 책임을 지지 않습니다. 또한, 회원이 타인의 정보를 무단으로 사용하여 발생하는 문제에 대해서도 사이트 운영자는 책임을 지지 않습니다.

7.약관의 변경
사이트 운영자는 필요한 경우 본 약관을 변경할 수 있으며, 변경된 약관은 사이트에 게시함으로써 효력이 발생합니다. 회원은 정기적으로 약관을 확인해야 하며, 변경에 대한 동의 여부를 결정할 수 있습니다.

8.이상의 약관에 동의하는 경우에만 회원가입을 진행해주시기 바랍니다. 만약 동의하지 않을 경우 회원가입이 불가능하며, 기존 회원이라면 서비스 이용이 제한될 수 있습니다.
		    </textarea><br/>		    
		    <input type="radio" name="agree" id="agree0" class="" value="Y" checked="checked"/>동의
		    <input type="radio" name="agree" id="agree1" class="" value="N"/>미동의
			<div id="notAgree" style="color:red"></div>
		  	
		    <label for="">마케팅 수신 동의 [선택]</label>		    
		    <textarea rows="6" readonly="readonly"  style="resize: none;">
[마케팅 수신 동의]

마케팅 수신 동의에 대한 안내
회원가입 시에는 챌린지 웹사이트가 제공하는 다양한 서비스와 이벤트에 대한 정보를 수신할 수 있습니다. 이러한 정보는 새로운 서비스 소식, 특별 할인 혜택, 이벤트 등의 관련 정보를 포함합니다.

마케팅 수신 동의 내용

1.이메일 수신 동의
챌린지 웹사이트는 회원의 이메일 주소를 수집하여 마케팅 메일을 발송할 수 있습니다.
이메일 수신에 동의하시면, 최신 정보, 프로모션, 이벤트 소식 등을 이메일로 받아보실 수 있습니다.

2.동의 철회 방법
회원은 언제든지 마케팅 수신 동의를 철회할 수 있습니다. 마케팅 수신 동의 철회는 사이트 내 마이페이지 또는 마케팅 메일의 "수신 거부" 링크를 통해 간편하게 처리하실 수 있습니다.

3.마케팅 수신 동의 유의사항
마케팅 수신 동의는 챌린지 웹사이트의 다양한 혜택과 정보를 누리는 데 도움을 드리기 위한 것이며, 이는 선택적인 동의 사항입니다. 동의하지 않아도 서비스 이용에 영향이 없습니다.
회원님의 개인정보는 개인정보처리방침에 따라 안전하게 보호되며, 제3자에게 제공되지 않습니다.
위의 내용을 확인하시고 마케팅 수신 동의 여부를 결정해주시기 바랍니다. 동의하지 않을 경우에도 정상적인 회원가입이 가능하며, 이후에도 언제든지 마음이 바뀌신 경우 동의를 철회할 수 있습니다.
		    </textarea><br/>    
		    <input type="radio" name="isMarketing" id="isMarketing0" class="" value="Y" checked="checked"/>동의
		    <input type="radio" name="isMarketing" id="isMarketing1" class="" value="N"/>미동의
			<br/>
			<br/>
			<br/>
            <input type="submit" style="text-align: center;" value="가입하기">
        </form>
    </div>
</div>
<script>
		//생일 입력 제한
		var now_utc = Date.now() // 지금 날짜를 밀리초로
		var timeOff = new Date().getTimezoneOffset()*60000; // 분단위를 밀리초로 변환 getTimezoneOffset()은 현재 시간과의 차이를 분 단위로 반환
		var today = new Date(now_utc-timeOff).toISOString().split("T")[0];// new Date(now_utc-timeOff).toISOString()은 '2022-05-11T18:09:38.134Z'를 반환
		document.getElementById("birthyear").setAttribute("max", today);

	function validCK() {
		//약관 동의 필수
	    var agree0 = document.getElementById('agree0');
	    if (!agree0.checked) {
		    document.getElementById('notAgree').textContent = '약관동의는 필수항목입니다';
		    return false;
	    }
	    return true;
    }
</script>
</body>
</html>