package com.itwillbs.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.domain.Criteria;

/**
 * 
 * DB의 정보를 처리하는 객체
 * => DB에서 수행해야하는 동작(메서드)을 정의
 *
 */

@Repository
public interface BoardDAO {
	
	// 디비 서버의 시간 정보를 가져오기
	public String getServerTime();
	
	// 게시판에 글 정보를 저장하는 동작(글쓰기)
	public void insertBoard(BoardVO vo) throws Exception;
	
	// 게시판 글 전체 목록 조회하기
	public List<BoardVO> selectBoardListAll() throws Exception;
	
	// 게시판 글 정보를 조회
	public BoardVO selectBoard(int bno) throws Exception;
	
	// 특정 글 조회 시 조회수 증가
	public void updateViewCount(int bno) throws Exception;
	
	// 특정 글 수정
	public void updateBoard(BoardVO uvo) throws Exception;
	
	// 특정 글 삭제
	public Integer deleteBoard(BoardVO dvo) throws Exception;
	
	// 게시판 목록 조회 (+ 페이징 처리)
	public List<BoardVO> listPage(int page) throws Exception;
	public List<BoardVO> listPage(Criteria criteria) throws Exception;
	
	// 글 전체 개수 조회
	public int selectTotalCount() throws Exception;
}
