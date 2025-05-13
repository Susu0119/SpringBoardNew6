<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<h1>/board/regist.jsp</h1>

<div class="box box-primary">
	<div class="box-header with-border">
		<h3 class="box-title">게시판 파일 업로드</h3>
	</div>
	<!-- /.box-header -->
	<!-- form start -->
	
	<!-- 
		파일 업로드 사용을 위한 폼태그 작성 규칙
		1) method 는 항상 post 방식
		2) 
	 -->
	
	<form role="form" method="post" enctype="multipart/form-data">
		<div class="box-body">
			<div class="form-group">
				<label for="exampleInputEmail1">제 목</label> 
				<input type="text" class="form-control" id="exampleInputEmail1" name="title" placeholder="제목을 입력하시오.">
			</div>
		</div>
		<div class="box-body">
			<div class="form-group">
				<label for="exampleInputEmail1">작성자</label> 
				<input type="text" class="form-control" id="exampleInputEmail1" name="writer" placeholder="작성자 이름을 입력하시오.">
			</div>
		</div>
		<div class="box-body">
          <label>내 용</label>
          <textarea class="form-control" rows="3" name="content" placeholder="내용을 입력하시오."></textarea>
        </div>
		<div class="box-body">
			<div class="form-group fileDiv">
				<label for="exampleInputEmail1">첨부파일</label> 
				<!-- <input type="file" class="form-control" id="exampleInputEmail1" name="writer" placeholder="작성자 이름을 입력하시오."> -->
			</div>
		</div>
		<div class="box-footer">
			<button type="submit" class="btn btn-primary">파일 업로드</button>
			<button type="button" class="btn btn-danger">파일 추가</button>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		var cnt = 1;
		$(".btn-danger").click(function(){
			// alert(" 파일 추가 버튼 클릭 ");
			$(".fileDiv").append('<input type="file" class="form-control" id="exampleInputEmail1" name="file' + cnt +'" placeholder="작성자 이름을 입력하시오.">');
			cnt ++;
		});
	});
</script>

<%@ include file="../include/footer.jsp"%>