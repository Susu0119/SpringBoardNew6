<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<div class ="content">
	<h1>listAll.jsp</h1>
	${result }
</div>

<script type="text/javascript">
	// EL 표현식의 데이터 -> JS 에서 출력
	var result = '${result }';
	// alert(result);
	
	if(result == "createOK") {
		alert(" 글쓰기 완료! ");
	}
</script>

<%@ include file="../include/footer.jsp"%>