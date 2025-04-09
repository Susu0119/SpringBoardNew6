package com.itwillbs.persistence;

import org.springframework.stereotype.Repository;

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
}
