<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>임시 이동 성공 페이지</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="https://tistory4.daumcdn.net/tistory/3134841/skin/images/confetti_v2.js"></script>
<style>
canvas{
z-index:10;
pointer-events: none;
position: fixed;
top: 0;
transform: scale(1.1);
}
</style>
</head>

<script>
$(document).ready(function(){
  function reAction(){
   $("#startButton").trigger("click");
 
   setTimeout(function(){
   $("#stopButton").trigger("click");
   }, 2500);
  }
 
  $("#wow").on('click', function(){
  reAction();
  });
});
</script>

<body>
<canvas id="canvas"></canvas>

<div style="display:none;">
<button id="startButton"></button>
<button id="stopButton"></button>
</div>
<button id="wow">아무것도 안했지만 축하받고싶다</button>

</body>
</html>