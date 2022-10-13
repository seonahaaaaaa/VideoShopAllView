package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.VideoDao;
import model.vo.CustomerVO;
import model.vo.VideoVO;

public class VideoDaoImpl implements VideoDao {
	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@192.168.0.35:1521:xe";
	final static String USER = "yun";
	final static String PASS = "1234";

	public VideoDaoImpl() throws Exception {

		// 1. 드라이버로딩
		Class.forName(DRIVER); // 드라이버 로딩
		System.out.println("드라이버로딩 성공");
	}
	
	// 등록
	public void insertVideo(VideoVO vo, int count) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		// 2. Connection 연결객체 얻어오기
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 만들기
			String sql = "INSERT INTO VIDEO(videoNo , genre , videoName , director , actor , exp) "
					+ "  VALUES(seq_video_no.nextval, ?,?,?,?,?)";

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getGenre());
			ps.setString(2, vo.getVideoName());
			ps.setString(3, vo.getDirector());
			ps.setString(4, vo.getActor());
			ps.setString(5, vo.getExp());

			// 5. sql 전송
			for (int i = 0; i < count; i++) { // 입고 수량을 뜻하는 count 변수만큼 데이터 전송을 반복한다.
				ps.executeUpdate();
			}
			// 6. 닫기
		} finally {
			ps.close();
			con.close();
		}
	}

	// 검색
	public ArrayList selectVideo(int idx, String word) throws Exception{
		// 비디오 검색시 가져올 데이터의 갯수를 모르니 배열을 사용하여 tbModelVideo 데이터 전체 출
		ArrayList data = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// 2. 연결객체 얻어오기
		con = DriverManager.getConnection(URL, USER, PASS);


		// 3. sql 문장 만들기
		String []colNames = {"videoName" , "director"}; // 0번방 : 제목컬럼명 / 1번방 : 감독컬럼명
		String sql = "SELECT videoNo , videoName , director , actor FROM VIDEO "
				+ "	WHERE " + colNames[idx]+" LIKE '%" + word + "%'";
			// ? 사용시 : '컬럼명' , '값' ==> ' ' 붙여져서 출려되어 인식 불가	LIKE %?% : ? LIKE 내의 ? 는 문자열 값으로 setString 으로 받을시 값이 문자로 취급되어 ' ' 자동으로 붙여서 인식되게 됨.
									//	==> 테이블명 LIKE '%?%' 사용 할 수 없어서 인덱스 방의 위치를 얻어와 값을 출력.
									// videoName , director WHERE 조건문에 배치시 videoName 과 director 의 데이터 값이 중복 될 수 있으므로 배열로 위치 값을 가져오게끔 함.
		
		// 4. 전송객체
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()) {
			ArrayList temp = new ArrayList();
			temp.add(Integer.parseInt(rs.getString("videoNo")));
			temp.add(rs.getString("videoName"));
			temp.add(rs.getString("director"));
			temp.add(rs.getString("actor"));
			data.add(temp);
			
		}
	
		return data;
	} 
	
	// 수정
	public int VideoModify(VideoVO vo) throws Exception {
		// 지역변수 초기화
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
	
		// 2. Connection 연결객체 얻어오기
		try {
			con = DriverManager.getConnection(URL , USER , PASS);

		// 3. sql 문장 만들기
		String sql = "UPDATE VIDEO SET genre = ? , videoName = ? , director = ? , actor = ? , exp = ? WHERE videoNo = ?";
			
		// 4. sql 전송객체 (PreparedStatement)
		ps = con.prepareStatement(sql);
	
		ps.setString(1, vo.getGenre());
		ps.setString(2, vo.getVideoName());
		ps.setString(3, vo.getDirector());
		ps.setString(4, vo.getActor());
		ps.setString(5, vo.getExp());
		ps.setInt(6, vo.getVideoNo());

		
		// 5. sql 전송
		result = ps.executeUpdate();
		
		return result;
		
		}finally {
			con.close();
			ps.close();
			
		}
		
	}

	
	public VideoVO selectByVnum(int vnum) throws Exception{
		VideoVO vo = new VideoVO();
		
		// 지역변수 초기화
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// 2. Connection 연결객체 얻어오기
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 만들기
			String sql = "SELECT * FROM VIDEO WHERE videoNo = ?";

			// 4. sql 전송객체
			ps = con.prepareStatement(sql);
			ps.setInt(1, vnum);

			// 5. 전송
			rs = ps.executeQuery();
			
			if(rs.next()) {
				vo.setVideoNo(Integer.parseInt(rs.getString("videoNo")));
				vo.setVideoName(rs.getString("videoName"));
				vo.setGenre(rs.getString("genre"));
				vo.setDirector(rs.getString("director"));
				vo.setActor(rs.getString("actor"));
				vo.setExp(rs.getString("exp"));
			}
		return vo;
		
	}finally {
		rs.close();
		ps.close();
		con.close();
	}
	}

	public int deleteVideo(int vnum) throws Exception {
		// 지역변수 초기화
			Connection con = null;
			PreparedStatement ps = null;
			int result = 0;
			
		// 2. Connection 연결객체 얻어오기
			try {
				con = DriverManager.getConnection(URL , USER , PASS);

		// 3. sql 문장 만들기
			String sql = "DELETE FROM VIDEO WHERE videoNo = ?";
					
		// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			

			ps.setInt(1, vnum);
			ps.executeUpdate();
		
		
		
		return 0;
	}finally {
		ps.close();
		con.close();
	}
	}	
}




