package com.itwillbs.domain;

/*
 * 페이징 처리 위한 객체 
 * - 페이징 블럭 위한 정보를 저장
 * 
 * 시작 페이지 번호 (startPage)
 * startPage = (endPage - pageBlock) + 1
 * 끝 페이지 번호 (endPage)
 * endpage = (int)(Math.ceil(page / (double)pageblock)) * pageblock;
 * 전체 데이터(글) 개수(totalCount)
 * => DB 조회
 * 이전 페이지 링크(prev)
 * 다음 페이지 링크(next)
 * 
 *=> => DB 조회 & endPage 다시 계산하기
 * */

// 한 페이지에 총 10개씩 출력, 글이 총 122개 => 13 페이지 필요
// 페이지 블럭의 크기 10개 (1,2,3,4,5 ... 10)

// 3 페이지 보는 중 => startPage: 1, endPage: 11, next 0, prev X

// 10 페이지 보는 중 startPage: 1, endPage: 10, next O, prev X 

// 11 페이지 보는 중 => startPage 11, endPage 20 -> 13, next O, prev X 

// 구하는 방법
// tmpEndPage = (int)Math.ceil(totalCount / (double)pageSize) 다시 계산하기
// 끝 페이지와 비교해서 변경. 계산으로 만들어진 것과 직접 만든 것의 차이점

// 이전 페이지 링크(prev)
// prev = startPate = 1? false : true; 이거 쓸데없음
// prev = start != 1; 이렇게하면 1일 때 false, 다르면 true 가 나옴
// 다음 페이지 링크(next)
// next = endPage * pageSize >= totalCount? false : true;
// next = endPage * pageSize < totalCount;

public class PageVO {
	private int totalCount; 		// 총 글의 개수
	private int startPage;			// (페이지 블럭) 시작 페이지 번호
	private int endPage;			// (페이지 블럭) 끝 페이지 번호
	private boolean prev;			// 이번 버튼
	private boolean next;			// 다음 버튼
	
	private int pageBlock = 2;		// 페이지 블럭의 개수 (일단 10개로 고정)
	
//	private int page;				// 페이지 정보	
//	private int pageSize;			// 한 페이지에 출력하는 개수
	private Criteria criteria;		// 위 두 정보 대신 두 정보가 다 들어있는 Criteria 이용

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	
	// 내가 만든 페이징 처리 계산 함수
	private void calcData() {
		System.out.println("페이징처리 계산 시작!");
		endPage = (int)(Math.ceil(criteria.getPage() / (double)pageBlock)) * pageBlock;
		
		startPage = (endPage - pageBlock) + 1;
		
		// endPage 다시 계산하기
		int tmpEndPage = (int)Math.ceil(totalCount / (double)criteria.getPageSize());
		
		if(endPage > tmpEndPage) {
			// endPage값이 내가 가진 페이지 수 보다 클 때
			endPage = tmpEndPage;
		}
		
		prev = startPage != 1;
		
		next = endPage * criteria.getPageSize() < totalCount;
		
		System.out.println("페이징처리 계산 끝!");
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public boolean isNext() {
		return next;
	}

	public int getPageBlock() {
		return pageBlock;
	}

	public Criteria getCriteria() {
		return criteria;
	}


	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}

	@Override
	public String toString() {
		return "PageVO [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", pageBlock=" + pageBlock + ", criteria=" + criteria + "]";
	}
	
}
