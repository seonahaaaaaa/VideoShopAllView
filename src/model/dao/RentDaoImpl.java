package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.RentDao;
import model.vo.CustomerVO;

public class RentDaoImpl implements RentDao {

	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@192.168.0.35:1521:xe";
	final static String USER = "yun";
	final static String PASS = "1234";

	public RentDaoImpl() throws Exception {
		// 1. 드라이버로딩
		Class.forName(DRIVER); // 드라이버 로딩
		System.out.println("드라이버로딩 성공");
	}

	public void rentVideo(String tel, int vnum) throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;

		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 만들기
			String sql = "INSERT INTO RENT(RENTNO ,VIDEONO , CUSTTEL1 , BORROWDAY , RETURN) "
					+ "  VALUES(seq_rent_no.nextval, ? , ? , sysdate , 'F') ";

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setInt(1, vnum);
			ps.setString(2, tel);

			// 5. 전송
			result = ps.executeUpdate();

		} finally {
			ps.close();
			con.close();
		}
	}

	public void returnVideo(int vnum) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;

		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 만들기
			String sql = "UPDATE RENT SET RETURN = 'T' WHERE VIDEONO = ? AND RETURN = 'F' ";
				// RETURN = 'F' : 반납 여부를 기재하기 않을시에 이전에 반납이 되었던 기록까지 다시한번 반납(Y)로 변경되기 때문에 조건 추가해야함.

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setInt(1, vnum);

			// 5. 전송
			result = ps.executeUpdate();

		} finally {
			ps.close();
			con.close();
		}
	}

	// 미납 목록
	public ArrayList selectList() throws Exception {
		ArrayList data = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// 2. 연결객체 얻어오기
		con = DriverManager.getConnection(URL, USER, PASS);


		// 3. sql 문장 만들기
	
		String sql = "select v.VIDEONO vvideono , v.VIDEONAME vvideoname , c.CUSTNAME ccustname , c.CUSTTEL1 ccusttel , r.BORROWDAY+3 returnday , '미납' return "
				+ " from	CUSTOMER c inner join RENT r"	
				+ " on c.CUSTTEL1 = r.CUSTTEL1 inner join VIDEO v on r.VIDEONO = v.VIDEONO where r.RETURN = 'F'";
		
		// 4. 전송객체
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		

		while(rs.next()) {
			ArrayList temp = new ArrayList();
			temp.add(Integer.parseInt(rs.getString("vvideono")));
			temp.add(rs.getString("vvideoname"));
			temp.add(rs.getString("ccustname"));
			temp.add(rs.getString("ccusttel"));
			temp.add(rs.getString("returnday"));
			temp.add(rs.getString("return"));
		
			data.add(temp);
			
		}
	
		return data;
		
		
	}

	public String selectByTel(String tel) throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = null;	// custTel1에 해당하는 name 의 결과값을 담는 변수 
		
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 만들기
			String sql = "SELECT custName FROM CUSTOMER WHERE custTel1 = ? ";
		
			ps = con.prepareStatement(sql);
			
			ps.setString(1, tel);
			
			// 4. 전송
			rs = ps.executeQuery();

			if(rs.next()) {
				result = rs.getString("custName");
			}

			return result;
			
			}finally {
				rs.close();
				ps.close();
				con.close();
			
			}
		
		
	}

}
