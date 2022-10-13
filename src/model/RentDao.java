package model;

// 인터페이스 먼저 생성

import java.util.ArrayList;

public interface RentDao {
	
	// 대여	: 전화번호를 입력하면 고객면 자동으로 출력이 되고 , 대여 할 비디오 번호 입력 후 대여 버튼 클릭시 대여한 비디오 번호에 해당하는 비디오 정보 출력
	public void rentVideo(String tel , int vnum) throws Exception;
	// 반납
	public void returnVideo(int vnum) throws Exception;
	// 미납 목록 검색
	public ArrayList selectList() throws Exception; 

	public String selectByTel(String tel) throws Exception; 
}
