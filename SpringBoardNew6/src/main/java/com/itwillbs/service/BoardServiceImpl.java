package com.itwillbs.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.domain.Criteria;
import com.itwillbs.persistence.BoardDAO;

/**
 * 
 * 실제 서비스 동작이 수행되는 객체
 *
 */

@Service
public class BoardServiceImpl implements BoardService {
	
	// mylog
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Inject
	private BoardDAO bDAO;
	
	@Override
	public String getServerTime() {
		return bDAO.getServerTime();
	}
	
	@Override
	public void registBoard(BoardVO vo) throws Exception {
		logger.info(" 게시판 글쓰기 - registBoard(BoardVO vo) 실행! ");
		logger.info(" 전달받은 정보를 DAO로 전달 + DAO 동작을 처리 ");
		
		bDAO.insertBoard(vo);
		
		logger.info(" DAO 실행 완료 -> 컨트롤러로 이동 ");
	}
	
	@Override
	public List<BoardVO> getBoardListAll() throws Exception {
		logger.info(" getBoardListAll() 호출! ");
		
		// DAO 동작 호출
		List<BoardVO> boardList = bDAO.selectBoardListAll();
		
		logger.info(" BoardList : {} 개", boardList.size());
		
		return boardList;
	}
	
	@Override
	public BoardVO getBoard(int bno) throws Exception {
		logger.info(" getBoard(int bno) 호출! ");
		
		// DAO - 특정 글정보를 조회
		BoardVO vo = bDAO.selectBoard(bno);
		
		return vo;
	}
	
	@Override
	public void increaseViewCnt(int bno) throws Exception {
		logger.info(" increaseViewCnt(int bno) 호출! ");
		
		bDAO.updateViewCount(bno);
	}
	
	@Override
	public void modifyBoard(BoardVO uvo) throws Exception {
		logger.info(" modifyBoard(BoardVO uvo) 호출! ");
		
		bDAO.updateBoard(uvo);
	}
	@Override
	public Integer removeBoard(BoardVO dvo) throws Exception {
		logger.info(" removeBoard(BoardVO dvo) 호출! ");
		return bDAO.deleteBoard(dvo);
	}
	
	@Override
	public List<BoardVO> getBoardListPage(Criteria criteria) throws Exception {
		logger.info(" getBoardListPage(Criteria criteria) 호출! ");
		
		return bDAO.listPage(criteria);
	}
	
	@Override
	public int getTotalCount() throws Exception {
		logger.info(" getTotalCount() 호출! ");
		return bDAO.selectTotalCount();
	}
}
