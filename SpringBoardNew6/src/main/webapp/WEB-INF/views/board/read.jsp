<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<h1>/board/read.jsp</h1>

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
		<button type="submit" class="btn btn-primary">글쓰기</button>
	</div>
</div>

<%@ include file="../include/footer.jsp"%>