package com.itwillbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.domain.Criteria;
import com.itwillbs.domain.PageVO;
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
		return "redirect:/board/listPage";
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
		session.setAttribute("id", "ok");
		
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
	public String modifyPOST(Criteria criteria, RedirectAttributes rttr, /* @ModelAttribute 생략 */ BoardVO uvo) throws Exception{
		logger.info(" modifyPOST() 실행 ");
		
		// 한글처리 인코딩 (이미 필터 처리 완료! - web.xml)
		// 전달된 정보(수정할 내용)를 저장 => 파라메터 자동 수집
		
		logger.info(" uvo:  " + uvo);
		
		// 서비스 - 사용자의 게시판 글 수정 메서드 호출
		bService.modifyBoard(uvo);
		logger.info(" 게시판 수정 성공! ");
		
		// 리스트(listAll.jsp)로 이동 + 수정 완료라는 메세지 alert 출력
		rttr.addAttribute("result", "modifyOK");
		
		return "redirect:/board/listPage?page=" + criteria.getPage();
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String removePOST(Criteria criteria, RedirectAttributes rttr, BoardVO dvo) throws Exception {
		logger.info(" removePOST() 호출! ");
		
		// 전달된 정보 (bno) 저장
		logger.info("dvo :" + dvo);
		
		// 서비스 - 특정 글 정보 삭제 기능 
		int result = bService.removeBoard(dvo);
		
		if(result == 0) {
			// 삭제 실패
			rttr.addFlashAttribute("result", "deleteErr");
			// return "redirect:/board/read?bno="+dvo.getBno();
			return "redirect:/board/listAll";
		} 
		
		// 삭제 성공!
		rttr.addFlashAttribute("result", "deleteOK");
		return "redirect:/board/listPage?page=" + criteria.getPage();
	}
	
	// 게시판 목록 - GET
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public String listPageGET(Criteria criteria
			, HttpSession session
			, @ModelAttribute("result") String result
			, Model model) throws Exception {
		logger.info(" listPageGET() 실행 ");
		
		// 전달정보 result 저장
		logger.info(" result : {}", result );
		
		// 기존의 DB 데이터를 가져와서 화면(view)에 출력
		// = Service를 통해서 DAO를 호출
//		Criteria criteria = new Criteria();
//		criteria.setPage(1);
//		criteria.setPageSize(10);
		
		List<BoardVO> boardList = bService.getBoardListPage(criteria);
		logger.info(" BoardList : {} 개", boardList.size());
		
		// 페이징 처리에 필요한 정보
		PageVO pageVO = new PageVO();
		pageVO.setCriteria(criteria);
		pageVO.setTotalCount(bService.getTotalCount());
		
		// 생성된 데이터를 뷰페이지에 전달(Model)
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageVO", pageVO);
		
		// Session 영역에 정보를 저장 & 전달
		session.setAttribute("updateCheck", true);
		session.setAttribute("id", "ok");
		
		// 연결된 뷰페이지로 이동 (/board/listAll.jsp)
		return "/board/listAll";
	}
	
	// 파일 업로드 뷰페이지 GET
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public void fileUploadGET() throws Exception {
		logger.info(" fileUploadGET() 실행 ");
	}
	
	// 파일 업로드 처리 POST
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String fileUploadPOST(MultipartHttpServletRequest multiRequest, Model model) throws Exception {
		logger.info(" fileUploadPOST() 실행 ");
		
		// 파라메터 데이터
		// logger.info(" title : " + multiRequest.getParameter("title"));
		Map map = new HashMap();
		
		Enumeration enu = multiRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			logger.info(" name : " + name); // 파라메터 이름 출력
			String value = multiRequest.getParameter(name);
			logger.info(" value : " + value);
			
			map.put(name, value);
		}
		
		logger.info(" 폼태그로 전달된 파라메터 정보 저장 완료! (파일 정보 제외한 나머지 정보)");
		logger.info(" map : " + map);
		
		// 파일 업로드 데이터
		List fileList = fileProcess(multiRequest);
		
		// 기존의 파라메터 정보를 저장한 map에 파일의 이름 정보도 추가로 저장
		map.put("fileList", fileList);
		logger.info(" map : " + map);
		
		model.addAttribute("map", map);
		
		return "/board/fileUploadResult";
	}
	
	// 파일 업로드를 처리하는 메서드
	private List fileProcess(MultipartHttpServletRequest multiRequest) {
		final String FAKE_PATH = "/upload";
		
		// 전송된 파일 정보를 저장
		List<String> fileList = new ArrayList<String>();
		
		// 파일 정보 (파라메터) 받아오기
		Iterator<String> fileNames = multiRequest.getFileNames();
		while(fileNames.hasNext()) { // 데이터 있을 때 처리
			String fileName = fileNames.next();
			
			// 임시로 전달받은 파일 정보 저장
			MultipartFile mFile = multiRequest.getFile(fileName);
			
			// 파일명 구하기
			String oFileName = mFile.getOriginalFilename();
			// 파일의 이름을 리스트에 저장
			fileList.add(oFileName);
			
			// 파일 정보 업로드
			// getRealPath()를 통해서 서버의 주소(위치)를 찾는 작업
			// ~~~서버 주소~~~/upload\파일명
			// File file = new File(multiRequest.getRealPath(FAKE_PATH) + "\\" + fileName); 파라메터 이름 저장하는 방식
			File file = new File(multiRequest.getRealPath(FAKE_PATH) + "\\" + oFileName); // 원본 파일 이름 저장하는 방식
			
			if(mFile.getSize() != 0) { // 파일 업로드 정보가 있다면
				if(! file.exists()) {
					// file 객체 경로에 정보가 없을 경우
					if(file.getParentFile().mkdirs()) { // 파일 폴더의 부모의 정보를 생성해라.(make directory), 없으면 만들기
 						try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} // mkdirs()
				} // exists
				
				// 해당 경로의 정보가 있을 때
				try {
					mFile.transferTo(file);
					logger.info(" 파일 업로드 성공! ");
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} // size
		} // while
		
		return fileList;
	}
	
	// 파일 다운로드 동작
	@RequestMapping(value = "/download",method = RequestMethod.GET)
	public void fileDownloadGET(@RequestParam("fileName") String fileName,
			                    HttpServletRequest request,
			                    HttpServletResponse response
			                    ) throws Exception{
		logger.info(" fileDownloadGET() ");
		
		// 다운로드 하려는 폴더 == 업로드한 폴더
		final String FAKE_PATH = "/upload";
		String downFile = request.getRealPath(FAKE_PATH) + "\\" + fileName ;
		
		// 다운로드할 파일 생성
		File file = new File(downFile);
		
		String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
		
		// 다운로드 정보를 출력할 객체
		OutputStream out = response.getOutputStream();
		response.setHeader("Cache-Control", "no-cache");
		//response.addHeader("Content-disposition", "attachment; fileName="+fileName);
		response.addHeader("Content-disposition", "attachment; fileName="+encodedFileName);
		//=> 모든 파일들이 다운로드 형태로 처리 
		
		// 파일정보를 읽어오기
		FileInputStream fis = new FileInputStream(file);
		
		byte[] buffer = new byte[1024 * 8]; // 8KB 
		
		while(true) {
			int data = fis.read(buffer);
			if(data == -1) break; // -1 (EOF, 파일의 끝)
			
			//파일 출력(다운로드)
			out.write(buffer,0,data);
		}
		
		fis.close();
		out.close();
		
	}
	
}
