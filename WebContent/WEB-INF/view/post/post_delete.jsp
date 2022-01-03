<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
alert("(${postDTO.postNumber})번 글의 삭제가 완료되었습니다.")
location.href="./PostSelectAll.do?gameNumber=${postDTO.gameNumber}";
</script>
</body> 
</html> 