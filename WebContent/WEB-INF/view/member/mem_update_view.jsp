<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
alert("입력하신 아이디 (${memberDTO.memId})의 정보를 수정하였습니다.")
location.href="./MemSelect.do?memId=${memberDTO.memId}";
</script>
</body>
</html>