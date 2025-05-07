package com.itwillbs.domain;

/**
 * 
 * 페이징 처리에 필요한 정보 저장 객체
 * page(시작 인덱스), size(크기)
 *
 */

public class Criteria {
	private int page; 		// 페이지 정보 ( 몇 페이지 인가? )
	private int pageSize; 	// 페이지 크기 ( 한 페이지에 몇 개씩 출력 )

	public Criteria() {
		// 페이징 처리 객체 기본 생성자
		this.page = 1;
		this.pageSize = 10;
	}
	
	// alt shift s + r => get/set method 
	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}
	public void setPageSize(int pageSize) {
		if(pageSize <= 0 || pageSize > 100) {
			this.pageSize = 1;
			return;
		}
		this.pageSize = pageSize;
	}
	// 객체 (VO)의 get메서드는 mapper의 #{이름 } 코드와 연결
	public int getPage() {
		return page;
	}
	public int getPageSize() {
		return pageSize;
	}
	
	// 변수와 상관없이 !
	// mapper에 값을 전달하기 위한 메서드
	// => 메서드 이름에서 get을 뺀 나머지 이름으로 mapper 호출 ! (첫글자를 소문자로)
	public int getStartPage() {
		// page 번호를 조회하는 인덱스 번호로 변경 계산
		return (this.page - 1) * pageSize;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", pageSize=" + pageSize + "]";
	}
	
}
