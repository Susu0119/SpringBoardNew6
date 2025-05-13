<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>fileUploadResult.jsp</h1>
	<h2>파일 업로드 결과</h2>
	
	${map }
	
	<hr>
	
	작성자 : ${map.writer } <br>
	제목 : ${map.title } <br>
	내용 : ${map.content } <br>
	
	<c:forEach var="fileName" items="${map.fileList }" >
		<%-- 파일명 : <a href="./upload/${fileName }">${fileName }</a> <br> --%>
		파일명 : <a href="./download?fileName=${fileName }">${fileName }</a> <br>
		<hr>
		<img src="./download?fileName=${fileName }" width="100">
		
	</c:forEach>
</body>
</html>