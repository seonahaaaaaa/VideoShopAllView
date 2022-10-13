package model;

//중복 아이디가 있는지 없는지를 검사하기위한 일련의 과정들 ( 1번째 영역) => Model 영역 (유저가 바라는 결과물을 올바르게 도출하기 위해 구상하는 로직)

import java.util.ArrayList;

import model.vo.VideoVO;

public interface VideoDao {
	public void insertVideo(VideoVO vo, int count) throws Exception;	// 비디오 정보 등록
	
	public ArrayList selectVideo(int idx, String word) throws Exception;
	
	public int VideoModify (VideoVO vo) throws Exception;	// 비디오 정보 수정
	
	public VideoVO selectByVnum(int vnum) throws Exception;	 
	
	public int deleteVideo(int vnum) throws Exception;
 	
}
