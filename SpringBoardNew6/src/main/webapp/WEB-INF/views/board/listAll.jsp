<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp"%>
${pageVO }
<div class ="content">
	<h1>listAll.jsp</h1>
	<%-- ${result }
	${boardList } --%>
	
	<!-- 게시판 목록 출력 -->
	<div class="box">
		<div class="box-header with-border">
		  <h3 class="box-title">ITWILL 게시판 목록</h3>
		</div>
		<!-- /.box-header -->
		<div class="box-body">
		  <table class="table table-bordered">
		    <tbody>
		    	<tr>
			      	<th style="width: 10px">BNO</th>
				    <th>TITLE</th>
				    <th>WRITER</th>
				    <th>REGDATE</th>
				    <th style="width: 40px">VIEWCOUNT</th>
				</tr>
				<c:forEach var="vo" items="${boardList}"  >
					<tr>
					    <td>${vo.bno }</td>
					    <td>
					    	<a href="/board/read?bno=${vo.bno }&page=${criteria.page }">${vo.title }</a>
					    </td>
					    <td>${vo.writer }</td>
					    <td><fmt:formatDate value="${vo.regdate }"/></td>
					    <td><span class="badge bg-red">0</span></td>
				  	</tr>
			  	</c:forEach>
		  	</tbody>
		  </table>
		</div>
		<!-- /.box-body -->
		  <div class="box-footer clearfix">
		    <ul class="pagination pagination-sm no-margin pull-right">
		      <c:if test="${pageVO.prev }">
			      <li><a href="/board/listPage?page=${pageVO.startPage-1 }">«</a></li>
		      </c:if>
		      <c:forEach var="i" begin="${pageVO.startPage }" end="${pageVO.endPage }" step="1">
			      <li class="${pageVO.criteria.page == i? 'active' : '' }"><a href="/board/listPage?page=${i }">${i }</a></li>		      
		      </c:forEach>
		      <c:if test="${pageVO.next }">
			      <li><a href="/board/listPage?page=${pageVO.endPage+1 }">»</a></li>
		      </c:if>  
		    </ul>
		  </div>
		</div>
	</div>

<script type="text/javascript">
	// EL 표현식의 데이터 -> JS 에서 출력
	var result = '${result }';
	// alert(result);
	
	if(result == "createOK") {
		// alert(" 글쓰기 완료! ");
		Swal.fire({
			title: " 글쓰기 완료! ",
			text: " 게시판 리스트로 이동합니다! ",
			icon: "success"
		});
	}
	
	if(result == "modifyOK") {
		// alert(" 글수정 완료! ");
		Swal.fire({
			title: " 글수정 완료! ",
			text: " 게시판 리스트로 이동합니다! ",
			icon: "success"
		});
	}
	
	if(result == "deleteOK") {
		Swal.fire({
			title: " 글삭제 완료! ",
			text: " 게시판 리스트로 이동합니다! ",
			icon: "success"
		});
	}
	
	if(result == "deleteErr") {
		Swal.fire({
			title: " 글삭제 실패! ",
			text: " 게시판 리스트로 이동합니다! ",
			icon: "error"
		});
	}
</script>

<script type="text/javascript">
	// JS 사용 -> 사용 가능!
	// Swal.fire("SweetAlert2 is working!");
	// 제이쿼리 사용 선언 후 추가 -> 사용 가능!
	$(document).ready(function() {
		// Swal.fire("SweetAlert2 is working!");
	})
</script>

<%@ include file="../include/footer.jsp"%>