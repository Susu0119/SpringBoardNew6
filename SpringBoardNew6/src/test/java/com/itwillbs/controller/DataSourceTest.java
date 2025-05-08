package com.itwillbs.controller;

import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.domain.Criteria;
import com.itwillbs.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"}
		)
public class DataSourceTest {
	
	// DataSource 객체를 사용 -> 객체를 주입해서 사용
	@Inject
	private DataSource ds;
	
	@Inject
	private BoardDAO bDAO;
	
	// mylog
	private static final Logger logger = LoggerFactory.getLogger(DataSourceTest.class);
	
	// @Test
	public void ds_test() {
		System.out.println("디비 연결정보 확인 테스트!");
		System.out.println("ds :" + ds);
	}
	
	// @Test
	public void getServerTime_test() {
		String time = bDAO.getServerTime();
		logger.info("time : {}", time);
	}
	
	@Test
	public void 페이징처리리스트_테스트() {
		logger.info(" 테스트 시작 ");
		try {
			Criteria criteria = new Criteria();
			criteria.setPage(2);
			criteria.setPageSize(10);
			
			// List<BoardVO> boardList = bDAO.listPage();
			List<BoardVO> boardList = bDAO.listPage(criteria);
			logger.info("size: " + boardList.size());
			
			for(BoardVO vo : boardList) {
				// bno, title
				logger.info(vo.getBno() + ":" + vo.getTitle());				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
