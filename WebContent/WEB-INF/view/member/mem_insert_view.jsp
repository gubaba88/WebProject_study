<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:forEach var="memberDTO" items="${arrayList}">
	<c:if test="${memberDTO.memId == memId}">
		<script type="text/javascript">
			if (confirm("입력하신 아이디(${memId})이미 등록되어 있습니다./n로그인 하시겠습니까?")) {
				location.href="./Login.do"
			} else {
				location.href="./MemInsert.do"	
			}
		</script>
	</c:if>
</c:forEach>
<script type="text/javascript">
alert("입력하신 아이디(${memId})로 가입 되었습니다.")
location.href="./BoardList.do"
</script>
</head>
<body>

</body>
</html>