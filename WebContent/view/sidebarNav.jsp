<%@page import="notice.model.Notice" %>
<%@page import="chall.model.CallStatusDTO" %>
<%@page import="chall.model.Chall"	 %>
<%@page import="chall.model.ChallOpin" %>
<%@page import="event.model.Event" %>
<%@page import="member.model.Auth" %>
<%@page import="member.model.Member" %>
<%@page import="member.model.MemberChallList" %>
<%@page import="member.model.MemberChallStatus" %>
<%@page import="myPage.model.MemberBadge" %>
<%@page import="myPage.model.MypageDTO" %>
<%@page import="qnaboard.model.Qna_answer_boardDTO" %>
<%@page import="qnaboard.model.Qna_boardDTO" %>



<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
<!-- bootswatch united theme -->
<link rel="stylesheet" href="../../../css/bootstrap.min.css">
<!--  Css 스타일 정의 -->
<link rel="stylesheet" href="../css/sidebarNav.css">
  <!--  FONTAWESOME: https://kit.fontawesome.com/a23e6feb03.js -->
 	<script src="/js/icons.js"></script>
<!-- FONTAWESOME : https://kit.fontawesome.com/a23e6feb03.js -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">



<nav class="navbar navbar-expand-lg bg-blur fixed-top">
   
    <button id="sidebarCollapse" class="btn navbar-btn">
      <i class="fas fa-lg fa-bars"></i>
    </button>
    
    <a class="navbar-brand" href="/chall/list.do">
	         <img src="/img/c3d.png" alt="작심삼일 챌린지 로고">
	     </a>
	     

   
   <%-- 로그인 상태를 가정하여 세션에 사용자 ID를 설정 테스트
	<c:set var="userID" value="AUTH_USER" />--%>
	     
      <!-- 로그인/회원가입 또는 로그아웃 버튼 -->
    <ul class="navbar-nav justify-content-end">
        <c:choose>
            <%-- 로그인 되어있지 않은 경우 --%>
            <c:when test="${empty AUTH_USER}">
                <li class="nav-item">
                    <a class="nav-link" href="${cPath}/login.do">
                        <button type="button" class="btn btn-primary">로그인</button>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${cPath}/join.do">
                        <button type="button" class="btn btn-primary">회원가입</button>
                    </a>
                </li>
            </c:when>
            <%-- 로그인 된 경우 --%>
            <c:otherwise>
                <li class="nav-item">
                    <a class="nav-link" href="${cPath}/logout.do">
                        <button type="button" class="btn btn-outline-secondary">로그아웃</button>
                    </a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>



	<!--  wrapper: 사이드바와 콘텐츠를 감싸는 컨테이너 역할 -->
  <div class="wrapper fixed-left">
     <nav id="sidebar">
	
	 <div class="sidebar-header">
    <c:if test="${not empty AUTH_USER}">
        <h4><i class="fas fa-user"></i>${AUTH_USER.nickname}님<br/>환영합니다</h4>
    </c:if>
    <c:if test="${empty AUTH_USER}">
        <h4><br/>로그인 해주세요</h4>
    </c:if>
</div>
	
    <ul class="list-unstyled components">
     <li class="active">
          <a href="#challengeDropdown" class="dropdown-toggle" data-toggle="collapse">
	          	<i class="fas fa-home"></i>모든 챌린지</a>
			         <ul class="collapse list-unstyled" id="challengeDropdown">
			         	 <li><a href="${cPath}/chall/list.do">모든 챌린지</a></li>
				      	 <li><a href="${cPath}/chall/cate.do?cate_no=2">생활습관</a></li>
				      	 <li><a href="${cPath}/chall/cate.do?cate_no=3">자기계발</a></li>
				         <li><a href="${cPath}/chall/cate.do?cate_no=1">운동</a></li>
			   		</ul>
  
	 </li>
        
         <li>
		 <a href="#boardDropdown" class="dropdown-toggle" data-toggle="collapse">
		 	<i class="fas fa-calendar-check"></i>공지/이벤트</a>
  				    <ul class="collapse list-unstyled" id="boardDropdown">
     
				        <li><a href="${cPath}/notice/list.do"">공지사항</a></li>
				     	<li><a href="${cPath}/event/list.do">이벤트</a></li>
					</ul>
       				 
	 	</li>
           <%-- 로그인 정보에 따라 마이페이지 또는 로그인 페이지 보여주기 --%>
                <c:choose>
                    <%-- userID가 'admin'인 경우 회원관리 메뉴 보여주기 --%>
                    <c:when test="${AUTH_USER.id eq 'admin'}">
                        <li id="memberManagement">
                            <a href="${cPath}/member/list.do"><i class="fas fa-user-cog"></i>회원관리</a>
                        </li>
                    </c:when>
                    <%-- 로그인하지 않은 경우 또는 userID가 'admin'이 아닌 경우 마이페이지 메뉴 보여주기 --%>
                    <c:otherwise>
                        <li id="myPage">
                            <a href="${cPath}/myPage/list.do"><i class="fas fa-clipboard"></i>마이페이지</a>
                        </li>
                    </c:otherwise>
                </c:choose>
                
                
                  <li>
       				 <a href="${cPath}/qna/List.do"><i class="fas fa-question"></i>QnA</a>
     			 </li>
                
                
      </ul>
    </nav>

  </div>

    
<!-- jQuery first, then Popper.js, then Bootstrap JS --> 
     <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script>

	$(document).ready(function() {
		// 사이드바 숨김 버튼을 클릭했을 때 사이드바와 콘텐츠를 토글합니다
    $('#sidebarCollapse').on('click', function() {
      $('#sidebar, #bodyContent').toggleClass('active');
      $('.collapse.in').toggleClass('in');
      $('a[aria-expanded=true]').attr('aria-expanded', 'false');
   	 });
	});
	
    
  

</script>
    

