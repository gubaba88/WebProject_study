<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>글 보기</title>
<link rel="stylesheet" href="../css/styles.css">
<script src="../js/jquery-3.5.1.min.js" type="text/javascript"></script>
</head>
<body>
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container px-4 px-lg-5">
		<a class="navbar-brand" href="./BoardList.do"> 메인 화면 </a>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
				<li class="nav-item"><a class="nav-link" href="#">공지사항</a></li>
				<c:choose>
					<c:when test="${empty sessionScope.memId}">
						<li class="nav-item"><a class="nav-link" id="mem_In" href="#">회원 가입</a></li>								
					</c:when>
					<c:when test="${sessionScope.memId == 'admin'}">
						<li class="nav-item"><a class='nav-link' href='#'>회원 목록</a></li>
					</c:when>
					<c:otherwise>
						<li class="nav-item"><a class='nav-link' href='#'>회원 정보</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<span>
			<c:if test="${!empty sessionScope.memId}">
				${sessionScope.memId} 님
			</c:if>
			<c:choose>
				<c:when test="${empty sessionScope.memId}">
					<a id="login_btn" class="btn btn-outline-dark mt-auto" href="#">로그인</a>
				</c:when>
				<c:otherwise>
					<a id='logOut_btn' class='btn btn-outline-dark mt-auto'>로그 아웃</a>
				</c:otherwise>
			</c:choose>
			</span>
		</div>
	</div>
</nav>
<!-- Header-->
<header class="bg-dark py-5">
	<div class="container px-4 px-lg-5 my-5">
		<div class="text-center text-white">
			<h1 class="display-6 fw-bolder"> 댓글 시험용 페이지 </h1>
			<p class="lead fw-normal text-white-50 mb-0">
				<a href="#">게시판</a></p>
		</div>
	</div>
</header>

<section class="py-5">
<div class="container px-4 px-lg-5">
	<table class="container px-4 px-lg-5">
		<tbody>
			<tr class="bg-dark text-white">
				<td class="text-start p-3">[글 주제]</td>
				<td class="text-center p-3">날짜</td>
				<td class="text-end p-3" id="memId">작성자</td>
			</tr>
			<tr>
				<td colspan="3" height="500px" style="padding: 3%; vertical-align: top;">
					글 내용
				</td>
			</tr>
			<tr>
				<td colspan="3" height="50px" class="bg-dark text-white px-4">
					<c:if test="${!empty postDTO.fileName}">
						<label for="">파일 다운 : &nbsp;</label><a href="./upload/${postDTO.fileName}">${postDTO.fileName}</a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
	<div style=" text-align: right; margin: 3px;">
	
	<c:choose>
		<c:when test="${postDTO.memId == sessionScope.memId}">
			<a href="./PostUpdate.do?gameNumber=${gameDTO.gameNumber}&postNumber=${postDTO.postNumber}"><button 
				type="button" class="btn btn-outline-dark mt-auto">글 수정</button></a>
			<button type="button" class="btn btn-outline-dark mt-auto" onclick="postDelete()">글 삭제</button>
		</c:when>
		<c:when test="${sessionScope.memId == 'admin'}">
			<button type="button" class="btn btn-outline-dark mt-auto" onclick="postDelete()">글 삭제</button>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
	
	</div>
	<div>
		<h3> 한줄 댓글 </h3>
		<div>
			<form action="./Comment.do" name="comment_form" id="comment_form">
			<fieldset>
				<div id="comment">
					<label for="comWriter"></label>
					<input type="text" name="comWriter" id="comWriter" placeholder="작성자 : String memId -> varchar2(12) memId">
					<label for="comContent"></label>
					<input type="text" name="comContent" id="comContent" placeholder="내용: String comment_content -> varchar2(60) comment_content">
					<input type="hidden" name="gameNumber" id="gameNumber" value="0">
					<input type="hidden" name="postNumber" id="postNumber" value="0">
					<input type="submit" class="btn btn-outline-dark mt-auto" value="댓글작성">
				</div>
				<p></p>
			</fieldset>
			</form>
		</div>
		<ul id="comment_list"> </ul>
	</div>
</div>
</section>
<!-- Footer-->
<footer class="py-5 bg-dark">
	<div class="container"><p class="m-0 text-center text-white">Copyright &copy; Your Website 2021</p></div>
</footer>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="../js/comment.js"></script>
</body>
</html>