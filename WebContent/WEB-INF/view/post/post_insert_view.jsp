<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
alert("(${gameDTO.gameName}) 게시판에 \n 글 (${postDTO.postTitle})을 등록하였습니다.")
location.href="./PostSelect.do?gameNumber=${gameDTO.gameNumber}&postNumber=${postDTO.postNumber}"
</script>
</body>
</html> 