<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<h1>/board/read.jsp</h1>

<form role="form" action="" method="get">
	<input type="hidden" name="bno" value="${boardVO.bno }">
	<input type="hidden" name="page" value="${param.page }">
</form>

<div class="box box-primary">
	<div class="box-header with-border">
		<h3 class="box-title">게시판 본문보기</h3>
	</div>
	<!-- /.box-header -->
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">번 호</label> 
			<input type="text" class="form-control" id="exampleInputEmail1" name="title" value="${boardVO.bno }" readonly>
		</div>
	</div>
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">조회수</label> 
			<input type="text" class="form-control" id="exampleInputEmail1" name="title" value="${boardVO.viewcnt }" readonly>
		</div>
	</div>
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">제 목</label> 
			<input type="text" class="form-control" id="exampleInputEmail1" name="title" value="${boardVO.title }" readonly>
		</div>
	</div>
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">작성자</label> 
			<input type="text" class="form-control" id="exampleInputEmail1" name="writer" value="${boardVO.writer }" readonly>
		</div>
	</div>
	<div class="box-body">
         <label>내 용</label>
         <textarea class="form-control" rows="3" name="content" readonly>${boardVO.content }</textarea>
       </div>

	<div class="box-footer">
		<c:if test="${!empty id }">
			<button type="submit" class="btn btn-danger">수정하기</button>
			<button type="submit" class="btn btn-warning">삭제하기</button>
			<button type="submit" class="btn btn-primary">목록으로</button>
		</c:if>
	</div>
</div>

<!-- <script src="./jQuery/jQuery-2.1.4.min.js"></script> 생략 ... 이미 넣어져있어서 -->
<script>
	// JSP(Java) -> HTML -> JavaScript -> JQuery
	// jQuery(js코드를 필요한 것만 따로 모아놓음) 사용
	$(document).ready(function(){
		// alert("Hello!");
		// 목록으로 버튼을 클릭하면 이동하도록
		$(".btn-primary").click(function(){
			location.href="/board/listPage?page=${param.page }";
		});
		
		// 폼 태그 정보 가져오기
		var fr = $("form[role='form']");
		console.log(fr);
		
		// 수정하기 버튼 클릭 시 
		$(".btn-danger").click(function(){
			// alert("수정하기 버튼 클릭!")
			// 글 번호를 가지고 이동 (폼태그)
			// /board/modify라는 주소로 이동, get방식 사용 페이지 이동
			fr.attr("action", "/board/modify");
			fr.submit();
		});

		// 삭제하기 버튼 클릭 시 
		$(".btn-warning").click(function(){
			// alert("삭제하기 버튼 클릭!")
			
			fr.attr("action", "/board/remove");
			fr.attr("method", "post");
			fr.submit();
		});
	});
</script>

<%@ include file="../include/footer.jsp"%>