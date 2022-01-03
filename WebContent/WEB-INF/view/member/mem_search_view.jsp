<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
<link rel="stylesheet" href="./css/styles.css">
<script src="./js/jquery-3.5.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("#limit").change(function() {
		location.href="./MemSearch.do?limit="+$(this).val()+"&keyword=${keyword}";
	});
	if (${!empty limit}) {
		$("#limit").val(${limit}).prop('selected', true);
	}
})
</script>
</head>
<body>
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container px-4 px-lg-5">
		<a class="navbar-brand" href="./BoardList.do"> 메인 화면 </a>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
				<li class="nav-item"><a class="nav-link" href="./PostSelectAll.do?gameNumber=0">공지사항</a></li>
				<c:choose>
					<c:when test="${empty sessionScope.memId}">
						<li class="nav-item"><a class="nav-link" id="mem_In" href="./MemInsert.do">회원 가입</a></li>								
					</c:when>
					<c:when test="${sessionScope.memId == 'admin'}">
						<li class="nav-item"><a class='nav-link' href='./MemSelectAll.do'>회원 목록</a></li>
					</c:when>
					<c:otherwise>
						<li class="nav-item"><a class='nav-link' href='./MemSelect.do?memId=${sessionScope.memId}'>회원 정보</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<span>
			<c:if test="${!empty sessionScope.memId}">
				${sessionScope.memId} 님
			</c:if>
			<c:choose>
				<c:when test="${empty sessionScope.memId}">
					<a id="login_btn" class="btn btn-outline-dark mt-auto" href="./Login.do">로그인</a>
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
			<h1 class="display-4 fw-bolder"> 회원 검색 목록 </h1>
			<p class="lead fw-normal text-white-50 mb-0">검색된 회원  ${listCount}명</p>
		</div>
	</div>
</header>
<section class="py-5">
<div class="container px-4 px-lg-5">
	<div class="my-2">
		<span>
			<select name="limit" id="limit">
				<option value="10">10명씩 보기
				<option value="20">20명씩 보기
			</select>
		</span>
	</div>
	<table border="1" class="w-100 text-center type04">
		<thead class="bg-dark text-white">
			<tr>
				<th scope="col" width="13%"> 회원 번호 </th>
				<th scope="col" width="17%"> 아이디 </th>
				<th scope="col" width="50%"> 이메일 </th>
				<th scope="col" width="20%"> 가입일 </th>
			</tr>
		</thead>
		<tbody>
		
			<c:choose>
				<c:when test="${listCount > 0}">
					<c:forEach var="arrayList" items="${arrayList}">
						<tr>
							<td>${arrayList.memNumber}</td>
							<td><a href="./MemSelect.do?memId=${arrayList.memId}">${arrayList.memId}</a></td>
							<td>${arrayList.memEmail}</td>
							<td>${arrayList.memDate}</td>
						</tr>
					</c:forEach>				
				</c:when>
				<c:otherwise>
					<tr><td colspan="4">등록된 회원이 없습니다.</td></tr> 
				</c:otherwise>
			</c:choose>
			
		</tbody>
	</table>
	<div class="text-end my-2">
		<a class="btn btn-outline-dark mt-auto" href="./MemInsert.do">회원 가입</a>
	</div>
	<p align="center">
	<c:choose>
		<c:when test="${page <= 1}">[이전]&nbsp;</c:when>
		<c:otherwise>
			<a href="./MemSearch.do?page=${page-1}&limit=${limit}&keyword=${keyword}">[이전]</a>&nbsp;
		</c:otherwise>
	</c:choose> 
	<c:forEach var="start" begin="${startpage}" end="${endpage}" step="1">
		<c:choose>
			<c:when test="${start==page}">[${start}]</c:when>
			<c:otherwise>
				<a href="./MemSearch.do?page=${start}&limit=${limit}&keyword=${keyword}">[${start}]</a>&nbsp;
			</c:otherwise>
		</c:choose>
	</c:forEach> 
	<c:choose>
		<c:when test="${page >= maxpage}">[다음]</c:when>
		<c:otherwise>
			<a href="./MemSearch.do?page=${page+1}&limit=${limit}&keyword=${keyword}">[다음]</a>
		</c:otherwise>
	</c:choose>
	</p>
</div>
<div class="text-center">
	<form action="./MemSearch.do" method="get"  name="searchForm">		
		<label for="keyword"> 회원 검색 : </label>
		<input type="search" id="keyword" name="keyword" required placeholder= "아이디를 입력하세요.">
		<button type="submit"> 검색 </button>
	</form>
</div>
</section>


<!-- Footer-->
<footer class="py-5 bg-dark">
	<div class="container"><p class="m-0 text-center text-white">Copyright &copy; Your Website 2021</p></div>
</footer>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="./js/scripts.js"></script>
</body>
</html>