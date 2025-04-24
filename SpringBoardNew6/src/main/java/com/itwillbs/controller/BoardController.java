package com.itwillbs.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.service.BoardService;

@Controller
@RequestMapping(value="/board/*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// BoardService 객체 주입
	@Inject
	private BoardService bService;
	
	// http://localhost:8088/controller/time (X)
	// http://localhost:8088/controller/board/time (X)
	// http://localhost:8088/board/time	
	@RequestMapping(value="/time", method=RequestMethod.GET)
	public void getServerTimeGET(Model model) {
		logger.info(" DB 서버의 시간 정보를 가져오기 ");
		
		String time = bService.getServerTime();
		logger.info(" time : {}", time);
		
		model.addAttribute("time", time);
		logger.info(" 연결된 뷰페이지에 정보 전달 ");
	}
	
	// http://localhost:8088/board/regist	
	// 게시판 글쓰기 - GET
	@RequestMapping(value="/regist", method=RequestMethod.GET)
	public void registGET() throws Exception {
		logger.info(" registGET() 실행 ");
		logger.info(" 글쓰기 뷰페이지(/board/regist.jsp)를 연결해서 보여줌 ");
	}
	
	// 게시판 글쓰기 - POST
	@RequestMapping(value="/regist", method =RequestMethod.POST)
	public String registPOST(BoardVO vo, RedirectAttributes rttr) throws Exception {
		logger.info(" registPOST() 실행! ");
		// 인코딩 데이터 처리 - 이미 web.xml 파일에 설정되어 있음! 
		
		// 폼태그에서 전달된 파라메터를 저장
		logger.info("vo: {}", vo);
		
		// 서비스 호출 - 게시판 글쓰기 동작을 처리 (DAO 호출)
		bService.registBoard(vo);
		
		// 정보 하나 전달 ! (createOK)
		rttr.addFlashAttribute("result", "createOK");
		// => 1회성 데이터 전달
		
		// 게시판 글 목록 페이지로 이동
		// return "/board/listAll"; (X)
		return "redirect:/board/listAll";
	}
	
	// 게시판 목록 - GET
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public void listAllGET(HttpSession session, @ModelAttribute("result") String result, Model model) throws Exception {
		logger.info(" listAllGET() 실행 ");
		
		// 전달정보 result 저장
		logger.info(" result : {}", result );
		
		// 기존의 DB 데이터를 가져와서 화면(view)에 출력
		// = Service를 통해서 DAO를 호출
		List<BoardVO> boardList = bService.getBoardListAll();
		logger.info(" BoardList : {} 개", boardList.size());
		
		// 생성된 데이터를 뷰페이지에 전달(Model)
		model.addAttribute("boardList", boardList);
		
		// Session 영역에 정보를 저장 & 전달
		session.setAttribute("updateCheck", true);
		
		// 연결된 뷰페이지로 이동 (/board/listAll.jsp)
	}
	
	// 게시판 본문보기 GET  /board/read 
	@RequestMapping(value="/read", method=RequestMethod.GET)
	public String readGET(HttpSession session, @RequestParam("bno") int bno, Model model) throws Exception {
		logger.info(" readGET() 실행 ");
		
		// 서비스 -> DAO -> mapper 호출
		// 전달 정보(bno)를 저장
		logger.info(" bno : {} ", bno);
		
		// 리스트 -> 본문 이동 시 마다 조회수가 증가
		// (본문에서 새로고침 수행해도 조회수 증가 X)
		boolean updateCheck = (boolean) session.getAttribute("updateCheck");
		if(updateCheck) {
			// 서비스 -> 글 조회수를 1씩 증가 동작
			bService.increaseViewCnt(bno);
			session.setAttribute("updateCheck", false);
		}
		
		// 서비스 -> 글 하나의 정보 조회하는 동작 호출
		BoardVO vo = bService.getBoard(bno);
		logger.info(" vo : {} ", vo);
		
		// DAO에서 받아온 글 정보를 연결된 뷰페이지(/board/read.jsp)로 이동
		model.addAttribute(vo);
		
		return "/board/read";
	}
	
	// 글정보 수정하기 - GET
	@RequestMapping(value= "/modify", method= RequestMethod.GET)
	public void modifyGET(Model model, @RequestParam("bno") int bno) throws Exception{
		logger.info(" modifyGET() 실행 ");
		
		// 전달 정보가 있는지 확인, 저장 (파라메터로 저장될 것)
		logger.info(" bno:{} ", bno);
		
		// bno를 사용해서 정보를 DB에서 가져오기
		BoardVO vo = bService.getBoard(bno);
		model.addAttribute(vo);
		
		// 연결된 뷰페이지 출력
		
	}
	
	// 글정보 수정하기 - POST
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifyPOST(RedirectAttributes rttr, /* @ModelAttribute 생략 */ BoardVO uvo) throws Exception{
		logger.info(" modifyPOST() 실행 ");
		
		// 한글처리 인코딩 (이미 필터 처리 완료! - web.xml)
		// 전달된 정보(수정할 내용)를 저장 => 파라메터 자동 수집
		
		logger.info(" uvo:  " + uvo);
		
		// 서비스 - 사용자의 게시판 글 수정 메서드 호출
		bService.modifyBoard(uvo);
		logger.info(" 게시판 수정 성공! ");
		
		// 리스트(listAll.jsp)로 이동 + 수정 완료라는 메세지 alert 출력
		rttr.addAttribute("result", "modifyOK");
		
		return "redirect:/board/listAll";
	}
	
}
